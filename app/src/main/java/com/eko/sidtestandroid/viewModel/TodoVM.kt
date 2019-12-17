package com.eko.sidtestandroid.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

import com.eko.sidtestandroid.model.data.TODO
import com.eko.sidtestandroid.model.repository.TodoRepository

class TodoVM(application: Application) : AndroidViewModel(application) {
   internal var todoLiveData = MutableLiveData<List<TODO>>()

    internal fun fetchData() {
        todoLiveData = TodoRepository(getApplication()).getResponse()
    }
}
