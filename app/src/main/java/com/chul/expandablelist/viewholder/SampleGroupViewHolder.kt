package com.chul.expandablelist.viewholder

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.updatePadding
import com.chul.expandablelist.R
import com.chul.expandablelist.databinding.ViewholderSampleGroupItemBinding
import com.chul.expandablelist.model.Payload

class SampleGroupViewHolder(
    private val binding: ViewholderSampleGroupItemBinding,
    private val padding: Int
): GroupViewHolder<String>(binding.root) {

    override fun bind(
        item: String?,
        isSelected: Boolean,
        isExpanded: Boolean,
        depth: Int,
        hasChild: Boolean
    ) {
        binding.groupTitle.text = "$item, selected = $isSelected"
        val depthPaddingStart = depth * padding
        binding.groupTitle.updatePadding(left = depthPaddingStart)
    }

    override fun update(item: String?, depth: Int, hasChild: Boolean, payload: Payload) {
        if(payload is Payload.ChangedSelect) {
            binding.groupTitle.text = "$item, selected = ${payload.isSelected}"
        }
    }

    override fun onClicked(position: Int) {

    }
}