import java.io.*;
import java.util.ArrayList;
import java.net.*;
import java.lang.*;

public class UDPSender{
    DatagramSocket sendSocket;
    DatagramPacket sendPacket;
    File localFoodItems;
    private String filename = "LocalFoodItems";
    private InetAddress controllerInetAddress;
    public static final int controllerPort = 1111;
    public static final String controllerInetAddressString = "127.0.0.1"; //Change when we know out actual.
    static final char delimeter = '?';
    byte[] sendMessage;
    String sendMessageString;
    
    /*Initialize the sendSocket and the controllerInetAddress, also creates a new file for local data. */
    public UDPSender(){
        try{
            sendSocket = new DatagramSocket(null);
        }catch(SocketException e){
            //Should we terminate??
            System.out.print("Socket creation failed");
        }
        
        try{
            controllerInetAddress = InetAddress.getByName(controllerInetAddressString);
        }catch(UnknownHostException e){
            System.out.print("Unknown host");
        }
        
        //localFoodItems = new File(context.getFilesDir(),filename);
        
        sendMessage = new byte[100];
    }
    
    /*This method will send the days until expiry to the controller to receive a stream of items expiring
     */
    private void sendReceivePacket(int expiry){
        sendMessageString =  "9";
        sendMessageString += delimeter;
        sendMessageString += expiry;
        sendMessageString += delimeter;
        sendMessage = sendMessageString.getBytes();
        
        
        DatagramPacket sendReceivePacket = new DatagramPacket(sendMessage, sendMessage.length, controllerInetAddress, controllerPort);
        
        try{
            sendSocket.send(sendPacket);
        } catch(IOException e){
            System.out.print("Error when trying to send packet");
        }
        
        //At this point we are goning to wait for return packets from the controller...
        
        //Make this a separate method and add multi threading.
        //While(Pakcet != final packet){receive packet and write the contents of that packet into a file}
        //can reuse the same packet for each new received packet.
        try{
            sendSocket.receive(sendReceivePacket);
        }catch(IOException e){
            //do something
        }
        
        byte[] data = sendReceivePacket.getData();
        
        try {
            outputStream = openFileOutput(localFoodItems, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    /*This method will send the inputted name and expiry back to the controller, this message is sent in response to a 6 packet and onClick of the
     send button.*/
    public void sendPacket(String name, int expiry){
        sendMessageString =  "1";
        sendMessageString += delimeter;
        sendMessageString += name;
        sendMessageString += delimeter;
        sendMessageString += expiry;
        sendMessageString += delimeter;
        
        sendMessage = sendMessageString.getBytes();
        
        
        DatagramPacket sendPacket = new DatagramPacket(sendMessage, sendMessage.length, controllerInetAddress, controllerPort);
        
        
        System.out.print(sendMessageString);
        
        
    }
    
    
}
