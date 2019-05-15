package com.truongvu.gymer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.truongvu.gymer.R;
import com.truongvu.gymer.activity.Itemchitietbaitap;
import com.truongvu.gymer.ultil.ItemBaiTap;

import java.util.List;

public class adaptertrangchu extends RecyclerView.Adapter<adaptertrangchu.MyViewHolder> {
    private List<ItemBaiTap> listtrangchu;
    private Context context;

    public adaptertrangchu(List<ItemBaiTap> listtrangchu, Context context) {
        this.listtrangchu = listtrangchu;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view= LayoutInflater.from(context).inflate(R.layout.itemtrangchu,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final ItemBaiTap item=listtrangchu.get(i);
        Picasso.get().load(item.getImage()).into(myViewHolder.img_tc);
        String s="";
        if(item.getTitle().length()>18)
            s = item.getTitle().substring(0,18);
        else s = item.getTitle();
        myViewHolder.txttitle.setText(s+"..");
        String s1="";
        if(item.getContent().length()>50)
            s1 = item.getContent().substring(0,50);
        else s1 = item.getContent();
        myViewHolder.mcontent.setText(s1+"...");
        myViewHolder.ln_iteam_click.setOnClickListener(new View.OnClickListener() {
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
        return listtrangchu.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_tc;
        private TextView mcontent;
        private TextView text;
        private TextView txttitle;
        private LinearLayout ln_iteam_click;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_tc=itemView.findViewById(R.id.img_tc);
            txttitle=itemView.findViewById(R.id.txt_title_tc);
            ln_iteam_click=itemView.findViewById(R.id.ln_iteam_click);
            mcontent=itemView.findViewById(R.id.txt_content_tc);
        }
    }
}
