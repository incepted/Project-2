package com.example.envy.citysearch;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView mSuggest1, mSuggest2, mSuggest3;
    ImageButton mButtonTrending, mButtonSurprise, mButtonFavorite;
    Button mButtonFood, mButtonDrinks, mButtonFun, mButtonAuto, mDonateButton;

    //Key for button intents
    String TRENDING;
    String SURPRISEME;
    String FAVORITES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Enable DBAssetHelper
        DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
        dbSetup.getReadableDatabase();

        //Listeners for Main activity suggestions and buttons

        mButtonTrending = (ImageButton) findViewById(R.id.venue1);
        mButtonSurprise = (ImageButton) findViewById(R.id.venue2);
        mButtonFavorite = (ImageButton) findViewById(R.id.venue3);
        mButtonFood = (Button) findViewById(R.id.venue4);
        mButtonDrinks = (Button) findViewById(R.id.venue5);
        mButtonFun = (Button) findViewById(R.id.venue6);
        mButtonAuto = (Button) findViewById(R.id.venue7);
        mDonateButton = (Button) findViewById(R.id.donateButton);

        //Buttons for Trending, Surprise, Favorites grid
        mButtonTrending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchResults.class);
                intent.putExtra("KEYWORD","REVIEWS");
                startActivity(intent);
            }
        });

        mButtonSurprise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchResults.class);
                intent.putExtra("KEYWORD","RANDOM");
                startActivity(intent);

            }
        });

        mButtonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainActivity.this, SearchResults.class);
                intent.putExtra("KEYWORD", "FAVORITE");
                startActivity(intent);
            }
        });

        //Buttons for the food, drinks, fun, car row
        mButtonFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchResults.class);
                intent.putExtra("KEYWORD","food");
                startActivity(intent);
            }
        });

        mButtonDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchResults.class);
                intent.putExtra("KEYWORD", "drinks");
                startActivity(intent);
            }
        });

        mButtonFun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchResults.class);
                intent.putExtra("KEYWORD", "fun");
                startActivity(intent);
            }
        });

        mButtonAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchResults.class);
                intent.putExtra("KEYWORD", "cars");
                startActivity(intent);
            }
        });

        mDonateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://i.imgur.com/KTS6pgZ.png");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });




        FrameLayout suggest1FL = (FrameLayout) findViewById(R.id.suggest1_framelayout);
        FrameLayout suggest2FL = (FrameLayout) findViewById(R.id.suggest2_framelayout);
        FrameLayout suggest3FL = (FrameLayout) findViewById(R.id.suggest3_framelayout);

        ImageView suggest1IV = (ImageView)findViewById(R.id.suggest1_imageview);
        ImageView suggest2IV = (ImageView)findViewById(R.id.suggest2_imageview);
        ImageView suggest3IV = (ImageView)findViewById(R.id.suggest3_imageview);

        TextView suggest1TitleTV = (TextView) findViewById(R.id.suggest1_title_textview);
        TextView suggest1DescTV = (TextView) findViewById(R.id.suggest1_desc_textview);
        TextView suggest2TitleTV = (TextView) findViewById(R.id.suggest2_title_textview);
        TextView suggest3TitleTV = (TextView) findViewById(R.id.suggest3_title_textview);


        ProjectSQLiteOpenHelper helepr = ProjectSQLiteOpenHelper.getInstance(MainActivity.this);
        Cursor randomCursor = helepr.resultsRandomMain();
        randomCursor.moveToFirst();

        //Getting the id of the random items
        String suggest1PhotoId = randomCursor.getString(randomCursor.getColumnIndex(ProjectSQLiteOpenHelper.PIC_ID));
        String suggest1Title = randomCursor.getString(randomCursor.getColumnIndex(ProjectSQLiteOpenHelper.NAME));
        String suggest1Desc = randomCursor.getString(randomCursor.getColumnIndex(ProjectSQLiteOpenHelper.DESCRIPMINI));
        final int idOfSuggest1 = randomCursor.getInt(randomCursor.getColumnIndex(ProjectSQLiteOpenHelper.ID));
        randomCursor.moveToNext();

        String suggest2PhotoId = randomCursor.getString(randomCursor.getColumnIndex(ProjectSQLiteOpenHelper.PIC_ID));
        String suggest2Title = randomCursor.getString(randomCursor.getColumnIndex(ProjectSQLiteOpenHelper.NAME));
        final int idOfSuggest2 = randomCursor.getInt(randomCursor.getColumnIndex(ProjectSQLiteOpenHelper.ID));
        randomCursor.moveToNext();

        String suggest3PhotoId = randomCursor.getString(randomCursor.getColumnIndex(ProjectSQLiteOpenHelper.PIC_ID));
        String suggest3Title = randomCursor.getString(randomCursor.getColumnIndex(ProjectSQLiteOpenHelper.NAME));
        final int idOfSuggest3 = randomCursor.getInt(randomCursor.getColumnIndex(ProjectSQLiteOpenHelper.ID));


        //converting the string id into integer format
        int drawableId1 = MainActivity.this.getResources().getIdentifier(suggest1PhotoId,"drawable",MainActivity.this.getPackageName());
        int drawableId2 = MainActivity.this.getResources().getIdentifier(suggest2PhotoId,"drawable",MainActivity.this.getPackageName());
        int drawableId3 = MainActivity.this.getResources().getIdentifier(suggest3PhotoId,"drawable",MainActivity.this.getPackageName());

        suggest1IV.setImageResource(drawableId1);
        suggest2IV.setImageResource(drawableId2);
        suggest3IV.setImageResource(drawableId3);

        suggest1TitleTV.setText(suggest1Title);
        suggest1DescTV.setText(suggest1Desc);

        suggest2TitleTV.setText(suggest2Title);
        suggest3TitleTV.setText(suggest3Title);


        suggest1FL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("ITEM_ID", idOfSuggest1);
                startActivity(intent);
            }
        });
        suggest2FL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("ITEM_ID",idOfSuggest2);
                startActivity(intent);
            }
        });
        suggest3FL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("ITEM_ID",idOfSuggest3);
                startActivity(intent);
            }
        });
    }

    //Enable SearchView on options_menu layout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this,SearchResults.class)));

        return true;

    }



}
