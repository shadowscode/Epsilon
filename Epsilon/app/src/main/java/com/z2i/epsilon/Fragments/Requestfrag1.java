package com.z2i.epsilon.Fragments;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.z2i.epsilon.Model.Upload;
import com.z2i.epsilon.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Requestfrag1 extends Fragment {

    EditText projecttitle, domain, desc;
    Button request;
    DatabaseReference database;
    DatabaseReference reference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_req_upload, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        database = FirebaseDatabase.getInstance().getReference();
        reference = database.child("alpha").child("request").push();

        projecttitle = (EditText) view.findViewById(R.id.ettitle);
        domain = (EditText) view.findViewById(R.id.etdomain);
        desc = (EditText) view.findViewById(R.id.etdesc);
        request = (Button) view.findViewById(R.id.buttonrequest);

        final Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        final SimpleDateFormat dateFormat1 = new SimpleDateFormat("HH:mm:ss");

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = projecttitle.getText().toString().trim();
                String domaintitle = domain.getText().toString().trim();
                String desc = domain.getText().toString().trim();
                String status = "Not Approved";
                String date = dateFormat.format(calendar.getTime());
                String time = dateFormat1.format(calendar.getTime());
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                reference.setValue(new Upload(title, domaintitle, date, time, status, desc,uid));

                RequestFragment requestfragment = new RequestFragment();
                loadFragment(requestfragment);

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
}
