package com.example.benjamin.blabfridgeapp;

/**
 * Created by Benjamin on 2017-03-22.
 */
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.io.IOException;
import android.R;

import static android.content.Context.NOTIFICATION_SERVICE;
import static java.lang.System.exit;

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
            System.out.println("Couldn't bind to the Listener Port.");
            exit(0);
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
            }

            byte[] buf = p.getData();

            p.setSocketAddress(p.getSocketAddress()); //this might actually be redundant (it certainly looks redundant), prep the packet for responding to the sender
            switch (buf[0]) {
                case '5':  //this is a notification from the fridgecontroller, create a notification and send it
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
        nBuilder.setAutoCancel(true);
        nBuilder.setVibrate(new long[] {1000,1000});
        nBuilder.setLights(Color.GREEN,3000,3000);

        Intent resultIntent = new Intent(context, MainActivity.class);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
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
        nBuilder.setAutoCancel(true);
        nBuilder.setVibrate(new long[] {1000,1000});
        nBuilder.setLights(Color.RED,3000,3000);

        Intent resultIntent = new Intent(context, NameActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        resultIntent.putExtra("Server Port", p.getPort());

        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        nBuilder.setContentIntent(resultPendingIntent);
        NotificationManager nNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        nNotificationManager.notify(002, nBuilder.build());
        System.out.println("6 Notification Recieved.");
    }
}
