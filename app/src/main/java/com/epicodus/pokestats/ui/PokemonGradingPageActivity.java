package com.epicodus.pokestats.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.pokestats.Constants;
import com.epicodus.pokestats.R;
import com.epicodus.pokestats.adapters.PokemonPagerAdapter;
import com.epicodus.pokestats.models.Pokemon;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PokemonGradingPageActivity extends AppCompatActivity {

    @Bind(R.id.viewPager) ViewPager mViewPager;
    private PokemonPagerAdapter mPokemonPagerAdapter;
    ArrayList<Pokemon> mPokemons = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_grading_page);

        ButterKnife.bind(this);

        mPokemons = Parcels.unwrap(getIntent().getParcelableExtra(Constants.POKEMONS));
        int startingPosition = Integer.parseInt(getIntent().getStringExtra(Constants.POSITION));

        mPokemonPagerAdapter = new PokemonPagerAdapter(getSupportFragmentManager(), mPokemons);
        mViewPager.setAdapter(mPokemonPagerAdapter);
        mViewPager.setCurrentItem(startingPosition);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
