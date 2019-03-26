package com.example.uclsourceproject.produce;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DialogTitle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uclsourceproject.BaseUtil;
import com.example.uclsourceproject.HttpUtil;
import com.example.uclsourceproject.JsonUtil;
import com.example.uclsourceproject.R;
import com.example.uclsourceproject.TCCallbackListener;
import com.example.uclsourceproject.process.ProcesserMessCompleteActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProducerMessCompleteActivity extends AppCompatActivity
        implements View.OnClickListener {
    private static final String TAG = "tigercheng";

    private static final int PHOTO_IDCARD = 1;
    private static final int PHOTO_CERTIFICATES = 2;
    private static final int ALBUM_IDCARD = 3;
    private static final int ALBUM_CERTIFICATES = 4;

    private Button btnProducerSave = null;

    private Intent intent = null;
    private SharedPreferences pref = null;
    private SharedPreferences.Editor prefEditor = null;

    private int characterFlags = 0b000000;

    private Uri IDCardUri;
    private Uri CertificatesUri;

    private EditText etxIDCardNo = null;
    private EditText etxEmployerName = null;

    private Button btnPhotoIDCard = null;
    private Button btnAlbumIDCard = null;
    private Button btnPhotoCertificates = null;
    private Button btnAlbumCertificates = null;
    private ImageView ivIDCard = null;
    private ImageView ivCertificates = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "ProducerMessCompleteActivity onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_producer_mess_complete);
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

        btnProducerSave = findViewById(R.id.btnProducerSave);
        btnProducerSave.setOnClickListener(this);

        btnPhotoIDCard = findViewById(R.id.btnPhotoIDCard);
        btnPhotoIDCard.setOnClickListener(this);

        btnAlbumIDCard = findViewById(R.id.btnAlbumIDCard);
        btnAlbumIDCard.setOnClickListener(this);

        btnPhotoCertificates = findViewById(R.id.btnPhotoCertificates);
        btnPhotoCertificates.setOnClickListener(this);

        btnAlbumCertificates = findViewById(R.id.btnAlbumCertificates);
        btnAlbumCertificates.setOnClickListener(this);

        ivIDCard = findViewById(R.id.ivIDCard);
        ivCertificates = findViewById(R.id.ivCertificates);

        etxIDCardNo = findViewById(R.id.etxIDCardNo);
        etxEmployerName = findViewById(R.id.etxEmployerName);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPhotoIDCard:
//                Log.d(TAG, "onClick: " + getExternalCacheDir());
                try {
                    BaseUtil.takeAPhoto(this, String.valueOf(getExternalCacheDir()), new TCCallbackListener() {
                        @Override
                        public void jump(Uri uri, int requestCode) {
                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            IDCardUri = uri;
                            startActivityForResult(intent, requestCode);
                        }
                    }, PHOTO_IDCARD);
                } catch (Exception e) {
                    Log.d(TAG, "onClick: " + e);
                }
                break;

            case R.id.btnAlbumIDCard:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent, ALBUM_IDCARD);
                }
                break;

            case R.id.btnPhotoCertificates:

                BaseUtil.takeAPhoto(this, String.valueOf(getExternalCacheDir()), new TCCallbackListener() {
                    @Override
                    public void jump(Uri uri, int requestCode) {
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        CertificatesUri = uri;
                        startActivityForResult(intent, requestCode);
                    }
                }, PHOTO_CERTIFICATES);
                break;

            case R.id.btnAlbumCertificates:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent, ALBUM_CERTIFICATES);
                }
                break;

            case R.id.btnProducerSave:
//                prefEditor = pref.edit();
                characterFlags = characterFlags | 0b100000;
                Log.d(TAG, "characterFlags: " + characterFlags);
//                prefEditor.putInt("characterFlags", characterFlags);
//                prefEditor.apply();

