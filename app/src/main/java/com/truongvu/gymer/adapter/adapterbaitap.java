package com.truongvu.gymer.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.truongvu.gymer.R;
import com.truongvu.gymer.activity.Itemchitietbaitap;
import com.truongvu.gymer.ultil.ItemBaiTap;

import java.util.List;

public class adapterbaitap extends RecyclerView.Adapter<adapterbaitap.MyViewHolder> {
    private List<ItemBaiTap> listbaitap;
    private Context context;
    private DatabaseReference mReference;

    public adapterbaitap(List<ItemBaiTap> listbaitap, Context context) {
        this.listbaitap = listbaitap;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view= LayoutInflater.from(context).inflate(R.layout.itembaitap,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int pos) {
        final ItemBaiTap item=listbaitap.get(pos);

        mReference= FirebaseDatabase.getInstance().getReference(("Posts"));
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        //myViewHolder.img_bt.setImageResource(item.getImg_bt());
        Picasso.get().load(item.getImage()).into(myViewHolder.img_bt);
        String s="";
        if(item.getContent().length()>40)
            s = item.getContent().substring(0,40);
        else s = item.getContent();
        myViewHolder.mcontent.setText(s+"...");
        myViewHolder.txttitle.setText(item.getTitle());
        myViewHolder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Itemchitietbaitap.class);
                intent.putExtra("id", item.getId());
                intent.putExtra("img", item.getImage());
                intent.putExtra("video", item.getLink());
                intent.putExtra("content", item.getContent());
                intent.putExtra("title", item.getTitle());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listbaitap.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_bt;
        private TextView mcontent;
        private LinearLayout click;
        private TextView txttitle;
      public MyViewHolder(@NonNull View itemView) {
          super(itemView);
          img_bt=itemView.findViewById(R.id.imgbaitap);
          mcontent=itemView.findViewById(R.id.txtbaitap);
          click=itemView.findViewById(R.id.clickbt);
          txttitle=itemView.findViewById(R.id.txttieude);
      }
  }
}
