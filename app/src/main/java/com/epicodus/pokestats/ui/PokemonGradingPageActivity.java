package com.epicodus.pokestats.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.epicodus.pokestats.R;
import com.epicodus.pokestats.models.Pokemon;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PokemonGradingPageActivity extends AppCompatActivity {
    public final String TAG = this.getClass().getSimpleName();


    @Bind(R.id.name) TextView pokename;
    private ArrayList<Pokemon> mPokemonArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_grading_page);
        ButterKnife.bind(this);
        if (getIntent().getExtras() != null) {
            mPokemonArrayList = getIntent().getExtras().getParcelable("pokemons");
            Log.d(TAG, "onCreate: "+ mPokemonArrayList.getClass());
            Log.d(TAG, "onCreate: "+mPokemonArrayList.get(Integer.parseInt(getIntent().getStringExtra("position"))));

        }

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
