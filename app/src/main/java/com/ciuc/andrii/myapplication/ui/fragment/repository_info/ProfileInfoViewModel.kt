package com.ciuc.andrii.myapplication.ui.fragment.repository_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ciuc.andrii.myapplication.client.models.repository.RepositoryD
import com.ciuc.andrii.myapplication.repository.ProductRepositoryImpl
import com.ciuc.andrii.myapplication.repository.SharedStorage
import com.ciuc.andrii.myapplication.utils.SingleLiveEvent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ProfileInfoViewModel(private var dataRepository: ProductRepositoryImpl,
                           private val sharedStorage: SharedStorage
) : ViewModel() {

    var repositoriesLiveData = SingleLiveEvent<List<RepositoryD>>()
    var errorLiveData = SingleLiveEvent<String>()

    fun getRepositories(userName: String): LiveData<List<RepositoryD>> {
        val result: Single<List<RepositoryD>> = dataRepository.getRepositories(userName)

        val res = result.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                Timber.d("subscribers -> %s", result.toString())
                repositoriesLiveData.value = result
            }, { error ->
                Timber.d("subscribers error -> %s", error)
                errorLiveData.postValue(error.message)
            })
        return repositoriesLiveData
    }

}