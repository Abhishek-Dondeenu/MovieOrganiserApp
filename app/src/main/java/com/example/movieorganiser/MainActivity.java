package com.example.movieorganiser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //launches the RegisterMovie activity
    public void registerMovie(View view) {
        Intent register = new Intent(this, RegisterMovie.class);
        startActivity(register);

    }

    //launches the DisplayMovies activity
    public void displayMovies(View view) {
        Intent display = new Intent(this, DisplayMovies.class);
        startActivity(display);
    }
    //launches the Favourites activity
    public void favourites(View view) {
        Intent favourites = new Intent(this, Favourites.class);
        startActivity(favourites);
    }

    //launches the EditMovies activity
    public void editMovies(View view) {
        Intent editMovies = new Intent(this, EditMovies.class);
        startActivity(editMovies);
    }
    //launches the Search activity
    public void search(View view) {
        Intent search = new Intent(this, Search.class);
        startActivity(search);
    }

    //launches the Ratings activity
    public void ratings(View view) {
        Intent rating = new Intent(this, Ratings.class);
        startActivity(rating);
    }
}