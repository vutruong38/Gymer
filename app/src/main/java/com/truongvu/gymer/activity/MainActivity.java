package com.truongvu.gymer.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.truongvu.gymer.R;
import com.truongvu.gymer.fragment.AdminFragment;
import com.truongvu.gymer.fragment.TrangChuFragment;
import com.truongvu.gymer.fragment.baitap;
import com.truongvu.gymer.fragment.taikhoan;
import com.truongvu.gymer.ultil.Users;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private TextView name, mail;
    private CircleImageView imgHinh;

    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid());

        AnhXa();
        ActionBar();
        signOutGG();
        Picasso.get().load(mAuth.getCurrentUser().getPhotoUrl()).into(imgHinh);
        name.setText(mAuth.getCurrentUser().getDisplayName());
        mail.setText(mAuth.getCurrentUser().getEmail());
        Fragment fragmentfirst = new TrangChuFragment();
        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.layout_content, fragmentfirst);
        ft.commit();
        setDrawer();
    }

    private void setDrawer() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment=null;
                switch (menuItem.getItemId()){
                    case R.id.item_tc:
                        fragment= new TrangChuFragment();
                        mToolbar.setTitle("Trang chủ");
                        break;
                    case R.id.item_tk:
                        fragment= new taikhoan();
                        mToolbar.setTitle("Tài khoản");
                        break;
                    case  R.id.item_bt:
                        fragment=new baitap();
                        mToolbar.setTitle("Bài tập");
                        break;
                    case  R.id.item_ad:
                        fragment=new AdminFragment();
                        mToolbar.setTitle("Quản lý bài tập");
                        break;
                    case  R.id.item_dx:
                        mAuth.signOut();
                        signOut();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                        break;
                }
                if (fragment!=null){
                    FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.layout_content, fragment);
                    ft.commit();
                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void ActionBar() {
        setSupportActionBar (mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled (true);
        mToolbar.setNavigationIcon (R.drawable.ic_menu_white_24dp);
        mToolbar.setNavigationOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer (GravityCompat.START);
            }
        });

    }
    private void AnhXa() {
        mToolbar = findViewById(R.id.toolbar_main);
        mNavigationView=findViewById(R.id.navigationviewmanhinhchinh);
       // navigationView.setNavigationItemSelectedListener(this);
        View header=mNavigationView.getHeaderView(0);
        mDrawerLayout=findViewById(R.id.drawelayoutmanhinhchinh);
        name= header.findViewById(R.id.name_nv);
        mail = header.findViewById(R.id.mail_nv);
        imgHinh = header.findViewById(R.id.img_nv);

        final Menu admenu = mNavigationView.getMenu();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Users load = dataSnapshot.getValue(Users.class);
                    if(load.getLoaitk() == 0){
                        admenu.findItem(R.id.item_ad).setVisible(false);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.layout_content, fragment);
        fragmentTransaction.commit();
    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //disconnectFromFacebook();
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(MainActivity.this,"Đã đăng xuất thành công!",Toast.LENGTH_LONG).show();
                    }
                });
    }
    private void signOutGG() {
        //  [START config_signin]getString(R.string.default_web_client_id)
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("966454279606-atgr8k8u72798fp0qm9iff0hib543ec2.apps.googleusercontent.com")
                .requestEmail()
                .build();

        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(MainActivity.this, gso);
    }

}
