package com.application.a1_sit305_91p;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void createNewAdvert(View view) {
        Intent intent = new Intent(this, CreateAdvertActivity.class);
        startActivity(intent);
    }

    public void showAllItems(View view) {
        Intent intent = new Intent(this, ShowItemsActivity.class);
        intent.putExtra("DISPLAY_ON_MAP", true);
        startActivity(intent);
    }

    public void showMap(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
