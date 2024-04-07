package com.example.b2110941_communicationofdream.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.b2110941_communicationofdream.R
import com.example.b2110941_communicationofdream.databinding.FragmentLoginBinding
import com.example.b2110941_communicationofdream.sharepreferences.AppSharedPreferences
import com.example.b2110941_communicationofdream.apis.Constants
import com.example.b2110941_communicationofdream.models.RequestRegisterOrLogin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var mAppSharedPreferences: AppSharedPreferences
    private var username = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater,container,false)

        mAppSharedPreferences = AppSharedPreferences(requireContext())
        binding.apply {
            tvSignup.setOnClickListener{
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, RegisterFragment()).commit()
            }
            btnLogin.setOnClickListener {
                if (etUsername.text.isNotEmpty()){
                    username = etUsername.text.toString().trim()
                    loginUser(username)
                }
            }
        }
        return binding.root
    }

    private fun loginUser(username:String){
        binding.apply {
            progressBar.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main){
                    val response = Constants.getInstance()
                        .loginUser(RequestRegisterOrLogin(username)).body()
                    if (response != null){
                        if(response.success){
                            //Đăng nhập thành công
                            //Nhận idUser và thực hiện lưu vào sharedPreference
                            mAppSharedPreferences.putIdUser("idUser", response.idUser!!)
                            requireActivity().supportFragmentManager.beginTransaction()
                                .replace(R.id.frame_layout, WishListFragment())
                                .commit()
                            progressBar.visibility = View.GONE
                        }
                        else{
                            //Đăng nhập thất bại
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