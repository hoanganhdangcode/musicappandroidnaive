package com.example.musicapp.views.Admin.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.musicapp.DTO.CreateSongDto; // Nhớ import DTO Song
import com.example.musicapp.R;
import com.example.musicapp.commons.ApiService;
import com.example.musicapp.commons.RetrofitClient;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerSongFragment extends Fragment {

    ExtendedFloatingActionButton btnAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manager_song, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnAdd = view.findViewById(R.id.fab_add_song); // Check đúng ID xml
        btnAdd.setOnClickListener(v -> showAddDialog());
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_song, null);
        builder.setView(view);

        EditText etName = view.findViewById(R.id.et_song_name);
        EditText etDesc = view.findViewById(R.id.et_song_desc);
        EditText etAudio = view.findViewById(R.id.et_song_audio);
        EditText etImage = view.findViewById(R.id.et_song_image);
        EditText etSingerId = view.findViewById(R.id.et_song_singer_id);
        EditText etGenreId = view.findViewById(R.id.et_song_genre_id);

        builder.setPositiveButton("Thêm", (dialog, which) -> {});
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            try {
                String name = etName.getText().toString().trim();
                String desc = etDesc.getText().toString().trim();
                String audio = etAudio.getText().toString().trim();
                String image = etImage.getText().toString().trim();

                // Parse ID từ chuỗi sang số
                String sIdStr = etSingerId.getText().toString().trim();
                String gIdStr = etGenreId.getText().toString().trim();

                if (name.isEmpty() || sIdStr.isEmpty() || gIdStr.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập tên và các ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                int singerId = Integer.parseInt(sIdStr);
                int genreId = Integer.parseInt(gIdStr);

                callApiAddSong(name, desc, audio, image, singerId, genreId, dialog);

            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "ID Ca sĩ và Thể loại phải là số!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callApiAddSong(String name, String desc, String audio, String image, int sId, int gId, AlertDialog dialog) {
        CreateSongDto dto = new CreateSongDto(name, desc, audio, image, sId, gId);
        ApiService apiService = RetrofitClient.getRetrofit("https://10.0.2.2:7007/", null).create(ApiService.class);

        apiService.addSong(dto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Thêm bài hát thành công!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    // loadData();
                } else {
                    // Code 400 nếu ID Ca sĩ/Thể loại không tồn tại
                    Toast.makeText(getContext(), "Lỗi: " + response.code() + " (Kiểm tra lại ID)", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
//                Toast.makeText(getContext(), "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();


            }
        });
    }
}