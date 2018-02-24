package com.example.shanespiess.shanetest;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Shane Spiess on 2/24/18.
 */

public class DataService {

    private static final String ENDPOINT = "https://www.abercrombie.com/anf/nativeapp/qa/codetest/codeTest_exploreData.json";
    private static DataService mInstance;
    private Context mContext;
    private final ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;
    private Gson mGson;
    private Product[] mProducts;
    private DataServiceListener mDataServiceListener;

    private DataService(Context context) {
        mContext = context;
        mRequestQueue = mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());

        GsonBuilder gsonBuilder = new GsonBuilder();
        mGson = gsonBuilder.create();

        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {

            private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(20);
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public interface DataServiceListener {

        public void onProductsLoaded(Product[] products);
    }

    public static DataService getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DataService(context);
        }

        return mInstance;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public void fetchProducts(DataServiceListener listener) {
        this.mDataServiceListener = listener;
        if (mProducts == null) {
            StringRequest request = new StringRequest(Request.Method.GET, ENDPOINT, onProductsLoaded, onProductsError);
            mRequestQueue.add(request);
        } else if (listener != null) {
                listener.onProductsLoaded(mProducts);
        }
    }

    private final Response.Listener<String> onProductsLoaded = new Response.Listener<String>() {
        public Object products;

        @Override
        public void onResponse(String response) {
            Log.i("ProductsActivity", response);
            List<Product> products = Arrays.asList(mGson.fromJson(response, Product[].class));
            Log.i("ProductsActivity", products.size() + " total loaded");
            mProducts = (Product[]) products.toArray();
            if (mDataServiceListener != null) {
                mDataServiceListener.onProductsLoaded(mProducts);
            }
        }
    };

    private final Response.ErrorListener onProductsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("ProductsActivity", error.toString());
        }
    };
}
