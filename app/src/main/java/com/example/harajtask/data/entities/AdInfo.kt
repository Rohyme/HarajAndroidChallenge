package com.example.harajtask.data.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "adTable")
data class AdInfo(
    @SerializedName("body")
    val body: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("commentCount")
    val commentCount: Int,
    @SerializedName("date")
    val date: Long,
    @SerializedName("thumbURL")
    val thumbURL: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("username")
    val username: String
){
    @PrimaryKey(autoGenerate = true) var id: Int? = null
}