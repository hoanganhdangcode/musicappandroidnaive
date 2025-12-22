package com.example.musicapp.views.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.musicapp.R;
import com.example.musicapp.commons.LoggedUser;
import com.example.musicapp.models.User;
import com.example.musicapp.views.LoginActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class AdminMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_main);

        // 1. Ánh xạ View
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        DrawerLayout drawerLayout = findViewById(R.id.main);
        NavigationView navigationView = findViewById(R.id.navigation_view);





            // 2. Lấy NavController (Bắt buộc phải có để chuyển trang)
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.admin_nav_host_fragment);

        // Kiểm tra null để tránh crash
        NavController navController = (navHostFragment != null) ? navHostFragment.getNavController() : null;

        ViewCompat.setOnApplyWindowInsetsListener(toolbar, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, 0);
            return insets;
        });

        View headerview = navigationView.getHeaderView(0);

        ImageView imgvavatar = headerview.findViewById(R.id.imgAvatar);
        TextView tvwellcome = headerview.findViewById(R.id.tvWelcomeadmin);
        TextView tvemail= headerview.findViewById(R.id.tvEmailAdmin);
        ImageButton btndangxuat = headerview.findViewById(R.id.btnLogout);

        tvwellcome.setText("Welcome "+ LoggedUser.loggedUser.name+"!");
        tvemail.setText(LoggedUser.loggedUser.email);

        Glide.with(AdminMainActivity.this)
                .load(LoggedUser.loggedUser.avatarUrl)
                .placeholder(R.drawable.ic_launcher_background) // Ảnh tạm
                .error(R.drawable.ic_launcher_background)       // Ảnh lỗi
                .into(imgvavatar);
        btndangxuat.setOnClickListener(v -> {
            LoggedUser.loggedUser = new User();
            startActivity(new Intent(AdminMainActivity.this, LoginActivity.class));
            finish();});


            // 3. Setup mặc định ban đầu
        if (navigationView != null) navigationView.setCheckedItem(R.id.nav_genres);
        toolbar.setTitle("Quản lý thể loại");

        // 4. Xử lý nút Menu (3 gạch) - Mở Drawer thủ công
        toolbar.setNavigationOnClickListener(v -> {
            drawerLayout.open();
        });

        // 5. Xử lý sự kiện chọn Menu - THÊM LOGIC CHUYỂN TRANG VÀO ĐÂY
        navigationView.setNavigationItemSelectedListener(item -> {
            // Đóng menu trước cho gọn
            drawerLayout.close();
            item.setChecked(true);

            int id = item.getItemId();

            if (navController != null) {
                if (id == R.id.nav_users){
                    toolbar.setTitle("Quản lý người dùng");
                    // Lệnh chuyển trang:
                    navController.navigate(R.id.nav_users);
                }
                else if (id == R.id.nav_songs){
                    toolbar.setTitle("Quản lý bài hát");
                    // Lệnh chuyển trang:
                    navController.navigate(R.id.nav_songs);
                }
                else if (id == R.id.nav_artists){
                    toolbar.setTitle("Quản lý ca sĩ");
                    // Lệnh chuyển trang:
                    navController.navigate(R.id.nav_artists);
                }
                else if (id == R.id.nav_genres){
                    toolbar.setTitle("Quản lý thể loại");
                    // Lệnh chuyển trang:
                    navController.navigate(R.id.nav_genres);
                }
            }
            return true;
        });
    }
}