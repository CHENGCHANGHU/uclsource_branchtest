package com.example.uclsourceproject;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.uclsourceproject.loginAndSign.LoginAndSignActivity;
import com.example.uclsourceproject.produce.ProductionStateActivity;
import com.example.uclsourceproject.quarantine.QuarantineResInActivity;
import com.example.uclsourceproject.transport.ProductionCheckActivity;
import com.example.uclsourceproject.transport.TransportDataActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        Button btn1 = findViewById(R.id.button1);
        btn1.setOnClickListener(this);
        Button btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener(this);
        Button btn3 = findViewById(R.id.button3);
        btn3.setOnClickListener(this);
        Button btn4 = findViewById(R.id.button4);
        btn4.setOnClickListener(this);
        Button btn5 = findViewById(R.id.button5);
        btn5.setOnClickListener(this);
//        Button btn4=findViewById(R.id.button4);
//        Button btn5=findViewById(R.id.button5);
//        Button btn6=findViewById(R.id.button6);
//        Button btn7=findViewById(R.id.button7);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                Intent intent1 = new Intent(MainActivity.this, ProductionStateActivity.class);
                intent1.putExtra("title", "羊状态信息查询");
                startActivity(intent1);
                break;

            case R.id.button2:
                Intent intent2 = new Intent(MainActivity.this, ProductionCheckActivity.class);
                intent2.putExtra("title", "商品核对");
                startActivity(intent2);
                break;

            case R.id.button3:
                Intent intent3 = new Intent(MainActivity.this, TransportDataActivity.class);
                intent3.putExtra("title", "运输数据输入");
                startActivity(intent3);
                break;

            case R.id.button4:
                Intent intent4 = new Intent(MainActivity.this, QuarantineResInActivity.class);
                intent4.putExtra("title", "检疫结果录入");
                startActivity(intent4);
                break;

            case R.id.button5:
                Intent intent5 = new Intent(MainActivity.this, LoginAndSignActivity.class);
                intent5.putExtra("title", "登录注册");
                startActivity(intent5);
                break;

            default:
                break;
        }
    }
}
