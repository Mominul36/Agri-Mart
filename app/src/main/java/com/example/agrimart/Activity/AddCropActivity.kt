package com.example.agrimart.Activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.agrimart.Class.MyClass
import com.example.agrimart.Model.Crop
import com.example.agrimart.Model.Seed
import com.example.agrimart.databinding.ActivityAddCropBinding
import com.example.agrimart.databinding.ActivityAddSeedBinding
import com.example.agrimarttrader.Class.ControlImage
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddCropActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddCropBinding
    private lateinit var controlImage: ControlImage
    lateinit var database: DatabaseReference
    var selectedUnit: String = ""

    private val cropData = mapOf(
        "Cereal" to listOf("Wheat", "Rice", "Maize", "Barley", "Sorghum"),
        "Pulse" to listOf("Lentil", "Chickpea", "Pea", "Bean", "Soybean", "Groundnuts"),
        "Fruit" to listOf("Jack-Fruit", "Apple", "Orange", "Banana", "Mango", "Grape", "Pineapple", "Strawberry"),
        "Vegetable" to listOf("Tomato", "Onion", "Cucumber", "Cabbage", "Potato", "Sweet Potato", "Carrot", "Turnip"),
        "Fiber" to listOf("Cotton", "Jute", "Hemp", "Flax"),
        "Beverage" to listOf("Coffee", "Tea", "Cocoa", "Sugarcane"),
        "Medicinal" to listOf("Aloe Vera", "Lavender", "Mint", "Turmeric"),
        "Spice" to listOf("Ginger", "Chili", "Cardamom", "Cloves", "Cinnamon")
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddCropBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener{
            finish()
        }

        setupCategorySpinner()

        controlImage = ControlImage(this, activityResultRegistry, "imagePickerKey")
        database = FirebaseDatabase.getInstance().getReference("Crop")

        binding.progressBar.visibility = View.GONE
        binding.mainlayout.visibility = View.VISIBLE

        binding.fertilizerphoto.setOnClickListener {
            controlImage.setImageView(binding.fertilizerphoto)
            controlImage.selectImage()
        }

        binding.btnpublish.setOnClickListener {
            addDataToDatabase()
        }

        val units = listOf("Piece", "Kg","40 kg","Sack")
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


    private fun setupCategorySpinner() {
        val categories = cropData.keys.toList()
        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.category.adapter = categoryAdapter

        binding.category.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedCategory = categories[position]
                updateCropSpinner(selectedCategory)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun updateCropSpinner(category: String) {
        val crops = cropData[category] ?: emptyList()
        val cropAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, crops)
        cropAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.cropName.adapter = cropAdapter
    }



    private fun addDataToDatabase() {
        binding.progressBar.visibility = View.VISIBLE
        binding.mainlayout.visibility = View.GONE


        val id = MyClass().getKey().toString()
        val category = binding.category.selectedItem.toString()
        val name = binding.cropName.selectedItem.toString()
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

                        val crop = Crop(id, farmerId,category, name, rate, unit, description, division, district, thana, pic, rating)
                        database.child(id).setValue(crop)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Crop Added Successfully", Toast.LENGTH_SHORT).show()
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
