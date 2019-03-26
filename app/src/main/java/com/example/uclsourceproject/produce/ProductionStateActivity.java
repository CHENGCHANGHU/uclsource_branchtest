package com.example.uclsourceproject.produce;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uclsourceproject.R;
import com.example.uclsourceproject.UCLadapters.ProductionStateAdapter;
import com.example.uclsourceproject.UCLclasses.ProductionState;

import java.util.ArrayList;
import java.util.List;

public class ProductionStateActivity extends AppCompatActivity
        implements ProductionStateAdapter.OnRecycleViewItemClickListener {
    private static final String TAG = "tigercheng";

    private List<ProductionState> productionStateList = new ArrayList<>();

    private SharedPreferences pref = null;
    private SharedPreferences.Editor prefEditor = null;

    private Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_production_state);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        initUI();

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        Log.d(TAG, "SharedPreferences: " + pref.getInt("characterFlag", 0));

    }

    private void initUI() {
        Intent intent = getIntent();
        ((TextView) findViewById(R.id.title_text)).setText(intent.getStringExtra("title"));

        initProductionState();
        RecyclerView recyclerView = findViewById(R.id.recycle_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ProductionStateAdapter adapter = new ProductionStateAdapter(productionStateList);
        adapter.setOnRecycleViewItemClickListener(this);

        recyclerView.setAdapter(adapter);
    }

    private void initProductionState() {
        for (int i = 0; i < 20; i++) {
//            int _id = Integer.parseInt("2019030100");
            ProductionState _ps = new ProductionState(
                    2019030100 + i,
                    "健康",
                    107,
                    37);
            productionStateList.add(_ps);
        }
    }

    @Override
    public void onItemClick(View view) {
        Toast.makeText(this, "click:" + view.getTag(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view) {
        Toast.makeText(this, "longclick:" + view.getTag(), Toast.LENGTH_SHORT).show();
    }
}
