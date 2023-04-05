package com.example.advweek4_160420088.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.advweek4_160420088.R
import com.example.advweek4_160420088.util.loadImage
import com.example.advweek4_160420088.viewmodel.DetailViewModel
import com.example.advweek4_160420088.viewmodel.ListViewModel
import com.google.android.material.textfield.TextInputEditText

class StudentDetailFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel
    private val studentDetail= StudentListAdapter(arrayListOf())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_detail, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(arguments != null) {
            val studentId =
                StudentDetailFragmentArgs.fromBundle(requireArguments()).studentId
        }
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.fetch()
        val id = view.findViewById<TextInputEditText>(R.id.txtID)
        val name =view.findViewById<TextInputEditText>(R.id.txtName)
        val dob =view.findViewById<TextInputEditText>(R.id.txtBod)
        val phone = view.findViewById<TextInputEditText>(R.id.txtPhone)
        val photoUrl = view.findViewById<ImageView>(R.id.imageView2)
        id.setText(studentDetail.toString())
        name.setText(studentDetail.toString())
        dob.setText(studentDetail.toString())
        phone.setText(studentDetail.toString())
        //photoUrl.setImageURI(studentDetail.toString())
        var imageView = view.findViewById<ImageView>(R.id.imageView)
        var progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        imageView.loadImage(studentDetail.toString(), progressBar)
        observeViewModel()
    }
    fun observeViewModel() {
        viewModel.studentLD.observe(viewLifecycleOwner, Observer {
            studentDetail.updateStudentList(arrayListOf())
        })
    }
}