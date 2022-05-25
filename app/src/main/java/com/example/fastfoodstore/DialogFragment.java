package com.example.fastfoodstore;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DialogFragment extends androidx.fragment.app.DialogFragment {

    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference_Payment;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Notice")
                .setMessage("Card Details deleted")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //Delete_card_details
                        auth = FirebaseAuth.getInstance();
                        databaseReference_Payment=database.getReference().child("Payment").child(auth.getUid());
                        databaseReference_Payment.removeValue();
                    }
                });
        return builder.create();
    }
}
