package com.example.benjamin.blabfridgeapp;


import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Benjamin on 2017-03-31.
 */

public class UDPListenTask extends AsyncTask<Context,Integer,Integer> {

    @Override
    public Integer doInBackground(Context... c){
        for(int i=0; i<c.length; i++) {
            new Thread(new UDPListener(c[i])).start();
        }
        return 0;
    }
}
