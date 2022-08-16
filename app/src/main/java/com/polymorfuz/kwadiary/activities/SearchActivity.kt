package com.polymorfuz.kwadiary.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.database.*
import com.polymorfuz.kwadiary.R
import com.polymorfuz.kwadiary.adapters.DistrictsAdapter
import com.polymorfuz.kwadiary.databinding.ActivitySearchBinding
import com.polymorfuz.kwadiary.interfaces.BaseRecyclerItemClickListener
import com.polymorfuz.kwadiary.viewmodels.SearchViewmodel

class SearchActivity : AppCompatActivity() {
    private lateinit var searchViewmodel: SearchViewmodel
    private lateinit var binding: ActivitySearchBinding
    private var districtsAdapter: DistrictsAdapter? = null
    var distlist = arrayListOf<String>()
    var filteredlist = arrayListOf<String>()
    var searchTxt: String = " "
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchViewmodel =
            ViewModelProviders.of(this@SearchActivity).get(SearchViewmodel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        binding.apply {
            viewModel = searchViewmodel
        }
        initViews()
        initObservers()
        initRecyclers()
        initDataLoaders()
    }

    private fun initDataLoaders() {
        val query: Query = database.reference.child("nodes").orderByChild("name")
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    distlist.add(postSnapshot.child("name").value.toString())
                }
                filteredlist.addAll(distlist)
                setDistrictRecycler()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun initRecyclers() {
        districtsAdapter = DistrictsAdapter(object : BaseRecyclerItemClickListener {
            override fun onListItemClicked(item: Any) {
                val intent = Intent(this@SearchActivity, SecContactActivity::class.java)
                intent.putExtra("lastnode", item.toString());
                startActivity(intent)
            }
        })
    }

    private fun setDistrictRecycler() {
        districtsAdapter?.setItems(filteredlist)
        binding.recyclerNodesSearch.adapter = districtsAdapter
    }

    private fun searchNodes() {
//        if (searchTxt.isNotEmpty()) {
//            searchTxt = searchTxt[0].uppercaseChar().toString() + searchTxt.substring(1)
//        }
        filteredlist.clear()
        for (str in distlist) {
            if (str.contains(searchTxt)) {
                filteredlist.add(str)
            }
        }
        setDistrictRecycler()
    }

    private fun initObservers() {
        searchViewmodel.apply {
            event.observe(this@SearchActivity, {
                when (it) {

                }
            })
            state.observe(this@SearchActivity, { binding.state = it })
        }
    }

    private fun initViews() {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding.searchviewNodes.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        binding.searchviewNodes.maxWidth = Int.MAX_VALUE
        binding.searchviewNodes.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchTxt = query
                searchNodes()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchTxt = newText
                searchNodes()
                return false
            }
        })
    }

}