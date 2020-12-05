package com.example.integratedproject

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView

class RegistrationAdapter (private val context: Context,
                           private val dataSource: ArrayList<StudentInfoActivity.Registration>) : BaseAdapter()
    /*Filterable*/ {
    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var filteredDataSource=dataSource
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.registration_list, parent, false)
        val dateTextView=rowView.findViewById(R.id.date) as TextView
        val locationTextView=rowView.findViewById(R.id.location) as TextView
        val signaturePercentage=rowView.findViewById(R.id.signaturePercentage) as TextView
/*
        val signaturePadView=rowView.findViewById(R.id.signaturePad) as MyCanvasView
*/
        val percentage=(1..100).random()
        var percentageStr=SpannableString(percentage.toString()+"%")
        if(percentage<=33){
            percentageStr.setSpan(ForegroundColorSpan(Color.RED),0,percentageStr.length-1,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        else if(percentage in 34..65){
            percentageStr.setSpan(ForegroundColorSpan(Color.YELLOW),0,percentageStr.length-1,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        else if(percentage in 66..100){
            percentageStr.setSpan(ForegroundColorSpan(Color.GREEN),0,percentageStr.length-1,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        val registration=getItem(position) as StudentInfoActivity.Registration
        dateTextView.text=registration.date
        locationTextView.text=registration.location
        signaturePercentage.text=percentageStr
       /* var coordList=ArrayList<Float>()
        var test=registration.signature.trim().splitToSequence(',')
            .filter { it.isNotEmpty() }
            .toList()
        test.forEach{it->
            coordList.add(it.toFloat())
        }

        signaturePadView.Init(50,50)
        signaturePadView.Redraw(coordList)
*/
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
    /*override fun getFilter(): Filter {
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
    }*/

}