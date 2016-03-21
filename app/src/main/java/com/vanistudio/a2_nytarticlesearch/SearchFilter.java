package com.vanistudio.a2_nytarticlesearch;

import java.util.ArrayList;

/**
 * Created by thuynh6 on 3/20/2016.
 */
public class SearchFilter {
    private String beginDate;
    private String endDate;
    private String sort;
    private String newsDesk;

    public SearchFilter(String beginDate, String endDate, String sort, String newsDesk){
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.sort = sort;
        this.newsDesk = newsDesk;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getSortNewest() {
        return sort;
    }

    public String getNewsDesk() {
        return newsDesk;
    }
}
