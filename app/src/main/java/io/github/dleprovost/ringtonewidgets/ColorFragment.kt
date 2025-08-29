package io.github.dleprovost.ringtonewidgets

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.github.dleprovost.ringtonewidgets.databinding.FragmentColorBinding
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener

class ColorFragment : Fragment() {

    private var _binding: FragmentColorBinding? = null
    private val binding get() = _binding!!

    private val colorId by lazy {
        ColorFragmentArgs.fromBundle(requireArguments()).colorId
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentColorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var currentColor = ColorStorage.getColor(requireContext(), colorId)

        val preview = binding.preview
        val colorPicker = binding.colorPicker
        val brightnessSlideBar = binding.brightnessSlide
        colorPicker.attachBrightnessSlider(brightnessSlideBar)
        val redComponent = binding.redBar
        val greenComponent = binding.greenBar
        val blueComponent = binding.blueBar
        val btnApply = binding.btnApply
        var updatePicker = true

        colorPicker.setColorListener(object : ColorEnvelopeListener {
            override fun onColorSelected(envelope: ColorEnvelope, fromUser: Boolean) {
                updatePicker = false
                currentColor = envelope.color
                redComponent.progress = (currentColor shr 16) and 0xFF
                greenComponent.progress = (currentColor shr 8) and 0xFF
                blueComponent.progress = currentColor and 0xFF
                preview.setBackgroundColor(currentColor)
                updatePicker = true
            }
        })

        val channelListener = object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (updatePicker) {
                    currentColor = Color.rgb(
                        redComponent.progress,
                        greenComponent.progress,
                        blueComponent.progress
                    )
                    colorPicker.setInitialColor(currentColor)
                    preview.setBackgroundColor(currentColor)
                }
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        }

        redComponent.setOnSeekBarChangeListener(channelListener)
        greenComponent.setOnSeekBarChangeListener(channelListener)
        blueComponent.setOnSeekBarChangeListener(channelListener)

        colorPicker.setInitialColor(currentColor)

        btnApply.setOnClickListener {
            val context = requireContext()
            ColorStorage.saveColor(context, colorId, currentColor)
            RingtoneWidgetProvider.updateAllWidgets(context)
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
