package com.example.hbapplicationgroupa.ui

import android.os.Bundle
 import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
  import androidx.navigation.fragment.findNavController
 import com.example.hbapplicationgroupa.R
import com.example.hbapplicationgroupa.databinding.FragmentForgotPasswordBinding
import com.example.hbapplicationgroupa.utils.ValidateEmail
import com.example.hbapplicationgroupa.viewModel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment () {
    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!

    val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnForgotPassword.setOnClickListener {
            val userEmail = binding.tvEmailTextForgotpassword.text.toString()

            // check if the email is valid email address
            if (!ValidateEmail.isEmailValid(userEmail)){
                binding.tvEmailTextForgotpassword.error = "please enter a valid email"
            }else{
                viewModel.sendForgortPasswordEmail(userEmail)
                viewModel.forgotPasswordEmail.observe(viewLifecycleOwner, Observer {
                    val response = viewModel.forgotPasswordEmail.value
                    Toast.makeText(context, "$response", Toast.LENGTH_LONG).show()
                })
            }
        }

        binding.tvForgotPasswordLoginText.setOnClickListener {
            findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
        }

        onBackPressed()
    }

    fun onBackPressed(){
        //Overriding onBack press to handle back press
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }
}