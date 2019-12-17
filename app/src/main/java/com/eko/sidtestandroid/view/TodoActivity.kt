package com.eko.sidtestandroid.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.eko.sidtestandroid.R
import com.eko.sidtestandroid.databinding.ActivityMainBinding
import com.eko.sidtestandroid.model.data.TODO
import com.eko.sidtestandroid.viewModel.TodoVM
/**
 * Created by Siddhant Mehta on 16/12/2019.
 */

class TodoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        setLoader(true)
        val (list, adapter) = initAdapter()
        initVM(list, adapter)
    }

    private fun setLoader(isVisible: Boolean) {
        binding.progressBar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
    }

    private fun initAdapter(): Pair<ArrayList<TODO>, TodoAdapter> {
        val list = ArrayList<TODO>()
        val adapter = TodoAdapter(list)
        binding.rvToDo.adapter = adapter
        return Pair(list, adapter)
    }

    private fun initVM(
        list: ArrayList<TODO>,
        adapter: TodoAdapter
    ) {
        val viewModel = ViewModelProviders.of(this).get(TodoVM::class.java)
        viewModel.fetchData()
        viewModel.todoLiveData.observe(this, Observer {
            setLoader(false)
            it?.let {
                list.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })
    }
}