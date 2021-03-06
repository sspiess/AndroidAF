package com.example.shanespiess.shanetest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by Shane Spiess on 2/24/18.
 */

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ViewHolder> {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final List<Product> mProducts;
    private final ImageLoader mImageLoader;

    public ProductRecyclerAdapter(Context mContext, List<Product> mProducts) {
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.mProducts = mProducts;
        this.mImageLoader = DataService.getInstance(mContext).getImageLoader();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_product_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = mProducts.get(position);
        if (product.bottomDescription != null) {
            holder.mTextBottomDescription.setText(Html.fromHtml(product.bottomDescription));
        }

        holder.mTextPromoMessage.setText(product.promoMessage);
        holder.mTextTitle.setText(product.title);
        holder.mTextTopDescription.setText(product.topDescription);
        holder.mImageBackground.setImageUrl(product.backgroundImage, mImageLoader);
        holder.mContentLayout.removeAllViews();
        if (product.content != null ) {
            for (ContentItem contentItem : product.content) {
                AppCompatButton button = createNewButton(contentItem);
                holder.mContentLayout.addView(button);
            }
        }
    }

    private AppCompatButton createNewButton(final ContentItem contentItem) {
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        final AppCompatButton button = new AppCompatButton(mContext);
        button.setLayoutParams(lparams);
        button.setBackgroundTintMode(PorterDuff.Mode.CLEAR);
        button.setId(View.generateViewId());
        button.setTextSize(15);
        button.setText(contentItem.title);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Test", contentItem.target);
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra(WebActivity.WEB_URL, contentItem.target);
                mContext.startActivity(intent);
            }
        });

        return button;
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final TextView mTextTopDescription;
        public final TextView mTextTitle;
        public final TextView mTextBottomDescription;
        public final TextView mTextPromoMessage;
        public final NetworkImageView mImageBackground;
        public final LinearLayout mContentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextTopDescription = (TextView) itemView.findViewById(R.id.text_top_description);
            mTextTitle = (TextView) itemView.findViewById(R.id.text_title);
            mTextBottomDescription = (TextView) itemView.findViewById(R.id.text_bottom_description);
            mTextPromoMessage = (TextView) itemView.findViewById(R.id.text_promo_message);
            mImageBackground = (NetworkImageView) itemView.findViewById(R.id.image_background);
            mContentLayout = (LinearLayout) itemView.findViewById(R.id.layout_content);
        }
    }
}
