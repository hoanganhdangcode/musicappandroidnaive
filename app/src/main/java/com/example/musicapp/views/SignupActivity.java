package com.example.musicapp.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.musicapp.DTO.LoginDTO;
import com.example.musicapp.DTO.LoginResponseDTO;
import com.example.musicapp.DTO.RegisterDTO;
import com.example.musicapp.R;
import com.example.musicapp.commons.ApiService;
import com.example.musicapp.commons.LoggedUser;
import com.example.musicapp.commons.RetrofitClient;
import com.example.musicapp.models.User;
import com.example.musicapp.views.Admin.AdminMainActivity;
import com.example.musicapp.views.User.UserMainActivity;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextInputEditText etname = findViewById(R.id.etFullName);
        TextInputEditText etemail = findViewById(R.id.etEmail);
        TextInputEditText etpassword = findViewById(R.id.etPassword);
        TextInputEditText etconfirmpassword = findViewById(R.id.etConfirmPassword);
        Button btndangki = findViewById(R.id.btnRegister);


        btndangki.setOnClickListener(v->{
            if(checkdata(etname,etemail,etpassword,etconfirmpassword)){
                dangki(btndangki,etname,etemail,etpassword);

            }

        });

    }
    public boolean checkdata( TextInputEditText etname,TextInputEditText etemail,TextInputEditText etpassword, TextInputEditText etconfirmpassword){
        boolean kt= true;
        if(etname.getText().toString().isBlank()){kt=false;
        etname.setError("Vui lòng nhập thông tin");
        }

        if(etemail.getText().toString().isBlank()){kt=false;
            etemail.setError("Vui lòng nhập thông tin");}

        if(etpassword.getText().toString().isBlank()){kt=false;
            etpassword.setError("Vui lòng nhập thông tin");}

        if(etconfirmpassword.getText().toString().isBlank()){kt=false;
            etconfirmpassword.setError("Vui lòng nhập thông tin");
        }

        if(!(etconfirmpassword.getText().toString()).equals(etpassword.getText().toString())){kt=false;
            etconfirmpassword.setError("Xác nhận mật khẩu không trùng khớp");
        }

        return kt;
    }
    public void dangki(Button btndangki, TextInputEditText etname,TextInputEditText etemail,TextInputEditText etpassword){
        String name = etname.getText().toString();
        String email = etemail.getText().toString();
        String password = etpassword.getText().toString();
        ApiService apiService = RetrofitClient
                .getRetrofit("https://10.0.2.2:7007/", null) // Kiểm tra lại port cho đúng
                .create(ApiService.class);

        btndangki.setEnabled(false);

        RegisterDTO request = new RegisterDTO(name, email, password);
        Call<Void> call = apiService.register(request);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // Trường hợp thành công (Code 201 Created)
                if (response.isSuccessful()) {
                    Toast.makeText(SignupActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

                    // Chuyển sang màn hình Login
                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                    finish();

                } else {
                    // Trường hợp thất bại (Ví dụ: Code 409 Conflict - Email đã tồn tại)
                    String message = "Đăng ký thất bại";
                    try {
                        if (response.errorBody() != null) {
                            // Backend trả về: Results.Conflict("Email đã tồn tại")
                            // Dòng này sẽ lấy được chuỗi "Email đã tồn tại"
                            message = response.errorBody().string();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(SignupActivity.this, message, Toast.LENGTH_LONG).show();
                }
                btndangki.setEnabled(true);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                btndangki.setEnabled(true);
            }
        });

    }
}