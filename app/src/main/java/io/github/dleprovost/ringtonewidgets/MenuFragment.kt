package io.github.dleprovost.ringtonewidgets

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.dleprovost.ringtonewidgets.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MenuAdapter
    private var defaultColor = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val typedValue = TypedValue()
        requireContext().theme.resolveAttribute(android.R.attr.colorActivatedHighlight, typedValue, true)
        defaultColor = typedValue.data
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MenuAdapter(getMenuItems()) { item ->
            if (item.isColor) {
                val action = MenuFragmentDirections.actionMenuFragmentToColorFragment(item.colorId!!)
                findNavController().navigate(action)
            } else {
                val action = MenuFragmentDirections.actionMenuFragmentToAboutFragment()
                findNavController().navigate(action)
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_main, menu)
            }

            override fun onMenuItemSelected(menuItem: android.view.MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_quit -> {
                        requireActivity().finish()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        if (!PermissionHelper.hasDndPermission(requireContext())) {
            findNavController().navigate(R.id.permissionFragment)
        }
    }

    override fun onResume() {
        super.onResume()

        adapter.updateItems(getMenuItems())

        val context = requireContext()
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        val selectedColor = ColorStorage.getColor(context, "selected")
        val unselectedColor = ColorStorage.getColor(context, "unselected")

        val selectedIndex = when (audioManager.ringerMode) {
            AudioManager.RINGER_MODE_NORMAL -> 0
            AudioManager.RINGER_MODE_VIBRATE -> 1
            AudioManager.RINGER_MODE_SILENT -> 2
            else -> -1
        }

        val icons = listOf(
            binding.previewIcon1,
            binding.previewIcon2,
            binding.previewIcon3
        )

        icons.forEachIndexed { index, icon ->
            icon.setColorFilter(if (index == selectedIndex) selectedColor else unselectedColor)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getMenuItems(): List<MenuItem> {
        return listOf(
            MenuItem(
                getString(R.string.selected_color),
                true,
                "selected",
                ColorStorage.getColor(requireContext(), "selected")
            ),
            MenuItem(
                getString(R.string.unselected_color),
                true,
                "unselected",
                ColorStorage.getColor(requireContext(), "unselected")
            ),
            MenuItem(getString(R.string.about), false)
        )
    }
}
