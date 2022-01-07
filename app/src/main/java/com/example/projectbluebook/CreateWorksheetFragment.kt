package com.example.projectbluebook

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_create_worksheet.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.Serializable
import java.text.DateFormat
import java.util.*
import android.app.Activity.RESULT_OK
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateWorksheetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateWorksheetFragment : Fragment() {
    // TODO: Rename and change types of parameters

    // Arguments from SafeArgs Plugin
    private val args: CreateWorksheetFragmentArgs by navArgs()

    private var param1: Serializable? = null

    private lateinit var sqliteHelper: SQLiteHelper

    private var RQ_SPEECH_REC = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        sqliteHelper = (activity as MainActivity).sqliteHelper

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_worksheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Decide if we are CREATING or UPDATING a worksheet
        if (args.worksheet is Worksheet) {
            // Set Q&A Edit Text Fields to data retrieved from clicking on the worksheet in Home Fragment
            setTextFields(args.worksheet)
            // Worksheet Submission (Update / Create New)
            btn_submit.setOnClickListener {
                updateWorksheet(args.worksheet as Worksheet)
            }
        } else {
            btn_submit.setOnClickListener {
                submitWorksheet()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreateWorksheetFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Serializable) =
            CreateWorksheetFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }

    private fun setTextFields(w0: Serializable?) {
        if (w0 is Worksheet) {
            et_worksheet_title.setText(w0.title)
            et_a1.setText(w0.answers[0])
            et_a2.setText(w0.answers[1])
            et_a3.setText(w0.answers[2])
            et_a4.setText(w0.answers[3])
            et_a5.setText(w0.answers[4])
            et_a6.setText(w0.answers[5])
        }

    }

    private fun submitWorksheet() {
        //val allWorksheets = sqliteHelper.getAllWorksheets()
        // Gathering data from views
        val name = et_worksheet_title.text.toString()

        // Gathering answers

        val a1 = et_a1.text.toString()
        val a2 = et_a2.text.toString()
        val a3 = et_a3.text.toString()
        val a4 = et_a4.text.toString()
        val a5 = et_a5.text.toString()
        val a6 = et_a6.text.toString()

        val answers = arrayListOf<String>(a1, a2, a3, a4, a5, a6)

        val date = "3/8/22"
        // Get the date
        val calendar = Calendar.getInstance()
        val currentDate = DateFormat.getDateInstance().format(calendar.time)
        // Worksheet instances
        if (name.isEmpty()) {
            Toast.makeText(activity, "Please enter title.", Toast.LENGTH_SHORT).show()
        } else {
            val newWorksheet = Worksheet(
                id = sqliteHelper.getAllWorksheets().size,
                title = name,
                answers = answers,
                date = currentDate
            )
            val status = sqliteHelper.insertWorksheet(newWorksheet)
            // Check insert status (success or failure)
            if (status > -1) {
                println("INSERT WORKSHEET SUCCESS ! :D")
            } else {
                println("insert worksheet failed.")
            }
            // Sending off

//            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
//            parentFragmentManager.popBackStack()
//            transaction.replace(R.id.mainFrameLayout, HomeFragment(), "Home Fragment")
//            transaction.commit()

            // Navigating back to Home Fragment
            val action = CreateWorksheetFragmentDirections.actionCreateWorksheetFragmentToHomeFragment()
            findNavController().navigate(action)
        }
    }

    private fun updateWorksheet(w: Worksheet) {
        // Gathering data from views
        val name = et_worksheet_title.text.toString()

        // Gathering answers

        val a1 = et_a1.text.toString()
        val a2 = et_a2.text.toString()
        val a3 = et_a3.text.toString()
        val a4 = et_a4.text.toString()
        val a5 = et_a5.text.toString()
        val a6 = et_a6.text.toString()


        val answers = arrayListOf<String>(a1, a2, a3, a4, a5, a6)


        val updatedWorksheet = Worksheet(id = w!!.id, title = name, answers, date = w.date)
        val status = sqliteHelper.updateWorksheet(updatedWorksheet)

        if (status > -1) {
            println("UPDATE WORKSHEET SUCCESS ! :-D")
        } else {
            println("Update record failed.")
        }

//        val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
//        parentFragmentManager.popBackStack()
//        transaction.replace(R.id.mainFrameLayout, HomeFragment(), "Home Fragment")
//        transaction.commit()

        // Navigating back to Home Fragment
        val action = CreateWorksheetFragmentDirections.actionCreateWorksheetFragmentToHomeFragment()
        findNavController().navigate(action)
    }



}