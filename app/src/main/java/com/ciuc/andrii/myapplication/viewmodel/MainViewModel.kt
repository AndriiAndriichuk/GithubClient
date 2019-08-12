package com.ciuc.andrii.myapplication.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ciuc.andrii.myapplication.App
import com.ciuc.andrii.myapplication.model.data.gh_repository.RepositoryDTO
import com.ciuc.andrii.myapplication.model.repository.ProductRepository
import com.ciuc.andrii.myapplication.model.repository.ProductRepositoryImpl
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MainViewModel : ViewModel() {

    private var callbackRepositories = MutableLiveData<List<RepositoryDTO>>()

    fun getRepositories(organization: String): LiveData<List<RepositoryDTO>> {
        val repositoryImpl: ProductRepository = ProductRepositoryImpl()
        var result: Single<List<RepositoryDTO>> = repositoryImpl.getRepositories(organization)

        result.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                Timber.d("repositories -> %s", result.toString())
                callbackRepositories.value = result
            }, { error ->
                Timber.d("repositories error -> %s", error)
            })
        return callbackRepositories
    }

    fun hasNetworkConnection(): Boolean {
        val connectivityManager =
            App.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}