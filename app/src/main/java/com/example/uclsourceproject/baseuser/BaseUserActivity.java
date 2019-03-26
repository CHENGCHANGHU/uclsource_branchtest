package com.example.uclsourceproject.baseuser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.uclsourceproject.R;

public class BaseUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_base_user);
    }
}
