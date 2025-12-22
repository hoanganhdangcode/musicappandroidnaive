package com.example.musicapp.commons;


    import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

    public class RetrofitClient {
        private static Retrofit retrofit;

        public static Retrofit getRetrofit(String baseUrl, String jwtToken) {
            if(retrofit == null){
                retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .client(HttpClient.getClient(jwtToken))
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }
    }


