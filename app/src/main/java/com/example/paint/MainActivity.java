package com.example.paint;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.kyanogen.signatureview.SignatureView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {

    int defaultcolor;
    SignatureView signatureView;
    ImageButton imgeraser,imgsave,imgcolor;
    TextView pensize;
    SeekBar seekBar;

    private static String Filename;

    File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/mypainting");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signatureView = findViewById(R.id.signature_view);
        pensize = findViewById(R.id.textsize_tv);
        seekBar = findViewById(R.id.seekbar);
        imgeraser = findViewById(R.id.eraser_view);
        imgcolor = findViewById(R.id.color_view);
        imgsave = findViewById(R.id.save_view);

        defaultcolor = ContextCompat.getColor(this,R.color.black);
        askPermisions();

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String date = format.format(new Date());
        Filename = path+"/"+date+".png";
        if (!path.exists()){
            path.mkdir();
        }
        imgcolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColourPad();
            }
        });

        imgeraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.setPenColor(Color.WHITE);
                signatureView.setPenSize(30);
            }
        });

        imgsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    saveImage();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "File can't be created", Toast.LENGTH_SHORT).show();
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                String size = String.valueOf(progress);

                pensize.setText(size);
                signatureView.setPenSize(progress);
                seekBar.setMax(35);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void saveImage() throws IOException {
        File file = new File(Filename);

        // file.createNewFile();

        Bitmap bitmap = signatureView.getSignatureBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0,byteArrayOutputStream);
        byte[] bitmapdata = byteArrayOutputStream.toByteArray();

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(bitmapdata);
        fileOutputStream.flush();
        fileOutputStream.close();

        Toast.makeText(this, "Image Saved", Toast.LENGTH_SHORT).show();

    }

    private void askPermisions(){
        Dexter.withContext(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE).
                withListener(new MultiplePermissionsListener() {
                                 @Override
                                 public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                                     Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                                 }

                                 @Override
                                 public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                                     permissionToken.continuePermissionRequest();
                                 }
                             }

                ).check();
    }

    void ColourPad(){

        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, defaultcolor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {

                defaultcolor = color;
                signatureView.setPenColor(defaultcolor);

            }
        });

        ambilWarnaDialog.show();
    }

}