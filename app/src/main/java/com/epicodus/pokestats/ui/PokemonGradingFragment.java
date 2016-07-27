package com.epicodus.pokestats.ui;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.epicodus.pokestats.R;
import com.epicodus.pokestats.adapters.ItemOffsetDecoration;
import com.epicodus.pokestats.adapters.StatsAdapter;
import com.epicodus.pokestats.models.Pokemon;
import com.epicodus.pokestats.models.Stat;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PokemonGradingFragment extends Fragment {
    private Pokemon mPokemon;

    @Bind(R.id.name) TextView pokeName;
    @Bind(R.id.cp) TextView pokeCp;
    @Bind(R.id.sprite)
    ImageView pokeSprite;
    public final String TAG = this.getClass().getSimpleName();
    private StatsAdapter mAdapter;
    @Bind(R.id.stats)
    RecyclerView mRecyclerview;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;

    public static PokemonGradingFragment newInstance(Pokemon pokemon) {
        PokemonGradingFragment pokemonGradingFragment = new PokemonGradingFragment();
        Bundle args = new Bundle();
        args.putParcelable("pokemon", Parcels.wrap(pokemon));
        pokemonGradingFragment.setArguments(args);
        return pokemonGradingFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPokemon = Parcels.unwrap(getArguments().getParcelable("pokemon"));
    }


    public PokemonGradingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokemon_gradingpage, container, false);
        ButterKnife.bind(this, view);

        String name = mPokemon.getName();
        if(mPokemon.getNickname() != null){
            name = name + " (" + mPokemon.getNickname()+ ")";
        }
        pokeName.setText(name);
        pokeCp.setText("CP " + mPokemon.getCp());
        Picasso.with(getActivity()).load(mPokemon.getSprite()).into(pokeSprite);
        mProgressBar.setMax(mPokemon.getStamina_max());
        mProgressBar.setProgress(mPokemon.getStamina());

        ArrayList<Stat> stats = new ArrayList<>();
        stats.add(new Stat("Attack", mPokemon.getIndividual_attack()+""));
        stats.add(new Stat("Defense", mPokemon.getIndividual_defense()+""));
        stats.add(new Stat("Stamina", mPokemon.getIndividual_stamina()+""));

        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mAdapter = new StatsAdapter(getActivity(), stats);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.activity_horizontal_margin);
        mRecyclerview.addItemDecoration(itemDecoration);
        mRecyclerview.setAdapter(mAdapter);


        return view;
    }
}
