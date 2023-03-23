package com.example.notetakingapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.notetakingapp.HomeFragment
import com.example.notetakingapp.R
import com.example.notetakingapp.database.NoteDAO
import com.example.notetakingapp.database.NoteDatabase
import com.example.notetakingapp.databinding.FragmentLoginBinding
import com.example.notetakingapp.util.safeNavigate
import com.example.notetakingapp.viewmodel.NoteViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding

    private lateinit var notesViewModel: NoteViewModel
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding?.tvSignUp?.setOnClickListener {
            findNavController().safeNavigate(
                LoginFragmentDirections.actionLoginFragmentToSignUpPage()
            )
        }

        binding?.btnLogin?.setOnClickListener {
            val email = binding?.etEmail?.text.toString()
            val password = binding?.etPassword?.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful){
                        if (firebaseAuth.currentUser?.uid != null){
                            notesViewModel.getAllNotesByUserId(firebaseAuth.currentUser!!.uid)
                            findNavController().safeNavigate(
                                LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                            )
                        }
//                        findNavController().safeNavigate(
//                            LoginFragmentDirections.actionLoginFragmentToHomeFragment()
//                        )
                    }else {
                        Toast.makeText(requireActivity(), it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireActivity(), "Empty fields are not allowed!", Toast.LENGTH_LONG).show()
            }
        }
        // Inflate the layout for this fragment
        return binding?.root
    }


    //this is to check if there is a user logged in already to login automatically else after signup, user goes to login page
    override fun onStart() {

        super.onStart()
        if (firebaseAuth.currentUser != null) {
//            firebaseAuth.currentUser?.getIdToken(true)
            findNavController().safeNavigate(
                LoginFragmentDirections.actionLoginFragmentToHomeFragment()
            )
        }else {
            findNavController().safeNavigate(
                SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
            )
        }

    }

}




