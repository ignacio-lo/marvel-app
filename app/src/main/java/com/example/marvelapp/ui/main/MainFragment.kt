package com.example.marvelapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.data.DataSource
import com.example.marvelapp.data.model.character.Character
import com.example.marvelapp.databinding.FragmentMainBinding
import com.example.marvelapp.domain.RepoImpl
import com.example.marvelapp.ui.adapter.CharacterAdapter
import com.example.marvelapp.ui.detail.DetailActivity
import com.example.marvelapp.ui.main.viewmodel.MainViewModel
import com.example.marvelapp.utils.VMFactory
import com.example.marvelapp.utils.Resource

class MainFragment : Fragment(), CharacterAdapter.CharacterActions {

    private val viewModel by viewModels<MainViewModel> {
        VMFactory(
            RepoImpl(DataSource())
        )
    }
    private lateinit var binding: FragmentMainBinding
    private var lastInformedCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerViewOnScrollListener()

        loadCharactersList()
    }

    override fun onCharacterClicked(id: Int) {
        startActivity(DetailActivity.createIntent(requireContext(), id))
    }

    private fun loadCharactersList() {
        viewModel.getCharacters(false).observe(viewLifecycleOwner, Observer { result ->
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
                    Toast.makeText(requireContext(), "Error getting characters", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun loadNextPage() {
        viewModel.getCharacters(true).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.progressBar.updateLayoutParams<ConstraintLayout.LayoutParams> { topToTop = ConstraintLayout.LayoutParams.UNSET }
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    addCharacters(result.data.data.list)
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Error getting next page characters", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun setAdapter(list: ArrayList<Character>) {
        binding.rvCharacters.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCharacters.setHasFixedSize(true)
        binding.rvCharacters.adapter = CharacterAdapter(requireContext(), list, this)
    }

    private fun addCharacters(list: ArrayList<Character>) {
        (binding.rvCharacters.adapter as CharacterAdapter).addCharacters(list)
    }

    private fun setRecyclerViewOnScrollListener() {
        binding.rvCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition: Int = (binding.rvCharacters.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                val totalItemCount = recyclerView.layoutManager!!.itemCount

                if (lastInformedCount != totalItemCount && totalItemCount == lastVisibleItemPosition + 1) {
                    loadNextPage()
                    lastInformedCount = recyclerView.layoutManager!!.itemCount
                }
            }
        })
    }
}