package com.truongvu.gymer.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.truongvu.gymer.R;
import com.truongvu.gymer.ultil.ItemBaiTap;

import java.io.FileDescriptor;
import java.io.IOException;

public class EditPost extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 123 ;
    private TextInputEditText up_td;
    private  TextInputEditText up_nd;
    private ImageView up_img, back, img_old;
    private Button btn_load;
    private ProgressBar up_probar;
    private Uri imageUri;
    private StorageReference mStorage;
    private DatabaseReference mReference;
    private FirebaseAuth mAuth;
    private ItemBaiTap mData;
    private String keyID, idpost;
    private TextInputEditText up_link;
    private ItemBaiTap itemBaiTap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        mStorage = FirebaseStorage.getInstance().getReference().child("images");
        mReference = FirebaseDatabase.getInstance().getReference("Posts");
        keyID = mReference.push().getKey();
        mAuth = FirebaseAuth.getInstance();
        idpost = getIntent().getStringExtra("idpost");

        anhxa();
        getData();

        getavatar();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getData() {
        mReference.child(idpost).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot!= null){
                    itemBaiTap = dataSnapshot.getValue(ItemBaiTap.class);
                    up_td.setText(itemBaiTap.getTitle());
                    up_nd.setText(itemBaiTap.getContent());
                    up_link.setText(itemBaiTap.getLink());
                    try {
                        Picasso.get().load(itemBaiTap.getImage()).into(img_old);
                    }catch (Exception e){}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getavatar() {
        up_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        btn_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri==null){

                    mData = new ItemBaiTap(
                            idpost,itemBaiTap.getImage() ,
                            up_link.getText().toString(),
                            up_nd.getText().toString(),
                            up_td.getText().toString(),
                            mAuth.getCurrentUser().getUid()

                    );
                    mReference.child(idpost).setValue(mData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(EditPost.this,"Chỉnh sửa thành công!!", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
                }
                else{
                    up_probar.setVisibility(View.VISIBLE);
                    btn_load.setVisibility(View.GONE);
                    final StorageReference user_profile = mStorage.child(keyID + ".jpg");
                    user_profile.putFile(imageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()){
                                throw task.getException();
                            }
                            return user_profile.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()){
                                Uri pathURL = task.getResult();
                                mData = new ItemBaiTap(
                                        idpost, pathURL.toString(),
                                        up_link.getText().toString(),
                                        up_nd.getText().toString(),
                                        up_td.getText().toString(),
                                        mAuth.getCurrentUser().getUid()

                                );
                                mReference.child(idpost).setValue(mData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(EditPost.this,"Chỉnh sửa thành công!!", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                });
                            } else {
                                up_probar.setVisibility(View.GONE);
                                btn_load.setVisibility(View.VISIBLE);
                                Toast.makeText(EditPost.this, "Lỗi: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void anhxa() {
        up_nd=findViewById(R.id.up_noidung_edit);
        up_td=findViewById(R.id.up_tieude_edit);
        up_img=findViewById(R.id.img_load_edit);
        img_old=findViewById(R.id.img_load_edit_old);
        up_link=findViewById(R.id.up_link_edit);
        btn_load=findViewById(R.id.btnload_edit);
        up_probar=findViewById(R.id.up_probar_edit);
        back = findViewById(R.id.up_back_edit);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            imageUri = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(imageUri,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();


            Bitmap bmp = null;
            try {
                bmp = getBitmapFromUri(imageUri);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            up_img.setImageBitmap(bmp);
            up_img.setPadding(0,0,0,0);

            //Upload hình

        }


    }



    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

}
