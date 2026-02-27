package com.smartcare.ui.records

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartcare.data.local.Prescription
import com.smartcare.databinding.FragmentHealthRecordsBinding
import java.util.UUID

class HealthRecordsFragment : Fragment() {

    private var _binding: FragmentHealthRecordsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HealthRecordsViewModel by viewModels()
    private lateinit var adapter: PrescriptionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHealthRecordsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
        setupMockAddButton()
    }

    private fun setupRecyclerView() {
        adapter = PrescriptionAdapter()
        binding.rvPrescriptions.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPrescriptions.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.allPrescriptions.observe(viewLifecycleOwner) { records ->
            adapter.submitList(records)

            if (records.isEmpty()) {
                binding.tvEmptyState.visibility = View.VISIBLE
                binding.rvPrescriptions.visibility = View.GONE
            } else {
                binding.tvEmptyState.visibility = View.GONE
                binding.rvPrescriptions.visibility = View.VISIBLE
            }
        }
    }

    private fun setupMockAddButton() {
        binding.fabAddMockPrescription.setOnClickListener {
            val newRecord = Prescription(
                id = UUID.randomUUID().toString(),
                date = System.currentTimeMillis(),
                doctor_name = "Dr. Kaur (Nabha Civil Hospital)",
                medicine_list = listOf(
                    "Azithromycin 500mg (1-0-1)",
                    "Paracetamol 500mg (SOS)"
                )
            )
            viewModel.insertPrescription(newRecord)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
