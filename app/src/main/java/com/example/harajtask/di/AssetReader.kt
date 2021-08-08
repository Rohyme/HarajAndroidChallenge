package com.example.harajtask.di

import android.content.Context
import com.example.harajtask.data.entities.AdInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class AssetReader(private val gson: Gson, private val context: Context) {


    fun  readJson(fileName: String): List<AdInfo>? {
        return getJsonDataFromAsset(fileName)?.let { gson.fromJson(it, object: TypeToken<List<AdInfo>>(){}.type) }
    }

    private fun getJsonDataFromAsset(fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

}