package com.example.movieorganiser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.media.Rating;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class Ratings extends AppCompatActivity {
    private DataBaseHelper myDb;//DataBaseHelper object
    private ListView movieTitleListView;//movieTitleListView ListView
    private ArrayList <String> movieList=new ArrayList<>();//arrayList that stores the movie titles
    private ArrayAdapter adapter;
    private String selectedFromList;
    public static final String EXTRA_MESSAGE = "com.example.movieorganiser.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);
        movieTitleListView = (ListView)findViewById(R.id.ratingsListView);
        myDb=new DataBaseHelper(this);
        getMovieTitles();
    }
    //getMovieTitles method
    public void getMovieTitles(){
        Cursor cursor= myDb.getMovieTitles();//calls the getMovieTitles from the DataBaseHelper class
        if (cursor.getCount()==0){
            Toast.makeText(Ratings.this,"No data entry exists",Toast.LENGTH_LONG).show();

        }else{
            while (cursor.moveToNext()){
                movieList.add(cursor.getString(0));
            }


        }
        Collections.sort(movieList);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked, movieList);
        movieTitleListView.setAdapter(adapter);
        TextView textView = new TextView(this);
        textView.setText("Movie List");
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        movieTitleListView.addHeaderView(textView);
        movieTitleListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        movieTitleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedFromList = (movieTitleListView.getItemAtPosition(position).toString());
            }
        });


    }

    //find movie method
    public void findMovie(View view) {
        if (selectedFromList==null){
            Toast.makeText(Ratings.this,"Choose a movie",Toast.LENGTH_LONG).show();

        }else {
            Intent intent = new Intent(getApplicationContext(),SecondRatingActivity.class);
            intent.putExtra(EXTRA_MESSAGE,selectedFromList);
            startActivity(intent);
        }


    }
}