package com.app.myapplication.Holders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.myapplication.Models.ExternalItemsModel
import com.app.myapplication.R
import kotlinx.android.extensions.LayoutContainer

class ExternalItemsHolder(
    override val containerView: View,
    private val clickLambda: (ExternalItemsModel) -> Unit
) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(externalItemsModel: ExternalItemsModel){
        containerView.findViewById<ImageView>(R.id.itemImage).setImageResource(externalItemsModel.itemPic)
        containerView.findViewById<TextView>(R.id.itemContent).text = externalItemsModel.title
        itemView.setOnClickListener { clickLambda(externalItemsModel) }
    }

    companion object{
        fun create(parent: ViewGroup, clickLambda: (ExternalItemsModel) -> Unit) =
            ExternalItemsHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.fragment_item, parent, false
                ),
                clickLambda
            )

    }
}


