package com.example.gnl;

public class newslist {
    private String mTitle;
    private String mCategory;
    private String mDate;
    private String mUrl;
    private String mType;

    public newslist(String titlenews, String newscategory, String newsDate, String urlnews, String newstype) {
        mTitle = titlenews;
        mCategory = newscategory;
        mDate = newsDate;
        mUrl = urlnews;
        mType = newstype;

    }

    public String gettitlefornews() {
        return mTitle;
    }

    public String getcategoryfornews() {
        return mCategory;
    }

    public String getdatefornews() {
        return mDate;
    }

    public String geturlfornews() {
        return mUrl;
    }

    public String gettypefornews() {
        return mType;
    }


}