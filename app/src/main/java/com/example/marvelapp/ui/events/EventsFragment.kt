package com.example.marvelapp.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelapp.R
import com.example.marvelapp.data.DataSource
import com.example.marvelapp.data.model.event.Event
import com.example.marvelapp.databinding.FragmentEventsBinding
import com.example.marvelapp.domain.RepoImpl
import com.example.marvelapp.ui.adapter.EventAdapter
import com.example.marvelapp.ui.events.viewmodel.EventsViewModel
import com.example.marvelapp.utils.Resource
import com.example.marvelapp.utils.VMFactory

class EventsFragment : Fragment() {

    private val viewModel by viewModels<EventsViewModel> {
        VMFactory(
            RepoImpl(DataSource())
        )
    }
    private lateinit var binding: FragmentEventsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_events, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadEventsList()
    }

    private fun loadEventsList() {
        viewModel.getEvents().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    setAdapter(result.data.data.list)
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Error getting events", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun setAdapter(list: ArrayList<Event>) {
        binding.rvEvents.layoutManager = LinearLayoutManager(requireContext())
        binding.rvEvents.setHasFixedSize(true)
        binding.rvEvents.adapter = EventAdapter(requireContext(), list)
    }
}