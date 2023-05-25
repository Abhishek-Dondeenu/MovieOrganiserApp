package com.example.movieorganiser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class MovieData extends AppCompatActivity {
    private String selectedMovieTitle="";
    private DataBaseHelper myDb;
    private EditText movieText;
    private EditText yearText;
    private EditText directorText;
    private EditText actorList;

    private EditText reviewText;
    private EditText favouritesText;

    private String strTitle;
    private String strYear;
    private String strDirector;
    private String strActors;
    private String strRatings;
    private String strReviews;
    private String strFavourites;

    private RatingBar rb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_data);


        Intent intent=getIntent();
        selectedMovieTitle=intent.getStringExtra(EditMovies.EXTRA_MESSAGE);
        myDb=new DataBaseHelper(this);


        movieText=findViewById(R.id.edit_Text_Movie);
        yearText=findViewById(R.id.edit_Text_Year);
        directorText=findViewById(R.id.edit_Text_Director);
        actorList=findViewById(R.id.edit_Text_Actors);
        reviewText =findViewById(R.id.edit_Text_Review);
        favouritesText =findViewById(R.id.edit_Text_favourites);

        rb =findViewById(R.id.ratingBar);

        strTitle="";
        strYear="";
        strDirector="";
        strActors="";
        strRatings="";
        strReviews="";
        strFavourites="";
        viewData();


    }

    //Reference: https://www.youtube.com/watch?v=KUq5wf3Mh0c
    //viewData method
    public void viewData(){

        Cursor res = myDb.getMovieData(selectedMovieTitle);//gets the movie data according to the selected movie title
        if(res.getCount() == 0) {
            //show message
            Toast.makeText(MovieData.this,"Nothing found",Toast.LENGTH_LONG).show();
            return;
        }
        while (res.moveToNext()) {
            strTitle=res.getString(1);
            strYear=res.getString(2);
            strDirector=res.getString(3);
            strActors=res.getString(4);
            strRatings=res.getString(5);
            strReviews=res.getString(6);
            strFavourites=res.getString(7);

        }
        int r=Integer.parseInt(strRatings);

        //displays all data
        movieText.setText(strTitle);
        yearText.setText(strYear);
        directorText.setText(strDirector);
        actorList.setText(strActors);
        rb.setRating((float)r);
        reviewText.setText(strReviews);
        favouritesText.setText(strFavourites);
        movieText.setEnabled(false);



    }

    //Reference: https://www.youtube.com/watch?v=PA4A9IesyCg
    //updateData method
    public void updateData(View view) {
        String title=movieText.getText().toString();
        String year=yearText.getText().toString();
        String director=directorText.getText().toString();
        String cast=actorList.getText().toString();
        int ratings= (int) rb.getRating();
        String reviews=reviewText.getText().toString();
        String favourites=favouritesText.getText().toString().toLowerCase();
        //checks if the year entered is greater than 1895 and the rating entered corresponds to the range of 1−10
        if(!checkYear(year)){
            Toast.makeText(MovieData.this,"Enter Valid Year",Toast.LENGTH_LONG).show();
            yearText.getText().clear();
        }
        else {
            boolean isUpdated=myDb.updateData(title,Integer.parseInt(year),director,cast,ratings,reviews,favourites);//updates data of the selected movie
            if (isUpdated==true){
                Toast.makeText(MovieData.this,"Data Updated",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(MovieData.this,"Data Not Updated",Toast.LENGTH_LONG).show();
            }
        }


    }
    //checkYear method checks if the year entered is greater than 1895
    public boolean checkYear(String year){
        int yearInt =Integer.parseInt(year);
        return year.length()==4 && yearInt>=1895;

    }
    


}