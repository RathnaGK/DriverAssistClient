package com.innominds.driverassist.repositories;

import android.content.Context;

public class BaseRepository {
    protected Context mContext;
    public BaseRepository(Context context)
    {
        mContext=context;
    }
}
