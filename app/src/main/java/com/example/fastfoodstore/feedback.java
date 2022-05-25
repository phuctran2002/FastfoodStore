package com.example.fastfoodstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class feedback extends AppCompatActivity {

    EditText txtfeedbakc;
    Button update,delete;
    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReferenceFeedback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        txtfeedbakc=findViewById(R.id.txtfeedback);
        update=findViewById(R.id.update);
        delete=findViewById(R.id.delete);




        auth = FirebaseAuth.getInstance();
        databaseReferenceFeedback = database.getReference().child("Feedback").child("Feedbacks").child(auth.getUid());
        databaseReferenceFeedback.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                try {
                    String Feedback = dataSnapshot.child("feed").getValue().toString();
                    txtfeedbakc.setText(Feedback);
                }catch (Exception e){
                    txtfeedbakc.setText("No feedbacks");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                txtfeedbakc.setText("No feedbacks");
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),feedback_update.class);
                startActivity(intent);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth = FirebaseAuth.getInstance();
                databaseReferenceFeedback = database.getReference().child("Feedback").child("Feedbacks").child(auth.getUid());
                databaseReferenceFeedback.child("feed").removeValue();

                Toast.makeText(getApplicationContext(), "Feedback Deleted", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(getApplicationContext(),profile_main.class);
                startActivity(intent);


            }
        });

    }
}