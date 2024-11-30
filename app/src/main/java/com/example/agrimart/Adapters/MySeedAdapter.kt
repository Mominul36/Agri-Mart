package com.example.agrimart.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.agrimart.Model.Seed
import com.example.agrimart.R
import com.google.firebase.database.FirebaseDatabase

class MySeedAdapter(
    private val context: Context,
    private val seedList: MutableList<Seed>
) : RecyclerView.Adapter<MySeedAdapter.MySeedViewHolder>() {

    class MySeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pic: ImageView = itemView.findViewById(R.id.pic)
        var id: TextView = itemView.findViewById(R.id.id)
        var name: TextView = itemView.findViewById(R.id.name)
        var rate: TextView = itemView.findViewById(R.id.rate)
        var rating: TextView = itemView.findViewById(R.id.rating)
        var delete: ImageView = itemView.findViewById(R.id.delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySeedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_my_seed, parent, false)
        return MySeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: MySeedViewHolder, position: Int) {
        val seed = seedList[position]

        holder.id.text = "S-Id: ${seed.id ?: "N/A"}"
        holder.name.text = seed.name ?: "Unknown"
        holder.rate.text = "${seed.rate ?: "0"} taka/${seed.unit ?: "unit"}"
        holder.rating.text = "Rating: ${seed.rating ?: "0.0"}"

        Glide.with(context).load(seed.pic).into(holder.pic)

        holder.delete.setOnClickListener {
            showDeleteConfirmationDialog(position, seed.id)
        }
    }

    override fun getItemCount() = seedList.size

    private fun showDeleteConfirmationDialog(position: Int, fertilizerId: String?) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Delete Seed")
        builder.setMessage("Are you sure you want to delete this seed?")
        builder.setPositiveButton("Yes") { dialog, _ ->
            deleteFertilizer(position, fertilizerId)
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    private fun deleteFertilizer(position: Int, fertilizerId: String?) {
        seedList.removeAt(position)
        notifyItemRemoved(position)

        fertilizerId?.let {
            FirebaseDatabase.getInstance().getReference("Seed")
                .child(it)
                .removeValue()
                .addOnSuccessListener {
                    Toast.makeText(context, "Seed deleted successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed to delete seed", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
