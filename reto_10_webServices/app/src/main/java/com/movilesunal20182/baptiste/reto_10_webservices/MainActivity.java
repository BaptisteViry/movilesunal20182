package com.movilesunal20182.baptiste.reto_10_webservices;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String EXTRA_EMP_ID = "id";

    private boolean modeSearch;

    private TextView title;
    private RecyclerView list;
    private MyAdapter mAdapter;
    private EditText mFilter;
    private ArrayList<Library> libraries;
    private JSONArray json;
    private String url, urlSearch;
    private boolean dataReady;
    private FloatingActionButton searchBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: init main activity");

        title = (TextView) findViewById(R.id.title_text_view);
        mFilter = (EditText) findViewById(R.id.filter);
        list = (RecyclerView) findViewById(R.id.list);
        searchBut = (FloatingActionButton) findViewById(R.id.searchBut);

        libraries = new ArrayList<Library>();
        dataReady = false;


        modeSearch = getIntent().getBooleanExtra("modeSearch",false);
        if(getIntent().hasExtra("urlSearch")) {
            urlSearch = getIntent().getStringExtra("urlSearch");

        }
        else
            urlSearch ="";

        Log.d(TAG, "onCreate: modeSearch="+modeSearch+" urlSearch="+urlSearch);

        new DownloadFilesTask().execute();

        while(!dataReady){}

        mAdapter = new MyAdapter(libraries);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        list.setLayoutManager(mLayoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        list.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        list.setAdapter(mAdapter);

        list.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), list, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Library lib = libraries.get(position);
                Toast.makeText(getApplicationContext(), lib.getName(), Toast.LENGTH_SHORT).show();

                Intent i = new Intent(MainActivity.this, LibraryView.class);
                i.putExtra(EXTRA_EMP_ID, lib.getCode());
                i.putExtra("department", lib.getDepartment());
                i.putExtra("city", lib.getCity());
                i.putExtra("type", lib.getType());
                i.putExtra("name", lib.getName());
                i.putExtra("address", lib.getAddress());
                i.putExtra("district", lib.getDistrict());
                i.putExtra("phone", lib.getPhone());
                i.putExtra("web", lib.getWeb());
                i.putExtra("geo", lib.getGeo());
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        mFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (MainActivity.this).mAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        searchBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: changement d'activite");
                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(i);
            }
        });


    }

    public static JSONArray requestWebService(String serviceUrl) {
        disableConnectionReuseIfNecessary();

        HttpURLConnection urlConnection = null;
        try {
            Log.d(TAG, "requestWebService: init ");
            // create connection
            URL urlToRequest = new URL(serviceUrl);
            urlConnection = (HttpURLConnection)
                    urlToRequest.openConnection();
           // urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
           // urlConnection.setReadTimeout(DATARETRIEVAL_TIMEOUT);

            // handle issues
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                // handle unauthorized (if service requires user login)
                Log.d(TAG, "requestWebService: http unauthorized");
            } else if (statusCode != HttpURLConnection.HTTP_OK) {
                // handle any other errors, like 404, 500,..
                Log.d(TAG, "requestWebService: error "+statusCode);
            }

            // create JSON object from content
            InputStream in = new BufferedInputStream(
                    urlConnection.getInputStream());
            return new JSONArray(getResponseText(in));

        } catch (MalformedURLException e) {
            // URL is invalid
            Log.e(TAG, "requestWebService: malformed url ", e);
        } catch (SocketTimeoutException e) {
            Log.e(TAG, "requestWebService: timeout exception ",e );
            // data retrieval or connection timed out
        } catch (IOException e) {
            Log.e(TAG, "requestWebService: other error", e);
            // could not read response body
            // (could not create input stream)
        } catch (JSONException e) {
            Log.e(TAG, "requestWebService: json error", e);
            // response body is no valid JSON string
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return null;
    }

    public void prepareData(JSONArray tab){
        Log.d(TAG, "prepareData: init");
        if (tab != null) {
            int len = tab.length();
            Log.d(TAG, "prepareData: tab not null and length : "+len);
            for (int i=0;i<len;i++){
                JSONObject o = null;
                try {
                    o = tab.getJSONObject(i);
                    Library lib = new Library(o.getString("c_digo_dane"),o.getString("departamento"),o.getString("municipio"),
                            o.getString("tipo_de_biblioteca"),o.getString("nombre_de_la_biblioteca"),o.getString("direcci_n_de_la_biblioteca"),
                            o.getString("barrio"),o.getString("tel_fonos_de_contacto"),o.getString("p_gina_web_de_labiblioteca"),
                            o.getString("georeferencia"));
                    libraries.add(lib);
                    dataReady=true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    /**
     * required in order to prevent issues in earlier Android version.
     */
    private static void disableConnectionReuseIfNecessary() {
        // see HttpURLConnection API doc
        if (Integer.parseInt(Build.VERSION.SDK)
                < Build.VERSION_CODES.FROYO) {
            System.setProperty("http.keepAlive", "false");
        }
    }

    private static String getResponseText(InputStream inStream) {
        // very nice trick from
        // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
        return new Scanner(inStream).useDelimiter("\\A").next();
    }

    private class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {
        protected Long doInBackground(URL... urls) {
            Log.d(TAG, "doInBackground: init ");

            long totalSize = 0;
            if(!modeSearch)
                url = "https://www.datos.gov.co/resource/in3j-awgi.json";
            else {
                url = urlSearch;
            }
                json = requestWebService(url);

                prepareData(json);





            return totalSize;
        }



    }

}

