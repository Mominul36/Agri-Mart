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
import com.example.agrimart.Activity.FertilizerActivity
import com.example.agrimart.Activity.FertilizerDetailsActivity
import com.example.agrimart.Activity.SeedDetailsActivity
import com.example.agrimart.Model.Fertilizer
import com.example.agrimart.Model.Seed
import com.example.agrimart.R

class FertilizerAdapter(
    private val context: Context,
    private val fertilizerList: MutableList<Fertilizer>
) : RecyclerView.Adapter<FertilizerAdapter.FertilizerViewHolder>() {

    class FertilizerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pic: ImageView = itemView.findViewById(R.id.pic)
        var name: TextView = itemView.findViewById(R.id.name)
        var rate: TextView = itemView.findViewById(R.id.rate)
        var rating: TextView = itemView.findViewById(R.id.rating)
        var show: ImageView = itemView.findViewById(R.id.show)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FertilizerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_seed, parent, false)
        return FertilizerViewHolder(view)
    }

    override fun onBindViewHolder(holder: FertilizerViewHolder, position: Int) {
        val fertilizer = fertilizerList[position]

        holder.name.text = fertilizer.name  ?: "Unknown"
        holder.rating.text = fertilizer.rating  ?: "Unknown"
        holder.rate.text = "৳${fertilizer.rate}/${fertilizer.unit}"
        Glide.with(context).load(fertilizer.pic).into(holder.pic)

        holder.show.setOnClickListener {

            var intent = Intent(context,FertilizerDetailsActivity::class.java)
            intent.putExtra("fertilizerId",fertilizer.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = fertilizerList.size

}
