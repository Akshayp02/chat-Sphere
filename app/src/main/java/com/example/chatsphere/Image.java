package com.example.chatsphere;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.example.chatsphere.databinding.ActivityImageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Map;

public class Image extends AppCompatActivity {
    private ActivityImageBinding binding;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private Uri imageUri;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference documentReference;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseStorage = FirebaseStorage.getInstance(); // Initialize FirebaseStorage

        storageReference = firebaseStorage.getReference("profile images"); // Use getReference method

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUid = user.getUid(); // Use camelCase for variable names

        binding.btnDelete.setVisibility(View.INVISIBLE);

        geturl();

        documentReference = db.collection("users").document(currentUid); // Simplify documentReference initialization

        db.runTransaction(transaction -> {
            transaction.update(documentReference, "image", "");
            return null;
        }).addOnSuccessListener(aVoid -> {
            binding.pvIvupload.setVisibility(View.INVISIBLE);

            Map<String, Object> map = documentReference.get().getResult().getData();
            if (map != null) {
                map.put("image", "");
            }
        });

        binding.btnPick.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(intent, 1);
        });

        binding.btnSave.setOnClickListener(v -> {
            if (imageUri != null) {
                StorageReference image = storageReference.child(currentUid + ".jpg");
                image.putFile(imageUri).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        image.getDownloadUrl().addOnSuccessListener(uri -> {
                            url = uri.toString();
                            documentReference.update("image", url);
                            binding.btnDelete.setVisibility(View.VISIBLE);
                        });
                    } else {
                        // Handle the error
                        Toast.makeText(Image.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                // Handle the case where imageUri is null
                Toast.makeText(Image.this, "Please pick an image first", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnDelete.setOnClickListener(v -> {
            if (url != null) {
                FirebaseStorage.getInstance().getReferenceFromUrl(url).delete().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Image.this, "Deleted", Toast.LENGTH_SHORT).show();
                    } else {
                        // Handle the error
                        Toast.makeText(Image.this, "Failed to delete image", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void geturl() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUid = user.getUid();

        documentReference = db.collection("users").document(currentUid);
        documentReference.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                url = documentSnapshot.getString("image");
                if (url != null && !url.equals("")) {
                    binding.btnDelete.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
