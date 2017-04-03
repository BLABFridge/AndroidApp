package com.example.benjamin.blabfridgeapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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



    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    public void sendFoodItem(){
        try {
            itemName = (EditText) findViewById(R.id.editText4);
        }catch(NumberFormatException e){
            System.out.println("Invalid String: Item Name");
        }
        try{
            itemExpiry = (EditText) findViewById(R.id.editText3);
        }catch(NumberFormatException e){
            System.out.println("Invalid int: Expiry Date");
        }
        FoodItem foodItem = new FoodItem(MainActivity.BLANK_TAG_CODE_CHAR_ARRAY, itemName.toString(), parseFloat(itemExpiry.toString()));
        byte[] buf = new byte[100];
        buf[0] = '1'; 
        buf[1] = FoodItem.opcodeDelimiter.getBytes()[0];
        byte[] nameAsBytes = itemName.toString().getBytes();
        System.arraycopy(nameAsBytes, 0, buf, 2, nameAsBytes.length);
        buf[2 + nameAsBytes.length] = FoodItem.opcodeDelimiter.getBytes()[0];
        byte[] lifetimeAsBytes = Integer.toString(Math.round(Integer.parseInt(itemExpiry.toString()))).getBytes();
        System.arraycopy(lifetimeAsBytes, 0, buf, 2 + 1 + nameAsBytes.length, lifetimeAsBytes.length);
        buf[2+1+lifetimeAsBytes.length + nameAsBytes.length] = FoodItem.opcodeDelimiter.getBytes()[0];
        DatagramPacket p = new DatagramPacket(buf,100);
        p.setPort(p.getPort());
    }
}
