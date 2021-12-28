package com.example.projectbluebook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_create_worksheet.*
import kotlinx.android.synthetic.main.worksheet_item.*
import java.util.*
import kotlin.collections.ArrayList
import java.io.Serializable
import kotlin.math.atan2

class CreateWorksheetActivity : AppCompatActivity() {

    // Initialize SQLite Database
    private lateinit var sqliteHelper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_worksheet)

        // Import Potential Data from CreateWorkSheetActivity
        var w: Worksheet
        var isNewWorksheet = true
        val editableWorksheet : Serializable? = intent.getSerializableExtra("EXTRA_WORKSHEET")
//        if (editableWorksheet is Worksheet){
//            isNewWorksheet = false
//            w = editableWorksheet
//            et_worksheet_title.setText(editableWorksheet.title)
//            et_a1.setText(editableWorksheet.answers[0])
//            et_a2.setText(editableWorksheet.answers[1])
//            et_a3.setText(editableWorksheet.answers[2])
//            et_a4.setText(editableWorksheet.answers[3])
//            et_a5.setText(editableWorksheet.answers[4])
//            et_a6.setText(editableWorksheet.answers[5])
//
//            // Worksheet Submission (Update / Create New)
//            btn_submit.setOnClickListener {
//                updateWorksheet(w)
//            }
//        } else {
//            btn_submit.setOnClickListener {
//                submitWorksheet()
//            }

        // SQLite Database
        //sqliteHelper = SQLiteHelper(this)
    }



    private fun submitWorksheet() {
//        val allWorksheets = sqliteHelper.getAllWorksheets()
//        // Gathering data from views
//        val name = et_worksheet_title.text.toString()
//
//        // Gathering answers
//
//        val a1 = et_a1.text.toString()
//        val a2 = et_a2.text.toString()
//        val a3 = et_a3.text.toString()
//        val a4 = et_a4.text.toString()
//        val a5 = et_a5.text.toString()
//        val a6 = et_a6.text.toString()
//
//        val answers = arrayListOf<String>(a1, a2, a3, a4, a5, a6)
//
//
//        val answer = et_a1.text.toString()
//        val date = "3/8/22"
//        // Worksheet instances
//        if (name.isEmpty()) {
//            Toast.makeText(this, "Please enter title.", Toast.LENGTH_SHORT).show()
//        } else {
//            val newWorksheet = Worksheet(id = allWorksheets.size, title = name, answer = answer, answers = answers, date = date)
//            val status = sqliteHelper.insertWorksheet(newWorksheet)
//            // Check insert status (success or failure)
//            if (status > -1) {
//                println("INSERT WORKSHEET SUCCESS ! :D")
//            } else {
//                println("insert worksheet failed.")
//            }
//            // Sending off
//            Intent(this, MainActivity::class.java).also {
//                it.putExtra("EXTRA_WORKSHEET", newWorksheet)
//                startActivity(it)
//            }
//        }
    }
    
    private fun updateWorksheet(w : Worksheet) {
//        Toast.makeText(this, "Worksheet updated!", Toast.LENGTH_SHORT).show()
//
//        // Gathering data from views
//        val name = et_worksheet_title.text.toString()
//
//        // Gathering answers
//
//        val a1 = et_a1.text.toString()
//        val a2 = et_a2.text.toString()
//        val a3 = et_a3.text.toString()
//        val a4 = et_a4.text.toString()
//        val a5 = et_a5.text.toString()
//        val a6 = et_a6.text.toString()
//
//
//        val answer = et_a1.text.toString()
//        val answers = arrayListOf<String>(a1, a2, a3, a4, a5, a6)
//        val date = "3/8/22"
//
//        val updatedWorksheet = Worksheet(id = w!!.id, title = name, answer = w.answer, answers, date)
//        val status = sqliteHelper.updateWorksheet(updatedWorksheet)
//
//        if (status > -1) {
//            println("UPDATE WORKSHEET SUCCESS ! :-D")
//        } else {
//            println("Update record failed.")
//        }
//
//        Intent(this, MainActivity::class.java).also {
//            it.putExtra("EXTRA_WORKSHEET", updatedWorksheet)
//            startActivity(it)
//        }
    }
}