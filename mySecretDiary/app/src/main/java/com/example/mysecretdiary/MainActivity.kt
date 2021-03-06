package com.example.mysecretdiary

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    private val firstNumberPicker: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.firstNumberPicker)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val secondNumberPicker: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.secondNumberPicker)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val thirdNumberPicker: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.thirdNumberPicker)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val openButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.openButton)
    }

    private val changePasswordButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.changePasswordButton)
    }

    private var changePasswordMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initOpenButton()
        initNumberPicker()
        initChangePasswordButton()
    }

    private fun initOpenButton() {
        openButton.setOnClickListener {

            val sharedPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            val password = "${firstNumberPicker.value}${secondNumberPicker.value}${thirdNumberPicker.value}"

            if(password == sharedPreferences.getString("password","000")) {
                val intent = Intent(this,DiaryActivity::class.java)
                startActivity(intent)
            }
            else
                showErrorPopup()
        }
    }

    private fun initNumberPicker() {
        firstNumberPicker
        secondNumberPicker
        thirdNumberPicker
    }

    private fun showErrorPopup() {
        AlertDialog.Builder(this)
            .setTitle("??????")
            .setMessage("??????????????? ?????????????????????.")
            .setPositiveButton("??????"){_, _ ->}
            .create()
            .show()
    }

    private fun initChangePasswordButton() {
        changePasswordButton.setOnClickListener {
            val sharedPreferences = getSharedPreferences("password",Context.MODE_PRIVATE)

            if (changePasswordMode) {
                val password =
                    "${firstNumberPicker.value}${secondNumberPicker.value}${thirdNumberPicker.value}"

                sharedPreferences.edit {
                    this.putString("password",password)
                    commit()
                }
                changePasswordMode = false
                changePasswordButton.setBackgroundColor(Color.BLACK)
                Toast.makeText(this,"??????????????? ?????????????????????.",Toast.LENGTH_SHORT).show()
            }

            else {
                val password =
                    "${firstNumberPicker.value}${secondNumberPicker.value}${thirdNumberPicker.value}"
                if(password != sharedPreferences.getString("password","000")) {
                    Toast.makeText(this,"??????????????? ?????????????????? ????????? ???????????????.",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                changePasswordButton.setBackgroundColor(Color.RED)
                Toast.makeText(this,"???????????? ?????? ???????????????. ??????????????? ??????????????? ????????? ?????? ???????????????.",Toast.LENGTH_SHORT).show()
                changePasswordMode = true

            }

        }
    }

}