package util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class Util {
	// char转byte

	public static byte[] getBytes (char[] chars) {
	   Charset cs = Charset.forName ("UTF-8");
	   CharBuffer cb = CharBuffer.allocate (chars.length);
	   cb.put (chars);
	                 cb.flip ();
	   ByteBuffer bb = cs.encode (cb);
	  
	   return bb.array();

	 }
	
	//通过传入的byte组数 构造，其字符串标示
		public static String getStrDeviceID( byte [] dev){
			StringBuffer sb=new StringBuffer();
			for(int i=0;i<dev.length;i++){
				sb.append(" "+dev[i]);
				
			}
			return sb.toString(); 
			
		}

	// byte转char

	public static char[] getChars (byte[] bytes) {
	      Charset cs = Charset.forName ("UTF-8");
	      ByteBuffer bb = ByteBuffer.allocate (bytes.length);
	      bb.put (bytes);
	                 bb.flip ();
	       CharBuffer cb = cs.decode (bb);
	  
	   return cb.array();
	}

	 
}
