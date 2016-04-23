package org.zlx;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.LastInfo;
import model.Line;
import model.Station;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import util.DB;

import com.alibaba.fastjson.JSON;


public class Init {
	
	static SqlSession session;
	
	
	public Init(SqlSession se){
		session=se;
	}
	
	@Test
    public  void initLines( Map<String,Line>  lineMap ){
			try {
				//初始化 所有线路的信息
				List list=session.selectList("all.getBusStation");
				
				Line line;
				for(int i=0;i<list.size();i++){
					Map  tempmap=(Map)list.get(i);
					//获取数据库信息 SUBROUTEID    STATIONID   DUALSERIALID
					BigDecimal routeid=(BigDecimal)tempmap.get("SUBROUTEID");
					if((routeid+"").equals(10))
						System.out.println("error before   "+10);
					String stationid=(String)tempmap.get("STATIONID");
					BigDecimal seq=(BigDecimal)tempmap.get("DUALSERIALID");
					
					//stations
					if( lineMap.containsKey(routeid+"") ){
						line=lineMap.get(routeid+"");
					}
					else{
						line=new Line();
					}
					line.getStations().add(stationid);
					line.getStationIndexs().add(seq.intValue());
					lineMap.put(routeid+"", line);
				}			
				
				System.out.println("初始化线路成功"+(new Date()));
			} finally {
				//session.close();			
			}
		}
	

	
	@Test	
	// 初始化 最新信息  	
	public void updateLastInfo(List<LastInfo> lastInfos){

		try {
			//只是获取最新的 15分钟之内的信息
			Date date =new Date(System.currentTimeMillis()-1000*60*15 );
			List list=session.selectList("all.getLastInfo",date);

			for(int i=0;i<list.size();i++){
				Map  map=(Map)list.get(i);
				String subroutid=(String)map.get("subroutid");
				String arrLeft=(String)map.get("arrLeft");
				Integer stationnumber=(Integer)map.get("stationnumber");
				String busid=(String)map.get("busid");
				Timestamp acptTime=(Timestamp)map.get("acptTime");


				LastInfo lastInfo =new LastInfo();
				lastInfo.setBusid(busid);
				lastInfo.setLineStr(subroutid);
				lastInfo.setStationindex(stationnumber);
				lastInfo.setArriveOrLeft(arrLeft.equals("1")?true:false );

				lastInfos.add(lastInfo);

			}			

		} finally {
			//session.close();
		}		
		System.out.println("更新站点信息成功"+(new Date()));

	}

	

   public void getDistance(List<LastInfo> lasts, Map<String,Line>  lineMap,Map<String,Station>  stationMapResult){

		System.out.println("begin the get distance");

		
		Station s;
		for(int i=0;i<lasts.size();i++){
			LastInfo last=lasts.get(i);
			String lineStr =last.getLineStr();
			int index=last.getStationindex();   
			
			//如果这个线路不存在（gprs表里面有，但是基础数据表里面没有）
			Line line=lineMap.get(lineStr);
			if(line==null){
				System.out.println("error:  "+lineStr);
				continue;
			}
			//得到这个着条线的所有 站点
			List<String>  stations =line.getStations();			
			for(int j=0;j<stations.size();j++){

				//站点标示
				String stationNO=(String) stations.get(j);
				int index2=line.getStationIndexs().get(j);
                 
				//是否在 到达的站点的后面
				if(index2>=index){
					if(stationMapResult.containsKey(stationNO)){
						s=stationMapResult.get(stationNO);
					}
					else{
						s=new Station();
						s.setStationNO(stationNO);
					}
					
					
					//这站点是否包含这条线
					if(s.getLines().contains(lineStr)){
						if( s.getDistanceByLine(lineStr)> (index2-index) )	{
							int lineIndex=s.getLines().indexOf(lineStr); //这个线在list 中的索引
							s.getStationDistances().set(lineIndex, (index2-index)) ; //更新
						}
					}
					else//不存在这条线 line
						s.addLine((index2-index), lineStr);
					
					stationMapResult.put(stationNO, s);
					
					
					
				}//if
				
			}//for
		}//for
		
		System.out.println(JSON.toJSONString(stationMapResult));
		
		System.out.println("end of get distance");

	}

	@Test 
	public  void test(){
		SqlSession session = DB.sessionFactory.openSession();
		List<LastInfo> lastInfos=new ArrayList<LastInfo>(1000);
		Map<String,Station>  stationMapResult=new HashMap<String,Station>();//string 存放站点的名称，对应这个站点的线路
		
		Map<String,Line>  lineMap=new HashMap<String,Line>();

		
		long old=System.currentTimeMillis();
		Init init=new Init(session);
		init.initLines(lineMap);
		init.updateLastInfo(lastInfos);
		System.out.println("init elapse"+(System.currentTimeMillis() -old));
		
		
	}

	
	

}
