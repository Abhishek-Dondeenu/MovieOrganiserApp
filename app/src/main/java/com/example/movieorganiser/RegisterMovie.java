package com.example.movieorganiser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterMovie extends AppCompatActivity {
    private DataBaseHelper myDb;

    private EditText movieText;//movieText editext
    private EditText yearText;//yearText editext
    private EditText directorText;//directorText editext
    private EditText actorList;//actorList editext
    private EditText ratingText;//ratingText editext
    private EditText reviewText;//reviewText editext

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_movie);

        myDb=new DataBaseHelper(this);//database helper object

        movieText=findViewById(R.id.editTextMovie);
        yearText=findViewById(R.id.editTextYear);
        directorText=findViewById(R.id.editTextDirector);
        actorList=findViewById(R.id.editTextActors);
        ratingText=findViewById(R.id.editTextRating);
        reviewText =findViewById(R.id.editTextReview);
    }

    //saves data to the database
    public void saveData(View view) {
        String title=movieText.getText().toString();
        String year=yearText.getText().toString();
        String director=directorText.getText().toString();
        String actors=actorList.getText().toString();
        String ratings=ratingText.getText().toString();
        String reviews=reviewText.getText().toString();
        String favourite="not favourite";//favourite value for the favourite column

        //checks if the editText text boxes are empty
        if (title.isEmpty()||year.isEmpty()||director.isEmpty()||actors.isEmpty()||ratings.isEmpty()||reviews.isEmpty()){
            Toast.makeText(RegisterMovie.this,"Please enter data",Toast.LENGTH_LONG).show();
        }
        else{
            //checks if the year entered is greater than 1895 and the rating entered corresponds to the range of 1−10
            if(!checkYear(year)||!checkRatings(ratings)){
                Toast.makeText(RegisterMovie.this,"Enter Valid Year/Rating",Toast.LENGTH_LONG).show();
                yearText.getText().clear();
            }

            else {
                //this variable holds the database method that takes in the data to be entered in the database
                boolean isInserted=myDb.insertData(title,Integer.parseInt(year),director,actors,Integer.parseInt(ratings),reviews,favourite);
                //gives a message if the data is saved or not
                if (isInserted==true){
                    clearEditTextValues();//clears the edit text boxes so new data can be entered
                    Toast.makeText(RegisterMovie.this,"Data Saved",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(RegisterMovie.this,"Data Not Saved",Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    //checkYear method checks if the year entered is greater than 1895
    public boolean checkYear(String year){
        int yearInt =Integer.parseInt(year);
        return year.length()==4 && yearInt>=1895;

    }
    // checkRatings method checks whether the rating entered corresponds to the range of 1−10
    public boolean checkRatings(String rating){
        int ratingInt =Integer.parseInt(rating);
        return ratingInt >0 &&ratingInt<11;
    }
    //clearEditTextValues clears entered editText values
    public void clearEditTextValues(){
        movieText.getText().clear();
        yearText.getText().clear();
        directorText.getText().clear();
        actorList.getText().clear();
        ratingText.getText().clear();
        reviewText.getText().clear();
    }



}