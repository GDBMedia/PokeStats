package com.epicodus.pokestats.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.epicodus.pokestats.R;
import com.epicodus.pokestats.models.User;
import com.epicodus.pokestats.services.PokemonApiService;
import com.google.gson.Gson;

import java.io.IOException;

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
    @Bind(R.id.teamImage) ImageView mTeamImage;
    @Bind(R.id.pokemonListButton) Button mPokemonListButton;


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

                        Log.d(TAG, "run: " + mUser.getUsername());
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
                                mTeamImage.setImageResource(R.drawable.temp_photo);
                                break;
                        }
                        mAuthProgressDialog.dismiss();

                    }
                });
            }
        });
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
