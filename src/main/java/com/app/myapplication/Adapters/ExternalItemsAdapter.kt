package com.app.myapplication.Adapters

import android.graphics.ColorSpace
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.myapplication.Holders.ExternalItemsHolder
import com.app.myapplication.Models.ExternalItemsModel

class ExternalItemsAdapter(
    var itemsData: ArrayList<ExternalItemsModel>,
    private val clickLambda: (ExternalItemsModel) -> Unit):
        RecyclerView.Adapter<ExternalItemsHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExternalItemsHolder =
        ExternalItemsHolder.create(
            parent,
            clickLambda
        )

    override fun onBindViewHolder(externalItemsHolder: ExternalItemsHolder, position: Int) =
        externalItemsHolder.bind((itemsData[position]))

    override fun getItemCount() = itemsData.size

/*    fun update(itemData: ArrayList<>?){
    if (itemData == null) {
        return
    }
        itemData.clear()
        notifyDataSetChanged()
        itemData.addAll(itemData)
        notifyDataSetChanged()
    }*/
}
