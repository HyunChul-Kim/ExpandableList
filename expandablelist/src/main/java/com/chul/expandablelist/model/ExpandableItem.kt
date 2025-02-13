package com.chul.expandablelist.model

data class ExpandableItem<T>(
    val title: String,
    val item: T,
    val children: List<ExpandableItem<T>> = emptyList()
)