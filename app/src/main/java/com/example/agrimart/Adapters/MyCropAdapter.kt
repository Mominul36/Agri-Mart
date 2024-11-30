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
import com.example.agrimart.Model.Crop
import com.example.agrimart.Model.Seed
import com.example.agrimart.R
import com.google.firebase.database.FirebaseDatabase

class MyCropAdapter(
    private val context: Context,
    private val cropList: MutableList<Crop>
) : RecyclerView.Adapter<MyCropAdapter.MyCropViewHolder>() {

    class MyCropViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pic: ImageView = itemView.findViewById(R.id.pic)
        var id: TextView = itemView.findViewById(R.id.id)
        var name: TextView = itemView.findViewById(R.id.name)
        var rate: TextView = itemView.findViewById(R.id.rate)
        var rating: TextView = itemView.findViewById(R.id.rating)
        var delete: ImageView = itemView.findViewById(R.id.delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCropViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_my_crop, parent, false)
        return MyCropViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyCropViewHolder, position: Int) {
        val crop = cropList[position]

        holder.id.text = "C-Id: ${crop.id ?: "N/A"}"
        holder.name.text = crop.name ?: "Unknown"
        holder.rate.text = "${crop.rate ?: "0"} taka/${crop.unit ?: "unit"}"
        holder.rating.text = "Rating: ${crop.rating ?: "0.0"}"

        Glide.with(context).load(crop.pic).into(holder.pic)

        holder.delete.setOnClickListener {
            showDeleteConfirmationDialog(position, crop.id)
        }
    }

    override fun getItemCount() = cropList.size

    private fun showDeleteConfirmationDialog(position: Int, fertilizerId: String?) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Delete Crop")
        builder.setMessage("Are you sure you want to delete this crop?")
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
        cropList.removeAt(position)
        notifyItemRemoved(position)

        fertilizerId?.let {
            FirebaseDatabase.getInstance().getReference("Crop")
                .child(it)
                .removeValue()
                .addOnSuccessListener {
                    Toast.makeText(context, "Crop deleted successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed to delete crop", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
