package com.example.agrimart.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.agrimart.Class.MyClass
import com.example.agrimart.MainActivity
import com.example.agrimart.R
import com.example.agrimart.databinding.FragmentProfileBinding
import com.example.agrimarttrader.Class.ControlImage
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    private var isImageSelected = false
    private lateinit var controlImage: ControlImage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        controlImage = ControlImage(
            requireContext(),
            requireActivity().activityResultRegistry,
            "imagePickerKey"
        )

        setAllData()


        binding.pic.setOnClickListener {
            controlImage.setImageView(binding.pic)
            controlImage.selectImage()
            isImageSelected = true
        }



        binding.passchange.setOnClickListener {
            val oldPassword = binding.oldpass.text.toString().trim()
            val newPassword = binding.newpass.text.toString().trim()

            if (oldPassword.isEmpty() || newPassword.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in both fields", Toast.LENGTH_SHORT).show()
            } else {
                // Call the method to change the password
                changePassword(oldPassword, newPassword)
            }
        }






        binding.updatebtn.setOnClickListener {
            if (isImageSelected) {
                controlImage.uploadImageToFirebaseStorage { isSuccess, message ->
                    if (isSuccess) {
                        updateTeacherProfile(message)
                    } else {
                        Toast.makeText(requireContext(), "Failed to upload image: $message", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                MyClass().getCurrentFarmer { farmer ->
                    updateTeacherProfile(farmer!!.pic ?: "")
                }
            }
        }











        binding.details.setOnClickListener {
            showDetailsLayout()
        }

        binding.password.setOnClickListener {
            showPasswordLayout()
        }





        return binding.root
    }

    private fun changePassword(oldPassword: String, newPassword: String) {
        val user = FirebaseAuth.getInstance().currentUser
        var email = user?.email ?: return


        val credential = EmailAuthProvider.getCredential(email, oldPassword)
        user.reauthenticate(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    user.updatePassword(newPassword)
                        .addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {

                                updatePasswordInDatabase(newPassword)
                            } else {
                                Toast.makeText(requireContext(), "Failed to update password in Authentication", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(requireContext(), "Old password is incorrect", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun updatePasswordInDatabase(newPassword: String) {
        MyClass().getCurrentFarmer { farmer ->
            val farmerId = farmer?.id ?: return@getCurrentFarmer
            val database = FirebaseDatabase.getInstance().getReference("Farmer")

            database.child(farmerId).child("password").setValue(newPassword)
                .addOnSuccessListener {
                    binding.oldpass.text.clear()
                    binding.newpass.text.clear()
                    Toast.makeText(requireContext(), "Password changed successfully", Toast.LENGTH_SHORT).show()
                    //Toast.makeText(requireContext(), "Password updated in database", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Failed to update password in database", Toast.LENGTH_SHORT).show()
                }
        }
    }

















    private fun updateTeacherProfile(profilePicUrl: String) {
        val database = FirebaseDatabase.getInstance().getReference("Farmer")

         var flag : Boolean
         if(binding.worker.isChecked)
             flag = true
        else
            flag = false

        val user = mapOf(
            "name" to binding.name.text.toString(),
            "phone" to binding.phone.text.toString(),
            "pic" to profilePicUrl,
            "division" to binding.division.text.toString(),
            "district" to binding.district.text.toString(),
            "thana" to binding.thana.text.toString(),
            "elocation" to binding.elocation.text.toString(),
            "worker" to flag  // Merged 'worker' field here
        )



        MyClass().getCurrentFarmer { farmer ->
            val farmerId = farmer?.id ?: return@getCurrentFarmer  // Safe access
            database.child(farmerId).updateChildren(user).addOnSuccessListener {
                Toast.makeText(requireContext(), "Successfully Updated", Toast.LENGTH_SHORT).show()
                var intent= Intent(requireContext(), MainActivity::class.java)
                intent.putExtra("fragment","profile")
                startActivity(intent)

                activity?.finish()




            }.addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to Update", Toast.LENGTH_SHORT).show()
            }
        }


    }








    private fun setAllData() {

        MyClass().getCurrentFarmer { farmer->
            if(farmer!=null){

                binding.name.setText(farmer.name)
                binding.phone.setText(farmer.phone)
                binding.division.setText(farmer.division)
                binding.district.setText(farmer.district)
                binding.thana.setText(farmer.thana)
                binding.elocation.setText(farmer.elocation)


                if(farmer.worker==true){
                    binding.worker.isChecked = true
                }else{
                    binding.worker.isChecked = false
                }


                if(farmer.pic==""){
                    binding.pic.setImageResource(R.drawable.profile)
                }else{
                    ControlImage(requireContext(), requireActivity().activityResultRegistry,"dsasdfd")
                        .setImageByURl(farmer.pic.toString(), binding.pic)
                }



            }
        }





    }


    private fun showDetailsLayout() {
        binding.details.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.base_color))
        binding.details.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.password.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.temp_color))
        binding.password.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

        binding.mainLayout.visibility = View.VISIBLE
        binding.passwordLayout.visibility = View.GONE
    }

    private fun showPasswordLayout() {
        binding.password.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.base_color))
        binding.password.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.details.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.temp_color))
        binding.details.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

        binding.mainLayout.visibility = View.GONE
        binding.passwordLayout.visibility = View.VISIBLE
    }

}












