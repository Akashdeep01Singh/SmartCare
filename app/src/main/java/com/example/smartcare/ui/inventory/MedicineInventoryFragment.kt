package com.smartcare.ui.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartcare.databinding.FragmentMedicineInventoryBinding
import java.util.ArrayList

class MedicineInventoryFragment : Fragment() {

    private var _binding: FragmentMedicineInventoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MedicineInventoryAdapter
    private val fullMedicineList = ArrayList<MedicineItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMedicineInventoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        populateMockData()
        setupRecyclerView()
        setupSearchView()
    }

    private fun populateMockData() {
        fullMedicineList.apply {
            add(MedicineItem(name = "Paracetamol 500mg", isAvailable = true))
            add(MedicineItem(name = "Amoxicillin 250mg", isAvailable = false))
            add(MedicineItem(name = "Cetirizine 10mg", isAvailable = true))
            add(MedicineItem(name = "Ibuprofen 400mg", isAvailable = true))
            add(MedicineItem(name = "Azithromycin 500mg", isAvailable = false))
            add(MedicineItem(name = "Aspirin 75mg", isAvailable = true))
            add(MedicineItem(name = "Metformin 500mg", isAvailable = true))
            add(MedicineItem(name = "Salbutamol Inhaler", isAvailable = false))
        }
    }

    private fun setupRecyclerView() {
        adapter = MedicineInventoryAdapter(fullMedicineList)
        binding.rvInventory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvInventory.adapter = adapter
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterList(query)
                binding.searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })
    }

    private fun filterList(query: String?) {
        if (query.isNullOrBlank()) {
            adapter.updateList(fullMedicineList)
            binding.tvEmptyState.visibility = View.GONE
            binding.rvInventory.visibility = View.VISIBLE
            return
        }

        val filtered = fullMedicineList.filter { medicine ->
            medicine.name.contains(query, ignoreCase = true)
        }
        adapter.updateList(filtered)

        if (filtered.isEmpty()) {
            binding.tvEmptyState.visibility = View.VISIBLE
            binding.rvInventory.visibility = View.GONE
        } else {
            binding.tvEmptyState.visibility = View.GONE
            binding.rvInventory.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
