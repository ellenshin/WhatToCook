package com.example.ellenshin.whattocook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by ellenshin on 3/10/18.
 */
public class SearchActivity extends AppCompatActivity {

    private Spinner mDietSpinner;
    private Spinner mServingSpinner;
    private Spinner mPrepTimeSpinner;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mContext = this;
        mDietSpinner = findViewById(R.id.diet_restriction_spinner);
        mServingSpinner = findViewById(R.id.serving_restriction_spinner);
        mPrepTimeSpinner = findViewById(R.id.prep_time_spinner);

        final ArrayList<Recipe> recipeList = Recipe.getRecipesFromFile("recipes.json", this);

        ArrayList<String> labelList = new ArrayList<String>();

        labelList.add("Select One");

        for(int i = 0; i < recipeList.size(); i++) {
            String currentLabel = recipeList.get(i).label;
            if (!labelList.contains(currentLabel)) {
                labelList.add(currentLabel);
            }
        }

        ArrayAdapter<String> label_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, labelList);
        mDietSpinner.setAdapter(label_adapter);

        //create a list of items for the spinner.
        String[] serving_items = new String[]{"Select One", "less than 4", "4-6", "7-9", "more than 10"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> serving_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, serving_items);
        //set the spinners adapter to the previously created one.
        mServingSpinner.setAdapter(serving_adapter);

        //create a list of items for the spinner.
        String[] time_items = new String[]{"Select one", "30 minutes or less", "less than 1 hour", "more than 1 hour"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> time_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, time_items);
        //set the spinners adapter to the previously created one.
        mPrepTimeSpinner.setAdapter(time_adapter);


    }

    public void onClickSearch(View view) {
        Intent goToNextActivity = new Intent(getApplicationContext(), ResultActivity.class);

        goToNextActivity.putExtra("label", mDietSpinner.getSelectedItem().toString());
        goToNextActivity.putExtra("serving", mServingSpinner.getSelectedItem().toString());
        goToNextActivity.putExtra("time", mPrepTimeSpinner.getSelectedItem().toString());

        startActivity(goToNextActivity);
    }
}

