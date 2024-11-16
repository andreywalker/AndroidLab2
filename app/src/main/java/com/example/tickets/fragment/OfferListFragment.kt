package com.example.tickets.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import com.example.tickets.R
import com.example.tickets.adapter.DogListAdapter
import com.example.tickets.databinding.FragmentOfferListBinding
import com.example.tickets.model.network.ApiClient
import com.example.tickets.model.network.Dog
import com.example.tickets.model.service.FakeService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OfferListFragment : Fragment() {

    companion object {
        fun newInstance() = OfferListFragment()
    }

    private var _binding: FragmentOfferListBinding? = null
    private val binding
        get() = _binding!!

    private val adapter: DogListAdapter by lazy {
        DogListAdapter()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOfferListBinding.inflate(layoutInflater, container, false)

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val call = ApiClient.apiService.getDogs()

        call.enqueue(object : Callback<List<Dog>> {
            override fun onResponse(call: Call<List<Dog>>, response: Response<List<Dog>>) {
                if (response.isSuccessful) {
                    val post = response.body()
                    var list=post

                    Log.d("offersss", post.toString())
                    if (list != null) {
                        FakeService.dogList=list
                        setupUI(list)
                        adapter.saveData(list)
                    }
                    Log.d("lllllllllllllllllllllllllllllllllllllll",FakeService.dogList.toString())

                } else {

                    val toast = Toast.makeText(context, "Error accured", Toast.LENGTH_SHORT)
                    toast.show()
                    Log.e("offersss", response.toString())
                }
            }

            override fun onFailure(call: Call<List<Dog>>, t: Throwable) {
                val toast = Toast.makeText(context, "Failure accured", Toast.LENGTH_SHORT)
                toast.show()
                Log.e("offersss fail", t.toString())
            }
        })

        setupUI(FakeService.dogList)
        adapter.saveData(FakeService.dogList)
    }

    private fun setupUI(list:List<Dog>) {
        with(binding) {
            offerList.adapter = adapter

            sortRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.sort_by_price -> {
                        val llist: List<Dog> = list.sortedBy { it.energy }
                        adapter.saveData(llist)
                    }

                    R.id.sort_by_duration -> {
                        val llist: List<Dog> = list.sortedBy { it.min_life_expectancy }
                        adapter.saveData(llist)
                    }
                }
            }
            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    if (query.isEmpty()){
                        adapter.saveData(FakeService.dogList)
                        setupUI(FakeService.dogList)
                        return false
                    }
                    var lklist=FakeService.dogList.filter { it.name == query }
                    if (lklist.isNotEmpty()) {
                        adapter.saveData(lklist)
                        setupUI(lklist)
                    } else {
                        val toast = Toast.makeText(context, "Nothing found", Toast.LENGTH_SHORT)
                        toast.show()
                    }
                    return false
                }
                override fun onQueryTextChange(newText: String): Boolean {
                    if (newText.isEmpty()){
                        adapter.saveData(FakeService.dogList)
                        setupUI(FakeService.dogList)
                        return false
                    }
                    adapter.saveData(FakeService.dogList.filter { it.name == newText })
                    setupUI(FakeService.dogList.filter { it.name == newText })
                    return false
                }
            })
                
        }
    }
}