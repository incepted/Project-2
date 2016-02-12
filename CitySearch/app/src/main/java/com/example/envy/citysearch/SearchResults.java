package com.example.envy.citysearch;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SearchResults extends AppCompatActivity {

    CursorAdapter mCursorAdapter;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        ListView mListView = (ListView)findViewById(R.id.listview_results);
        ProjectSQLiteOpenHelper helper = ProjectSQLiteOpenHelper.getInstance(SearchResults.this);

        //Received String keyword for the corresponding MainActiviity Buttons
        String receivedKeyword = getIntent().getStringExtra("KEYWORD");
        Cursor cursor = helper.mainSearchFunction(query);
        //bridge access to database
        if (receivedKeyword != null) {
            if(receivedKeyword.equals("RANDOM")){
                cursor = helper.resultsRandom();
            }else if(receivedKeyword.equals("FAVORITE")){
                cursor = helper.resultsFavorited();
            }else if(receivedKeyword.equals("REVIEWS")){
                cursor = helper.resultsFavorited();
            }else {
                cursor = helper.resultsByCategory(receivedKeyword);
            }
        }



        //show data in this activity
        mCursorAdapter = new CursorAdapter(SearchResults.this, cursor, 0) {
            @Override
            //position that the view will be inflated
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                //creating inflated view layout
                return LayoutInflater.from(context).inflate(R.layout.listitemstuff,parent,false);
            }

            @Override
            //parsing data into the inflated view
            //puts content into the cards
            public void bindView(View view, Context context, Cursor cursor) {
                //referring to view of the inflated view
                ImageView iconImageView = (ImageView) view.findViewById(R.id.venue_image);
                TextView titleTextView = (TextView) view.findViewById(R.id.title_text);
                TextView descriptionTextView = (TextView) view.findViewById(R.id.description_text);

                //data from cursor goes here
                //getColumnIndex converts to index value of 1 for "NAME"


                //setting the image on the search results card view
                String photoId = cursor.getString(cursor.getColumnIndex(ProjectSQLiteOpenHelper.ICON_ID));
                int drawableId = SearchResults.this.getResources().getIdentifier(photoId,"drawable",SearchResults.this.getPackageName());
                iconImageView.setImageResource(drawableId);

                String titleText = cursor.getString(cursor.getColumnIndex("NAME"));
                String descriptionText = cursor.getString(cursor.getColumnIndex("DESCRIPMINI"));
                titleTextView.setText(titleText);
                descriptionTextView.setText(descriptionText);
            }
        };
        mListView.setAdapter(mCursorAdapter);
        handleIntent(getIntent());

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchResults.this, DetailActivity.class);
                //gets selected filtered info from database
                Cursor currentCursor = mCursorAdapter.getCursor();
                //positions "Highlights" row
                currentCursor.moveToPosition(position);
                //gets actual ID of position (selected)
                int idOfClickedRow = currentCursor.getInt(currentCursor.getColumnIndex(ProjectSQLiteOpenHelper.ID));
                intent.putExtra("ITEM_ID",idOfClickedRow);
                startActivity(intent);
            }
        });

    }

    //Intent to handle user queries below
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }


    //
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(SearchResults.this,"Searching for "+query, Toast.LENGTH_SHORT).show();
            //helper object to gain access to mainSearchFunction
            //results from OpenHelper stored here
            Cursor newCursor = ProjectSQLiteOpenHelper.getInstance(SearchResults.this).mainSearchFunction(query);
            mCursorAdapter.swapCursor(newCursor);

            //Perform method after search comes in

        }

}}
