package com.example.musicapp.views.Admin.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.DTO.CreateSingerDto; // Nhớ import DTO Singer
import com.example.musicapp.DTO.SingerDTO;
import com.example.musicapp.R;
import com.example.musicapp.commons.ApiService;
import com.example.musicapp.commons.LoggedUser;
import com.example.musicapp.commons.RetrofitClient;
import com.example.musicapp.views.Admin.Adapter.SingerAdapter;
import com.example.musicapp.views.Admin.Adapter.SingerAdapter;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerSingerFragment extends Fragment {

    ExtendedFloatingActionButton btnAdd;
    RecyclerView recyclerView;
    EditText searchView;
    SingerAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manager_singer, container, false); // Nhớ tạo file xml cho fragment này
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Nhớ đổi ID cho đúng với file layout fragment_manager_singer.xml
        btnAdd = view.findViewById(R.id.fab_add_artist);

        btnAdd.setOnClickListener(v -> showAddDialog());
        recyclerView = view.findViewById(R.id.rv_artists);
        searchView = view.findViewById(R.id.edt_search_artist);



         adapter = new SingerAdapter(getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        loadSingers(adapter,searchView.getText().toString());
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                loadSingers(adapter, s.toString());


            }
        });


    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_singer, null);
        builder.setView(dialogView);

        EditText etName = dialogView.findViewById(R.id.et_singer_name);
        EditText etDesc = dialogView.findViewById(R.id.et_singer_desc);
        EditText etImage = dialogView.findViewById(R.id.et_singer_image);

        builder.setPositiveButton("Thêm", (dialog, which) -> {});
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String desc = etDesc.getText().toString().trim();
            String image = etImage.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(getContext(), "Tên không được để trống!", Toast.LENGTH_SHORT).show();
                return;
            }

            callApiAddSinger(name, desc, image, dialog);
        });
    }

    private void callApiAddSinger(String name, String desc, String image, AlertDialog dialog) {
        CreateSingerDto dto = new CreateSingerDto(name, desc, image);
        ApiService apiService = RetrofitClient
                .getRetrofit( LoggedUser.loggedUser.getAccessToken()) // Kiểm tra lại port cho đúng
                .create(ApiService.class);

        apiService.addSinger(dto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Thêm ca sĩ thành công!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    loadSingers(adapter,searchView.getText().toString());
                    // loadData();
                } else {
                    Toast.makeText(getContext(), "Lỗi: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void loadSingers(SingerAdapter adapter, String keyword) {
        ApiService apiService = RetrofitClient
                .getRetrofit( LoggedUser.loggedUser.getAccessToken())
                .create(ApiService.class);

        Call<List<SingerDTO>> call = apiService.adminGetSingers(keyword);

        call.enqueue(new Callback<List<SingerDTO>>() {
            @Override
            public void onResponse(Call<List<SingerDTO>> call,
                                   Response<List<SingerDTO>> response) {
                if (response.isSuccessful()) {
                    adapter.setData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<SingerDTO>> call, Throwable t) {
                if(getContext() != null) {
                    Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            }

        });


    }
}