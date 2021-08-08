package com.example.harajtask.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

object ViewBindingUtil {
    @BindingAdapter("isVisible")
    @JvmStatic
    fun View.setViewVisibility(isVisible:Boolean?){
        if (isVisible == null) return
        visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    @BindingAdapter("imgUrl")
    @JvmStatic
    fun ImageView.setImageUrl(imgUrl:String?){
        if (imgUrl == null) return
        Glide.with(context)
            .load(imgUrl)
            .into(this)
    }

    @BindingAdapter("adDate")
    @JvmStatic
    fun TextView.setDate(date: Long?){
        if (date == null) return
        RelativeTime.getTimeAgo(date)?.let {
            text = it
        }
    }
    @BindingAdapter("adDateFormatted")
    @JvmStatic
    fun TextView.dateFormatted(date: Long?){
        if (date == null) return
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.ENGLISH)
        sdf.format(Date(date)).let {
            text = it
        }

    }
}