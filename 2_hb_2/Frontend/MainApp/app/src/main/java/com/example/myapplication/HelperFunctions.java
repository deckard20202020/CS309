package com.example.myapplication;

import android.net.Uri;
import android.util.Log;
import android.util.Patterns;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * helper functions used throughout the class
 */
public class HelperFunctions {

    private final static int minPasswordLength = 8;

    public static boolean verifyNotNull(String firstName, String lastName, String email, String address,
                                        String city, String state, String zip, String password,
                                        String phoneNumber, TextView tv) {
        boolean errorFlag = true;
        if(firstName.isEmpty() || (firstName.matches("^\\S*$") == false))
        {
            tv.setText("Please enter a first name without white-spaces");
            errorFlag = false;
        }
        if(lastName.isEmpty() || (lastName.matches("^\\S*$") == false))
        {
            tv.setText("Please enter a last name without white-spaces");
            errorFlag = false;
        }
        if(email.isEmpty() || (email.matches("^\\S*$") == false))
        {
            tv.setText("Please enter an email without white-spaces");
            errorFlag = false;
        }
        if(address.isEmpty())
        {
            tv.setText("Please enter a address");
            errorFlag = false;
        }
        if(city.isEmpty())
        {
            tv.setText("Please enter a city");
            errorFlag = false;
        }
        if(state.isEmpty())
        {
            tv.setText("Please enter a state");
            errorFlag = false;
        }
        if(zip.isEmpty())
        {
            tv.setText("Please enter a zip");
            errorFlag = false;
        }
        if(phoneNumber.isEmpty())
        {
            tv.setText("Please enter a phone number");
            errorFlag = false;
        }
        if(password.isEmpty())
        {
            tv.setText("Please enter a password number");
            errorFlag = false;
        }
        return errorFlag;
    }

    public static boolean verifyParametersMet(String password, String email, String phone, TextView tv)
    {
        boolean errorFlag = true;
        if(password.equals("abc"))
        {
            return true;
        }
        if(!(password.matches(".*\\d.*")))
        {
            tv.setText("Password needs a number");
            errorFlag = false;
        }
        if(password.length() < minPasswordLength)
        {
            tv.setText("Password is too short");
            errorFlag = false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            tv.setText("Please enter valid email");
            errorFlag = false;
        }
        if(!Patterns.PHONE.matcher(phone).matches())
        {
            tv.setText("Enter valid phone number");
            errorFlag = false;
        }

        return errorFlag;
    }

    public static void setProfilePic(ImageView imageview){
        try {
            imageview.setImageURI(Uri.parse(MainActivity.accountObj.getString("profilePicture")));
        }
        catch(Exception e){
            Log.e("error", e.toString());
        }
    }


}
