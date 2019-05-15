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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.truongvu.gymer.R;
import com.truongvu.gymer.activity.EditPost;
import com.truongvu.gymer.activity.Itemchitietbaitap;
import com.truongvu.gymer.ultil.ItemBaiTap;

import java.sql.Array;
import java.util.ArrayList;


public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.MyViewHolder> {
    private ArrayList<ItemBaiTap> listbaitap;
    private Context context;
    private DatabaseReference mReference;

    public AdminAdapter(ArrayList<ItemBaiTap> listbaitap, Context context) {
        this.listbaitap = listbaitap;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view= LayoutInflater.from(context).inflate(R.layout.item_admin,viewGroup,false);
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
        myViewHolder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn xóa bài viết này không?");
                builder.setCancelable(false);
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        mReference.child(item.getId()).removeValue();
                        Toast.makeText(context,"Đã xóa!",Toast.LENGTH_SHORT).show();
                        listbaitap.remove(pos);
                        notifyItemRemoved(pos);
                        notifyItemRangeChanged(pos, getItemCount());
                    }
                });
                builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        myViewHolder.clickbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditPost.class);
                intent.putExtra("idpost", item.getId());
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
        private ImageView click;
        private TextView txttitle;
        private LinearLayout clickbt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_bt=itemView.findViewById(R.id.imgbaitap_admin);
            mcontent=itemView.findViewById(R.id.txtbaitap_admin);
            txttitle=itemView.findViewById(R.id.txttieude_admin);
            click=itemView.findViewById(R.id.delete_admin);
            clickbt= itemView.findViewById(R.id.clickbt);
        }
    }
}
