package com.example.movieorganiser;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Search extends AppCompatActivity {
    private DataBaseHelper myDb;//DataBaseHelper object
    private EditText searchEditText;
    private TextView searchTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        myDb=new DataBaseHelper(this);
        searchEditText=findViewById(R.id.searchEditText);
        searchTextView=findViewById(R.id.searchTextView);

    }

    //lookUpResult method
    public void lookUpResult(View view) {
        String searchEditTextInput=searchEditText.getText().toString();//contains the user input
        Cursor cursor=myDb.getAllMovieData();//calls the getAllMovieData method from the DataBaseHelper
        if (searchEditTextInput.matches("")){
            Toast.makeText(Search.this,"Type in a letter or a word",Toast.LENGTH_LONG).show();
            return;
        }
        if (cursor.getCount()==0){
            Toast.makeText(Search.this,"No data entry exists",Toast.LENGTH_LONG).show();

        }else{
            searchTextView.setText("");//sets the textView empty before getting called again
            while (cursor.moveToNext()){
                    //checks if the Movie title data contains a letter or word that is equal to user input
                    if (cursor.getString(1).toLowerCase().contains(searchEditTextInput.toLowerCase())){
                        searchTextView.setText(searchTextView.getText()+"\n"+cursor.getString(1));
                    }
                    //checks if the Director data contains a letter or word that is equal to user input
                    if (cursor.getString(3).toLowerCase().contains(searchEditTextInput.toLowerCase())){
                        searchTextView.setText(searchTextView.getText()+"\n"+cursor.getString(3));

                    }
                    //checks if the movie cast data contains a letter or word that is equal to user input
                    if (cursor.getString(4).toLowerCase().contains(searchEditTextInput.toLowerCase())){
                        searchTextView.setText(searchTextView.getText()+"\n"+cursor.getString(4));
                    }
            }
        }
    }
}