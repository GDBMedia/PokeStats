package com.epicodus.pokestats.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.epicodus.pokestats.Constants;
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
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {
    public final String TAG = this.getClass().getSimpleName();
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private User mCurrentUser;
    private User mUser;
    private ProgressDialog mAuthProgressDialog;
    private StatsAdapter mAdapter;
    private final int PERMISSION_ACCESS_COARSE_LOCATION = 1;
    private String mLatitude;
    private String mLongitude;
    private String mLocation;
    private String mOrigin;
    protected LocationManager locationManager;
    protected LocationListener locationListener;

    @Bind(R.id.teamImage) ImageView mTeamImage;
    @Bind(R.id.pokemonListButton) Button mPokemonListButton;
    @Bind(R.id.stats)
    RecyclerView mRecyclerview;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;
    @Bind(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;


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

        String json = mSharedPreferences.getString(Constants.CURRENT_USER, null);
        mCurrentUser = gson.fromJson(json, User.class);

        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(MainActivity.this, R.dimen.newmarg);
        mRecyclerview.addItemDecoration(itemDecoration);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUser(mLocation);
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, PERMISSION_ACCESS_COARSE_LOCATION);
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


    }

    private void getUser(String location) {
        mAuthProgressDialog.show();
        final PokemonApiService pokemonApiService = new PokemonApiService();
        pokemonApiService.getData(mCurrentUser.getGoogleUser(), mCurrentUser.getGooglePassword(), location, 0, new Callback() {

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
                        Date date = new Date();
                        long currenttime = date.getTime();
                        double diff = currenttime - mUser.getCreation_time();
                        int diff_in_days = (int) (diff / (24*60*60*10*10*10));
                        Log.d(TAG, "run: " + diff_in_days);

                        ArrayList<Stat> stats = new ArrayList();
                            stats.add(new Stat(getString(R.string.level), mUser.getLevel()+""));
                            stats.add(new Stat(getString(R.string.pokestops), mUser.getPoke_stop_visits()+""));
                            stats.add(new Stat(getString(R.string.avg_pokestops), mUser.getPoke_stop_visits()/diff_in_days+""));
                            stats.add(new Stat(getString(R.string.pokeballs_thrown), mUser.getPokeballs_thrown()+""));
                            stats.add(new Stat(getString(R.string.pokemon_caught), mUser.getPokemons_captured()+""));
                            stats.add(new Stat(getString(R.string.avg_pokemon), mUser.getPokemons_captured()/diff_in_days+""));
                            String catchPer = Double.toString(mUser.getPokemons_captured()/(double)mUser.getPokemons_encountered()*100);
                            int clength = catchPer.length();
                            if(clength >=4){
                                clength = 4;
                            }
                            stats.add(new Stat(getString(R.string.catch_per), catchPer.substring(0,clength)+"%"));
                            stats.add(new Stat(getString(R.string.gyms_caped), mUser.getPokemon_deployed()+""));
                            String battleWinPer = Double.toString(mUser.getBattle_attack_won()/(double)mUser.getBattle_attack_total()*100);
                            int blength = battleWinPer.length();
                            if(blength >=4){
                                blength = 4;
                            }
                            stats.add(new Stat(getString(R.string.win_per), battleWinPer.substring(0,blength)+"%"));
                            stats.add(new Stat(getString(R.string.pokedex_ent), mUser.getUnique_pokedex_entries()+""));
                            String kmWalked = Double.toString(mUser.getKm_walked());
                            int klength = kmWalked.length();
                            if(klength >= 4){
                                klength = 4;
                            }

                            String kmpdWalked = Double.toString(mUser.getKm_walked()/diff_in_days);
                            int kplength = kmpdWalked.length();
                            if(kplength >= 4){
                                kplength = 4;
                            }
                            stats.add(new Stat(getString(R.string.km_walked), kmWalked.substring(0,klength)));
                            stats.add(new Stat(getString(R.string.avg_km), kmpdWalked.substring(0,kplength)));
                            stats.add(new Stat(getString(R.string.evo), mUser.getEvolutions()+""));
                            stats.add(new Stat(getString(R.string.eggs), mUser.getEgg_count()+""));
                            stats.add(new Stat(getString(R.string.pokemon), mUser.getPokemon_count()+""));
                            stats.add(new Stat(getString(R.string.startdust), mUser.getStardust()+""));
                            stats.add(new Stat(getString(R.string.pokecoin), mUser.getPokecoin()+""));
                            stats.add(new Stat(getString(R.string.eggs_hatched), mUser.getEggs_hatched()+""));
                            stats.add(new Stat(getString(R.string.pokeballs), mUser.getPokeball_count()+""));
                            stats.add(new Stat(getString(R.string.greatballs), mUser.getGreatball_count()+""));
                            stats.add(new Stat(getString(R.string.ultraballs), mUser.getUltraball_count()+""));
                            stats.add(new Stat(getString(R.string.masterballs), mUser.getMasterball_count()+""));
                            stats.add(new Stat(getString(R.string.razz), mUser.getRazz_count()+""));
                            stats.add(new Stat(getString(R.string.revive), mUser.getRevive_count()+""));

                        Log.d(TAG, "run: " + mUser.getUsername());
                        getSupportActionBar().setTitle(mUser.getUsername());
                        switch(mUser.getTeam()){
                            case "Valor":
                                mTeamImage.setImageResource(R.drawable.valor);
                                mProgressBar.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplication(), R.color.valor)));
                                break;
                            case "Mystic":
                                mTeamImage.setImageResource(R.drawable.mystic);
                                mProgressBar.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplication(), R.color.mystic)));
                                break;
                            case "Instinct":
                                mTeamImage.setImageResource(R.drawable.instinct);
                                mProgressBar.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplication(), R.color.instinct)));
                                break;
                            default:
                                mTeamImage.setImageResource(R.drawable.defaultimage);
                                mProgressBar.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplication(), R.color.noob)));
                                break;
                        }

                        Log.d(TAG, "exp: "+ mUser.getNext_level_xp() +"exp: "+ mUser.getPrev_level_xp() +"exp: "+ mUser.getExperience());

                        mProgressBar.setMax(mUser.getNext_level_xp() - Constants.LEVELS_XP.get(mUser.getLevel()));
                        Log.d(TAG, "run: " +Constants.LEVELS_XP.get(mUser.getLevel()) +"");
                        Log.d(TAG, "run: " + mUser.getExperience()/(double)mUser.getNext_level_xp());
                        mProgressBar.setProgress(mUser.getExperience() - Constants.LEVELS_XP.get(mUser.getLevel()));
                        mProgressBar.setVisibility(View.VISIBLE);
                        mAdapter = new StatsAdapter(MainActivity.this, stats);
                        mRecyclerview.setAdapter(mAdapter);
                        mAuthProgressDialog.dismiss();
                        swipeContainer.setRefreshing(false);

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
        mAuthProgressDialog.setTitle(getString(R.string.loading));
        mAuthProgressDialog.setMessage(getString(R.string.get_user_data));
        mAuthProgressDialog.setCancelable(false);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, PokemonListActivity.class);
        intent.putExtra(Constants.LOCATION, mLocation);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, Constants.UPDATE_LENGTH, Constants.UPDATE_DISTANCE, this);
            try{
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                mLongitude = location.getLongitude()+"";
                mLatitude = location.getLatitude()+"";
                mLocation = mLatitude + "," + mLongitude;
                getUser(mLocation);

            }catch (NullPointerException e){
                Log.d(TAG, "onStart: " +e);
                Toast.makeText(this, "Can't Get Location", Toast.LENGTH_SHORT).show();
            }

            Log.d(TAG, "onStart: listening");
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "onStop: notListening");
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_COARSE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // All good!
                } else {
                    Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        mLatitude = location.getLatitude()+"";
        mLongitude = location.getLongitude()+"";



        mLocation = mLatitude + "," + mLongitude;
        Log.d(TAG, "onLocationChanged: " + mLatitude + "," + mLongitude);

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
