package com.samplecode.giphyapplication.views

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.samplecode.giphyapplication.R
import com.samplecode.giphyapplication.adapters.GifAdapter
import com.samplecode.giphyapplication.databinding.FragmentGifsOverviewBinding
import com.samplecode.giphyapplication.models.Gif
import com.samplecode.giphyapplication.viewmodels.GifsOverviewViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GifsOverviewFragment : Fragment(), GifAdapter.OnItemClickListener {

    private var _binding: FragmentGifsOverviewBinding? = null
    private val viewModel: GifsOverviewViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var tempSearchView: SearchView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentGifsOverviewBinding.inflate(inflater, container, false)

        binding.pullToRefresh.setOnRefreshListener {
            binding.gifList.scrollToPosition(0)
            viewModel.searchGifs("")

            binding.pullToRefresh.isRefreshing = false
        }
        return binding.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        //if(!viewModel.currentQuery.value.isNullOrEmpty()){
            tempSearchView?.performClick()
            tempSearchView?.requestFocus()
        //}
    }

    override fun onResume() {
        super.onResume()

        tempSearchView?.performClick()
        tempSearchView?.requestFocus()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapterTemp = GifAdapter(this)
        binding.apply {
            gifList.layoutManager = StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL )
            gifList.adapter = adapterTemp
        }

        viewModel.listOfGifs.observe(viewLifecycleOwner) {
            adapterTemp.submitData(viewLifecycleOwner.lifecycle, it)
            adapterTemp.notifyDataSetChanged()
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)

        val item = menu.findItem(R.id.search_action)
        val searchView = item?.actionView as? SearchView
        tempSearchView = searchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.queryHint = "Search gifs..."

        searchView?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                searchView?.setQuery(viewModel.currentQuery.value, false)
            }
        })

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null) {
                    binding.gifList.scrollToPosition(0)
                    viewModel.searchGifs(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                try {
                    if (newText != null && newText.isEmpty()) {
                        binding.gifList!!.scrollToPosition(0)
                        viewModel.searchGifs("")
                        searchView.clearFocus()
                    }
                }catch (e: Exception){

                }

                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(gif: Gif) {
        val action = GifsOverviewFragmentDirections.actionFirstFragmentToSecondFragment(
            gif.images.original.url,
            gif.title
        )
        findNavController().navigate(action)
    }
}