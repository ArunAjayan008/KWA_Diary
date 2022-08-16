package com.polymorfuz.kwadiary.activities

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.database.*
import com.polymorfuz.kwadiary.R
import com.polymorfuz.kwadiary.adapters.DivisionListAdapter
import com.polymorfuz.kwadiary.base.BaseActivity
import com.polymorfuz.kwadiary.beans.DivNodeModel
import com.polymorfuz.kwadiary.databinding.ActivityDivisionBinding
import com.polymorfuz.kwadiary.interfaces.BaseRecyclerItemClickListener
import com.polymorfuz.kwadiary.viewmodels.DivisionListViewModel

class DivisionListActivity : BaseActivity() {
    private lateinit var divisionListViewModel: DivisionListViewModel
    private lateinit var binding: ActivityDivisionBinding
    private var divisionAdapter: DivisionListAdapter? = null
    var divlist = arrayListOf<DivNodeModel>()
    var extractList = arrayListOf<String>()
    var distName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        divisionListViewModel =
            ViewModelProviders.of(this@DivisionListActivity).get(DivisionListViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_division)
        binding.apply {
            viewModel = divisionListViewModel
        }
        val intent = intent
        distName = intent.getStringExtra("distName").toString()
        initViews()
        initObservers()
        initRecyclers()
        initDataLoaders()
    }

    private fun initDataLoaders() {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val query: Query =
            database.reference.child("nodes").orderByChild("district").equalTo(distName)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    postSnapshot.getValue(DivNodeModel::class.java)?.let { divlist.add(it) }
                }
                filterDivisions()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    private fun filterDivisions() {
        for (i in 0 until divlist.size) {
            if (divlist[i].type.equals("division")) {
                extractList.add(divlist[i].division.toString())
            }
        }
        setDivisionRecycler()
    }

    private fun initRecyclers() {
        divisionAdapter = DivisionListAdapter(object : BaseRecyclerItemClickListener {
            override fun onListItemClicked(item: Any) {
                val intent = Intent(this@DivisionListActivity, DivisionContactActivity::class.java)
                intent.putExtra("divName", item.toString());
                startActivity(intent)
            }
        })
    }

    private fun setDivisionRecycler() {
        divisionAdapter?.setItems(extractList)
        binding.recyclerDivision.adapter = divisionAdapter
    }

    private fun initObservers() {
        divisionListViewModel.apply {
            event.observe(this@DivisionListActivity, {
                when (it) {
//                    SubDivisionViewModel.SubDivisionEvent.SUCCESS->
                }
            })
            state.observe(this@DivisionListActivity, { binding.state = it })
        }
    }

    private fun initViews() {

    }
}