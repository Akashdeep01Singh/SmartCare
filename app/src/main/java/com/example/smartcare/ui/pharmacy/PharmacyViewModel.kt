package com.smartcare.ui.pharmacy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Medicine(
    val id: String,
    val name: String,
    val inStock: Boolean,
    val lastUpdated: String
)

enum class FilterMode {
    ALL, IN_STOCK, OUT_OF_STOCK
}

class PharmacyViewModel : ViewModel() {

    // Mock Live Data representing Realtime Firebase DB
    private val _dbMedicines = listOf(
        Medicine("1", "Paracetamol 500mg (ਪੈਰਾਸੀਟਾਮੋਲ)", true, "10 mins ago"),
        Medicine("2", "Amoxicillin 250mg (ਅਮੌਕਸੀਸਿਲਿਨ)", false, "2 hours ago"),
        Medicine("3", "Cetirizine 10mg", true, "1 min ago"),
        Medicine("4", "Ibuprofen 400mg", true, "15 mins ago"),
        Medicine("5", "Azithromycin 500mg", false, "1 day ago"),
        Medicine("6", "Metformin 500mg", true, "5 mins ago"),
        Medicine("7", "Aspirin 75mg", true, "1 hour ago"),
        Medicine("8", "Salbutamol Inhaler", false, "5 hours ago")
    )

    private val _medicines = MutableLiveData<List<Medicine>>(_dbMedicines)
    val medicines: LiveData<List<Medicine>> = _medicines

    private var currentQuery = ""
    private var currentFilter = FilterMode.ALL

    fun updateSearchQuery(query: String) {
        currentQuery = query
        applyFilters()
    }

    fun updateFilterMode(mode: FilterMode) {
        currentFilter = mode
        applyFilters()
    }

    private fun applyFilters() {
        var filteredList = _dbMedicines

        // 1. Text Search
        if (currentQuery.isNotBlank()) {
            filteredList = filteredList.filter {
                it.name.contains(currentQuery, ignoreCase = true)
            }
        }

        // 2. Chip Filters
        filteredList = when (currentFilter) {
            FilterMode.ALL -> filteredList
            FilterMode.IN_STOCK -> filteredList.filter { it.inStock }
            FilterMode.OUT_OF_STOCK -> filteredList.filter { !it.inStock }
        }

        _medicines.value = filteredList
    }
}
