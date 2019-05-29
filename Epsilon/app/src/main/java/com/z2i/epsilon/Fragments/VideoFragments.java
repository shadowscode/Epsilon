package com.z2i.epsilon.Fragments;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.PhoneAuthProvider;
import com.z2i.epsilon.AuthActivity;
import com.z2i.epsilon.Model.Videos;
import com.z2i.epsilon.Model.YoutubeAdapter;
import com.z2i.epsilon.R;
import com.z2i.epsilon.Utils.NetworkState;
import com.z2i.epsilon.Utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VideoFragments extends Fragment {

    private String BASEURL = "https://www.googleapis.com/youtube/v3/playlistItems";
    private String PLAYLISTID = "PLpoo9scEH8cyqn3FXYPAWsc-c1ruISaZ0";
    private String PART = "snippet";
    private String MAXRESULT = "50";
    private String APIKEY = "AIzaSyDHCJeU1nEhpcegYGFDxB5-wd-saUsHQ3Q";


    RecyclerView recyclerView;
    List<Videos> list;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_videofrag, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);


        Uri builturl = Uri.parse(BASEURL).buildUpon()
                .appendQueryParameter("part", PART)
                .appendQueryParameter("maxResults", MAXRESULT)
                .appendQueryParameter("playlistId", PLAYLISTID)
                .appendQueryParameter("key", APIKEY).build();


        recyclerView = (RecyclerView) view.findViewById(R.id.videorecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        list = new ArrayList<Videos>();

        URL url = null;
        try {
            url = new URL(builturl.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        if (NetworkState.getInstance(view.getContext()).isOnline()) {
            new NetAsyncTask().execute(url);
        } else {
            Toast.makeText(getActivity(), "Check your Internet Connection", Toast.LENGTH_SHORT).show();
        }

        return view;

    }

    public class NetAsyncTask extends AsyncTask<URL, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchurl = urls[0];
            String Searchresults = null;
            try {
                Searchresults = NetworkUtils.getHttpResponses(searchurl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Searchresults;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject root = new JSONObject(s);
                JSONArray items = root.getJSONArray("items");
                for (int i = 0; i < items.length(); i++) {
                    JSONObject snippet = items.getJSONObject(i).getJSONObject("snippet");
                    String title = snippet.getString("title");
                    String desc = snippet.getString("description");

                    JSONObject thumbnail = snippet.getJSONObject("thumbnails");
                    JSONObject thumbhigh = thumbnail.getJSONObject("high");
                    String thumbnailurl = thumbhigh.getString("url");

                    JSONObject resid = snippet.getJSONObject("resourceId");
                    String videoId = resid.getString("videoId");

                    Videos videos = new Videos(title, desc, thumbnailurl, videoId);
                    list.add(videos);

                    YoutubeAdapter adapter = new YoutubeAdapter(list, getContext());
                    recyclerView.setAdapter(adapter);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

}
