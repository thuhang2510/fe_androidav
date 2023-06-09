package com.example.btlandroidav.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btlandroidav.R;
import com.example.btlandroidav.networks.RetrofitClient;
import com.example.btlandroidav.response.tuple.Triplet;
import com.example.btlandroidav.response.User;
import com.example.btlandroidav.utils.Helper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrangChu extends AppCompatActivity {

    private TextView name_user;
    private TextView coin;
    private TextView coinHide;
    private Integer user_id = 0;
    private ImageView eyeOff;
    private ImageView eye;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);

        init();
        getInfoFromIntent();
        get_by_id(user_id);
    }

    private void init(){
        name_user = findViewById(R.id.trang_chu_tv_name);
        coin = findViewById(R.id.trang_chu_tv_money);
        eye = findViewById(R.id.eye);
        eyeOff = findViewById(R.id.eye_off);
        coinHide = findViewById(R.id.trang_chu_tv_money_hide);
    }

    private void getInfoFromIntent(){
        Intent intent = getIntent();
        user_id = intent.getIntExtra("id_user", 0);
    }

    public void on_click_trang_chu_eye_off(View view){
        eye.setVisibility(View.VISIBLE);
        eyeOff.setVisibility(View.INVISIBLE);
        coin.setVisibility(View.VISIBLE);
        coinHide.setVisibility(View.INVISIBLE);
    }

    public void on_click_trang_chu_eye(View view){
        eye.setVisibility(View.INVISIBLE);
        eyeOff.setVisibility(View.VISIBLE);
        coin.setVisibility(View.INVISIBLE);
        coinHide.setVisibility(View.VISIBLE);
    }

    public void on_click_trang_chu_history_cars(View view){
        Intent intent = new Intent();
        intent.putExtra("id_user", user_id);
        intent.setClass(view.getContext(), LichSuGuiXe.class);
        startActivity(intent);
    }

    public void on_click_trang_chu_exit(View view){
        Intent intent = new Intent();
        intent.setClass(view.getContext(), MainActivity.class);
        startActivity(intent);
    }

    public void on_click_trang_chu_setting(View view){
        Intent intent = new Intent();
        intent.putExtra("id_user", user_id);
        intent.setClass(view.getContext(), CaiDat.class);
        startActivity(intent);
    }

    public void on_click_trang_chu_list_car(View view){
        Intent intent = new Intent();
        intent.putExtra("id_user", user_id);
        intent.setClass(view.getContext(), DanhSachXe.class);
        startActivity(intent);
    }

    public void on_click_trang_chu_tai_khoan(View view){
        Intent intent = new Intent();
        intent.putExtra("id_user", user_id);
        intent.setClass(view.getContext(), TaiKhoan.class);
        startActivity(intent);
    }

    public void get_by_id(Integer user_id){
        Call<Triplet<User, Integer, String>> call = RetrofitClient.getInstance().getMyApi().get_by_id(user_id);
        call.enqueue(new Callback<Triplet<User, Integer, String>>() {
            @Override
            public void onResponse(Call<Triplet<User, Integer, String>> call, Response<Triplet<User, Integer, String>> response) {
                Triplet<User, Integer, String> results = response.body();
                Toast.makeText(getApplicationContext(), results.getThird(), Toast.LENGTH_LONG).show();
                if(results.getSecond() == 0)
                {
                    User user = results.getFirst();
                    Toast.makeText(getApplicationContext(), "Get users success", Toast.LENGTH_LONG).show();
                    name_user.setText(user.getFullName());
                    coin.setText(Helper.formatMoney(user.getCoin()));
                }
            }

            @Override
            public void onFailure(Call<Triplet<User, Integer, String>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}