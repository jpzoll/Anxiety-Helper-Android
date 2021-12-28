package com.example.projectbluebook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_create_worksheet.*
import kotlinx.android.synthetic.main.fragment_fears.*
import java.text.DateFormat
import java.text.FieldPosition
import java.util.*
import kotlin.random.Random

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FearsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FearsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var sqliteHelper: SQLiteHelper

    private lateinit var adapter: FearAdapter

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
        return inflater.inflate(R.layout.fragment_fears, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var fList = sqliteHelper.getAllFears()

        var fearList = mutableListOf<Fear>(
            Fear(1,"Public Speaking"),
            Fear(2,"Cold Approach"),
            Fear(3,"Sky Diving"),
            Fear(4,"Spiders"),
            Fear(5,"Heights"),
            Fear(6,"Going outside"),
            Fear(7,"Crowded Areas"),
            Fear(8,"Socializing")
        )

       val adapter = FearAdapter(fList)
        rv_fears.adapter = adapter
        rv_fears.layoutManager = LinearLayoutManager(activity)

        btn_add_fear.setOnClickListener {
            val fearName = et_fear.text.toString()
            val index = Random.nextInt()
            val newFear = Fear(fList.size + 1, fearName)
            //fearList.add(newFear)
            saveFear(newFear)
            adapter.notifyItemInserted(fList.size + 1)
        }
        btn_delete_fear.setOnClickListener {
            val adapter = FearAdapter(sqliteHelper.getAllFears())
            for (f in fList) {
                if (f.isSelected) {
                    removeFear(f)
                    //adapter.notifyDataSetChanged()
                }
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
         * @return A new instance of fragment FearsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FearsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun saveFear(f: Fear) {
        val title = et_fear.text.toString()
        if (title.isEmpty()) {
            Toast.makeText(activity, "Please enter title.", Toast.LENGTH_SHORT).show()
        } else {
            val newWorksheet = Worksheet(
                id = 2,//sqliteHelper2.getAllFears().size,
                title = title,
            )
            val status = sqliteHelper.insertFear(f)
            //rv_fears.adapter = FearAdapter(sqliteHelper.getAllFears(). this)
            // Check insert status (success or failure)
            if (status > -1) {
                println("INSERT WORKSHEET SUCCESS ! :D")
            } else {
                println("insert worksheet failed.")
            }
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.mainFrameLayout, FearsFragment())
                commit()
            }
        }
    }

    private fun removeFear(f: Fear) {
        var fList = sqliteHelper.getAllFears()

        sqliteHelper.deleteFearById(f.id)
        rv_fears.adapter = FearAdapter(sqliteHelper.getAllFears())
        //adapter.notifyItemRemoved(position)
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.mainFrameLayout, FearsFragment())
            commit()
        }

    }
}