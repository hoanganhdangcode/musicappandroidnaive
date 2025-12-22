//package com.example.musicapp.commons;
//
//import com.example.musicapp.DTO.LoginDTO;
//import com.example.musicapp.DTO.LoginResponseDTO;
//import com.example.musicapp.models.User;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class ApiHelper {
//
//    public void login1(String email, String password){
//        ApiService apiService = RetrofitClient
//                .getRetrofit("https://127.0.0.1:7007/", null)
//                .create(ApiService.class);
//
//        LoginDTO request = new LoginDTO(email, password);
//        Call<LoginResponseDTO> call = apiService.login(request);
//
//        call.enqueue(new Callback<LoginResponseDTO>() {
//            @Override
//            public void onResponse(Call<LoginResponseDTO> call, Response<LoginResponseDTO> response) {
//                if(response.isSuccessful() && response.body() != null){
//                    LoginResponseDTO dto = response.body();
//                    // Map sang model User phẳng
//                    User user = new User();
//                    user.setUserId(dto.user.userId);
//                    user.setName(dto.user.name);
//                    user.setEmail(dto.user.email);
//                    user.setAvatarUrl(dto.user.avatarurl);
//                    user.setRole(dto.user.role);
//                    user.setAccessToken(dto.accessToken);
//
//                    System.out.println("Login thành công: " + user.getName());
//                } else {
//                    System.out.println("Login thất bại, code: " + response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LoginResponseDTO> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//    }
//}
