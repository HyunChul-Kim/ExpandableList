package com.chul.expandablelist.model

data class Node<T>(
    val data: T?,
    val parent: Node<T>? = null,
    val children: MutableList<Node<T>> = mutableListOf(),
    var isExpanded: Boolean = false,
    var isSelected: Boolean = false,
    val depth: Int
) {

    fun addChild(child: Node<T>) {
        children.add(child)
    }

    fun removeChild(child: Node<T>) {
        children.remove(child)
    }

    fun findChild(childData: T): Node<T>? {
        return findChild(this, childData)
    }

    private fun findChild(node: Node<T>, childData: T): Node<T>? {
        node.children.forEach { child ->
            val found = findChild(child, childData)
            if(found != null) return found
            if(child.data == childData) return child
        }
        return null
    }

    override fun toString(): String {
        return "Node(data=$data)"
    }
}