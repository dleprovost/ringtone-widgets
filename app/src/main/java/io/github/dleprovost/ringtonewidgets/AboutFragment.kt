package io.github.dleprovost.ringtonewidgets

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.github.dleprovost.ringtonewidgets.databinding.FragmentAboutBinding


class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)

        val version = try {
            val pInfo = requireContext().packageManager.getPackageInfo(requireContext().packageName, 0)
            pInfo.versionName
        } catch (e: Exception) {
            "?"
        }

        val githubUrl = getString(R.string.github_url)

        binding.aboutVersion.text = "Version $version"
        binding.aboutDescription.text = getString(R.string.about_description)
        binding.aboutLimitations.text = "⚠️ ${getString(R.string.about_limitations)}"
        binding.aboutThanks.text = getString(R.string.about_thanks)
        binding.aboutContrib.text = "${getString(R.string.about_contrib)}\n$githubUrl"
        binding.aboutContrib.movementMethod = LinkMovementMethod.getInstance()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
