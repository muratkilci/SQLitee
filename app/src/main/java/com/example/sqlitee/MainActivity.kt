package com.example.sqlitee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sqlitee.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val context = this
        var db = DataBaseHelper(context)
        
        //verileri kaydetme
        binding.btnSave.setOnClickListener {
            var etnamesurname = binding.etNameSurname.text.toString()
            var etage = binding.etAge.text.toString()
            if (etnamesurname.isNotEmpty() && etage.isNotEmpty()) {
                var users = User(etnamesurname, etage.toInt())
                db.insertData(users)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Please fill in the blank fields",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        //verileri okumak için
        binding.btnRead.setOnClickListener {
            var data = db.readData()
            binding.twSonuc.text = ""
            for (i in 0 until data.size) {
                binding.twSonuc.append(
                    data.get(i).id.toString() + " "
                            + data.get(i).namesurname + " " + data.get(i).age + " \n "
                )
            }
        }

        //verileri güncelleme
        binding.btnUpdate.setOnClickListener {
            db.updateData()
            binding.btnRead.performClick()
        }

        //verileri silme
        binding.btnDelete.setOnClickListener {
            db.deleteData()
            binding.btnRead.performClick()
        }
    }
}