package com.example.ellenshin.whattocook;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ellenshin on 3/10/18.
 */

public class ResultActivity extends AppCompatActivity {

    private ListView myListView;
    private Context myContext;
    private TextView myfoundText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_listview);

        //data to display
        ArrayList<Recipe> recipeList = Recipe.getRecipesFromFile("recipes.json", this);
        ArrayList<Recipe> filteredList = filterList(recipeList);

        myContext = this;
        //create the adapter
        //RecipeAdapter adapter = new RecipeAdapter(this, filteredList);
        RecipeAdapter adapter = new RecipeAdapter(this, filteredList);

        //find the listview in the layout
        //set the adapter to listview
        myListView = findViewById(R.id.recipe_list_view);
        myfoundText = findViewById(R.id.found_textView);

        myfoundText.setText("Found " + filteredList.size() + " Recipes.");
        myListView.setAdapter(adapter);

    }



    private ArrayList<Recipe> filterList(ArrayList<Recipe> list) {

        ArrayList<Recipe> filteredList = new ArrayList<Recipe>();

        String label = this.getIntent().getExtras().getString("label");
        String _serving = this.getIntent().getExtras().getString("serving");

        int max_serving;
        int min_serving;
        if (_serving.equals("less than 4")) {
            min_serving = 1;
            max_serving = 3;
        } else if (_serving.equals("4-6")) {
            min_serving = 4;
            max_serving = 6;
        } else if (_serving.equals("7-9")) {
            min_serving = 7;
            max_serving = 9;
        } else if (_serving.equals("Select One")) {
            min_serving = 0;
            max_serving = 1000;
        } else {
            min_serving = 10;
            max_serving = 1000;
        }

        String time = this.getIntent().getExtras().getString("time");

        int max_time;
        int min_time;
        if (time.equals("30 minutes or less")) {
            min_time = 0;
            max_time = 30;
        } else if (time.equals("less than 1 hour")) {
            min_time = 0;
            max_time = 59;
        } else if (time.equals("more than 1 hour")) {
            min_time = 60;
            max_time = 1000;
        } else {
            min_time = 0;
            max_time = 1000;
        }

        ArrayList<Integer> time_list = convertTime(list);

        for (int i = 0; i < list.size(); i++) {
            if (!label.equals("Select One")) {
                if ((list.get(i).label.equals(label)) &&
                        (min_serving <= list.get(i).serving) &&
                        (max_serving >= list.get(i).serving) &&
                        (time_list.get(i) <= max_time) &&
                        (time_list.get(i) >= min_time)) {
                    filteredList.add(list.get(i));
                }
            } else {
                if ((min_serving <= list.get(i).serving) &&
                        (max_serving >= list.get(i).serving) &&
                        (time_list.get(i) <= max_time) &&
                        (time_list.get(i) >= min_time)) {
                    filteredList.add(list.get(i));
                }
            }
        }

        return filteredList;

    }


    private ArrayList<Integer> convertTime(ArrayList<Recipe> list) {
        ArrayList<Integer> time_list = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            int minutes = 0;
            String currentPrepTime = list.get(i).prepTime;
            String[] _elements = currentPrepTime.split(" ");
            ArrayList<String> elements = new ArrayList<String>(Arrays.asList(_elements));

            if(elements.contains("minutes")) {
                int index = elements.indexOf("minutes");
                minutes += Integer.valueOf(elements.get(index-1));
            }
            if(elements.contains("hours") || elements.contains("hour")) {
                int index = elements.indexOf("hours");
                if (index != -1) {
                    minutes += 60*(Integer.valueOf(elements.get(index-1)));
                } else {
                    minutes += 60;
                }
            }

            time_list.add(minutes);
        }
        return time_list;
    }


}














