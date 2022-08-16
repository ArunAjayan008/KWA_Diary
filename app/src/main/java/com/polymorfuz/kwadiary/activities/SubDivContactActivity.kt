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
import com.polymorfuz.kwadiary.databinding.ActivitySubDivContactBinding
import com.polymorfuz.kwadiary.helpers.showShortToast
import com.polymorfuz.kwadiary.interfaces.BaseRecyclerItemClickListener
import com.polymorfuz.kwadiary.utils.Utils
import com.polymorfuz.kwadiary.viewmodels.SubDivContactViewmodel

class SubDivContactActivity : BaseActivity() {
    private lateinit var subDivisionContactViewmodel: SubDivContactViewmodel
    private lateinit var binding: ActivitySubDivContactBinding
    private var subDivListAdapter: SubDivListAdapter? = null
    private var divContactAdapter: DivContactAdapter? = null
    var extractList = arrayListOf<String>()
    var subdivContactList = arrayListOf<ContactModel>()
    var nodeList = arrayListOf<DivNodeModel>()
    var subdivName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subDivisionContactViewmodel = ViewModelProviders.of(this@SubDivContactActivity)
            .get(SubDivContactViewmodel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sub_div_contact)
        binding.apply {
            viewModel = subDivisionContactViewmodel
        }
        val intent = intent
        subdivName = intent.getStringExtra("subdivName").toString()
        initViews()
        initRecyclers()
        initDataLoaders()
        initObservers()
    }

    private fun initDataLoaders() {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val query: Query =
            database.reference.child("nodes").orderByChild("subdivision").equalTo(subdivName)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    postSnapshot.getValue(DivNodeModel::class.java)
                        ?.let { nodeList.add(it) }
                }
                filterSubDivs()
                setDivisionData()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun filterSubDivs() {
        for (i in 0 until nodeList.size) {
            if (nodeList[i].type.equals("section")) {
                extractList.add(nodeList[i].section.toString())
            }
        }
        setSubDivisionRecycler()
    }

    private fun setDivisionData() {
        for (i in 0 until nodeList.size) {
            if (nodeList[i].type.equals("subdivision")) {
                subdivContactList =
                    nodeList[i].contact as ArrayList<ContactModel>
            }
        }
        setDivisionContactRecycler()
    }

    private fun initRecyclers() {
        subDivListAdapter = SubDivListAdapter(object : BaseRecyclerItemClickListener {
            override fun onListItemClicked(item: Any) {
                val intent = Intent(this@SubDivContactActivity, SecContactActivity::class.java)
                intent.putExtra("lastnode", item.toString());
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
        binding.recyclerSections.adapter = subDivListAdapter
    }

    private fun setDivisionContactRecycler() {
        divContactAdapter?.setItems(subdivContactList)
        binding.recyclerSubdivisionContact.adapter = divContactAdapter
    }

    private fun initViews() {
        binding.txtDivSubdiv.text = Utils.capitalizeWords(subdivName)
    }

    private fun initObservers() {
        subDivisionContactViewmodel.apply {
            event.observe(this@SubDivContactActivity, {
                when (it) {
//                    SubDivisionViewModel.SubDivisionEvent.SUCCESS->
                }
            })
            state.observe(this@SubDivContactActivity, { binding.state = it })
        }
    }
}


