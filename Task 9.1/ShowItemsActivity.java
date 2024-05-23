package com.application.a1_sit305_91p;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.Serializable;
import java.util.List;

public class ShowItemsActivity extends AppCompatActivity implements AdvertAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private AdvertAdapter advertAdapter;
    private List<Advert> advertList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items);

        dbHelper = new DatabaseHelper(this);
        advertList = dbHelper.getAllAdverts();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        advertAdapter = new AdvertAdapter(advertList, this);
        recyclerView.setAdapter(advertAdapter);
    }

    @Override
    public void onItemClick(Advert advert) {
        Intent intent = new Intent(this, AdvertDetailActivity.class);
        intent.putExtra("advert", advert);
        intent.putExtra("advertList", (Serializable) advertList);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            advertAdapter.notifyDataSetChanged();
        }
    }
}
