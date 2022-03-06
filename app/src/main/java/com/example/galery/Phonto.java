package com.example.galery;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class Phonto extends AppCompatActivity {

    private StorageReference mStorageRef;
    ImageView imageViewFoto;
    Button guardaFotoFire;
    FirebaseStorage storage;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//Phonto
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonto);

        guardaFotoFire = findViewById(R.id.guardaFoto);

        storage = FirebaseStorage.getInstance();



        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},0);
        }
        imageViewFoto = (ImageView) findViewById(R.id.imageView);
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tirarFoto();
            }
        });






        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if(result != null){
                            imageViewFoto.setImageURI(result);
                            imageUri = result;
                        }
                    }
        });

        imageViewFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("image/*");
            }
        });

        guardaFotoFire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });
    }

    private void uploadImage() {

        if(imageUri != null){
            StorageReference reference = storage.getReference().child("image/" + UUID.randomUUID().toString());
            reference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Phonto.this, "foto guardada com sucesso", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Phonto.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    public void tirarFoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

//    public void guardaFoto(){
//        Bitmap bitmap = ((BitmapDrawable)imageViewFoto.getDrawable()).getBitmap();
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//        byte[] imagem = byteArrayOutputStream.toByteArray();
//
//        StorageReference imgRef = mStorageRef.child("img-001.jpeg");
//        UploadTask uploadTask = imgRef.putBytes(imagem);
//        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                //Toast.makeText(this,"enviado com sucesso",Toast.LENGTH_LONG).show();
//            }
//        });
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 1 && resultCode == RESULT_OK){
            Bundle  extras = data.getExtras();
            Bitmap imagem = (Bitmap) extras.get("data");
            imageViewFoto.setImageBitmap(imagem);

        }        super.onActivityResult(requestCode, resultCode, data);
    }
//endPhonto

    //navegarPraLocation
    public void searchLocation(View view){
        startActivity(new Intent(Phonto.this, Location.class));
    }
//endNavegarPraLocation

//menu

    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_principal, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.itemSalvar:
                Toast.makeText(Phonto.this,"salvo com sucesso",Toast.LENGTH_LONG).show();
                break;
            case R.id.itemLocalizacao:
                startActivity(new Intent(Phonto.this, Location.class));
                break;
            case R.id.itemSair:
                startActivity(new Intent(Phonto.this, MainActivity.class));

                break;
        }
        return super.onOptionsItemSelected(item);

    }
}