

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

import model.Msg;

public class Mydevice {
	static Socket socket;

	public static void main(String[] args) throws Exception {
		//socket地址
		socket = new Socket(InetAddress.getLocalHost(), 5678);
		//获取socket的输入流， 就是接受消息
		BufferedReader in = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		Msg msg=new Msg();
		msg.setHeader( (byte)0x1e);
		msg.setMsg_type( (byte)0x01);
		msg.setDeviceid(new byte[]{0x01,0x02,0x03,0x04,0x5,   0x01,0x02,0x03,0x04,0x5,  0x01});
		
		//获取socket的输出流， 发现消息
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		System.out.println("begin to go ");
		byte [] bytes=msg.toByte();
		//out.println(bytes);
		out.write(bytes);
		out.flush();
		BufferedReader wt = new BufferedReader(new InputStreamReader(System.in));
		
		while (true) {
			String str = wt.readLine();
			//out.println(str);
			out.flush();
			if (str.equals("end")) {
				break;
			}
			System.out.println(in.readLine());
		}
		socket.close();
	}
}