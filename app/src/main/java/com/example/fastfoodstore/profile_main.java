package com.example.fastfoodstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile_main extends AppCompatActivity {
    private TextView user_name;
    private TextView user_contact_no;
    private TextView user_email;

    private TextView user_address_name;
    private TextView user_address_street;
    private TextView user_address_street2;
    private TextView user_address_city;
    private TextView user_address_contact;

    private TextView user_cardNo;
    private TextView user_card_name;

    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference_Payment;
    private DatabaseReference databaseReference_Address;
    private DatabaseReference databaseReferenceuser;

    private Button ratings;

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main);

        //Database_Auth
        auth = FirebaseAuth.getInstance();
        databaseReferenceuser = database.getReference().child("User").child(auth.getUid());
        databaseReference_Address=database.getReference().child("Addresss").child(auth.getUid());
        databaseReference_Payment=database.getReference().child("Payment").child(auth.getUid());

        //Registration_info
        user_name=findViewById(R.id.user_name);
        user_contact_no=findViewById(R.id.user_contact_no);
        user_email=findViewById(R.id.user_email);

        //Address_info
        user_address_name=findViewById(R.id.user_address_name);
        user_address_street=findViewById(R.id.user_address_street);
        user_address_street2=findViewById(R.id.user_address_street2);
        user_address_city=findViewById(R.id.user_address_city);
        user_address_contact=findViewById(R.id.user_address_contact);

        //Card_info
        user_cardNo=findViewById(R.id.user_cardNo);
        user_card_name=findViewById(R.id.user_card_name);
        ratings=findViewById(R.id.ratings);





        //Spinner_for_dropDown_User_registration_details
         spinner=findViewById(R.id.option1);
        adapter= ArrayAdapter.createFromResource(this,R.array.option1, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).equals(null)){
                    return;
                }
                 else if (adapterView.getItemAtPosition(i).equals("Edit")) {
                    Intent intent = new Intent(getApplicationContext(), user_progfile_edit.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        //Spinner_for_Address
        spinner=findViewById(R.id.option2);
        adapter= ArrayAdapter.createFromResource(this,R.array.option2, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).equals(null)){
                    return;
                }
                else if (adapterView.getItemAtPosition(i).equals("Edit")){
                    Intent intent=new Intent(getApplicationContext(),editAddress.class);
                    startActivity(intent);
                }else if(adapterView.getItemAtPosition(i).equals("Delete")){
                    //Call_delete_message_fragment
                    Fragment_delete_address fragment_delete_address= new Fragment_delete_address();
                    fragment_delete_address.show(getSupportFragmentManager(),"DialogFragment");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        //Spinner_for_Payment
        spinner=findViewById(R.id.option3);
        adapter= ArrayAdapter.createFromResource(this,R.array.option3, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).equals(null)){
                    return;
                }
                else if(adapterView.getItemAtPosition(i).equals("Delete")){
                    DialogFragment Dialog= new DialogFragment();
                    Dialog.show(getSupportFragmentManager(),"DialogFragment");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //Get_registration_details
        databaseReferenceuser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    String Emails= dataSnapshot.child("email").getValue().toString();
                    String Name= dataSnapshot.child("name").getValue().toString();
                    String contacts= dataSnapshot.child("phone").getValue().toString();
                    user_email.setText(Emails);
                    user_name.setText(Name);
                    user_contact_no.setText(contacts);
                }catch (Exception e){
                    user_email.setText("Emails");
                    user_name.setText("Name");
                    user_contact_no.setText("contacts");
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                user_email.setText(null);
                user_name.setText(null);
                user_contact_no.setText(null);
            }
        });

        //Get_address_details
        databaseReference_Address.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    String Phn= dataSnapshot.child("phoneNumber").getValue().toString();
                    String Name= dataSnapshot.child("names").getValue().toString();
                    String Address1= dataSnapshot.child("street1").getValue().toString();
                    String Address2= dataSnapshot.child("street3").getValue().toString();
                    String City= dataSnapshot.child("cities").getValue().toString();

                    user_address_name.setText(Name);
                    user_address_street.setText(Address1);
                    user_address_street2.setText(Address2);
                    user_address_city.setText(City);
                    user_address_contact.setText(Phn);
                }catch (Exception e){
                    user_address_name.setText("Name");
                    user_address_street.setText("APT");
                    user_address_street2.setText("Street2");
                    user_address_city.setText("City");
                    user_address_contact.setText("Contact");
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                user_address_name.setText("");
                user_address_street.setText("");
                user_address_street2.setText("");
                user_address_city.setText("");
                user_address_contact.setText("");

            }
        });

        //Get_Payment_details
        databaseReference_Payment.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    String Cardno = dataSnapshot.child("cardNo").getValue().toString();
                    String cardname = dataSnapshot.child("holderName").getValue().toString();
                    user_cardNo.setText(Cardno);
                    user_card_name.setText(cardname);
                }catch (Exception e){
                    user_cardNo.setText("Card No");
                    user_card_name.setText("Holder Name");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                user_cardNo.setText("");
                user_card_name.setText("");
            }
        });

        //Rating_feedback_Update_delete
        ratings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),feedback.class);
                startActivity(intent);
            }
        });








    }




}