package com.example.loginapp.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
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
import com.example.loginapp.utils.APILevelCheck
import com.example.loginapp.viewmodel.CustomNewsViewModel
import java.io.BufferedInputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream

class AddNewsFragment : Fragment() {

    private lateinit var viewModel: CustomNewsViewModel
    private lateinit var binding: FragmentAddNewsBinding

    private var readPermission: Boolean = false
    private var writePermission: Boolean = false
    private val requestPermission: MutableList<String> = mutableListOf()
    private var imageFile: File? = null
    private var imageUri: Uri? = null

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

        val result =
            this.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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
                    } else {
                        if (imageUri != null) {
                            var fileInputStream: InputStream? = null
                            try {
                                fileInputStream =
                                    context?.contentResolver?.openInputStream(imageUri!!)
                            } catch (exception: FileNotFoundException) {
                                showExceptionAlert(R.string.file_creation_error)
                            }
                            val image = fileInputStream?.let { BitmapFactory.decodeStream(it) }
                            binding.capImage.setImageBitmap(image)

                        }
                    }

                }
            }
        val takePic = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

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
                Log.d("per", "$readPermission $writePermission $requestPermission")
                if (requestPermission.size <= 0) {
                    if (APILevelCheck.isAPIGreaterThan28()) {
                        val dirPath = Environment.DIRECTORY_PICTURES + "/Truth"
                        context?.applicationContext?.getExternalFilesDir(dirPath)
                        val time = System.currentTimeMillis().toString()
                        val imageName = "IMG_$time.jpg"
                        val contentValues = ContentValues().apply {
                            put(MediaStore.MediaColumns.DISPLAY_NAME, imageName)
                            put(MediaStore.MediaColumns.MIME_TYPE, "image/*")
                            put(MediaStore.MediaColumns.RELATIVE_PATH, dirPath)
                        }

                        val resolver = context?.contentResolver
                        if (resolver != null) {
                            imageUri = resolver.insert(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                contentValues
                            )
                            if (imageUri != null) {
                                takePic.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                                result.launch(takePic)
                            }

                        }
                    } else {
                        if (false) {
                            val selectPic = Intent(Intent.ACTION_GET_CONTENT)
                            selectPic.type = "image/*"
                            result.launch(selectPic)
                        } else {
                            createTemImageFile()
                            if (imageFile != null) {
                                val uri = FileProvider.getUriForFile(
                                    requireContext(), "com.example.loginapp.provider",
                                    imageFile!!
                                )
                                val resolveInfoList =
                                    context?.packageManager?.queryIntentActivities(
                                        takePic,
                                        PackageManager.MATCH_DEFAULT_ONLY
                                    )
                                Log.d("resolve", resolveInfoList.toString())
                                if (resolveInfoList != null) {
                                    for (resInfo in resolveInfoList) {
                                        val packageName = resInfo.activityInfo.packageName
                                        context?.grantUriPermission(
                                            packageName,
                                            uri,
                                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                                        )
                                    }
                                }
                                takePic.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                                result.launch(takePic)
                            }
                        }
                    }
                } else {
                    ActivityCompat.requestPermissions(
                        requireContext() as Activity,
                        requestPermission.toTypedArray(),
                        111
                    )
                }
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(android.Manifest.permission.CAMERA),
                    101
                )
            }
        }
        binding.addNewsButton.setOnClickListener {
            val title: String = binding.newNewsTitle.text.toString()
            val date: String = binding.date.text.toString()
            val content: String = binding.content.text.toString()
            if (checkForEmptyFields(title, date, content)) {
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

    private fun createTemImageFile() {
        val path: File? = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val storageDirectory = File(path, "/Truth")
        if (!storageDirectory.exists()) {
            if (!storageDirectory.mkdir()) {
                Toast.makeText(requireContext(), "cant create dir", Toast.LENGTH_SHORT).show()
            }
        }
        val time = System.currentTimeMillis().toString()
        val imageName = "IMG_$time"
        try {
            imageFile = File.createTempFile(imageName, ".jpg", storageDirectory).apply {
                createNewFile()
            }
        } catch (exception: Exception) {
            showExceptionAlert(R.string.file_creation_error)
        }
    }

    private fun checkReadwritePermission() {
        readPermission = ActivityCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        writePermission = ActivityCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED || Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
        requestPermission.clear()
        if (APILevelCheck.isAPIGreaterThan28() && !readPermission) {
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
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
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
        if (TextUtils.isEmpty(content)) {
            notEmpty = false
            binding.contentTextInputLayout.error = getString(R.string.input_error)
        }

        return notEmpty
    }

    private fun showExceptionAlert(errorStringResource: Int) {
        AlertDialog.Builder(requireContext()).setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle(R.string.error)
            .setMessage(errorStringResource)
            .setPositiveButton(R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }


}