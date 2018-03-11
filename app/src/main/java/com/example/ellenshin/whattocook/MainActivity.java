package com.example.ellenshin.whattocook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickStartCooking(View view) {
        Intent goToNextActivity = new Intent(getApplicationContext(), SearchActivity.class);
        startActivity(goToNextActivity);
    }
}
