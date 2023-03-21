package com.example.notetakingapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.example.notetakingapp.R
import com.example.notetakingapp.databinding.FragmentSignUpBinding
import com.example.notetakingapp.util.safeNavigate
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding

    private lateinit var firebaseAuth: FirebaseAuth

    lateinit var loginFragment: LoginFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentSignUpBinding.inflate(inflater, container, false)

        binding?.tvLoginAcc?.setOnClickListener {
            findNavController().safeNavigate(
                SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
            )
        }

        binding?.btnSignup?.setOnClickListener {
            val email = binding?.etSignupEmail?.text.toString()
            val password = binding?.etSignupPassword?.text.toString()
            val confirmPassword = binding?.etConfirmPassword?.text.toString()

            when {
                email.isEmpty() && password.isEmpty() && confirmPassword.isEmpty() -> {
                    Toast.makeText(requireActivity(), "Empty fields are not allowed!", Toast.LENGTH_LONG).show()
                }
                password != confirmPassword -> {
                    Toast.makeText(requireActivity(), "Passwords do not match!", Toast.LENGTH_LONG).show()
                }
                else -> {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful){
                            findNavController().safeNavigate(
                                SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
                            )
                        }else {
                            Toast.makeText(requireActivity(), it.exception.toString(), Toast.LENGTH_SHORT).show()
                            Log.e("Login-error", it.exception.toString())
                        }
                    }
                }
            }
        }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}