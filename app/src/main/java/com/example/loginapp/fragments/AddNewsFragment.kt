package com.example.loginapp.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.loginapp.R
import com.example.loginapp.databinding.FragmentAddNewsBinding
import com.example.loginapp.db.CustomNewsEntity
import com.example.loginapp.viewmodel.CustomNewsViewModel

class AddNewsFragment : Fragment() {

    private lateinit var viewModel: CustomNewsViewModel
    lateinit var binding: FragmentAddNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CustomNewsViewModel::class.java)

        binding.newNewsTitle.addTextChangedListener {
            binding.titleTextInputLayout.error = ""
        }
        binding.date.addTextChangedListener {
            binding.dateTextInputLayout.error = ""
        }
        binding.content.addTextChangedListener {
            binding.contentTextInputLayout.error = ""
        }

        binding.addNewsButton.setOnClickListener {
            var title: String = binding.newNewsTitle.text.toString()
            var date: String = binding.date.text.toString()
            var content: String = binding.content.text.toString()
            if (checkForEmptyFields(title, date, content)){
                var news = CustomNewsEntity(
                    title,
                    content,
                    date,
                    "asdf/asd.ll",
                    "asdfasdfasdfas"
                )
                viewModel.addToDB(news)
            }
        }
    }

    private fun checkForEmptyFields(title: String, date: String, content: String): Boolean {
        var notEmpty: Boolean = true
        if (TextUtils.isEmpty(title)) {
            notEmpty = false
            binding.titleTextInputLayout.error = getString(R.string.input_error)
        }
        if (TextUtils.isEmpty(date)) {
            notEmpty = false
            binding.dateTextInputLayout.error = getString(R.string.input_error)
        }
        if (TextUtils.isEmpty(content)){
            notEmpty = false
            binding.contentTextInputLayout.error = getString(R.string.input_error)
        }

        return notEmpty
    }


}