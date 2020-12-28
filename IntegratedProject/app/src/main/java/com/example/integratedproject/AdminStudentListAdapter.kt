package com.example.integratedproject

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class AdminStudentListAdapter(private val context: Context,
                              private val dataSource: ArrayList<AdminList.Student>): BaseAdapter(), Filterable {
    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var filteredDataSource=dataSource
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.admin_student_list, parent, false)
        val nameTextView=rowView.findViewById(R.id.FirstText) as TextView
        val sNumberTextView=rowView.findViewById(R.id.SecondText) as TextView
        val deleteBtn=rowView.findViewById(R.id.DeleteBtn) as Button
        val student=getItem(position) as AdminList.Student
        nameTextView.text=student.name
        sNumberTextView.text="S"+student.studentNr
        deleteBtn.setOnClickListener {
            filteredDataSource.removeAt(position)
            Log.d("delete","telede")
            var dbHelper=DataBaseHandler(context)
            dbHelper.deleteStudent(student.studentNr)
            notifyDataSetChanged()
        }
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
    override fun getFilter(): Filter {
        return object: Filter(){
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
                filteredDataSource = results?.values as ArrayList<AdminList.Student>
                notifyDataSetChanged()
            }
        }
    }
}