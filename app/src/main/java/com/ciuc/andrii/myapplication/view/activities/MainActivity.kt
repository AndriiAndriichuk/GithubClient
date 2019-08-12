package com.ciuc.andrii.myapplication.view.activities

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chootdev.recycleclick.RecycleClick
import com.ciuc.andrii.myapplication.R
import com.ciuc.andrii.myapplication.model.utils.Constants.Companion.listRepositories
import com.ciuc.andrii.myapplication.model.utils.Constants.Companion.repositoryDTO
import com.ciuc.andrii.myapplication.model.utils.toast
import com.ciuc.andrii.myapplication.view.adapters.RepositoryAdapter
import com.ciuc.andrii.myapplication.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        supportActionBar?.hide()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle(resources.getString(R.string.loading))
        progressDialog.setMessage(resources.getString(R.string.please_wait))
        progressDialog.setCancelable(false)

        val linearLayoutManager =
            LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager

        btnSearch.setOnClickListener {
            searchRepositories(editSearchRepositories.text.toString())
        }

        editSearchRepositories.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchRepositories(editSearchRepositories.text.toString())
                true
            } else {
                false
            }
        }


        RecycleClick.addTo(recyclerView).setOnItemClickListener { _, position, _ ->
            repositoryDTO = listRepositories[position]
            startActivity(Intent(this@MainActivity, RepositoryInfoActivity::class.java))
        }
    }

    private fun searchRepositories(text: String) {
        hideSoftKeyboard(editSearchRepositories)
        progressDialog.show()
        if (mainViewModel.hasNetworkConnection()) {
            mainViewModel.getRepositories(text).observe(this, androidx.lifecycle.Observer {
                if (it != null) {
                    listRepositories = it
                    val adapter = RepositoryAdapter(listRepositories)
                    recyclerView.adapter = adapter
                }
                progressDialog.cancel()
            })
        } else {
            progressDialog.cancel()
            toast(resources.getString(R.string.you_dont_have_internet))
        }
    }

    private fun hideSoftKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
