package com.example.musicapp.views.User.UserMainFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.musicapp.R;
import com.example.musicapp.commons.LoggedUser;
import com.example.musicapp.models.User;
import com.example.musicapp.views.LoginActivity;
import com.example.musicapp.views.User.PlayMusicActivity;

public class ProfileFragment extends Fragment {

    ImageView imageView;
    TextView textView;

    Button btndangxuat;


    public ProfileFragment() {
        // Constructor rỗng
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.img_avatar);
        textView = view.findViewById(R.id.tv_username);
        btndangxuat =  view.findViewById(R.id.btnLogout);

        textView.setText(LoggedUser.loggedUser.name);

        Glide.with(getActivity())
                .load(LoggedUser.loggedUser.avatarUrl)
                .placeholder(R.drawable.ic_launcher_background) // Ảnh tạm
                .error(R.drawable.ic_launcher_background)       // Ảnh lỗi
                .into(imageView);
        btndangxuat.setOnClickListener(v -> {
            LoggedUser.loggedUser = new User();
            startActivity(new Intent(getContext(), LoginActivity.class));
            getActivity().finish();

        });


    }

}