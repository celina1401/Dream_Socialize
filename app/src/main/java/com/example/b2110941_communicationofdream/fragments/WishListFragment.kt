package com.example.b2110941_communicationofdream.fragments

import android.os.Bundle
import android.text.style.UpdateLayout
import com.example.b2110941_communicationofdream.apis.Constants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.b2110941_communicationofdream.R
import com.example.b2110941_communicationofdream.adapters.WishAdapter
import com.example.b2110941_communicationofdream.databinding.FragmentWishListBinding
import com.example.b2110941_communicationofdream.models.RequestDeleteWish
import com.example.b2110941_communicationofdream.models.Wish
import com.example.b2110941_communicationofdream.sharepreferences.AppSharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WishListFragment : Fragment(){
    private lateinit var binding: FragmentWishListBinding
    private lateinit var mWishList: ArrayList<Wish>
    private lateinit var mWishAdapter:WishAdapter
    private lateinit var mAppSharedPreferences: AppSharedPreferences
    private var idUser = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWishListBinding.inflate(layoutInflater,container,false)
        mAppSharedPreferences = AppSharedPreferences(requireActivity())
        idUser = mAppSharedPreferences.getIdUser("idUser").toString()

        //Init mWishList
        mWishList = ArrayList()

        //Call Api - getWishList()
        getWishList()

        binding.btnAdd.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout,AddFragment())
                .commit()
        }
        return binding.root
    }

    private fun getWishList(){
        binding.progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main){
                val response = Constants.getInstance().getWishList().body()
                if (response != null){
                    mWishList.addAll(response)
                    initAdapterAndSetLayout()
                }
            }
        }
    }

    private fun initAdapterAndSetLayout(){
        if (context == null){
            return
        }
        mWishAdapter = WishAdapter(requireActivity(), mWishList, mAppSharedPreferences,
            object : WishAdapter.IClickItemWish{
            override fun onClickUpdate(iWish: String, fullName: String, content: String) {
                mAppSharedPreferences.pushWish("idWish", iWish)
                mAppSharedPreferences.pushWish("fullname",fullName)
                mAppSharedPreferences.pushWish("content", content)
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout,UpdateFragment()).commit()
            }

            override fun onClickRemove(idWish: String) {
                deleteWish(idWish)
            }
        })
        binding.rcvWishList.adapter = mWishAdapter
        binding.rcvWishList.layoutManager = LinearLayoutManager(requireActivity())
        binding.progressBar.visibility = View.GONE
    }
    private fun deleteWish(idWish:String){
        binding.progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main){
                val response = Constants.getInstance()
                    .deleteWish(RequestDeleteWish(idUser,idWish)).body()
                if (response!=null){
                    if (response.success){
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, LoginFragment())
                            .commit()
                    }
                }
            }
        }
    }

}