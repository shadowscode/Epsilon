package com.z2i.epsilon.Fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.z2i.epsilon.Holder.HomeViewholder;
import com.z2i.epsilon.Holder.NotificationViewHolder;
import com.z2i.epsilon.Model.Home;
import com.z2i.epsilon.Model.Notification;
import com.z2i.epsilon.R;

public class NotificationFragment extends Fragment {

    RecyclerView recyclerView;
    FirebaseRecyclerAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_notificationfrag, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);



        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_notification);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);


        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference().child("alpha").child("notification");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adapter.startListening();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        DividerItemDecoration decoration=new DividerItemDecoration(recyclerView.getContext(),layoutManager.getOrientation());
        recyclerView.addItemDecoration(decoration);

        Query query = FirebaseDatabase.getInstance().getReference().child("alpha").child("notification");
        FirebaseRecyclerOptions<Notification> options = new FirebaseRecyclerOptions.Builder<Notification>().setQuery(query, Notification.class).build();

        adapter = new FirebaseRecyclerAdapter<Notification, NotificationViewHolder>(options) {
            @NonNull
            @Override
            public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View myview = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_notifications, parent, false);
                return new NotificationViewHolder(myview);
            }

            @Override
            protected void onBindViewHolder(@NonNull NotificationViewHolder holder, int position, @NonNull Notification model) {
                holder.title.setText(model.getTitle());
                holder.desc.setText(model.getDesc());
                holder.date.setText(model.getDate());
                holder.time.setText(model.getTime());
                if(model.getIcon().equals("project")){
                    holder.icon.setImageResource(R.mipmap.ic_noti_proj);
                }else if(model.getIcon().equals("video")){
                    holder.icon.setImageResource(R.mipmap.ic_noti_vid);
                }
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
