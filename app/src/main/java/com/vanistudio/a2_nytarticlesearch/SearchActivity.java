package com.vanistudio.a2_nytarticlesearch;

import android.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.GridLayoutAnimationController;
import android.widget.GridView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity implements dfSearchFilter.OnClickListener {
    private ArticleClient client;
    private GridView gvArticleList;
    private gvArticleListAdapter adapter;
    private String menuQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        gvArticleList = (GridView)findViewById(R.id.gvArticleList);
        ArrayList<Article> tArticles = new ArrayList<>();
        adapter = new gvArticleListAdapter(this, tArticles);
        gvArticleList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                menuQuery = query;
                Date x=new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("MM");
                String month = sdf.format(x);
                String today = String.valueOf(x.getYear() + 1900) + month + String.valueOf(x.getDate());
                String startDate = String.valueOf(x.getYear() - 1 + 1900) + month + String.valueOf(x.getDate());
                fetchArticle(query, startDate, today);
                searchView.clearFocus();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        MenuItem settingItem = menu.findItem(R.id.action_settings);
       settingItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(MenuItem item) {
               showEditDialog();
               return true;
           }
       });
        return super.onCreateOptionsMenu(menu);
    }


    private void fetchArticle(String fq, String begin_date, String end_date){
        client = new ArticleClient(this);
        client.getArticles(fq, begin_date, end_date, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray docs;
                    if (response != null) {
                        // Get the docs json array
                        docs = response.getJSONObject("response").getJSONArray("docs");
                        final ArrayList<Article> articles = Article.fromJson(docs);
                        adapter.clear();
                        adapter.addAll(articles);
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    // Invalid JSON format, show appropriate error.
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    private void showEditDialog() {
        FragmentManager fm = getFragmentManager();
        dfSearchFilter editNameDialog = dfSearchFilter.newInstance("Some Title");
        editNameDialog.show(fm, "abc");
    }

    public void onSaveClick(SearchFilter filter){
        fetchArticle(filter.getNewsDesk() + " AND " + menuQuery, filter.getBeginDate(), filter.getEndDate());
    }

}
