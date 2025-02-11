package com.chul.expandablelist.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.chul.expandablelist.listener.OnChildClickListener

open class ChildViewHolder(
    itemView: View
): ViewHolder(itemView) {

    private var onChildClickListener: OnChildClickListener? = null

    init {
        itemView.setOnClickListener {
            onChildClickListener?.onChildClicked(bindingAdapterPosition)
        }
    }

    fun setOnChildClickListener(listener: OnChildClickListener) {
        this.onChildClickListener = listener
    }
}