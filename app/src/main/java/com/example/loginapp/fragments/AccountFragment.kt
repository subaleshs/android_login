package com.example.loginapp.fragments

import android.app.Activity
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
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
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.loginapp.activities.LoginScreenActivity
import com.example.loginapp.R
import com.example.loginapp.databinding.FragmentAccountBinding
import com.example.loginapp.viewmodel.AuthViewModel
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.lang.NullPointerException


class AccountFragment : Fragment() {

    private lateinit var accountFragmentBinding: FragmentAccountBinding
    private lateinit var viewModel: AuthViewModel
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        accountFragmentBinding = FragmentAccountBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        sharedPreferences = activity?.getSharedPreferences(viewModel.getCurrentUser()?.uid.toString(), Context.MODE_PRIVATE)
        return accountFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadImage()
        val email = viewModel.getCurrentUser()?.email
        accountFragmentBinding.userName.text = email?.split('@')?.get(0) ?: "No Username"
        accountFragmentBinding.loginButtonView.setOnClickListener { showLogoutDialog() }

        val result =
            this.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val imgBitmap = result.data?.extras?.get("data") as Bitmap
                    accountFragmentBinding.profileImage.setImageBitmap(imgBitmap)
                    Log.d("test",result?.data?.extras?.keySet().toString())
                    saveImage(imgBitmap)
                } else {
                    Log.d("failcam", "fail")
                }
            }
        val takePic = Intent(MediaStore.ACTION_IMAGE_CAPTURE)


        accountFragmentBinding.profileImage.setOnClickListener {
            if (checkCamPermission()) {
                result.launch(takePic)
            } else {
                ActivityCompat.requestPermissions(
                    activity!!,
                    arrayOf(android.Manifest.permission.CAMERA),
                    111
                )
            }
            accountFragmentBinding.favoritesButton.setOnClickListener {
                val viewPager = requireActivity().findViewById<ViewPager2>(R.id.viewPagerBottomNav)
                viewPager.currentItem = 1
            }

        }
    }

    private fun loadImage() {
        if (sharedPreferences?.contains("path") == true) {
            val imageName = viewModel.getCurrentUser()?.uid.toString()+".jpg"
            val path = sharedPreferences?.getString("path", null)
            try {
                val file = File(path, imageName)
                val image = BitmapFactory.decodeStream(FileInputStream(file))
                accountFragmentBinding.profileImage.setImageBitmap(image)
            } catch (exception: FileNotFoundException) {
                accountFragmentBinding.profileImage.setImageResource(R.drawable.profile)
            }
        } else {
            accountFragmentBinding.profileImage.setImageResource(R.drawable.profile)
        }
    }

    private fun saveImage(image: Bitmap) {
        val directory = context?.getDir("images", Context.MODE_PRIVATE)
        val imageName = viewModel.getCurrentUser()?.uid.toString()+".jpg"
        try {
            val file = File(directory,imageName)
            val fileOutputStream = FileOutputStream(file)
            image.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
        } catch (exception: Exception) {
            AlertDialog.Builder(activity!!).setTitle(R.string.error)
                .setMessage("Image not found")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(R.string.ok){
                    _,_ ->
                }.show()
        }

        if (sharedPreferences?.contains("path") == false) {
            val editor = sharedPreferences?.edit()
            editor?.putString("path", directory?.absolutePath)
            editor?.apply()
        } else {
            if (sharedPreferences?.getString("path", null) == directory?.absolutePath) {
                val editor = sharedPreferences?.edit()
                editor?.putString("path", directory?.absolutePath)
                editor?.apply()
            }
        }
    }

    private fun checkCamPermission(): Boolean {
        return try {
            ActivityCompat.checkSelfPermission(
                activity!!,
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