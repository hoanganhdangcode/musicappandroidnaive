package com.example.musicapp.views.Admin.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.musicapp.DTO.CreateGenreDto;
import com.example.musicapp.R;
import com.example.musicapp.commons.ApiService;
import com.example.musicapp.commons.RetrofitClient;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerGenreFragment extends Fragment {

    ExtendedFloatingActionButton btnadd;

    public ManagerGenreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manager_genre, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnadd = view.findViewById(R.id.fab_add_genre);

        // Bắt sự kiện click
        btnadd.setOnClickListener(v -> {
            showAddGenreDialog();
        });
    }

    // Hàm hiển thị Dialog và xử lý thêm mới
    private void showAddGenreDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // 1. Gán layout mình vừa tạo vào Dialog
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_genre, null);
        builder.setView(dialogView);

        // 2. Ánh xạ các View trong Dialog
        EditText etName = dialogView.findViewById(R.id.et_genre_name);
        EditText etImage = dialogView.findViewById(R.id.et_genre_image);

        // 3. Thiết lập nút bấm
        builder.setPositiveButton("Thêm", (dialog, which) -> {
            // Sự kiện này sẽ được override bên dưới để tránh dialog đóng khi lỗi
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        // 4. Tạo và hiển thị Dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        // 5. Xử lý sự kiện nút "Thêm" (Override để kiểm tra dữ liệu rỗng)
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String image = etImage.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(getContext(), "Tên thể loại không được để trống!", Toast.LENGTH_SHORT).show();
                return; // Không đóng dialog, bắt nhập lại
            }

            // Gọi hàm API
            callApiAddGenre(name, image, dialog);
        });
    }

    // Hàm gọi API riêng biệt cho gọn code
    private void callApiAddGenre(String name, String image, AlertDialog dialog) {
        // Tạo DTO
        CreateGenreDto dto = new CreateGenreDto(name, image);

        // Gọi Retrofit
        ApiService apiService = RetrofitClient.getRetrofit("https://10.0.2.2:7007/", null).create(ApiService.class);

        apiService.addGenre(dto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss(); // Đóng dialog

                    // loadData();
                } else {
                    Toast.makeText(getContext(), "Thất bại: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}