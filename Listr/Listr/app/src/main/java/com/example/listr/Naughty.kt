package com.example.listr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.listr.databinding.FragmentNaughtyBinding
import com.google.firebase.firestore.FirebaseFirestore

class Naughty : Fragment() {

    private var _binding: FragmentNaughtyBinding? = null

    private val binding get() = _binding!!

    private var firestoreInstance : FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activity = requireActivity()

        firestoreInstance = (activity as? MainActivity)?.getFirestoreInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNaughtyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.button2.setOnClickListener {
            savetoNaughtyCollection(binding.editText2.text.toString())
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun savetoNaughtyCollection(data: String) {
        val dataToSave: MutableMap<String, Any> = HashMap()
        dataToSave["naughty_data"] = data

        firestoreInstance?.collection("naughty")?.add(dataToSave)?.addOnSuccessListener { documentReference ->

            Toast.makeText(requireContext(), "Data saved successfully!", Toast.LENGTH_SHORT ).show()

        }?.addOnFailureListener { e ->
            Toast.makeText(requireContext(), "Error saving data", Toast.LENGTH_SHORT ).show()
        }
    }
}