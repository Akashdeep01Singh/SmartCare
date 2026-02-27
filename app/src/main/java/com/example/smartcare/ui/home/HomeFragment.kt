package com.smartcare.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.smartcare.R
import com.smartcare.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {

        val dashboardItems = listOf(
            DashboardItem(
                titleEn = "Check Symptoms",
                titlePa = "ਲੱਛਣਾਂ ਦੀ ਜਾਂਚ",
                iconResId = R.drawable.baseline_15mp_24,
                destinationId = R.id.navigation_telemedicine
            ),
            DashboardItem(
                titleEn = "Consult Doctor",
                titlePa = "ਡਾਕਟਰ ਦੀ ਸਲਾਹ",
                iconResId = R.drawable.baseline_15mp_24,
                destinationId = R.id.navigation_telemedicine
            ),
            DashboardItem(
                titleEn = "Medicine Stock",
                titlePa = "ਦਵਾਈ ਦਾ ਸਟਾਕ",
                iconResId = R.drawable.baseline_15mp_24,
                destinationId = R.id.navigation_pharmacy
            ),
            DashboardItem(
                titleEn = "My Records",
                titlePa = "ਮੇਰੇ ਰਿਕਾਰਡ",
                iconResId = R.drawable.baseline_15mp_24,
                destinationId = R.id.navigation_records
            )
        )

        val adapter = DashboardAdapter(dashboardItems) { destinationId ->
            findNavController().navigate(destinationId)
        }

        binding.rvDashboard.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvDashboard.adapter = adapter
        binding.rvDashboard.isNestedScrollingEnabled = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}