package com.example.agrimart.Adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.agrimart.Model.Farmer
import com.example.agrimart.Activity.MessageActivity
import com.example.agrimart.R

class WorkerAdapter(
    private val context: Context,
    private val users: List<Farmer>
) : RecyclerView.Adapter<WorkerAdapter.WorkerViewHolder>() {

    class WorkerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pic: ImageView = itemView.findViewById(R.id.pic)
        val call: RelativeLayout = itemView.findViewById(R.id.call)
        val chat: RelativeLayout = itemView.findViewById(R.id.chat)
        val name: TextView = itemView.findViewById(R.id.name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_worker, parent, false)
        return WorkerViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkerViewHolder, position: Int) {
        val user = users[position]
        holder.name.text = user.name

        if(user.pic==""){
            Glide.with(holder.pic.context)
                .load(R.drawable.profile)
                .into(holder.pic)
        }else{
            Glide.with(holder.pic.context)
                .load(user.pic)
                .into(holder.pic)
        }

        holder.call.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:"+user.phone)
            context.startActivity(intent)
        }



        holder.chat.setOnClickListener{
            var intent = Intent(context, MessageActivity::class.java)
            intent.putExtra("opId",user.id)
            context.startActivity(intent)
        }





    }

    override fun getItemCount(): Int = users.size
}
