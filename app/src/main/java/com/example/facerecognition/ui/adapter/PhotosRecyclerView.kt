package com.example.facerecognition.ui.adapter

import android.content.Context
import android.graphics.Bitmap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.ImageView

import androidx.recyclerview.widget.RecyclerView
import com.example.facerecognition.R

import java.util.*


class PhotosRecyclerView(
    var arrayList: ArrayList<Bitmap>,
    val context: Context,
    val listener: OnItemClick
) :
    RecyclerView.Adapter<PhotosRecyclerView.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val imPhoto: ImageView = view.findViewById(R.id.imPhoto)


        init {

            //pass the listener
            itemView.setOnClickListener(this)

        }


        //handle the songs selected
        override fun onClick(v: View?) {
            val position: Int = absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }


    interface OnItemClick {
        fun onItemClick(i: Int)

    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.row_list_photo, viewGroup, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = this.arrayList[position]
        viewHolder.imPhoto.setImageBitmap(item)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


}