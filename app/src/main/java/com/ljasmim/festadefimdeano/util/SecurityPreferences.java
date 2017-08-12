package com.ljasmim.festadefimdeano.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Classe que encapsula a classe SharedPreferences
 *
 * Created by ljasmim on 11/08/17.
 */

public class SecurityPreferences {

    private final SharedPreferences mSharedPreferences;

    public SecurityPreferences(Context context){
        this.mSharedPreferences = context.getSharedPreferences("FimDeAno", Context.MODE_PRIVATE);
    }

    public void storeString(String key, String value){
        this.mSharedPreferences.edit().putString(key, value).apply();
    }

    public String getStoredString(String key){
        return this.mSharedPreferences.getString(key,"");
    }

}
