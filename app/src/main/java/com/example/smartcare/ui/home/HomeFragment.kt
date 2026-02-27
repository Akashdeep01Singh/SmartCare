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
        // High-Quality Icons from Android or standard drawables
        val dashboardItems = listOf(
            DashboardItem(
                titleEn = getString(R.string.service_symptoms),
                titlePa = "ਲੱਛਣਾਂ ਦੀ ਜਾਂਚ", // Mocking bilingual API/DB response
                iconResId = android.R.drawable.ic_menu_search,
                destinationId = R.id.navigation_symptoms
            ),
            DashboardItem(
                titleEn = getString(R.string.service_telemedicine),
                titlePa = "ਡਾਕਟਰ ਦੀ ਸਲਾਹ",
                iconResId = android.R.drawable.ic_menu_camera,
                destinationId = R.id.navigation_telemedicine
            ),
            DashboardItem(
                titleEn = getString(R.string.service_stock),
                titlePa = "ਦਵਾਈ ਦਾ ਸਟਾਕ",
                iconResId = android.R.drawable.ic_menu_agenda,
                destinationId = R.id.navigation_pharmacy
            ),
            DashboardItem(
                titleEn = getString(R.string.service_records),
                titlePa = "ਮੇਰੇ ਰਿਕਾਰਡ",
                iconResId = android.R.drawable.ic_menu_save,
                destinationId = R.id.navigation_records
            )
        )

        setupLanguageSwitcher()

        val adapter = DashboardAdapter(dashboardItems) { destinationId ->
            // Use findNavController to navigate when a grid card is tapped
            findNavController().navigate(destinationId)
        }

        binding.rvDashboard.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvDashboard.adapter = adapter
        // Ensure RecyclerView doesn't scroll independently inside NestedScrollView
        binding.rvDashboard.isNestedScrollingEnabled = false
    }

    private fun setupLanguageSwitcher() {
        binding.ivLanguage.setOnClickListener {
            val languages = arrayOf(
                getString(R.string.language_english),
                getString(R.string.language_punjabi),
                getString(R.string.language_hindi)
            )
            val langCodes = arrayOf("en", "pa", "hi")

            val currentLang = com.smartcare.utils.LocaleHelper.getLanguage(requireContext())
            val checkedItem = langCodes.indexOf(currentLang).takeIf { it >= 0 } ?: 0

            androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.select_language))
                .setSingleChoiceItems(languages, checkedItem) { dialog, which ->
                    val selectedLang = langCodes[which]
                    if (selectedLang != currentLang) {
                        com.smartcare.utils.LocaleHelper.setLocale(requireContext(), selectedLang)
                        requireActivity().recreate() // Reload strings
                    }
                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
