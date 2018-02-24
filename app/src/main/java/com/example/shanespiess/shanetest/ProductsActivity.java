package com.example.shanespiess.shanetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {

    public static final String EXPLORE_DATA = "com.example.shanespiess.shanetest.EXPLORE_DATA";
    private static final String ENDPOINT = "https://www.abercrombie.com/anf/nativeapp/qa/codetest/codeTest_exploreData.json";

    private RequestQueue mRequestQueue;
    private Gson mGson;
    private Product[] mProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        mRequestQueue = Volley.newRequestQueue(this);

        GsonBuilder gsonBuilder = new GsonBuilder();
        mGson = gsonBuilder.create();

        if(savedInstanceState == null) {
            fetchProducts();
        } else {
            mProducts = (Product[]) savedInstanceState.getParcelableArray(EXPLORE_DATA);
        }

        initializeDisplayContent();
    }

    private void initializeDisplayContent() {

        final RecyclerView recyclerProducts = (RecyclerView) findViewById(R.id.list_products);
        final LinearLayoutManager productsLayoutManager = new LinearLayoutManager(this);
        recyclerProducts.setLayoutManager(productsLayoutManager);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArray(EXPLORE_DATA, mProducts);
    }

    private void fetchProducts() {
        StringRequest request = new StringRequest(Request.Method.GET, ENDPOINT, onProductsLoaded, onProductsError);
        mRequestQueue.add(request);
    }

    private final Response.Listener<String> onProductsLoaded = new Response.Listener<String>() {
        public Object products;

        @Override
        public void onResponse(String response) {
            Log.i("ProductsActivity", response);
            List<Product> products = Arrays.asList(mGson.fromJson(response, Product[].class));
            Log.i("ProductsActivity", products.size() + " total loaded");
            mProducts = (Product[]) products.toArray();
        }
    };

    private final Response.ErrorListener onProductsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("ProductsActivity", error.toString());
        }
    };
}
