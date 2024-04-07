package com.example.b2110941_communicationofdream.adapters

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.b2110941_communicationofdream.R
import com.example.b2110941_communicationofdream.databinding.ItemWishBinding
import com.example.b2110941_communicationofdream.models.Wish
import com.example.b2110941_communicationofdream.sharepreferences.AppSharedPreferences


class WishAdapter (
    private val context: Context,
    private val wishList: List<Wish>,
    private val appSharedPreferences: AppSharedPreferences,
    //Truyền interface từ bên ngoài vào
    private val iClickItemWish: IClickItemWish,
): RecyclerView.Adapter<WishAdapter.WishViewHolder>(){
    class WishViewHolder(val binding: ItemWishBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishViewHolder {
        return WishViewHolder(ItemWishBinding
            .inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return wishList.size
    }

    override fun onBindViewHolder(holder: WishViewHolder, position: Int) {
        val wish: Wish = wishList[position]
        holder.binding.tvName.text = wish.name
        holder.binding.tvContent.text = wish.content
        Glide.with(context).load(wish.owner.avatar)
            .error(R.drawable.avt_default).into(holder.binding.imgAvatar)
        //Neu ng dung post uoc mo thi co quyen sua va xoa uoc mo
        if(appSharedPreferences.getIdUser("idUser").toString() == wish.owner._id){
            holder.binding.imgUpdate.visibility = View.VISIBLE
            holder.binding.imgDelete.visibility = View.VISIBLE
        }

        //Bat su kien
        holder.binding.imgDelete.setOnClickListener {
            iClickItemWish.onClickRemove(wish._id)
        }
        holder.binding.imgUpdate.setOnClickListener {
            iClickItemWish.onClickUpdate(wish._id, wish.name, wish.content)
        }

    }


    interface IClickItemWish{
        fun onClickUpdate(iWish: String, fullName: String, content: String)
        fun onClickRemove(iWish: String)
    }
}

