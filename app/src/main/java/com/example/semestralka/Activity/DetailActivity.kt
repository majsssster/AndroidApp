package com.example.semestralka.Activity

import android.content.Intent
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.project1762.Helper.ManagmentCart
import com.example.semestralka.Adapter.PicListAdapter
import com.example.semestralka.Adapter.SizeListAdapter
import com.example.semestralka.Model.ItemsModel
import com.example.semestralka.R
import com.example.semestralka.databinding.ActivityDetailBinding

class DetailActivity : BaseActivity() {
    private lateinit var binding:ActivityDetailBinding
    private lateinit var item:ItemsModel
    private var numberOrder=1
    private lateinit var managmentCart:ManagmentCart

    inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
        SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
    }

    inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
        SDK_INT >= 33 -> getParcelable(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelable(key) as? T
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart= ManagmentCart(this)

        getBundle()
        initList()
    }

    private fun initList() {
        val sizeList= ArrayList<String>()
        for (size in item.size){
            sizeList.add(size.toString())
        }
        binding.sizeList.adapter=SizeListAdapter(sizeList)
        binding.sizeList.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        val colorList=ArrayList<String>()
        for (imageUrl in item.picUrl){
            colorList.add(imageUrl)
        }

        Glide.with(this)
            .load(colorList[0])
            .into(binding.picMain)

        binding.picList.adapter= PicListAdapter(colorList,binding.picMain)
        binding.picList.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
    }


    private fun getBundle(){
        item = intent.getParcelableExtra("Object")!!
        Log.d("mnau", item.toString())

        binding.titleTxt.text=item.title
        binding.descriptionTxt.text=item.description
        binding.priceTxt.text="€"+item.price
        binding.ratingTxt.text= "${item.ratting} Rating"
        binding.SellerNameTxt.text=item.sellerName

        binding.AddToCartBtn.setOnClickListener{
            item.numberInCart=numberOrder
            managmentCart.insertItems(item)
        }
        binding.backBtn.setOnClickListener{finish()}
        binding.cartBtn.setOnClickListener{
            startActivity(Intent(this@DetailActivity,CartActivity::class.java))

        }
        Glide.with(this)
            .load(item.sellerPic)
            .apply(RequestOptions().transform(CenterCrop()))
            .into(binding.picSeller)

        binding.msgToSellerBtn.setOnClickListener{
            val sendIntent=Intent(Intent.ACTION_VIEW)
            sendIntent.setData(Uri.parse("sms:"+item.sellerTell))
            sendIntent.putExtra("sms_body", "type your message")
            startActivity(intent)
        }
        binding.calToSellerBtn.setOnClickListener{
            val phone=item.sellerTell.toString()
            val intent=Intent(Intent.ACTION_DIAL,Uri.fromParts("tel",phone,null))
            startActivity(intent)
        }
    }
}