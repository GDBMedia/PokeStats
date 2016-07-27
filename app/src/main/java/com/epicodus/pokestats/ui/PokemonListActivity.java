package com.epicodus.pokestats.ui;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.epicodus.pokestats.R;
import com.epicodus.pokestats.adapters.PokemonListAdapter;
import com.epicodus.pokestats.models.Pokemon;
import com.epicodus.pokestats.models.User;
import com.epicodus.pokestats.services.PokemonApiService;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PokemonListActivity extends AppCompatActivity {
    public final String TAG = this.getClass().getSimpleName();
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private User mCurrentUser;
    private ArrayList<Pokemon> mPokemons = new ArrayList<>();
    private ProgressDialog mAuthProgressDialog;
    private PokemonListAdapter mAdapter;

    @Bind(R.id.allPokemonlistView)
    RecyclerView mRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_list);

        ButterKnife.bind(this);

        Gson gson = new Gson();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();
        createAuthProgressDialog();

        String json = mSharedPreferences.getString("currentUser", null);
        mCurrentUser = gson.fromJson(json, User.class);
        getPokemon();

    }

    private void getPokemon() {
        mAuthProgressDialog.show();
        final PokemonApiService pokemonApiService = new PokemonApiService();
        pokemonApiService.getData(mCurrentUser.getGoogleUser(), mCurrentUser.getGooglePassword(), "portland", 1, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response){
                mPokemons = pokemonApiService.processPokemon(response);
                PokemonListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        for(Pokemon pokemon : mPokemons){
                            Log.d(TAG, "run: " + pokemon.getName());
                        }
                        mRecyclerview.setHasFixedSize(true);
                        mRecyclerview.setLayoutManager(new GridLayoutManager(PokemonListActivity.this, 2));
                        mAdapter = new PokemonListAdapter(PokemonListActivity.this, mPokemons);
                        mRecyclerview.setAdapter(mAdapter);

                        mAuthProgressDialog.dismiss();

                    }
                });
            }
        });
    }
    private void createAuthProgressDialog() {
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading...");
        mAuthProgressDialog.setMessage("Getting Pokemon Data... Please Don't Sue still...");
        mAuthProgressDialog.setCancelable(false);
    }
}
