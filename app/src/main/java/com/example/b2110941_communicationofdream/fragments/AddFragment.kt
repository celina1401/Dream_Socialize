package com.example.b2110941_communicationofdream.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.b2110941_communicationofdream.R
import com.example.b2110941_communicationofdream.databinding.FragmentAddBinding
import com.example.b2110941_communicationofdream.sharepreferences.AppSharedPreferences
import com.example.b2110941_communicationofdream.apis.Constants
import com.example.b2110941_communicationofdream.models.RequestAddWish
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddFragment : Fragment() {
    private lateinit var binding:FragmentAddBinding
    private lateinit var mAppSharedPreferences: AppSharedPreferences
    private var idUser = ""
    private var fullName = ""
    private var content = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(layoutInflater,container,false)
        mAppSharedPreferences = AppSharedPreferences(requireActivity())
        idUser = mAppSharedPreferences.getIdUser("idUser").toString()

        binding.apply {
            btnSave.setOnClickListener {
                if (edtFullName.text.isNotEmpty() && edtContent.text.isNotEmpty()){
                    fullName = edtFullName.text.toString().trim()
                    content = edtContent.text.toString().trim()

                    addWish(fullName,content)
                }
            }
        }
        return binding.root
    }

    private fun addWish(fullname:String, content:String){
        binding.progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main){
                val response = Constants.getInstance()
                    .addWish(RequestAddWish(idUser,fullname,content)).body()
                if (response != null){
                    if (response.success){
                        binding.progressBar.visibility = View.VISIBLE
                        Toast.makeText(requireContext(),response.message,Toast.LENGTH_SHORT).show()
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout,WishListFragment())
                            .commit()
                    }
                    else{
                        binding.progressBar.visibility = View.GONE
                        //Them dieu uoc khong thanh cong
                        Toast.makeText(requireContext(), response.message,Toast.LENGTH_SHORT).show()
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, LoginFragment())
                            .commit()

                    }
                }
            }
        }
    }

}