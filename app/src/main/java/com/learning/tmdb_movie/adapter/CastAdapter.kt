package com.learning.tmdb_movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.learning.tmdb_movie.Util.IMAGE_BASE_URL
import com.learning.tmdb_movie.databinding.ListItemCastBinding
import com.learning.tmdb_movie.model.Detail.CastItem

class CastAdapter(val castList : List<CastItem>) : RecyclerView.Adapter<CastAdapter.CastViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CastViewHolder =
        CastViewHolder(ListItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(
        holder: CastViewHolder,
        position: Int
    ) {
        holder.bind(castList[position])
    }

    override fun getItemCount(): Int =
        castList.size

    inner class CastViewHolder(val binding : ListItemCastBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(castItem : CastItem) = with(binding){
            Glide.with(root.context)
                .load(IMAGE_BASE_URL + castItem.profilePath)
                .into(ivPoster)
            tvTitle.text = castItem.name
        }
    }
}