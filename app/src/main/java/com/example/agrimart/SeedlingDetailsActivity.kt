package com.example.agrimart.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultRegistry
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.agrimart.Class.MyClass
import com.example.agrimart.Model.Farmer
import com.example.agrimart.Model.Seed
import com.example.agrimart.Model.Seedling
import com.example.agrimart.R
import com.example.agrimart.databinding.ActivitySeedDetailsBinding
import com.example.agrimart.databinding.ActivitySeedlingBinding
import com.example.agrimart.databinding.ActivitySeedlingDetailsBinding
import com.example.agrimarttrader.Class.ControlImage
import com.google.android.material.bottomsheet.BottomSheetDialog

class SeedlingDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivitySeedlingDetailsBinding
    lateinit var seedlingId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySeedlingDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        seedlingId = intent.getStringExtra("seedlingId").toString()


        setAllData()


        binding.back.setOnClickListener{
            finish()
        }

        binding.plus.setOnClickListener{
            var num = binding.quantity.text.toString().toInt()
            num++
            binding.quantity.text = num.toString()
        }


        binding.minus.setOnClickListener{
            var num = binding.quantity.text.toString().toInt()
            num--

            if(num<1){
                Toast.makeText(this, "Minimum 1 item required",Toast.LENGTH_SHORT).show()
            }else{
                binding.quantity.text = num.toString()
            }
        }






        binding.call.setOnClickListener{
            showBottomSheet()
        }

        binding.chat.setOnClickListener{
            goToMessage()
        }


    }


    private fun goToMessage() {
        MyClass().fetchSingleData("Seedling",seedlingId, Seedling::class.java, onDataChange = { seedling->
            if(seedling!=null){
                var intent = Intent(this, MessageActivity::class.java)
                intent.putExtra("opId",seedling.farmerId)
                startActivity(intent)
            }
        }, onCancelled = {
        })
    }


    @SuppressLint("MissingInflatedId")
    private fun showBottomSheet() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet, null)
        dialog.setContentView(view)

        val closeButton: ImageView = view.findViewById(R.id.close)
        val bname: TextView = view.findViewById(R.id.bname)
        val bphone: TextView = view.findViewById(R.id.bphone)
        val bchat: ImageView = view.findViewById(R.id.bchat)
        val bcall: ImageView = view.findViewById(R.id.bcall)


        MyClass().fetchSingleData("Seedling",seedlingId, Seedling::class.java, onDataChange = { seedling->
            if(seedling!=null){
                MyClass().fetchSingleData("Farmer",seedling.farmerId!!, Farmer::class.java, onDataChange = { farmer->
                    if(farmer!=null){
                        bname.text = "Contact " + farmer.name
                        bphone.text = farmer.phone

                    }
                }, onCancelled = {

                })
            }
        }, onCancelled = {
        })






        bcall.setOnClickListener {

            MyClass().fetchSingleData("Seedling",seedlingId, Seedling::class.java, onDataChange = { seedling->
                if(seedling!=null){
                    MyClass().fetchSingleData("Farmer",seedling.farmerId!!, Farmer::class.java, onDataChange = { farmer->
                        if(farmer!=null){
                            val intent = Intent(Intent.ACTION_DIAL)
                            intent.data = Uri.parse("tel:"+farmer.phone)
                            startActivity(intent)
                        }
                    }, onCancelled = {
                    })
                }
            }, onCancelled = {
            })
        }


        bchat.setOnClickListener {

            MyClass().fetchSingleData("Seedling",seedlingId, Seedling::class.java, onDataChange = { seedling->
                if(seedling!=null){
                    MyClass().fetchSingleData("Farmer",seedling.farmerId!!, Farmer::class.java, onDataChange = { farmer->
                        if(farmer!=null){
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse("smsto:"+farmer.phone)
                            startActivity(intent)
                        }
                    }, onCancelled = {
                    })
                }
            }, onCancelled = {
            })

        }




        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }





    private fun setAllData() {

        MyClass().fetchSingleData("Seedling",seedlingId, Seedling::class.java, onDataChange = { seedling->
            if(seedling!=null){
                ControlImage(this, activityResultRegistry, "asdfadf" ).
                setImageByURl(seedling.pic.toString(),binding.pic)

                binding.name.text = seedling.name + " Seedlings"
                binding.rate.text = "৳"+seedling.rate+"/"
                binding.unit.text = seedling.unit
                binding.rating.text = seedling.rating
                binding.description.text = seedling.description







            }
        }, onCancelled = {

        })


    }
}