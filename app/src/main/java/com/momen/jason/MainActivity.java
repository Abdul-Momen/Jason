package com.momen.jason;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    AutoCompleteTextView autoCompleteTextView;
    String Url="https://api.myjson.com/bins/1gwxfc";
    ArrayList<String>MyArrayList;
    ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autoCompleteTextView=findViewById(R.id.autocomplet_id);
        MyArrayList=new ArrayList<>();
        adapter=new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,MyArrayList);
        loadData();

    }

    private void loadData() {

        StringRequest stringRequest=new StringRequest(Request.Method.GET, Url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {

                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray= jsonObject.getJSONArray("myData");
                            for (int i=0;1<jsonArray.length() ;i++ ){

                                JSONObject receive=jsonArray.getJSONObject(i);
                                MyArrayList.add(receive.getString("countrylist"));

                            }

                            autoCompleteTextView.setThreshold(1);
                            autoCompleteTextView.setAdapter(adapter);

                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, "Server Error", Toast.LENGTH_SHORT).show();

            }
        }
        );

        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(stringRequest);


    }


}
