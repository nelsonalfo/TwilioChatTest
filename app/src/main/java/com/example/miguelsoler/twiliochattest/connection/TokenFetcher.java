package com.example.miguelsoler.twiliochattest.connection;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.miguelsoler.twiliochattest.listeners.TaskCompletionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/****************************************************************************************
 * CREDITOS DE ARCHIVO:_________________________|__________________FECHA__________________
 * | # | DESARROLLADORES
 * |---|----------------------------------------|-----------------------------------------
 * | 1 |    Miguel Soler                        |                 02/03/2018.
 * |_____________________________________________________________________________________
 ***************************************************************************************/

public class TokenFetcher {
    private final Context context;
    private String token, identity, channel;

    public TokenFetcher(Context context) {
        this.context = context;
    }

    public void fetch(final TaskCompletionListener<String, String> listener) {
        String requestUrl = "http://staging.flyersconcierge.com/api/v1.47/twilio_token/16/diegoduncan21@gmail.com";

        StringRequest jsonObjReq = new StringRequest(Request.Method.GET, requestUrl, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e("TokenFetcher", "StringResponse " + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            token = jsonObject.getString("token");
                            identity = jsonObject.getString("identity");
                            channel = jsonObject.getString("channel");

                            SessionManager.getInstance(context).createSession(channel, identity, token);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("TokenFetcher", "JSONException " + String.valueOf(e));
                            listener.onError("Failed to parse token JSON response");
                        }
                        Log.e("TokenFetcher", "Success");
                        listener.onSuccess(token);
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        switch (response.statusCode) {
                            case 404:
                                Log.e("TokenFetcher", " onErrorResponse 404 " + Arrays.toString(response.data));
                                listener.onError("Failed to fetch token");
                                break;
                            case 500:
                                Log.e("TokenFetcher", " onErrorResponse 500 " + Arrays.toString(response.data));
                                listener.onError("Failed to fetch token");
                                break;
                        }
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("Authorization", "apikey ce3b207d2fde495bb8dcc95753cb49f25acde464");
                        return headers;
                    }
                };

        jsonObjReq.setShouldCache(false);
        TokenRequest.getInstance().addToRequestQueue(jsonObjReq);
    }
}