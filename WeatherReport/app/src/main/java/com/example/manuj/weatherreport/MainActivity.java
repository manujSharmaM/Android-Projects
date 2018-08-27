package com.example.manuj.weatherreport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

public class MainActivity extends AppCompatActivity {

    TextView result2;
    TextView result;
    Button getWeather;
    EditText city;

    //API UEL
    String baseUrl="http://api.openweathermap.org/data/2.5/weather?q=";
    String API="&appid=cd8f5713089b582bf556734bb04c8448";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result=(TextView) findViewById(R.id.result);
        result2=(TextView)findViewById(R.id.result2);
        getWeather=(Button)findViewById(R.id.button);
        city=(EditText)findViewById(R.id.getCity);


        getWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String myUrl=baseUrl+city.getText().toString()+API;
                Log.i("URL","URL IS "+myUrl);

                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, myUrl,null,

                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i("JSON", "JSON " + response);

                                try {

                                    //EXtracting jsonObjects

                                    String main=response.getString("main");
                                    JSONObject jsonObject=new JSONObject(main);
                                    String temperature=jsonObject.getString("temp");
                                    result2.setText(temperature);

                                    String info = response.getString("weather");
                                    Log.i("INFO","INFO "+info);

                                    JSONArray jsonArray=new JSONArray(info);
                                    for(int i=0;i<jsonArray.length();i++){
                                        JSONObject parObj= jsonArray.getJSONObject(i);
                                        String myWeather=parObj.getString("main");
                                        result.setText(myWeather);
                                    }

                                }catch (JSONException e){
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError VolleyError) {
                                Log.i("ERROR","cannot make request");

                            }
                        }
                );
                MySingleton.getInstance(MainActivity.this).addToRequestQue(jsonObjectRequest);

            }
        });

    }

}
