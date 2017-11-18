package com.example.yan.attendance.main;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yan.attendance.Db.GroupDb;
import com.example.yan.attendance.MainActivity;
import com.example.yan.attendance.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.List;

import static android.os.Build.VERSION_CODES.O;

public class GroupsActivity extends AppCompatActivity {

    private GroupDb mGroupDb;
    private TextView mGroupNameText;
    private TextView mNumberText;
    private ImageView backImg;
    private Button startBtn;
    private LocationManager mLocationManager;
    private String provider;
    public static final int SHOW_LOCATION =0;
    private double lngA, latA, eachlocation;


    private final double EARTH_RADIUS = 6378137.0;

    private ImageView popupImg;
    private String[] datas={"查看群成员","查看群信息","解散该群"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }else {
            getLocation();
        }
        mGroupDb =(GroupDb) getIntent().getSerializableExtra("groupInfo");

        mGroupNameText = (TextView) findViewById(R.id.group_name_text_view);
        mNumberText = (TextView) findViewById(R.id.group_number_text_view);
        mGroupNameText.setText(mGroupDb.getName());
        String numMsg = "签到人数0/"+mGroupDb.getNumber();
        mNumberText.setText(numMsg);

        //pop弹出框功能
        //参考链接：http://blog.csdn.net/csdnzouqi/article/details/51433633
        popupImg = (ImageView) findViewById(R.id.popup_image_view);
        popupImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View popupView = GroupsActivity.this.getLayoutInflater().inflate(R.layout.groups_popup_window,null);
                ListView lsvMore = (ListView) popupView.findViewById(R.id.popup_list);
                lsvMore.setAdapter(new ArrayAdapter<String>(GroupsActivity.this, android.R.layout.simple_list_item_1, datas));
                PopupWindow window = new PopupWindow(popupView,400,520);
                window.setAnimationStyle(R.style.popup_window_anim);
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
                window.setFocusable(true);
                window.setOutsideTouchable(true);
                window.update();
                window.showAsDropDown(popupImg,-300,60);
            }
        });

        backImg = (ImageView) findViewById(R.id.back);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        startBtn = (Button) findViewById(R.id.start_attendance_btn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GroupsActivity.this,"lnga:"+lngA+"lata:"+latA,Toast.LENGTH_SHORT).show();
              //
            }
        });

    }

    private void getLocation(){
        mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        List<String> providerList = mLocationManager.getProviders(true);
        if (providerList.contains(LocationManager.GPS_PROVIDER)){
            Toast.makeText(this,"GPS",Toast.LENGTH_SHORT).show();
            provider = LocationManager.GPS_PROVIDER;
        }else if (providerList.contains(LocationManager.NETWORK_PROVIDER)){
            Toast.makeText(this,"network",Toast.LENGTH_SHORT).show();
            provider = LocationManager.NETWORK_PROVIDER;
        }else {
            Toast.makeText(this, "no location provider to use", Toast.LENGTH_SHORT).show();
            return;
        }
        try{
            Location location = mLocationManager.getLastKnownLocation(provider);
            if (location != null){
                showLocation(location);
            }
            mLocationManager.requestLocationUpdates(provider, 5000,1,mLocationListener);
        }catch (SecurityException e){
            Toast.makeText(this, "getLastKnownLocation 出错", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getLocation();
                }else {
                    Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();

                }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationManager !=null){
            mLocationManager.removeUpdates(mLocationListener);
        }
    }

    LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };
    private void showLocation(final Location location) {
        Log.d("message", "showLocation: start");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String latitude = location.getLatitude() + "";
                    String longitude = location.getLongitude() + "";
                    Log.d("message", "run: " + latitude + "....." + longitude);
                    latA = location.getLatitude();
                    lngA = location.getLongitude();



                    double a1 = 114.318815;
                    double a2 = 34.82418;
                    double b1 = 114.359347;
                    double b2 = 34.777525;
                    double s2 = getDistance(a1, a2, b1, b2);
                    Log.d("message", "run: ok"+s2);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static double rad(double d){
        return d * Math.PI / 180.0;
    }
    public double getDistance(double lng1, double lat1, double lng2, double lat2){
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(
                Math.sqrt(
                        Math.pow(Math.sin(a/2),2)
                                + Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)
                )
        );
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }
}
