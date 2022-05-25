package com.example.fastfoodstore;

        import android.app.AlertDialog;
        import android.app.Dialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.Bundle;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;

        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

public class Fragment_delete_address extends androidx.fragment.app.DialogFragment {

    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference_address;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Notice")
                .setMessage("Delivery Address deleted")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //Delete_address_details
                        auth = FirebaseAuth.getInstance();
                        databaseReference_address=database.getReference().child("Addresss").child(auth.getUid());
                        databaseReference_address.removeValue();
                        Intent intent=new Intent(getContext(),profile_main.class);
                        startActivity(intent);
                    }
                });
        return builder.create();
    }
}
