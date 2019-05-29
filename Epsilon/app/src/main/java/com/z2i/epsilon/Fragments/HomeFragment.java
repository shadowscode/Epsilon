package com.z2i.epsilon.Fragments;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.z2i.epsilon.Holder.HomeViewholder;
import com.z2i.epsilon.Model.Home;
import com.z2i.epsilon.R;

import java.io.File;

public class HomeFragment extends Fragment {

    RecyclerView homerecyclerview;
    FirebaseRecyclerAdapter adapter;
    long refid;

    FirebaseAuth auth;
    FirebaseUser currentFirebaseUser;
    DatabaseReference root;
    DatabaseReference childref;
    int topup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_homefrag, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        homerecyclerview = (RecyclerView) view.findViewById(R.id.recyclerhome);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        homerecyclerview.setLayoutManager(layoutManager);

        auth = FirebaseAuth.getInstance();
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        root = FirebaseDatabase.getInstance().getReference();
        childref = root.child("alpha").child("credits").child(currentFirebaseUser.getUid());

        childref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    topup = dataSnapshot.getValue(Integer.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query query = FirebaseDatabase.getInstance().getReference().child("alpha").child("home");
        FirebaseRecyclerOptions<Home> options = new FirebaseRecyclerOptions.Builder<Home>().setQuery(query, Home.class).build();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("alpha").child("home");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adapter.startListening();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter = new FirebaseRecyclerAdapter<Home, HomeViewholder>(options) {

            @NonNull
            @Override
            public HomeViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View myview = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_home, parent, false);
                return new HomeViewholder(myview);
            }

            @Override
            protected void onBindViewHolder(@NonNull HomeViewholder holder, int position, @NonNull final Home model) {

                holder.title.setText(model.getTitle());

                holder.credit.setText("" + model.getCredit());

                holder.domain.setText(model.getDomain());

                if (model.getCredit() <= topup) {
                    holder.download.setVisibility(View.VISIBLE);
                }

                holder.download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        childref.setValue(topup - model.getCredit());
                        download(model.getSurl(), model.getTitle());

                    }
                });

                holder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(model.getPdf()));
                        startActivity(intent);
                    }
                });

                Picasso.with(getContext()).load(model.getIcon()).into(holder.homeicon);

            }
        };

        DividerItemDecoration decoration = new DividerItemDecoration(homerecyclerview.getContext(), layoutManager.getOrientation());
        homerecyclerview.addItemDecoration(decoration);
        homerecyclerview.setAdapter(adapter);
        adapter.startListening();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    public void download(String url, String title) {

        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {

            File direct = new File(Environment.getExternalStorageDirectory() + "/Alpha Projects");

            if (!direct.exists()) {
                direct.mkdirs();
            }

            DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
            Uri Download_Uri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
            request.setTitle(title + ".zip");
            request.setDescription("Downloading " + title + ".zip");
            request.setVisibleInDownloadsUi(true);
            request.setDestinationInExternalPublicDir("/Epsilon", title + ".zip");
            refid = downloadManager.enqueue(request);
        } else {
            Toast.makeText(getActivity(), "Change permissions for External storage in Settings", Toast.LENGTH_SHORT).show();

        }

    }
}
