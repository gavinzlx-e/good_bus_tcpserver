package model;

import java.util.Arrays;

public class Msg {
	byte header;
	byte []deviceid=new byte[11];
	byte  msg_type;
	String ip;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
	public byte getHeader() {
		return header;
	}
	public void setHeader(byte header) {
		this.header = header;
	}
	public byte[] getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(byte[] deviceid) {
		this.deviceid = deviceid;
	}
	public byte getMsg_type() {
		return msg_type;
	}
	public void setMsg_type(byte msg_type) {
		this.msg_type = msg_type;
	}
	
	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<11;i++){
			sb.append(" "+deviceid[i]);
			
		}
		return String.valueOf((byte)header)+ " : "+sb.toString()+ " : "+String.valueOf((byte)msg_type); 
		
		//return String.valueOf((byte)header)+ " : "+String.valueOf(deviceid)+ " : "+String.valueOf((byte)msg_type);
		
	}
	public  byte[] toByte() {
		// TODO Auto-generated method stub
		byte [] allbyte=new byte[13];
		allbyte[0]=header;
		allbyte[12]=msg_type;
		System.arraycopy(deviceid, 0, allbyte, 1, 11);
		//		for(int i=1;i<11;i++)
		//			allbyte[i]=deviceid[i-0];
		
		return allbyte;
	}
	
	
}
