package com.card.my;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditFormActivity extends AppCompatActivity {

    private EditText serialNoEditText, nameEditText, addressEditText, dateEditText;
    private ImageView pictureImageView;
    private Button appendSignatureButton, sendToTajirButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_form);

        serialNoEditText = findViewById(R.id.serialNoEditText);
        nameEditText = findViewById(R.id.nameEditText);
        addressEditText = findViewById(R.id.addressEditText);
        dateEditText = findViewById(R.id.dateEditText);
        pictureImageView = findViewById(R.id.pictureImageView);
        appendSignatureButton = findViewById(R.id.appendSignatureButton);
        sendToTajirButton = findViewById(R.id.sendToTajirButton);

        //Retrieve data from the intent
        Intent intent = getIntent();
        String serialNo = intent.getStringExtra("SERIAL_NO");
        String name = intent.getStringExtra("NAME");
        String address = intent.getStringExtra("ADDRESS");
        String date = intent.getStringExtra("DATE");
        Bitmap picture = intent.getParcelableExtra("PICTURE");

        //Populate the views with the data
        serialNoEditText.setText(serialNo);
        nameEditText.setText(name);
        addressEditText.setText(address);
        dateEditText.setText(date);
        pictureImageView.setImageBitmap(picture);

        //Handle the append signature button click
        appendSignatureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Add code to append the signature to the form
            }
        });

        //Handle the send to tajir button click
        sendToTajirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Add code to send the completed form to the tajir
            }
        });
    }
}
