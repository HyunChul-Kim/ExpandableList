package com.chul.expandablelist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.chul.expandablelist.listener.OnChildClickListener
import com.chul.expandablelist.listener.OnGroupClickListener
import com.chul.expandablelist.model.ExpandableItem
import com.chul.expandablelist.model.Node
import com.chul.expandablelist.model.Payload
import com.chul.expandablelist.viewholder.ChildViewHolder
import com.chul.expandablelist.viewholder.GroupViewHolder

abstract class ExpandableAdapter<T, GVH: GroupViewHolder<T>, CVH: ChildViewHolder<T>>(
    private val isSingleExpandedMode: Boolean = true
): Adapter<ViewHolder>(),
    OnGroupClickListener,
    OnChildClickListener
{
    private var rootNode: Node<T>? = null
    private var displayData: MutableList<Node<T>> = mutableListOf()
    private var previouslyCheckedItem: Node<T>? = null

    fun submitList(list: List<ExpandableItem<T>>) {
        rootNode = buildTree(list)
        displayData.clear()
        rootNode?.let {
            displayData.addAll(it.children)
        }
        notifyDataSetChanged()
    }

    private fun buildTree(
        list: List<ExpandableItem<T>>,
        parent: Node<T>? = null,
    ): Node<T> {
        val root = parent ?: Node<T>(data = null, depth = 0)
        val depth = root.depth + 1
        list.forEach { group ->
            val node = Node(
                data = group.item,
                parent = root,
                depth = depth
            )
            root.addChild(node)
            if(group.children.isNotEmpty()) {
                buildTree(group.children, node)
            }
        }
        return root
    }

    fun getData(position: Int): T? {
        return displayData.getOrNull(position)?.data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType) {
            VIEW_TYPE_GROUP -> onCreateGroupViewHolder(parent).apply {
                setOnGroupClickListener(this@ExpandableAdapter)
                //setOnClickListener(this@ExpandableAdapter)
            }
            VIEW_TYPE_CHILD -> onCreateChildViewHolder(parent).apply {
                setOnChildClickListener(this@ExpandableAdapter)
                //setOnClickListener(this@ExpandableAdapter)
            }
            else -> throw IllegalArgumentException("not valid view type")
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(holder) {
            is GroupViewHolder<*> -> {
                val node = displayData[position]
                (holder as GVH).bind(node.data, node.isSelected, node.isExpanded, node.depth, node.children.isNotEmpty())
            }
            is ChildViewHolder<*> -> {
                val node = displayData[position]
                (holder as CVH).bind(node.data, node.isSelected, node.depth)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if(payloads.isEmpty()) return super.onBindViewHolder(holder, position, payloads)
        payloads.forEach { payload ->
            if(payload is Payload) {
                when(holder) {
                    is GroupViewHolder<*> -> {
                        val node = displayData[position]
                        (holder as GVH).update(
                            node.data,
                            node.depth,
                            node.children.isNotEmpty(),
                            payload
                        )
                    }

                    is ChildViewHolder<*> -> {
                        val node = displayData[position]
                        (holder as CVH).update(node.data, node.depth, payload)
                    }
                }
            } else {
                super.onBindViewHolder(holder, position, payloads)
            }
        }
    }

    override fun getItemCount(): Int = displayData.size

    override fun onGroupClicked(position: Int) {
        val node = displayData[position]
        if(node.children.isNotEmpty()) {
            if(node.isExpanded) {
                collapseWithLowDepth(node)
            } else {
                expand(node)
            }
        } else if(isTopLevelNode(node) && !node.isSelected) {
            // the top-level group does not have children
            if(isSingleExpandedMode) {
                collapseOtherSameDepth(node)
            }
            check(node)
        }
    }

    private fun expand(node: Node<T>) {
        if(isSingleExpandedMode) {
            collapseOtherSameDepth(node)
        }
        val displayPosition = displayData.indexOf(node)
        if(displayPosition == -1) return
        val insertPosition = displayPosition + 1
        node.isExpanded = true
        displayData.addAll(insertPosition, node.children)
        notifyItemRangeInserted(insertPosition, node.children.size)
        notifyItemChanged(displayPosition, Payload.ChangedExpand(true))
    }

    private fun collapse(node: Node<T>) {
        val displayPosition = displayData.indexOf(node)
        if(displayPosition == -1) return
        val children = node.children
        val childrenSize = children.size
        if(childrenSize == 0) return
        node.isExpanded = false
        displayData.removeAll(children)
        notifyItemRangeRemoved(displayPosition + 1, childrenSize)
        notifyItemChanged(displayPosition, Payload.ChangedExpand(false))
    }

    private fun collapseOtherSameDepth(node: Node<T>) {
        node.parent?.children?.forEach { child ->
            if(child.isExpanded) {
                collapseWithLowDepth(child)
            }
        }
    }

    private fun collapseWithLowDepth(node: Node<T>) {
        if(!isGroupNode(node) || !node.isExpanded) return
        node.children.forEach { child ->
            collapseWithLowDepth(child)
        }
        collapse(node)
    }

    override fun onChildClicked(position: Int) {
        val node = displayData[position]
        if(previouslyCheckedItem == node) {
            return
        }
        previouslyCheckedItem?.let {
            it.isSelected = !it.isSelected
            val previousPosition = displayData.indexOf(it)
            if(previousPosition != -1) {
                notifyItemChanged(previousPosition, Payload.ChangedSelect(it.isSelected))
            }
        }
        node.isSelected = !node.isSelected
        notifyItemChanged(position, Payload.ChangedSelect(node.isSelected))
        previouslyCheckedItem = node
    }

    private fun check(node: Node<T>) {
        if(previouslyCheckedItem == node) {
            return
        }
        previouslyCheckedItem?.let {
            it.isSelected = false
            val previousPosition = displayData.indexOf(it)
            if(previousPosition != -1) {
                notifyItemChanged(previousPosition, Payload.ChangedSelect(it.isSelected))
            }
        }
        val displayPosition = displayData.indexOf(node)
        if(displayPosition == -1) return
        node.isSelected = true
        notifyItemChanged(displayPosition, Payload.ChangedSelect(node.isSelected))
        previouslyCheckedItem = node
    }

    override fun getItemViewType(position: Int): Int {
        val node = displayData[position]
        return if(node.parent == rootNode || node.children.isNotEmpty()) {
            VIEW_TYPE_GROUP
        } else {
            VIEW_TYPE_CHILD
        }
    }

    fun selectChild(childItem: T) {
        val child = rootNode?.findChild(childItem) ?: return
        if(child.isSelected) return
        expandParent(child)
        check(child)
    }

    private fun expandParent(node: Node<T>?) {
        if(node == null || node == rootNode) return
        expandParent(node.parent)
        if(!node.isExpanded) {
            expand(node)
        }
    }

    private fun collapseParent(node: Node<T>) {
        if(isGroupNode(node) && node.isExpanded) {
            collapse(node)
        }
        var parent = node.parent
        while(parent != null && parent != rootNode) {
            collapse(parent)
            parent = parent.parent
        }
    }

    private fun isTopLevelNode(node: Node<T>): Boolean {
        return node.parent == rootNode
    }

    private fun isGroupNode(node: Node<T>): Boolean {
        return isTopLevelNode(node) || node.children.isNotEmpty()
    }

    abstract fun onCreateGroupViewHolder(parent: ViewGroup): GVH
    abstract fun onCreateChildViewHolder(parent: ViewGroup): CVH

    companion object {
        const val VIEW_TYPE_GROUP = 1
        const val VIEW_TYPE_CHILD = 2
    }
}