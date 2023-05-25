package com.example.movieorganiser;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
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
import java.util.Collections;

public class EditMovies extends AppCompatActivity {
    private DataBaseHelper myDb;//DataBaseHelper object
    private ListView movieTitleListView;//movieTitleListView ListView
    private ArrayList <String> movieList=new ArrayList<>();//arrayList that stores the movie titles
    private ArrayAdapter adapter;
    public static final String EXTRA_MESSAGE = "com.example.movieorganiser.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movies);
        movieTitleListView = (ListView)findViewById(R.id.editListView);
        myDb=new DataBaseHelper(this);
        getMovieTitles();
    }

    //getMovieTitles method gets movie titles and is displayed in a list
    public void getMovieTitles(){
        Cursor cursor= myDb.getMovieTitles();//calls the getMovieTitles from the DataBaseHelper class
        if (cursor.getCount()==0){
            Toast.makeText(EditMovies.this,"No data entry exists",Toast.LENGTH_LONG).show();//shows a message that data exits in the database

        }else{
            while (cursor.moveToNext()){//moves to the next movie title
                movieList.add(cursor.getString(0));//adds the movie titles from database to a arrayList
            }
        }
        Collections.sort(movieList);//sorts the movie names in alphabetical order
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, movieList);
        movieTitleListView.setAdapter(adapter);
        TextView textView = new TextView(this);
        textView.setText(R.string.Edit_title);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        movieTitleListView.addHeaderView(textView);
        movieTitleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(),MovieData.class);
                String selectedFromList = (movieTitleListView.getItemAtPosition(position).toString());//gets the selected movie title
                intent.putExtra(EXTRA_MESSAGE,selectedFromList);//passes the selected movie title to MoviData class
                startActivity(intent);

            }
        });
    }


}