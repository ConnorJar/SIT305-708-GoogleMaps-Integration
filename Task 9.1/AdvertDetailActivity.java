package com.application.a1_sit305_91p;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AdvertDetailActivity extends AppCompatActivity {

    private TextView typeTextView, nameTextView, phoneTextView, descriptionTextView, dateTextView, locationTextView;
    private DatabaseHelper dbHelper;
    private Advert advert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert_detail);

        typeTextView = findViewById(R.id.typeTextView);
        nameTextView = findViewById(R.id.nameTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        dateTextView = findViewById(R.id.dateTextView);
        locationTextView = findViewById(R.id.locationTextView);

        dbHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        int advertId = intent.getIntExtra("ADVERT_ID", -1);
        if (advertId != -1) {
            advert = dbHelper.getAdvertById(advertId);
            if (advert != null) {
                typeTextView.setText(advert.getType());
                nameTextView.setText(advert.getName());
                phoneTextView.setText(advert.getPhone());
                descriptionTextView.setText(advert.getDescription());
                dateTextView.setText(advert.getDate());
                locationTextView.setText(advert.getLocation());
            }
        }
    }

    private void deleteAdvert() {
        if (advert != null) {
            long result = dbHelper.deleteAdvert(advert.getId());
            if (result != -1) {
                finish();
            }
        }
    }
}
