package io.github.dleprovost.ringtonewidgets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.dleprovost.ringtonewidgets.databinding.ItemMenuBinding

class MenuAdapter(
    private var items: List<MenuItem>,
    private val onClick: (MenuItem) -> Unit
) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MenuItem) {
            binding.tvTitle.text = item.title

            if (item.isColor) {
                binding.colorPreview.setBackgroundColor(item.color)
                binding.colorPreview.visibility = View.VISIBLE
                binding.chevron.visibility = View.VISIBLE
            } else {
                binding.colorPreview.visibility = View.GONE
                binding.chevron.visibility = View.GONE
            }

            binding.root.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<MenuItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}
