package com.truongvu.gymer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.truongvu.gymer.R;

public class ItemChiTietTrangChu extends AppCompatActivity {
    String link,id,content,title;
    private TextView mcontent;
       private TextView mtitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_trang_chu);
        anhxa();
        id = getIntent().getStringExtra("id");
        content = getIntent().getStringExtra("content");
        link = getIntent().getStringExtra("video");
        title= getIntent().getStringExtra("title");

        Toast.makeText(ItemChiTietTrangChu.this, "ID: " + id, Toast.LENGTH_LONG).show();
        mcontent.setText(content);
        mtitle.setText(title);
    }

    private void anhxa() {
        mtitle=findViewById(R.id.txt_tieude_trangchu);
        mcontent=findViewById(R.id.txt_content_tc);
    }
}
