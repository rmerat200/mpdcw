package com.example.trafficinfo.ui.plannedroadworks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.trafficinfo.R;
import com.example.trafficinfo.adapters.TrafficAdapter;
import com.example.trafficinfo.models.TrafficInfoModel;

import org.jsoup.Jsoup;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PlannedRoadworksFragment extends Fragment {

    OkHttpClient client = new OkHttpClient();
    String BASE_URL = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx",
            responseTxt ="", titleTxt = "", descriptionTxt = "", pubDateTxt = "", linkTxt = "", latLongTxt = "";

    ArrayList<TrafficInfoModel> plannedRoadWorksList;
    TrafficAdapter adapter;

    RecyclerView recyclerView;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_planned_roadworks, container, false);

        recyclerView=view.findViewById(R.id.plannedRecyclerView);
        progressBar=view.findViewById(R.id.plannedProgressBar);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        plannedRoadWorksList = new ArrayList<>();

        apiRequest();

        return view;
    }

    private void apiRequest() {
        Request request = new Request.Builder()
                .url(BASE_URL)
                .build();

        client.newCall(request).enqueue(new Callback(){

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                responseTxt = response.body().string();
                fetchItems();
                if(plannedRoadWorksList!=null)
                    setValuesOnAdapter();
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("error", e.toString());
            }
        });
    }

    private void setValuesOnAdapter() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter = new TrafficAdapter(plannedRoadWorksList,getActivity());
                if (adapter.getItemCount() > 0) {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(adapter);
                    //emptyList.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    //emptyList.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void fetchItems() {
        try {

            boolean foundItem = false;
            String tagValue = "";

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput( new StringReader( responseTxt ) );
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = xpp.getName();
                if(eventType == XmlPullParser.START_DOCUMENT) {

                } else if(eventType == XmlPullParser.START_TAG) {
                    if(tagName.equalsIgnoreCase("item"))
                        foundItem = true;
                } else if(eventType == XmlPullParser.TEXT) {
                    tagValue = xpp.getText();
                } else if(eventType == XmlPullParser.END_TAG) {
                    if(tagName.equalsIgnoreCase("item")) {
                        foundItem = false;
                        plannedRoadWorksList.add(new TrafficInfoModel(titleTxt,descriptionTxt,pubDateTxt,latLongTxt,linkTxt));
                    } else if (foundItem && tagName.equals("title")) {
                        titleTxt = tagValue;
                    } else if (foundItem && tagName.contains("description")) {
                        descriptionTxt = Jsoup.parse(tagValue).text();
                    } else if (foundItem && tagName.equals("link")) {
                        linkTxt = tagValue;
                    } else if (foundItem && tagName.contains("point")) {
                        latLongTxt = tagValue;
                    } else if (foundItem && tagName.equals("pubDate")) {
                        pubDateTxt = tagValue;
                    }
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}