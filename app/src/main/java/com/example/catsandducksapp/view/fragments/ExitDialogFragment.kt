package com.example.catsandducksapp.view.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.DialogFragment
import com.example.catsandducksapp.R

class ExitDialogFragment : DialogFragment() {

    companion object {
        const val TAG = "ExitDialog"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.exit_dialog_title))
            .setPositiveButton(getString(R.string.exit_dialog_positive_button_text)) { _, _ ->
                finishAffinity(requireActivity())
            }
            .setNegativeButton(getString(R.string.exit_dialog_negative_button_text)) { _, _ ->
                dismiss()
            }
            .create()
    }

}