package com.yoti.android.cryptocurrencychallenge.ui.assets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yoti.android.cryptocurrencychallenge.databinding.AssetItemBinding
import com.yoti.android.cryptocurrencychallenge.presentation.model.AssetUiItem

class AssetsAdapter : RecyclerView.Adapter<AssetItemViewHolder>() {

    private val assetItems: MutableList<AssetUiItem> = mutableListOf()
    private var onClickItem: ((AssetUiItem) -> (Unit))? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AssetItemViewHolder(
            AssetItemBinding.inflate(
                inflater, parent, false
            )
        ) {
            onClickItem?.invoke(it)
        }
    }

    override fun onBindViewHolder(holder: AssetItemViewHolder, position: Int) {
        holder.bind(assetItems[position])
    }

    override fun getItemCount() = assetItems.size

    fun setOnItemClickListener(onItemClickListener: ((AssetUiItem) -> (Unit))? = null) {
        onClickItem = onItemClickListener
    }

    fun updateList(newItems: List<AssetUiItem>) {
        val diffCallback = AssetDiffCallback(assetItems, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        assetItems.clear()
        assetItems.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }
}

class AssetItemViewHolder(
    private val binding: AssetItemBinding,
    private val onItemClickListener: ((AssetUiItem) -> (Unit))
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(asset: AssetUiItem) {
        binding.apply {
            textViewAssetCode.text = asset.symbol
            textViewAssetName.text = asset.name
            textViewAssetPrice.text = asset.price
            root.setOnClickListener {
                onItemClickListener.invoke(asset)
            }
        }
    }
}

class AssetDiffCallback(
    private val oldList: List<AssetUiItem>,
    private val newList: List<AssetUiItem>
) :
    DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }

}