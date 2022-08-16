package com.polymorfuz.kwadiary.activities

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.database.*
import com.polymorfuz.kwadiary.R
import com.polymorfuz.kwadiary.adapters.DistrictsAdapter
import com.polymorfuz.kwadiary.base.BaseActivity
import com.polymorfuz.kwadiary.databinding.ActivityMainBinding
import com.polymorfuz.kwadiary.interfaces.BaseRecyclerItemClickListener
import com.polymorfuz.kwadiary.viewmodels.MainActivityViewModel

class MainActivity : BaseActivity() {
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding
    private var districtsAdapter: DistrictsAdapter? = null
    var distlist = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityViewModel =
            ViewModelProviders.of(this@MainActivity).get(MainActivityViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            viewModel = mainActivityViewModel
        }
        initViews()
        initObservers()
        initRecyclers()
        initDataLoaders()
    }

    private fun initDataLoaders() {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val databaseRef: DatabaseReference = database.reference.child("Districts").child("distlist")

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    distlist.add(postSnapshot.value.toString())
                }
                setDistrictRecycler()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun initRecyclers() {
        districtsAdapter = DistrictsAdapter(object : BaseRecyclerItemClickListener {
            override fun onListItemClicked(item: Any) {
                val intent = Intent(this@MainActivity, DivisionListActivity::class.java)
                intent.putExtra("distName", item.toString());
                startActivity(intent)
            }
        })
    }

    private fun setDistrictRecycler() {
        districtsAdapter?.setItems(distlist)
        binding.recyclerDistrictsMain.adapter = districtsAdapter
    }

    private fun initObservers() {
        mainActivityViewModel.apply {
            event.observe(this@MainActivity, {
                when (it) {
                    MainActivityViewModel.MainActivityEvents.SUCCESS -> showSuccess()
                }
            })
            state.observe(this@MainActivity, { binding.state = it })
        }
    }

    private fun showSuccess() {

    }

    private fun initViews() {
        binding.floatbtnSearchMain.setOnClickListener {
            val intent = Intent(this@MainActivity, SearchActivity::class.java)
            startActivity(intent)
        }
    }

}