package com.eko.sidtestandroid.model.repository

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.eko.sidtestandroid.model.api.ApiBuilder
import com.eko.sidtestandroid.model.data.TODO
import com.eko.sidtestandroid.model.local.AppDatabase
import com.eko.sidtestandroid.util.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TodoRepository(var application: Application) {

    private var responseLiveData: MutableLiveData<List<TODO>> = MutableLiveData()

    internal fun getResponse(): MutableLiveData<List<TODO>> {

        return when {
            Util.isNetworkAvailable(application) -> {
                fetchfromRemote()
            }
            else -> {
                fetchfromLocal()
            }
        }
    }

    private fun fetchfromLocal(): MutableLiveData<List<TODO>> {
        val list = AppDatabase.getDatabase(application).todoDao().getAll()
        responseLiveData.value = list
        return responseLiveData

    }

    private fun fetchfromRemote(): MutableLiveData<List<TODO>> {
        val call: Call<List<TODO>> = ApiBuilder.apiService.getTodoList()
        call.enqueue(object : Callback<List<TODO>> {

            override fun onResponse(call: Call<List<TODO>>, response: Response<List<TODO>>) {
                if (response.isSuccessful && response.body() != null) {
                    val list = response.body()
                    if (!list.isNullOrEmpty()) {
                        responseLiveData.value = list
                        savetoLocal(list)
                    }
                }
            }

            override fun onFailure(call: Call<List<TODO>>, t: Throwable) {
                responseLiveData.value = null
                Toast.makeText(application, t.message, Toast.LENGTH_LONG).show()
            }
        })
        return responseLiveData
    }

    private fun savetoLocal(list: List<TODO>) {
        AppDatabase.getDatabase(application).todoDao().insertAll(list)
    }
}