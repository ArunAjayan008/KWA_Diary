package com.polymorfuz.kwadiary.adapters

import android.view.View
import com.polymorfuz.kwadiary.R
import com.polymorfuz.kwadiary.base.BaseViewAdapter
import com.polymorfuz.kwadiary.base.BaseViewHolder
import com.polymorfuz.kwadiary.databinding.AdapterDistrictsBinding
import com.polymorfuz.kwadiary.databinding.AdapterDivisionsBindingImpl
import com.polymorfuz.kwadiary.helpers.bindings
import com.polymorfuz.kwadiary.interfaces.BaseRecyclerItemClickListener

class DivisionListAdapter (val listener: BaseRecyclerItemClickListener) : BaseViewAdapter() {
    override fun layout(type: String): Int = R.layout.adapter_divisions

    override fun viewHolder(layout: Int, view: View): BaseViewHolder = DivisionViewHolder(view)

    inner class DivisionViewHolder(view: View) : BaseViewHolder(view) {
        private val binding: AdapterDivisionsBindingImpl by bindings(view)
        override fun bindData(division: Any) {
            if (division is String) {
                binding.apply {
                    item = division
                    root.setOnClickListener {
                        listener.onListItemClicked(division)
                    }
                }
            }
        }
    }
}