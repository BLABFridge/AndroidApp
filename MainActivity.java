package com.example.benjamin.blabfridgeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public UDPSender sender;
    public String foodList;

    private EditText expiryButton, searchName;
    private DatagramSocket sock;
    public static InetAddress fridgeControllerInetAddress;


    public static final byte PACKET_REQUEST_OPCODE = '9';
    public static final char[] BLANK_TAG_CODE_CHAR_ARRAY = {'0','0','0','0','0','0','0','0','0','0'};
    public static final int FRIDGE_CONTROLLER_PORT = 1111;
    private static final String FRIDGE_CONTROLLER_INET_ADDRESS_STRING = "10.0.0.101";
    // Liam's Listener: 172.17.36.76
    // Brydon's Server: 172.17.197.117, 1111
    // Black Box Server 10.0.0.102
    //This app will eventually receive an update from the fridge, and interpret that signal to
    // an update message. This will be displayed on the screen of the phone.
    //Future Ideas:
    //  Make a method for analyzing the fridge's contents against Recipes to see what can be made
    //  from the contents of the fridge.
    //  Colour the string.
    protected MainActivity() {
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
            fridgeControllerInetAddress = InetAddress.getByName(FRIDGE_CONTROLLER_INET_ADDRESS_STRING);
        }catch(UnknownHostException e){
            //do something
            System.out.println("Host is Unknown");
        }
        new UDPListenTask().execute(this);

    }

    /** Called when the user clicks the send button */

    public void listFridge(View view){
        new Thread(){
            public void run(){
                Intent intent = new Intent(MainActivity.this, DisplayMessageActivity.class);
                ArrayList<FoodItem> arrayList = getItemsExpiringBefore(0);
                ArrayList<FoodItem> arrayList2 = sortList(arrayList);
                foodList = listFood(arrayList2);
                intent.putExtra("listFood", foodList);
                startActivity(intent);
            }
        }.start();
    }
    public void listExpiring(View view){
        new Thread() {
            public void run() {
                Intent intent = new Intent(MainActivity.this, DisplayMessageActivity.class);
                try{
                    expiryButton = (EditText) findViewById(R.id.editText);
                }catch(NumberFormatException e){
                    System.out.println("Invalid int: Days");
                }
                int expiry = Integer.parseInt(expiryButton.getText().toString());
                ArrayList<FoodItem> arrayList = getItemsExpiringBefore(expiry);
                ArrayList<FoodItem> arrayList2 = sortList(arrayList);
                foodList = listFood(arrayList2);
                intent.putExtra("listFood", foodList);
                startActivity(intent);
            }
        }.start();
    }
    public void searchFridge(View view){
        new Thread() {
            public void run() {
                Intent intent = new Intent(MainActivity.this, DisplayMessageActivity.class);
                searchName = (EditText) findViewById(R.id.editText2);
                String searchItemName = searchName.getText().toString();
                ArrayList<FoodItem> arrayList = getItemsExpiringBefore(0);
                ArrayList<FoodItem> arrayList2 = searchList(arrayList, searchItemName);
                foodList = listFood(arrayList2);
                intent.putExtra("listFood", foodList);
                startActivity(intent);
            }
        }.start();
    }
    public ArrayList<FoodItem> getItemsExpiringBefore(int days){ //this should be called by the actionEvent created by a button press, this must be called
        //in a thread
        byte[] buf = new byte[100];
        buf[0] = PACKET_REQUEST_OPCODE;
        buf[1] = FoodItem.opcodeDelimiter.getBytes()[0];
        byte[] daysAsBytes = Integer.toString(days).getBytes();
        System.arraycopy(daysAsBytes, 0, buf, 2, daysAsBytes.length);
        buf[daysAsBytes.length + 2] = FoodItem.opcodeDelimiter.getBytes()[0];
        DatagramPacket p = new DatagramPacket(buf, buf.length, fridgeControllerInetAddress, FRIDGE_CONTROLLER_PORT);
        try {
            sock.send(p);
        } catch (IOException e) {
            System.out.println("Error sending on socket");
        }
        ArrayList<FoodItem> items = new ArrayList<>();
        while (true) {
            try {
                sock.receive(p);
            } catch (IOException e) {
                System.out.println("Error receiving on socket");
            }
            buf = p.getData();
            if (buf[0] == '9') break; //we got the last packet, exit.
            items.add(FoodItem.getFoodItemFromByteArray(BLANK_TAG_CODE_CHAR_ARRAY, buf));
        }
        return items;
    }
    public String listFood(ArrayList<FoodItem> array){
        String list = "";
        for(int i=0;i<array.size(); i++){
            if(i!=0){
                list += '\n';
            }
            if(array.get(i).getExpiryToLifetimeRatio() < 0.25){
// Colouring String code area
            }
            list += array.get(i).toHumanReadableString();
        }
        return list;
    }
    public ArrayList<FoodItem> sortList(ArrayList<FoodItem> array){
        while(true){
            int size = array.size();
            if(size == 1){
                break;
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
                break;
            }
        }
        return array;
    }
    public ArrayList<FoodItem> searchList(ArrayList<FoodItem> array, String searchName){
        ArrayList<FoodItem> array2 = new ArrayList<>();
        String searchNameLower = searchName;
        System.out.println(searchNameLower);
        for(int i=0; i<array.size(); i++){
            if(array.get(i).getName().equalsIgnoreCase(searchNameLower)){
                array2.add(array.get(i));
            }
        }
        for(int i=0; i<array2.size(); i++){
            System.out.println(array2.get(i).getName().toLowerCase());
        }
        return array2;
    }
}
