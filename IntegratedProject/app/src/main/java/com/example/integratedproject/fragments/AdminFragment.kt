package com.example.integratedproject.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.integratedproject.AdminDBHelper
import com.example.integratedproject.AdminList
import com.example.integratedproject.R
import kotlinx.android.synthetic.main.activity_login.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdminFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var passwordText: EditText
    private lateinit var loginBtn: Button
    private lateinit var adminDbHelper: AdminDBHelper
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_login, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        passwordText=editTextTextPassword
        loginBtn=loginBtn
        adminDbHelper= AdminDBHelper(activity)
        /*if(adminDbHelper.getRowCount()>0L){
            val hashedPwd=adminDbHelper.getPassword()
            Log.d("pwd","hashedPwd$hashedPwd")
        }
        else{
            adminDbHelper.insertPassword("admin")
        }

        loginBtn.setOnClickListener {
            if(!passwordText.text.toString().isEmpty()){
                if(adminDbHelper.comparePassword(passwordText.text.toString())){
                    Toast.makeText(requireContext(), "Login Success!", Toast.LENGTH_SHORT).show()
                    val intent= Intent(requireContext(), AdminList::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(requireContext(), "Wrong Password!", Toast.LENGTH_SHORT).show()
                }
            }
        }*/
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AdminFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdminFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}