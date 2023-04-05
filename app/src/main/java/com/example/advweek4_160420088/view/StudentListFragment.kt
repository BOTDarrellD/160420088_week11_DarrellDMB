package com.example.advweek4_160420088.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.advweek4_160420088.R
import com.example.advweek4_160420088.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_student_list.*

class StudentListFragment : Fragment() {
    private lateinit var viewModel: ListViewModel
    private val studentListAdapter = StudentListAdapter(arrayListOf())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_list, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh()
        val recView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recView.layoutManager = LinearLayoutManager(context)
        recView.adapter = studentListAdapter
        observeViewModel()

        refreshLayout.setOnRefreshListener {
            recView.visibility = View.GONE
            txtError.visibility = View.GONE
            progressLoad.visibility = View.VISIBLE
            viewModel.refresh()
            refreshLayout.isRefreshing = false
        }

    }

    fun observeViewModel() {
        viewModel.studentsLD.observe(viewLifecycleOwner, Observer {
            studentListAdapter.updateStudentList(it)
        })
        viewModel.studentLoadErrorLD.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                txtError.visibility = View.VISIBLE
            } else {
                txtError.visibility = View.GONE
            }
        })
        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            val recView = view?.findViewById<RecyclerView>(R.id.recyclerView)
            if(it == true) {
                if (recView != null) {
                    recView.visibility = View.GONE
                }
                progressLoad.visibility = View.VISIBLE
            } else {
                if (recView != null) {
                    recView.visibility = View.VISIBLE
                }
                progressLoad.visibility = View.GONE
            }
        })

    }
}