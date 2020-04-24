package com.example.android.miwok;

import android.media.MediaPlayer;
import android.net.Uri;

public class word {
    private String miwokword;
    private String englishword;
    private int audioid;
    private int imageid=-1;

    public String getMiwokTranslation(){
        return miwokword;
    }
    public word(String miwokword, String englishword, int audioid)
    {
        this.englishword=englishword;
        this.miwokword=miwokword;
        this.audioid=audioid;
    }
    public word(String miwokword,String englishword,int imageid,int audioid)
    {
        this.englishword=englishword;
        this.miwokword=miwokword;
        this.imageid=imageid;
        this.audioid=audioid;
    }
    public String getDefaultTranslation(){
        return englishword;
    }
    public int getimageid(){return imageid;}
    public int getaudioid(){return audioid;}
    public void setMiwokword(String miwokword){
        this.miwokword=miwokword;
    }
    public void setEnglishword(String englishword){
        this.englishword=englishword;
    }
}
