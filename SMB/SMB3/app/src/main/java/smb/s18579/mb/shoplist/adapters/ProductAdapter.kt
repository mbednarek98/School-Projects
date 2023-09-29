package smb.s18579.mb.shoplist.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import smb.s18579.mb.shoplist.ProductListActivity
import smb.s18579.mb.shoplist.R
import smb.s18579.mb.shoplist.database.Product
import smb.s18579.mb.shoplist.databinding.ShoplistRowRecycleviewBinding

class ProductViewHolder(private val binding: ShoplistRowRecycleviewBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(product: Product) {
        binding.apply {
            productName.text = product.name
            productPrice.text = product.price.toString()
            productQuantity.text = product.quantity.toString()
            productBought.isChecked = product.bought
        }
    }

    fun binding() = binding

}

class ProductAdapter(private val activity : ProductListActivity): RecyclerView.Adapter<ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder = ProductViewHolder(ShoplistRowRecycleviewBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        with(holder){
            bind(activity.listOfProducts[position])
            preferencesSetUp(holder)
            itemView.setOnClickListener { activity.onClickProductDialog(activity.listOfProducts[position],activity.resources.getString(R.string.edit_product)) }
            binding().productBought.setOnCheckedChangeListener { _, switchValue ->
                activity.listOfProducts[position].bought = switchValue
                val pro = hashMapOf<String, Any>(
                    "bought" to activity.listOfProducts[position].bought
                )

                activity.db.collection("products").document(activity.listOfProducts[position].id).update(pro)
            }
        }

    }

    private fun preferencesSetUp(holder : ProductViewHolder){
        val pref = activity.getSharedPreferences("save", Context.MODE_PRIVATE)
        with(holder.binding()) {
            val textSize = pref.getFloat("TEXTSIZE", 12F)
            val textColor = pref.getInt("TEXTCOLOR", android.graphics.Color.BLACK)
            productName.setTextColor(textColor)
            productBought.setTextColor(textColor)
            productPrice.setTextColor(textColor)
            productQuantity.setTextColor(textColor)
            productPriceName.setTextColor(textColor)
            productQuantityName.setTextColor(textColor)

            productBought.textSize = textSize
            productPrice.textSize = textSize
            productQuantity.textSize = textSize
            productPriceName.textSize = textSize
            productQuantityName.textSize = textSize
        }
    }


    fun removeAt(position: Int) {
        activity.db.collection("products").document(activity.listOfProducts[position].id).delete().addOnCompleteListener { activity.setUpAdapterNew() }
        activity.listOfProducts.drop(position)
    }

    override fun getItemCount(): Int = activity.listOfProducts.size

}