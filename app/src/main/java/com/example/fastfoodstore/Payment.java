package com.example.fastfoodstore;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Payment extends AppCompatActivity {

    private EditText HolderName;
    private Button Payment_Done_Summary_page;
    private DatabaseReference databaseReference;
    private EditText CardNo,MM,YY,CVV;

    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference_Payment;
    private String CakeName;
    private String cakeprice;
    private String cakequantity;
    private String cakedescription;
    private int resize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        //Create_payment_form
        CardForm cardForm = findViewById(R.id.card_form);
        cardForm.cardRequired(true);
        cardForm.expirationRequired(true);
        cardForm.cvvRequired(true);
        cardForm.setup(Payment.this);

        //Get_input_values
        CVV = cardForm.getCvvEditText();
        CardNo = cardForm.getCardEditText();
        MM = cardForm.getExpirationDateEditText();
        HolderName = findViewById(R.id.txtEditname);

        Payment_Done_Summary_page = findViewById(R.id.btnPayment);


        //Product_details
        CakeName = getIntent().getStringExtra("CakeName");
        cakeprice = getIntent().getStringExtra("cakeprice");
        cakequantity = getIntent().getStringExtra("cakequantity");
        cakedescription = getIntent().getStringExtra("cakedescription");


        Payment_Done();
    }

    private void Payment_Done(){
        Payment_Done_Summary_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NameOfHolder = HolderName.getText().toString();
                String CardNumber = CardNo.getText().toString();
                String Month = MM.getText().toString();
                String SecurityCode = CVV.getText().toString();

                if (!NameOfHolder.isEmpty()){
                    if(!CardNumber.isEmpty()){
                        if(!Month.isEmpty()){
                            if (!SecurityCode.isEmpty()){
                                PaymentInsert();
                            }
                            else{ Toast.makeText(Payment.this, "Required Security Code", Toast.LENGTH_SHORT).show();}
                        }
                        else{ Toast.makeText(Payment.this, "Required Date", Toast.LENGTH_SHORT).show(); }
                    }
                    else{ Toast.makeText(Payment.this, "Required Card Number", Toast.LENGTH_SHORT).show(); }
                }
                else{ Toast.makeText(Payment.this, "Required Holder Name", Toast.LENGTH_SHORT).show(); }
            }
        });
    }

    //Insert_payment_Details
    private void PaymentInsert(){
        auth = FirebaseAuth.getInstance();
        databaseReference_Payment=database.getReference().child("Payment").child(auth.getUid());
        String NameOfHolder = HolderName.getText().toString();
        String CardNumber = CardNo.getText().toString();
        String Month = MM.getText().toString();
        String SecurityCode = CVV.getText().toString();

        PaymentHandle payments = new PaymentHandle(CardNumber, NameOfHolder, SecurityCode, Month);
        databaseReference_Payment.setValue(payments);

        Intent i = new Intent(getApplicationContext(), Address.class);
        i.putExtra("CakeName",CakeName);
        i.putExtra("cakeprice",cakeprice);
        i.putExtra("cakequantity",cakequantity);
        i.putExtra("cakedescription",cakedescription);

        startActivity(i);
    }
}
