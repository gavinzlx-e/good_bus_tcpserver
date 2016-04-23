import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.junit.Test;


public class test {
	@Test
	public void testTime(){
		Date date =new Date(System.currentTimeMillis()-1000*60*15 );
		System.out.println(date);
	}
	@Test
	public  void main2() throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
        String str2="3";
        
        System.out.println("+++"+ str2.getBytes("unicode"));
		
		
		byte b[]={0x1E,0x00,0x55,0x23,0x52,0x32,0x2C };
		String str = null;
		str = new String(b, "unicode");


		System.out.println("难男男女女   "+str);

		str = new String(b, "ascii");
        System.out.println("男女女   "+str);
	}

}
