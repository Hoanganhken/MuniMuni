package com.example.englandhoang.munimuni.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.englandhoang.munimuni.Main.MainActivity;
import com.example.englandhoang.munimuni.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class LoginActivity extends AppCompatActivity {

    private EditText mLoginEmail, mLoginPassword;

    private Button mLogin_btn;

    private ProgressDialog mLoginProgress;

    private FirebaseAuth mAuth;

    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mLoginProgress = new ProgressDialog(this);

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mLoginEmail = (EditText) findViewById(R.id.login_email);
        mLoginPassword = (EditText) findViewById(R.id.login_password);
        mLogin_btn = (Button) findViewById(R.id.login_btn);

        mLogin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mLoginEmail.getText().toString();
                String password = mLoginPassword.getText().toString();

                if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {

                    mLoginProgress.setTitle("Đợi tí!");
                    mLoginProgress.setMessage("Đang đăng nhập...");
                    mLoginProgress.setCanceledOnTouchOutside(false);
                    mLoginProgress.show();

                    loginUser(email, password);
                }
            }
        });


    }

    private void loginUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    mLoginProgress.dismiss();

                    String current_user_id = mAuth.getCurrentUser().getUid();

                    String deviceToken = FirebaseInstanceId.getInstance().getToken();

                    mUserDatabase.child(current_user_id).child("device_token").setValue(deviceToken).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mainIntent);
                            finish();

                        }
                    });


                } else {

                    mLoginProgress.hide();

                    Toast.makeText(LoginActivity.this, "Lỗi! Kiểm tra lại thông tin tài khoản.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
