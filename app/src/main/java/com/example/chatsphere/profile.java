package com.example.chatsphere;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.chatsphere.databinding.ActivityProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class profile extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String CurentUserId = user.getUid();
        String documentReference = String.valueOf(db.collection("users").document(CurentUserId));


        binding.profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile.this, profile.class);
                startActivity(intent);
            }
        });

        binding.btnADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String btnStatus = binding.btnADD.getText().toString();
                if(btnStatus.equals("ADD")){
                    binding.btnADD.setText("ADDED");
                   // shownamesheet();
                    db.collection("users").document(CurentUserId).update("status", "ADDED");
                }
                else{
                    binding.btnADD.setText("ADD");
                   db.collection("users").document(CurentUserId).update("status", "ADD");
                  //  editsheet();
                }

            }
        });

        binding.btnLOGOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(profile.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        getdata();

    }

    private void getdata() {
        final Dialog dialog = new Dialog(profile.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.profile_bs);
        // TODO: Bottom Sheet Code here



        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.BottomAnim;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

    private void editsheet() {
    }

    private void shownamesheet() {
    }
}