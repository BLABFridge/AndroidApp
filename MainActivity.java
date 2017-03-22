package com.example.benjamin.blabfridgeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    UDPListener listener;
    UDPSender sender;
    String foodList = "";
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
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user clicks the send button */

    public void listFridge(View view){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        sender.sendReceivePacket(0);
        // Read from file, make into a list to display.
        startActivity(intent);
    }
    public void listExpiring(int expiry){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        sender.sendReceivePacket(expiry);
        //Read from file, make into a list to display.
        FoodItem[] array = new FoodItem[0];
        //Read from file
        sortList(array);
        listFood(array);
        startActivity(intent);
    }
    public void listFood(FoodItem[] array){
        for(int i=0;i<array.length; i++){
            if(i!=array.length){
                foodList += "/n";
            }
            foodList += array[i].toString();
        }
    }
    public void sortList(FoodItem[] array){
        int k = 0;
        while(k==0){
            int size = array.length;
            if(size == 1){
                k = 1;
            }
            for(int i=0; i<size-1; i++){
                FoodItem a = array[i];
                FoodItem b = array[i+1];
                if(a.expiresInDays()>b.expiresInDays()){
                    FoodItem temp = a;
                    array[i] = b;
                    array[i+1] = temp;
                }
            }
            int dummyVar = 0;
            for(int j=0; j<size-1; j++){
                if(array[j].expiresInDays()<=array[j+1].expiresInDays()){
                    dummyVar++;
                }
            }
            if(dummyVar == size-1){
                k = 1;
            }
        }
    }
}
