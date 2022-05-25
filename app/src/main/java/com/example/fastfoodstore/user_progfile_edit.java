package com.example.fastfoodstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.HashMap;
import java.util.Map;

public class user_progfile_edit extends AppCompatActivity {


    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReferenceuser;

    private EditText txtuser_profile_edit_name;
    private EditText txtuserprofile_editemail;
    private EditText userprofile_editcontactNo;
    private EditText userprofile_editpwd;
    private EditText userprofile_editconfirmPassword;

    private Button btnupdate_userprofile_details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_progfile_edit);

        txtuser_profile_edit_name=findViewById(R.id.txtuser_profile_edit_name);
        txtuserprofile_editemail=findViewById(R.id.txtuserprofile_editemail);
        userprofile_editcontactNo=findViewById(R.id.userprofile_editcontactNo);
        userprofile_editpwd=findViewById(R.id.userprofile_editpwd);
        userprofile_editconfirmPassword=findViewById(R.id.userprofile_editconfirmPassword);

        btnupdate_userprofile_details=findViewById(R.id.btnupdate_userprofile_details);

        auth = FirebaseAuth.getInstance();
        databaseReferenceuser = database.getReference().child("User").child(auth.getUid());

        databaseReferenceuser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Emails= dataSnapshot.child("email").getValue().toString();
                String Name= dataSnapshot.child("name").getValue().toString();
                String contacts= dataSnapshot.child("phone").getValue().toString();
                String password= dataSnapshot.child("password").getValue().toString();
                txtuserprofile_editemail.setText(Emails);
                txtuser_profile_edit_name.setText(Name);
                userprofile_editcontactNo.setText(contacts);
                userprofile_editpwd.setText(password);
                userprofile_editconfirmPassword.setText(password);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                txtuserprofile_editemail.setText(null);
                txtuser_profile_edit_name.setText(null);
                userprofile_editcontactNo.setText(null);
                userprofile_editpwd.setText(null);
                userprofile_editconfirmPassword.setText(null);
            }
        });

        btnupdate_userprofile_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_user_details();
            }
        });



    }

    private void update_user_details(){

        txtuser_profile_edit_name=findViewById(R.id.txtuser_profile_edit_name);
        txtuserprofile_editemail=findViewById(R.id.txtuserprofile_editemail);
        userprofile_editcontactNo=findViewById(R.id.userprofile_editcontactNo);
        userprofile_editpwd=findViewById(R.id.userprofile_editpwd);
        userprofile_editconfirmPassword=findViewById(R.id.userprofile_editconfirmPassword);


        String user_profile_name= txtuser_profile_edit_name.getText().toString();
        String userprofile_email=txtuserprofile_editemail.getText().toString();
        String userprofile_contactNo=userprofile_editcontactNo.getText().toString();
        String userprofile_pwd=userprofile_editpwd.getText().toString();
        String userprofile_confirmPassword=userprofile_editconfirmPassword.getText().toString();

        if (!user_profile_name.isEmpty()){
            if(!userprofile_email.isEmpty()){
                if (!userprofile_contactNo.isEmpty()){
                    if(!userprofile_pwd.isEmpty()){
                        if (!userprofile_confirmPassword.isEmpty()){

                            if (userprofile_pwd.equals(userprofile_confirmPassword)){
                                Map<String,Object> updateuser_details=new HashMap<>();
                                updateuser_details.put("name",user_profile_name);
                                updateuser_details.put("email",userprofile_email);
                                updateuser_details.put("phone",userprofile_contactNo);
                                updateuser_details.put("password",userprofile_pwd);
                                databaseReferenceuser.updateChildren(updateuser_details);

                                Fragment_userdata_update fragment_userdata_update= new Fragment_userdata_update();
                                fragment_userdata_update.show(getSupportFragmentManager(),"DialogFragment");

                            }else Toast.makeText(getApplicationContext(), "Password not match", Toast.LENGTH_SHORT).show();
                        } else userprofile_editconfirmPassword.setError("Enter confirmation password");
                    } else userprofile_editpwd.setError("Enter password");
                } else userprofile_editcontactNo.setError("Enter phone numbrt");
            } else txtuserprofile_editemail.setError("Enter email");
        } else txtuser_profile_edit_name.setError("Enter name");
    }


}