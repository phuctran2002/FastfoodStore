package com.example.fastfoodstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Select_Payment_Method extends AppCompatActivity {

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
        setContentView(R.layout.activity_select__payment__method);

        btnCash=findViewById(R.id.btnCash);
        btncard=findViewById(R.id.btnCard);

        //Get_product_details_from_cake_page
        CakeName = getIntent().getStringExtra("Name");
        cakeprice = getIntent().getStringExtra("Price");
        cakequantity = getIntent().getStringExtra("Quantity");
        cakedescription = getIntent().getStringExtra("Description");

        Bundle bundle=getIntent().getExtras();
        resize = bundle.getInt("img");

        //Card_payment_navi
        btncard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Product_info
                Intent intent=new Intent(getApplicationContext(),Payment.class);
                intent.putExtra("Name",CakeName);
                intent.putExtra("Price",cakeprice);
                intent.putExtra("Quantity",cakequantity);
                intent.putExtra("Description",cakedescription);
                intent.putExtra("img",resize);
                startActivity(intent);
            }
        });

        //Cash_payment_Navi
        btnCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Product_info
                Intent i = new Intent(getApplicationContext(), Address.class);
                i.putExtra("Name",CakeName);
                i.putExtra("Price",cakeprice);
                i.putExtra("Quantity",cakequantity);
                i.putExtra("Description",cakedescription);
                i.putExtra("img",resize);
                startActivity(i);
            }
        });

    }
}