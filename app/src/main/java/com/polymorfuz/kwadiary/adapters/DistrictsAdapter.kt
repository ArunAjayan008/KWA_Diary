package com.polymorfuz.kwadiary.adapters

import android.view.View
import android.widget.Filter
import com.polymorfuz.kwadiary.R
import com.polymorfuz.kwadiary.base.BaseViewAdapter
import com.polymorfuz.kwadiary.base.BaseViewHolder
import com.polymorfuz.kwadiary.beans.DistrictsModel
import com.polymorfuz.kwadiary.databinding.AdapterDistrictsBinding
import com.polymorfuz.kwadiary.helpers.bindings
import com.polymorfuz.kwadiary.interfaces.BaseRecyclerItemClickListener

class DistrictsAdapter(val listener: BaseRecyclerItemClickListener) : BaseViewAdapter() {
    override fun layout(type: String): Int = R.layout.adapter_districts

    override fun viewHolder(layout: Int, view: View): BaseViewHolder = DistrictViewHolder(view)

    inner class DistrictViewHolder(view: View) : BaseViewHolder(view) {
        private val binding: AdapterDistrictsBinding by bindings(view)
        override fun bindData(district: Any) {
            if (district is String) {
                binding.apply {
                    item = district
                    root.setOnClickListener {
                        listener.onListItemClicked(district)
                    }
                }
            }
        }
    }

}