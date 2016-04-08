package com.example.caavm.clase1;

import android.content.*;
import android.support.v7.app.*;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button btn_send;
    private EditText edt_name, edt_phone, edt_email;
    private double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_phone = (EditText) findViewById(R.id.edt_phone);
        edt_email = (EditText) findViewById(R.id.edt_email);
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "HOLA", Toast.LENGTH_LONG).show();
                createNewUser();
            }
        });
    }
    private void gps(){
        GPSTracker gps = new GPSTracker(this);

        // check if GPS enabled
        if(gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            Log.d("GPS", "envio");
            Log.d("GPS", "lat" + latitude);
            Log.d("GPS", "long" + longitude);

        }
    }
    private void createNewUser() {
        Clase1.getInstance().add(new StringRequest(Request.Method.POST, "http://www.inkadroid.com/usil2016/1/crear.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Something went wrong!");
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //IMEI
                TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                String IMEI= mngr.getDeviceId();
                gps();
                Map<String, String> params = new HashMap<>();
                params.put("name", edt_name.getText().toString());
                params.put("email", edt_email.getText().toString());
                params.put("phone", edt_phone.getText().toString());
                params.put("IMEI", IMEI);
                params.put("lat", String.valueOf(latitude));
                params.put("long", String.valueOf(longitude));
                Log.d("main", "envio");
                return params;
            }
        });
    }
}

