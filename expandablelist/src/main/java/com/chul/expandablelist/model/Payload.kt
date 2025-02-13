package com.chul.expandablelist.model

sealed interface Payload {
    data class ChangedExpand(val isExpanded: Boolean): Payload
    data class ChangedSelect(val isSelected: Boolean): Payload
}