package com.example.fastfoodstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class Address extends AppCompatActivity {

    private EditText Name, phn, phn2, Street, Street2, City;
    private Button SaveAddress;
    private String phoneNumbers;
    private String carNo, name;

    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReferenceAddress;
    private DatabaseReference databaseReference_Payment;
    private String [] SPINNERLIST={"Da Nang","Quang Nam","Hue","Sai Gon","Ha Noi","Binh Phuoc","Quang Binh","Ninh Thuan"};


    private Button btnCash;
    private Button btncard;
    private String CakeName;
    private String cakeprice;
    private String cakequantity;
    private String cakedescription;
    private int resize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,SPINNERLIST);

        MaterialBetterSpinner BetterSpinner= findViewById(R.id.android_material_design_spinner);
        BetterSpinner.setAdapter(arrayAdapter);

        //Get_Address_ID
        Name = findViewById(R.id.txtEditname);
        phn = findViewById(R.id.txtEditCont);
        Street = findViewById(R.id.txteditStreet);
        Street2 = findViewById(R.id.txteditStreet2);
        City = findViewById(R.id.android_material_design_spinner);
        SaveAddress = findViewById(R.id.btnAddress);

        //Get_Product_details
        CakeName = getIntent().getStringExtra("CakeName");
        cakeprice = getIntent().getStringExtra("cakeprice");
        cakequantity = getIntent().getStringExtra("cakequantity");
        cakedescription = getIntent().getStringExtra("cakedescription");
        Save_Address_Details();

    }

    private void Save_Address_Details(){
        SaveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String BuyerName = Name.getText().toString();
                String PhoneNumber = phn.getText().toString();
                String Street_one = Street.getText().toString();
                String Street_two = Street2.getText().toString();
                String Cit = City.getText().toString();

                if (!BuyerName.isEmpty()) {
                    if (!PhoneNumber.isEmpty()) {
                        if (!Street_one.isEmpty()) {
                            if (!Cit.isEmpty())
                            {
                                insertAddress();

                            }
                            else {City.setError("select city");}
                        } else { Street.setError("enter street");}
                    } else { phn.setError("enter contact number");}
                } else { Name.setError("enter name");}
            }
        });
    }

    //Insert_Address_to_the_Database
    private void insertAddress() {
        auth = FirebaseAuth.getInstance();
        databaseReferenceAddress = database.getReference().child("Addresss").child(auth.getUid());

        String BuyerName = Name.getText().toString();
        String PhoneNumber = phn.getText().toString();
        String Street_one = Street.getText().toString();
        String Street_two = Street2.getText().toString();
        String Cit = City.getText().toString();

        AddressHandle AddressHD = new AddressHandle(BuyerName, PhoneNumber, Street_one, Street_two, Cit);
        databaseReferenceAddress.setValue(AddressHD);

        //Send_Product_Data_to_the_Confirmation_page
        Intent intent =new Intent(getApplicationContext(),order_confirmations.class);
        intent.putExtra("CakeName",CakeName);
        intent.putExtra("cakeprice",cakeprice);
        intent.putExtra("cakequantity",cakequantity);
        intent.putExtra("cakedescription",cakedescription);
        startActivity(intent);




    }
    }






