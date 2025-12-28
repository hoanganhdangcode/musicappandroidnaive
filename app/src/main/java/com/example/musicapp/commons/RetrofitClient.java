package com.example.musicapp.commons;


    import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

    public class RetrofitClient {
        private static Retrofit retrofit;
        private static String baseUrlclient = "https://192.168.30.3:7007/";


        public static Retrofit getRetrofit(String jwtToken) {
            if(retrofit == null){
                retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrlclient)
                        .client(HttpClient.getClient(jwtToken))
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }
    }


