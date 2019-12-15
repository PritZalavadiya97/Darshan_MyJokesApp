package com.aswdc_projectname.myjokes.Design.activity;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.aswdc_projectname.myjokes.BuildConfig;
import com.aswdc_projectname.myjokes.R;
import com.aswdc_projectname.myjokes.bal.BALTopic;
import com.aswdc_projectname.myjokes.bal.BALWebView;
import com.aswdc_projectname.myjokes.model.BeanTopics;
import com.aswdc_projectname.myjokes.util.Constant;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;


public class ActivityWebView extends BaseActivity {

    WebView webView;
    String html = " ";
    MenuItem item;
    String css = " ";
    String finalHtml = " ";
    int TopicId;
    ImageButton download, copy, share, image ,like , unlike;
    ClipboardManager cbm;
    ClipData cd;
    Bitmap bitmap;
    BeanTopics beanTopics;
    File imagePath;
    int isFavorite;
    String str;
    private int STORAGE_PERMISSION_CODE=1;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.web_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_web_view);
        super.onCreate(savedInstanceState);
        removeElevation();
        getTopicData();
        setHtmlData();
        TopicId = Objects.requireNonNull(getIntent().getExtras()).getInt("TopicID");

    }


    @Override
    public void initVariables() {
        copy = (ImageButton) findViewById(R.id.copy);
        share = (ImageButton) findViewById(R.id.share);
        download = (ImageButton) findViewById(R.id.download);
        image = (ImageButton) findViewById(R.id.image);
        cbm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        webView = findViewById(R.id.webViewTopic);

    }

    @Override
    public void bindWidgetEvents() {

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = finalHtml.replaceAll("</br>", "@@@");
                String CopyText = Html.fromHtml(str).toString().trim().replaceAll("@@@", "\n");
                CopyText += "\n\nShared by: Jokes";

                cd = ClipData.newPlainText("text", CopyText);
                cbm.setPrimaryClip(cd);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = finalHtml.replaceAll("</br>", "@@@");
                String CopyText = Html.fromHtml(str).toString().trim().replaceAll("@@@", "\n");

                CopyText +="\n\nSent using app \"JOKES\" from Play Store " + "\nhttp://tiny.cc/amyjokes";

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/apps/developer?id=Your_Publisher_Name"));
                startActivity(intent);

                Intent share = new Intent();
                share.setAction("android.intent.action.SEND");
                share.setType("text/plain");
                share.putExtra("android.intent.extra.TEXT", CopyText );
                startActivity(share);
            }
        });


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isWritePermissionAvailable()) {
                    Bitmap bitmap = getBitmapFromView(findViewById(R.id.webViewTopic));
                    saveBitmap(bitmap);
                    imageIt();

                } else {
                    requestStoragePermission();
                }
            }
        });


    }

    public void getTopicData() {
        Bundle bundle = getIntent().getExtras();
        html = bundle.getString("HtmlPage");
        String topicName = bundle.getString("TopicName");
        str= bundle.getString(Constant.IS_FAVORITE);
        TopicId = bundle.getInt(Constant.TOPIC_ID);
        beanTopics = BALWebView.getInstance().webData(TopicId);
        isFavorite = beanTopics.getIsFavorite();
//        if (!TextUtils.isEmpty(str) && TextUtils.isDigitsOnly(str)) {
//            isFavorite = Integer.parseInt(str);
//        } else {
//            isFavorite = 0;
//        }
        setTitle(topicName);
    }

    public void setHtmlData() {
        finalHtml = css + html;
        webView.loadData("<html>" + finalHtml + "</html>", "text/html", null);
    }

    boolean isWritePermissionAvailable() {
        Log.d("isWritePermission", "" + (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED));
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }



    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Permission_GRANTED", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(this, "Permission_DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    private void imageIt() {

        Uri photoURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, imagePath);
         Log.d("FilePath",imagePath.getAbsolutePath());
         Log.d("FilePath",photoURI.getPath());
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        String shareBody = "Shared by: Jokes";
        sharingIntent.setDataAndType(photoURI, "image/*");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);

        sharingIntent.putExtra(Intent.EXTRA_STREAM, photoURI);
        startActivity(Intent.createChooser(sharingIntent, "Shared via"));
    }
    private void saveBitmap(Bitmap bitmap) {
        imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }
    private Bitmap takeScreenshot() {
       View iv = findViewById(R.id.webViewTopic);

        Bitmap bitmap = Bitmap.createBitmap(iv.getDrawingCache());
        iv.setDrawingCacheEnabled(false);
        /*Canvas c = new Canvas(bitmap);*/
        return bitmap;

    }

    private Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        } else {
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        item = menu.findItem(R.id.isFavorite);
        if (isFavorite == 0) {
            menu.findItem(R.id.isFavorite).setIcon(ContextCompat.getDrawable(this, R.mipmap.ic_launcher_like));
        } else {
            menu.findItem(R.id.isFavorite).setIcon(ContextCompat.getDrawable(this, R.mipmap.ic_launcher_unlike));
        }
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {

            case R.id.isFavorite:
                if (isFavorite == 1) {
                    item.setIcon(ContextCompat.getDrawable(this, R.mipmap.ic_launcher_like));
                    isFavorite=0;
                    BALTopic.getInstance().updateFavorite(TopicId, 0);

                } else {
                    item.setIcon(ContextCompat.getDrawable(this, R.mipmap.ic_launcher_unlike));
                    isFavorite=1;
                    BALTopic.getInstance().updateFavorite(TopicId, 1);
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
