package com.rey.tutorial;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.squareup.picasso.Picasso;

/**
 * Created by Rey on 6/10/2015.
 */
public class TestApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        Picasso.with(this)
                .setIndicatorsEnabled(true);
        Picasso.with(this)
                .setLoggingEnabled(true);

        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .build();

        Fresco.initialize(this, config);
    }
}
