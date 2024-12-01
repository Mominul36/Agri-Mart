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
import com.example.agrimart.Activity.PesticideActivity
import com.example.agrimart.Activity.PesticideDetailsActivity
import com.example.agrimart.Activity.SeedDetailsActivity
import com.example.agrimart.Model.Fertilizer
import com.example.agrimart.Model.Pesticide
import com.example.agrimart.Model.Seed
import com.example.agrimart.R

class PesticideAdapter(
    private val context: Context,
    private val pesticideList: MutableList<Pesticide>
) : RecyclerView.Adapter<PesticideAdapter.PesticideViewHolder>() {

    class PesticideViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pic: ImageView = itemView.findViewById(R.id.pic)
        var name: TextView = itemView.findViewById(R.id.name)
        var rate: TextView = itemView.findViewById(R.id.rate)
        var rating: TextView = itemView.findViewById(R.id.rating)
        var show: ImageView = itemView.findViewById(R.id.show)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PesticideViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_seed, parent, false)
        return PesticideViewHolder(view)
    }

    override fun onBindViewHolder(holder: PesticideViewHolder, position: Int) {
        val pesticide = pesticideList[position]

        holder.name.text = pesticide.name  ?: "Unknown"
        holder.rating.text = pesticide.rating  ?: "Unknown"
        holder.rate.text = "৳${pesticide.rate}/${pesticide.unit}"
        Glide.with(context).load(pesticide.pic).into(holder.pic)

        holder.show.setOnClickListener {

            var intent = Intent(context,PesticideDetailsActivity::class.java)
            intent.putExtra("pesticideId",pesticide.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = pesticideList.size

}
