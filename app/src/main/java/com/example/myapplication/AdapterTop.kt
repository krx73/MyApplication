package com.example.myapplication

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.logic_with_interface.InterfaceImImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdapterTop(private val topUsersList: List<UserModel>) :
    RecyclerView.Adapter<AdapterTop.TopUsersViewHolder>() {
    class TopUsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageViewAvatar: ImageView = itemView.findViewById(R.id.card_user_avatar)
        var textViewUserName: TextView = itemView.findViewById(R.id.card_user_name)
        var textViewScore: TextView = itemView.findViewById(R.id.card_user_score)
        val textPoints: TextView = itemView.findViewById(R.id.textView_points)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopUsersViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.card_top, parent, false)
        return TopUsersViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return topUsersList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TopUsersViewHolder, position: Int) {
        val currentItem = topUsersList[position]
        val userImageImpl = InterfaceImImpl()
        val currentImageUri = currentItem.userImage.toUri()
        if (currentItem.userImage.isNotEmpty()) {
            CoroutineScope(Dispatchers.Main).launch {
                val userImageBitmap = userImageImpl.getBitmapFromUri(currentImageUri)
                holder.imageViewAvatar.scaleType = ImageView.ScaleType.CENTER_CROP
                holder.imageViewAvatar.setImageBitmap(userImageBitmap)
            }
        } else {
            holder.imageViewAvatar.setImageResource(R.color.white)
        }
        holder.textViewUserName.text = "${currentItem.firstname} ${currentItem.lastname}"
        holder.textViewScore.text = currentItem.score.toString()
        holder.textPoints.text = " points"
    }
}