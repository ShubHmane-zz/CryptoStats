package com.example.cryptostats;


import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder>{

    LayoutInflater mInflater;
    Context context;
    List<NewsModel> newsdm;


    public NewsAdapter(Context context, List<NewsModel> dm) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.newsdm = dm;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v ;
        LayoutInflater layoutInflater =  LayoutInflater.from(context);
        v = layoutInflater.inflate(R.layout.itemsview,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        holder.title.setText(newsdm.get(position).getTitle());
        holder.description.setText(newsdm.get(position).getDescription());
        holder.author.setText(newsdm.get(position).getAuthor());
        holder.publishedAt.setText(newsdm.get(position).getPublishedAt());
//        holder.name.setText(dmfilter.get(position).getName());
//        holder.price.setText(dmfilter.get(position).getPrice()+" ₹");




        RequestBuilder<PictureDrawable> requestBuilder = GlideToVectorYou
                .init()
                .with(context)
                .getRequestBuilder();

        requestBuilder
                .load(newsdm.get(position).getUrlToImage())
                .apply(new RequestOptions().override(100,100))
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(14)))
                .into(holder.logo_url1);

//.transition(DrawableTransitionOptions.withCrossFade())

//        Glide.with(context)
//                .load(newsdm.get(position).getUrlToImage())
//                .apply(new RequestOptions().override(100, 100))
//                .apply(RequestOptions.bitmapTransform(new RoundedCorners(50)))
//                .into(holder.logo_url1);

    }

    @Override
    public int getItemCount() {
        return newsdm.size();

    }



    public  static  class MyViewHolder extends RecyclerView.ViewHolder{


//        TextView name;
        TextView title;
        TextView description;
        ImageView logo_url1;
        TextView author;
        TextView publishedAt ;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.titleView);
            logo_url1 = itemView.findViewById(R.id.news_img);
//            name = itemView.findViewById(R.id.SourceView);
            author = itemView.findViewById(R.id.authorView);
            publishedAt = itemView.findViewById(R.id.dateView);
            description = itemView.findViewById(R.id.descriptionView);

        }
    }
}
