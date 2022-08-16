package com.polymorfuz.kwadiary.adapters

import android.view.View
import com.polymorfuz.kwadiary.R
import com.polymorfuz.kwadiary.base.BaseViewAdapter
import com.polymorfuz.kwadiary.base.BaseViewHolder
import com.polymorfuz.kwadiary.databinding.AdapterSubdivisionsBinding
import com.polymorfuz.kwadiary.helpers.bindings
import com.polymorfuz.kwadiary.interfaces.BaseRecyclerItemClickListener

class SubDivListAdapter(val listener: BaseRecyclerItemClickListener) : BaseViewAdapter() {

    override fun layout(type: String): Int = R.layout.adapter_subdivisions

    override fun viewHolder(layout: Int, view: View): BaseViewHolder = SubDivisionViewHolder(view)

    inner class SubDivisionViewHolder(view: View) : BaseViewHolder(view) {
        private val binding: AdapterSubdivisionsBinding by bindings(view)
        override fun bindData(subdivision: Any) {
            if (subdivision is String) {
                binding.apply {
                    item = subdivision
                    root.setOnClickListener {
                        listener.onListItemClicked(subdivision)
                    }
                }
            }
        }
    }
}