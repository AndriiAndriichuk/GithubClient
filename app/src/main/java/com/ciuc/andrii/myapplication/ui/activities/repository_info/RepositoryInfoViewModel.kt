package com.ciuc.andrii.myapplication.ui.activities.repository_info

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ciuc.andrii.myapplication.App
import com.ciuc.andrii.myapplication.client.models.followers.Subscriber
import com.ciuc.andrii.myapplication.repository.ProductRepository
import com.ciuc.andrii.myapplication.repository.ProductRepositoryImpl
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class RepositoryInfoViewModel : ViewModel() {

    private var callbackFollowers = MutableLiveData<List<Subscriber>>()

    fun getSubscribers(userName: String): LiveData<List<Subscriber>> {
        val repositoryImpl: ProductRepository = ProductRepositoryImpl()
        var result: Single<List<Subscriber>> = repositoryImpl.getSubscribers(userName)

        result.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                Timber.d("subscribers -> %s", result.toString())
                callbackFollowers.value = result
            }, { error ->
                Timber.d("subscribers error -> %s", error)
            })
        return callbackFollowers
    }

    fun hasNetworkConnection(): Boolean {
        val connectivityManager =
            App.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

}