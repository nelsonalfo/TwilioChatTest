package com.example.miguelsoler.twiliochattest.Conexion;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.miguelsoler.twiliochattest.TwilioChatApplication;

public class TokenRequest {
  private static TokenRequest mInstance;
  private RequestQueue mRequestQueue;

  private TokenRequest() {
    mRequestQueue = Volley.newRequestQueue(TwilioChatApplication.get().getApplicationContext());
  }

  public static synchronized TokenRequest getInstance() {
    if (mInstance == null) {
      mInstance = new TokenRequest();
    }
    return mInstance;
  }

  public <T> void addToRequestQueue(Request<T> req) {
    mRequestQueue.add(req);
  }
}
