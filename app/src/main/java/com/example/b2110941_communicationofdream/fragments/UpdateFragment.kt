package com.example.b2110941_communicationofdream.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.b2110941_communicationofdream.R
import com.example.b2110941_communicationofdream.apis.Constants
import com.example.b2110941_communicationofdream.databinding.FragmentUpdateBinding
import com.example.b2110941_communicationofdream.models.RequestUpdateWish
import com.example.b2110941_communicationofdream.sharepreferences.AppSharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateFragment : Fragment() {
    private lateinit var binding: FragmentUpdateBinding
    private lateinit var mAppSharedPreferences: AppSharedPreferences
    private var idUser = ""
    private var idWish = ""
    private var fullName = ""
    private var content = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateBinding.inflate(layoutInflater,container,false)

        mAppSharedPreferences = AppSharedPreferences(requireContext())
        idUser = mAppSharedPreferences.getIdUser("idUser").toString()
        idWish = mAppSharedPreferences.getWish("idWish").toString()
        fullName = mAppSharedPreferences.getWish("fullname").toString()
        content = mAppSharedPreferences.getWish("content").toString()

        //Thiet lap nd len giao dien
        binding.edtFullName.setText(fullName)
        binding.edtContent.setText(content)

        binding.apply {
            btnSave.setOnClickListener {
                fullName = edtFullName.text.toString().trim()
                content = edtContent.text.toString().trim()
                updateWish(fullName,content)
            }
        }
        return binding.root
    }

    private fun updateWish(fullname:String, content:String){
        binding.progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main){
                val response = Constants.getInstance()
                    .updateWish(RequestUpdateWish(idUser,idWish,fullName,content)).body()
                if (response != null){
                    if (response.success){
                        binding.progressBar.visibility = View.VISIBLE
                        Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT)
                            .show()
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout,WishListFragment()).commit()
                    }
                }
            }
        }
    }

}