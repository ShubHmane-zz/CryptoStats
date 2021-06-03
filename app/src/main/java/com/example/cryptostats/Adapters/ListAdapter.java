package com.example.cryptostats.Adapters;


import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.cryptostats.Model.Home.DataModel;
import com.example.cryptostats.R;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;

import java.util.ArrayList;
import java.util.List;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> implements Filterable {

    LayoutInflater mInflater;
    Context context;
    List<DataModel> dm;
    List<DataModel> dmfilter;

    public ListAdapter(Context context, List<DataModel> dm) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.dm = dm;
        this.dmfilter=new ArrayList<>(dm);

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




        holder.currency.setText(dmfilter.get(position).getCurrency());
        holder.name.setText(dmfilter.get(position).getName());
        holder.price.setText(dmfilter.get(position).getPrice()+" ₹");


        RequestBuilder<PictureDrawable> requestBuilder = GlideToVectorYou
                .init()
                .with(context)
                .getRequestBuilder();

        requestBuilder
                .load(dmfilter.get(position).getLogo_url())
                .apply(new RequestOptions().override(100,100))
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(14)))
                .into(holder.logo_url);

//.transition(DrawableTransitionOptions.withCrossFade())

        Glide.with(context)
                .load(dmfilter.get(position).getLogo_url())
                .apply(new RequestOptions().override(100, 100))
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(50)))
                .into(holder.logo_url);

    }

    @Override
    public int getItemCount() {
        return dmfilter.size();

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String character = constraint.toString();
                if(character.isEmpty()){
                    dmfilter = dm;
                }

                else {
                    List<DataModel> filterlist = new ArrayList<>();

                    for (DataModel dataModel :dm){
                        if(dataModel.getName().toLowerCase().contains(character.toLowerCase())){
                            filterlist.add(dataModel);
                        }

                    }

                    dmfilter = filterlist;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = dmfilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {


                dmfilter = (ArrayList<DataModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    public  static  class MyViewHolder extends RecyclerView.ViewHolder{
        TextView currency;
        ImageView logo_url;
        TextView name;
        TextView price ;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            currency = itemView.findViewById(R.id.name);
            logo_url = itemView.findViewById(R.id.logo);
            name = itemView.findViewById(R.id.full_name);
            price = itemView.findViewById(R.id.price);


        }
    }
}
