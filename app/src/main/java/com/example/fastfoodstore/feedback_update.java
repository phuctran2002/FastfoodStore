package com.example.fastfoodstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class feedback_update extends AppCompatActivity {

    EditText txtupdatefeedback;
    Button btnupdate;

    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReferenceFeedback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_update);

        txtupdatefeedback=findViewById(R.id.txtupdatefeedback);
        btnupdate=findViewById(R.id.btnupdate);


        auth = FirebaseAuth.getInstance();
        databaseReferenceFeedback = database.getReference().child("Feedback").child("Feedbacks").child(auth.getUid());
        databaseReferenceFeedback.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    String Feedback = dataSnapshot.child("feed").getValue().toString();
                    txtupdatefeedback.setText(Feedback);
                }catch (Exception e){
                    txtupdatefeedback.setText("No feedbacks");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                txtupdatefeedback.setText("No FeedBacks");
            }
        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String feed= txtupdatefeedback.getText().toString();

                Map<String,Object> updateValues=new HashMap<>();
                updateValues.put("feed",feed);
                databaseReferenceFeedback.updateChildren(updateValues);
                Intent intent =new Intent(getApplicationContext(),feedback.class);
                startActivity(intent);
            }
        });


    }
}