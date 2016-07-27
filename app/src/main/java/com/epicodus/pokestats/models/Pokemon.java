package com.epicodus.pokestats.models;

import org.parceler.Parcel;

/**
 * Created by Guest on 7/26/16.
 */
@Parcel
public class Pokemon {

    public int pokeball;
    public int pokemon_id;
    public double creation_time_ms;
    public double height_m;
    public int stamina_max;
    public double weight_kg;
    public int individual_defense;
    public int stamina;
    public int individual_stamina;
    public int individual_attack;
    public int cp;
    public String sprite;
    public String name;

    public String nickname;

    public Pokemon() {}

//    public Pokemon(int pokeball, int pokemon_id, double creation_time_ms, int height_m, int stamina_max,
//                   int weight_kg, int individual_defense, int stamina, int individual_stamina, int individual_attack, int cp, String sprite, String name) {
//        this.pokeball = pokeball;
//        this.pokemon_id = pokemon_id;
//        this.creation_time_ms = creation_time_ms;
//        this.height_m = height_m;
//        this.stamina_max = stamina_max;
//        this.weight_kg = weight_kg;
//        this.individual_defense = individual_defense;
//        this.stamina = stamina;
//        this.individual_stamina = individual_stamina;
//        this.individual_attack = individual_attack;
//        this.cp = cp;
//        this.sprite = sprite;
//        this.name = name;
//    }
    public int getPokeball() {
        return pokeball;
    }

    public int getPokemon_id() {
        return pokemon_id;
    }

    public double getCreation_time_ms() {
        return creation_time_ms;
    }

    public double getHeight_m() {
        return height_m;
    }

    public int getStamina_max() {
        return stamina_max;
    }

    public double getWeight_kg() {
        return weight_kg;
    }

    public int getIndividual_defense() {
        return individual_defense;
    }

    public int getStamina() {
        return stamina;
    }

    public int getIndividual_stamina() {
        return individual_stamina;
    }

    public int getIndividual_attack() {
        return individual_attack;
    }

    public int getCp() {
        return cp;
    }

    public String getSprite() {
        return sprite;
    }

    public String getName() {
        return name;
    }
    public String getNickname() {
        return nickname;
    }
}
