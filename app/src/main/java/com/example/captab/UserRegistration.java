package com.example.captab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UserRegistration extends AppCompatActivity {

    private EditText inputEmail, inputPassword,nameEditText,address,phoneEditText,passwordEditText;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private CheckBox buyerCheckBox, sellerCheckBox;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private static final String USERS = "users";
    private static final String SELLERS = "sellers";
    private String TAG = "RegisterActivity";
    private String username, fname, email, adress, phone, workplace;
    private String password;
    private User user;
    String UserType;
    private FirebaseAuth mAuth;
    Context mctx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        nameEditText = findViewById(R.id.name);
        address = findViewById(R.id.address);
        phoneEditText = findViewById(R.id.phone);
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        btnSignIn =  findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.example.captab.UserRegistration.this, MainActivity.class));
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                fname = nameEditText.getText().toString();
                phone = phoneEditText.getText().toString();
                adress = address.getText().toString();
                user = new User(fname, adress, phone, email, password);

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                FirebaseUser user = mAuth.getCurrentUser();
                String uid = user.getUid();

                //this is to enter all the details under the UID of currently logged in user
                Toast.makeText(UserRegistration.this, uid, Toast.LENGTH_LONG).show();

                mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                HashMap<String, String> userMap = new HashMap<String, String>();
                userMap.put("name", fname);
                userMap.put("address", adress);
                userMap.put("phone", phone);

                mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            // Toast.makeText(UpdateUserSettings.this, "Your settings have been updated", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(UserRegistration.this, MainActivity.class));
                        }
                        else{
                            Toast.makeText(UserRegistration.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });



            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    public void LoginUser(View view){



        Intent intent = new Intent(com.example.captab.UserRegistration.this, MainActivity.class);
        startActivity(intent);


    }




}