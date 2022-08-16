package com.polymorfuz.kwadiary.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.database.*
import com.polymorfuz.kwadiary.R
import com.polymorfuz.kwadiary.adapters.DivContactAdapter
import com.polymorfuz.kwadiary.adapters.SubDivListAdapter
import com.polymorfuz.kwadiary.base.BaseActivity
import com.polymorfuz.kwadiary.beans.ContactModel
import com.polymorfuz.kwadiary.beans.DivNodeModel
import com.polymorfuz.kwadiary.databinding.ActivityDivisionContactBinding
import com.polymorfuz.kwadiary.helpers.showShortToast
import com.polymorfuz.kwadiary.interfaces.BaseRecyclerItemClickListener
import com.polymorfuz.kwadiary.utils.Utils
import com.polymorfuz.kwadiary.viewmodels.DivisionContactViewmodel

class DivisionContactActivity : BaseActivity() {

    private lateinit var divisionContactViewmodel: DivisionContactViewmodel
    private lateinit var binding: ActivityDivisionContactBinding
    private var subDivListAdapter: SubDivListAdapter? = null
    private var divContactAdapter: DivContactAdapter? = null
    var extractList = arrayListOf<String>()
    var divisionContactList = arrayListOf<ContactModel>()
    var divName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        divisionContactViewmodel = ViewModelProviders.of(this@DivisionContactActivity)
            .get(DivisionContactViewmodel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_division_contact)
        binding.apply {
            viewModel = divisionContactViewmodel
        }
        val intent = intent
        divName = intent.getStringExtra("divName").toString()
        initViews()
        initRecyclers()
        initDataLoaders()
        initObservers()
    }

    private fun initDataLoaders() {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val query: Query =
            database.reference.child("nodes").orderByChild("division").equalTo(divName)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    postSnapshot.getValue(DivNodeModel::class.java)
                        ?.let { divisionContactViewmodel.nodeList.add(it) }
                }
                filterSubDivs()
                setDivisionData()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun filterSubDivs() {
        for (i in 0 until divisionContactViewmodel.nodeList.size) {
            if (divisionContactViewmodel.nodeList[i].type.equals("subdivision")) {
                extractList.add(divisionContactViewmodel.nodeList[i].subdivision.toString())
            }
        }
        setSubDivisionRecycler()
    }

    private fun setDivisionData() {
        for (i in 0 until divisionContactViewmodel.nodeList.size) {
            if (divisionContactViewmodel.nodeList[i].type.equals("division")) {
                divisionContactList =
                    divisionContactViewmodel.nodeList[i].contact as ArrayList<ContactModel>
            }
        }
        setDivisionContactRecycler()
    }

    private fun initRecyclers() {
        subDivListAdapter = SubDivListAdapter(object : BaseRecyclerItemClickListener {
            override fun onListItemClicked(item: Any) {
                val intent = Intent(this@DivisionContactActivity, SubDivContactActivity::class.java)
                intent.putExtra("subdivName", item.toString());
                startActivity(intent)
            }

        })
        divContactAdapter = DivContactAdapter(object : BaseRecyclerItemClickListener {
            override fun onListItemClicked(item: Any) {
                val u = Uri.parse("tel:$item")
                val i = Intent(Intent.ACTION_DIAL, u)

                try {
                    startActivity(i)
                } catch (s: SecurityException) {
                    showShortToast("Something went wrong..")
                }
            }

        })
    }

    private fun setSubDivisionRecycler() {
        subDivListAdapter?.setItems(extractList)
        binding.recyclerSubdivisions.adapter = subDivListAdapter
    }

    private fun setDivisionContactRecycler() {
        divContactAdapter?.setItems(divisionContactList)
        binding.recyclerDivisionContact.adapter = divContactAdapter
    }

    private fun initViews() {
        binding.txtDivSubdiv.text = Utils.capitalizeWords(divName)
    }

    private fun initObservers() {
        divisionContactViewmodel.apply {
            event.observe(this@DivisionContactActivity, {
                when (it) {
//                    SubDivisionViewModel.SubDivisionEvent.SUCCESS->
                }
            })
            state.observe(this@DivisionContactActivity, { binding.state = it })
        }
    }
}