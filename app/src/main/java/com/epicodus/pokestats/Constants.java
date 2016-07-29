package com.epicodus.pokestats;

import javax.crypto.SecretKey;

/**
 * Created by Guest on 7/26/16.
 */
public class Constants {
    public static final String PASSKEY = BuildConfig.PASSKEY;
    public static final String EMAILKEY = BuildConfig.EMAILKEY;
    public static final String GETUSER_URL = "http://gdbmedia.ngrok.io/getuserinfo/";
    public static final String GETPOKEMON_URL = "http://gdbmedia.ngrok.io/getpokemon/";
    public static final String USERNAME_PARAM = "u";
    public static final String PASSWORD_PARAM = "p";
    public static final String LOCATION_PARAM = "l";
    public static final String CURRENT_USER = "currentUser";
    public static final String USERS = "users";
    public static final String POKEMON = "pokemon";
    public static final String POKEMONS = "pokemons";
    public static final String POSITION = "position";
    public static final String LOCATION = "location";
    public static final int UPDATE_LENGTH = 300000;
    public static final int UPDATE_DISTANCE = 15;


}