//                Log.d(TAG, "characterFlags:" + Integer.toBinaryString(pref.getInt("characterFlags", 0b111111)));
//
//                if (characterFlags >>> 5 == 0b000001) {
//                    Toast.makeText(this, "信息注册成功", Toast.LENGTH_SHORT).show();
//                    intent = new Intent(ProducerMessCompleteActivity.this, ProductionStateActivity.class);
//                    intent.putExtra("title", "羊状态信息查询");
//                    startActivity(intent);
//                    finish();
//                }

                Log.d(TAG, "id: " + pref.getString("id", "id"));
                HttpUtil.sendOKHttp3RequestPOST(HttpUtil.BASEURL_LOGIN_SIGN_PRODUCE + "/fulfil?characterFlag=0",
                        JsonUtil.getJSON(
                                "ConsumerId", pref.getString("id", "id"),
                                "IDNo", etxIDCardNo.getText().toString(),
                                "CompanyName", etxEmployerName.getText().toString()
                        ), new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.d(TAG, "onFailure: " + e);
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String resStr = response.body().string();
                                Log.d(TAG, "response.code: " + response.code());
                                Log.d(TAG, "生产者信息完善resStr: " + resStr);

//                                try {
//                                    MediaType mediaType = MediaType.parse("image/*; charset=utf-8");
//                                    OkHttpClient okHttpClient = new OkHttpClient();
//                                    File file = new File(BaseUtil.getImagePath(ProducerMessCompleteActivity.this, IDCardUri, null));
//                                    Request request = new Request.Builder()
//                                            .url(HttpUtil.BASEURL_LOGIN_SIGN_PRODUCE + "/fufil_img")
//                                            .post(RequestBody.create(mediaType, file))
//                                            .build();
//                                    okHttpClient.newCall(request).enqueue(new Callback() {
//                                        @Override
//                                        public void onFailure(Call call, IOException e) {
//                                            Log.d(TAG, "onFailure: " + e.getMessage());
//                                        }
//
//                                        @Override
//                                        public void onResponse(Call call, Response response) throws IOException {
//                                            Log.d(TAG, response.protocol() + " " + response.code() + " " + response.message());
//                                            Headers headers = response.headers();
//                                            for (int i = 0; i < headers.size(); i++) {
//                                                Log.d(TAG, headers.name(i) + ":" + headers.value(i));
//                                            }
//                                            Log.d(TAG, "onResponse: " + response.body().string());
//                                        }
//                                    });
//                                } catch (Exception e) {
//                                    Log.d(TAG, "图片失败: " + e);
//                                }
                            }
                        }
                );


                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PHOTO_IDCARD:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(IDCardUri));
                        ivIDCard.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相机调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case ALBUM_IDCARD:
//                handleImageOnKitKat(this, data);
                if (resultCode == RESULT_OK) {
                    try {
//                        Bitmap bitmap = BitmapFactory.decodeStream(
//                                getContentResolver().openInputStream(
//                                        BaseUtil.getAlbumImagePath(this, data)));
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(data.getData()));
                        ivIDCard.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相册调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case PHOTO_CERTIFICATES:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(CertificatesUri));
                        ivCertificates.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相机调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case ALBUM_CERTIFICATES:
//                handleImageOnKitKat(this, data);
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(data.getData()));
                        ivCertificates.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相册调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;


        }

    }

//    private void handleImageOnKitKat(Context context, Intent data) {
//
//        String imagePath = null;
//        Uri uri = data.getData();
//        if (DocumentsContract.isDocumentUri(context, uri)) {
//            String docId = DocumentsContract.getDocumentId(uri);
//            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
//                String id = docId.split(":")[1];
//                String selection = MediaStore.Images.Media._ID + "=" + id;
//                imagePath = BaseUtil.getImagePath(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
//            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
//                Uri contentUri = ContentUris.withAppendedId(
//                        Uri.parse("content://downloads/public_downloads"),
//                        Long.valueOf(docId)
//                );
//                imagePath = BaseUtil.getImagePath(context, contentUri, null);
//            }
//        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
//            imagePath = BaseUtil.getImagePath(context, uri, null);
//        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            imagePath = uri.getPath();
//        }
//
//        if (imagePath != null) {
//            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//            ivIDCard.setImageBitmap(bitmap);
//        } else {
//            Log.d(TAG, "handleImageOnKitKat: null");
//        }
//    }


}
