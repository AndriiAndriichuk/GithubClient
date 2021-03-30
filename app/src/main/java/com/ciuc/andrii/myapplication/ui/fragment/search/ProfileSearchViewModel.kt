package com.ciuc.andrii.myapplication.ui.fragment.search

import androidx.lifecycle.ViewModel
import com.ciuc.andrii.myapplication.client.models.user.UserSearchItem
import com.ciuc.andrii.myapplication.client.models.user.UserSearchResponse
import com.ciuc.andrii.myapplication.mappers.UserMapper
import com.ciuc.andrii.myapplication.model.User
import com.ciuc.andrii.myapplication.repository.ProductRepositoryImpl
import com.ciuc.andrii.myapplication.repository.SharedStorage
import com.ciuc.andrii.myapplication.utils.SingleLiveEvent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ProfileSearchViewModel(
    private var dataRepository: ProductRepositoryImpl,
    private val sharedStorage: SharedStorage
) : ViewModel() {

    var usersLiveData = SingleLiveEvent<List<User>>()
    var errorLiveData = SingleLiveEvent<String>()

    fun getUsers(query: String) {
        if (query.isEmpty()) searchUsersWithoutQuery()
        else searchUsers(query)
    }

    private fun searchUsers(query: String) {
        val result: Single<UserSearchResponse> = dataRepository.searchUsers(query)

        val res = result.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                Timber.d("repositories -> %s", result.toString())
                usersLiveData.value = result.items.map { UserMapper.toUser(it) }
            }, { error ->
                Timber.d("repositories error -> %s", error)
                errorLiveData.postValue(error.message)
            })
    }

    private fun searchUsersWithoutQuery() {
        val result: Single<List<UserSearchItem>> = dataRepository.searchUsersWithoutQuery()

        val res = result.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                Timber.d("repositories -> %s", result.toString())
                usersLiveData.value = result.map { UserMapper.toUser(it) }
            }, { error ->
                Timber.d("repositories error -> %s", error)
                errorLiveData.postValue(error.message)
            })
    }
}