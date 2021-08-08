package com.example.harajtask.presenter.mainAdsList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.harajtask.data.entities.AdInfo
import com.example.harajtask.databinding.RecyclerItemAdBinding

class AdsAdapter(private val onAdInfoClicked: (AdInfo)-> Unit) : ListAdapter<AdInfo, AdsAdapter.AdPlaceHolder>(AdDiff) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdPlaceHolder = AdPlaceHolder(RecyclerItemAdBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: AdPlaceHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bindItem(currentItem)
        holder.onClickListener {
            onAdInfoClicked.invoke(currentItem)
        }
    }

    class AdPlaceHolder(private val binding: RecyclerItemAdBinding ): RecyclerView.ViewHolder(binding.root){
        fun bindItem(adInfo: AdInfo) {
            binding.adModel = adInfo
        }
        fun onClickListener(block: () -> Unit) {
            binding.root.setOnClickListener {
                block.invoke()
            }
        }
    }

    object AdDiff: DiffUtil.ItemCallback<AdInfo>() {
        override fun areItemsTheSame(oldItem: AdInfo, newItem: AdInfo): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: AdInfo, newItem: AdInfo): Boolean {
            return oldItem.id == newItem.id
        }

    }

}