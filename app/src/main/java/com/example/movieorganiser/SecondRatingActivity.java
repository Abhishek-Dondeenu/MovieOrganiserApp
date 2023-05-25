package com.example.movieorganiser;

import androidx.appcompat.app.AppCompatActivity;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;




public class SecondRatingActivity extends AppCompatActivity {

    private String selectedMovieTitle;//movieTitle string
    private String movieId;
    private String movieTitle;
    private String imageURL;
    private String rating;
    private TextView movieTitleTextView;
    private TextView ratingTextView;
    private ImageView movieImage;
    URL image_url;
    Bitmap bmp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_rating);

        String api1="k_q5fqo3u5";
        String api2="k_4qp3d0ul";
        movieTitleTextView=findViewById(R.id.movieTitleTextView);
        ratingTextView=findViewById(R.id.ratingTextView);
        movieImage=findViewById(R.id.movieImage);
        selectedMovieTitle = getIntent().getStringExtra(Ratings.EXTRA_MESSAGE);
        new Thread(new IMDBMovieTitleRunnable(selectedMovieTitle)).start();


    }

    //IMDBMovieTitleRunnable class
    class IMDBMovieTitleRunnable implements Runnable {
        String movieTitleRequested;
        StringBuilder stringBuilder = new StringBuilder();

        IMDBMovieTitleRunnable(String title){
            this.movieTitleRequested = title;
        }

        @Override
        public void run() {
            try{
                // makes the connection and receives the input stream
                URL url = new URL("https://imdb-api.com/en/API/SearchTitle/k_q5fqo3u5/" + movieTitleRequested.toLowerCase());

                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                // reads all lines in a stringbuilder
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }



                //do the JSON parsing
                JSONObject json = new JSONObject(stringBuilder.toString());
                JSONArray menuItemArray2 = json.getJSONArray("results");
                JSONObject descriptionJson = menuItemArray2.getJSONObject(0);

                // finds the IMDB id, the movie title and the image URL
                movieId = descriptionJson.getString("id");
                movieTitle = descriptionJson.getString("title");
                imageURL=descriptionJson.getString("image");
                Log.d("d",imageURL);

                //Reference: https://stackoverflow.com/questions/5776851/load-image-from-url
                image_url = new URL(imageURL);
                bmp = BitmapFactory.decodeStream(image_url.openConnection().getInputStream());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //updates the movieTitleTextView TextView and the movieImage ImageView
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    movieTitleTextView.setText(movieTitle);
                    movieImage.setImageBitmap(bmp);


                }
            });
            new Thread(new IMDBMovieTitleRunnable2(selectedMovieTitle)).start();//starts the next thread

        }

    }
    //IMDBMovieTitleRunnable2 class
    class IMDBMovieTitleRunnable2 implements Runnable {
        String movieTitleRequested;
        StringBuilder stringBuilder = new StringBuilder();

        IMDBMovieTitleRunnable2(String title){
            this.movieTitleRequested = title;
        }

        @Override
        public void run() {
            try{
                // makes the connection and receives the input stream
                URL url = new URL("https://imdb-api.com/en/API/UserRatings/k_22jipl79/" + movieId);

                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                // reads all lines in a stringbuilder
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                //do the JSON parsing
                JSONObject json = new JSONObject(stringBuilder.toString());

                //finds the total rating
                Log.d("d",stringBuilder.toString());
                rating = json.getString("totalRating");
                Log.d("d",rating);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //updates the rating textView
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (rating.matches("")){
                        ratingTextView.setText("No Rating available");
                    }else{
                        ratingTextView.setText(rating);
                    }

                }
            });
        }
    }


}