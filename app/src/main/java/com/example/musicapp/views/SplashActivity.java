package com.example.musicapp.views;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.musicapp.DTO.UserInfoResponseDTO;
import com.example.musicapp.R;
import com.example.musicapp.commons.ApiService;
import com.example.musicapp.commons.LoggedUser;
import com.example.musicapp.commons.RetrofitClient;
import com.example.musicapp.commons.SecureStorage;
import com.example.musicapp.models.User;
import com.example.musicapp.views.Admin.AdminMainActivity;
import com.example.musicapp.views.User.UserMainActivity;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import org.jspecify.annotations.NonNull;

import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    SecureStorage secureStorage;
    int trycount=3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageView logo = findViewById(R.id.imgLogo);
        TextView appName = findViewById(R.id.tvAppName);
        LinearProgressIndicator indicator = findViewById(R.id.indicator);


        logo.setAlpha(0f);
        appName.setAlpha(0f);
        indicator.setMax(100);
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(indicator, "progress", 0, 100);
        progressAnimator.setDuration(3000);
        progressAnimator.setInterpolator(new DecelerateInterpolator());
        progressAnimator.start();
        logo.animate()
                .alpha(1f)
                .setDuration(1500)
                .start();
        appName.animate()
                .alpha(1f)
                .setDuration(1500)
                .setStartDelay(0)
                .start();

        secureStorage = new SecureStorage(SplashActivity.this);
        int uid = secureStorage.getInt("uid",-1);
        String token = secureStorage.getString("token","");

        BiometricManager biometricManager = BiometricManager.from(this);
        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(
                this,
                executor,
                new BiometricPrompt.AuthenticationCallback() {

                    @Override
                    public void onAuthenticationSucceeded(
                            BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        Toast.makeText(SplashActivity.this, "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SplashActivity.this,LoggedUser.loggedUser.role== User.RoleEnumType.User? UserMainActivity.class: AdminMainActivity.class));
                        finish();


                    }

                    @Override
                    public void onAuthenticationError(
                            int errorCode,
                            @NonNull CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                    }
                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                        trycount--;
                        if(trycount<=0){
                            Toast.makeText(SplashActivity.this, "Vượt quá số lần thử, vui lòng đăng nhập để tiếp tục", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(SplashActivity.this, "Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        BiometricPrompt.PromptInfo promptInfo =
                new BiometricPrompt.PromptInfo.Builder()
                        .setTitle("Xác thực vân tay")
                        .setSubtitle("Dùng vân tay để đăng nhập")
                        .setNegativeButtonText("Huỷ")
                        .build();



        int canAuth = biometricManager.canAuthenticate(
                BiometricManager.Authenticators.BIOMETRIC_STRONG
        );

        if(token.isEmpty()||token.isBlank()){
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }
        else if (canAuth != BiometricManager.BIOMETRIC_SUCCESS) {
            Toast.makeText(this, "Bật chức năng vân tay để kích hoạt đăng nhập tự động", Toast.LENGTH_LONG).show();
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }
        else{
            ApiService apiService = RetrofitClient
                    .getRetrofit( token)
                    .create(ApiService.class);
            Call<UserInfoResponseDTO> call = apiService.getInfo();
            call.enqueue(new Callback<UserInfoResponseDTO>() {
                @Override
                public void onResponse(Call<UserInfoResponseDTO> call, Response<UserInfoResponseDTO> response) {
                    if(response.isSuccessful() && response.body() != null){
                        UserInfoResponseDTO dto = response.body();
                        // Map sang model User phẳng
                        User user = new User();
                        user.setUserId(dto.userId);
                        user.setName(dto.name);
                        user.setEmail(dto.email);
                        user.setAvatarUrl(dto.avatarurl);
                        user.setRole(dto.role);
                        user.setAccessToken(token);
                        LoggedUser.loggedUser=user;
                        biometricPrompt.authenticate(promptInfo);






                    } else {
                        Toast.makeText(SplashActivity.this, "Phiên đăng nhập đã hết hạn, vui lòng đăng nhập lại", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<UserInfoResponseDTO> call, Throwable t) {
                    Toast.makeText(SplashActivity.this, t.getMessage(),Toast.LENGTH_LONG);
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();

                }
            });

        }








    }
}