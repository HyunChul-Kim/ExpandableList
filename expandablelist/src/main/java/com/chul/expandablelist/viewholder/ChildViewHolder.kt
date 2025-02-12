package com.chul.expandablelist.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.chul.expandablelist.listener.OnChildClickListener
import com.chul.expandablelist.model.ChildItem

abstract class ChildViewHolder<T>(
    itemView: View
): ViewHolder(itemView) {

    private var onChildClickListener: OnChildClickListener? = null

    init {
        itemView.setOnClickListener {
            onChildClickListener?.onChildClicked(bindingAdapterPosition)
        }
    }

    abstract fun bind(item: ChildItem<T>)

    fun setOnChildClickListener(listener: OnChildClickListener) {
        this.onChildClickListener = listener
    }
}