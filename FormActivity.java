package com.card.my;

import static android.service.controls.ControlsProviderService.TAG;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FormActivity extends AppCompatActivity {

    private EditText serialNoEditText, nameEditText, addressEditText, dateEditText, fatherNameEditText, whatsAppNumberEditText, cnicNumberEditText;
    private ImageView pictureImageView;
    private Button submitFormButton, takePictureButton;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1;
    private static final int CAMERA_REQUEST_CODE = 2;
    private static final int REQUEST_IMAGE_CAPTURE = 3;
    private Uri pictureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        serialNoEditText = findViewById(R.id.serialNoEditText);
        nameEditText = findViewById(R.id.nameEditText);
        fatherNameEditText = findViewById(R.id.fatherNameEditText);
        addressEditText = findViewById(R.id.addressEditText);
        dateEditText = findViewById(R.id.dateEditText);
        pictureImageView = findViewById(R.id.pictureImageView);
        submitFormButton = findViewById(R.id.submitFormButton);
        takePictureButton = findViewById(R.id.takePictureButton);
        cnicNumberEditText = findViewById(R.id.cnicNumberEditText);
        whatsAppNumberEditText = findViewById(R.id.whatsAppNumberEditText);

        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(FormActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(FormActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
                } else {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                }
            }
        });

        submitFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateForm()) {
                    return;
                }
                String serialNo = serialNoEditText.getText().toString();
                String name = nameEditText.getText().toString();
                String address = addressEditText.getText().toString();
                String date = dateEditText.getText().toString();
                String father = fatherNameEditText.getText().toString();
                String whatsapp = whatsAppNumberEditText.getText().toString();
                String cnic = cnicNumberEditText.getText().toString();
                Bitmap picture = ((BitmapDrawable) pictureImageView.getDrawable()).getBitmap();
                try {
                    submitForm(serialNo, name, address, father, whatsapp, cnic, date, picture);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            pictureImageView.setImageBitmap(imageBitmap);

            // Save image to Uri
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            pictureUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), imageBitmap, "Title", null));
        }
    }
    private boolean validateForm() {
        String serialNo = serialNoEditText.getText().toString();
        String name = nameEditText.getText().toString();
        String address = addressEditText.getText().toString();
        String date = dateEditText.getText().toString();
        String father = fatherNameEditText.getText().toString();
        String whatsapp = whatsAppNumberEditText.getText().toString();
        String cnic = cnicNumberEditText.getText().toString();


        if (serialNo.isEmpty()) {
            serialNoEditText.setError("سیریل نمبر درکار ہے۔");
            serialNoEditText.requestFocus();
            return false;
        }
        if (date.isEmpty()) {
            dateEditText.setError("تاریخ درکار ہے۔");
            dateEditText.requestFocus();
            return false;
        }
        if (name.isEmpty()) {
            nameEditText.setError("نام درکار ہے۔");
            nameEditText.requestFocus();
            return false;
        }
        if (father.isEmpty()) {
            fatherNameEditText.setError("والد کا نام درکار ہے۔");
            fatherNameEditText.requestFocus();
            return false;
        }
        if (address.isEmpty()) {
            addressEditText.setError("دکان کا پتہ درکار ہے۔");
            addressEditText.requestFocus();
            return false;
        }
        if (whatsapp.isEmpty()) {
            whatsAppNumberEditText.setError("واٹس ایپ نمبر درکار ہے۔");
            whatsAppNumberEditText.requestFocus();
            return false;
        }
        if (cnic.isEmpty()) {
            cnicNumberEditText.setError("شناختی کارڈ نمبر درکار ہے۔");
            cnicNumberEditText.requestFocus();
            return false;
        }
        return true;
    }

    private void submitForm(String serialNo, String name, String address, String father, String whatsapp, String cnic, String date, Bitmap picture) throws IOException {
        //TODO: Add code to submit the form data to the server or database

        // Save image to Firebase Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child("images/" + serialNo + ".jpg");

        // Parse the date string into a Date object using SimpleDateFormat
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate;
        try {
            parsedDate = dateFormat.parse(date);
        } catch (ParseException e) {
            // Handle any errors that occur during parsing
            e.printStackTrace();
            Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert date to string
        String dateString = dateFormat.format(parsedDate);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("FormActivity", "Upload failed: " + exception.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Get the download URL for the image
                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Save the form data to Firebase Database
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference formRef = database.getReference("forms");
                        String key = formRef.push().getKey();

                        Map<String, String> formData = new HashMap<>();
                        formData.put("serialNo", serialNo);
                        formData.put("name", name);
                        formData.put("address", address);
                        formData.put("father", father);
                        formData.put("whatsapp", whatsapp);
                        formData.put("cnic", cnic);
                        formData.put("date", dateString);
                        formData.put("pictureUrl", uri.toString());

                        formRef.child(key).setValue(formData).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(FormActivity.this, "Form submitted successfully!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(FormActivity.this, "Form submission failed. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Open camera
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
            } else {
                Toast.makeText(this, "Camera permission is required to take picture", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

// ...
