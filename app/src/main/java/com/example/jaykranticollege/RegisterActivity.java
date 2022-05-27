package com.example.jaykranticollege;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegisterActivity extends AppCompatActivity {
    public EditText emailId;

    //private final StorageReference storage= FirebaseStorage.getInstance().getReference();
    public EditText passwd;
    public EditText name;
    public EditText Phone;
    public EditText birthdate;
    public ImageView cover;
    Button btnSignUp, ch;
    FirebaseAuth firebaseAuth;
    ImageView img;
    UserData UserData;
    public Uri imguri;
    StorageReference mStorageRef;
    DatabaseReference dbreff;
    String uid1;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        firebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.email);
        name = findViewById(R.id.name);
        birthdate = findViewById(R.id.birthdate);
        Phone= findViewById(R.id.Phone);

        passwd = findViewById(R.id.password);
        btnSignUp = findViewById(R.id.upload);








        UserData=new UserData();

        mStorageRef = FirebaseStorage.getInstance().getReference("UserImage");
        dbreff = FirebaseDatabase.getInstance().getReference().child("UserData");
        ch.setOnClickListener(view -> Filechooser());


        btnSignUp.setOnClickListener(
                view -> {
            String emailID = emailId.getText().toString();
            String paswd = passwd.getText().toString();
            String phone=Phone.getText().toString().trim();
            String names=name.getText().toString().trim();
            if(names.isEmpty())
            {
                name.setError("Enter valid no");
                name.requestFocus();
                return;
            }


            phoneNumber="+91"+phone;

            if (emailID.isEmpty()) {
                emailId.setError("Provide your Email first!");
                emailId.requestFocus();
            } else if (paswd.isEmpty()) {
                passwd.setError("Set your password");
                passwd.requestFocus();
            } else if (emailID.isEmpty() && paswd.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
            } else if (!(emailID.isEmpty() && paswd.isEmpty())) {
                firebaseAuth.createUserWithEmailAndPassword(emailID, paswd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {

                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "SignUp unsuccessful: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null) {
                                uid1 = user.getUid();
                                Fileuploader(uid1);
                                Intent intent =new Intent(RegisterActivity.this,MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    }
                });
            } else {
                Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public String getExtension(Uri uri)
    {
        ContentResolver cr=getContentResolver();
        MimeTypeMap mimiTypeMap=MimeTypeMap.getSingleton();
        return mimiTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    public void Fileuploader(final String uid1)
    {


        final String imageid;
        imageid=System.currentTimeMillis()+"."+getExtension(imguri);
        UserData.setName(name.getText().toString().trim());
        UserData.setEmail(emailId.getText().toString().trim());
        UserData.setPhone(Phone.getText().toString().trim());
        UserData.setBirthdate(birthdate.getText().toString().trim());
        UserData.setUserid(uid1);
        UserData.setImageId(imageid);
        final StorageReference Ref=mStorageRef.child(imageid);
        Ref.putFile(imguri)
                .addOnSuccessListener(taskSnapshot -> Toast.makeText(RegisterActivity.this,"IMAGE UPLOADED",Toast.LENGTH_LONG).show())
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@androidx.annotation.NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
        dbreff.child(uid1).setValue(UserData);

    }

    private void Filechooser() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode== Activity.RESULT_OK && data!=null && data.getData()!=null)
        {
            imguri=data.getData();
            img.setImageURI(imguri);

        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}