package com.example.wevote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import android.widget.ListView


class createBlankFragment : Fragment() {
    private lateinit var db: DatabaseHandler
    private val dataList = ArrayList<DataModel>()

    private lateinit var addBtn: Button
    private lateinit var displayBtn: Button

    private lateinit var cid: EditText
    private lateinit var opt1countTextView: TextView
    private lateinit var opt2countTextView: TextView
    private lateinit var subjectEditText: EditText
    private lateinit var opt1nameEditText: EditText
    private lateinit var opt2nameEditText: EditText
    private lateinit var listView: ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listView = view.findViewById(R.id.listView)

        val db = DatabaseHandler(requireContext())

        addBtn = view.findViewById(R.id.addBtn)
        displayBtn = view.findViewById(R.id.display)

        cid = view.findViewById(R.id.c_id)
        opt1countTextView = view.findViewById(R.id.opt1count)
        opt2countTextView = view.findViewById(R.id.opt2count)

        subjectEditText = view.findViewById(R.id.c_sub)
        opt1nameEditText = view.findViewById(R.id.opt1name)
        opt2nameEditText = view.findViewById(R.id.opt2name)



        addBtn.setOnClickListener {
            val cid = cid.text.toString().toInt()
            val subject = subjectEditText.text.toString()
            val opt1name = opt1nameEditText.text.toString()
            val opt1count = opt1countTextView.text.toString().toInt()
            val opt2name = opt2nameEditText.text.toString()
            val opt2count = opt2countTextView.text.toString().toInt()


            val data = DataModel(cid, subject, opt1name, opt1count, opt2name, opt2count)

            val id = db.addData(data)
            if (id > 0) {

                subjectEditText.text.clear()
                opt1nameEditText.text.clear()
//                opt1countTextView.text = ""
                opt2nameEditText.text.clear()
//                opt2countTextView.text = ""
            }
        }

        displayBtn.setOnClickListener {
            dataList.clear()
            dataList.addAll(db.getAllData())
            val adapter = CustomListAdapter(requireContext(), dataList)
            listView.adapter = adapter
        }
    }
}
