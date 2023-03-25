package org.dicoding.githubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.dicoding.githubuser.databinding.ItemRowFollowerBinding
import org.dicoding.githubuser.models.ItemsItem
import org.dicoding.githubuser.ui.DetailActivity

class FollowerAdapter(private val listFollower:List<ItemsItem>):
    RecyclerView.Adapter<FollowerAdapter.ListViewHolder>() {
    class ListViewHolder(var binding: ItemRowFollowerBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemRowFollowerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ListViewHolder, position: Int) {
        listFollower[position].let { item ->
            viewHolder.binding.tvItemName.text = item.login
            Glide.with(viewHolder.binding.root.context)
                .load(item.avatarUrl)
                .into(viewHolder.binding.imgItemPhoto)
        }
        viewHolder.itemView.setOnClickListener{
            val intentDetail = Intent(viewHolder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra(DetailActivity.EXTRA_DETAIL_USER, listFollower[viewHolder.adapterPosition].login)
            viewHolder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount() = listFollower.size


}