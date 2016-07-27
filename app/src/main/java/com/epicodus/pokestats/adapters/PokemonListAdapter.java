package com.epicodus.pokestats.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.pokestats.R;
import com.epicodus.pokestats.models.Pokemon;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guest on 7/26/16.
 */
public class PokemonListAdapter extends RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder> {
    private ArrayList<Pokemon> mPokemonArrayList = new ArrayList<>();
    private Context mContext;
    private String mOrigin;
    private DatabaseReference mDmvDatabase;

    public PokemonListAdapter(Context context, ArrayList<Pokemon> pokemonArrayList) {
        mContext = context;
        mPokemonArrayList = pokemonArrayList;
    }

    @Override
    public PokemonListAdapter.PokemonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_grid_view, parent, false);
        PokemonViewHolder viewHolder = new PokemonViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PokemonListAdapter.PokemonViewHolder holder, int position) {
        holder.bindPokemon(mPokemonArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return mPokemonArrayList.size();
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.cv) CardView cv;
        @Bind(R.id.name) TextView pokeName;
        @Bind(R.id.cp) TextView pokeCp;
        @Bind(R.id.sprite) ImageView pokeSprite;
        private Context mContext;

        public PokemonViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindPokemon(Pokemon pokemon) {
            String name = pokemon.getName();
            if(pokemon.getNickname() != null){
                name = name + " (" + pokemon.getNickname()+ ")";
            }
            pokeName.setText(name);
            pokeCp.setText("CP " + pokemon.getCp());
            Picasso.with(mContext).load(pokemon.getSprite()).into(pokeSprite);

        }

        @Override
        public void onClick(View v) {
//            int itemPosition = getLayoutPosition();
//            Intent intent = new Intent(v.getContext(), UpdateDmv.class);
//            intent.putExtra("position", itemPosition + "");
//            intent.putExtra("dmvs", Parcels.wrap(mPokemonArrayList));
//            intent.putExtra("origin", mOrigin);
//            mContext.startActivity(intent);
        }
    }
}
