package com.example.loginapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.loginapp.databinding.ActivityHomeScreenBinding
import com.example.loginapp.databinding.FragmentSignupBinding
import org.json.JSONObject
import java.io.IOException

class HomeScreenActivity : AppCompatActivity() {

    private lateinit var homeScreenActivityBinding: ActivityHomeScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loginSharedPreferences: SharedPreferences= getSharedPreferences("user", MODE_PRIVATE)
        val appContext = applicationContext

        if (!loginSharedPreferences.contains("email") && !loginSharedPreferences.contains("password")){

            val activityIntent = Intent(appContext, LoginScreenActivity::class.java)
            startActivity(activityIntent)
        }
        else{

            homeScreenActivityBinding = ActivityHomeScreenBinding.inflate(layoutInflater)
            setContentView(homeScreenActivityBinding.root)

            val userName: String? = loginSharedPreferences.getString("email", null)

            if (userName != null){

                homeScreenActivityBinding.userNameText.text = userName.split("@")[0]
            }

            val jsonObject = JSONObject(getJSON("news.json"))

            var newsDataList = getNews(jsonObject)



            homeScreenActivityBinding.newsRecyclerLayout.adapter = NewsAdapter(newsDataList)


            val logoutButton = findViewById<Button>(R.id.logoutButton)
            logoutButton.setOnClickListener {
                showLogoutDialog(loginSharedPreferences)
            }
        }

    }

    private fun getNews(jsonObject: JSONObject): ArrayList<NewsTitle> {

        val newsArrayList: ArrayList<NewsTitle> = ArrayList()
        val jsonArray = jsonObject.getJSONArray("data")

        for(index in 0 until jsonArray.length()){
            val news = jsonArray.getJSONObject(index)

            newsArrayList.add(
                NewsTitle(
                    news.getString("title"),
                    news.getString("author"),
                    news.getString("date")
            )
            )
        }

        return  newsArrayList
    }

    private fun getJSON(fileName: String): String? {

        var json: String? = null

        try {
            val jsonFile = assets.open(fileName)
            json = jsonFile.bufferedReader().use { it.readText() }
            return json
        }
        catch (exception: IOException){
            exception.printStackTrace()
            return null
        }

    }

    private fun logOut(sharedPreferences: SharedPreferences) {
        val loginDetailEditor = sharedPreferences.edit()
        loginDetailEditor.clear()
        loginDetailEditor.apply()
        val activityIntent = Intent(applicationContext, LoginScreenActivity::class.java)
        startActivity(activityIntent)
    }

    private fun showLogoutDialog(sharedDetails: SharedPreferences){

        val logoutConfirmAlert = AlertDialog.Builder(this)
        logoutConfirmAlert.setMessage("Press Confirm to Logout")
        logoutConfirmAlert.setPositiveButton("Confirm"){ _, _ -> logOut(sharedDetails) }
        logoutConfirmAlert.setNegativeButton("Cancel"){ dialog, _ -> dialog.dismiss() }
        logoutConfirmAlert.show()
    }

    override fun onBackPressed() {

        val exitConfirm = AlertDialog.Builder(this)
        exitConfirm.setMessage("Do you want to exit?")
        exitConfirm.setPositiveButton("Confirm"){ _, _ -> finishAffinity() }
        exitConfirm.setNegativeButton("Cancel"){ dialog, _ -> dialog.cancel() }
        exitConfirm.show()
    }

}