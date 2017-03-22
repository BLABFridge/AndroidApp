import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    UDPListener listener;
    UDPSender sender;

    public String foodList;
    public static final char[] blankTagCodeCharArray = {'0','0','0','0','0','0','0','0','0','0'};
    public static final int fridgeControllerPort = 1111;
    private DatagramSocket sock;
    private static final String fridgeControllerInetAddressString = "10.0.2.2";
    private InetAddress fridgeControllerInetAddress;
    //This app will eventually receive an update from the fridge, and interpret that signal to
    // an update message. This will be displayed on the screen of the phone.
    //
    //
    //Future Ideas:
    //  How we connect the phone to the fridge?
    //      Access ID per fridge?
    //  Make a method for analyzing the fridge's contents against Recipes to see what can be made
    //  from the contents of the fridge.
    //  Send a message to the fridge to let it know that you went on a shopping trip
    //  Send a message to the fridge saying you are on vacation, what things will expire while out.
    //  Notification method
    public MainActivity() {
        listener = new UDPListener();
        sender = new UDPSender();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            sock = new DatagramSocket();
        }catch(SocketException e){
            System.out.println("Socket Error");
        }

        try{
            fridgeControllerInetAddress = InetAddress.getByName(fridgeControllerInetAddressString);
        }catch(UnknownHostException e){
            //do something
        }
    }

    /** Called when the user clicks the send button */

    public void listFridge(View view){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        ArrayList<FoodItem> arrayList = getItemsExpiringBefore(0);
        sortList(arrayList);
        foodList = listFood(arrayList);
        intent.putExtra("listFood",foodList);
        startActivity(intent);
    }
    public void listExpiring(int expiry){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        ArrayList<FoodItem> arrayList = getItemsExpiringBefore(expiry);
        sortList(arrayList);
        foodList = listFood(arrayList);
        intent.putExtra("listFood",foodList);
        startActivity(intent);
    }
    public ArrayList<FoodItem> getItemsExpiringBefore(int days){ //this should be called by the actionEvent created by a button press
        byte[] buf = new byte[100];
        buf[0] = '9';
        buf[1] = FoodItem.opcodeDelimiter.getBytes()[0];
        byte[] daysAsBytes = Integer.toString(days).getBytes();
        System.arraycopy(daysAsBytes, 0, buf, 2, daysAsBytes.length);
        DatagramPacket p = new DatagramPacket(buf, buf.length, fridgeControllerInetAddress, fridgeControllerPort);
        try{
            sock.send(p);
        } catch(IOException e){
            System.out.println("Error sending on socket");
        }

        ArrayList<FoodItem> items = new ArrayList<>();

        while(true){
            try{
                sock.receive(p);
            } catch(IOException e){
                System.out.println("Error receiving on socket");
            }
            buf = p.getData();
            if (buf[0] == '9') break; //we got the last packet, exit.
            items.add(FoodItem.getFoodItemFromByteArray(blankTagCodeCharArray, buf));
        }
        return items;
    }
    public String listFood(ArrayList<FoodItem> array){
        String list = "";
        for(int i=0;i<array.size(); i++){
            if(i!=0){
                list += "/n";
            }
            list += array.get(i).toString();
        }
        return list;
    }
    public void sortList(ArrayList<FoodItem> array){
        int k = 0;
        while(k==0){
            int size = array.size();
            if(size == 1){
                k = 1;
            }
            for(int i=0; i<size-1; i++){
                FoodItem a = array.get(i);
                FoodItem b = array.get(i+1);
                if(a.expiresInDays()>b.expiresInDays()){
                    array.set(i,b);
                    array.set(i+1,a);
                }
            }
            int dummyVar = 0;
            for(int j=0; j<size-1; j++){
                if(array.get(j).expiresInDays()<=array.get(j+1).expiresInDays()){
                    dummyVar++;
                }
            }
            if(dummyVar == size-1){
                k = 1;
            }
        }
    }
}
