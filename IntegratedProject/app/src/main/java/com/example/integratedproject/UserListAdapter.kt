package com.example.integratedproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class UserListAdapter (private val context: Context,
                       private val dataSource: ArrayList<UserList.User>) : BaseAdapter(),Filterable {
    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var filteredDataSource=dataSource
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.student_list, parent, false)
        val nameTextView=rowView.findViewById(R.id.FirstText) as TextView
        val sNumberTextView=rowView.findViewById(R.id.SecondText) as TextView
       // val checkBoxStudent=rowView.findViewById(R.id.checkBoxStudent) as CheckBox

        val student=getItem(position) as UserList.User
        nameTextView.text=student.name
        sNumberTextView.text=student.studentNr


        return  rowView
    }

    override fun getItem(position: Int): Any {
        return filteredDataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return filteredDataSource.size
    }
    override fun getFilter():Filter{
        return object:Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val queryString = constraint?.toString()?.toLowerCase()

                val filterResults = FilterResults()
                filterResults.values = if (queryString==null || queryString.isEmpty())
                    dataSource
                else
                    dataSource.filter {
                        it.name.toLowerCase().contains(queryString)
                    }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredDataSource = results?.values as ArrayList<UserList.User>
                notifyDataSetChanged()
            }

        }
    }

}