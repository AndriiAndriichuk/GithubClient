package com.ciuc.andrii.myapplication.ui.activities.main

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ciuc.andrii.myapplication.R
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by inject()

    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


      /*  progressDialog = ProgressDialog(this)
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
        }*/
    }


}
