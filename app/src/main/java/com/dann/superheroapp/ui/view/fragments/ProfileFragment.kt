package com.dann.superheroapp.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.DialogFragment
import com.dann.superheroapp.R
import com.dann.superheroapp.databinding.FragmentProfileBinding
import com.dann.superheroapp.ui.view.dialogs.AddPhotoDialogFragment

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getImageFromGalery()
    }

    private fun getImageFromGalery(){
        binding.addPProfilePhoto.setOnClickListener {
            AddPhotoDialogFragment(
                onSubmitClickListener = { image ->
                    binding.imgPerfilLlena.setImageDrawable(image)
                }
            ).show(childFragmentManager,"addPhotoDialogFragment")
        }
    }

}