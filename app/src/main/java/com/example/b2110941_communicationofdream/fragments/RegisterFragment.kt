package com.example.b2110941_communicationofdream.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.b2110941_communicationofdream.R
import com.example.b2110941_communicationofdream.apis.Constants
import com.example.b2110941_communicationofdream.databinding.FragmentRegisterBinding
import com.example.b2110941_communicationofdream.models.RequestRegisterOrLogin
import com.example.b2110941_communicationofdream.sharepreferences.AppSharedPreferences
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterFragment : Fragment() {
    lateinit var binding: FragmentRegisterBinding
    lateinit var mAppSharedPreferences: AppSharedPreferences
    private var userName = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater,container,false)
        mAppSharedPreferences = AppSharedPreferences(requireContext())

        binding.apply {
            btnSignup.setOnClickListener {
                if (etUsername.text.isNotEmpty()){
                    userName = etUsername.text.toString().trim()
                    //Thực hiện call api đăng kí tài khoản
                    registerUser(userName)
                }else{
                    Snackbar.make(it,"Vui lòng nhập mã số sinh viên!!", Snackbar.LENGTH_LONG).show()
                }
            }
            tvLogin.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout,LoginFragment())
                    .commit()
            }
        }
        return binding.root
    }

    private fun registerUser(username:String){
        binding.apply {
            progressBar.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main){
                    val response = Constants.getInstance()
                        .registerUser(RequestRegisterOrLogin(username))
                        .body()
                    if(response != null){
                        if(response.success){
                            mAppSharedPreferences.putIdUser("idUser", response.idUser!!)
                            requireActivity().supportFragmentManager.beginTransaction()
                                .replace(R.id.frame_layout,WishListFragment()).commit()
                            progressBar.visibility = View.GONE
                        }
                        else
                        {
                            tvMessage.text = response.message
                            tvMessage.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                        }
                    }

                }
            }
        }
    }


}