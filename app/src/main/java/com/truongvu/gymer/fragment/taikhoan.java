package com.truongvu.gymer.fragment;

import android.arch.lifecycle.Lifecycle;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.truongvu.gymer.R;
import com.truongvu.gymer.activity.MainActivity;
import com.truongvu.gymer.ultil.Users;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class taikhoan extends Fragment {

    View view;
    private EditText editns, editsdt,editcc, editcn;
    private Button btncn;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private Users users;
    private CircleImageView circleImageView;
    private TextView name;
    private TextView email;

    private String userID;

    @Override
    public Lifecycle getLifecycle() {
        return super.getLifecycle();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.taikhoan, container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //getActivity().setTitle("Tài Khoản");

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(userID);

        AnhXa();
        name.setText(mAuth.getCurrentUser().getDisplayName()+"");
        email.setText(mAuth.getCurrentUser().getEmail()+"");
        Picasso.get().load(mAuth.getCurrentUser().getPhotoUrl()).into(circleImageView);
        loadData();
        setControl();
    }

    private void loadData() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Users load = dataSnapshot.getValue(Users.class);
                    editns.setText(load.getNgSing());
                    editsdt.setText(load.getSDT()+"");
                    editcc.setText(load.getChieuCao()+"");
                    editcn.setText(load.getCanNang()+"");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void AnhXa(){
        editns = view.findViewById(R.id.ieditns);
        editcc = view.findViewById(R.id.ieditcc);
        editcn = view.findViewById(R.id.ieditcn);
        editsdt = view.findViewById(R.id.ieditsdt);
        btncn = view.findViewById(R.id.btncn);
        email=view.findViewById(R.id.txtemail);
        name = view.findViewById(R.id.tk_name);
        circleImageView = view.findViewById(R.id.image_av);
    }

    private void setControl(){
        btncn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editns.getText().toString().matches("")
                ||editcc.getText().toString().matches("")
                ||editcn.getText().toString().matches("")
                ||editsdt.getText().toString().matches("")){
                    Toast.makeText(getContext(), "Bạn cần nhập đầy đủ thông tin!",Toast.LENGTH_LONG).show();
                }else {

                    if (userID.matches("hhawBjKHfxYH068pbMsfL2VZhAj2")){
                        users = new Users(editns.getText().toString(),
                                editsdt.getText().toString(),
                                Float.parseFloat(editcc.getText().toString()),
                                Float.parseFloat(editcn.getText().toString()),1,userID);
                        mDatabase.setValue(users)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getContext(), "Cập nhật thông tin thành công!",Toast.LENGTH_LONG).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(), "Cập nhật thông tin thất bại!",Toast.LENGTH_LONG).show();
                                    }
                                });
                    }
                    else {
                        users = new Users(editns.getText().toString(),
                                editsdt.getText().toString(),
                                Float.parseFloat(editcc.getText().toString()),
                                Float.parseFloat(editcn.getText().toString()),0,userID);
                        mDatabase.setValue(users)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getContext(), "Cập nhật thông tin thành công!",Toast.LENGTH_LONG).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(), "Cập nhật thông tin thất bại!",Toast.LENGTH_LONG).show();
                                    }
                                });
                    }
                }
            }
        });
    }

}
