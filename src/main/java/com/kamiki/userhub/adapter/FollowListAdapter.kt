package com.kamiki.userhub.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kamiki.userhub.databinding.ItemUserBinding
import com.kamiki.userhub.ui.userview.DetailUserActivity

class FollowListAdapter(private val listUser: ArrayList<String>) :
    RecyclerView.Adapter<FollowListAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            val arrayUser = item.split(";")
            Glide.with(binding.root).load(arrayUser[0]).into(binding.img)
            binding.tvUsername.text = arrayUser[1].replace(".","")

            val str = arrayUser[1]
            val newStr = str.replace(".", "")

            binding.root.setOnClickListener{
                val intent = Intent(binding.root.context, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_USERNAME, newStr)
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(listUser[position])
    }

    override fun getItemCount() = listUser.size

}