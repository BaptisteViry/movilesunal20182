package com.movilesunal20182.baptiste.reto_10_webservices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";

    private EditText mCity,mDep,mType;
    private Button searchButton;
    private String city,dep,type, url;
    private boolean searchActive, b1, b2, b3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mCity = (EditText) findViewById(R.id.city_search);
        mDep = (EditText) findViewById(R.id.department_search);
        mType = (EditText) findViewById(R.id.type_search);
        searchButton = (Button) findViewById(R.id.button_search);

        searchActive = false;
        b1=true;
        b2=true;
        b3=true;

        city = "";
        dep = "";
        type = "";

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city = mCity.getText().toString();
                dep = mDep.getText().toString();
                type = mType.getText().toString();

                Intent i = new Intent(SearchActivity.this, Main2Activity.class);
                i.putExtra("modeSearch", true);
                url = "https://www.datos.gov.co/resource/in3j-awgi.json";

                if(!city.equals("") && dep.equals("") && type.equals(""))
                    url+="?municipio="+city.toUpperCase();

                if(city.equals("") && !dep.equals("") && type.equals(""))
                    url+="?departamento="+dep.toUpperCase();

                if(city.equals("") && dep.equals("") && !type.equals(""))
                    url+="?tipo_de_biblioteca="+type.toUpperCase();

                if(!city.equals("") && !dep.equals("") && type.equals(""))
                    url+="?municipio="+city.toUpperCase()+"&departamento="+dep.toUpperCase();

                if(city.equals("") && !dep.equals("") && !type.equals(""))
                    url+="?departamento="+dep.toUpperCase()+"&tipo_de_biblioteca="+type.toUpperCase();

                if(!city.equals("") && dep.equals("") && !type.equals(""))
                    url+="?tipo_de_biblioteca="+type.toUpperCase()+"&municipio="+city.toUpperCase();

                if(!city.equals("") && !dep.equals("") && !type.equals(""))
                    url+="?tipo_de_biblioteca="+type.toUpperCase()+"&municipio="+city.toUpperCase()+"&departamento="+dep.toUpperCase();

                Log.d(TAG, "onClick: "+city+"!"+dep+"!"+type+"!");
                Log.d(TAG, "onClick:   "+url);
                i.putExtra("urlSearch",url);
                startActivity(i);
            }
        });
    }
}
