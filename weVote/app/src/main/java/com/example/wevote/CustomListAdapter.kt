package com.example.wevote

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CustomListAdapter(
    private val context: Context,
    private var dataList: List<DataModel>
) : BaseAdapter() {

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): Any {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val listItemView = inflater.inflate(R.layout.list_item, null)



        val votingid = listItemView.findViewById<TextView>(R.id.votingid)
        val votingSubjectTextView = listItemView.findViewById<TextView>(R.id.votingSubjectTextView)
        val option1NameTextView = listItemView.findViewById<TextView>(R.id.option1NameTextView)
        val option1CountTextView = listItemView.findViewById<TextView>(R.id.option1CountTextView)
        val option2NameTextView = listItemView.findViewById<TextView>(R.id.option2NameTextView)
        val option2CountTextView = listItemView.findViewById<TextView>(R.id.option2CountTextView)
        val refreshImage = listItemView.findViewById<ImageView>(R.id.refreshimage) // Add the ImageView for the refresh image

        val data = dataList[position]

        votingid.text = data.id.toString()
        votingSubjectTextView.text = data.subject
        option1NameTextView.text = data.opt1name
        option1CountTextView.text = data.opt1count.toString()
        option2NameTextView.text = data.opt2name
        option2CountTextView.text = data.opt2count.toString()

        refreshImage.setOnClickListener {
            val dbHelper = DatabaseHandler(context)
            val updatedData = dbHelper.getAllData()
            updateData(updatedData)

            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout: View = inflater.inflate(R.layout.refresh_toast, null)

            val toast = Toast(context)
            toast.setGravity(Gravity.BOTTOM, 0, 300)
            toast.view = layout
            toast.duration = Toast.LENGTH_SHORT
            toast.show()
        }

        return listItemView
    }
    fun updateData(newData: List<DataModel>) {
        dataList = newData
        notifyDataSetChanged() // Notify the adapter of the data change
    }
}
