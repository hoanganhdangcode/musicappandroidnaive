package com.example.musicapp.views;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.musicapp.R;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class SplashActivity extends AppCompatActivity {

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
        // 1. Ánh xạ View từ XML
        ImageView logo = findViewById(R.id.imgLogo);
        TextView appName = findViewById(R.id.tvAppName);
        LinearProgressIndicator indicator = findViewById(R.id.indicator);


        // 2. Thiết lập trạng thái ban đầu (ẩn đi bằng Alpha = 0)
        logo.setAlpha(0f);
        appName.setAlpha(0f);
        indicator.setMax(100);
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(indicator, "progress", 0, 100);
        progressAnimator.setDuration(3000);
        progressAnimator.setInterpolator(new DecelerateInterpolator());

// Bắt đầu chạy
        progressAnimator.start();


        // 3. Chạy hiệu ứng hiện dần (Animation)
        // Logo hiện lên trong 1.5 giây
        logo.animate()
                .alpha(1f)
                .setDuration(1500)
                .start();

        // Tên App hiện lên sau logo (delay 0.5s)
        appName.animate()
                .alpha(1f)
                .setDuration(1500)
                .setStartDelay(500)
                .start();


        // 4. Chuyển sang màn hình Login sau 3 giây
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Tạo Intent để chuyển sang LoginActivity
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);

                // Đóng SplashActivity lại để người dùng không back về được
                finish();
            }
        }, 30000); // 3000 mili giây = 3 giây

    }
}