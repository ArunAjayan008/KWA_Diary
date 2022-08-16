package com.polymorfuz.kwadiary.adapters

import android.view.View
import com.polymorfuz.kwadiary.R
import com.polymorfuz.kwadiary.base.BaseViewAdapter
import com.polymorfuz.kwadiary.base.BaseViewHolder
import com.polymorfuz.kwadiary.beans.ContactModel
import com.polymorfuz.kwadiary.databinding.AdapterDivContactBinding
import com.polymorfuz.kwadiary.helpers.bindings
import com.polymorfuz.kwadiary.interfaces.BaseRecyclerItemClickListener

class DivContactAdapter(val listener: BaseRecyclerItemClickListener) : BaseViewAdapter() {

    override fun layout(type: String): Int = R.layout.adapter_div_contact

    override fun viewHolder(layout: Int, view: View): BaseViewHolder = DivViewHolder(view)

    inner class DivViewHolder(view: View) : BaseViewHolder(view) {
        private val binding: AdapterDivContactBinding by bindings(view)
        override fun bindData(district: Any) {
            if (district is ContactModel) {
                binding.apply {
                    item = district
                    root.setOnClickListener {
                        district.mob?.let { it1 -> listener.onListItemClicked(it1) }
                    }
                }
            }
        }
    }
}