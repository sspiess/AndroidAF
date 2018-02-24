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
    private Product[] mProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        if(savedInstanceState == null) {
            DataService.getInstance(this).fetchProducts(new DataService.DataServiceListener() {
                @Override
                public void onProductsLoaded(Product[] products) {
                    mProducts = products;
                    initializeDisplayContent();
                }
            });
        } else {
            mProducts = (Product[]) savedInstanceState.getParcelableArray(EXPLORE_DATA);
            initializeDisplayContent();
        }

    }

    private void initializeDisplayContent() {
        final RecyclerView recyclerProducts = (RecyclerView) findViewById(R.id.list_products);
        final LinearLayoutManager productsLayoutManager = new LinearLayoutManager(this);
        recyclerProducts.setLayoutManager(productsLayoutManager);

        final ProductRecyclerAdapter productRecyclerAdapter =
                new ProductRecyclerAdapter(this, Arrays.asList(mProducts));

        recyclerProducts.setAdapter(productRecyclerAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArray(EXPLORE_DATA, mProducts);
    }

}
