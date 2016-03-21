package com.vanistudio.a2_nytarticlesearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by thuynh6 on 3/19/2016.
 */
public class gvArticleListAdapter extends ArrayAdapter<Article> {

    private static class ViewHolder {
        public ImageView ivImage;
        public TextView tvHeadline;
    }


    public gvArticleListAdapter(Context context, ArrayList<Article> articles) {
        super(context,0, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        // Get the data item for this position
        final Article article = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        //ViewHolder viewHolder; // view lookup cache stored in tag
        //if (convertView == null) {
            //viewHolder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.article, parent, false);
            ImageView ivImage = (ImageView)v.findViewById(R.id.ivImage);
            TextView tvHeadline = (TextView)v.findViewById(R.id.tvHeadline);
            //convertView.setTag(viewHolder);
        //} else {
            //viewHolder = (ViewHolder) convertView.getTag();
        //}
        // Populate data into the template view using the data object
        tvHeadline.setText(article.getHeadline());
        if (article.getImageUrl() != null) {
            Picasso.with(getContext()).load(article.getImageUrl()).into(ivImage);
        }
        else ivImage.setImageResource(R.mipmap.ic_launcher);
        // Return the completed view to render on screen
        return v;
    }

}
