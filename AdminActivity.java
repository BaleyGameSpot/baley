package com.card.my;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;

import java.util.ArrayList;
public class AdminActivity extends AppCompatActivity {

    private ListView formListView;
    private FormAdapter formAdapter;
    private ArrayList<Form> formList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        formListView = findViewById(R.id.formListView);
        formList = new ArrayList<>(); // Initialize the form list
        formAdapter = new FormAdapter(formList, this); // Pass the form list and context to the FormAdapter
        formListView.setAdapter(formAdapter); // set the adapter to list view
        formListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Form form = formList.get(position);
                Intent editFormIntent = new Intent(AdminActivity.this, EditFormActivity.class);
                editFormIntent.putExtra("FORM", (CharSequence) form);
                startActivity(editFormIntent);
            }
        });

        fetchForms();
    }

    private void fetchForms() {
        DatabaseReference formsRef = databaseReference.child("forms");

        formsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // clear existing forms
                formList.clear();

                // loop through all forms in the database and add them to the formList
                for (DataSnapshot formSnapshot : dataSnapshot.getChildren()) {
                    Form form = formSnapshot.getValue(Form.class);
                    formList.add(form);
                }

                // notify the formAdapter that the data has changed
                formAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("AdminActivity", "Failed to read forms", databaseError.toException());
            }
        });
    }

}
