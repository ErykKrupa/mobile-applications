package com.example.simplemath

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val retrofit = Retrofit.Builder()
//            .baseUrl("https://newton.now.sh")
            .baseUrl("http://156.17.7.48:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val newton = retrofit.create(NewtonAPI::class.java)
        val callback = object: Callback<NewtonResult> {
            override fun onFailure(call: Call<NewtonResult>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message ?: "Error", Toast.LENGTH_LONG).show()
                progressBar.visibility = View.GONE
            }
            override fun onResponse(call: Call<NewtonResult>, response: Response<NewtonResult>) {
                val body = response.body()
                if (body?.result == null || response.code() != 200) {
                    onFailure(call, Throwable("Unable to perform calculation"))
                    return
                } else if(body.result.equals("NaN")){
                    onFailure(call, Throwable("Not a number"))
                    return
                }
                showResults(body)
            }
        }
        val adapter = ArrayAdapter<Operation>(this, android.R.layout.simple_spinner_item, Operation.values())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        calcSpinner.adapter = adapter
        calcSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
               val op =  p0?.selectedItem as Operation
                if (op.extra){
                extrasGroup.visibility = View.VISIBLE
                    extrasLabel.text = op.extraName
                }
                else extrasGroup.visibility = View.GONE

                if(op.range)
                    rangeGroup.visibility = View.VISIBLE
                else
                    rangeGroup.visibility = View.GONE
            }
        }

        button.setOnClickListener {
            showLoading()
            val op = calcSpinner.selectedItem as Operation
            val data = when {
                op.type() == 0 -> mainDataEt.text.toString().replace(',','.').trim()
                op.type() == 1 -> extraDataEt.text.toString().trim() + "|" + mainDataEt.text.toString().replace(',','.').trim()
                op.type() == 2 -> rangeText1.text.toString().trim() + ":" + rangeText2.text + "|" + mainDataEt.text.toString().replace(',','.').trim()
                else -> ""
            }
            val call: Call<NewtonResult> = op.calc(newton,data)
            call.enqueue(callback)
        }
    }
    fun showResults(result: NewtonResult) {
        rezTextView.text = result.result
        calcTextView.text = result.operation + "(" + result.expression?.replace("|", ",", false) + ")"
        resultGroup.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }
    fun showLoading() {
        resultGroup.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

}
