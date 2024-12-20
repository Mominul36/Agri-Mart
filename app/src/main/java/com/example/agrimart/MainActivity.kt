package com.example.agrimart


import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.ActivityResultRegistry
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.agrimart.Activity.LoginActivity
import com.example.agrimart.Activity.MyProductActivity
import com.example.agrimart.Class.MyClass
import com.example.agrimart.Fragments.HomeFragment
import com.example.agrimart.Fragments.MarketFragment
import com.example.agrimart.Fragments.MessageFragment
import com.example.agrimart.Fragments.ProfileFragment
import com.example.agrimart.Fragments.ServiceFragment
import com.example.agrimart.Model.Farmer
import com.example.agrimart.databinding.ActivityMainBinding
import com.example.agrimarttrader.Class.ControlImage
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var navHome: LinearLayout
    lateinit var navMarket: LinearLayout
    lateinit var navService: LinearLayout
    lateinit var navMessage: LinearLayout
    lateinit var navProfile: LinearLayout

    lateinit var iconHome: ImageView
    lateinit var iconMarket: ImageView
    lateinit var iconCropCare: ImageView
    lateinit var iconAdvisor: ImageView
    lateinit var iconProfile: ImageView

    lateinit var txtHome: TextView
    lateinit var txtMarket: TextView
    lateinit var txtCropCare: TextView
    lateinit var txtAdvisor: TextView
    lateinit var txtProfile: TextView


    lateinit var binding: ActivityMainBinding
    var flag: Boolean = false
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var fragment = intent.getStringExtra("fragment")

        auth = FirebaseAuth.getInstance()
        initVariable()

        setNavigationItemColor(navHome)

        if(fragment=="home"){
            setFragment(HomeFragment())
        }else if(fragment=="profile"){
            setFragment(ProfileFragment())
        }



        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        setFarmerNameAndProfilePic()



        binding.rightNavIcon.setOnClickListener {
            if (binding.drawerlayout.isDrawerOpen(GravityCompat.END)) {
                binding.drawerlayout.closeDrawer(GravityCompat.END)
            } else {
                binding.drawerlayout.openDrawer(GravityCompat.END)
            }
        }

        binding.navView.setNavigationItemSelectedListener(this)




        navHome.setOnClickListener {
            setNavigationItemColor(navHome)
            setFragment(HomeFragment())
        }

        navMarket.setOnClickListener {
            setNavigationItemColor(navMarket)
            setFragment(MarketFragment())
        }

        navService.setOnClickListener {
            setNavigationItemColor(navService)
            setFragment(ServiceFragment())
        }

        navMessage.setOnClickListener {
            setNavigationItemColor(navMessage)
            setFragment(MessageFragment())
        }

        navProfile.setOnClickListener {
            setNavigationItemColor(navProfile)
            setFragment(ProfileFragment())
        }










    }

    private fun setFarmerNameAndProfilePic() {
        MyClass().getCurrentFarmer { farmer->
            if(farmer!=null){

                binding.name.text = farmer.name

                if(farmer.pic==""){
                    binding.pic.setImageResource(R.drawable.profile)
                }else{
                    ControlImage(this, activityResultRegistry, "imagePickerKey")
                        .setImageByURl(farmer.pic.toString(), binding.pic)
                }



            }
        }


    }

    fun setFragment(fragment: Fragment){
        val fragmentManager : FragmentManager = supportFragmentManager
        val frammentTransition : FragmentTransaction = fragmentManager.beginTransaction()

        if(!flag){
            frammentTransition.add(R.id.frame,fragment)
            flag = true
        }
        else{
            frammentTransition.replace(R.id.frame,fragment)
        }
        frammentTransition.addToBackStack(null)
        frammentTransition.commit()
    }

    private fun initVariable() {
        // LinearLayout items
        navHome = findViewById(R.id.nav_home)
        navMarket = findViewById(R.id.nav_market)
        navService = findViewById(R.id.nav_service)
        navMessage = findViewById(R.id.nav_message)
        navProfile = findViewById(R.id.nav_profile)

// ImageView items
        iconHome = findViewById(R.id.icon_home)
        iconMarket = findViewById(R.id.icon_market)
        iconCropCare = findViewById(R.id.icon_crop_care)
        iconAdvisor = findViewById(R.id.icon_advisor)
        iconProfile = findViewById(R.id.icon_profile)

// TextView items
        txtHome = findViewById(R.id.txt_home)
        txtMarket = findViewById(R.id.txt_market)
        txtCropCare = findViewById(R.id.txt_crop_care)
        txtAdvisor = findViewById(R.id.txt_advisor)
        txtProfile = findViewById(R.id.txt_profile)
    }

    private fun setNavigationItemColor(layout: LinearLayout?) {
        // Reset all items to default color and icon
        setAllNavigationItemBlack()
        val newWidth = 50
        val newHeight = 50
        val params = LinearLayout.LayoutParams(newWidth, newHeight)

        // Set selected item color and icon based on the passed layout
        when (layout) {
            navHome -> {
                txtHome.setTextColor(ContextCompat.getColor(this, R.color.base_color))
                iconHome.setImageResource(R.drawable.home2)
                iconHome.layoutParams = params
            }
            navMarket -> {
                txtMarket.setTextColor(ContextCompat.getColor(this, R.color.base_color))
                iconMarket.setImageResource(R.drawable.cart2)
            }
            navService -> {
                txtCropCare.setTextColor(ContextCompat.getColor(this, R.color.base_color))
                iconCropCare.setImageResource(R.drawable.crop_care2)
            }
            navMessage -> {
                txtAdvisor.setTextColor(ContextCompat.getColor(this, R.color.base_color))
                iconAdvisor.setImageResource(R.drawable.message2)
            }
            navProfile -> {
                txtProfile.setTextColor(ContextCompat.getColor(this, R.color.base_color))
                iconProfile.setImageResource(R.drawable.profile_user2)
            }
        }
    }



    private fun setAllNavigationItemBlack() {
        // Set color for all TextViews
        txtHome.setTextColor(ContextCompat.getColor(this, R.color.black))
        txtMarket.setTextColor(ContextCompat.getColor(this, R.color.black))
        txtCropCare.setTextColor(ContextCompat.getColor(this, R.color.black))
        txtAdvisor.setTextColor(ContextCompat.getColor(this, R.color.black))
        txtProfile.setTextColor(ContextCompat.getColor(this, R.color.black))

        // Set image resources for all ImageViews
        iconHome.setImageResource(R.drawable.home)
        iconMarket.setImageResource(R.drawable.cart)
        iconCropCare.setImageResource(R.drawable.crop_care)
        iconAdvisor.setImageResource(R.drawable.message)
        iconProfile.setImageResource(R.drawable.profile_user)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (binding.drawerlayout.isDrawerOpen(GravityCompat.END)) {
            binding.drawerlayout.closeDrawer(GravityCompat.END)
        } else {
            finish()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.myproduct -> {
//                supportFragmentManager.beginTransaction().replace(R.id.content_frame, HomeFragment()).commit()
                startActivity(Intent(this,MyProductActivity::class.java))
            }
            R.id.nav_profile -> {
//                supportFragmentManager.beginTransaction().replace(R.id.content_frame, ProfileFragment()).commit()
            }
            R.id.nav_settings -> {
//                supportFragmentManager.beginTransaction().replace(R.id.content_frame, SettingsFragment()).commit()
            }
            R.id.nav_logout -> {
                auth.signOut()
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }
        }
        binding.drawerlayout.closeDrawer(GravityCompat.END)
        return true
    }



}
