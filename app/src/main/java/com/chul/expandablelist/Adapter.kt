package com.chul.expandablelist

import android.view.LayoutInflater
import android.view.ViewGroup
import com.chul.expandablelist.databinding.ViewholderSampleChildItemBinding
import com.chul.expandablelist.databinding.ViewholderSampleGroupItemBinding
import com.chul.expandablelist.viewholder.SampleChildViewHolder
import com.chul.expandablelist.viewholder.SampleGroupViewHolder

class Adapter(
    private val depthPadding: Int
): ExpandableAdapter<String, SampleGroupViewHolder, SampleChildViewHolder>() {

    override fun onCreateGroupViewHolder(parent: ViewGroup): SampleGroupViewHolder {
        return SampleGroupViewHolder(
            ViewholderSampleGroupItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            depthPadding
        )
    }

    override fun onCreateChildViewHolder(parent: ViewGroup): SampleChildViewHolder {
        return SampleChildViewHolder(
            ViewholderSampleChildItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            depthPadding
        )
    }
}