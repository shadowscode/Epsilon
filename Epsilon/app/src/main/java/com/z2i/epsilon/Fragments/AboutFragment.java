package com.z2i.epsilon.Fragments;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.z2i.epsilon.AuthActivity;
import com.z2i.epsilon.R;

public class AboutFragment extends Fragment {

    TextView logout, topup, rewardcount;
    FirebaseAuth auth;
    private RewardedVideoAd mRewardedVideoAd;
    DatabaseReference database;
    DatabaseReference reference;
    FirebaseUser currentFirebaseUser;
    int sample;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_aboutfrag, container, false);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        logout = (TextView) view.findViewById(R.id.buttonlogout);
        topup = (TextView) view.findViewById(R.id.buttontopup);
        rewardcount = (TextView) view.findViewById(R.id.rewardid);
        auth = FirebaseAuth.getInstance();

        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        database = FirebaseDatabase.getInstance().getReference();
        reference = database.child("alpha").child("credits").child(currentFirebaseUser.getUid());


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    int count = dataSnapshot.getValue(Integer.class);
                    sample = dataSnapshot.getValue(Integer.class);
                    rewardcount.setText(" " + count);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(getContext());
        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdOpened() {

            }

            @Override
            public void onRewardedVideoStarted() {

            }

            @Override
            public void onRewardedVideoAdLeftApplication() {

            }

            @Override
            public void onRewardedVideoAdLoaded() {
                Toast.makeText(getContext(), "Ad loaded for Reward.", Toast.LENGTH_SHORT).show();
                topup.setTextColor(Color.GREEN);
                //topup.setEnabled(true);
            }

            @Override
            public void onRewardedVideoAdClosed() {
                mRewardedVideoAd.loadAd("ca-app-pub-7428629731661981/7666440792", new AdRequest.Builder().build());
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {
                final int newcount;

                Toast.makeText(getContext(), "Credits Added to your Account", Toast.LENGTH_SHORT).show();
                int newsample = sample + 1;
                reference.setValue(newsample);
                topup.setTextColor(Color.RED);
                mRewardedVideoAd.loadAd("ca-app-pub-7428629731661981/7666440792", new AdRequest.Builder().build());
            }


            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                Toast.makeText(getContext(), "Try again Later or Contact Admin", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoCompleted() {
                mRewardedVideoAd.loadAd("ca-app-pub-7428629731661981/7666440792", new AdRequest.Builder().build());
            }
        });

        topup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (topup.getText().equals("Load Reward")) {
                    mRewardedVideoAd.loadAd("ca-app-pub-7428629731661981/7666440792", new AdRequest.Builder().build());
                    topup.setText("Claim Reward");
                } else if (topup.getText().equals("Claim Reward") && topup.getCurrentTextColor() == Color.RED) {
                    Toast.makeText(getContext(), "Please wait Ad is loading", Toast.LENGTH_SHORT).show();
                } else if (topup.getText().equals("Claim Reward")) {
                    mRewardedVideoAd.show();
                }

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(getActivity(), AuthActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
       // mRewardedVideoAd.loadAd("ca-app-pub-7428629731661981/7666440792", new AdRequest.Builder().build());
    }
}
