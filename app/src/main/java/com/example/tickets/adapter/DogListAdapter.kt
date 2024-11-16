package com.example.tickets.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.Executors
import com.example.tickets.databinding.ItemOfferBinding
import com.example.tickets.model.network.Dog

class DogListAdapter : RecyclerView.Adapter<DogListAdapter.ViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<Dog>() {
        override fun areItemsTheSame(oldItem: Dog, newItem: Dog):Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    fun saveData( dogs: List<Dog>){
        Log.d("ssssssssssssssssssssssssssssss", dogs.toString())
        asyncListDiffer.submitList(dogs)
    }






    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemOfferBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = asyncListDiffer.currentList[position]
        holder.bind(data)
    }


    inner class ViewHolder(
        val binding: ItemOfferBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val context = binding.root.context

        fun bind(dog: Dog) {
            binding.name.text=dog.name
            binding.protectiveness.text="Protect: "+ dog.protectiveness
            binding.trainability.text="Train: "+dog.trainability
            binding.energy.text="Energy: "+dog.energy
            binding.lifeExpect.text="Life span: "+dog.min_life_expectancy
            setImage(binding, dog.image_link)
            Log.d("uuuuuuuuuuuuuuuuu","seeeettttttttttttttttttuuuuupppppppppppp")
        }


        // Declaring executor to parse the URL


    }
    fun setImage(binding:ItemOfferBinding, url:String?){
        val executor = Executors.newSingleThreadExecutor()

        // Once the executor parses the URL
        // and receives the image, handler will load it
        // in the ImageView
        val handler = Handler(Looper.getMainLooper())

        // Initializing the image
        var image: Bitmap? = null

        // Only for Background process (can take time depending on the Internet speed)
        executor.execute {

            // Tries to get the image and post it in the ImageView
            // with the help of Handler
            try {
                val `in` = java.net.URL(url).openStream()
                image = BitmapFactory.decodeStream(`in`)

                // Only for making changes in UI
                handler.post {
                    binding.image.setImageBitmap(image)
                }
            }

            // If the URL doesnot point to
            // image or any other kind of failure
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}