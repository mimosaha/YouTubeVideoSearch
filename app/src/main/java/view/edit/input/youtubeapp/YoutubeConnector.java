package view.edit.input.youtubeapp;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class YoutubeConnector {
    private YouTube youtube;
    private YouTube.Search.List query;
    private Context context;

    // Your developer key goes here
    public static final String KEY
            = "AIzaSyAOIXxJetlsLM9lk_H7vpbOx7bGMONIaXw";

    public YoutubeConnector() {

    }

    public YoutubeConnector(Context context) {
        this.context = context;
        youtube = new YouTube.Builder(new NetHttpTransport(),
                new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest hr) throws IOException {
            }
        }).setApplicationName(context.getString(R.string.app_name)).build();

        try {
            query = youtube.search().list("id,snippet");
            query.setKey(KEY);
            query.setType("video");
            query.setFields("*");
        } catch (IOException e) {
            Log.d("YC", "Could not initialize: " + e);
        }
    }

    public List<VideoItem> search(String keywords) {
        query.setQ(keywords);
        try {
            SearchListResponse response = query.execute();
            List<SearchResult> results = response.getItems();

            List<VideoItem> items = new ArrayList<VideoItem>();
            for (SearchResult result : results) {
                VideoItem item = new VideoItem();
                item.setTitle(result.getSnippet().getTitle());
                item.setDescription(result.getSnippet().getDescription());
                item.setThumbnailURL(result.getSnippet().getThumbnails().getDefault().getUrl());
                item.setId(result.getId().getVideoId());
                items.add(item);
            }

            String ids = "";
            for (VideoItem videoItem : items) {
                if (TextUtils.isEmpty(ids)) {
                    ids = videoItem.getId();
                } else {
                    ids = ids + "," + videoItem.getId();
                }
            }
            retrieveDataByRetrofit(context, ids);

            return items;
        } catch (IOException e) {
            Log.d("YC", "Could not search: " + e);
            return null;
        }
    }

    public void setInitReq(Context context, String searchText) {
        RequestQueue queue = Volley.newRequestQueue(context);

        String url = "https://www.googleapis.com/youtube/v3/search?part=id&snippet&q=" + searchText + "&type=video&key=" + KEY;

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("MIMO_SAHA::", "Res: " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("MIMO_SAHA::", "Err: " + error.getMessage());
            }
        });
        queue.add(stringRequest);
    }

    private void retrieveAllData(Context context, String ids) {

        RequestQueue queue = Volley.newRequestQueue(context);

        String url = "https://www.googleapis.com/youtube/v3/videos?id=" + ids + "&key=" + KEY
                + "&part=snippet,contentDetails,statistics,status";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("MIMO_SAHA::", "Res: " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("MIMO_SAHA::", "Err: " + error.getMessage());
            }
        });
        queue.add(stringRequest);
    }

    private void retrieveDataByRetrofit(Context context, String ids) {
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        FileApis fileApis = retrofit.create(FileApis.class);

        Call<PlaylistItemListResponse> call = fileApis.getVideoInfo(ids, KEY, "snippet,contentDetails,statistics,status");

        call.enqueue(new Callback<PlaylistItemListResponse>() {
            @Override
            public void onResponse(Call<PlaylistItemListResponse> call,
                                   retrofit2.Response<PlaylistItemListResponse> response) {

                if (response.body() != null) {
                    PlaylistItemListResponse playlistItemListResponse = response.body();
                }

                Log.v("MIMO_SAHA::", "Response: " + call + " >> " + response);
            }

            @Override
            public void onFailure(Call<PlaylistItemListResponse> call, Throwable t) {
                Log.v("MIMO_SAHA::", "Response: " + call + " >> " + t);
            }
        });
    }
}