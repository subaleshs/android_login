package com.example.loginapp.fragments

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.loginapp.activities.LoginScreenActivity
import com.example.loginapp.R
import com.example.loginapp.databinding.FragmentAccountBinding
import com.google.firebase.auth.FirebaseAuth
import java.lang.NullPointerException


class AccountFragment() : Fragment() {

    private lateinit var accountFragmentBinding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        accountFragmentBinding = FragmentAccountBinding.inflate(inflater, container, false)
        return accountFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val authFirebase = FirebaseAuth.getInstance()
        accountFragmentBinding.profileImage.setImageResource(R.drawable.profile)
        val email = authFirebase.currentUser?.email
        accountFragmentBinding.userName.text = email?.split('@')?.get(0) ?: "No Username"
        accountFragmentBinding.loginButtonView.setOnClickListener { showLogoutDialog() }

        val result =
            this.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val imgBitmap = result.data?.extras?.get("data") as Bitmap
                    accountFragmentBinding.profileImage.setImageBitmap(imgBitmap)
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

    private fun performCrop(imageUri: Uri?, p: ActivityResultLauncher<Intent>) {
        try {
            val cropIntent = Intent("com.android.camera.action.CROP")
            cropIntent.setDataAndType(imageUri, "image/*")
            cropIntent.putExtra("crop", "true")
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 2)
            cropIntent.putExtra("aspectY", 1)
            // indicate output X and Y
            cropIntent.putExtra("outputX", 256)
            cropIntent.putExtra("outputY", 256)
            // retrieve data on return
            cropIntent.putExtra("return-data", true)
            p.launch(cropIntent)

        } catch (e: ActivityNotFoundException) {
            Log.d("crop", "This device doesn't support the crop action!")
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
        FirebaseAuth.getInstance().signOut()
        val activityIntent = Intent(activity, LoginScreenActivity::class.java)
        startActivity(activityIntent)
    }

}