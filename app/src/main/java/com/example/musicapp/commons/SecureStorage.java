package com.example.musicapp.commons;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class SecureStorage {

    private static final String PREFS_NAME = "secure_prefs";
    private SharedPreferences sharedPreferences;

    public SecureStorage(Context context) {
        try {
            MasterKey masterKey = new MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            sharedPreferences = EncryptedSharedPreferences.create(
                    context,
                    PREFS_NAME,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }

    public void putString(String key, String value) {
        if (sharedPreferences != null) {
            sharedPreferences.edit().putString(key, value).apply();
        }
    }

    public String getString(String key, String defaultValue) {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(key, defaultValue);
        }
        return defaultValue;
    }
    public void putInt(String key, int value) {

        if (sharedPreferences != null) {
            sharedPreferences.edit().putInt(key, value).apply();
        }
        }
        public int getInt(String key, int defaultValue) {
        if (sharedPreferences != null) {
            return sharedPreferences.getInt(key, defaultValue);
        }
        return defaultValue;
        }
        public void remove(String key) {
            if (sharedPreferences != null) {
                sharedPreferences.edit().remove(key).apply();
            }
        }
        public void removepref(){
            if (sharedPreferences != null) {
                sharedPreferences.edit().clear().apply();
            }
            }

}