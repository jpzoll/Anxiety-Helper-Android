package com.example.projectbluebook

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(), WorksheetAdapter.OnWorksheetClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var sqliteHelper: SQLiteHelper

    private lateinit var adapter : WorksheetAdapter

    // Initialize SQl database
    //private var sqliteHelper = SQLiteHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        sqliteHelper = (activity as MainActivity).sqliteHelper

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //

        var wsList = sqliteHelper.getAllWorksheets()
        //var wsList = generateDummyList(10)
        //for (w in dummyList) { wsList += w }
        //var wsList = arrayListOf<Worksheet>(Worksheet(0, "The Time Is Now", "_ANSWER",
          //  arrayListOf("I am so grateful to be done with my Android Project. Project Bluebook ia amazing"),"12/8/21"))
//        for (i in wsList.indices) {
//            println("--------------")
//
//            val id = wsList[i].id
//            println("Worksheet $id")
//            println("Title : " + wsList[i].title)
//            for (k in wsList[i].answers.indices) {
//                val currentAnswer = k + 1
//                println("Answers [$currentAnswer] : " +  wsList[i].answers[k])
//            }
//
//            println("--------------")
//
//        }
        // Load all worksheets onto screen
        adapter = WorksheetAdapter(wsList, this)
        rv_worksheet.adapter = adapter
        rv_worksheet.layoutManager = LinearLayoutManager(activity)
        rv_worksheet.setHasFixedSize(true)

        // Load Home Fragment
        val createFragment = CreateWorksheetFragment()
        btn_create.setOnClickListener {
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.mainFrameLayout, createFragment)
            //transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onWorksheetClick(position: Int) {
        // Get worksheet from the SQL database to show detail of
        val wsList = sqliteHelper.getAllWorksheets()
        val w = wsList[position]
        // Pass worksheet into detail fragment
        val worksheetDetailFragment = CreateWorksheetFragment.newInstance(w)
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.mainFrameLayout, worksheetDetailFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onWorksheetClickDelete(position: Int) {
        // Get worksheet from the SQL database to delete
        var wsList = sqliteHelper.getAllWorksheets()
        val w = wsList[position]
        // Confirm the user the deletion with an Alert
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Are you sure you want to delete '${w.title}'?")
        builder.setCancelable(true)
        builder.setNegativeButton("No") {dialog, _ ->
            dialog.dismiss()
        }
        builder.setPositiveButton("Yes") {dialog, _ ->
            sqliteHelper.deleteWorksheetById(w.id)
            rv_worksheet.adapter = WorksheetAdapter(sqliteHelper.getAllWorksheets(), this)
            adapter.notifyItemRemoved(position)
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
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
}