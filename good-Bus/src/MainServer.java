import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;

import model.LastInfo;
import model.Line;
import model.Msg;
import model.Station;

import org.apache.ibatis.session.SqlSession;

import util.DB;


public class MainServer {
	
	
	static int MAXCOUNT=500;        //每个线程负责500  站点
	static  int DEVICEPORT=3700;     //设备的接受端口
	static int UPDATEINTERVAL=10000;// 10s更新一次
	static int LISTENINGPOROT=2222;  //服务器 监听的端口		
	
	//链接 redis
	public void send(Map<String,Msg> deviceIpMap,Map<String,Station>  stationMapResult){
		Set set=(Set)stationMapResult.keySet();
		String[] deviceIDs=(String[]) set.toArray();
		
		for(int i=0;i<deviceIDs.length/MAXCOUNT;i++)
		{
			
			int from=i*MAXCOUNT;
			int to=0;
			if(i+500>stationMapResult.size())
				to=stationMapResult.size();  //最后几个
			else
				to=i+500;
			// 启动一个线程让他发送
			SenderThread senderThread=new SenderThread(from,to,deviceIDs,stationMapResult,deviceIpMap,DEVICEPORT);
			senderThread.start();
			
		}
	}

	public static void main(String[] args) throws IOException {
		SqlSession session = DB.sessionFactory.openSession();
		final List<LastInfo> lastInfos=new ArrayList<LastInfo>(1000);
		final Map<String,Station>  stationMapResult=new HashMap<String,Station>();//string 存放站点的名称，对应这个站点的线路
		final Map<String,Msg> deviceIpMap=new HashMap<String,Msg>();
		final Map<String,Line>  lineMap=new HashMap<String,Line>();
		
		
		final MainServer sMain=new MainServer();
		// 初始化好所有的数据			
		
		java.util.Timer timer = new java.util.Timer(true);   
		final Init init=new Init(session);
		init.initLines(lineMap);
		
		//开始监听线程2222 端口
		ListeningThread l=new ListeningThread(LISTENINGPOROT);
		l.start();
		
		
		TimerTask task = new TimerTask() {   
		public void run() {   
			System.out.println("the new stask is running  "+(new Date()));
			//更新站点信息
			lastInfos.clear();
			init.updateLastInfo(lastInfos);
			
			// 获取到各个站点需要发送的数据
			init.getDistance(lastInfos,lineMap, stationMapResult);
			
			//发送数据
			sMain.send(deviceIpMap,stationMapResult);
			
			}   
		};   
		timer.schedule(task, 1000, UPDATEINTERVAL);  
		
		
	}
	
}
