package com.truongvu.gymer.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.truongvu.gymer.R;
import com.truongvu.gymer.activity.UpLoadPostActivity;
import com.truongvu.gymer.adapter.AdminAdapter;
import com.truongvu.gymer.adapter.adapterbaitap;
import com.truongvu.gymer.ultil.ItemBaiTap;

import java.util.ArrayList;
import java.util.List;


public class AdminFragment extends Fragment {

    private View view;
    private TextView add;
    private RecyclerView rcv;
    private ArrayList<ItemBaiTap> list;
    private AdminAdapter adapter;
    private DatabaseReference mReference;
    public AdminFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        add = view.findViewById(R.id.addnew_admin);
        rcv = view.findViewById(R.id.rcv_admin);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), UpLoadPostActivity.class));
            }
        });

        mReference= FirebaseDatabase.getInstance().getReference(("Posts"));
        list=new ArrayList<>();
        adapter=new AdminAdapter(list,getContext());
        rcv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rcv.setAdapter(adapter);
        loaddata();
    }

    private void loaddata() {
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot!=null){
                    list.clear();
                    for (DataSnapshot data : dataSnapshot.getChildren()){

                        ItemBaiTap item = data.getValue(ItemBaiTap.class);
                        list.add(item);
                        Log.d("MMMMM",data.toString());
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
