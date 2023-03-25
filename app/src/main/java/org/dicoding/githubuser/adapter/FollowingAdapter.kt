package org.dicoding.githubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.dicoding.githubuser.databinding.ItemRowFollowingBinding
import org.dicoding.githubuser.models.ItemsItem
import org.dicoding.githubuser.ui.DetailActivity

class FollowingAdapter (private val listFollowing:List<ItemsItem>):
    RecyclerView.Adapter<FollowingAdapter.ListViewHolder>(){
    class ListViewHolder(var binding: ItemRowFollowingBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemRowFollowingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ListViewHolder, position: Int) {
        listFollowing[position].let { item ->
            viewHolder.binding.tvItemName.text = item.login
            Glide.with(viewHolder.binding.root.context)
                .load(item.avatarUrl)
                .into(viewHolder.binding.imgItemPhoto)
        }
        viewHolder.itemView.setOnClickListener{
            val intentDetail = Intent(viewHolder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra(DetailActivity.EXTRA_DETAIL_USER, listFollowing[viewHolder.adapterPosition].login)
            viewHolder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount() = listFollowing.size
}