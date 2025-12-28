package com.example.musicapp.views.Admin.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.DTO.CloudianySignatureDTO;
import com.example.musicapp.DTO.CreateSongDto; // Nhớ import DTO Song
import com.example.musicapp.DTO.GenreDTO;
import com.example.musicapp.DTO.SingerDTO;
import com.example.musicapp.DTO.SongDTO;
import com.example.musicapp.DTO.SongTrendingDTO;
import com.example.musicapp.R;
import com.example.musicapp.commons.ApiService;
import com.example.musicapp.commons.LoggedUser;
import com.example.musicapp.commons.RetrofitClient;
import com.example.musicapp.views.Admin.Adapter.GenreAdapter;
import com.example.musicapp.views.Admin.Adapter.SingerAdapter;
import com.example.musicapp.views.Admin.Adapter.SongAdapter;
import com.example.musicapp.views.User.Adapter.SongTrendingAdapter;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerSongFragment extends Fragment {

    ExtendedFloatingActionButton btnAdd;
    RecyclerView recyclerView;
    EditText searchView;
    SongAdapter adapter;

    SingerDTO choosedsinger;
    GenreDTO choosedgenre;
    public interface UploadCallback {
        void onSuccess(String secureUrl, String publicId);
        void onError(String error);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manager_song, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnAdd = view.findViewById(R.id.fab_add_song); // Check đúng ID xml
        recyclerView = view.findViewById(R.id.rv_songs);

        btnAdd.setOnClickListener(v -> showAddDialog());
        searchView = view.findViewById(R.id.edt_search_song);


        adapter = new SongAdapter(getContext());
        adapter.SetItemListener(song -> {
//            Toast.makeText(getContext(), "Click"+song.name, Toast.LENGTH_SHORT).show();
            showModifyDialog(song);

        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
            loadSongs(adapter,searchView.getText().toString());
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                loadSongs(adapter, s.toString());
            }
        });

    }

    private void showAddDialog() {
        choosedsinger=null;
        choosedgenre=null;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_song, null);
        builder.setView(view);

        EditText etName = view.findViewById(R.id.et_song_name);
        EditText etDesc = view.findViewById(R.id.et_song_desc);
        EditText etAudio = view.findViewById(R.id.et_song_audio);
        EditText etImage = view.findViewById(R.id.et_song_image);
        EditText etSingerSearch = view.findViewById(R.id.et_song_singer_search);
        EditText etGenreSearch = view.findViewById(R.id.et_song_genre_search);
        TextView tvChoosesinger = view.findViewById(R.id.tv_choosesinger);
        TextView tvChoosegenre = view.findViewById(R.id.tv_choosegenre);


        builder.setPositiveButton("Thêm", (dialog, which) -> {});
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        Button btnselectimg = view.findViewById(R.id.btn_select_image);
        btnselectimg.setVisibility(View.GONE);
        Button btndeleteimg = view.findViewById(R.id.btn_delete_image);
        ImageView selectedImage = view.findViewById(R.id.selected_image);

        RecyclerView rv_choosesinger = view.findViewById(R.id.rv_choosesinger);
        RecyclerView rv_choosegenre = view.findViewById(R.id.rv_choosegenre);
        SingerAdapter singerAdapter = new SingerAdapter(getContext());
        GenreAdapter genreAdapter = new GenreAdapter(getContext());
        rv_choosesinger.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_choosesinger.setAdapter(singerAdapter);
        rv_choosegenre.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_choosegenre.setAdapter(genreAdapter);
        loadSingers(singerAdapter,etSingerSearch.getText().toString());
        loadGenres(genreAdapter,etGenreSearch.getText().toString());


        singerAdapter.SetItemListener(singer -> {
            choosedsinger=singer;
            tvChoosesinger.setText("Đang chọn: "+singer.getName());
                });
        genreAdapter.SetItemListener(genre -> {
            choosedgenre=genre;
            tvChoosegenre.setText("Đang chọn: "+genre.getName());
        });
        etSingerSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadSingers(singerAdapter,s.toString());
            }
        });
        etGenreSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadGenres(genreAdapter,s.toString());
            }
        });



        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            try {
                String name = etName.getText().toString().trim();
                String desc = etDesc.getText().toString().trim();
                String audio = etAudio.getText().toString().trim();
                String image = etImage.getText().toString().trim();




                if (name.isEmpty() || choosedsinger==null || choosedgenre==null) {
                    Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                int singerId = choosedsinger.singerId;
                int genreId = choosedgenre.genreId;

                callApiAddSong(name, desc, audio, image, singerId, genreId, dialog);

            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "ID Ca sĩ và Thể loại phải là số!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showModifyDialog(SongDTO songedit) {
        choosedsinger=null;
        choosedgenre=null;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_song, null);
        builder.setView(view);
        TextView title = view.findViewById(R.id.tv_title);
        title.setText("Sửa bài hát");

        EditText etName = view.findViewById(R.id.et_song_name);
        EditText etDesc = view.findViewById(R.id.et_song_desc);
        EditText etAudio = view.findViewById(R.id.et_song_audio);
        EditText etImage = view.findViewById(R.id.et_song_image);
        EditText etSingerSearch = view.findViewById(R.id.et_song_singer_search);
        EditText etGenreSearch = view.findViewById(R.id.et_song_genre_search);
        TextView tvChoosesinger = view.findViewById(R.id.tv_choosesinger);
        TextView tvChoosegenre = view.findViewById(R.id.tv_choosegenre);
        etName.setText(songedit.name!=null?songedit.name:"");
        etDesc.setText(songedit.description!=null?songedit.description:"");
        etAudio.setText(songedit.audioUrl!=null?songedit.audioUrl:"");
        etImage.setText(songedit.imageUrl!=null?songedit.imageUrl:"");


        tvChoosesinger.setText("Đang chọn: "+songedit.singerName);
        tvChoosegenre.setText("Đang chọn: "+songedit.genreName);

        builder.setPositiveButton("Sửa", (dialog, which) -> {});
        builder.setNeutralButton("Xóa", (dialog, which) -> {
                deleteSong(songedit);
                dialog.dismiss();
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        Button btnselectimg = view.findViewById(R.id.btn_select_image);
        btnselectimg.setVisibility(View.GONE);
        Button btndeleteimg = view.findViewById(R.id.btn_delete_image);
        ImageView selectedImage = view.findViewById(R.id.selected_image);

        RecyclerView rv_choosesinger = view.findViewById(R.id.rv_choosesinger);
        RecyclerView rv_choosegenre = view.findViewById(R.id.rv_choosegenre);
        SingerAdapter singerAdapter = new SingerAdapter(getContext());
        GenreAdapter genreAdapter = new GenreAdapter(getContext());
        rv_choosesinger.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_choosesinger.setAdapter(singerAdapter);
        rv_choosegenre.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_choosegenre.setAdapter(genreAdapter);
        loadSingers(singerAdapter,etSingerSearch.getText().toString());
        loadGenres(genreAdapter,etGenreSearch.getText().toString());


        singerAdapter.SetItemListener(singer -> {
            choosedsinger=singer;
            tvChoosesinger.setText("Đang chọn: "+singer.getName());
        });
        genreAdapter.SetItemListener(genre -> {
            choosedgenre=genre;
            tvChoosegenre.setText("Đang chọn: "+genre.getName());
        });
        etSingerSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadSingers(singerAdapter,s.toString());
            }
        });
        etGenreSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadGenres(genreAdapter,s.toString());
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            try {
                String name = etName.getText().toString().trim();
                String desc = etDesc.getText().toString().trim();
                String audio = etAudio.getText().toString().trim();
                String image = etImage.getText().toString().trim();


                songedit.name=name;
                songedit.description=desc;
                songedit.audioUrl=audio;
                songedit.imageUrl=image;
                if(choosedsinger!=null){
                songedit.singerId = choosedsinger.singerId;}
                if(choosedgenre!=null){
                    songedit.genreId = choosedgenre.genreId;
                }
                if (name.isEmpty() ||( choosedsinger==null && songedit.singerId==0) || (choosedgenre==null && songedit.genreId==0)) {
                    Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                updateSong(songedit);
                dialog.dismiss();


            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "ID Ca sĩ và Thể loại phải là số!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteSong(SongDTO songedit) {
        ApiService apiService = RetrofitClient
                .getRetrofit( LoggedUser.loggedUser.getAccessToken()) // Kiểm tra lại port cho đúng
                .create(ApiService.class);
        apiService.deleteSong(songedit.songId).enqueue(
                new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Xóa bài hát thành công!", Toast.LENGTH_SHORT).show();
                            // loadData();
                            loadSongs(adapter,searchView.getText().toString());
                        } else {
                            // Code 400 nếu ID Ca sĩ/Thể loại không tồn tại
                            Toast.makeText(getContext(), "Lỗi: " + response.code() + " (Kiểm tra lại ID)", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getContext(), "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }

        );
    }
    private void updateSong(SongDTO songedit) {
        ApiService apiService = RetrofitClient
                .getRetrofit( LoggedUser.loggedUser.getAccessToken()) // Kiểm tra lại port cho đúng
                .create(ApiService.class);
        apiService.updateSong(songedit.songId,songedit).enqueue(
                new Callback<Void>() {


                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Cập nhật bài hát thành công!", Toast.LENGTH_SHORT).show();
                            // loadData();
                            loadSongs(adapter,searchView.getText().toString());
                        } else {
                            // Code 400 nếu ID Ca sĩ/Thể loại không tồn tại
                            Toast.makeText(getContext(), "Lỗi: " + response.code() + " (Kiểm tra lại ID)", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getContext(), "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }

        );


    }

    private void callApiAddSong(String name, String desc, String audio, String image, int sId, int gId, AlertDialog dialog) {
        CreateSongDto dto = new CreateSongDto(name, desc, audio, image, sId, gId);
        ApiService apiService = RetrofitClient
                .getRetrofit( LoggedUser.loggedUser.getAccessToken()) // Kiểm tra lại port cho đúng
                .create(ApiService.class);

        apiService.addSong(dto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Thêm bài hát thành công!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    // loadData();
                    loadSongs(adapter,searchView.getText().toString());
                } else {
                    // Code 400 nếu ID Ca sĩ/Thể loại không tồn tại
                    Toast.makeText(getContext(), "Lỗi: " + response.code() + " (Kiểm tra lại ID)", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();


            }
        });
    }

    public void loadSongs(SongAdapter adapter, String keyword) {
        ApiService apiService = RetrofitClient
                .getRetrofit( LoggedUser.loggedUser.getAccessToken())
                .create(ApiService.class);

        Call<List<SongDTO>> call = apiService.adminGetSongs(keyword);

        call.enqueue(new Callback<List<SongDTO>>() {
            @Override
            public void onResponse(Call<List<SongDTO>> call,
                                   Response<List<SongDTO>> response) {
                if (response.isSuccessful()) {
                    adapter.setData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<SongDTO>> call, Throwable t) {
                if(getContext() != null) {
                    Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
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
    public void loadGenres(GenreAdapter adapter, String keyword) {
        ApiService apiService = RetrofitClient
                .getRetrofit( LoggedUser.loggedUser.getAccessToken())
                .create(ApiService.class);

        Call<List<GenreDTO>> call = apiService.adminGetGenres(keyword);

        call.enqueue(new Callback<List<GenreDTO>>() {
            @Override
            public void onResponse(Call<List<GenreDTO>> call,
                                   Response<List<GenreDTO>> response) {
                if (response.isSuccessful()) {
                    adapter.setData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<GenreDTO>> call, Throwable t) {
                if(getContext() != null) {
                    Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            }

        });





}
}