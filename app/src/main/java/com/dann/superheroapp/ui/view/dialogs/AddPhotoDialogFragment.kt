package com.dann.superheroapp.ui.view.dialogs

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.dann.superheroapp.R
import com.dann.superheroapp.databinding.FragmentDialogAddBinding

class AddPhotoDialogFragment (
    private val onSubmitClickListener:(Drawable) -> Unit
):DialogFragment(){

    private var _binding: FragmentDialogAddBinding? = null
    private val binding get() = _binding!!
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogAddBinding.inflate(layoutInflater)
        dialog!!.window?.setBackgroundDrawableResource(R.color.transparent)

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.submitButton.setOnClickListener{
            if (binding.addImageOnContainer.drawable != null){
                onSubmitClickListener(binding.addImageOnContainer.drawable)
                dismiss()
            }
        }

        binding.containerAddFoto.setOnClickListener {
            loadImageFromGalry()
        }

        binding.addImage.setOnClickListener {
            loadImageFromGalry()
        }

        return binding.root
    }

    // Intent para abrir la galeria
    @SuppressLint("IntentReset")
    fun loadImageFromGalry() {
        val intent = Intent(
            Intent.ACTION_PICK
        )
        intent.type = "image/*"
        this.startActivityForResult(intent, 100)
    }

    // regresa la imagen seleccionada por el usuario
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            imageUri = data!!.data
            Glide.with(this)
                .load(imageUri)
                .transform(CenterCrop())
                .into(binding.addImageOnContainer)
            binding.addImage.visibility = View.VISIBLE
            binding.containerAddFoto.visibility = View.GONE
        }
    }

}