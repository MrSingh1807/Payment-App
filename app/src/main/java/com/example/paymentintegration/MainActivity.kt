package com.example.paymentintegration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.paymentintegration.databinding.ActivityMainBinding
import com.razorpay.Checkout
import com.razorpay.ExternalWalletListener
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONException
import org.json.JSONObject
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity(), PaymentResultWithDataListener, ExternalWalletListener {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.payBtn.setOnClickListener {
            val valueET = binding.enterAmoutET.text.toString()
            val amount = (valueET.toFloat() * 100).roundToInt()

            val checkout = Checkout()
            if (valueET.isNotEmpty()) {
                checkout.setKeyID(KEY_ID)
            }
            checkout.setImage(R.drawable.ic_money)

            val jsonObject = JSONObject()
            try {
                jsonObject.apply {
                    put("Name", "Mr Singh")
                    put("Description", "Example Payment")
                    put("Image", IMAGE_ADDRESS)
                    put("Currency", "INR")
                    put("amount", amount)
                    put("Send_SMS_Hash", true)

                    val preFill = JSONObject()
                        .put("Email", "singh.Mayank@gmail.com")
                        .put("Phone No", "8899442266")
                    put("prefill", preFill)
                }
                checkout.open(this@MainActivity, jsonObject)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        Toast.makeText(
            this,
            "Payment is successful : Payment ID: $p0 \n Payment Data: ${p1?.data}",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        Toast.makeText(this, "Payment Failed due to error : ${p2?.data}", Toast.LENGTH_SHORT).show()
    }

    override fun onExternalWalletSelected(p0: String?, p1: PaymentData?) {
        Toast.makeText(
            this,
            "External wallet was selected : Payment Data: ${p1?.data}",
            Toast.LENGTH_SHORT
        ).show()
    }
}