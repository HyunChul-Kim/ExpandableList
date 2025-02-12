package com.chul.expandablelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chul.expandablelist.model.ChildItem
import com.chul.expandablelist.model.ExpandableItem
import com.chul.expandablelist.model.Payload
import com.chul.expandablelist.viewholder.SampleChildViewHolder
import com.chul.expandablelist.viewholder.SampleGroupViewHolder

class Adapter(
    private val depthPadding: Int
): ExpandableAdapter<String, SampleGroupViewHolder, SampleChildViewHolder>() {

    override fun onCreateGroupViewHolder(parent: ViewGroup): SampleGroupViewHolder {
        return SampleGroupViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_sample_group_item, parent, false),
            depthPadding
        )
    }

    override fun onCreateChildViewHolder(parent: ViewGroup): SampleChildViewHolder {
        return return SampleChildViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_sample_child_item, parent, false),
            depthPadding
        )
    }

    override fun onBindChildViewHolder(
        holder: RecyclerView.ViewHolder,
        item: ChildItem<String>,
        payload: Payload
    ) {

    }

    override fun onBindChildViewHolder(holder: RecyclerView.ViewHolder, item: ChildItem<String>) {

    }

    override fun onBindGroupViewHolder(
        holder: RecyclerView.ViewHolder,
        item: ExpandableItem<String>,
        payload: Payload
    ) {

    }

    override fun onBindGroupViewHolder(
        holder: RecyclerView.ViewHolder,
        item: ExpandableItem<String>
    ) {

    }
}