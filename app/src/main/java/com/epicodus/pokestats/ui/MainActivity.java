package com.epicodus.pokestats.ui;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.ImageView;

import com.epicodus.pokestats.Constants;
import com.epicodus.pokestats.R;
import com.epicodus.pokestats.models.User;
import com.epicodus.pokestats.services.PokemonApiService;
import com.google.gson.Gson;
import com.scottyab.aescrypt.AESCrypt;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.crypto.SecretKey;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public final String TAG = this.getClass().getSimpleName();
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private User mCurrentUser;
    private User mUser;
    private ProgressDialog mAuthProgressDialog;
    @Bind(R.id.teamImage) ImageView mTeamImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

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
        pokemonApiService.getUser(mCurrentUser.getGoogleUser(), mCurrentUser.getGooglePassword(), "portland", new Callback() {

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

}
