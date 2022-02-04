package com.example.loginapp.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.loginapp.R
import com.example.loginapp.databinding.FragmentAddNewsBinding
import com.example.loginapp.viewmodel.CustomNewsViewModel
import java.io.File
import java.io.IOException

class AddNewsFragment : Fragment() {

    private lateinit var viewModel: CustomNewsViewModel
    private lateinit var binding: FragmentAddNewsBinding

    private var readPermission: Boolean = false
    private var writePermission: Boolean = false
    private val requestPermission: MutableList<String> = mutableListOf()
    private var imageFile: File? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CustomNewsViewModel::class.java)

        val result = this.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (imageFile != null) {
                    val bmp = BitmapFactory.decodeFile(imageFile?.absolutePath)
                    Log.d("imgF", imageFile?.absolutePath.toString())
                    binding.capImage.setImageBitmap(bmp)
                    context?.sendBroadcast(
                        Intent(
                            Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                            Uri.parse("file://" + imageFile?.absolutePath)
                        )
                    )
                }

            }
        }
        val takePic = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

//        takePic.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)


        binding.newNewsTitle.addTextChangedListener {
            binding.titleTextInputLayout.error = ""
        }
        binding.date.addTextChangedListener {
            binding.dateTextInputLayout.error = ""
        }
        binding.content.addTextChangedListener {
            binding.contentTextInputLayout.error = ""
        }

        binding.appCompatImageButton.setOnClickListener {
            if (checkCameraPermission()) {
                checkReadwritePermission()
                if (requestPermission.size <=0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                    } else {
                        createTemImageFile()
                        if (imageFile != null) {
                            val uri = FileProvider.getUriForFile(requireContext(), "com.example.loginapp.provider",
                                imageFile!!
                            )
                            val resolveInfoList = context?.packageManager?.queryIntentActivities(takePic, PackageManager.MATCH_DEFAULT_ONLY)
                            Log.d("resolve", resolveInfoList.toString())
                            if (resolveInfoList != null) {
                                for (resInfo in resolveInfoList) {
                                    val packageName = resInfo.activityInfo.packageName
                                    context?.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                                }
                            }
                            takePic.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                            result.launch(takePic)
                        }
                    }
                } else {
                    ActivityCompat.requestPermissions(requireContext() as Activity, requestPermission.toTypedArray(), 111)
                }
            } else {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CAMERA), 101)
            }
        }
        binding.addNewsButton.setOnClickListener {
            val title: String = binding.newNewsTitle.text.toString()
            val date: String = binding.date.text.toString()
            val content: String = binding.content.text.toString()
            if (checkForEmptyFields(title, date, content)){
//                var news = CustomNewsEntity(
//                    title,
//                    content,
//                    date,
//                    "asdf/asd.ll",
//                    "asdfasdfasdfas"
//                )
//                viewModel.addToDB(news)
            }
        }
    }

    private fun createTemImageFile(){
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val storageDirectory = File(path,"/Truth")
        if (!storageDirectory.exists()){
            if (!storageDirectory.mkdir()) {
                Toast.makeText(requireContext(), "cant create dir", Toast.LENGTH_SHORT).show()
            }
        }
        Log.d("abs", storageDirectory.absolutePath)
        val time = System.currentTimeMillis().toString()
        val imageName = "IMG_$time"
        Log.d("dir", "$storageDirectory $imageName")
        try {
            imageFile = File.createTempFile(imageName, ".jpg", storageDirectory).apply {
                createNewFile()
            }
        } catch (exception: Exception) {
            AlertDialog.Builder(requireContext()).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.error)
                .setMessage(R.string.file_creation_error)
        }
    }

    private fun checkReadwritePermission() {
        readPermission = ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        writePermission = ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED || Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
        requestPermission.clear()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !readPermission) {
            requestPermission.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            if (!readPermission) {
                requestPermission.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            if (!writePermission) {
                requestPermission.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }

    private fun checkCameraPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkForEmptyFields(title: String, date: String, content: String): Boolean {
        var notEmpty = true
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