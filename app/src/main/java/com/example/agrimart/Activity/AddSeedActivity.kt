package com.example.agrimart.Activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.agrimart.Class.MyClass
import com.example.agrimart.Model.Seed
import com.example.agrimart.databinding.ActivityAddSeedBinding
import com.example.agrimarttrader.Class.ControlImage
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddSeedActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddSeedBinding
    private lateinit var controlImage: ControlImage
    lateinit var database: DatabaseReference
    var selectedUnit: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddSeedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener{
            finish()
        }

        controlImage = ControlImage(this, activityResultRegistry, "imagePickerKey")
        database = FirebaseDatabase.getInstance().getReference("Seed")

        binding.progressBar.visibility = View.GONE
        binding.mainlayout.visibility = View.VISIBLE

        binding.fertilizerphoto.setOnClickListener {
            controlImage.setImageView(binding.fertilizerphoto)
            controlImage.selectImage()
        }

        binding.btnpublish.setOnClickListener {
            addDataToDatabase()
        }

        val units = listOf("Packet", "Kg")
        val unitAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, units)
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.unit.adapter = unitAdapter

        binding.unit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedUnit = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun addDataToDatabase() {
        binding.progressBar.visibility = View.VISIBLE
        binding.mainlayout.visibility = View.GONE


        val id = MyClass().getKey().toString()
        val name = binding.fertilizername.text.toString()
        val rate = binding.rate.text.toString()
        val unit = binding.unit.selectedItem.toString()
        val description = binding.description.text.toString()
        val rating: String = "0.0"

        controlImage.uploadImageToFirebaseStorage { success, message ->
            if (success) {
                val pic = message.toString()

                MyClass().getCurrentFarmer { farmer ->
                    if (farmer != null) {
                        val farmerId = farmer.id.toString()
                        val division = farmer.division
                        val district = farmer.district
                        val thana = farmer.thana

                        val seed = Seed(id, farmerId, name, rate, unit, description, division, district, thana, pic, rating)
                        database.child(id).setValue(seed)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Seed Added Successfully", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                            .addOnFailureListener {
                                binding.progressBar.visibility = View.GONE
                                binding.mainlayout.visibility = View.VISIBLE
                                Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
            } else {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
