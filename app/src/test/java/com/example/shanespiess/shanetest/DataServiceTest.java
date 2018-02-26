package com.example.shanespiess.shanetest;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Shane Spiess on 2/25/18.
 */
@RunWith(MockitoJUnitRunner.class)
public class DataServiceTest {

    @Mock
    Context mMockContext;

    @Mock
    DataService.DataServiceListener mMockListener;

    @Mock
    RequestQueue mMockRequestQueue;

    @Mock
    ImageLoader mMockImageLoader;

    Product[] testProducts;

    static DataService sDataService;

    @Before
    public void setUp() throws Exception {

        String testJson = "[\n" +
                "  {\n" +
                "    \"title\": \"TOPS STARTING AT $12\",\n" +
                "    \"backgroundImage\": \"http://anf.scene7.com/is/image/anf/anf-20160527-app-m-shirts?$anf-ios-fullwidth-3x$\",\n" +
                "    \"content\": [\n" +
                "      {\n" +
                "        \"target\": \"https://www.abercrombie.com/shop/us/mens-new-arrivals\",\n" +
                "        \"title\": \"Shop Men\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"target\": \"https://www.abercrombie.com/shop/us/womens-new-arrivals\",\n" +
                "        \"title\": \"Shop Women\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"promoMessage\": \"USE CODE: 12345\",\n" +
                "    \"topDescription\": \"A&F ESSENTIALS\",\n" +
                "    \"bottomDescription\": \"*In stores & online. <a href=\\\\\\\"http://www.abercrombie.com/anf/media/legalText/viewDetailsText20160602_Tier_Promo_US.html\\\\\\\">Exclusions apply. See Details</a>\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"title\": \"T-SHIRT DRESSES\",\n" +
                "    \"backgroundImage\": \"http://anf.scene7.com/is/image/anf/anf-US-20160601-app-women-dresses?$anf-ios-fullwidth-3x$\",\n" +
                "    \"topDescription\": \"THROW ON & GO\",\n" +
                "    \"content\": [\n" +
                "      {\n" +
                "        \"elementType\": \"hyperlink\",\n" +
                "        \"target\": \"https://www.abercrombie.com/shop/us/womens-dresses-and-rompers\",\n" +
                "        \"title\": \"SHOP NOW\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }]";
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        testProducts = gson.fromJson(testJson, Product[].class);

        sDataService = DataService.getInstance(mMockContext, mMockRequestQueue, mMockImageLoader);
        sDataService.setProducts(testProducts);
    }

    @Test
    public void fetchProducts_callListener_whenProductsRetrieved() throws Exception {
        sDataService.fetchProducts(mMockListener);
        verify(mMockListener,times(1)).onProductsLoaded(testProducts);
    }

}