package com.truongvu.gymer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.truongvu.gymer.R;
import com.truongvu.gymer.activity.UpLoadPostActivity;
import com.truongvu.gymer.adapter.adaptertrangchu;
import com.truongvu.gymer.ultil.ItemBaiTap;

import java.util.ArrayList;
import java.util.List;


public class TrangChuFragment extends Fragment {

    View view;
    private DatabaseReference mReference;
    private Button btndangbai;
    private ViewFlipper mFlipper;
    private RecyclerView rcv;
    private List<ItemBaiTap> lData;
    private adaptertrangchu adapter;

    public TrangChuFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_trang_chu, container, false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AnhXa();
        mReference= FirebaseDatabase.getInstance().getReference(("Posts"));
        //setAdmin();
        ActionViewFlipper();
        lData = new ArrayList<>();
        loaddata();
        btndangbai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), UpLoadPostActivity.class));
            }
        });

    }

    private void setAdmin() {
        String mail= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String name= FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        if (mail.matches("quyeovut@gmail.com")){
            btndangbai.setVisibility(View.VISIBLE);
        }else{

            btndangbai.setVisibility(View.GONE);
        }
        Toast.makeText(getContext(),"Xin chào "+name+"!",Toast.LENGTH_LONG).show();
    }


    private void loaddata() {
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot!=null){
                    lData.clear();
                    for (DataSnapshot data : dataSnapshot.getChildren()){

                        ItemBaiTap item = data.getValue(ItemBaiTap.class);
                        lData.add(item);
                        Log.d("MMMMM",data.toString());
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        adapter=new adaptertrangchu(lData,getContext());
        //Danh sach
        //rcv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        //Cot
        rcv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rcv.setAdapter(adapter);
    }

    private void AnhXa() {
        btndangbai=view.findViewById(R.id.btndangbai);
        rcv= view.findViewById(R.id.recycleviewmanhinhchinh);
    }

    private void ActionViewFlipper() {
        //ImageView imageView = new ImageView (MainActivity.this);
        /*ArrayList<String>mangquangcao=new ArrayList<> ();
        mangquangcao.add("https://dwbxi9io9o7ce.cloudfront.net/images/tap-the-duc-the-thao-va-yoga-tai-He-Thong-Ca.max-800x800.jpg");
        mangquangcao.add("http://lykos.vn/web/wp-content/uploads/2017/03/bai-tap-gym-tai-nha-cho-nam.jpg");
        mangquangcao.add("https://mbhfit.vn/wp-content/uploads/2018/03/nanmamna.png");*/

        mFlipper = view.findViewById(R.id.viewflippertrangchu);
        Animation animation_slide_in= AnimationUtils.loadAnimation (getActivity(),R.anim.slide_in_right);
        Animation animation_slide_out= AnimationUtils.loadAnimation (getActivity(),R.anim.slide_out_right);

        int[] mangquangcao = {R.drawable.mot,R.drawable.hai,R.drawable.ba};

        mFlipper.setInAnimation (animation_slide_in);
        mFlipper.setOutAnimation (animation_slide_out);
        for (int i=0;i<mangquangcao.length;i++){
            ImageView imageView=new ImageView (getActivity());
            //Picasso.get().load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType (ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(mangquangcao[i]);
            mFlipper.addView (imageView);
        }
        mFlipper.setFlipInterval (3000);
        mFlipper.setAutoStart(true);
    }
}
