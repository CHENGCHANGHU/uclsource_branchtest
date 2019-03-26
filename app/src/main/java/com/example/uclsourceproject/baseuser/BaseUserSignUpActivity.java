package com.example.uclsourceproject.baseuser;

import android.content.Intent;
import android.icu.util.LocaleData;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.uclsourceproject.CodeUtil;
import com.example.uclsourceproject.HttpUtil;
import com.example.uclsourceproject.JsonUtil;
import com.example.uclsourceproject.R;
import com.example.uclsourceproject.loginAndSign.LoginAndSignActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BaseUserSignUpActivity extends AppCompatActivity
        implements View.OnClickListener {
    private static final String TAG = "tigercheng";

    private EditText etxUserName = null;
    private EditText etxContact = null;
    private EditText etxPassword = null;
    private EditText etxRePassword = null;
    private EditText etxVerificationCode = null;

    private String vCode = "";

    private Button btnGetVerificationCode = null;
    private ImageView ivVerification = null;
    private Button btnBaseUserSignUp = null;

    private Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_base_user_sign_up);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initUI();
    }

    private void initUI() {
        Intent intent = getIntent();
        ((TextView) findViewById(R.id.title_text)).setText(intent.getStringExtra("title"));

        etxUserName = findViewById(R.id.etxUserName);
        etxContact = findViewById(R.id.etxContact);
        etxPassword = findViewById(R.id.etxPassword);
        etxRePassword = findViewById(R.id.etxRePassword);
        etxVerificationCode = findViewById(R.id.etxVerificationCode);

//        btnGetVerificationCode = findViewById(R.id.btn_getVerificationCode);
        ivVerification = findViewById(R.id.iv_verification);
        ivVerification.setOnClickListener(this);
        btnBaseUserSignUp = findViewById(R.id.btn_baseUserSignUp);
        btnBaseUserSignUp.setOnClickListener(this);

        ivVerification.setImageBitmap(CodeUtil.getInstance().createBitmap());
        vCode = CodeUtil.getInstance().getCode();
        Toast.makeText(this, CodeUtil.getInstance().getCode(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btn_getVerificationCode:
//                Log.d(TAG, "获取验证码");
//                break;

            case R.id.iv_verification:
                ivVerification.setImageBitmap(CodeUtil.getInstance().createBitmap());
                vCode = CodeUtil.getInstance().getCode();
                Toast.makeText(this, CodeUtil.getInstance().getCode(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_baseUserSignUp:
                Log.d(TAG, "提交注册");
                baseUserSignUp();
                break;

        }
    }

    private boolean baseUserSignUp() {
        boolean access = false;

        String userName = etxUserName.getText().toString();
        String contact = etxContact.getText().toString();
        String password = etxPassword.getText().toString();
        String rePassword = etxRePassword.getText().toString();
        String verificationCode = etxVerificationCode.getText().toString();

        if (!rePassword.equals(password)) {
            Toast.makeText(this, "密码不一致", Toast.LENGTH_SHORT).show();
            return access;
        } else if (!(verificationCode.toLowerCase()).equals(vCode.toLowerCase())) {
            Toast.makeText(this, "验证码有误:" + verificationCode.toLowerCase() + ":" + vCode.toLowerCase(), Toast.LENGTH_SHORT).show();
            return access;
        }

        Log.d(TAG, "baseUserSignUp: ");
        //http://223.3.72.161/register??characterFlag=1
        HttpUtil.sendOKHttp3RequestPOST(HttpUtil.BASEURL_LOGIN_SIGN_PRODUCE + "/register?CharacterFlag=1",
                JsonUtil.getJSON(
                        "ConsumerName", userName,
                        "ContactNo", Integer.parseInt(contact),
                        "Password", password
                ),
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String resStr = response.body().string();
                        Log.d(TAG, "response.code: " + response.code());
                        Log.d(TAG, "resStr: " + resStr);
                        try {
                            JSONObject resJson = new JSONObject(resStr);
                            Log.d(TAG, "resJson: " + resJson.toString());

                            if (true) {
                                intent = new Intent(BaseUserSignUpActivity.this, LoginAndSignActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            Log.d(TAG, "JSONException: " + e.toString());
                            etxContact.setText("");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Toast.makeText(BaseUserSignUpActivity.this, resStr, Toast.LENGTH_SHORT).show();
                                }
                            });

                            e.printStackTrace();
                        }
                    }
                }

        );

//        HttpUtil.sendOKHttp3RequestGET(HttpUtil.BASEURL_SELL + "/Sell2/inquiry_supermarket?CompanyID=123",
//                new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        Log.d(TAG, "onFailure: " + e);
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        String resStr = response.body().string();
//                        Log.d(TAG, "response.code: " + response.code());
//                        Log.d(TAG, "resStr: " + resStr);
//
//                        //处理多个Json
//                        Log.d(TAG, "length: " + resStr.split("\\{").length);
//                        Log.d(TAG, "0: " + resStr.split("\\{")[0]);
//                        Log.d(TAG, "1: " + resStr.split("\\{")[1]);
//                        String test = "{\"hhh\":123,\"huhuhuh\":\"jjj\"}" +
//                                "{\"hhh\":123,\"qwhdiuq\":\"qwq\"}" +
//                                "{\"hhh\":123,\"bhbhbhb\":\"jjqdedj\"}" +
//                                "{\"hhh\":123,\"dcc\":\"vfvfvf\"}" +
//                                "{\"hhh\":123,\"jiuyiu\":\"ijqutuydfwqu\"}" +
//                                "{\"hhh\":12134223,\"dwqdwqd\":\"qewqedw\"}";
//                        Log.d(TAG, "onResponse: " + JsonUtil.getJSONArray(resStr).toString());
//
//                    }
//                });

//        HttpUtil.sendOKHttp3RequestPOST(HttpUtil.BASEURL_SELL + "/Sell2/alter_seller/",
//                JsonUtil.getJSON(
//                        "ConsumerId", "123",
//                        "ConsumerName", "Name",
//                        "ContactNo", 1111111,
//                        "RegisterTimeConsumer", "2019-01-01",
//                        "SearchCounts", 0,
//                        "VIP", false,
//                        "Password", "tigercheng",
//                        "consumerregistry_ptr", 1,
//                        "SellerID", "123",
//                        "SellerName", "tigercheng",
//                        "IDNo", "3210XXXXXXXXXXXXXX",
//                        "RegisterTime", "2019-01-01 14:20:00",
//                        "WorkPlaceID", "123XXXXXXXX",
//                        "PhotoSrc", "XXXXXXXXX"
//
//                ), new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        Log.d(TAG, "onFailure: " + e);
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        String resStr = response.body().string();
//                        Log.d(TAG, "response.code: " + response.code());
//                        Log.d(TAG, "resStr: " + resStr);
//                    }
//                });

        return access;
    }
}
