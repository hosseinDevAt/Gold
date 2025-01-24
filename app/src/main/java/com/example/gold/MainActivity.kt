package com.example.gold

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gold.databinding.ActivityMainBinding
import com.example.gold.remote.gold.GoldApiRepository
import com.example.gold.remote.gold.GoldRequest
import com.example.gold.remote.model.gold.ContentModel
import com.example.gold.remote.model.gold.CurrenciesModel
import com.example.gold.remote.time.TimeRequest
import com.example.gold.remote.model.time.DateModel
import com.example.gold.remote.time.TimeApiRepository
import retrofit2.Callback

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val goldPrice = ArrayList<ContentModel>()
    private val currencyPrice = ArrayList<ContentModel>()
    private val cryptoCurrencyPrice = ArrayList<ContentModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBar.visibility = View.VISIBLE


        TimeApiRepository.instance.getTimeNow(
            object : TimeRequest {
                override fun onSuccess(date: DateModel) {
                    val data = date.date
                    val text = "${data.day} ${data.dayOfMonth} ${data.month} ${data.year}"
                    binding.txtDateFa.text = text
                }

                override fun onNotSuccess(message: String) {
                    Log.i("NOT_SUCCESS", message)
                }

                override fun onError(message: String) {
                    Log.e("ERROR_SERVER", message)
                }
            }
        )

        getPrice()


        binding.mainRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.txtArs.setOnClickListener {
            binding.txtArs.setTextColor(Color.parseColor("#E7C376"))
            binding.txtGold.setTextColor(Color.parseColor("#787879"))
            binding.txtCryptoCurrency.setTextColor(Color.parseColor("#787879"))

            setData(currencyPrice)
        }

        binding.txtGold.setOnClickListener {
            binding.txtGold.setTextColor(Color.parseColor("#E7C376"))
            binding.txtArs.setTextColor(Color.parseColor("#787879"))
            binding.txtCryptoCurrency.setTextColor(Color.parseColor("#787879"))

            setData(goldPrice)
        }

        binding.txtCryptoCurrency.setOnClickListener {
            binding.txtCryptoCurrency.setTextColor(Color.parseColor("#E7C376"))
            binding.txtGold.setTextColor(Color.parseColor("#787879"))
            binding.txtArs.setTextColor(Color.parseColor("#787879"))

            setData(cryptoCurrencyPrice)

        }

    }


    private fun getPrice(){
        GoldApiRepository.instance.getCurrencies(
            object : GoldRequest{
                override fun onSuccess(data: CurrenciesModel) {
                    goldPrice.addAll(data.data.golds)
                    binding.txtMessageLastUpdate.text = data.message
                    currencyPrice.addAll(data.data.currencies)
                    cryptoCurrencyPrice.addAll(data.data.cryptocurrencies)
                    cryptoCurrencyPrice.sortByDescending { it.price }
                    setData(goldPrice)
                    binding.progressBar.visibility = View.INVISIBLE
                }

                override fun onNotSuccess(message: String) {
                    Log.e("NOT_SUCCESS_GOLD", message)
                    binding.progressBar.visibility = View.INVISIBLE
                }

                override fun onError(message: String) {
                    Log.e("ERROR_GOLD", message)
                    binding.progressBar.visibility = View.INVISIBLE
                }

            }
        )
    }

    private fun setData(data: ArrayList<ContentModel>){

        binding.mainRecycler.adapter = RecyclerMainAdapter(data)

    }

}