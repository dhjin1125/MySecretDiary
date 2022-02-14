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
            .setTitle("실패")
            .setMessage("비밀번호가 잘못되었습니다.")
            .setPositiveButton("확인"){_, _ ->}
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
                Toast.makeText(this,"비밀번호가 변경되었습니다.",Toast.LENGTH_SHORT).show()
            }

            else {
                val password =
                    "${firstNumberPicker.value}${secondNumberPicker.value}${thirdNumberPicker.value}"
                if(password != sharedPreferences.getString("password","000")) {
                    Toast.makeText(this,"비밀번호가 일치하지않아 변경이 불가합니다.",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                changePasswordButton.setBackgroundColor(Color.RED)
                Toast.makeText(this,"비밀번호 변경 모드입니다. 비밀번호를 입력하시고 버튼을 다시 눌러주세요.",Toast.LENGTH_SHORT).show()
                changePasswordMode = true

            }

        }
    }

}