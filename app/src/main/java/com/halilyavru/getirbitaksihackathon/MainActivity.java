package com.halilyavru.getirbitaksihackathon;

import android.content.DialogInterface;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements DateCountSelectFragment.Listener{

    private Toolbar toolbar;
    private final String TAG = "MainActivity";
    private final String URL = "https://getir-bitaksi-hackathon.herokuapp.com/searchRecords";
    private List<Record> listRecord;
    private boolean recordBoolean = true;
    private LinearLayout llPb;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            DateCountSelectFragment fragment = new DateCountSelectFragment();
            fragment.setListener(this);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,  fragment)
                    .commit();
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        llPb = (LinearLayout) findViewById(R.id.ll_pb);
        pb = (ProgressBar) findViewById(R.id.progressBar);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         if (item.getItemId() == android.R.id.home) {
             if(recordBoolean){
                 cikisDialog();
             }else{
                 recordBoolean = true;
                 DateCountSelectFragment fragment = new DateCountSelectFragment();
                 fragment.setListener(this);
                 getSupportFragmentManager().beginTransaction()
                         .replace(R.id.container,  fragment)
                         .commit();
             }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getRecordsListener(String startDate, String endDate, int minCount, int maxCount) {
        final Map<String,String> params = new HashMap<String,String>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("minCount", String.valueOf(minCount));
        params.put("maxCount", String.valueOf(maxCount));

        requestPost(params);
    }

    public void requestPost(final Map<String,String> mapRequest){
        llPb.setVisibility(View.VISIBLE);
        final StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                llPb.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject =  new JSONObject(response);
                    String message = jsonObject.optString("msg");
                    Log.d(TAG,"code: "+jsonObject.optInt("code")+", msg: "+message);

                    if(jsonObject.optInt("code") == 0){
                        listRecord =  new ArrayList<>();
                        JSONArray jsonArray = jsonObject.getJSONArray("records");

                        if(jsonArray.length()>0){

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jObjRecord = jsonArray.getJSONObject(i);
                                JSONObject jObjSubRecord = jObjRecord.getJSONObject("_id");

                                listRecord.add(new Record(jObjSubRecord.optString("_id"),
                                        jObjSubRecord.optString("key"),
                                        jObjSubRecord.optString("value"),
                                        jObjSubRecord.optString("createdAt"),
                                        jObjRecord.optInt("totalCount")));

                            }
                            Log.d(TAG,"listRecord: "+listRecord.size());
                            recordBoolean = false;
                            Fragment fragment =  new RecordsFragment();
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("listRecord", (ArrayList<? extends Parcelable>) listRecord);
                            fragment.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.container,  fragment).commit();

                        }else{
                            message = "Kayıt Bulunamadı.";
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Açıklama")
                                    .setMessage(message)
                                    .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }


                    }else{

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Açıklama")
                                .setMessage(message)
                                .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }

                    System.out.println("Response: "+response);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,error.toString());
                llPb.setVisibility(View.GONE);

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> map = mapRequest;

                return map;
            }
        };
        MySingleton.getInstance(MainActivity.this).getRequestQueue().add(request);

    }

    private void cikisDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Uyarı");
        builder.setMessage("Çıkmak istediğinize eminmisiniz?");
        builder.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton("VAZGEÇ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        if(recordBoolean){
            cikisDialog();
        }else{
            recordBoolean = true;
            DateCountSelectFragment fragment = new DateCountSelectFragment();
            fragment.setListener(this);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,  fragment)
                    .commit();
        }

    }
}
