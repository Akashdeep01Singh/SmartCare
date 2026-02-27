package com.smartcare.ui.inventory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smartcare.R
import com.smartcare.databinding.ItemMedicineInventoryBinding

class MedicineInventoryAdapter(private var medicineList: List<MedicineItem>) :
    RecyclerView.Adapter<MedicineInventoryAdapter.InventoryViewHolder>() {

    fun updateList(newList: List<MedicineItem>) {
        medicineList = newList
        notifyDataSetChanged()
    }

    inner class InventoryViewHolder(private val binding: ItemMedicineInventoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(medicine: MedicineItem) {
            binding.tvMedicineName.text = medicine.name

            if (medicine.isAvailable) {
                // In Stock styling
                binding.chipAvailability.text = "ਭਰਪੂਰ"
                binding.chipAvailability.setChipBackgroundColorResource(R.color.teal_secondary)
            } else {
                // Out of Stock styling
                binding.chipAvailability.text = "ਖਤਮ"
                binding.chipAvailability.setChipBackgroundColorResource(R.color.red_alert)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
        val binding = ItemMedicineInventoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return InventoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
        holder.bind(medicineList[position])
    }

    override fun getItemCount(): Int = medicineList.size
}
