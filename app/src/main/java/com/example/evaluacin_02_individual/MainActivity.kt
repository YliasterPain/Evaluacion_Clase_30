package com.example.evaluacin_02_individual

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.evaluacin_02_individual.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(),SearchView.OnQueryTextListener {
    //nueva forma mas practica de trabajar con referencias de la vista
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: DogsAdapter
    private val dogImages =  mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.svDogs.setOnQueryTextListener(this)
        initRecycleview()
    }

    private fun initRecycleview() {
        adapter= DogsAdapter(dogImages)
        binding.rvDogs.layoutManager = LinearLayoutManager(this)
        binding.rvDogs.adapter=adapter
    }

    //metodo vuelve Retrofit tendra url original, comversor de json a modelo de datos y configuracion de llamada a internet
    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //creamos una corrutina para que todo lo que corra dentro estara en un hilo secundario
    // (para que no de error al llamar a internet )
    private fun searchName(query:String){
        CoroutineScope(Dispatchers.IO).launch {
            val call:Response<DogsResponse> = getRetrofit().create(ApiService::class.java).getDogsByBreeds("$query/images")
            val puppies = call.body()
            runOnUiThread{
                if(call.isSuccessful){
                    val images = puppies?.images ?: emptyList()
                    dogImages.clear()
                    dogImages.addAll(images)
                    adapter.notifyDataSetChanged()
                    //show recyclerview
                }else{
                    showError()
                }
            }
        }
    }
//funcion que muestra un toas si no consige la imagen
    private fun showError() {
        Toast.makeText(this, "Error no se puede optener datos", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            searchName(query.toLowerCase())
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}