package com.smartcare.ui.symptoms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.smartcare.R
import com.smartcare.databinding.FragmentSymptomCheckerBinding

class SymptomCheckerFragment : Fragment() {

    private var _binding: FragmentSymptomCheckerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SymptomCheckerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSymptomCheckerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCustomSymptomInput()
        setupAnalyzeButton()
        observeViewModel()
    }

    private fun setupCustomSymptomInput() {
        binding.btnAddCustom.setOnClickListener {
            val customSymptom = binding.etCustomSymptom.text?.toString()?.trim()
            if (!customSymptom.isNullOrEmpty()) {
                addCustomSymptom(customSymptom)
                binding.etCustomSymptom.text?.clear()
            } else {
                Toast.makeText(requireContext(), "Please enter a symptom", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addCustomSymptom(symptom: String) {
        val chip = Chip(requireContext()).apply {
            text = symptom
            isCheckable = true
            isChecked = true
            isCloseIconVisible = true
            setOnCloseIconClickListener {
                binding.chipGroupSymptoms.removeView(this)
            }
        }
        binding.chipGroupSymptoms.addView(chip)
    }

    private fun setupAnalyzeButton() {
        binding.btnAnalyze.setOnClickListener {
            val selectedSymptoms = mutableListOf<String>()
            for (i in 0 until binding.chipGroupSymptoms.childCount) {
                val chip = binding.chipGroupSymptoms.getChildAt(i) as? Chip
                if (chip?.isChecked == true) {
                    selectedSymptoms.add(chip.text.toString())
                }
            }
            if (selectedSymptoms.isEmpty()) {
                Toast.makeText(requireContext(), "Please select at least one symptom", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.analyzeSymptoms(selectedSymptoms)
        }
    }

    private fun observeViewModel() {
        viewModel.triageResult.observe(viewLifecycleOwner) { result ->
            result?.let {
                showTriageDialog(it)
                viewModel.onTriageResultShown() // reset the result
            }
        }
    }

    private fun showTriageDialog(message: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.action_analyze_symptoms))
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
