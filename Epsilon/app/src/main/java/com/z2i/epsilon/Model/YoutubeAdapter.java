package com.z2i.epsilon.Model;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.z2i.epsilon.R;
import com.z2i.epsilon.VideoPlayerActivity;

import org.w3c.dom.Text;

import java.util.List;

public class YoutubeAdapter extends RecyclerView.Adapter<YoutubeAdapter.MyYoutubeViewHolder> {

    List<Videos> list;
    Context context;

    LinearLayout layout;

    public YoutubeAdapter(List<Videos> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyYoutubeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_videos,parent,false);
        return new MyYoutubeViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyYoutubeViewHolder holder, int position) {
        final Videos videos=list.get(position);

        Picasso.with(context).load(videos.getUrl()).into(holder.thumbnail);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), VideoPlayerActivity.class);
                intent.putExtra("videoId",videos.videoId);
                holder.layout.getContext().startActivity(intent);

            }
        });
        holder.title.setText(videos.getTitle());
        holder.desc.setText(videos.getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyYoutubeViewHolder extends RecyclerView.ViewHolder{
        public TextView title,desc;
        public ImageView thumbnail;
        public LinearLayout layout;
        public MyYoutubeViewHolder(View itemView) {
            super(itemView);
            thumbnail=(ImageView)itemView.findViewById(R.id.imgv_thumbnail);
            layout=(LinearLayout)itemView.findViewById(R.id.videoclick);

            title=(TextView)itemView.findViewById(R.id.vid_title);
            desc=(TextView)itemView.findViewById(R.id.vid_desc);


        }
    }
}
