package com.vanistudio.a2_nytarticlesearch;

/**
 * Created by thuynh6 on 3/19/2016.
 */
import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ArticleClient {
    private String API_BASE_URL = "http://api.nytimes.com/svc/search/v2/";
    private AsyncHttpClient client;
    private Context context;

    public ArticleClient(Context context) {
        //this.context = context;
        //API_BASE_URL = context.getString(R.string.api_link);
        this.client = new AsyncHttpClient();
        this.client.setTimeout(200 * 1000);
    }

    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }

    // Method for accessing the search API
    public void getArticles(String fq, String begin_date, String end_date, JsonHttpResponseHandler handler) {
        try {
            //String url = getApiUrl(context.getString(R.string.api_search_serivce));
            String url = getApiUrl("articlesearch.json?fq=" + fq + "&begin_date=" + begin_date + "&end_date=" + end_date + "&api-key=1e80c1facf29f34936020c8a484f5aa6:13:74742440");
            /*RequestParams params = new RequestParams();
            params.put("fq", fq);
            params.put("facet_field", facet_field);
            params.put("begin_date", begin_date);
            params.put("end_date", end_date);
            params.put("api-key", "1e80c1facf29f34936020c8a484f5aa6:13:74742440");*/
            //params.setContentEncoding("");
            client.get(url, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}