import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketException;

class UDPListener extends Thread{

	public static final int listenPort = 1078;
	public static final byte packetDelimeter = '?';

	//private Class c
	private DatagramSocket sock;

	public UDPListener(/*Class c*/){
		//this.c = c;
		try{
			sock = new DatagramSocket(listenPort);
		} catch (SocketException e){
			//AAHAHHAHAHH BAD THINGS
		}
	}

	static String getStringFromByteArray(byte[] arr, int indexOfString){
		String s = new String(arr);
		String[] strings = s.split();
		return strings[i];
	}

	public void run(){
		DatagramPacket p = new DatagramPacket(new byte[100], 100);
		try{
			sock.receive(p);
		} catch (IOException e){
			///MORE BAD THINGS AAAAH
		}

		byte[] buf = p.getBytes();
		switch(buf[0]){
			case '5': 
			//c.notify(getStringFromByteArray(buf, 1));
			break;

		}

	}


}