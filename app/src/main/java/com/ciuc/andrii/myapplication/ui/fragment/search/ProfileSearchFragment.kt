package com.ciuc.andrii.myapplication.ui.fragment.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciuc.andrii.myapplication.R
import com.ciuc.andrii.myapplication.databinding.FragmentProfileListBinding
import com.ciuc.andrii.myapplication.model.User
import com.ciuc.andrii.myapplication.ui.fragment.base.BaseFragment
import com.ciuc.andrii.myapplication.ui.fragment.search.adapter.UsersAdapter
import com.ciuc.andrii.myapplication.utils.*
import org.koin.android.ext.android.inject

class ProfileSearchFragment : BaseFragment() {
    private val viewModel: ProfileSearchViewModel by inject()
    private lateinit var layout: FragmentProfileListBinding
    private val adapter = UsersAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        layout = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_list, container, false)
        return layout.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setupUIForHideKeyboard(requireContext())

        setUpView()
        setOnClickListeners()
        subscribeToLiveData()

    }

    private fun setUpView() {
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        layout.recyclerView.layoutManager = linearLayoutManager

    }

    override fun onResume() {
        super.onResume()
        val query = layout.editSearchRepositories.text.toString()
        searchUsers(query)
    }

    private fun setOnClickListeners() {
        layout.btnSearch.setOnClickListener {
            searchUsers(layout.editSearchRepositories.text.toString())
            requireContext().hideKeyboard()
        }

        layout.editSearchRepositories.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchUsers(layout.editSearchRepositories.text.toString())
                requireContext().hideKeyboard()
                true
            } else {
                false
            }
        }

        layout.editSearchRepositories.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (connectivityManager.isOnline) {
                    viewModel.getUsers(s.toString())
                } else {
                    toast(resources.getString(R.string.you_dont_have_internet))
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        adapter.onUserClickListener = { user ->
            openProfileInfo(user)
            requireContext().hideKeyboard()
        }
    }

    private fun subscribeToLiveData() {
        viewModel.usersLiveData.observe(requireActivity(), androidx.lifecycle.Observer {
            if (it.isNotEmpty()) {
                layout.recyclerView.show()
                layout.layoutNoSearch.gone()
                adapter.setData(it)
                layout.recyclerView.adapter = adapter
            } else {
                layout.recyclerView.gone()
                layout.layoutNoSearch.show()
            }
        })

        viewModel.errorLiveData.observe(requireActivity(), androidx.lifecycle.Observer {
            toast(it)
        })
    }


    private fun searchUsers(text: String) {
        if (connectivityManager.isOnline) {
            viewModel.getUsers(text)
        } else {
            toast(resources.getString(R.string.you_dont_have_internet))
        }
    }

    private fun openProfileInfo(user: User) {
        val direction =
            ProfileSearchFragmentDirections.toProfileInfoFragment(user)
        findNavController().navigate(direction)
    }


}
