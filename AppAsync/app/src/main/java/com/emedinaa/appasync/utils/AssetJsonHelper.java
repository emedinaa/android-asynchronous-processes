package com.emedinaa.appasync.utils;

import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by emedinaa on 5/08/17.
 */

public class AssetJsonHelper {

    private final AssetManager assetManager;

    public AssetJsonHelper(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public <T>T convertObjectToJsonAssets(String jsonFileName, Class<T> cls)
            throws Exception{

        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();

        T object= gson.fromJson(loadJSONFromFileAsset(jsonFileName), cls);
        return object;
    }

    public String loadJSONFromFileAsset(String filename) {
        String json = null;
        try {
            InputStream is = assetManager.open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
