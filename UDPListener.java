package com.example.benjamin.blabfridgeapp;

/**
 * Created by Benjamin on 2017-03-22.
 */
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.io.IOException;
import android.R;

import static android.content.Context.NOTIFICATION_SERVICE;

class UDPListener extends Thread{

    public Context context;

    public static final int listenPort = 1078;
    public static final byte packetDelimeter = '?';
    public static final String packetDelimeterMatchRegex = "\\?";

    //private Class c
    private int numMessages = 0;
    private DatagramSocket sock;

    public UDPListener(/*Class c*/Context context){
        //this.c = c;
        this.context=context;
        try{
            sock = new DatagramSocket(listenPort);
        } catch (SocketException e){
            System.out.println("Socket does not exist.");
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
        while(true) {
            try {
                sock.receive(p);
            } catch (IOException e) {
                System.out.println("Failed to recieve a packet.");
                ///MORE BAD THINGS AAAAH
            }

            byte[] buf = p.getData();

            p.setSocketAddress(p.getSocketAddress()); //this might actually be redundant (it certainly looks redundant), prep the packet for responding to the sender
            switch (buf[0]) {
                case '5':  //this is a notification from the fridgecontroller, create a notification and send it
                    //c.notify(getStringFromByteArray(buf, 1));
                    String fullPacketString = new String(buf);
                    String notificationString = fullPacketString.split(FoodItem.matchRegexOpcodeDelimiter)[1];
                    send5Notification(notificationString);
                    byte[] buf2 = new byte[100];
                    buf2[0] = '5';
                    buf2[1] = FoodItem.opcodeDelimiter.getBytes()[0];
                    p.setData(buf2);
                    try {
                        sock.send(p);
                    } catch (IOException e) {
                        System.out.println("Failed to send a response to expiring item notification.");
                    }
                    break;
                case '6'://whatever was scanned does not exist, open a prompt allowing the user to input credentials, to be sent back in a 1 packet

                    send6Notification(p);

                    FoodItem foodItem = NameActivity.foodItem;
                    byte[] foodItemAsByteArray = foodItem.to1Packet(); //= FoodItemCeator.createFoodItem().to1Packet(); //BEN createFoodItem() is whatever method you decide to call to get the user to create a food item
                    p.setData(foodItemAsByteArray);
                    try {
                        sock.send(p);
                    } catch (IOException e) {
                        System.out.println("Failed to send a packet");
                    }

                    break;
                default:
                    break;
            }
        }
    }

    public void send5Notification(String string){
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context);
        nBuilder.setSmallIcon(R.drawable.sym_def_app_icon);
        nBuilder.setContentTitle("BlabFridge");
        nBuilder.setContentText(string);
        Intent resultIntent = new Intent(context, DisplayMessageActivity.class);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, 0);
        nBuilder.setContentIntent(resultPendingIntent);
        NotificationManager nNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        nNotificationManager.notify(001, nBuilder.build());
        System.out.println("5 Notification Recieved.");

    }
    public void send6Notification(DatagramPacket p){
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context);
        nBuilder.setSmallIcon(R.drawable.sym_def_app_icon);
        nBuilder.setContentTitle("BlabFridge");
        nBuilder.setContentText("Scanned Item Does Not Exist");
        Intent resultIntent = new Intent(context, DisplayMessageActivity.class);
        resultIntent.putExtra("Server Port", p.getPort());

        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, 0);
        nBuilder.setContentIntent(resultPendingIntent);
        NotificationManager nNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        nNotificationManager.notify(002, nBuilder.build());
        System.out.println("6 Notification Recieved.");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        switch(requestCode){
            case(MY_CHILD_ACTIVITY):{
                if(resultCode == Activity.RESULT_OK){
                    String result = ;
                }
                break;
            }
        }
    }
}
