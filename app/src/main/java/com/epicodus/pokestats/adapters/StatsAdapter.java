package com.epicodus.pokestats.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.pokestats.R;
import com.epicodus.pokestats.models.Pokemon;
import com.epicodus.pokestats.models.Stat;
import com.epicodus.pokestats.ui.PokemonGradingPageActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guest on 7/26/16.
 */
public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.StatsViewHolder> {
    private ArrayList<Stat> mStatArrayList = new ArrayList<>();
    private Context mContext;


    public StatsAdapter(Context context, ArrayList<Stat> statArrayList) {
        mContext = context;
        mStatArrayList = statArrayList;
    }

    @Override
    public StatsAdapter.StatsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stats_item, parent, false);
        StatsViewHolder viewHolder = new StatsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StatsAdapter.StatsViewHolder holder, int position) {
        holder.bindStat(mStatArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return mStatArrayList.size();
    }

    public class StatsViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.cv) CardView cv;
        @Bind(R.id.statname) TextView mStateName;
        @Bind(R.id.stat) TextView mStat;
        private Context mContext;

        public StatsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mContext = itemView.getContext();
        }

        public void bindStat(Stat stat) {
            mStateName.setText(stat.getStatname());
            mStat.setText(stat.getStat());

        }

    }
}

