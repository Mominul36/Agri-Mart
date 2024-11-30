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
import com.example.agrimart.Model.Seedling
import com.example.agrimart.R
import com.google.firebase.database.FirebaseDatabase

class MySeedlingAdapter(
    private val context: Context,
    private val seedlingList: MutableList<Seedling>
) : RecyclerView.Adapter<MySeedlingAdapter.MySeedlingViewHolder>() {

    class MySeedlingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pic: ImageView = itemView.findViewById(R.id.pic)
        var id: TextView = itemView.findViewById(R.id.id)
        var name: TextView = itemView.findViewById(R.id.name)
        var rate: TextView = itemView.findViewById(R.id.rate)
        var rating: TextView = itemView.findViewById(R.id.rating)
        var delete: ImageView = itemView.findViewById(R.id.delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySeedlingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_my_seedling, parent, false)
        return MySeedlingViewHolder(view)
    }

    override fun onBindViewHolder(holder: MySeedlingViewHolder, position: Int) {
        val seedling = seedlingList[position]

        holder.id.text = "S-Id: ${seedling.id ?: "N/A"}"
        holder.name.text = seedling.name ?: "Unknown"
        holder.rate.text = "${seedling.rate ?: "0"} taka/${seedling.unit ?: "unit"}"
        holder.rating.text = "Rating: ${seedling.rating ?: "0.0"}"

        Glide.with(context).load(seedling.pic).into(holder.pic)

        holder.delete.setOnClickListener {
            showDeleteConfirmationDialog(position, seedling.id)
        }
    }

    override fun getItemCount() = seedlingList.size

    private fun showDeleteConfirmationDialog(position: Int, fertilizerId: String?) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Delete Seedling")
        builder.setMessage("Are you sure you want to delete this seedling?")
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
        seedlingList.removeAt(position)
        notifyItemRemoved(position)

        fertilizerId?.let {
            FirebaseDatabase.getInstance().getReference("Seedling")
                .child(it)
                .removeValue()
                .addOnSuccessListener {
                    Toast.makeText(context, "Seedling deleted successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed to delete seedling", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
