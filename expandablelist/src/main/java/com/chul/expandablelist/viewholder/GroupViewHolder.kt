package com.chul.expandablelist.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.chul.expandablelist.listener.OnGroupClickListener
import com.chul.expandablelist.model.Payload

abstract class GroupViewHolder<T>(
    itemView: View
): ViewHolder(itemView) {

    private var onGroupClickListener: OnGroupClickListener? = null

    init {
        itemView.setOnClickListener {
            onGroupClickListener?.onGroupClicked(bindingAdapterPosition)
            onClicked(bindingAdapterPosition)
        }
    }

    abstract fun bind(item: T?, isSelected: Boolean, isExpanded: Boolean, depth: Int, hasChild: Boolean)

    abstract fun update(item: T?, depth: Int, hasChild: Boolean, payload: Payload)

    abstract fun onClicked(position: Int)

    fun setOnGroupClickListener(listener: OnGroupClickListener) {
        onGroupClickListener = listener
    }
}