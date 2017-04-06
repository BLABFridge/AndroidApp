package com.example.benjamin.blabfridgeapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import static java.lang.Float.parseFloat;

/**
 * Created by Benjamin on 2017-03-31.
 */

public class NameActivity extends AppCompatActivity {
    private EditText itemName, itemExpiry;
    public UDPSender sender;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

    }
    public void sendFoodItem(View view) {
        new Thread() {
            public void run() {
                DatagramSocket sock = null;
                try {
                    sock = new DatagramSocket();
                }catch(SocketException e){
                    System.out.println("Error Creating send socket for 6 packet.");
                }
                try {
                    itemName = (EditText) findViewById(R.id.editText3);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid String: Item Name");
                }
                try {
                    itemExpiry = (EditText) findViewById(R.id.editText4);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid int: Expiry Date");
                }
                FoodItem foodItem = new FoodItem(MainActivity.BLANK_TAG_CODE_CHAR_ARRAY, itemName.getText().toString(), parseFloat(itemExpiry.getText().toString()));
                byte[] buf = foodItem.to1Packet();
//                byte[] buf = new byte[100];
//                buf[0] = '1';
//                buf[1] = FoodItem.opcodeDelimiter.getBytes()[0];
//                byte[] nameAsBytes = itemName.toString().getBytes();
//                System.arraycopy(nameAsBytes, 0, buf, 2, nameAsBytes.length);
//                buf[2 + nameAsBytes.length] = FoodItem.opcodeDelimiter.getBytes()[0];
//                //byte[] lifetimeAsBytes = Integer.toString(Math.round(Integer.parseInt(itemExpiry.toString()))).getBytes();
                //System.arraycopy(lifetimeAsBytes, 0, buf, 2 + 1 + nameAsBytes.length, lifetimeAsBytes.length);
                //buf[2 + 1 + lifetimeAsBytes.length + nameAsBytes.length] = FoodItem.opcodeDelimiter.getBytes()[0];

                Intent intent = getIntent();
                int port = intent.getIntExtra("Server Port", 80);
                System.out.println(port);
                DatagramPacket p = new DatagramPacket(buf, 100, MainActivity.fridgeControllerInetAddress, port);
                try {
                    sock.send(p);
                }catch(IOException e){
                    System.out.println("Error sending response to 6 packet.");
                }
            }
        }.start();
        finish();
    }
}
