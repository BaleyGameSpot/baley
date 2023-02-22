package com.card.my;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CardActivity extends AppCompatActivity {

    private TextView serialNoTextView, nameTextView, addressTextView, dateTextView;
    private ImageView pictureImageView;
    private static final int CAMERA_REQUEST = 1888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        serialNoTextView = findViewById(R.id.serialNoTextView);
        nameTextView = findViewById(R.id.nameTextView);
        addressTextView = findViewById(R.id.addressTextView);
        dateTextView = findViewById(R.id.dateTextView);
        pictureImageView = findViewById(R.id.pictureImageView);

        //Retrieve user input from Intent
        String serialNo = getIntent().getStringExtra("SERIAL_NO");
        String name = getIntent().getStringExtra("NAME");
        String address = getIntent().getStringExtra("ADDRESS");
        String date = getIntent().getStringExtra("DATE");
        Bitmap photo = getIntent().getParcelableExtra("PICTURE");

        //Populate TextViews and ImageView with user input
        serialNoTextView.setText(serialNo);
        nameTextView.setText(name);
        addressTextView.setText(address);
        dateTextView.setText(date);
        pictureImageView.setImageBitmap(photo);

        pictureImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
    }
}

