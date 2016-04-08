package com.example.caavm.clase1;

import android.app.Application;
import android.util.Log;

import com.android.volley.*;
import com.android.volley.toolbox.Volley;

public class Clase1 extends Application {
    private static final int TIME_OUT =10000;
    private static final int NUM_RETRY =3;
    private static final String TAG = Clase1.class.getName();
    private RequestQueue requestQueue;
    private static Clase1 instance;
    public static synchronized Clase1 getInstance(){
        return instance;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        instance=this;
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        Log.d("volley", "inicio");
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public <T> void add(Request<T> request){
        request.setTag(TAG);
        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT, NUM_RETRY, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(request);
        Log.d("volley", "envio");
    }

    public void cancel(){
        requestQueue.cancelAll(TAG);
    }
}
