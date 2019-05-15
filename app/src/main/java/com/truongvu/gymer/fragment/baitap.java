package com.truongvu.gymer.fragment;

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
import android.widget.Adapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.truongvu.gymer.R;
import com.truongvu.gymer.adapter.adapterbaitap;
import com.truongvu.gymer.ultil.ItemBaiTap;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public class baitap extends Fragment {

    View view;
    private DatabaseReference mReference;
    private RecyclerView rcv;
    private List<ItemBaiTap> list;
    private adapterbaitap adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.baitap, container,false);
       return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity(). setTitle("Bài tập ở nhà cho bạn");
        AnhXa();
        mReference= FirebaseDatabase.getInstance().getReference(("Posts"));
        list=new ArrayList<>();
        loaddata();
    }

    private void loaddata() {

        //Them du lieu
        /*String keyID = mReference.push().getKey();
        ItemBaiTap item = new ItemBaiTap(keyID,"https://tse3.mm.bing.net/th?id=OIP.QcagO139dgmkjwqRKZ52OQHaFj&pid=15.1&P=0&w=224&h=169","0hGUMRjGDfw", "asdasd...............");
        mReference.child(keyID).setValue(item);*/
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot!=null){
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
        adapter=new adapterbaitap(list,getContext());
        rcv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rcv.setAdapter(adapter);
    }

    private void AnhXa() {
        rcv= view.findViewById(R.id.rcvbaitap);
    }
}
