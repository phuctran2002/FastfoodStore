package com.example.fastfoodstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class order_confirmations extends AppCompatActivity {

    private TextView address_name;
    private TextView address_street;
    private TextView address_street2;
    private TextView address_city;
    private TextView address_contact;

    private TextView cardNo;
    private TextView card_name;


    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference_Payment;
    private DatabaseReference databaseReference_Address;

    private Button place;

    private TextView product_name;
    private TextView product_price;
    private TextView product_qty;
    private TextView product_description;
    private int resize;
    private ImageView product_img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmations);

        //Delivery Address
        address_name=findViewById(R.id.confirm_name);
        address_street=findViewById(R.id.confirm_street1);
        address_street2=findViewById(R.id.confirm_street2);
        address_city=findViewById(R.id.confirm_city);
        address_contact=findViewById(R.id.confirm_phn);

        //Product Details
        product_name=findViewById(R.id.product_name);
        product_price=findViewById(R.id.product_price);
        product_qty=findViewById(R.id.product_qty);
        product_description=findViewById(R.id.product_description);
        product_img=findViewById(R.id.product_img);

        //Payment Details
        cardNo=findViewById(R.id.confirm_cardNo);
        card_name=findViewById(R.id.confirm_cardName);
        place=findViewById(R.id.place);
        auth = FirebaseAuth.getInstance();
        databaseReference_Address=database.getReference().child("Addresss").child(auth.getUid());
        databaseReference_Payment=database.getReference().child("Payment").child(auth.getUid());


        String CakeName = getIntent().getStringExtra("CakeName");
        String cakeprice = getIntent().getStringExtra("cakeprice");
        String cakequantity = getIntent().getStringExtra("cakequantity");
        String cakedescription = getIntent().getStringExtra("cakedescription");


        product_name.setText(CakeName);
        product_price.setText(cakeprice);
        product_qty.setText(cakequantity);
        product_description.setText(cakedescription);



        //Address_Data_Retrive
        databaseReference_Address.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    String Phn= dataSnapshot.child("phoneNumber").getValue().toString();
                    String Name= dataSnapshot.child("names").getValue().toString();
                    String Address1= dataSnapshot.child("street1").getValue().toString();
                    String Address2= dataSnapshot.child("street3").getValue().toString();
                    String City= dataSnapshot.child("cities").getValue().toString();

                    address_name.setText(Name);
                    address_street.setText(Address1);
                    address_street2.setText(Address2);
                    address_city.setText(City);
                    address_contact.setText(Phn);
                }catch (Exception e){

                    address_name.setText("Name");
                    address_street.setText("APT");
                    address_street2.setText("Street");
                    address_city.setText("City");
                    address_contact.setText("Contact");
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                address_name.setText("");
                address_street.setText("");
                address_street2.setText("");
                address_city.setText("");
                address_contact.setText("");

            }
        });

        //Payment_data_retrive
        databaseReference_Payment.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    String Cardno = dataSnapshot.child("cardNo").getValue().toString();
                    String cardname = dataSnapshot.child("holderName").getValue().toString();
                    cardNo.setText(Cardno);
                    card_name.setText(cardname);
                }catch (Exception e){
                    cardNo.setText("");
                    card_name.setText("");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                cardNo.setText("");
                card_name.setText("");
            }
        });

        //Order_confirmation_Button_and_Loading
        place.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {

    final Loading loading = new Loading(order_confirmations.this);
       loading.PaymentLoadingAnimation();
       Handler handler = new Handler();
      handler.postDelayed(new Runnable() {
            @Override
           public void run() {
              loading.dismissDialog();
               Intent i = new Intent(getApplicationContext(), purchaseSuccess.class);
               startActivity(i);
            }
        }, 5000);
            }
        });


    }
}