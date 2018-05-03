package com.example.tentsering.googlebookreloaded;

public class Ebooks {
    private String mTitle;
    private String mAuthor;
    private String mBookLink;
    private String mThumbnail;

    public Ebooks(String mTitle, String mAuthor, String mBookLink, String mThumbnail){
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mBookLink = mBookLink;
        this.mThumbnail = mThumbnail;
    }

    public String getmAuthor(){
        return mAuthor;
    }

    public String getmTitle(){
        return mTitle;
    }

    public String getmBookLink(){
        return mBookLink;
    }

    public String getmThumbnail(){
        return mThumbnail;
    }
}