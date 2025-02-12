package com.chul.expandablelist.viewholder

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.chul.expandablelist.R
import com.chul.expandablelist.model.ChildItem

class SampleChildViewHolder(
    itemView: View,
    private val padding: Int
): ChildViewHolder<String>(itemView) {

    private val title = itemView.findViewById<AppCompatTextView>(R.id.child_title)

    override fun bind(item: ChildItem<String>) {
        title.text = item.title
        val depthPaddingStart = item.depth * padding
        title.setPadding(depthPaddingStart, 0, 0, 0)
    }
}