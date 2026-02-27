package com.smartcare.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smartcare.databinding.ItemDashboardCardBinding

data class DashboardItem(
    val titleEn: String,
    val titlePa: String,
    val iconResId: Int,
    val destinationId: Int
)

class DashboardAdapter(
    private val items: List<DashboardItem>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>() {

    inner class DashboardViewHolder(private val binding: ItemDashboardCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DashboardItem) {
            binding.tvTitleEn.text = item.titleEn
            binding.tvTitlePa.text = item.titlePa
            binding.ivIcon.setImageResource(item.iconResId)

            binding.root.setOnClickListener {
                onItemClick(item.destinationId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val binding = ItemDashboardCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DashboardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
