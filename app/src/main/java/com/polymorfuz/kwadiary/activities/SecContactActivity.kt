package com.polymorfuz.kwadiary.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.database.*
import com.polymorfuz.kwadiary.R
import com.polymorfuz.kwadiary.adapters.DivContactAdapter
import com.polymorfuz.kwadiary.beans.ContactModel
import com.polymorfuz.kwadiary.beans.DivNodeModel
import com.polymorfuz.kwadiary.databinding.ActivitySecContactBinding
import com.polymorfuz.kwadiary.helpers.showShortToast
import com.polymorfuz.kwadiary.interfaces.BaseRecyclerItemClickListener
import com.polymorfuz.kwadiary.utils.Utils
import com.polymorfuz.kwadiary.viewmodels.SecConViewmodel

class SecContactActivity : AppCompatActivity() {
    private lateinit var secConViewmodel: SecConViewmodel
    private lateinit var binding: ActivitySecContactBinding
    private var divContactAdapter: DivContactAdapter? = null
    var secContactList = arrayListOf<ContactModel>()
    var nodeList = arrayListOf<DivNodeModel>()
    var secName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        secConViewmodel =
            ViewModelProviders.of(this@SecContactActivity).get(SecConViewmodel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sec_contact)
        binding.apply {
            viewModel = secConViewmodel
        }
        val intent = intent
        secName = intent.getStringExtra("lastnode").toString()
        initViews()
        initRecyclers()
        initDataLoaders()
        initObservers()
    }
    private fun initDataLoaders() {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val query: Query =
            database.reference.child("nodes").orderByChild("name").equalTo(secName)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    postSnapshot.getValue(DivNodeModel::class.java)
                        ?.let { nodeList.add(it) }
                }
                setContactData()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun setContactData() {
//        for (i in 0 until nodeList.size) {
//            if (nodeList[i].type.equals("section")) {
                secContactList =
                    nodeList[0].contact as ArrayList<ContactModel>
//            }
//        }
        setDivisionContactRecycler()
    }

    private fun initRecyclers() {
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

    private fun setDivisionContactRecycler() {
        divContactAdapter?.setItems(secContactList)
        binding.recyclerSecContact.adapter = divContactAdapter
    }

    private fun initViews() {
        binding.txtSec.text = Utils.capitalizeWords(secName)
    }

    private fun initObservers() {
        secConViewmodel.apply {
            event.observe(this@SecContactActivity, {
                when (it) {
//                    SubDivisionViewModel.SubDivisionEvent.SUCCESS->
                }
            })
            state.observe(this@SecContactActivity, { binding.state = it })
        }
    }
}
