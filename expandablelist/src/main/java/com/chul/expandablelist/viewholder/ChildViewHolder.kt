package com.chul.expandablelist.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.chul.expandablelist.listener.OnChildClickListener
import com.chul.expandablelist.model.Payload

abstract class ChildViewHolder<T>(
    itemView: View
): ViewHolder(itemView) {

    private var onChildClickListener: OnChildClickListener? = null

    init {
        itemView.setOnClickListener {
            onChildClickListener?.onChildClicked(bindingAdapterPosition)
            onClicked(bindingAdapterPosition)
        }
    }

    abstract fun bind(item: T?, isSelected: Boolean, depth: Int)

    abstract fun update(item: T?, depth: Int, payload: Payload)

    abstract fun onClicked(position: Int)

    fun setOnChildClickListener(listener: OnChildClickListener) {
        this.onChildClickListener = listener
    }
}