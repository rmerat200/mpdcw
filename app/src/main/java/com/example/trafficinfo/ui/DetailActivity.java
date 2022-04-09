//Reza Merati S1711794
package com.example.trafficinfo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.trafficinfo.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    TextView detail_title, detail_desc, detail_link, detail_pubDate;

    SupportMapFragment mapFragment;
    GoogleMap googleMap;
    FusedLocationProviderClient fusedLocationProviderClient;

    private String latTxt = "";
    private String longTxt = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setTitle("Detail Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        detail_title = findViewById(R.id.detail_title);
        detail_desc = findViewById(R.id.detail_desc);
        detail_link = findViewById(R.id.detail_link);
        detail_pubDate = findViewById(R.id.detail_pubDate);

        //set values on text
        detail_title.setText(getIntent().getStringExtra("title"));
        detail_desc.setText(getIntent().getStringExtra("description"));
        detail_link.setText(getIntent().getStringExtra("link"));
        detail_pubDate.setText(getIntent().getStringExtra("pubDate"));

        String latLng = getIntent().getStringExtra("latLng");
        String[] latLngParts = latLng.split(" ");
        latTxt = latLngParts[0];
        longTxt = latLngParts[1];

        //mapFragment = (R.id.map);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        detail_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(detail_link.getText().toString()));
                startActivity(browserIntent);
            }
        });
    }
}