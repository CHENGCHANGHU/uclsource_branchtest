package com.example.uclsourceproject.company;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.uclsourceproject.R;
import com.example.uclsourceproject.loginAndSign.LoginAndSignActivity;

public class CompanyManagerActivity extends AppCompatActivity
        implements View.OnClickListener {

    private static final String TAG = "tigercheng";

    private Button btnCompanyMessage = null;
    private Button btnCompanyStaff = null;
    private Button btnOperateScale = null;
    private Button btnExit = null;

    private Intent intent = null;

    private SharedPreferences pref = null;
    private SharedPreferences.Editor prefEditor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_company_manager);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initUI();

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        prefEditor = pref.edit();
    }

    private void initUI() {
        btnCompanyMessage = findViewById(R.id.btnCompanyMessage);
        btnCompanyMessage.setOnClickListener(this);
        btnCompanyStaff = findViewById(R.id.btnCompanyStaff);
        btnCompanyStaff.setOnClickListener(this);
        btnOperateScale = findViewById(R.id.btnOperateScale);
        btnOperateScale.setOnClickListener(this);
        btnExit = findViewById(R.id.btnExit);
        btnExit.setOnClickListener(this);

        intent = getIntent();
        ((TextView) findViewById(R.id.title_text)).setText(intent.getStringExtra("title"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCompanyMessage:
                break;
            case R.id.btnCompanyStaff:
                break;
            case R.id.btnOperateScale:
                break;
            case R.id.btnExit:
                prefEditor.putBoolean("rememberPwd", false);
                prefEditor.apply();
                Log.d(TAG, "CompanyManagerActivity: " + pref.getBoolean("rememberPwd", true));

                intent = new Intent(CompanyManagerActivity.this, LoginAndSignActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}
