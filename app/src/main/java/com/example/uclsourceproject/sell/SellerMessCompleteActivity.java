package com.example.uclsourceproject.sell;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uclsourceproject.BaseUtil;
import com.example.uclsourceproject.R;
import com.example.uclsourceproject.transport.ProductionCheckActivity;
import com.example.uclsourceproject.transport.TransporterMessCompleteActivity;

public class SellerMessCompleteActivity extends AppCompatActivity
        implements View.OnClickListener {

    private Intent intent = null;

    private Button btnSellerSave = null;
    private SharedPreferences pref = null;
    private SharedPreferences.Editor prefEditor = null;
    private int characterFlags = 0b000000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_seller_mess_complete);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initUI();

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        characterFlags = pref.getInt("characterFlags", 0b000000);
    }

    private void initUI() {
        intent = getIntent();
        ((TextView) findViewById(R.id.title_text)).setText(intent.getStringExtra("title"));

        btnSellerSave = findViewById(R.id.btnSellerSave);
        btnSellerSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSellerSave:
                prefEditor = pref.edit();
                characterFlags = characterFlags | 0b000010;
                prefEditor.putInt("characterFlags", characterFlags);
                prefEditor.apply();

                if (BaseUtil.isCompleted(pref.getInt("characterFlags", 0b000000), 5)) {
                    Toast.makeText(this, "信息注册成功", Toast.LENGTH_SHORT).show();
                    intent = new Intent(SellerMessCompleteActivity.this, ProductionCheckActivity.class);
                    intent.putExtra("title", "商品核对");
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }
}
