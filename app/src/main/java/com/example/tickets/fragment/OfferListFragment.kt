package com.example.tickets.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tickets.R
import com.example.tickets.adapter.OfferListAdapter
import com.example.tickets.databinding.FragmentOfferListBinding
import com.example.tickets.model.entity.Offer
import com.example.tickets.model.network.ApiClient
import com.example.tickets.model.service.FakeService
import com.google.gson.JsonArray
import com.google.gson.JsonPrimitive
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

    private val adapter: OfferListAdapter by lazy {
        OfferListAdapter()
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
        val call = ApiClient.apiService.getOffers()

        call.enqueue(object : Callback<JsonPrimitive> {
            override fun onResponse(call: Call<JsonPrimitive>, response: Response<JsonPrimitive>) {
                if (response.isSuccessful) {
                    val post = response.body()
                    Log.d("offersss error", post.toString())
                } else {
                    val toast = Toast.makeText(context, "Error accured", Toast.LENGTH_SHORT)
                    toast.show()
                    Log.e("offersss", response.toString())
                }
            }

            override fun onFailure(call: Call<JsonPrimitive>, t: Throwable) {
                val toast = Toast.makeText(context, "Failure accured", Toast.LENGTH_SHORT)
                toast.show()
                Log.e("offersss fail", t.toString())
            }
        })
        val list=FakeService.offerList
        setupUI(list)

        adapter.saveData(FakeService.offerList)
    }

    private fun setupUI(list:List<Offer>) {
        with(binding) {
            offerList.adapter = adapter

            sortRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.sort_by_price -> {
                        val llist: List<Offer> = list.sortedBy { it.price }
                        adapter.saveData(llist)
                    }

                    R.id.sort_by_duration -> {
                        val llist: List<Offer> = list.sortedBy { it.flight.duration }
                        adapter.saveData(llist)
                    }
                }
            }
        }
    }
}