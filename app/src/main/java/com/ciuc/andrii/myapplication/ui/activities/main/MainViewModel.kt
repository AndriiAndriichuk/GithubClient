package com.ciuc.andrii.myapplication.ui.activities.main

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ciuc.andrii.myapplication.App
import com.ciuc.andrii.myapplication.client.models.gh_repository.RepositoryDTO
import com.ciuc.andrii.myapplication.repository.ProductRepositoryImpl
import com.ciuc.andrii.myapplication.repository.SharedStorage
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MainViewModel(private var dataRepository: ProductRepositoryImpl,
                    private val sharedStorage: SharedStorage
) : ViewModel() {

    private var callbackRepositories = MutableLiveData<List<RepositoryDTO>>()

    fun getRepositories(organization: String): LiveData<List<RepositoryDTO>> {
        var result: Single<List<RepositoryDTO>> = dataRepository.getRepositories(organization)

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