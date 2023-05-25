package com.example.movieorganiser;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class Favourites extends AppCompatActivity {
    private DataBaseHelper myDb;//DataBaseHelper object
    private ListView movieTitleListView;//movieTitleListView ListView
    private ArrayList <String> movieList=new ArrayList<>();//arrayList that stores the movie titles
    private ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        movieTitleListView = (ListView)findViewById(R.id.favouritesListView);
        myDb=new DataBaseHelper(this);
        getMovieTitles();
    }

    //getMovieTitles method gets the user favourite movie titles and is displayed in a list
    public void getMovieTitles(){
        Cursor cursor= myDb.getFavouriteMovieTitles();//calls the getFavouriteMovieTitles method from the DataBaseHelper class
        if (cursor.getCount()==0){
            Toast.makeText(Favourites.this,"No data entry exists",Toast.LENGTH_LONG).show();//shows a message that data exits in the database

        }else{
            while (cursor.moveToNext()){//moves to the next movie title
                movieList.add(cursor.getString(0));//adds the movie titles from database to a arrayList
            }
        }
        Collections.sort(movieList);//sorts the movie names in alphabetical order
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, movieList);
        movieTitleListView.setAdapter(adapter);
        TextView textView = new TextView(this);
        textView.setText(R.string.favourites_title);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        movieTitleListView.addHeaderView(textView);
        movieTitleListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        for (int i=0;i<movieTitleListView.getCount();i++){
            movieTitleListView.setItemChecked(i,true);

        }

    }

    //deselectFavourites method deselects a favourite movie title
    public void deselectFavourites(View view) {
        for (int i=0;i<movieTitleListView.getCount();i++){
            if (!movieTitleListView.isItemChecked(i)){//gets the unchecked movie title
                String movieTitle=String.valueOf(movieTitleListView.getItemAtPosition(i));
                if(myDb.deselectFavouritesColumn(movieTitle)){//sets the unchecked movie's favourite status to not favourite
                    Toast.makeText(Favourites.this,"Updated",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(Favourites.this,"Not Updated",Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}