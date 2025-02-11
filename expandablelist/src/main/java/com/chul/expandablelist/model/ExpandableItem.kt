package com.chul.expandablelist.model

import java.util.UUID

data class ExpandableItem<T>(
    val title: String,
    val item: T,
    val children: List<ExpandableItemType<T>> = emptyList(),
    val depth: Int = 0,
    var isExpanded: Boolean = false,
    val id: String = UUID.randomUUID().toString()
)

data class ChildItem<T>(
    val title: String,
    val item: T,
    var isSelected: Boolean = false,
    val id: String = UUID.randomUUID().toString()
)

sealed interface ExpandableItemType<T> {
    data class Group<T>(val item: ExpandableItem<T>): ExpandableItemType<T>
    data class Child<T>(val item: ChildItem<T>): ExpandableItemType<T>
}

sealed interface Payload {
    data class ChangedGroupExpand(val isExpanded: Boolean): Payload
    data class ChangedChildSelected(val isSelected: Boolean): Payload
}
