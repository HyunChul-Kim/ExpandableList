package com.chul.expandablelist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.chul.expandablelist.listener.OnChildClickListener
import com.chul.expandablelist.listener.OnGroupClickListener
import com.chul.expandablelist.model.ChildItem
import com.chul.expandablelist.model.ExpandableItem
import com.chul.expandablelist.model.ExpandableItemType
import com.chul.expandablelist.model.Payload
import com.chul.expandablelist.viewholder.ChildViewHolder
import com.chul.expandablelist.viewholder.GroupViewHolder
import java.util.Stack

abstract class ExpandableAdapter<T, GVH: GroupViewHolder<T>, CVH: ChildViewHolder<T>>:
    Adapter<ViewHolder>(),
    OnGroupClickListener,
    OnChildClickListener
{
    private var previouslyExpandedItems: Stack<ExpandableItem<T>> = Stack()
    private var previouslyCheckedItem: ChildItem<T>? = null

    private var data: MutableList<ExpandableItemType<T>> = mutableListOf()

    fun submitList(list: List<ExpandableItemType<T>>) {
        val previousItemCount = data.size
        data.clear()
        data.addAll(list)
        if(previousItemCount != 0) {
            notifyItemRangeRemoved(0, previousItemCount)
        }
        notifyItemRangeInserted(0, data.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType) {
            VIEW_TYPE_GROUP -> onCreateGroupViewHolder(parent).apply {
                setOnGroupClickListener(this@ExpandableAdapter)
            }
            VIEW_TYPE_CHILD -> onCreateChildViewHolder(parent).apply {
                setOnChildClickListener(this@ExpandableAdapter)
            }
            else -> throw IllegalArgumentException("not valid view type")
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(holder) {
            is GroupViewHolder<*> -> {
                val item = (data[position] as ExpandableItemType.Group).item
                (holder as GVH).bind(item)
            }
            is ChildViewHolder<*> -> {
                val item = (data[position] as ExpandableItemType.Child).item
                (holder as CVH).bind(item)
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onGroupClicked(position: Int) {
        val group = (data[position] as? ExpandableItemType.Group) ?: return
        if(group.item.isExpanded) {
            collapseWithLowDepth(group.item)
        } else {
            expand(group.item)
        }
    }

    private fun expand(item: ExpandableItem<T>) {
        collapseWithLowDepth(item)
        val expandPosition = data.indexOf(ExpandableItemType.Group(item))
        val insertPosition = expandPosition + 1
        item.isExpanded = true
        val visibleChildren = getVisibleChildren(item)
        data.addAll(insertPosition, visibleChildren)
        notifyItemRangeInserted(insertPosition, visibleChildren.size)
        notifyItemChanged(expandPosition)
        previouslyExpandedItems.push(item)
    }

    private fun collapse(item: ExpandableItem<T>, position: Int) {
        val insertPosition = position + 1
        val children = item.children
        item.isExpanded = false
        data.removeAll(children)
        notifyItemRangeRemoved(insertPosition, children.size)
        notifyItemChanged(position)
    }

    private fun collapseWithLowDepth(item: ExpandableItem<T>) {
        getSameDepthExpandedItem(item)?.let { sameDepthExpandedItem ->
            while(previouslyExpandedItems.isNotEmpty()) {
                val previousItem = previouslyExpandedItems.pop()
                val previousPosition = data.indexOf(ExpandableItemType.Group(previousItem))
                if(previousPosition != -1) {
                    collapse(previousItem, previousPosition)
                }
                if(previousItem == sameDepthExpandedItem) break
            }
        }
    }

    override fun onChildClicked(position: Int) {
        val child = data[position] as? ExpandableItemType.Child<T> ?: return
        if(previouslyCheckedItem == child.item) {
            return
        }
        previouslyCheckedItem?.let {
            it.isSelected = !it.isSelected
            val previousPosition = data.indexOf(ExpandableItemType.Child(it))
            if(previousPosition != -1) {
                notifyItemChanged(previousPosition)
            }
        }
        child.item.isSelected = !child.item.isSelected
        notifyItemChanged(position)
        previouslyCheckedItem = child.item
    }

    override fun getItemViewType(position: Int): Int {
        return when(data[position]) {
            is ExpandableItemType.Child -> VIEW_TYPE_CHILD
            is ExpandableItemType.Group -> VIEW_TYPE_GROUP
        }
    }

    private fun getAllChildren(item: ExpandableItem<T>): List<ExpandableItemType<T>> {
        val allChildren = mutableListOf<ExpandableItemType<T>>()
        item.children.forEach { child ->
            allChildren.add(child)
            if(child is ExpandableItemType.Group) {
                allChildren.addAll(getAllChildren(child.item))
            }
        }
        return allChildren
    }

    private fun getVisibleChildren(item: ExpandableItem<T>): List<ExpandableItemType<T>> {
        val visibleChildren = mutableListOf<ExpandableItemType<T>>()
        if(item.isExpanded) {
            item.children.forEach { child ->
                visibleChildren.add(child)
                when(child) {
                    is ExpandableItemType.Group -> {
                        child.item.depth = item.depth + 1
                        visibleChildren.addAll(getVisibleChildren(child.item))
                    }
                    is ExpandableItemType.Child -> {
                        child.item.depth = item.depth + 1
                    }
                }
            }
        }
        return visibleChildren
    }

    private fun getParent(item: ExpandableItem<T>): ExpandableItemType<T>? {
        data.forEach { dataItem ->
            if(dataItem is ExpandableItemType.Group) {
                if(dataItem.item.children.contains(ExpandableItemType.Group(item))) {
                    return dataItem
                }
                if(dataItem.item.children.any { it is ExpandableItemType.Child && it.item == item}) {
                    return dataItem
                }
            }
        }
        return null
    }

    private fun getSameDepthExpandedItem(item: ExpandableItem<T>): ExpandableItem<T>? {
        val parent = getParent(item)
        return previouslyExpandedItems.find { getParent(it) == parent }
    }

    abstract fun onCreateGroupViewHolder(parent: ViewGroup): GVH
    abstract fun onCreateChildViewHolder(parent: ViewGroup): CVH
    abstract fun onBindGroupViewHolder(holder: ViewHolder, item: ExpandableItem<T>)
    abstract fun onBindChildViewHolder(holder: ViewHolder, item: ChildItem<T>)
    abstract fun onBindGroupViewHolder(holder: ViewHolder, item: ExpandableItem<T>, payload: Payload)
    abstract fun onBindChildViewHolder(holder: ViewHolder, item: ChildItem<T>, payload: Payload)

    companion object {
        const val VIEW_TYPE_GROUP = 1
        const val VIEW_TYPE_CHILD = 2
    }
}