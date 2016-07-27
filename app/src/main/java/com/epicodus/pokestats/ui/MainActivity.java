package com.epicodus.pokestats.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.epicodus.pokestats.R;
import com.epicodus.pokestats.adapters.ItemOffsetDecoration;
import com.epicodus.pokestats.adapters.PokemonListAdapter;
import com.epicodus.pokestats.adapters.StatsAdapter;
import com.epicodus.pokestats.models.Stat;
import com.epicodus.pokestats.models.User;
import com.epicodus.pokestats.services.PokemonApiService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public final String TAG = this.getClass().getSimpleName();
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private User mCurrentUser;
    private User mUser;
    private ProgressDialog mAuthProgressDialog;
    private StatsAdapter mAdapter;

    @Bind(R.id.teamImage) ImageView mTeamImage;
    @Bind(R.id.pokemonListButton) Button mPokemonListButton;
    @Bind(R.id.stats)
    RecyclerView mRecyclerview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        mPokemonListButton.setOnClickListener(this);
        Gson gson = new Gson();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();
        createAuthProgressDialog();

        String json = mSharedPreferences.getString("currentUser", null);
        mCurrentUser = gson.fromJson(json, User.class);
        getUser();

    }

    private void getUser() {
        mAuthProgressDialog.show();
        final PokemonApiService pokemonApiService = new PokemonApiService();
        pokemonApiService.getData(mCurrentUser.getGoogleUser(), mCurrentUser.getGooglePassword(), "portland", 0, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response){
                mUser = pokemonApiService.processUser(response);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        ArrayList<Stat> stats = new ArrayList<Stat>();
                            stats.add(new Stat("Level", mUser.getLevel()+""));
                            stats.add(new Stat("Pokestops Visited", mUser.getPoke_stop_visits()+""));
                            stats.add(new Stat("Pokeballs Thrown", mUser.getPokeballs_thrown()+""));
                            String catchPer = Double.toString(mUser.getPokemons_captured()/(double)mUser.getPokemons_encountered()*100);
                            int clength = catchPer.length();
                            if(clength >=4){
                                clength = 4;
                            }
                            stats.add(new Stat("Catch %", catchPer.substring(0,clength)+"%"));
                            stats.add(new Stat("Gym's Captured", mUser.getPokemon_deployed()+""));
                            String battleWinPer = Double.toString(mUser.getBattle_attack_won()/(double)mUser.getBattle_attack_total()*100);
                            int blength = battleWinPer.length();
                            if(blength >=4){
                                blength = 4;
                            }
                            stats.add(new Stat("Battle Win %", battleWinPer.substring(0,blength)+"%"));
                            stats.add(new Stat("Pokedex Entries", mUser.getUnique_pokedex_entries()+""));
                            String kmWalked = Double.toString(mUser.getKm_walked());
                        Log.d(TAG, "run: " + kmWalked);
                            int klength = kmWalked.length();
                            if(klength >= 4){
                                klength = 4;
                            }
                            stats.add(new Stat("KM Walked", kmWalked.substring(0,klength)));
                            stats.add(new Stat("Evolutions", mUser.getEvolutions()+""));
                            stats.add(new Stat("Eggs", mUser.getEgg_count()+""));
                            stats.add(new Stat("Pokemon", mUser.getPokemon_count()+""));



                        Log.d(TAG, "run: " + mUser.getUsername());
                        getSupportActionBar().setTitle(mUser.getUsername());
                        switch(mUser.getTeam()){
                            case "Valor":
                                mTeamImage.setImageResource(R.drawable.valor);
                                break;
                            case "Mystic":
                                mTeamImage.setImageResource(R.drawable.mystic);
                                break;
                            case "Instinct":
                                mTeamImage.setImageResource(R.drawable.instinct);
                                break;
                            default:
                                mTeamImage.setImageResource(R.drawable.defaultimage);
                                break;
                        }
                        mRecyclerview.setHasFixedSize(true);
                        mRecyclerview.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
                        mAdapter = new StatsAdapter(MainActivity.this, stats);
                        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(MainActivity.this, R.dimen.activity_horizontal_margin);
                        mRecyclerview.addItemDecoration(itemDecoration);
                        mRecyclerview.setAdapter(mAdapter);
                        mAuthProgressDialog.dismiss();

                    }
                });
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    private void createAuthProgressDialog() {
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading...");
        mAuthProgressDialog.setMessage("Getting User Data... Please Don't Sue...");
        mAuthProgressDialog.setCancelable(false);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, PokemonListActivity.class);
        startActivity(intent);
    }
}
