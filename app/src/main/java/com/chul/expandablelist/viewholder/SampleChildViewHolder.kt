package com.chul.expandablelist.viewholder

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.updatePadding
import com.chul.expandablelist.R
import com.chul.expandablelist.databinding.ViewholderSampleChildItemBinding
import com.chul.expandablelist.model.Payload

class SampleChildViewHolder(
    private val binding: ViewholderSampleChildItemBinding,
    private val padding: Int
): ChildViewHolder<String>(binding.root) {

    override fun bind(item: String?, isSelected: Boolean, depth: Int) {
        binding.childTitle.text = "$item selected = $isSelected"
        val depthPaddingStart = depth * padding
        binding.childTitle.updatePadding(left = depthPaddingStart)
    }

    override fun update(item: String?, depth: Int, payload: Payload) {
        if(payload is Payload.ChangedSelect) {
            binding.childTitle.text = "$item selected = ${payload.isSelected}"
        }
    }

    override fun onClicked(position: Int) {

    }
}