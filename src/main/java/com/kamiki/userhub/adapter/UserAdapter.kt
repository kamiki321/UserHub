package com.kamiki.userhub.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kamiki.userhub.data.response.UserResponse
import com.kamiki.userhub.databinding.ItemUserBinding
import com.kamiki.userhub.ui.userview.DetailUserActivity

class UserAdapter(private val listUser: List<UserResponse.Item>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = listUser.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(listUser[position])
    }

    class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UserResponse.Item) {
            binding.tvUsername.text = item.login
            Glide.with(binding.root).load(item.avatarUrl).circleCrop().into(binding.img)

            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_USERNAME, item.login)
                binding.root.context.startActivity(intent)
            }
        }
    }
}