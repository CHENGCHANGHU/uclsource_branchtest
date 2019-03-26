package com.example.uclsourceproject;

import android.content.ContentUris;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.sip.SipSession;
import android.os.Build;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import javax.security.auth.callback.Callback;

public class BaseUtil {

    private static final String TAG = "tigercheng";

    public static void initActivity(View view, ActionBar actionBar, String title) {
//        ActionBar actionBar = context.getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

//        Intent intent = getIntent();
        ((TextView) view.findViewById(R.id.title_text)).setText(title);

    }

//    public static void

    //信息是否完善 完善返回true
    public static boolean isCompleted(int characterFlags, int character) {
        boolean result = false;
//        characterFlags = pref.getInt("characterFlags", 0b000000);
        Log.d(TAG, "the characterFlags: " + Integer.toBinaryString(characterFlags));
        switch (character) {
            case 1:
                result = characterFlags >>> 5 == 0b000001;
//                Log.d(TAG, "" + Integer.toBinaryString(characterFlags >>> 5));
                Log.d(TAG, "characterFlags = 1 completed ? " + result);
                break;
            case 2:
//                Log.d(TAG, "first: " + Integer.toBinaryString(characterFlags));
//                Log.d(TAG, "second: " + Integer.toBinaryString(0b010000));
//                result = (characterFlags & 0b010000) >>> 4 == 0b000001;
                result = (characterFlags & 0b010000) > 1;
//                Log.d(TAG, "characterFlags 2 and: " + Integer.toBinaryString(characterFlags & 0b010000));
                Log.d(TAG, "characterFlags = 2 completed ? " + result);
                break;
            case 3:
//                result = (characterFlags & 0b001000) >>> 3 == 0b000001;
                result = (characterFlags & 0b001000) > 1;
                Log.d(TAG, "characterFlags = 3 completed ? " + result);
                break;
            case 4:
//                result = (characterFlags & 0b000100) >>> 2 == 0b000001;
                result = (characterFlags & 0b000100) > 1;
                Log.d(TAG, "characterFlags = 4 completed ? " + result);
                break;
            case 5:
//                result = (characterFlags & 0b000010) >>> 1 == 0b000001;
                result = (characterFlags & 0b000010) > 1;
                Log.d(TAG, "characterFlags = 5 completed ? " + result);
                break;
        }
        Log.d(TAG, "isCompleted: " + result);
        return result;
    }

    public static Uri takeAPhoto(Context context, String path,
                                 TCCallbackListener tcCallbackListener,
                                 int requestCode) {
        File outputImage = new File(path, "image.jpg");
        Uri imageUri = null;

        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();

            if (Build.VERSION.SDK_INT >= 24) {
                imageUri = FileProvider.getUriForFile(context,
                        "com.example.uclsourceproject.fileprovider", outputImage);
            } else {
                imageUri = Uri.fromFile(outputImage);
            }
            tcCallbackListener.jump(imageUri, requestCode);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.d(TAG, "exception: " + e);
        }

        return imageUri;
    }

    public static Uri getAlbumImagePath(Context context, Intent data) {
        String imagePath = null;

        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(context, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = BaseUtil.getImagePath(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docId)
                );
                imagePath = BaseUtil.getImagePath(context, contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = BaseUtil.getImagePath(context, uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }

//        File file = new File(imagePath);
//        uri = FileProvider.getUriForFile(context,
//                "com.example.uclsourceproject.fileprovider", file);

//        if (imagePath != null) {
//            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//            ivIDCard.setImageBitmap(bitmap);
//        } else {
//            Log.d(TAG, "handleImageOnKitKat: null");
//        }

        return uri;
    }

    public static void handleImageOnKitKat(Context context, Intent data) {

        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(context, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = BaseUtil.getImagePath(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docId)
                );
                imagePath = BaseUtil.getImagePath(context, contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = BaseUtil.getImagePath(context, uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }

//        if (imagePath != null) {
//            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//            ivIDCard.setImageBitmap(bitmap);
//        } else {
//            Log.d(TAG, "handleImageOnKitKat: null");
//        }
    }

    public static String getImagePath(Context context, Uri uri, String selection) {
        String path = null;

//        Cursor cursor = getContentResolver().query(uri,
//                null, selection, null, null);
        Cursor cursor = context.getContentResolver().query(uri,
                null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
        }

        return path;
    }


}
