package com.vanistudio.a2_nytarticlesearch;

import android.hardware.Camera;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by thuynh6 on 3/19/2016.
 */
public class Article {
    private String imageUrl;
    private String headline;

    public String getImageUrl(){
        return imageUrl;
    }

    public String getHeadline() {
        return headline;
    }

    public static ArrayList<Article> fromJson(JSONArray jsonArray){
        ArrayList<Article> articles = new ArrayList<Article>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = null;
            try {
                jsonObject = jsonArray.getJSONObject(i);
            }
            catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Article article = Article.fromJson(jsonObject);
            if (articles != null) {
                articles.add(article);
            }
        }
        return  articles;
    }

    public static Article fromJson(JSONObject jsonObject) {
        Article article = new Article();
        try {
            JSONArray dump = jsonObject.getJSONArray("multimedia");
            if (dump.length() > 0) {
                article.imageUrl = "http://nytimes.com/" + dump.getJSONObject(1).getString("url");
            }
            else article.imageUrl = null;
            article.headline = jsonObject.getJSONObject("headline").getString("main");

        } catch (Exception e) {

        }
        return article;
    }
}
