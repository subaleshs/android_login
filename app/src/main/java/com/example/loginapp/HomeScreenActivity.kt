package com.example.loginapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.loginapp.databinding.ActivityHomeScreenBinding
import com.example.loginapp.fragments.AccountFragment
import com.example.loginapp.fragments.NewsFeedFragment
import org.json.JSONObject
import java.io.IOException

class HomeScreenActivity : AppCompatActivity() {

    private lateinit var homeScreenActivityBinding: ActivityHomeScreenBinding
    private lateinit var newsFeedFragment: NewsFeedFragment
    private lateinit var accountFragment: AccountFragment

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

//            val jsonObject = JSONObject(getJSON("news.json"))
//
//            val newsDataList = getNews(jsonObject)

            newsFeedFragment = NewsFeedFragment()
            accountFragment = AccountFragment(loginSharedPreferences, this)
            val viewPager = homeScreenActivityBinding.viewPagerBottomNav

            viewPager.adapter = SwipeViewAdapter(supportFragmentManager, lifecycle, newsFeedFragment, accountFragment)

//            if (savedInstanceState == null){
//                replaceFragment(newsFeedFragment)
//            }


            homeScreenActivityBinding.bottomNavigation.setOnNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.home -> {
                        viewPager.currentItem = 0
                    }
                    R.id.favorites->{
                        viewPager.currentItem = 1
                    }
                    R.id.account -> {
                        viewPager.currentItem = 2
                    }
                }
                true
            }
        }

    }

    private fun replaceFragment(fragment: Fragment) {

        val transaction = supportFragmentManager.beginTransaction()

        transaction.apply {
            replace(R.id.fragmentContainerView, fragment)
            addToBackStack("navigation")
            commit()
        }
    }

//    private fun getNews(jsonObject: JSONObject): ArrayList<NewsTitle> {
//
//        val newsArrayList: ArrayList<NewsTitle> = ArrayList()
//        val jsonArray = jsonObject.getJSONArray("data")
//
//        for(index in 0 until jsonArray.length()){
//            val news = jsonArray.getJSONObject(index)
//
//            newsArrayList.add(
//                NewsTitle(
//                    news.getString("title"),
//                    news.getString("author"),
//                    news.getString("date")
//            )
//            )
//        }
//
//        return  newsArrayList
//    }
//
//    private fun getJSON(fileName: String): String {
//
//        var json = "{}"
//
//        try {
//            val jsonFile = assets.open(fileName)
//            json =  jsonFile.bufferedReader().use { it.readText() }
//        }
//        catch (exception: IOException){
//            exception.printStackTrace()
//            return "{}"
//        }
//
//        return json
//
//    }

    override fun onBackPressed() {

        if(supportFragmentManager.backStackEntryCount <= 1){
            val exitConfirm = AlertDialog.Builder(this)
            exitConfirm.setMessage("Do you want to exit?")
            exitConfirm.setPositiveButton("Confirm"){ _, _ -> finishAffinity() }
            exitConfirm.setNegativeButton("Cancel"){ dialog, _ -> dialog.cancel() }
            exitConfirm.show()
        }
        else{
            super.onBackPressed()
        }
    }

}