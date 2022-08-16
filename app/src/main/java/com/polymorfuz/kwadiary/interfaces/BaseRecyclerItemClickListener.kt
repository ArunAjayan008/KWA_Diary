package com.polymorfuz.kwadiary.interfaces


interface BaseRecyclerItemClickListener {
    fun onListItemClicked(item: Any)
}

interface RecycleItemTwoObjectPassListener {
    fun onListItemClicked(itemId: Any, itemName: Any)
}