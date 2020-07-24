package com.example.dummy;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingletonPattern {
    private static VolleySingletonPattern instance;
    private static RequestQueue requestQueue;
    private static Context context;

    private VolleySingletonPattern(Context cntx) {
        context = cntx;
        requestQueue = getRequestQueue();
    }

    public static synchronized VolleySingletonPattern getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySingletonPattern(context);
            requestQueue = getRequestQueue();
        }
        return instance;
    }

    private static RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }
}
