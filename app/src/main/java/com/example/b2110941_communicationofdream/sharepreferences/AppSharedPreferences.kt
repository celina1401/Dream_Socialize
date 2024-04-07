package com.example.b2110941_communicationofdream.sharepreferences

import android.content.Context
import android.content.SharedPreferences

class AppSharedPreferences(val context: Context) {
    fun putIdUser(key: String, value: String){
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("BEST_WISHES",0)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getIdUser(key: String):String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("BEST_WISHES",0)
        return sharedPreferences.getString(key,"")
    }

    fun pushWish(key: String, value: String){
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("BEST_WISHES", 0)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }
    fun getWish(key: String): String?{
        val sharedPreferences:SharedPreferences = context.getSharedPreferences("BEST_WISHES", 0)
        return sharedPreferences.getString(key,"")
    }
}