package com.geekathlon.shailshah.faceemoji.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by shailshah on 10/27/17.
 */

@Module
public class MainActivityModule {
    private final Context context;

    public MainActivityModule (Context context) {
        this.context = context;
    }

    @Provides //scope is not necessary for parameters stored within the module
    public Context context() {
        return context;
    }
}

