package com.example.loginapp.fragments

import android.app.Activity
import android.content.*
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.loginapp.activities.LoginScreenActivity
import com.example.loginapp.R
import com.example.loginapp.databinding.FragmentAccountBinding
import com.example.loginapp.utils.NetworkChecks
import com.example.loginapp.viewmodel.AuthViewModel
import java.io.File
import java.io.FileNotFoundException
import java.lang.NullPointerException
import java.util.jar.Manifest


class AccountFragment : Fragment() {

    private lateinit var accountFragmentBinding: FragmentAccountBinding
    private lateinit var viewModel: AuthViewModel
    private var sharedPreferences: SharedPreferences? = null
    private var photoFile: File? = null
    private var mCurrentPhotoPath: String? = null
    private var readPermission = false
    private var writePermission = false
    var permissionRequired: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        accountFragmentBinding = FragmentAccountBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        sharedPreferences = activity?.getSharedPreferences(
            viewModel.getCurrentUser()?.uid.toString(),
            Context.MODE_PRIVATE
        )
        return accountFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadImage()
        val email = viewModel.getCurrentUser()?.email
        accountFragmentBinding.userName.text = email?.split('@')?.get(0) ?: "No Username"
        accountFragmentBinding.logoutButtonView.setOnClickListener { showLogoutDialog() }


        val result =
            this.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
//                    val imgBitmap = result.data?.extras?.get("data") as Bitmap
                    if (photoFile != null) {
                        Log.d("path", photoFile?.absolutePath.toString())
                        val img = BitmapFactory.decodeFile(photoFile?.absolutePath)
                        val editor = sharedPreferences?.edit()
                        editor?.putString("path", photoFile?.absolutePath)
                        editor?.apply()
                        accountFragmentBinding.profileImage.setImageBitmap(img)
                    }
                    Log.d("test", result?.data?.extras?.keySet().toString())
//                    saveImage(imgBitmap)
                } else {
                    Log.d("failcam", "fail")
                }
            }
        val takePic = Intent(MediaStore.ACTION_IMAGE_CAPTURE)


        accountFragmentBinding.profileImage.setOnClickListener {
            if (checkCamPermission()) {
                if (!checkReadAndWritePermissions()) {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        permissionRequired.toTypedArray(),
                        111
                    )
                } else {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                    } else {
                        photoFile = createImageFile()
                        if (photoFile != null) {
                            val photoURI = FileProvider.getUriForFile(
                                requireActivity(), "com.example.loginapp.provider",
                                photoFile!!
                            )
                            takePic.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                            result.launch(takePic)
                        } else {
                            print("v")
                        }
                    }
                }
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(android.Manifest.permission.CAMERA),
                    111
                )
            }
        }

        accountFragmentBinding.resetPasswordButton.setOnClickListener {
            if (email != null) {
                AlertDialog.Builder(it.context).setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.reset_password_check)
                    .setPositiveButton(R.string.confirm) { _, _ ->
                        resetUserPassword(email)
                    }
                    .setNegativeButton(R.string.cancel) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }

        accountFragmentBinding.favoritesButton.setOnClickListener {
            val viewPager = requireActivity().findViewById<ViewPager2>(R.id.viewPagerBottomNav)
            viewPager.currentItem = 1
        }

    }

    private fun checkReadAndWritePermissions(): Boolean {
        val hasReadPermission = ActivityCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        val hasWritePermission = ActivityCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissionRequired.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            hasReadPermission
        } else {
            if (!hasReadPermission) {
                permissionRequired.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            if (!hasWritePermission) {
                permissionRequired.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }

            permissionRequired.size <= 0
        }

    }

    private fun loadImage() {
        if (sharedPreferences?.contains("path") == true) {
            val path = sharedPreferences?.getString("path", null)
            Log.d("paa", path.toString())
            try {
                val image = BitmapFactory.decodeFile(path)
                accountFragmentBinding.profileImage.setImageBitmap(image)
            } catch (exception: FileNotFoundException) {
                accountFragmentBinding.profileImage.setImageResource(R.drawable.profile)
            }
        } else {
            accountFragmentBinding.profileImage.setImageResource(R.drawable.profile)
        }
    }

    private fun createImageFile(): File {
        val imageFileName = viewModel.getCurrentUser()?.uid.toString()
        val storageDir = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
        )
        mCurrentPhotoPath = image.absolutePath
        return image
    }


    private fun checkCamPermission(): Boolean {
        return try {
            ActivityCompat.checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        } catch (exception: NullPointerException) {
            false
        }
    }


    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.account_title)
    }

    private fun resetUserPassword(email: String) {
        if (NetworkChecks.isNetworkConnected(activity)) {
            viewModel.resetPassword(email)
            AlertDialog.Builder(requireContext()).setIcon(R.drawable.ic_done)
                .setTitle(R.string.email_send_success)
                .setMessage(R.string.check_email)
                .setCancelable(true)
                .setNeutralButton(R.string.cancel) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        } else {
            android.app.AlertDialog.Builder(requireContext())
                .setTitle(R.string.no_internet)
                .setMessage(R.string.no_internet_message)
                .setPositiveButton(android.R.string.ok) { _, _ -> }
                .setIcon(android.R.drawable.ic_dialog_alert).show()
        }
    }


    private fun showLogoutDialog() {

        val logoutConfirmAlert = AlertDialog.Builder(requireActivity())
        logoutConfirmAlert.setMessage(R.string.logout_confirm)
        logoutConfirmAlert.setPositiveButton(R.string.confirm) { _, _ -> logOut() }
        logoutConfirmAlert.setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
        logoutConfirmAlert.show()
    }

    private fun logOut() {
        viewModel.logOut()
        val activityIntent = Intent(activity, LoginScreenActivity::class.java)
        startActivity(activityIntent)
    }

}