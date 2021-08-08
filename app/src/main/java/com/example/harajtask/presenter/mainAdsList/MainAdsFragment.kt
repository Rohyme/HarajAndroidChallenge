package com.example.harajtask.presenter.mainAdsList

import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.harajtask.databinding.MainAdsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.harajtask.R
import com.example.harajtask.utils.DataResult
import kotlinx.android.synthetic.main.main_ads_fragment.*
import android.app.Activity

import android.R.attr.name
import android.R.attr.textColorHint
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController


@AndroidEntryPoint
class MainAdsFragment : Fragment() {
    private val viewModel: MainAdsViewModel by viewModels()
    private lateinit var binding: MainAdsFragmentBinding
    private val adsAdapter by lazy {
        AdsAdapter {ad ->
            ad.id?.let {id ->
                val dirs = MainAdsFragmentDirections.actionMainAdsFragmentToAdDetailsFragment(id, ad.title)
                findNavController().navigate(dirs)
            }
        }
    }

    private val onSearchChange by lazy {
        object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.onSearchTextChange(newText)
                return true
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainAdsFragmentBinding.inflate(inflater,container,false)
        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setUpRecyclerView()
        setUpObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_screen_menu, menu)
        val searchItem: MenuItem? = menu.findItem(R.id.action_search)
        val searchView: SearchView = searchItem?.actionView as SearchView
        searchView.apply {
            setIconifiedByDefault(false)
            queryHint = "Search for ad (title)"
            setOnQueryTextListener(onSearchChange)
        }
        return super.onCreateOptionsMenu(menu, inflater)
    }


    private fun setUpObservers() {
        viewModel.data.observe(viewLifecycleOwner, {
            if (it != null){
                adsAdapter.submitList(it)
            }
        })
        viewModel.dataState.observe(viewLifecycleOwner, {
            swipeToRefresh.isEnabled = it is DataResult.Error
        })
        viewModel.showError.observe(viewLifecycleOwner){
            Toast.makeText(context, it ,Toast.LENGTH_SHORT).show()
        }
        viewModel.scrollToTop.observe(viewLifecycleOwner){

            binding.adsList.postDelayed({
                binding.adsList.scrollToPosition(0)
            },100)
        }
    }

    private fun setUpRecyclerView() {
        swipeToRefresh.setOnRefreshListener {
            viewModel.retryFetchingData()
        }

        binding.adsList.apply {
            if (this.itemDecorationCount == 0)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = adsAdapter
        }
    }

}