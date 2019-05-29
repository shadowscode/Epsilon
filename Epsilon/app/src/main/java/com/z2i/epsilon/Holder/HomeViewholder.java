package com.z2i.epsilon.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.z2i.epsilon.R;

public class HomeViewholder extends RecyclerView.ViewHolder {
    public TextView title,domain,credit,download;
    public ImageView homeicon;
    public LinearLayout layout;
    public HomeViewholder(View itemView) {
        super(itemView);
        title=(TextView)itemView.findViewById(R.id.home_appname);
        domain=(TextView)itemView.findViewById(R.id.home_domain);
        credit=(TextView)itemView.findViewById(R.id.home_credit);
        download=(TextView)itemView.findViewById(R.id.home_download_url);

        homeicon=(ImageView)itemView.findViewById(R.id.homeicon);

        layout=(LinearLayout)itemView.findViewById(R.id.homelinear);

    }
}
