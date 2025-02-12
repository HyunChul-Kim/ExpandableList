package com.chul.expandablelist.viewholder

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.chul.expandablelist.R
import com.chul.expandablelist.model.ExpandableItem

class SampleGroupViewHolder(
    itemView: View,
    private val padding: Int
): GroupViewHolder<String>(itemView) {

    private val title = itemView.findViewById<AppCompatTextView>(R.id.group_title)

    override fun bind(item: ExpandableItem<String>) {
        title.text = item.title
        val depthPaddingStart = item.depth * padding
        title.setPadding(depthPaddingStart, 0, 0, 0)
    }
}