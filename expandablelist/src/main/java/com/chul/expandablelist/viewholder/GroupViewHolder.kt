package com.chul.expandablelist.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.chul.expandablelist.listener.OnGroupClickListener

open class GroupViewHolder(
    itemView: View
): ViewHolder(itemView) {

    private var onGroupClickListener: OnGroupClickListener? = null

    init {
        itemView.setOnClickListener {
            onGroupClickListener?.onGroupClicked(bindingAdapterPosition)
        }
    }

    fun setOnGroupClickListener(listener: OnGroupClickListener) {
        onGroupClickListener = listener
    }
}