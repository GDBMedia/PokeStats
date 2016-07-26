package com.epicodus.pokestats.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.epicodus.pokestats.PokemonListActivity;
import com.epicodus.pokestats.R;

public class CreateAccountActivity extends AppCompatActivity {

    private Button mPokemonListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mPokemonListButton = (Button) findViewById(R.id.pokemonListButton);
        mPokemonListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateAccountActivity.this, PokemonListActivity.class);
                startActivity(intent);
            }
        });
    }
}
