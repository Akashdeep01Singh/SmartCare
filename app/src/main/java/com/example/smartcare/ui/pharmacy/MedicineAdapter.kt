package com.smartcare.ui.pharmacy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smartcare.R
import com.smartcare.databinding.ItemMedicineBinding

class MedicineAdapter : ListAdapter<Medicine, MedicineAdapter.MedicineViewHolder>(MedicineDiffCallback()) {

    inner class MedicineViewHolder(private val binding: ItemMedicineBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(medicine: Medicine) {
            binding.tvMedicineName.text = medicine.name
            binding.tvLastUpdated.text = "Updated: ${medicine.lastUpdated}"

            // Status Chip logic: Green (In Stock) vs Red (Out of Stock)
            if (medicine.inStock) {
                binding.chipStatus.text = "IN STOCK"
                binding.chipStatus.setChipBackgroundColorResource(R.color.teal_secondary)
            } else {
                binding.chipStatus.text = "OUT OF STOCK"
                binding.chipStatus.setChipBackgroundColorResource(R.color.red_alert)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val binding = ItemMedicineBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MedicineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class MedicineDiffCallback : DiffUtil.ItemCallback<Medicine>() {
    override fun areItemsTheSame(oldItem: Medicine, newItem: Medicine): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Medicine, newItem: Medicine): Boolean {
        return oldItem == newItem
    }
}
