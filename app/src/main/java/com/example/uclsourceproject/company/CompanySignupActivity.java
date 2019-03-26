package com.example.uclsourceproject.company;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uclsourceproject.R;
import com.example.uclsourceproject.loginAndSign.LoginAndSignActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class CompanySignupActivity extends AppCompatActivity
        implements View.OnClickListener {
    private static final String TAG = "tigercheng";

    private ArrayList<String> characterList = null;
    private Spinner spinner = null;
    private int currCharacter = 0;

    private LinearLayout characterBox = null;
    private Button btnCompanySignUp = null;

    private Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_company_signup);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initUI();


        characterList = new ArrayList<String>(
                Arrays.asList("牧场", "检疫局", "加工厂", "运输公司 ", "销售点"));
//        list.add("1.苹果");
        /*
         * 第二个参数是显示的布局
         * 第三个参数是在布局显示的位置id
         * 第四个参数是将要显示的数据
         */
        ArrayAdapter spinnerAdapter =
                new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, characterList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CompanySignupActivity.this, characterList.get(position), Toast.LENGTH_SHORT).show();

                for (int _i = 0; _i < 5; _i++) {
                    characterBox.getChildAt(_i).setVisibility(View.GONE);
                }

                characterBox.getChildAt(position).setVisibility(View.VISIBLE);

                currCharacter = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initUI() {
        Intent intent = getIntent();
        ((TextView) findViewById(R.id.title_text)).setText(intent.getStringExtra("title"));

        spinner = findViewById(R.id.spinner);
        characterBox = findViewById(R.id.ll_characterBox);
        btnCompanySignUp = findViewById(R.id.btn_companySignUp);
        btnCompanySignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_companySignUp:
                Toast.makeText(this,
                        characterList.get(currCharacter) + " 注册成功", Toast.LENGTH_SHORT).show();
                intent = new Intent(CompanySignupActivity.this, LoginAndSignActivity.class);
                startActivity(intent);
                break;
        }
    }
}
