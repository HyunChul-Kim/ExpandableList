package com.chul.expandablelist.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.chul.expandablelist.listener.OnGroupClickListener
import com.chul.expandablelist.model.ExpandableItem

abstract class GroupViewHolder<T>(
    itemView: View
): ViewHolder(itemView) {

    private var onGroupClickListener: OnGroupClickListener? = null

    init {
        itemView.setOnClickListener {
            onGroupClickListener?.onGroupClicked(bindingAdapterPosition)
        }
    }

    abstract fun bind(item: ExpandableItem<T>)

    fun setOnGroupClickListener(listener: OnGroupClickListener) {
        onGroupClickListener = listener
    }
}