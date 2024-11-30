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
import com.example.agrimart.Activity.SeedDetailsActivity
import com.example.agrimart.Model.Seed
import com.example.agrimart.R

class SeedAdapter(
    private val context: Context,
    private val seedList: MutableList<Seed>
) : RecyclerView.Adapter<SeedAdapter.SeedViewHolder>() {

    class SeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pic: ImageView = itemView.findViewById(R.id.pic)
        var name: TextView = itemView.findViewById(R.id.name)
        var rate: TextView = itemView.findViewById(R.id.rate)
        var rating: TextView = itemView.findViewById(R.id.rating)
        var show: ImageView = itemView.findViewById(R.id.show)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_seed, parent, false)
        return SeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: SeedViewHolder, position: Int) {
        val seed = seedList[position]

        holder.name.text = seed.name  ?: "Unknown"
        holder.rating.text = seed.rating  ?: "Unknown"
        holder.rate.text = "৳${seed.rate}/${seed.unit}"
        Glide.with(context).load(seed.pic).into(holder.pic)

        holder.show.setOnClickListener {

         var intent = Intent(context,SeedDetailsActivity::class.java)
            intent.putExtra("seedId",seed.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = seedList.size

}
