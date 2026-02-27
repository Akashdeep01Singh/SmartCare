package com.smartcare.ui.records

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartcare.data.local.HealthRecord
import com.smartcare.databinding.FragmentRecordsBinding
import java.util.UUID

class RecordsFragment : Fragment() {

    private var _binding: FragmentRecordsBinding? = null
    private val binding get() = _binding!!

    // By default, ViewModelFactory creates AndroidViewModel providing Application context
    private val viewModel: RecordsViewModel by viewModels()
    private lateinit var adapter: RecordAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecordsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
        setupMockAddButton()
    }

    private fun setupRecyclerView() {
        adapter = RecordAdapter()
        binding.rvRecords.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRecords.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.allRecords.observe(viewLifecycleOwner) { records ->
            adapter.submitList(records)

            if (records.isEmpty()) {
                binding.tvEmptyState.visibility = View.VISIBLE
                binding.rvRecords.visibility = View.GONE
            } else {
                binding.tvEmptyState.visibility = View.GONE
                binding.rvRecords.visibility = View.VISIBLE
            }
        }
    }

    private fun setupMockAddButton() {
        binding.fabAddMockRecord.setOnClickListener {
            val newRecord = HealthRecord(
                id = UUID.randomUUID().toString(),
                date = System.currentTimeMillis(),
                doctorName = "Dr. Kaur",
                prescriptionText = "Azithromycin 500mg, Rest for 2 days. Drink water.",
                diagnosis = "Viral Fever"
            )
            viewModel.insert(newRecord)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
