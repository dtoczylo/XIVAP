/**/
/*

NAME

        Main Activity Controller

PURPOSE

        This is the main controller for the main view of the app. This is responsible for directing
        the user to the appropriate activities and displaying in the upper left corner if there
        is a main profile set or not. There is a default icon used for the "no profile set" but
        will populate this icon if there is a character object found in the serialization file
        to hold a main profile.

AUTHOR

        Desiree Toczylowski

DATE

        5/8/2019

*/
/**/

package com.dtoczylo.xivap;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;


public class MainActivity extends AppCompatActivity {

    // Class contains a mainCharacter object to populate before serializing
    Character mainCharacter = new Character();

    // Progress bar view id
    ProgressBar progressBar;

    // Status string to check if there is a character object found, false is default value
    String status = "False";

    /**/
    /*
    public void onCreate()

    NAME

        onCreate - this formats the view of the activity on creation

    SYNOPSIS

        public void onCreate(Bundle savedInstanceState)
            Bundle savedInstanceState   --> if the activity of being re-initialized after previously being shut down this bundle
                                            contains the data it most recently supplied in onSaveInstanceState(Bundle)
            de.hdodenhof.circleimageview.CircleImageView icon   --> CircleImage library to hold the character icon
            TextView loggedInStatus         --> text view id of status
            TextView loggedInStatusIcon     --> text view id of status icon (emoji)
            progressBar                     --> class progress bar for transitions
            status                          --> class string status to check if main character object set
            mainCharacter                   --> class character object holding character data from file if found

    DESCRIPTION

        The onCreate method that is called when the activity is starting. It will create the view to reflect
        the main character object if found in the serialization file. If there is no profile set, it will
        reflect the default icon unless there is a character object, in which case it will set the icon
        and view to reflect there was a character selected.

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set XML for activity
        setContentView(R.layout.activity_main);
        // Set screen orientation
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Set view id's to appropriate fields
        de.hdodenhof.circleimageview.CircleImageView icon = findViewById(R.id.circleView_characterIcon);
        TextView loggedInStatus = findViewById(R.id.textView_loggedInStatus);
        TextView loggedInStatusIcon = findViewById(R.id.textView_loggedInStatusIcon);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        // Set progress bar to invisible on load
        progressBar.setVisibility(View.INVISIBLE);

        // SAVE FOR TESTING
        //forceClear();

        // If the main profile has text ..
        if(setMainProfile() == true){
            // Set the status to true
            status = "True";
            // Change the emoji to reflect logged in
            loggedInStatusIcon.setText("\uD83C\uDF8A\n");
            // Get the character objects name
            loggedInStatus.setText(mainCharacter.GetName());
            // Store the character objects icon in the icon image view
            Picasso.get()
                    .load(mainCharacter.GetIcon())
                    .into(icon);

        // Else no main profile =>
        }else{
            status = "False";
            // Reflect default view
            loggedInStatus.setText("NO PROFILE SET");
            icon.setImageResource(R.drawable.searching);
        }

    }

    /**/
    /*
   public void settingsButton()

    NAME

        settingsButton - function attached to the settings button to change the view from
        main activity to settings activity (search character screen).

    SYNOPSIS
        public void settingsButton(View view)
            View view       --> current view of application
            progressBar     --> class progress bar to toggle visibility
            Intent intent   --> intent to change activity from main to settings activity

    DESCRIPTION

        This is a function attached to a button responsible for changing the activity from
        main to settings (character search).


    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

    5/8/2019

    */
    /**/
    public void settingsButton(View view) {
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    /**/
    /*
   public void viewerButton()

    NAME

        viewerButton - function attached to the viewer button to change the view from
        main activity to viewer activity (character view).

    SYNOPSIS
        public void viewerButton(View view)
            View view       --> current view of application
            progressBar     --> class progress bar to toggle visibility
            Intent intent   --> intent to change activity from main to viewer activity

    DESCRIPTION

        This is a function attached to a button responsible for changing the activity from
        main to viewer (character view).

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

    5/8/2019

    */
    /**/
    public void viewerButton(View view) {
        // Toggle Progress Bar to visible
        progressBar.setVisibility(View.VISIBLE);

        // If no character object =>
        if(status.equals("False")){

            // Display toast
            Toast toast = Toast.makeText(getApplicationContext(), "No Main Profile Set", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            progressBar.setVisibility(View.INVISIBLE);

        // If character found =>
        }else{
            // Transition to viewer
            Intent intent = new Intent(MainActivity.this, ViewerActivity.class);
            startActivity(intent);
        }
    }

    /**/
    /*
    public void trackerButton()

    NAME

        trackerButton - function attached to the viewer button to change the view from
        main activity to tracker activity (achievement overview).

    SYNOPSIS
        public void trackerButton(View view)
            View view       --> current view of application
            progressBar     --> class progress bar to toggle visibility
            Intent intent   --> intent to change activity from main to tracker activity

    DESCRIPTION

        This is a function attached to a button responsible for changing the activity from
        main to tracker (achievements overview).

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

    5/8/2019

    */
    /**/
    public void trackerButton(View view) {
        // Toggle progress bar
        progressBar.setVisibility(View.VISIBLE);

        // If character profile not found => display toast
        if(status.equals("False")){
            Toast toast = Toast.makeText(getApplicationContext(), "No Main Profile Set", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            progressBar.setVisibility(View.INVISIBLE);

        // If character profile found => transition
        }else{
            Intent intent = new Intent(MainActivity.this, TrackerActivity.class);
            startActivity(intent);
        }
    }
    /**/
    /*
    public void comparisonButton()

    NAME

        comparisonButton - function attached to the viewer button to change the view from
        main activity to comparison activity (achievement comparison).

    SYNOPSIS
        public void comparisonButton(View view)
            View view       --> current view of application
            progressBar     --> class progress bar to toggle visibility
            Intent intent   --> intent to change activity from main to comparison activity

    DESCRIPTION

        This is a function attached to a button responsible for changing the activity from
        main to comparison (achievements comparison).

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

    5/8/2019

    */
    /**/
    public void comparisonButton(View view) {
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = new Intent(MainActivity.this, ComparisonActivity.class);
        startActivity(intent);
    }

    /**/
    /*
    public void onBackPressed()

    NAME

        onBackPressed - this is a work around to disable the back button on the device

    SYNOPSIS

        public void onBackPressed()


    DESCRIPTION

        This function is used to disable the back button on the android tablet, forcing user to hit
        the respective buttons within the app

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    @Override
    public void onBackPressed() {
    }

    /**/
    /*
    public boolean setMainProfile()

    NAME

        setMainProfile - returns true if main character was found in file, sets character to class character
        object. If false returns, there is no main character found.

    SYNOPSIS

        public boolean setMainProfile()
            FileInputStream fileIn      --> file input stream
            ObjectInputStream inputReader --> object input stream
            mainCharacter   --> class main character object to be set from file

    DESCRIPTION

        The setMainProfile function will read the serialization file, and if a character
        object is found, will set the class character object from the file.

    RETURNS

        True if character object found in serialization file
        False if character object NOT found in serialization file

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public boolean setMainProfile(){
        // Try to open file =>
        try{
            FileInputStream fileIn = openFileInput("savedCharacterProfile.txt");
            ObjectInputStream inputReader = new ObjectInputStream(fileIn);
            // Set character object from file
            mainCharacter = (Character) inputReader.readObject();
            inputReader.close();
            fileIn.close();
            return true;

        }
        // Else => catch exception, return false
        catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    /**/
    /*
    public void forceClear()

    NAME

        forceClear - force clears the main character serialization file for utility purposes and testing

    SYNOPSIS

        public void forceClear()
            FileOutputStream fileOut         --> file output stream
            OutputStreamWriter outputWriter  --> output object stream writer

    DESCRIPTION

        This method is used as a utility to force clear the savedCharacterProfile.txt file

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void forceClear(){
        try{
            FileOutputStream fileOut = openFileOutput("savedCharacterProfile.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileOut);
            outputWriter.write("");
            outputWriter.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
