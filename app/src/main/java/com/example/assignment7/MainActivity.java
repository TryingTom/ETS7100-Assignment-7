package com.example.assignment7;

import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RequestQueue queue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // tell that there is a layout inside layout
        LinearLayout ToolBox = findViewById(R.id.ToolBox);

        // then let's make the editText
        final EditText txt = new EditText(this);
        txt.setBackgroundColor(Color.rgb(50,150,50));
        int txtID = 500;
        txt.setId(txtID);
        txt.setWidth(400);
        txt.setHeight(70);

        // Let's make the button dynamically
        Button btn = new Button(this);
        int ButtonID = 1;
        btn.setId(ButtonID);
        btn.setText("The button");
        btn.setBackgroundColor(Color.rgb(255,50,50));


        // add the button and make it do stuff
        ToolBox.addView(btn);
        btn = (Button) findViewById(ButtonID);

        // add the editText to the layout
        ToolBox.addView(txt);

        // make the queue
        queue = Volley.newRequestQueue(this);

        // let's set up an array adapter
        final ArrayList<urlOwnerLicense> kuvalista = new ArrayList<>();
        final kuvAdapteri kuvaAdapteri = new kuvAdapteri(this, R.layout.adapteri_layout, kuvalista);

        // testiyritys, ei toimi
        urlOwnerLicense uusi = new urlOwnerLicense("https://i1.wp.com/vivas.fi/wp-content/uploads/2015/12/enhanced-buzz-31490-1340745205-2.jpg?resize=560%2C367&ssl=1", "Kallen oma", "Kalle");
        kuvalista.add(uusi);



        JSONObject obj = new JSONObject();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(HaveNetwork()) {
                    String url = "https://loremflickr.com/json/g/320/240/" + txt.getText().toString() + "/all";

                    // se että miksi nämä haluaa olla listoja, sitä en tiedä
                    final String[] fileurl = new String[1];
                    final String[] owner = new String[1];
                    final String[] license = new String[1];

                    // if the user doesn't give anything, the application is terminated... kinda silly design,
                    // but I think it was in the assignment?
                    //if(txt.getText().toString().matches("")){
                    //    finish();
                    //    System.exit(0);
                    //}

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    //Toast.makeText(getApplicationContext(), "Nappia painettu", Toast.LENGTH_SHORT)
                                    //        .show();


                                    // json all the way brotherrr
                                    /*
                                    GsonBuilder gsonBuilder = new GsonBuilder();
                                    Gson gson = gsonBuilder.create();
                                    Type listantyyppi = new TypeToken<Object>() {}.getType();
                                    Object lista;

                                    lista = gson.fromJson(response.toString(), listantyyppi);*/

                                    try {
                                        fileurl[0] = response.getString("file").toString();
                                        owner[0] = response.getString("owner");
                                        license[0] = response.getString("license");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    urlOwnerLicense uusi = new urlOwnerLicense(fileurl[0], license[0], owner[0]);
                                    kuvalista.add(uusi);
                                    kuvaAdapteri.notifyDataSetChanged();

                                    //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT)
                                    //       .show();

                                    //Picasso.with(getApplicationContext()).load(fileurl[0]).into(imageview);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(MainActivity.this, "Error fetching data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                    queue.add(jsonObjectRequest);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Ei internetyhteyttä",Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    private boolean HaveNetwork()
    {
        boolean have_WIFI = false;
        boolean have_MobileData = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();

        for(NetworkInfo info:networkInfos){
            if (info.getTypeName().equalsIgnoreCase("WIFI")){
                if (info.isConnected()){
                    have_WIFI = true;
                }
            }
            if (info.getTypeName().equalsIgnoreCase("MOBILE")){
                if (info.isConnected()){
                    have_MobileData = true;
                }
            }
        }
        return have_MobileData || have_WIFI;
    }
}



