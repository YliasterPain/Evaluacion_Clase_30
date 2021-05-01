package com.example.evaluacin_02_individual

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.evaluacin_02_individual.databinding.ItemDogBinding
import com.squareup.picasso.Picasso
//Esta clase tiene una funcion que sera llamada de adapter y le pasara un url en formato string
class DogViewHolder(view: View):RecyclerView.ViewHolder(view) {

    private val binding = ItemDogBinding.bind(view)

    //combertimos url a imagen con la libreria picasso
    fun bind(images:String){
        Picasso.get().load(images).into(binding.ivDog)

    }
}