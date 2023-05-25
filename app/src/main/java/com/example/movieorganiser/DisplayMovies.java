package com.example.movieorganiser;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class DisplayMovies extends AppCompatActivity {
    private DataBaseHelper myDb;//DataBaseHelper object
    private ListView movieTitleListView;//movieTitleListView ListView
    private ArrayList <String> movieList=new ArrayList<>();//arrayList that stores the movie titles
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movies);
        movieTitleListView = (ListView)findViewById(R.id.listView);
        myDb=new DataBaseHelper(this);
        getMovieTitles();
    }
    //getMovieTitles method gets movie titles and is displayed in a list
    public void getMovieTitles(){
        Cursor cursor= myDb.getMovieTitles();//calls the getMovieTitles from the DataBaseHelper class
        if (cursor.getCount()==0){
            Toast.makeText(DisplayMovies.this,"No data entry exists",Toast.LENGTH_LONG).show();//shows a message that data exits in the database

        }else{
            while (cursor.moveToNext()){//moves to the next movie title
                movieList.add(cursor.getString(0));//adds the movie titles from database to a arrayList
            }
        }
        Collections.sort(movieList);//sorts the movie names in alphabetical order
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, movieList);
        movieTitleListView.setAdapter(adapter);
        movieTitleListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        TextView textView = new TextView(this);
        textView.setText(R.string.display_title);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        movieTitleListView.addHeaderView(textView);
    }

    //addToFavorites method takes the selected movie titles updates favourite status column for those check movie titles
    public void addToFavorites(View view) {
        for (int i=0;i<movieTitleListView.getCount();i++){
            //checks if the movie title has been checked
            if (movieTitleListView.isItemChecked(i)){
                String movieTitle=String.valueOf(movieTitleListView.getItemAtPosition(i));//gets the selected movie title
                if(myDb.updateFavouritesColumn(movieTitle)){//updates the favourite status of the selected movie title
                    Toast.makeText(DisplayMovies.this,"Updated",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(DisplayMovies.this,"Not Updated",Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}