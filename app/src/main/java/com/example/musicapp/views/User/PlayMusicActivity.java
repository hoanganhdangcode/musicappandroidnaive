package com.example.musicapp.views.User;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.musicapp.DTO.RegisterDTO;
import com.example.musicapp.R;
import com.example.musicapp.commons.ApiService;
import com.example.musicapp.commons.LoggedUser;
import com.example.musicapp.commons.RetrofitClient;
import com.example.musicapp.commons.SecureStorage;
import com.example.musicapp.views.LoginActivity;
import com.example.musicapp.views.SignupActivity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayMusicActivity extends AppCompatActivity {
    // Khai báo biến
    MediaPlayer mediaPlayer;
    ImageButton btnBack, btnPlayPause;
    TextView tvSongName, tvSingerName;
    ImageView imageView;
    SeekBar seekBar;
    SecureStorage secureStorage;
    private Handler handler = new Handler();
    private Runnable runnable;
    Intent i;

    String audiourl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_play_music);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // 1. Ánh xạ View
        btnBack = findViewById(R.id.btnback);
        btnPlayPause = findViewById(R.id.btnPlayPause);
        tvSongName = findViewById(R.id.tvsongname);
        imageView = findViewById(R.id.imageurl); // Lưu ý: ID bên XML phải là @+id/imageurl
        tvSingerName = findViewById(R.id.tvSingerName);
        seekBar = findViewById(R.id.seekBar);
        secureStorage = new SecureStorage(PlayMusicActivity.this);



        i = getIntent();

        tvSongName.setText(i.getStringExtra("songname"));
        tvSingerName.setText(i.getStringExtra("singername"));
        int songid = i.getIntExtra("songid",-1);
        if(songid == -1){finish();}else{
            ApiService apiService = RetrofitClient
                    .getRetrofit( LoggedUser.loggedUser.getAccessToken()) // Kiểm tra lại port cho đúng
                    .create(ApiService.class);
            Call<Void> call = apiService.listensong(songid);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });




        Glide.with(PlayMusicActivity.this)
                .load(i.getStringExtra("avatarurl"))
                .placeholder(R.drawable.loadingimg) // Ảnh tạm
                .error(R.drawable.ic_notfound)       // Ảnh lỗi
                .into(imageView);


        audiourl = i.getStringExtra("songurl");


        prepareMediaPlayer(audiourl);

        // 3. Sự kiện Click
        btnBack.setOnClickListener(v -> finish());

        btnPlayPause.setOnClickListener(v -> {
            // Kiểm tra kỹ xem mediaPlayer đã tải xong chưa
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    btnPlayPause.setImageResource(mediaPlayer.isPlaying()?R.drawable.ic_pause:R.drawable.ic_play);
                } else {
                    mediaPlayer.start();
                    btnPlayPause.setImageResource(mediaPlayer.isPlaying()?R.drawable.ic_pause:R.drawable.ic_play);
                }
            } else {
//
            }
        });

        // 4. Sự kiện SeekBar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (fromUser && mediaPlayer != null) {
                    mediaPlayer.seekTo(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // 5. Sự kiện khi hát xong (Chuyển xuống đây cho gọn)
        if (mediaPlayer != null) {
            mediaPlayer.setOnCompletionListener(mp -> {
                btnPlayPause.setImageResource(mediaPlayer.isPlaying()?R.drawable.ic_pause:R.drawable.ic_play);
                seekBar.setProgress(0);
            });
        }
    }}

    private void prepareMediaPlayer(String url) {
        try {
            mediaPlayer = new MediaPlayer();

            mediaPlayer.setDataSource(url);

            // Sử dụng prepareAsync để không bị đơ màn hình khi tải nhạc mạng
            mediaPlayer.prepareAsync();

            // Khi nào tải xong nhạc thì mới cho phép chạy và lấy độ dài
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    seekBar.setMax(mediaPlayer.getDuration());
                     mediaPlayer.start();
                    btnPlayPause.setImageResource(mediaPlayer.isPlaying()?R.drawable.ic_pause:R.drawable.ic_play);

//                    Toast.makeText(PlayMusicActivity.this, "Đã tải xong nhạc!", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi kết nối nguồn nhạc", Toast.LENGTH_SHORT).show();
        }

        updateSeekBar();
    }

    private void updateSeekBar() {
        runnable = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                }
                handler.postDelayed(this, 500);
            }
        };
        handler.post(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        handler.removeCallbacks(runnable);
    }
}