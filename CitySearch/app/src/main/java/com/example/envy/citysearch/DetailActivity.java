package com.example.envy.citysearch;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    int receivedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View contentLayout = findViewById(R.id.content_layout);
        TextView detailContentText = (TextView) contentLayout.findViewById(R.id.detail_content_text);

        //getting the id of selected item from search results activity
        Intent intent = getIntent();
        receivedId = intent.getIntExtra("ITEM_ID", -1);

        //Getting row of the clicked item by user
        final ProjectSQLiteOpenHelper helper = ProjectSQLiteOpenHelper.getInstance(DetailActivity.this);
        Cursor cursor = helper.getDetailsById(receivedId);
        cursor.moveToFirst();

        //Change title of actionbar in details activity
        getSupportActionBar().setTitle(cursor.getString(cursor.getColumnIndex("NAME")));
        //Stuff description into the details activity
        String details = cursor.getString(cursor.getColumnIndex(ProjectSQLiteOpenHelper.DESCRIPFULL));
        detailContentText.setText(details);

        //Setting image

        //Grabbed imageview and stored into variable
        ImageView titleImageView = (ImageView)findViewById(R.id.detail_imageview);
        //Get pic ID from db
        String photoId = cursor.getString(cursor.getColumnIndex(ProjectSQLiteOpenHelper.PIC_ID));
        //making String "bmw" into integer R.drawable.bmw;
        int drawableId = DetailActivity.this.getResources().getIdentifier(photoId, "drawable", DetailActivity.this.getPackageName());
        titleImageView.setImageResource(drawableId);

        //Setting floating action button
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        int favoriteStatus = cursor.getInt(cursor.getColumnIndex(ProjectSQLiteOpenHelper.FAVORITE));
        switch (favoriteStatus) {
            case 0:
                fab.setImageResource(R.drawable.ic_heart_border);
                break;
            case 1:
                fab.setImageResource(R.drawable.ic_heart);
                break;

        }




                //Setting detail texts.

        TextView creditcardTV = (TextView) contentLayout.findViewById(R.id.detail_credit_card_textview);
        TextView priceTV = (TextView) contentLayout.findViewById(R.id.detail_price_textview);
        TextView addressTV = (TextView) contentLayout.findViewById(R.id.detail_address_textview);

        int creditCardStatus = cursor.getInt(cursor.getColumnIndex(ProjectSQLiteOpenHelper.CREDITCARD));
        switch(creditCardStatus){
            case 0:
                creditcardTV.setText("Credit Card: No");
                break;
            case 1:
                creditcardTV.setText("Credit Card: Yes");
                break;
        }

        priceTV.setText("Price: "+cursor.getString(cursor.getColumnIndex(ProjectSQLiteOpenHelper.PRICE)));
        addressTV.setText("Address: "+cursor.getString(cursor.getColumnIndex(ProjectSQLiteOpenHelper.ADDRESS)));



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = ProjectSQLiteOpenHelper.getInstance(DetailActivity.this).getDetailsById(receivedId);
                cursor.moveToFirst();
                int favoriteStatus = cursor.getInt(cursor.getColumnIndex(ProjectSQLiteOpenHelper.FAVORITE));
                switch (favoriteStatus){
                    case 0:
                        fab.setImageResource(R.drawable.ic_heart);
                        helper.updateFavoriteStatus(0, receivedId);
                        break;
                    case 1:
                        fab.setImageResource(R.drawable.ic_heart_border);
                        helper.updateFavoriteStatus(1, receivedId);
                        break;
                }
            }
        });


    }
}
