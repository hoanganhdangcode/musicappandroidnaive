package com.example.musicapp.commons;

import java.io.IOException;
import java.security.cert.CertificateException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class HttpClient {
    public static OkHttpClient getClient(String jwtToken) {
        try {
            // 1. Tạo TrustManager chấp nhận mọi chứng chỉ (Vượt lỗi SSL của .NET local)
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {}
                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {}
                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // 2. Cấu hình SSLContext
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // 3. Giữ nguyên phần Logging của Thầy
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            // 4. Xây dựng Builder với cấu hình mới + cấu hình cũ của Thầy
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]) // Thêm dòng này
                    .hostnameVerifier((hostname, session) -> true) // Thêm dòng này
                    .addInterceptor(logging);

            // 5. Giữ nguyên phần xử lý Token của Thầy
            if (jwtToken != null && !jwtToken.isEmpty()) {
                builder.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .header("Authorization", "Bearer " + jwtToken)
                                .build();
                        return chain.proceed(request);
                    }
                });
            }

            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}