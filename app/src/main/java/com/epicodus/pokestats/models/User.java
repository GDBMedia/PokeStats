package com.epicodus.pokestats.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import org.parceler.Parcel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Guest on 7/26/16.
 */
@IgnoreExtraProperties
@Parcel
public class User {




    public String team;
    public String username;
    public String googleUser;
    public String googlePassword;
    public int pokecoin;
    public int stardust;
    public int max_item;
    public int creation_time;
    public int max_pokemon;
    public int revive_count;
    public int razz_count;
    public int pokemons_encountered;
    public int pokemon_deployed;
    public int battle_attack_total;
    public int next_level_xp;
    public int battle_attack_won;
    public int prestige_raised_total;
    public int pokeballs_thrown;
    public int eggs_hatched;
    public int prestige_dropped_total;
    public int prev_level_xp;
    public int unique_pokedex_entries;
    public int km_walked;
    public int level;
    public int experience;
    public int poke_stop_visits;
    public int evolutions;
    public int pokeball_count;
    public int greatball_count;
    public int potion_count;
    public int sup_potion_count;
    public int hyp_potion_count;
    public int egg_count;
    public int pokemon_count;

    public User() {
    }

    public User(String googleUser, String googlePassword) {
        this.googleUser = googleUser;
        this.googlePassword = googlePassword;
    }

    public User(String team, String username, int pokecoin,
                int stardust, int max_item, int creation_time, int max_pokemon, int revive_count, int razz_count,
                int pokemons_encountered, int pokemon_deployed, int battle_attack_total, int next_level_xp, int battle_attack_won,
                int prestige_raised_total, int pokeballs_thrown, int eggs_hatched, int prestige_dropped_total, int prev_level_xp,
                int unique_pokedex_entries, int km_walked, int level, int experience, int poke_stop_visits, int evolutions, int pokeball_count,
                int greatball_count, int potion_count, int sup_potion_count, int hyp_potion_count, int egg_count, int pokemon_count) {
        this.team = team;
        this.username = username;
        this.pokecoin = pokecoin;
        this.stardust = stardust;
        this.max_item = max_item;
        this.creation_time = creation_time;
        this.max_pokemon = max_pokemon;
        this.revive_count = revive_count;
        this.razz_count = razz_count;
        this.pokemons_encountered = pokemons_encountered;
        this.pokemon_deployed = pokemon_deployed;
        this.battle_attack_total = battle_attack_total;
        this.next_level_xp = next_level_xp;
        this.battle_attack_won = battle_attack_won;
        this.prestige_raised_total = prestige_raised_total;
        this.pokeballs_thrown = pokeballs_thrown;
        this.eggs_hatched = eggs_hatched;
        this.prestige_dropped_total = prestige_dropped_total;
        this.prev_level_xp = prev_level_xp;
        this.unique_pokedex_entries = unique_pokedex_entries;
        this.km_walked = km_walked;
        this.level = level;
        this.experience = experience;
        this.poke_stop_visits = poke_stop_visits;
        this.evolutions = evolutions;
        this.pokeball_count = pokeball_count;
        this.greatball_count = greatball_count;
        this.potion_count = potion_count;
        this.sup_potion_count = sup_potion_count;
        this.hyp_potion_count = hyp_potion_count;
        this.egg_count = egg_count;
        this.pokemon_count = pokemon_count;
    }

    public String getTeam() {
        return team;
    }

    public String getUsername() {
        return username;
    }

    public String getGoogleUser() {
        return googleUser;
    }

    public String getGooglePassword() {
        return googlePassword;
    }

    public int getPokecoin() {
        return pokecoin;
    }

    public int getStardust() {
        return stardust;
    }

    public int getMax_item() {
        return max_item;
    }

    public int getCreation_time() {
        return creation_time;
    }

    public int getMax_pokemon() {
        return max_pokemon;
    }

    public int getRevive_count() {
        return revive_count;
    }

    public int getRazz_count() {
        return razz_count;
    }

    public int getPokemons_encountered() {
        return pokemons_encountered;
    }

    public int getPokemon_deployed() {
        return pokemon_deployed;
    }

    public int getBattle_attack_total() {
        return battle_attack_total;
    }

    public int getNext_level_xp() {
        return next_level_xp;
    }

    public int getBattle_attack_won() {
        return battle_attack_won;
    }

    public int getPrestige_raised_total() {
        return prestige_raised_total;
    }

    public int getPokeballs_thrown() {
        return pokeballs_thrown;
    }

    public int getEggs_hatched() {
        return eggs_hatched;
    }

    public int getPrestige_dropped_total() {
        return prestige_dropped_total;
    }

    public int getPrev_level_xp() {
        return prev_level_xp;
    }

    public int getUnique_pokedex_entries() {
        return unique_pokedex_entries;
    }

    public int getKm_walked() {
        return km_walked;
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public int getPoke_stop_visits() {
        return poke_stop_visits;
    }

    public int getEvolutions() {
        return evolutions;
    }

    public int getPokeball_count() {
        return pokeball_count;
    }

    public int getGreatball_count() {
        return greatball_count;
    }

    public int getPotion_count() {
        return potion_count;
    }

    public int getSup_potion_count() {
        return sup_potion_count;
    }

    public int getHyp_potion_count() {
        return hyp_potion_count;
    }

    public int getEgg_count() {
        return egg_count;
    }

    public int getPokemon_count() {
        return pokemon_count;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("googleUser", googleUser);
        result.put("googlePassword", googlePassword);

        return result;
    }



}
