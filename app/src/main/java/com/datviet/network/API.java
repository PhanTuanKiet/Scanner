package com.datviet.network;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.datviet.scanner.CoreApplication;
import com.datviet.utils.AppLog;
import com.datviet.utils.Constant;
import com.datviet.utils.KEY;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class API {

    private static final String TAG = API.class.getSimpleName();

    public static void getBookInfo(final String bookCode, final RequestListener listener) {

        String bookUrl = Domain.HOST + "/api/library/v1/getInfoBook";
        AppLog.d(TAG, "#getBookInfo-->  " + bookUrl);


        StringRequest request = new StringRequest(Request.Method.POST,bookUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AppLog.d(TAG, "#getBookInfo#onResponse-->  " + response);
                if (listener != null) listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppLog.d(TAG, "#getBookInfo#Error--> " + error.getMessage());
                if (listener != null) listener.onError();
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY.APP_ID, Constant.ID);
                params.put(KEY.SIGNATURE, Constant.SIGNATURE_KEY);

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(KEY.BOOK_CODE, bookCode);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                params.put(KEY.DATA, jsonObject.toString());
                return new JSONObject(params).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        if (listener != null) listener.onStartRequest();
        CoreApplication.getsInstance().addToRequestQueue(request);

    }

    public static void getMemberInfo(final String memberCode, final RequestListener listener) {

        String studentUrl = Domain.HOST + "/api/library/v1/getInfoMember";
        AppLog.d(TAG, "#getStudentInfo-->  " + studentUrl);

        StringRequest request = new StringRequest(Request.Method.POST,studentUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AppLog.d(TAG, "#getStudentInfo#onResponse-->  " + response);
                if (listener != null) listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppLog.d(TAG, "#getStudentInfo#Error--> " + error.getMessage());
                if (listener != null) listener.onError();
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY.APP_ID, Constant.ID);
                params.put(KEY.SIGNATURE, Constant.SIGNATURE_KEY);

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(KEY.MEMBER_CODE, memberCode);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                params.put(KEY.DATA, jsonObject.toString());
                return new JSONObject(params).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        if (listener != null) listener.onStartRequest();
        CoreApplication.getsInstance().addToRequestQueue(request);

    }

}
