package com.navi.dogbreedapp.doglist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.navi.dogbreedapp.DogModel
import com.navi.dogbreedapp.databinding.DogListItemBinding

class DogAdapter : ListAdapter<DogModel, DogAdapter.DogViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<DogModel>() {
        override fun areItemsTheSame(oldItem: DogModel, newItem: DogModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DogModel, newItem: DogModel): Boolean {
            return oldItem.id == newItem.id
        }

    }

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val binding = DogListItemBinding.inflate(LayoutInflater.from(parent.context))
        return DogViewHolder(binding)
    }

    override fun onBindViewHolder(dogViewHolder: DogViewHolder, position: Int) {
       val dog = getItem(position)
        dogViewHolder.bind(dog)
    }


    inner class DogViewHolder(private val binding: DogListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(dog: DogModel) {
                binding.dogName.text = dog.name
            }
    }

}