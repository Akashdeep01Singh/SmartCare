package com.smartcare.ui.records

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smartcare.databinding.ItemPrescriptionCardBinding
import com.smartcare.data.local.Prescription
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PrescriptionAdapter : ListAdapter<Prescription, PrescriptionAdapter.PrescriptionViewHolder>(PrescriptionDiffCallback()) {

    // Store expansion state by ID
    private val expandedIds = mutableSetOf<String>()

    inner class PrescriptionViewHolder(private val binding: ItemPrescriptionCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(prescription: Prescription) {
            binding.tvDoctorName.text = prescription.doctor_name

            // Format Timestamp
            val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            binding.tvDate.text = sdf.format(Date(prescription.date))

            // Build Bulleted List string from the list of medicines
            val bulletedList = prescription.medicine_list.joinToString(separator = "\n") { medicine ->
                "• $medicine"
            }
            binding.tvMedicineList.text = bulletedList

            // Expansion logic
            val isExpanded = expandedIds.contains(prescription.id)
            binding.llExpandedContent.visibility = if (isExpanded) View.VISIBLE else View.GONE
            binding.ivExpand.rotation = if (isExpanded) 180f else 0f

            // Handle Click
            binding.root.setOnClickListener {
                if (isExpanded) {
                    expandedIds.remove(prescription.id)
                } else {
                    expandedIds.add(prescription.id)
                }
                notifyItemChanged(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrescriptionViewHolder {
        val binding = ItemPrescriptionCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PrescriptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PrescriptionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PrescriptionDiffCallback : DiffUtil.ItemCallback<Prescription>() {
    override fun areItemsTheSame(oldItem: Prescription, newItem: Prescription): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Prescription, newItem: Prescription): Boolean {
        return oldItem == newItem
    }
}
