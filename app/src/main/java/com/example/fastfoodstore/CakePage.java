package com.example.fastfoodstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class CakePage extends Fragment {
    private ImageView cakeImage;
    private TextView cakeName;
    private TextView cakePrice;
    private TextView cakeQuantity;
    private TextView cakeDescription;

    private Button addToCartButton;
    private Button buyNowButton;
    private Bundle bundle;
    private String Email;
    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference allItems = database.getReference().child("All_Cakes");
    private DatabaseReference cartReference = database.getReference().child("Cart");
   private DatabaseReference databaseReferenceAddress;
    private DatabaseReference databaseReference_Payment;
    private String name,carNo;
    private ImageButton ratingsbutton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cake_page, container, false);
        bundle = this.getArguments();
        cakeImage = view.findViewById(R.id.display_image);
        cakeName = view.findViewById(R.id.display_name);
        cakePrice = view.findViewById(R.id.display_price);
        cakeQuantity = view.findViewById(R.id.display_quantity);
        cakeDescription = view.findViewById(R.id.display_Description);

        addToCartButton = view.findViewById(R.id.add_to_cart_button);
        buyNowButton = view.findViewById(R.id.buy_now_button);

        ratingsbutton=view.findViewById(R.id.ratingsbutton);


        auth = FirebaseAuth.getInstance();
        cartReference = cartReference.child(auth.getCurrentUser().getUid());

        displayItemDetails();
        addToCart();


        buyNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), Select_Payment_Method.class);

                String cakename = cakeName.getText().toString();
                String cakeprice = cakePrice.getText().toString();
                String cakequantity = cakeQuantity.getText().toString();
                String cakedescription = cakeDescription.getText().toString();

                i.putExtra("cakename",cakename);
                i.putExtra("cakeprice",cakeprice);
                i.putExtra("cakequantity",cakequantity);
                i.putExtra("cakedescription",cakedescription);
                startActivity(i);
            }
        });




        ratingsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getContext(),Feedback_ratings.class);
                startActivity(intent);
            }
        });







        return view;
    }

    private void addToCart() {
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = bundle.getString("cakeName");
                String itemPrice = bundle.getString("cakePrice");
                String itemImage = bundle.getString("cakeImage");
                String itemDescription = bundle.getString("Description");
                String itemId = cartReference.push().getKey();
                int itemQuantity = 1;
                int totalPrice = Integer.parseInt(itemPrice);

                Cart newCartItem = new Cart(itemId, itemName, itemPrice, itemImage,itemDescription, itemQuantity, totalPrice);
                cartReference.child(itemId).setValue(newCartItem);
                Toast.makeText(getContext(), "Item Added to Cart", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void displayItemDetails() {
        Picasso.get().load(bundle.getString("cakeImage")).into(cakeImage);
        cakeName.setText(bundle.getString("cakeName"));
        cakePrice.setText(bundle.getString("cakePrice"));
        cakeQuantity.setText(bundle.getString("cakeQuantity"));
        cakeDescription.setText(bundle.getString("cakeDescription"));



}


}
