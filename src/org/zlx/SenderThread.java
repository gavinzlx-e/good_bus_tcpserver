package org.zlx;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import java.util.Map;

import model.Msg;
import model.Station;


public class SenderThread extends Thread{
	int from;
	int  to;
	
	int destPort;
	String deviceIDs[];
	Map<String, Station> stationMapResult;
	Map<String,Msg>  deviceIpMap;
	
	// 需要给这些设备deviceIDs 编号(from,to)，发送数据，数据在stationMapResult中   
	public SenderThread(int from,int to,String [] deviceIDs, Map<String, Station> stationMapResult,
			Map<String,Msg> deviceIpMap,int destPort) {
		// TODO Auto-generated constructor stub
		this.from=from;
		this.to=to;
		this.destPort=destPort;
		this.deviceIDs=deviceIDs;
		
		this.stationMapResult=stationMapResult;
		this.deviceIpMap=deviceIpMap;
	}
	public void run(){
		//创建套接字
		try  
        {  
        	
        DatagramSocket ds = new DatagramSocket();  
        
		for(int i=from;i<to;i++){
			
			String deviceID=deviceIDs[i];
			 //获取发送的ip
            Msg msg=deviceIpMap.get(deviceID);
            if(msg==null)
            	continue;
            
            String destIP=msg.getIp();
            
            
			
			//创建发送内容， 需要更改
			StringBuffer sb=new StringBuffer();
			Station station=stationMapResult.get(deviceID);
			List distances=station.getStationDistances();
			List lines=station.getLines();
			for(int j=0;j<lines.size();j++) 
				sb.append( "("+lines.get(j)+" "+distances.get(j)+"" );
            String str =sb.toString();  
            
            //创建发送包
            DatagramPacket dp = new DatagramPacket(str.getBytes(),0,str.length(),  
                                                InetAddress.getByName(destIP),destPort);//发送给本机的地址，端口为8600  
            ds.send(dp);  
   
            //演示接受返回回来的数据。  
            System.out.println("send str"+str+(new Date()));  
		}
		 ds.close();  
        }  
        catch(Exception e)  
        {  
            e.printStackTrace();  
        }  
		
	 }
	 
	
	   
}
