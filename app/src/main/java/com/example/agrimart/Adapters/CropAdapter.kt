package com.example.agrimart.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.agrimart.Activity.CropDetailsActivity
import com.example.agrimart.Activity.SeedDetailsActivity
import com.example.agrimart.Model.Crop
import com.example.agrimart.Model.Seed
import com.example.agrimart.R

class CropAdapter(
    private val context: Context,
    private val cropList: MutableList<Crop>
) : RecyclerView.Adapter<CropAdapter.CropViewHolder>() {

    class CropViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pic: ImageView = itemView.findViewById(R.id.pic)
        var name: TextView = itemView.findViewById(R.id.name)
        var rate: TextView = itemView.findViewById(R.id.rate)
        var rating: TextView = itemView.findViewById(R.id.rating)
        var show: ImageView = itemView.findViewById(R.id.show)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CropViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_seed, parent, false)
        return CropViewHolder(view)
    }

    override fun onBindViewHolder(holder: CropViewHolder, position: Int) {
        val crop = cropList[position]

        holder.name.text = crop.name  ?: "Unknown"
        holder.rating.text = crop.rating  ?: "Unknown"
        holder.rate.text = "৳${crop.rate}/${crop.unit}"
        Glide.with(context).load(crop.pic).into(holder.pic)

        holder.show.setOnClickListener {

         var intent = Intent(context,CropDetailsActivity::class.java)
            intent.putExtra("cropId",crop.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = cropList.size

}
