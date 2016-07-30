package com.epicodus.pokestats;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

/**
 * Created by Guest on 7/26/16.
 */
public class Constants {
    public static final String PASSKEY = BuildConfig.PASSKEY;
    public static final String EMAILKEY = BuildConfig.EMAILKEY;
    public static final String GETUSER_URL = "https://shielded-citadel-24301.herokuapp.com/getuserinfo/";
    public static final String GETPOKEMON_URL = "https://shielded-citadel-24301.herokuapp.com/getpokemon/";
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

    public static final Map<Integer, Integer> LEVELS_XP;

    static{
        HashMap<Integer, Integer> levels_xp = new HashMap<>();
        levels_xp.put(1, 0);
        levels_xp.put(2, 1000);
        levels_xp.put(3, 3000);
        levels_xp.put(4, 6000);
        levels_xp.put(5, 10000);
        levels_xp.put(6, 15000);
        levels_xp.put(7, 21000);
        levels_xp.put(8, 28000);
        levels_xp.put(9, 36000);
        levels_xp.put(10, 45000);
        levels_xp.put(11, 55000);
        levels_xp.put(12, 65000);
        levels_xp.put(13, 75000);
        levels_xp.put(14, 85000);
        levels_xp.put(15, 100000);
        levels_xp.put(16, 120000);
        levels_xp.put(17, 140000);
        levels_xp.put(18, 160000);
        levels_xp.put(19, 185000);
        levels_xp.put(20, 210000);
        levels_xp.put(21, 260000);
        levels_xp.put(22, 335000);
        levels_xp.put(23, 435000);
        levels_xp.put(24, 560000);
        levels_xp.put(25, 710000);
        levels_xp.put(26, 900000);
        levels_xp.put(27, 1100000);
        levels_xp.put(28, 1350000);
        levels_xp.put(29, 1650000);
        levels_xp.put(30, 2000000);
        levels_xp.put(31, 2500000);
        levels_xp.put(32, 3000000);
        levels_xp.put(33, 3750000);
        levels_xp.put(34, 4750000);
        levels_xp.put(35, 6000000);
        levels_xp.put(36, 7500000);
        levels_xp.put(37, 9500000);
        levels_xp.put(38, 12000000);
        levels_xp.put(39, 15000000);
        levels_xp.put(40, 20000000);
        LEVELS_XP = Collections.unmodifiableMap(levels_xp);
    }



}
