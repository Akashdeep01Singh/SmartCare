package com.smartcare.ui.pharmacy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartcare.databinding.FragmentPharmacyBinding

class PharmacyFragment : Fragment() {

    private var _binding: FragmentPharmacyBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PharmacyViewModel by viewModels()
    private lateinit var adapter: MedicineAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPharmacyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearchView()
        setupChipFilters()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = MedicineAdapter()
        binding.rvMedicines.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMedicines.adapter = adapter
    }

    private fun setupSearchView() {
        // Handle search queries
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.updateSearchQuery(query.orEmpty())
                binding.searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.updateSearchQuery(newText.orEmpty())
                return true
            }
        })
    }

    private fun setupChipFilters() {
        // Handle filter chip selections (All, In Stock, Out of Stock)
        binding.chipGroupFilters.setOnCheckedStateChangeListener { _, checkedIds ->
            if (checkedIds.isEmpty()) return@setOnCheckedStateChangeListener

            val filterMode = when (checkedIds.first()) {
                binding.chipInStock.id -> FilterMode.IN_STOCK
                binding.chipOutOfStock.id -> FilterMode.OUT_OF_STOCK
                else -> FilterMode.ALL // Default chip_all
            }
            viewModel.updateFilterMode(filterMode)

            // Scroll RV to top on new filter
            binding.rvMedicines.scrollToPosition(0)
        }
    }

    private fun observeViewModel() {
        viewModel.medicines.observe(viewLifecycleOwner) { medicinesList ->
            adapter.submitList(medicinesList)

            // Show Empty State if no medicines match search/filters
            if (medicinesList.isEmpty()) {
                binding.tvEmptyState.visibility = View.VISIBLE
                binding.rvMedicines.visibility = View.GONE
            } else {
                binding.tvEmptyState.visibility = View.GONE
                binding.rvMedicines.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
