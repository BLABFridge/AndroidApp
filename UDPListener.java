import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.io.IOException;

class UDPListener extends Thread{

	public static final int listenPort = 1078;
	public static final byte packetDelimeter = '?';
	public static final String packetDelimeterMatchRegex = "\\?";

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
		String[] strings = s.split(UDPListener.packetDelimeterMatchRegex);
		return strings[indexOfString];
	}

	public void run(){
		DatagramPacket p = new DatagramPacket(new byte[100], 100);
		try{
			sock.receive(p);
		} catch (IOException e){
			///MORE BAD THINGS AAAAH
		}

		byte[] buf = p.getData();

		p.setSocketAddress(p.getSocketAddress()); //this might actually be redundant (it certainly looks redundant), prep the packet for responding to the sender
		switch(buf[0]){
			case '5':  //this is a notification from the fridgecontroller, create a notification and send it
				//c.notify(getStringFromByteArray(buf, 1));
				break;
			case '6'://whatever was scanned does not exist, open a prompt allowing the user to input credentials, to be sent back in a 7 packet
				String tagCode = getStringFromByteArray(buf, 1); //the first argument in a 6 packet is the tagcode that is missing - the user doesn't really need this but might as well
				byte[] foodItemAsByteArray = null; //= FoodItemCeator.createFoodItem().to1Packet(); //BEN createFoodItem() is whatever method you decide to call to get the user to create a food item
				p.setData(foodItemAsByteArray);
				try{
					sock.send(p);
				} catch (IOException e){
					System.out.println("Failed to send a packet");
				}
				break;
			default: break;

		}

	}


}