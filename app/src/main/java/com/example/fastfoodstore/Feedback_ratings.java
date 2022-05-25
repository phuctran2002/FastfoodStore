package com.example.fastfoodstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Feedback_ratings extends AppCompatActivity {

    private RadioButton customer_service_A;

    private RadioButton delivery_speed_A;

    private RadioButton quality_A;
    private RadioButton quality_B;
    private RadioButton quality_C;
    private RadioButton quality_D;

    private RadioGroup radioGroupA;
    private RadioGroup radioGroupB;
    private RadioGroup radioGroupC;



    private Button Rating;

    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReferenceRatings;
    private DatabaseReference databaseReferenceFeedback;
    private EditText feedback_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_ratings);


        feedback_text=findViewById(R.id.feedback_text);
        radioGroupA=findViewById(R.id.Customer_Service);
        radioGroupB=findViewById(R.id.Delivery);
        radioGroupC=findViewById(R.id.Quality);


        Rating = findViewById(R.id.Rating);

        Rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 String feedback=  feedback_text.getText().toString();
                auth = FirebaseAuth.getInstance();
                databaseReferenceFeedback = database.getReference().child("Feedback").child("Feedbacks").child(auth.getUid());
                databaseReferenceFeedback.child("feed").setValue(feedback);
                
                auth = FirebaseAuth.getInstance();
                databaseReferenceRatings = database.getReference().child("Feedback").child("Ratings").child(auth.getUid());


                int customer_service = radioGroupA.getCheckedRadioButtonId();
                int delivery_speed = radioGroupB.getCheckedRadioButtonId();
                int quality = radioGroupC.getCheckedRadioButtonId();

                customer_service_A = findViewById(customer_service);
                delivery_speed_A = findViewById(delivery_speed);
                quality_A = findViewById(quality);


                databaseReferenceRatings.child("Customer_Service").setValue(customer_service_A.getText());
                databaseReferenceRatings.child("Delivery_Speed").setValue(delivery_speed_A.getText());
                databaseReferenceRatings.child("Quality").setValue(quality_A.getText());

                startActivity(new Intent(getApplicationContext(), UserHome.class));






            }
        });

    }
}