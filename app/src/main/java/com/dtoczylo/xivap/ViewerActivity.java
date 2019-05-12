/**/
/*

NAME

        Viewer Activity Controller

PURPOSE

        This is the controller that handles the character view activity. It will set
        a character object from the serialization file, if there is one, and display
        each of the attributes for a character object onto the screen. It will also
        generate percentage views for the characters total mount/minion collection.

AUTHOR

        Desiree Toczylowski

DATE

        5/8/2019

*/
/**/
package com.dtoczylo.xivap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class ViewerActivity extends AppCompatActivity {

    // Initialize character object to be used upon reading serialization file
    Character mainCharacterProfile = new Character();

    // Initialize variables for mount and minion total
    final int radius = 40;
    final int margin = 0;
    final float mountTotal = 136;
    final float minionTotal = 311;

    // Initialize view ids
    final Transformation trasnformation = new RoundedCornersTransformation(radius, margin);
    ProgressBar progressBar;

    /**/
    /*
    public void onCreate()

    NAME

        onCreate - this formats the view of the activity on creation

    SYNOPSIS

        public void onCreate(Bundle savedInstanceState)
            Bundle savedInstanceState   --> if the activity of being re-initialized after previously being shut down this bundle
                                            contains the data it most recently supplied in onSaveInstanceState(Bundle)
            ProgressBar progress bar    --> progress bar for transition into screen and leaving activity
            TextView displayATTRIBUTE   --> text view id for ATTRIBUTE of character object
            ImageView displayATTRIBUTE  --> image view id for ATTRIBUTE of character object
            String[] BATTLECLASS        --> string array of character objects BATTLECLASS category: tank, healer, mele, ranged
            String[] BATTLECLASSICONS   --> string array of character objects BATTLECLASS ICONS category: tank, healer, mele, ranged
            Float minionCountPercentage --> float to calculate mount percentage
            Float mountCountPercentage  --> float to calculate minion percentage
            Float totalMinion           --> float to hold minion total
            Float totalMount            --> float to hold mount total
            Integer displayMinionCount  --> integer for display minion total
            Integer displayMountCount   --> integer for display mount total

    DESCRIPTION

        The onCreate method that is called when the activity is starting. It will create the view to reflect
        the character object that has been set. It pre-populate text and image views with data that has been
        stored within the character object. There are many text view and image views being used here but
        they are all primarily being used to populate XML boxes.

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // Set XML for activity
        setContentView(R.layout.activity_viewer);
        // Set screen orientation
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Set progress bar view ids
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.INVISIBLE);

        // Set character general information text and image view ids
        TextView displayName = findViewById(R.id.textView_v_charName);
        TextView displayServer = findViewById(R.id.textView_v_charServer);
        ImageView displayImage = findViewById(R.id.imageView_v_charImage);
        TextView displayTitle = findViewById(R.id.textView_v_charTitle);
        TextView displayRace = findViewById(R.id.textView_v_charRace);
        TextView displayGuardian = findViewById(R.id.textView_v_charGuardian);
        ImageView displayGuardianIcon = findViewById(R.id.imageView_v_charGuardianIcon);
        ImageView displayCityIcon = findViewById(R.id.imageView_v_charCityIcon);
        TextView displayCity = findViewById(R.id.textView_v_charCity);
        ImageView displayGCIcon = findViewById(R.id.imageView_v_charGCIcon);
        TextView displayGC = findViewById(R.id.textView_v_charGC);
        ImageView displayFCIconBase = findViewById(R.id.imageView_v_charFCIconBase);
        ImageView displayFCIcon = findViewById(R.id.imageView_v_charFCIcon);
        TextView displayFC = findViewById(R.id.textView_v_charFC);

        // Set character mount and minion percentage view id
        TextView displayMinionPercent = findViewById(R.id.textView_v_charMinionPercent);
        TextView displayMountPercent = findViewById(R.id.textView_v_charMountPercent);

        // Set character battle class view ids and image view icon ids
        // BATTLECLASS: TANKS
        TextView displayPLD = findViewById(R.id.textView_v_charPld);
        ImageView displayPLDIcon = findViewById(R.id.imageView_v_charPldIcon);
        TextView displayWAR = findViewById(R.id.textView_v_charWar);
        ImageView displayWARIcon = findViewById(R.id.imageView_v_charWarIcon);
        TextView displayDK = findViewById(R.id.textView_v_charDk);
        ImageView displayDKIcon = findViewById(R.id.imageView_v_charDkIcon);

        // BATTLECLASS: HEALERS
        TextView displayWHM = findViewById(R.id.textView_v_charWhm);
        ImageView displayWHMIcon = findViewById(R.id.imageView_v_charWhmIcon);
        TextView displaySCH = findViewById(R.id.textView_v_charSch);
        ImageView displaySCHIcon = findViewById(R.id.imageView_v_charSchIcon);
        TextView displayAST = findViewById(R.id.textView_v_charAst);
        ImageView displayASTIcon = findViewById(R.id.imageView_v_charAstIcon);

        // BATTLECLASS: MELE
        TextView displayMNK = findViewById(R.id.textView_v_charMNK);
        ImageView displayMNKIcon = findViewById(R.id.imageView_v_charMNKIcon);
        TextView displayDRG = findViewById(R.id.textView_v_charDRG);
        ImageView displayDRGIcon = findViewById(R.id.imageView_v_charDRGIcon);
        TextView displayNIN = findViewById(R.id.textView_v_charNIN);
        ImageView displayNINIcon = findViewById(R.id.imageView_v_charNINIcon);
        TextView displaySAM = findViewById(R.id.textView_v_charSAM);
        ImageView displaySAMIcon = findViewById(R.id.imageView_v_charSAMIcon);

        // BATTLECLASS: RANGED
        TextView displayBRD = findViewById(R.id.textView_v_charBRD);
        ImageView displayBRDIcon = findViewById(R.id.imageView_v_charBRDIcon);
        TextView displayMCH = findViewById(R.id.textView_v_charMCH);
        ImageView displayMCHIcon = findViewById(R.id.imageView_v_charMCHIcon);
        TextView displayBLM = findViewById(R.id.textView_v_charBLM);
        ImageView displayBLMIcon = findViewById(R.id.imageView_v_charBLMIcon);
        TextView displaySMN = findViewById(R.id.textView_v_charSMN);
        ImageView displaySMNIcon = findViewById(R.id.imageView_v_charSMNIcon);
        TextView displayRDM = findViewById(R.id.textView_v_charRDM);
        ImageView displayRDMIcon = findViewById(R.id.imageView_v_charRDMIcon);
        TextView displayBLU = findViewById(R.id.textView_v_charBLU);
        ImageView displayBLUIcon = findViewById(R.id.imageView_v_charBLUIcon);

        // If there is a main profile set =>
        if(setMainProfile() == true){
            // Display character information from character object
            // Name & Server
            displayServer.setText("\uD83C\uDF10 ");
            displayServer.append(mainCharacterProfile.GetServer().toUpperCase());
            displayName.setText(mainCharacterProfile.GetName());

            // Formatting for title
            if(!mainCharacterProfile.GetTitle().isEmpty()){
                displayTitle.setText("« ");
                displayTitle.append(mainCharacterProfile.GetTitle().toUpperCase());
                displayTitle.append(" »");
            }

            // General information
            displayRace.setText(mainCharacterProfile.GetRace());
            displayGuardian.setText(mainCharacterProfile.GetGuardian());
            displayCity.setText(mainCharacterProfile.GetCity());
            displayGC.setText(mainCharacterProfile.GetGC());
            displayFC.setText(mainCharacterProfile.GetFC());

            // BATTLECLASS: TANKS
            String tanks[] = mainCharacterProfile.GetTanks();
            String tankIcons[] = mainCharacterProfile.GetTankIcons();

            // BATTLECLASS: HEALERS
            String healers[] = mainCharacterProfile.GetHealers();
            String healerIcons[] = mainCharacterProfile.GetHealerIcons();

            // BATTLECLASS: MELE
            String mele[] = mainCharacterProfile.GetMele();
            String meleIcons[] = mainCharacterProfile.GetMeleIcons();

            // BATTLECLASS: RANGED
            String ranged[] = mainCharacterProfile.GetRanged();
            String rangedIcons[] = mainCharacterProfile.GetRangedIcons();

            // Set tank levels
            displayPLD.setText(tanks[0]);
            displayWAR.setText(tanks[1]);
            displayDK.setText(tanks[2]);

            // Set healer levels
            displayWHM.setText(healers[0]);
            displaySCH.setText(healers[1]);
            displayAST.setText(healers[2]);

            // Set mele levels
            displayMNK.setText(mele[0]);
            displayDRG.setText(mele[1]);
            displayNIN.setText(mele[2]);
            displaySAM.setText(mele[3]);

            // Set ranged levels
            displayBRD.setText(ranged[0]);
            displayMCH.setText(ranged[1]);
            displayBLM.setText(ranged[2]);
            displaySMN.setText(ranged[3]);
            displayRDM.setText(ranged[4]);
            displayBLU.setText(ranged[5]);

            // Use Picasso Library to inject URL into image view icon ids: TANKS
            Picasso.get().load(tankIcons[0]).into(displayPLDIcon);
            Picasso.get().load(tankIcons[1]).into(displayWARIcon);
            Picasso.get().load(tankIcons[2]).into(displayDKIcon);

            // Use Picasso Library to inject URL into image view icon ids: HEALERS
            Picasso.get().load(healerIcons[0]).into(displayWHMIcon);
            Picasso.get().load(healerIcons[1]).into(displaySCHIcon);
            Picasso.get().load(healerIcons[2]).into(displayASTIcon);

            // Use Picasso Library to inject URL into image view icon ids: MELE
            Picasso.get().load(meleIcons[0]).into(displayMNKIcon);
            Picasso.get().load(meleIcons[1]).into(displayDRGIcon);
            Picasso.get().load(meleIcons[2]).into(displayNINIcon);
            Picasso.get().load(meleIcons[3]).into(displaySAMIcon);

            // Use Picasso Library to inject URL into image view icon ids: RANGED
            Picasso.get().load(rangedIcons[0]).into(displayBRDIcon);
            Picasso.get().load(rangedIcons[1]).into(displayMCHIcon);
            Picasso.get().load(rangedIcons[2]).into(displayBLMIcon);
            Picasso.get().load(rangedIcons[3]).into(displaySMNIcon);
            Picasso.get().load(rangedIcons[4]).into(displayRDMIcon);
            Picasso.get().load(rangedIcons[5]).into(displayBLUIcon);


            // Initialize mount/minion totals for percentages
            Float minionCountPercentage, mountCountPercentage, totalMinion, totalMount;
            Integer displayMinionCount, displayMountCount;

            // Get total minion from character object & calculate percentage
            totalMinion = (float) mainCharacterProfile.GetMinionTotal();
            minionCountPercentage = (totalMinion / minionTotal) * 100;
            displayMinionCount = Math.round(minionCountPercentage);

            // Set minion percentage for text view
            displayMinionPercent.setText( displayMinionCount.toString());
            displayMinionPercent.append("%");

            // Get total mount from character object & calculate percentage
            totalMount = (float) mainCharacterProfile.GetMountTotal();
            mountCountPercentage = (totalMount / mountTotal) * 100;
            displayMountCount = Math.round(mountCountPercentage);

            // Set mount percentage for text view
            displayMountPercent.setText(displayMountCount.toString());
            displayMountPercent.append("%");

            // Use Picasso to inject FC, GC, City & Guaridan icon URLS from character object
            // FC ICON BASE
            if(!mainCharacterProfile.GetFCIconBase().isEmpty()){
                Picasso.get().load(mainCharacterProfile.GetFCIconBase()).transform(trasnformation).into(displayFCIconBase);
            }
            // FC ICON
            if(!mainCharacterProfile.GetFCIcon().isEmpty()) {
                Picasso.get().load(mainCharacterProfile.GetFCIcon()).transform(trasnformation).into(displayFCIcon);
            }

            // GC ICON
            if(!mainCharacterProfile.GetGCIcon().isEmpty()) {
                Picasso.get().load(mainCharacterProfile.GetGCIcon()).into(displayGCIcon);
            }

            // CITY ICON
            if(!mainCharacterProfile.GetCityIcon().isEmpty()) {
                Picasso.get().load(mainCharacterProfile.GetCityIcon()).into(displayCityIcon);
            }

            // GUARDIAN ICON
            if(!mainCharacterProfile.GetGuardianIcon().isEmpty()) {
                Picasso.get().load(mainCharacterProfile.GetGuardianIcon()).into(displayGuardianIcon);
            }

            // Use Picasso to inject character image URL
            Picasso.get().load(mainCharacterProfile.GetImage()).transform(trasnformation).into(displayImage);

        // ELSE => no character profile set
        }else{
            displayName.setText("NO PROFILE SET");
        }
    }

    /**/
    /*
    public void returnButton()

    NAME

        returnButton - returns ViewerActivity to MainActivity view

    SYNOPSIS

        public void returnButton(View view)
            View view       --> parameter view
            progressBar2    --> set the progress bar to visible
            Intent intent   --> intent for new activity

    DESCRIPTION

        The return button allows to return to the Main Activity view from the Viewer Activity view.
        Setting the progress bar to visible for the transition.

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void returnButton(View view) {
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = new Intent(ViewerActivity.this, MainActivity.class);
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
            mainCharacterProfile = (Character) inputReader.readObject();
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

}
