package com.example.projectbluebook

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.Serializable
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), WorksheetAdapter.OnWorksheetClickListener {

    // Initialize SQl database
    var sqliteHelper = SQLiteHelper(this)
    //var sqLiteHelper2 = SQLiteHelper2(this)
    // Generate ArrayList from SQL Database information

    // Adapter
    //private val adapter = WorksheetAdapter(wsList.reversed(), this)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Action Bar
        supportActionBar!!.title = "Project Bluebook"

        // Import Potential Data from CreateWorkSheetActivity
        //var w: Worksheet
        //val newWorksheet : Serializable? = intent.getSerializableExtra("EXTRA_WORKSHEET")
        // Recycler View (Worksheets)
        //var myList = generateDummyList(0)
        //if (newWorksheet is Worksheet){
        //    w = newWorksheet as Worksheet
        //    myList.add(0, w)
        //}

        var wsList = sqliteHelper.getAllWorksheets()
        //var fList = sqLiteHelper2.getAllFears()
        //var wsList = generateDummyList(30)
        for (i in wsList.indices) {
            println("--------------")

            val id = wsList[i].id
            println("Worksheet $id")
            println("Title : " + wsList[i].title)
            for (k in wsList[i].answers.indices) {
                val currentAnswer = k + 1
                println("Answers [$currentAnswer] : " +  wsList[i].answers[k])
            }

            println("--------------")

        }

        // Bottom Nav Bar Functionality
        val homeFragment = HomeFragment()
        val fearsFragment = FearsFragment()
        val resourcesFragment = ResourcesFragment()
        // Initially, we start at the home screen / fragment
        setCurrentFragment(homeFragment)

        nav_menu.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.i_home -> setCurrentFragment(homeFragment)
                R.id.i_fears -> setCurrentFragment(fearsFragment)
                R.id.i_resources -> {
                    //setCurrentFragment(resourcesFragment)
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage("What type of resource are you interested in?")
                    builder.setCancelable(true)
                    builder.setNegativeButton("YouTube Video") {dialog, _ ->
                        Intent(this, YouTubeActivity::class.java).also {
                            startActivity(it)
                        }
                        dialog.dismiss()
                    }
                    builder.setPositiveButton("Therapy Search") {dialog, _ ->
                        Intent(this, MapsActivity::class.java).also {
                            startActivity(it)
                        }
                        dialog.dismiss()
                    }
                    val alert = builder.create()
                    alert.show()
                }
            }
            true
        }


    }

    private fun setCurrentFragment(f: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainFrameLayout, f)
            commit()
        }
    }

    override fun onWorksheetClick(position: Int) {
        println("Worksheet $position clicked.")
        val wsList = sqliteHelper.getAllWorksheets()
        Intent(this, CreateWorksheetActivity::class.java).also {
            it.putExtra("EXTRA_WORKSHEET", wsList[position])
            println("---------------")
            println("position : " + position)
            println("id : " + wsList[position].id)
            println("Worksheet Title : " + wsList[position].title)
            println("---------------")
            startActivity(it)
        }
        //val clickedWorksheet = wsList[position]
        //clickedWorksheet.title = "HEALING VULNERABILITY"
        //adapter.notifyItemChanged(position)
    }

    override fun onWorksheetClickDelete(position: Int) {
        Toast.makeText(this, "DELETE : $position" , Toast.LENGTH_SHORT).show()
    }

    private fun generateDummyList(size: Int): ArrayList<Worksheet> {

        val list = ArrayList<Worksheet>()
        val myAnswers = arrayListOf<String>("a1", "a2", "a3", "a4", "a5", "a6")
        // get the date
        val calendar = Calendar.getInstance()
        val currentDate = DateFormat.getDateInstance().format(calendar.time)

        for (i in 1 until size) {
            val currentItem = Worksheet(i, "Worksheet $i", myAnswers, currentDate)
            list += currentItem
        }

        return list
    }

    private fun deleteWorksheet(position: Int) {
        val wsList = sqliteHelper.getAllWorksheets()
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete item?")
        builder.setCancelable(true)
        builder.setNegativeButton("No") {dialog, _ ->
            dialog.dismiss()
        }
        builder.setPositiveButton("Yes") {dialog, _ ->
            sqliteHelper.deleteWorksheetById(wsList[position].id)
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        
        when (item.itemId) {
            R.id.i_profile -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.mainFrameLayout, ProfileFragment(), "Profile Fragment")
                    .addToBackStack(null)
                    .commit()
            }
            R.id.i_about -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.mainFrameLayout, AboutFragment(), "About Fragment")
                    .addToBackStack(null)
                    .commit()
            }
            R.id.i_settings -> Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show()
        }
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        return true
    }

    private fun onSupportNavigateUpd(): Boolean {
        onBackPressed()
        Toast.makeText(this, "onSupportNavigateUp", Toast.LENGTH_SHORT).show()
        println("onSupportNavigateUp")
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        println("onSupport.......................")
        return super.onSupportNavigateUp()
    }







}