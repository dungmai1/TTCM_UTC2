package com.maidanhdung.ecommerce.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.maidanhdung.ecommerce.databinding.ActivitySignInBinding;
import com.maidanhdung.ecommerce.databinding.ActivitySignUpBinding;

public class SignUp extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ecommercelogin-6d3ed-default-rtdb.firebaseio.com/");

    ActivitySignUpBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_sign_in);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EventClickSignup();
        EventClickCreate();
    }

    private void EventClickCreate() {
        binding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtLastName = binding.editTextLastName.getText().toString();
                String txtFirstName = binding.editTextFirstName.getText().toString();
                String txtEmailPhone = binding.editTextEmailPhone.getText().toString();
                String txtPassword = binding.editTextPassword.getText().toString();
                String txtRePassword = binding.editTextRePassword.getText().toString();
                if(txtLastName.isEmpty() || txtFirstName.isEmpty() || txtEmailPhone.isEmpty() || txtPassword.isEmpty() ||txtRePassword.isEmpty()){
                    Toast.makeText(SignUp.this,"Pleas enter all fields",Toast.LENGTH_LONG).show();
                }
                else if (!txtPassword.equals(txtRePassword)){
                    Toast.makeText(SignUp.this,"Password do not match",Toast.LENGTH_LONG).show();
                }
                else{
                                databaseReference.child("users").child(txtEmailPhone).child("lastname").setValue(txtLastName);
                                databaseReference.child("users").child(txtEmailPhone).child("firstname").setValue(txtFirstName);
                                databaseReference.child("users").child(txtEmailPhone).child("password").setValue(txtPassword);
                                Toast.makeText(SignUp.this,"Sign up successfully",Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }

        });
    }
    private void EventClickSignup() {
        binding.txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, SignIn.class);
                startActivity(intent);
            }
        });
    }
}