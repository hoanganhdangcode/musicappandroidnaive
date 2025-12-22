package com.example.musicapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.musicapp.DTO.LoginDTO;
import com.example.musicapp.DTO.LoginResponseDTO;
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

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView tvsignup = findViewById(R.id.tvsignup);
        tvsignup.setOnClickListener(
                v->{
                    startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                }
        );
        TextInputEditText emailview = findViewById(R.id.etEmail);
        TextInputEditText passwordview = findViewById(R.id.etPassword);

        Button loginbuttonview = findViewById(R.id.btnLogin);
        loginbuttonview.setOnClickListener(v -> {
            login(emailview.getText().toString(), passwordview.getText().toString(),loginbuttonview);
        });
    
        
    }
    public void login(String email, String password,Button btnlogin){
        ApiService apiService = RetrofitClient
                .getRetrofit("https://10.0.2.2:7007/", null)
                .create(ApiService.class);
        btnlogin.setEnabled(false);
        LoginDTO request = new LoginDTO(email, password);
        Call<LoginResponseDTO> call = apiService.login(request);

        call.enqueue(new Callback<LoginResponseDTO>() {
            @Override
            public void onResponse(Call<LoginResponseDTO> call, Response<LoginResponseDTO> response) {
                if(response.isSuccessful() && response.body() != null){
                    LoginResponseDTO dto = response.body();
                    // Map sang model User phẳng
                    User user = new User();
                    user.setUserId(dto.user.userId);
                    user.setName(dto.user.name);
                    user.setEmail(dto.user.email);
                    user.setAvatarUrl(dto.user.avatarurl);
                    user.setRole(dto.user.role);
                    user.setAccessToken(dto.accessToken);
                    LoggedUser.loggedUser=user;

                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", 1).show();
                    startActivity(new Intent(LoginActivity.this,LoggedUser.loggedUser.role== User.RoleEnumType.User? UserMainActivity.class: AdminMainActivity.class));
                    finish();

                } else {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại. Kiểm tra lại thông tin đăng nhập", 0).show();
                }
                btnlogin.setEnabled(true);
            }

            @Override
            public void onFailure(Call<LoginResponseDTO> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(),1);
                btnlogin.setEnabled(true);
            }
        });
        
    }
}