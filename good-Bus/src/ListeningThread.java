import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import model.Msg;

public class ListeningThread extends Thread {

	public Map<String, Msg> deviceIpMap = new HashMap<String, Msg>();
	// 监听的端口为参数，通过接受终端发送的消息，构造deviceIpMap
	int serverPort;

	public ListeningThread(int port) {
		serverPort = port;
	}

	public void run() {
		System.out.println("the ListeningThread is listening");
		rece(serverPort);
	}

	public void rece(int servPort) {
		// 1. 构建DatagramSocket实例，指定本地端口。

		try {
			DatagramSocket socket;

			socket = new DatagramSocket(servPort);
			// 2. 构建需要收发的DatagramPacket报文
			int ECHOMAX = 1000;
			DatagramPacket packet = new DatagramPacket(new byte[ECHOMAX],
					ECHOMAX);

			try {
				while (true) {
					// 3. 收报, 每接收到一个报文,创建msg，

					socket.receive(packet);

					byte cbuf[] = packet.getData();
					//应答包
					if(cbuf[12]==0x01){
						Msg msg;
						String deviceStr = util.Util.getStrDeviceID(Arrays
								.copyOfRange(cbuf, 1, 12));
						
						
					}
					//如果是心跳包才继续
					if (cbuf[12] == 0x00) {
						// 以deviceid的 byte 数组作为 key，msg作为value
						Msg msg;
						String deviceStr = util.Util.getStrDeviceID(Arrays
								.copyOfRange(cbuf, 1, 12));
						if (deviceIpMap.containsKey(deviceStr))
							msg = (Msg) deviceIpMap.get(deviceStr);
						else
							msg = new Msg();
						msg.setMsg_type((byte) cbuf[12]);
						msg.setHeader((byte) cbuf[0]);
						// 把数组cbuf的 1到11个byte 复制到 deviceid中
						msg.setDeviceid(Arrays.copyOfRange(cbuf, 1, 12));
						// 把 设备对应的ip放进来
						msg.setIp(packet.getAddress().toString());

						deviceIpMap.put(deviceStr, msg);

						System.out.println("Handling device id:  " + deviceStr
								+ "   ip :"
								+ packet.getAddress().getHostAddress());

					}

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}