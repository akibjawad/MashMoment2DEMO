package com.example.jawad.mashmoment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;

import com.example.jawad.mashmoment.MyParcelable;

public class Main2Activity extends AppCompatActivity {


    public MyParcelable ob = new MyParcelable();
    public Matrix matrix = new Matrix();
    public int mashId;
    public RelativeLayout overlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bitmap bmp = null;
        String filename = getIntent().getStringExtra("image");
        try {
            FileInputStream is = this.openFileInput(filename);
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Bitmap bmp2 = null;
        //String filename2 = getIntent().getStringExtra("image2");
        ob = (MyParcelable) getIntent().getSerializableExtra("transform");
        //Toast.makeText(this, "values of f"+ob.f.toString(), Toast.LENGTH_SHORT).show();
        matrix.setValues(ob.f);
        Log.d("transformValue", " " + matrix.toString());
        //Bundle b=getIntent().getExtras();
        //ob= (MyParcelable) b.getSerializable("transform");
        /*
        try {
            FileInputStream is2 = this.openFileInput(filename2);
            bmp2 = BitmapFactory.decodeStream(is2);
            is2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        Log.d("transformValue", " " + matrix.toString());
        mashId = getIntent().getIntExtra("emblem", 0);
        ImageView imgs = (ImageView) findViewById(R.id.imgs);
        ImageView shakib = (ImageView) findViewById(R.id.shakib);
        //int drawableResourceId = this.getResources().getIdentifier("mash"+mashId+".png", "drawable", this.getPackageName());
        Log.d("fuck", mashId + " ");
        switch (mashId) {
            case 1:
                shakib.setImageDrawable(getResources().getDrawable(R.drawable.mash01, getApplicationContext().getTheme()));
                break;
            case 2:
                shakib.setImageDrawable(getResources().getDrawable(R.drawable.mash02, getApplicationContext().getTheme()));
                break;
            case 3:
                shakib.setImageDrawable(getResources().getDrawable(R.drawable.mash03, getApplicationContext().getTheme()));
                break;
            case 4:
                shakib.setImageDrawable(getResources().getDrawable(R.drawable.mash04, getApplicationContext().getTheme()));
                break;
            case 5:
                shakib.setImageDrawable(getResources().getDrawable(R.drawable.x, getApplicationContext().getTheme()));
                break;

        }
        //
        // ImageView shakib=findViewById(mashId);
        //ImageView shakib=new ImageView(this);
        //shakib.setImageBitmap(BitmapFactory.decodeFile(mashId));
        //bmp=Bitmap.createBitmap(bmp,0,0,bmp.getWidth(),bmp.getHeight());
        imgs.setImageBitmap(bmp);
        // Matrix matrix2 = new Matrix();
        //matrix2.preScale(1, -1);
        //matrix2.postTranslate(bmp.getWidth(), 0);
        //imgs.setImageMatrix(matrix2);
        //shakib.setImageBitmap(bmp2);
        Log.d("transformValue", " " + matrix.toString());
        shakib.setImageMatrix(matrix);
        overlay = (RelativeLayout) findViewById(R.id.overLay2);

        /*
        Canvas canvas = new Canvas(bmp);
        canvas.drawBitmap(bmp, 0,0, null);
        canvas.drawBitmap(((BitmapDrawable)shakib.getDrawable()).getBitmap(),matrix,null);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        OutputStream output = null;
        File file = new File(Environment.getExternalStorageDirectory()+"/pic.jpg");
        try {
            output = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        // Get the preview size
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);


        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        // Set the height of the overlay so that it makes the preview a square
        RelativeLayout.LayoutParams overlayParams = (RelativeLayout.LayoutParams) overlay.getLayoutParams();
        overlayParams.height = height - width;
        overlay.setLayoutParams(overlayParams);
    }
    public void takeScreenshot(View v) {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);

            v1.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            v1.buildDrawingCache();
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            bitmap=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getWidth());
            v1.setDrawingCacheEnabled(false);
            v1.destroyDrawingCache();
            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
            Dialog settingsDialog = new Dialog(this);
            settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.popup
                    , null));
            settingsDialog.show();


        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
        }
    }
    public void grayscale(View v){
        ImageView imgs=(ImageView) findViewById(R.id.imgs);
        Bitmap original=((BitmapDrawable)imgs.getDrawable()).getBitmap();
        Bitmap bitmap = Bitmap.createBitmap(original.getWidth(),
                original.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        paint.setColorFilter(new ColorMatrixColorFilter(
                colorMatrix));
        canvas.drawBitmap(original, 0, 0, paint);
        imgs.setImageBitmap(bitmap);
        imgs=(ImageView) findViewById(R.id.shakib);
        original=((BitmapDrawable)imgs.getDrawable()).getBitmap();
        bitmap = Bitmap.createBitmap(original.getWidth(),
                original.getHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);

        paint.setColorFilter(new ColorMatrixColorFilter(
                colorMatrix));
        canvas.drawBitmap(original, 0, 0, paint);
        imgs.setImageBitmap(bitmap);
    }

    public void sepia(View v){
        ImageView imgs=(ImageView) findViewById(R.id.imgs);
        Bitmap original=((BitmapDrawable)imgs.getDrawable()).getBitmap();
        Bitmap bitmap = Bitmap.createBitmap(original.getWidth(),
                original.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);

        ColorMatrix colorScale = new ColorMatrix();
        colorScale.setScale(1, 1, 0.8f, 1);

        // Convert to grayscale, then apply brown color
        colorMatrix.postConcat(colorScale);

        paint.setColorFilter(new ColorMatrixColorFilter(
                colorMatrix));
        canvas.drawBitmap(original, 0, 0, paint);
        imgs.setImageBitmap(bitmap);
        imgs=(ImageView) findViewById(R.id.shakib);
        original=((BitmapDrawable)imgs.getDrawable()).getBitmap();
        bitmap = Bitmap.createBitmap(original.getWidth(),
                original.getHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);

        paint.setColorFilter(new ColorMatrixColorFilter(
                colorMatrix));
        canvas.drawBitmap(original, 0, 0, paint);
        imgs.setImageBitmap(bitmap);
    }

    public void binary(View v){
        ImageView imgs=(ImageView) findViewById(R.id.imgs);
        Bitmap original=((BitmapDrawable)imgs.getDrawable()).getBitmap();
        Bitmap bitmap = Bitmap.createBitmap(original.getWidth(),
                original.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);

        float m = 255f;
        float t = -255*128f;
        ColorMatrix threshold = new ColorMatrix(new float[] {
                m, 0, 0, 1, t,
                0, m, 0, 1, t,
                0, 0, m, 1, t,
                0, 0, 0, 1, 0
        });

        // Convert to grayscale, then scale and clamp
        colorMatrix.postConcat(threshold);
        paint.setColorFilter(new ColorMatrixColorFilter(
                colorMatrix));
        canvas.drawBitmap(original, 0, 0, paint);
        imgs.setImageBitmap(bitmap);
        imgs=(ImageView) findViewById(R.id.shakib);
        original=((BitmapDrawable)imgs.getDrawable()).getBitmap();
        bitmap = Bitmap.createBitmap(original.getWidth(),
                original.getHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);

        paint.setColorFilter(new ColorMatrixColorFilter(
                colorMatrix));
        canvas.drawBitmap(original, 0, 0, paint);
        imgs.setImageBitmap(bitmap);
    }
    public void invert(View v){
        ImageView imgs=(ImageView) findViewById(R.id.imgs);
        Bitmap original=((BitmapDrawable)imgs.getDrawable()).getBitmap();
        Bitmap bitmap = Bitmap.createBitmap(original.getWidth(),
                original.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        ColorMatrix colorMatrix=new ColorMatrix(new float[] {
                -1,  0,  0,  0, 255,
                0, -1,  0,  0, 255,
                0,  0, -1,  0, 255,
                0,  0,  0,  1,   0
        });
        paint.setColorFilter(new ColorMatrixColorFilter(
                colorMatrix));
        canvas.drawBitmap(original, 0, 0, paint);
        imgs.setImageBitmap(bitmap);
        imgs=(ImageView) findViewById(R.id.shakib);
        original=((BitmapDrawable)imgs.getDrawable()).getBitmap();
        bitmap = Bitmap.createBitmap(original.getWidth(),
                original.getHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);

        paint.setColorFilter(new ColorMatrixColorFilter(
                colorMatrix));
        canvas.drawBitmap(original, 0, 0, paint);
        imgs.setImageBitmap(bitmap);
    }

}