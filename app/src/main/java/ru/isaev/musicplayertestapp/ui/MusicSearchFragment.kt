package ru.isaev.musicplayertestapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log.e
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.isaev.musicplayertestapp.R
import ru.isaev.musicplayertestapp.adapter.TrackListAdapter
import ru.isaev.musicplayertestapp.databinding.FragmentMusicSearchBinding
import ru.isaev.musicplayertestapp.utils.Constants
import ru.isaev.musicplayertestapp.utils.Resource


class MusicSearchFragment : Fragment() {

    private var _binding: FragmentMusicSearchBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Binding isn't initialized"
        }
    private val viewModel: MainViewModel by activityViewModels()
    private var _trackAdapter: TrackListAdapter? = null
    private val trackAdapter
        get() = checkNotNull(_trackAdapter) {
            "TrackListAdapter isn't initialized"
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMusicSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()

        trackAdapter.setOnItemClickListener { song ->
            val bundle = Bundle().apply {
                putSerializable("song", song)
            }
            findNavController().navigate(
                R.id.action_FirstFragment_to_SecondFragment,
                bundle
            )
        }
        binding.apply {
            var job: Job? = null
            etSearch.addTextChangedListener {
                job?.cancel()
                job = MainScope().launch {
                    delay(Constants.SEARCH_REQUEST_DELAY)
                    it?.let {
                        if (it.toString().isNotEmpty() && it.toString().length > 5) {
                            viewModel.searchForAllMusic(it.toString())
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("LongLogTag")
    override fun onStart() {
        super.onStart()
        viewModel.musicSearch.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    trackAdapter.differ.submitList(it.data?.results)
                    e("MusicSearchFragment", it.data.toString())
                }
                is Resource.Error -> {
                    showLoading(false)
                    e("MusicSearchFragmentError", it.message.toString())
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRV() {
        _trackAdapter = TrackListAdapter()
        binding.rvSearchMusic.apply {
            adapter = trackAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun showLoading(status: Boolean) {
        binding.paginationProgressBar.visibility = if (status) View.VISIBLE else View.GONE
    }
}