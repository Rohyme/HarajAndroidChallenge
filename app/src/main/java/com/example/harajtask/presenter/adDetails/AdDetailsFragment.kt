package com.example.harajtask.presenter.adDetails

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.harajtask.R
import com.example.harajtask.databinding.AdDetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdDetailsFragment : Fragment() {

    private val viewModel: AdDetailsViewModel by viewModels()
    private lateinit var binding: AdDetailsFragmentBinding
    private val navArgs = navArgs<AdDetailsFragmentArgs>()
    private val adId by lazy { navArgs.value.adId }
    private val adTitle by lazy { navArgs.value.adTitle }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadAdDetails(adId)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AdDetailsFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@AdDetailsFragment.viewModel
        }

        viewModel.showError.observe(viewLifecycleOwner){
            Toast.makeText(context, it , Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.ad_details_menu, menu)
        val shareItem : MenuItem? = menu.findItem(R.id.action_share)
        shareItem?.setOnMenuItemClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, adTitle)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
            return@setOnMenuItemClickListener true
        }

        return super.onCreateOptionsMenu(menu, inflater)
    }



}