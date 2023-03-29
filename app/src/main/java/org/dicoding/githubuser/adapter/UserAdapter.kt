package org.dicoding.githubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.dicoding.githubuser.databinding.ItemRowUserBinding
import org.dicoding.githubuser.models.ItemsItem
import org.dicoding.githubuser.ui.DetailActivity

class UserAdapter(private val listUser:List<ItemsItem>):RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int):ListViewHolder {
        val binding =
            ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    class ListViewHolder(var binding:ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onBindViewHolder(viewHolder: ListViewHolder, position: Int) {
        listUser[position].let { item ->
            viewHolder.binding.tvItemName.text = item.login
            Glide.with(viewHolder.binding.root.context)
                .load(item.avatarUrl)
                .into(viewHolder.binding.imgItemPhoto)
            viewHolder.itemView.setOnClickListener{
                val intentDetail = Intent(viewHolder.itemView.context, DetailActivity::class.java)
                intentDetail.putExtra(DetailActivity.EXTRA_DETAIL_USER, listUser[viewHolder.adapterPosition].login)
                intentDetail.putExtra(DetailActivity.EXTRA_ID, listUser[viewHolder.adapterPosition].id)
                intentDetail.putExtra(DetailActivity.EXTRA_IMG, listUser[viewHolder.adapterPosition].avatarUrl)
                viewHolder.itemView.context.startActivity(intentDetail)
            }
        }

    }

    override fun getItemCount() = listUser.size

}
