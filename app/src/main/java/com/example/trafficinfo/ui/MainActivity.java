//Reza Merati S1711794
package com.example.trafficinfo.ui;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.trafficinfo.R;
import com.example.trafficinfo.ui.currentincidents.CurrentIncidentsFragment;
import com.example.trafficinfo.ui.plannedroadworks.PlannedRoadworksFragment;
import com.example.trafficinfo.ui.roadworks.RoadworksFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Current Incidents");
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new CurrentIncidentsFragment()).commit();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.currentIncidentsFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                {
                    switch (item.getItemId()) {
                        case R.id.currentIncidentsFragment:
                            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new CurrentIncidentsFragment()).commit();
                            getSupportActionBar().setTitle("Current Incidents - S1711794");
                            return true;
                        case R.id.roadworksFragment:
                            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new RoadworksFragment()).commit();
                            getSupportActionBar().setTitle("Roadworks - S1711794");
                            return true;
                        case R.id.plannedRoadworksFragment:
                            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new PlannedRoadworksFragment()).commit();
                            getSupportActionBar().setTitle("Planned Roadworks - S1711794");
                            return true;
                    }
                }
                return false;
            }
        });
    }
}