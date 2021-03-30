package com.ciuc.andrii.myapplication.ui.fragment.search

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chootdev.recycleclick.RecycleClick
import com.ciuc.andrii.myapplication.R
import com.ciuc.andrii.myapplication.databinding.FragmentProfileListBinding
import com.ciuc.andrii.myapplication.ui.activities.main.MainViewModel
import com.ciuc.andrii.myapplication.ui.activities.repository_info.adapters.RepositoryAdapter
import com.ciuc.andrii.myapplication.ui.fragment.base.BaseFragment
import com.ciuc.andrii.utils.Constants.Companion.listRepositories
import com.ciuc.andrii.utils.Constants.Companion.repositoryDTO
import com.ciuc.andrii.utils.toast
import org.koin.android.ext.android.inject

class ProfileListFragment : BaseFragment() {
    private val mainViewModel: MainViewModel by inject()
    private lateinit var layout: FragmentProfileListBinding
    lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        layout = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_list, container, false)
        return layout.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressDialog = ProgressDialog(requireContext())
        progressDialog.setTitle(resources.getString(R.string.loading))
        progressDialog.setMessage(resources.getString(R.string.please_wait))
        progressDialog.setCancelable(false)

        val linearLayoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        layout.recyclerView.layoutManager = linearLayoutManager

        layout.btnSearch.setOnClickListener {
            searchRepositories(layout.editSearchRepositories.text.toString())
        }

        layout.editSearchRepositories.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchRepositories(layout.editSearchRepositories.text.toString())
                true
            } else {
                false
            }
        }


         RecycleClick.addTo(layout.recyclerView).setOnItemClickListener { _, position, _ ->
             repositoryDTO = listRepositories[position]
             val direction = ProfileListFragmentDirections.toProfileInfoFragment()
             findNavController().navigate(direction)
         }

/*
        setUpView()
        setOnClickListeners()
        subscribeToLiveData()*/
    }

    private fun searchRepositories(text: String) {
        hideSoftKeyboard(layout.editSearchRepositories)
        progressDialog.show()
        if (mainViewModel.hasNetworkConnection()) {
            mainViewModel.getRepositories(text)
                .observe(requireActivity(), androidx.lifecycle.Observer {
                    if (it != null) {
                        listRepositories = it
                        val adapter = RepositoryAdapter(listRepositories)
                        layout.recyclerView.adapter = adapter
                    }
                    progressDialog.cancel()
                })
        } else {
            progressDialog.cancel()
            requireContext().toast(resources.getString(R.string.you_dont_have_internet))
        }
    }

    private fun hideSoftKeyboard(view: View) {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


}
