package com.epicodus.pokestats.ui;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.epicodus.pokestats.Constants;
import com.epicodus.pokestats.R;
import com.epicodus.pokestats.adapters.ItemOffsetDecoration;
import com.epicodus.pokestats.adapters.PokemonListAdapter;
import com.epicodus.pokestats.models.Pokemon;
import com.epicodus.pokestats.models.User;
import com.epicodus.pokestats.services.PokemonApiService;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PokemonListActivity extends AppCompatActivity{
    public final String TAG = this.getClass().getSimpleName();
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private User mCurrentUser;
    private ArrayList<Pokemon> mPokemons = new ArrayList<>();
    private ProgressDialog mAuthProgressDialog;
    private PokemonListAdapter mAdapter;

    @Bind(R.id.allPokemonlistView) RecyclerView mRecyclerview;
    @Bind(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    @Bind(R.id.error) TextView mError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_list);

        ButterKnife.bind(this);

        Gson gson = new Gson();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();
        createAuthProgressDialog();

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(PokemonListActivity.this, R.dimen.activity_horizontal_margin);
        mRecyclerview.addItemDecoration(itemDecoration);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new GridLayoutManager(PokemonListActivity.this, 2));

        String json = mSharedPreferences.getString(Constants.CURRENT_USER, null);
        mCurrentUser = gson.fromJson(json, User.class);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPokemon();
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

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
                Collections.sort(mPokemons, new Comparator<Pokemon>() {
                    @Override public int compare(Pokemon p1, Pokemon p2) {
                        return (p2.getIndividual_attack()+p2.getIndividual_defense()+p2.getIndividual_stamina()) - (p1.getIndividual_attack()+p1.getIndividual_defense()+p1.getIndividual_stamina());
                    }
                });
                PokemonListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listPokemon(mPokemons);


                        mAuthProgressDialog.dismiss();
                        swipeContainer.setRefreshing(false);

                    }
                });
            }
        });
    }

    private void listPokemon(ArrayList<Pokemon> pokemons) {
        mError.setVisibility(View.GONE);
        if(pokemons.size() == 0){
            mError.setVisibility(View.VISIBLE);
        }
            mAdapter = new PokemonListAdapter(PokemonListActivity.this, pokemons);
            mRecyclerview.setAdapter(mAdapter);


    }

    private void createAuthProgressDialog() {
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle(getString(R.string.loading));
        mAuthProgressDialog.setMessage(getString(R.string.dont_sue));
        mAuthProgressDialog.setCancelable(false);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.item1:
                sortByIv(mPokemons);
                return true;
            case R.id.item2:
                sortByCp(mPokemons);
                return true;
            case R.id.item3:
                sortByTime(mPokemons);
                return true;
            case R.id.item4:
                sortByNum(mPokemons);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sortByNum(ArrayList<Pokemon> pokemons) {
        Collections.sort(pokemons, new Comparator<Pokemon>() {
            @Override public int compare(Pokemon p1, Pokemon p2) {
                return (p1.getPokemon_id()) - (p2.getPokemon_id());
            }
        });
        listPokemon(pokemons);
    }

    private void sortByTime(ArrayList<Pokemon> pokemons) {
        Collections.sort(pokemons, new Comparator<Pokemon>() {
            @Override public int compare(Pokemon p1, Pokemon p2) {
                return Double.compare(p2.getCreation_time_ms(), p1.getCreation_time_ms());
            }
        });
        listPokemon(pokemons);
    }

    private void sortByCp(ArrayList<Pokemon> pokemons) {
        Collections.sort(pokemons, new Comparator<Pokemon>() {
            @Override public int compare(Pokemon p1, Pokemon p2) {
                return (p2.getCp()) - (p1.getCp());
            }
        });
        listPokemon(pokemons);
    }

    private void sortByIv(ArrayList<Pokemon> pokemons) {
        Collections.sort(pokemons, new Comparator<Pokemon>() {
            @Override public int compare(Pokemon p1, Pokemon p2) {
                return (p2.getIndividual_attack()+p2.getIndividual_defense()+p2.getIndividual_stamina()) - (p1.getIndividual_attack()+p1.getIndividual_defense()+p1.getIndividual_stamina());
            }
        });
        listPokemon(pokemons);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                searchPokemon(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "onQueryTextChange: " + newText);
                searchPokemon(newText);
                return false;
            }

        });

        return true;
    }

    private void searchPokemon(String query) {
        ArrayList<Pokemon> newPokemon = new ArrayList<>();
        for(Pokemon pokemon : mPokemons){
            if(pokemon.getName().toLowerCase().contains(query.toLowerCase())){
                newPokemon.add(pokemon);
            }
        }
        listPokemon(newPokemon);
    }
}
