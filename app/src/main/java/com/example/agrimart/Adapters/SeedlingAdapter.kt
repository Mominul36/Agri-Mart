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
import com.example.agrimart.Activity.SeedlingDetailsActivity
import com.example.agrimart.Model.Seed
import com.example.agrimart.Model.Seedling
import com.example.agrimart.R

class SeedlingAdapter(
    private val context: Context,
    private val seedlingList: MutableList<Seedling>
) : RecyclerView.Adapter<SeedlingAdapter.SeedlingViewHolder>() {

    class SeedlingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pic: ImageView = itemView.findViewById(R.id.pic)
        var name: TextView = itemView.findViewById(R.id.name)
        var rate: TextView = itemView.findViewById(R.id.rate)
        var rating: TextView = itemView.findViewById(R.id.rating)
        var show: ImageView = itemView.findViewById(R.id.show)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeedlingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_seed, parent, false)
        return SeedlingViewHolder(view)
    }

    override fun onBindViewHolder(holder: SeedlingViewHolder, position: Int) {
        val seedling = seedlingList[position]

        holder.name.text = seedling.name  ?: "Unknown"
        holder.rating.text = seedling.rating  ?: "Unknown"
        holder.rate.text = "৳${seedling.rate}/${seedling.unit}"
        Glide.with(context).load(seedling.pic).into(holder.pic)

        holder.show.setOnClickListener {

         var intent = Intent(context, SeedlingDetailsActivity::class.java)
            intent.putExtra("seedlingId",seedling.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = seedlingList.size

}
