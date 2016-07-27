package com.epicodus.pokestats.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.epicodus.pokestats.models.Pokemon;
import com.epicodus.pokestats.ui.PokemonGradingFragment;

import java.util.ArrayList;

/**
 * Created by Guest on 7/27/16.
 */
public class PokemonPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Pokemon> mPokemons;

    public PokemonPagerAdapter(FragmentManager fm, ArrayList<Pokemon> pokemon) {
        super(fm);
        mPokemons = pokemon;
    }

    @Override
    public Fragment getItem(int position) {
        return PokemonGradingFragment.newInstance(mPokemons.get(position));
    }

    @Override
    public int getCount() {
        return mPokemons.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mPokemons.get(position).getName();
    }
}
