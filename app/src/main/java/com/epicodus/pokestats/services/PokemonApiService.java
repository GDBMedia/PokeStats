package com.epicodus.pokestats.services;



import com.epicodus.pokestats.Constants;
import com.epicodus.pokestats.models.User;
import com.scottyab.aescrypt.AESCrypt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.GeneralSecurityException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Guest on 7/26/16.
 */
public class PokemonApiService {





    public static void getData(String emailEn, String passwordEn, String location, int type, Callback callback){
        String email = "";
        try {
            email = AESCrypt.decrypt(Constants.EMAILKEY, emailEn);
        }catch (GeneralSecurityException e){
            //handle error - could be due to incorrect password or tampered encryptedMsg
        }
        String password = "";
        try {
            password = AESCrypt.decrypt(Constants.PASSKEY, passwordEn);
        }catch (GeneralSecurityException e){
            //handle error - could be due to incorrect password or tampered encryptedMsg
        }

        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        HttpUrl.Builder urlBuilder;
        if(type == 0){
            urlBuilder = HttpUrl.parse(Constants.GETUSER_URL).newBuilder();
        }else{
            urlBuilder = HttpUrl.parse(Constants.GETPOKEMON_URL).newBuilder();
        }


        urlBuilder.addQueryParameter(Constants.USERNAME_PARAM,  email)
                .addQueryParameter(Constants.PASSWORD_PARAM, password)
                .addQueryParameter(Constants.LOCATION_PARAM, location);
        String url = urlBuilder.build().toString();

        Request request= new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public static User processUser(Response response){
        User user = null;
        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject userJSON = new JSONObject(jsonData);
                String username = userJSON.getString("username");
                int pokecoin = userJSON.optInt("pokecoin", 0);
                int stardust = userJSON.optInt("stardust", 0);
                int max_item = userJSON.getInt("max_item");
                int creation_time = userJSON.getInt("creation_time");
                int teamnum = userJSON.optInt("team", 100);
                String team = "";
                switch(teamnum){
                    case 1:
                        team = "Mystic";
                        break;
                    case 2:
                        team = "Valor";
                        break;
                    case 3:
                        team = "Instinct";
                        break;
                    default:
                        team = "Noob";
                        break;
                }
                int max_pokemon = userJSON.getInt("max_pokemon");
                int revive_count = userJSON.optInt("revive_count", 0);
                int razz_count = userJSON.optInt("razz_count", 0);
                int pokemons_encountered = userJSON.optInt("pokemons_encountered", 0);
                int pokemon_deployed = userJSON.optInt("pokemon_deployed", 0);
                int battle_attack_total = userJSON.optInt("battle_attack_total", 0);
                int next_level_xp = userJSON.getInt("next_level_xp");
                int pokemons_captured = userJSON.optInt("pokemons_captured", 0);
                int battle_attack_won = userJSON.optInt("battle_attack_won", 0);
                int prestige_raised_total = userJSON.optInt("prestige_raised_total", 0);
                int pokeballs_thrown = userJSON.optInt("pokeballs_thrown", 0);
                int eggs_hatched = userJSON.optInt("eggs_hatched", 0);
                int prestige_dropped_total = userJSON.optInt("prestige_dropped_total", 0);
                int prev_level_xp = userJSON.optInt("prev_level_xp", 0);
                int unique_pokedex_entries = userJSON.optInt("unique_pokedex_entries", 0);
                int km_walked = userJSON.optInt("km_walked", 0);
                int level = userJSON.optInt("level", 0);
                int experience = userJSON.optInt("experience", 0);
                int poke_stop_visits = userJSON.optInt("poke_stop_visits", 0);
                int evolutions = userJSON.optInt("evolutions", 0);
                int pokeball_count = userJSON.optInt("pokeball_count", 0);
                int greatball_count = userJSON.optInt("greatball_count", 0);
                int potion_count = userJSON.optInt("potion_count", 0);
                int sup_potion_count = userJSON.optInt("sup_potion_count", 0);
                int hyp_potion_count = userJSON.optInt("hyp_potion_count", 0);
                int egg_count = userJSON.optInt("egg_count", 0);
                int pokemon_count = userJSON.optInt("pokemon_count", 0);

                user = new User(team, username, pokecoin, stardust, max_item, creation_time, max_pokemon, revive_count, razz_count,
                        pokemons_encountered, pokemon_deployed, battle_attack_total, next_level_xp, battle_attack_won,
                        prestige_raised_total, pokeballs_thrown, eggs_hatched, prestige_dropped_total, prev_level_xp,
                        unique_pokedex_entries, km_walked, level, experience, poke_stop_visits, evolutions, pokeball_count,
                        greatball_count, potion_count, sup_potion_count, hyp_potion_count, egg_count, pokemon_count);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }

}
