package com.parse.starter;

import android.app.LauncherActivity;
import android.content.Context;

import java.util.List;

public class post
{
    private String mUsername;
    private String mCaption;
    private String mObjectId;

    public post(String username,String caption,String objectId) {
        this.mUsername = username;
        this.mCaption = caption;
        this.mObjectId = objectId;
    }

    public String getUsername()
    {
        return mUsername;
    }

    public String getCaption()
    {
        return mCaption;
    }

    public String getObjectId()
    {
        return mObjectId;
    }

}
