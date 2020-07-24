package com.example.dummy;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private AdView mAdView;
    private RewardedVideoAd mRewardedVideoAd;
    private InterstitialAd interstitialAd;
    final static int REQUEST_CODE_INTERNET = 100;
    Button btn, btnInter, getserver, upload;
    private String host_url  = "https://jsonplaceholder.typicode.com/";
    TextView user,body,title;
    EditText userName, details;

    private RetroFitApiInterFace apiInterFace;
    private List<UserModel> userModelList;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.INTERNET,
                                Manifest.permission.ACCESS_NETWORK_STATE},REQUEST_CODE_INTERNET);
        }
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(rewardedVideoAdListener);
        loadRewardedVideoAd();

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.setAdListener(listener);
        loadInterstitalAd();

        btn = findViewById(R.id.ad);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.setVisibility(View.GONE);
                if (mRewardedVideoAd.isLoaded())
                    mRewardedVideoAd.show();
            }
        });

        btnInter = findViewById(R.id.interAd);
        btnInter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnInter.setVisibility(View.GONE);
                if (interstitialAd.isLoaded())
                    interstitialAd.show();
            }
        });

        mAdView = findViewById(R.id.adView);
        loadbannerAd();

        Button button = findViewById(R.id.click);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialogFirst bottomSheetDialogFirst = new BottomSheetDialogFirst(getApplicationContext(),getSupportFragmentManager());
                bottomSheetDialogFirst.show(getSupportFragmentManager(),"FirstComment");
            }
        });

        user = findViewById(R.id.user);
        title = findViewById(R.id.title);
        body = findViewById(R.id.body);
        getserver = findViewById(R.id.get);
        getserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String url ="https://gorest.co.in/public-api/users";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String User = response.getString("Gradyy");
                            String Title = response.getString("title");
                            String Body = response.getString("body");
                            user.setText(User);
                            title.setText(Title);
                            body.setText(Body);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                VolleySingletonPattern.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);*/
                getDataFromServer();

            }
        });
        userName = findViewById(R.id.edtuser);
        details = findViewById(R.id.details);
        upload = findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*JSONObject object = new JSONObject();
                try {
                    object.put("id", 12);
                    object.put("name", "Tester");
                    object.put("username", "Born tester");
                    object.put("email", "Rey.Padberg@karina.biz");
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("street", "Kattie Turnpike");
                    jsonObject.put("suite", "Suite 198");
                    jsonObject.put("city", "Lebsackbury");
                    jsonObject.put("zipcode", "31428-2261");
                    JSONObject object1 = new JSONObject();
                    object1.put("lat", "-38.2386");
                    object1.put("lng", "57.2232");
                    jsonObject.put("geo",object1);
                    object.put("address",jsonObject);
                    object.put("phone", "024-648-3804");
                    object.put("website", "ambrose.net");
                    JSONObject object2 = new JSONObject();
                    object2.put("name", "Hoeger LLC");
                    object2.put("catchPhrase", "Centralized empowering task-force");
                    object2.put("bs", "target end-to-end models");
                    object.put("company",object2);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String url = "https://jsonplaceholder.typicode.com/users";
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getInt("code")==201){
                                Log.e("Responseif",response.toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("Responseif",response.toString() + "   Successful");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error",error.toString() + "   Unsuccessful");
                    }
                });
                VolleySingletonPattern.getInstance(MainActivity.this).addToRequestQueue(request);*/
                String title = userName.getText().toString();
                String body = details.getText().toString();
                uploadToServer(title,body);
            }
        });
    }

    private void uploadToServer(String title, String body) {
        apiInterFace = RetroFitApiClient.getRetrofitApiClient().create(RetroFitApiInterFace.class);
        Call<List<UserModel>> call = apiInterFace.setUserInfo("11",title,body);
        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                Toast.makeText(MainActivity.this,"Upload Successful",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {

            }
        });
    }

    private void getDataFromServer() {
        apiInterFace = RetroFitApiClient.getRetrofitApiClient().create(RetroFitApiInterFace.class);
        Call<List<UserModel>> call = apiInterFace.getUserInfo();
        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, retrofit2.Response<List<UserModel>> response) {
                userModelList = new ArrayList<>();
                userModelList = response.body();
                user.setText(userModelList.get(0).getUserId());
                title.setText(userModelList.get(0).getTitle());
                body.setText(userModelList.get(0).getBody());
                Log.e("check",userModelList.size() + "");
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {

            }
        });
    }

    RewardedVideoAdListener rewardedVideoAdListener = new RewardedVideoAdListener() {
        @Override
        public void onRewardedVideoAdLoaded() {
            //btn.setVisibility(View.VISIBLE);
        }

        @Override
        public void onRewardedVideoAdOpened() {

        }

        @Override
        public void onRewardedVideoStarted() {

        }

        @Override
        public void onRewardedVideoAdClosed() {

        }

        @Override
        public void onRewarded(RewardItem rewardItem) {

        }

        @Override
        public void onRewardedVideoAdLeftApplication() {

        }

        @Override
        public void onRewardedVideoAdFailedToLoad(int i) {

        }

        @Override
        public void onRewardedVideoCompleted() {

        }
    };

    AdListener listener = new AdListener(){
        @Override
        public void onAdLoaded() {
            // Code to be executed when an ad finishes loading.
            //btnInter.setVisibility(View.VISIBLE);
        }
    };

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());
    }

    private void loadbannerAd(){
        mAdView.loadAd(new AdRequest.Builder().build());
    }

    private void loadInterstitalAd(){
        interstitialAd.loadAd(new AdRequest.Builder().build());
        if (interstitialAd.isLoaded())
            interstitialAd.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_CODE_INTERNET:

        }
    }
}