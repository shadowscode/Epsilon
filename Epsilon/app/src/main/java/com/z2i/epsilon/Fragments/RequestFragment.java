package com.z2i.epsilon.Fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.app.Fragment;

import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.z2i.epsilon.Holder.HomeViewholder;
import com.z2i.epsilon.Holder.RequestViewHolder;
import com.z2i.epsilon.Model.Home;
import com.z2i.epsilon.Model.Request;
import com.z2i.epsilon.R;

public class RequestFragment extends Fragment {

    FloatingActionButton actionButton;

    RecyclerView recyclerview;
    FirebaseRecyclerAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_requestfrag, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        actionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton3);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerrequest);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(layoutManager);

        DividerItemDecoration decoration = new DividerItemDecoration(recyclerview.getContext(), layoutManager.getOrientation());
        recyclerview.addItemDecoration(decoration);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("alpha").child("notification");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adapter.startListening();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Query query = FirebaseDatabase.getInstance().getReference().child("alpha").child("request");
        FirebaseRecyclerOptions<Request> options = new FirebaseRecyclerOptions.Builder<Request>().setQuery(query, Request.class).build();

        adapter = new FirebaseRecyclerAdapter<Request, RequestViewHolder>(options) {

            @NonNull
            @Override
            public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View myview = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_request, parent, false);
                return new RequestViewHolder(myview);
            }

            @Override
            protected void onBindViewHolder(@NonNull RequestViewHolder holder, int position, @NonNull Request model) {
                holder.title.setText(model.getTitle());
                holder.domain.setText(model.getDomain());
                holder.status.setText(model.getStatus());
                holder.date.setText(model.getDate());
                holder.time.setText(model.getTime());
            }
        };


        recyclerview.setAdapter(adapter);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Requestfrag1 requestfrag1 = new Requestfrag1();
                loadFragment(requestfrag1);
            }
        });
        return view;
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            return true;
        }
        return false;
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
