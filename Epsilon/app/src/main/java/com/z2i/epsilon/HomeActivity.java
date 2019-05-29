package com.z2i.epsilon;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.z2i.epsilon.Fragments.AboutFragment;
import com.z2i.epsilon.Fragments.HomeFragment;
import com.z2i.epsilon.Fragments.NotificationFragment;
import com.z2i.epsilon.Fragments.RequestFragment;
import com.z2i.epsilon.Fragments.VideoFragments;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView navigationView;
    boolean doubleBackToExitPressedOnce = false;
    DatabaseReference reference;
    FirebaseUser currentFirebaseUser;
    DatabaseReference database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        if (ContextCompat.checkSelfPermission(HomeActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }

        database = FirebaseDatabase.getInstance().getReference();
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = database.child("alpha").child("credits").child(currentFirebaseUser.getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    reference.setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        getSupportActionBar().hide();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        HomeFragment fragment1 = new HomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment1).commit();

        FirebaseMessaging.getInstance().subscribeToTopic("All").
                addOnCompleteListener(new OnCompleteListener<Void>() {
                                          @Override
                                          public void onComplete(@NonNull Task<Void> task) {
                                              if (task.isSuccessful()) {
                                                  //Log.i("Messaging ","subscribed");
                                              } else {
                                                  //Log.i("subscribed", "Failed");
                                              }
                                          }

                                      });

        navigationView = (BottomNavigationView) findViewById(R.id.btmnavmenu);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        HomeFragment home = new HomeFragment();
                        loadFragment(home);
                        navigationView.getMenu().getItem(0).setChecked(true);
                        break;
                    case R.id.nav_notification:
                        NotificationFragment notification = new NotificationFragment();
                        loadFragment(notification);
                        navigationView.getMenu().getItem(1).setChecked(true);
                        break;
                    case R.id.nav_videos:
                        VideoFragments videoFragments = new VideoFragments();
                        loadFragment(videoFragments);
                        navigationView.getMenu().getItem(2).setChecked(true);
                        break;
                    case R.id.nav_request:
                        RequestFragment requestFragment = new RequestFragment();
                        loadFragment(requestFragment);
                        navigationView.getMenu().getItem(3).setChecked(true);
                        break;
                    case R.id.nav_about:
                        AboutFragment aboutFragment = new AboutFragment();
                        loadFragment(aboutFragment);
                        navigationView.getMenu().getItem(4).setChecked(true);
                        break;
                }
                return loadFragment(fragment);
            }
        });
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}
