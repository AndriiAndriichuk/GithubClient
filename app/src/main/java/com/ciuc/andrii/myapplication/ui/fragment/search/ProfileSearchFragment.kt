package com.ciuc.andrii.myapplication.ui.fragment.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciuc.andrii.myapplication.R
import com.ciuc.andrii.myapplication.client.models.user.UserSearchItem
import com.ciuc.andrii.myapplication.databinding.FragmentProfileListBinding
import com.ciuc.andrii.myapplication.ui.fragment.base.BaseFragment
import com.ciuc.andrii.myapplication.ui.fragment.search.adapter.UsersAdapter
import com.ciuc.andrii.myapplication.utils.gone
import com.ciuc.andrii.myapplication.utils.show
import com.ciuc.andrii.myapplication.utils.toast
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

        setUpView()
        setOnClickListeners()
        subscribeToLiveData()

        searchUsers("")
    }

    private fun setUpView() {
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        layout.recyclerView.layoutManager = linearLayoutManager

    }

    private fun setOnClickListeners() {
        layout.btnSearch.setOnClickListener {
            searchUsers(layout.editSearchRepositories.text.toString())
        }

        layout.editSearchRepositories.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchUsers(layout.editSearchRepositories.text.toString())
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
                    requireContext().toast(resources.getString(R.string.you_dont_have_internet))
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        adapter.onUserClickListener = { user ->
            openProfileInfo(user)
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
    }


    private fun searchUsers(text: String) {
        hideSoftKeyboard(layout.editSearchRepositories)
        if (connectivityManager.isOnline) {
            viewModel.getUsers(text)
        } else {
            requireContext().toast(resources.getString(R.string.you_dont_have_internet))
        }
    }

    private fun openProfileInfo(userSearchItem: UserSearchItem) {
        val direction =
            ProfileSearchFragmentDirections.toProfileInfoFragment(userSearchItem)
        findNavController().navigate(direction)
    }

    private fun hideSoftKeyboard(view: View) {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


}
