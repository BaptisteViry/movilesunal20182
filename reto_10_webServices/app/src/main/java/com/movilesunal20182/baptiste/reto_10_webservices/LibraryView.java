package com.movilesunal20182.baptiste.reto_10_webservices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LibraryView extends AppCompatActivity {

    private static final String TAG = "LibraryView";

    private String code,department,city,type,name,address,district,phone,web,geo;
    private TextView mCode, mDepartment, mCity, mType, mName, mAddress, mDistrict, mPhone, mWeb, mGeo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_view);

        code = getIntent().getStringExtra("id");
        department = getIntent().getStringExtra("department");
        city = getIntent().getStringExtra("city");
        type = getIntent().getStringExtra("type");
        name = getIntent().getStringExtra("name");
        address = getIntent().getStringExtra("address");
        district = getIntent().getStringExtra("district");
        phone = getIntent().getStringExtra("phone");
        web = getIntent().getStringExtra("web");
        geo = getIntent().getStringExtra("geo");

        mCode = (TextView) findViewById(R.id.code);
        mCity = (TextView) findViewById(R.id.city);
        mType = (TextView) findViewById(R.id.type);
        mName = (TextView) findViewById(R.id.name);
        mAddress = (TextView) findViewById(R.id.address);
        mDistrict = (TextView) findViewById(R.id.district);
        mPhone = (TextView) findViewById(R.id.phone);
        mWeb = (TextView) findViewById(R.id.web);
        mGeo = (TextView) findViewById(R.id.geo);

        mCode.setText(code);
        mCity.setText(city +" | "+ department);
        mType.setText("Tipo : "+type);
        mName.setText(name);
        mAddress.setText("Dir. : "+address);
        mDistrict.setText(district);
        mPhone.setText("Tel. : "+phone);
        mWeb.setText("Web : "+web);
        mGeo.setText(geo);
    }
}
