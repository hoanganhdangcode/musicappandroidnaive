package com.example.musicapp.views.Admin.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.musicapp.DTO.UserDTO;
import com.example.musicapp.R;
import com.example.musicapp.commons.ApiService;
import com.example.musicapp.commons.LoggedUser;
import com.example.musicapp.commons.RetrofitClient;
import com.example.musicapp.views.Admin.Adapter.SongAdapter;
import com.example.musicapp.views.Admin.Adapter.UserAdapter;
import com.example.musicapp.views.Admin.Adapter.UserAdapter;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ManagerUserFragment extends Fragment {
    RecyclerView recyclerView;
    EditText searchView;
    UserAdapter adapter;


    public ManagerUserFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manager_user, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_users);
        searchView = view.findViewById(R.id.edt_search_user);



         adapter = new UserAdapter(getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        loadUsers(adapter,searchView.getText().toString());
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                loadUsers(adapter, s.toString());

            }
        });
    }
    public void loadUsers(UserAdapter adapter, String keyword) {
        ApiService apiService = RetrofitClient
                .getRetrofit( LoggedUser.loggedUser.getAccessToken())
                .create(ApiService.class);

        Call<List<UserDTO>> call = apiService.adminGetUsers(keyword);

        call.enqueue(new Callback<List<UserDTO>>() {
            @Override
            public void onResponse(Call<List<UserDTO>> call,
                                   Response<List<UserDTO>> response) {
                if (response.isSuccessful()) {
                    adapter.setData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<UserDTO>> call, Throwable t) {
                if(getContext() != null) {
                    Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            }

        });


    }

}