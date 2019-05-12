/**/
/*

NAME

        Category Battle Activity

PURPOSE
        This is an activity controller for the category display of the battle achievements.
        The methods used here initialize the RecyclerView and is responsible for populating the achievement
        data list to be displayed with the achievement adapter class.

AUTHOR

        Desiree Toczylowski

DATE

        5/8/2019

*/
/**/

package com.dtoczylo.xivap;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class CategoryBattleActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    // Initializations
    private RecyclerView recyclerView;
    private AchievementAdapter adapter;
    private ArrayList<Achievement> achievementArrayList;
    private Achievement[] battleAchievements;
    private Character mainCharacter;
    ProgressBar progressBar;

    /**/
    /*
    public void onCreate()

    NAME

        onCreate - this formats the view of the activity on creation

    SYNOPSIS

        public void onCreate(Bundle savedInstanceState)
            Bundle savedInstanceState               --> if the activity of being re-initialized after previously being shut down this bundle
                                                        contains the data it most recently supplied in onSaveInstanceState(Bundle)
            Spinner spinner                         --> spinner for the filter options
            String [] dropDownList                  --> string containing the filter options (all/completed/incomplete)
            ArrayAdapter<String> spinnerAdapter     --> creates a new array adapter
            progressBar                             --> id of the progress bar
            achievementArrayList                    --> new arrayList
            mainCharacter                           --> new character object
            battleAchievements                      --> all battle achievements for character object

    DESCRIPTION

        The onCreate method that is called when the activity is starting

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
        // Set XML for this controller
        setContentView(R.layout.activity_battle);
        // Set screen orientation
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Initializations
        Spinner spinner = findViewById(R.id.spinner_battle_dropdown);
        String [] dropDownList = new String[] {"FILTER: All", "FILTER: Completed", "FILTER: Incomplete"};
        spinner.setPrompt("FILTER VIEW");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, R.layout.spinner_theme, dropDownList);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_theme);
        spinner.setAdapter(spinnerAdapter);

        progressBar = findViewById(R.id.progressBar5);
        progressBar.setVisibility(View.VISIBLE);
        achievementArrayList = new ArrayList<>();
        mainCharacter = new Character();
        battleAchievements = mainCharacter.GetAllBattle();
        achievementArrayList.clear();

        // Check if main profile has been selected
        if(setMainProfile() == true){

            // If true => populate the array list with the battle achievements
            for(int i = 0; i < battleAchievements.length; i++){
                if(!battleAchievements[i].GetTitle().equals("")){
                    achievementArrayList.add(battleAchievements[i]);
                }
            }
        }

        // Set view on spinner selection
        spinner.setOnItemSelectedListener(this);
    }

    /**/
    /*
    public void returnButton()

    NAME

        returnButton - returns CategoryBattleActivity to TrackerActivity view

    SYNOPSIS

        public void returnButton(View view)
            View view       --> parameter view
            progressBar     --> set the progress bar to visible
            Intent intent   --> intent for new activity

    DESCRIPTION

        The return button allows to return to the Tracker Activity view from the Category Battle Activity view

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
        Intent intent = new Intent(CategoryBattleActivity.this, TrackerActivity.class);
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
    public void onItemSelected()

    NAME

        onItemSelected - this method sets the array list for the adapter based on the current
                         selection of the spinner

    SYNOPSIS

        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            AdapterView<?> parent      --> parameter parent adapter view
            View view                  --> view
            int position               --> position of the card
            long id                    --> id of the item
            Spinner spinner            --> spinner view id
            int spinnerState           --> index of the spinner selection
            battleAchievements         --> current character objects battle achievements
            achievementArrayList       --> array list created from array of battle achievements

    DESCRIPTION

        onItemSelected is responsible for populating and changing the data list of achievements for the
        adapter. Depending on the filter selected the achievements will be displayed upon change and
        initialization.

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        // Initializations
        Spinner spinner = findViewById(R.id.spinner_battle_dropdown);
        int spinnerState = spinner.getSelectedItemPosition();
        battleAchievements = mainCharacter.GetAllBattle();
        achievementArrayList.clear();

        // Spinner state influences the display of achievements
        switch (spinnerState){
            // Filter: All shows all achievements completed/incomplete
            case 0:
                if(setMainProfile() == true){

                    for(int i = 0; i < battleAchievements.length; i++){
                        if(!battleAchievements[i].GetTitle().equals("")){
                            battleAchievements[i].SetDescription(setAchievementDescriptions(battleAchievements[i].GetTitle()));
                            achievementArrayList.add(battleAchievements[i]);
                        }
                    }
                }
                break;
            // Filter: Completed shows all completed achievements only
            case 1:
                if(setMainProfile() == true){

                    for(int i = 0; i < battleAchievements.length; i++){
                        if(!battleAchievements[i].GetTitle().equals("")){
                            if(battleAchievements[i].GetObtained().equals("Yes")){
                                battleAchievements[i].SetDescription(setAchievementDescriptions(battleAchievements[i].GetTitle()));
                                achievementArrayList.add(battleAchievements[i]);
                            }
                        }
                    }
                }
                break;
            // Filter: Incomplete shows all incomplete achievements only
            case 2:
                if(setMainProfile() == true){

                    for(int i = 0; i < battleAchievements.length; i++){
                        if(!battleAchievements[i].GetTitle().equals("")){
                            if(battleAchievements[i].GetObtained().equals("No")){
                                battleAchievements[i].SetDescription(setAchievementDescriptions(battleAchievements[i].GetTitle()));
                                achievementArrayList.add(battleAchievements[i]);
                            }
                        }
                    }
                }
                break;
        }

        // Set RecyclerView for display
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new AchievementAdapter(achievementArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CategoryBattleActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.INVISIBLE);
        spinner.setOnItemSelectedListener(this);
    }

    /**/
    /*
    public void onNothingSelected()

    NAME

        onNothingSelected - required for the OnSelectedItem implementation of the class

    SYNOPSIS

        public void onNothingSelected(AdapterView<?> arg0)
            AdapterView<?> arg0     --> AdapterView


    DESCRIPTION

        onNothingSelected is required for this class even though the spinner never has nothing
        selected.

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void onNothingSelected(AdapterView<?> arg0){

    }

    /**/
    /*
    public void setAchievementDescriptions()

    NAME

        setAchievementDescriptions - parses a text file of descriptions to display for given achievement

    SYNOPSIS

        public String setAchievementDescriptions(String title)
            String a_title              --> parameter title of achievement
            BufferedReader reader       --> reader
            String[] lineContents       --> text file line contents
            String lineTitle            --> achievement title from file
            String lineDescription      --> achievement description from file
            String returnDescription    --> description to be returned


    DESCRIPTION

        setAchievementDescriptions accepts an achievement title to compare title against in a text file
        called descriptions.txt. The file contains all titles and associated descriptions so that when
        viewing the battle achievements the descriptions are also visible. The HTML when parsing the achievements
        does not include descriptions so this part has to be done manually. The best solution was to create a
        text file of all descriptions and parse that file to populate the field.

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String setAchievementDescriptions(String a_title){

        // Initializations
        BufferedReader reader = null;
        String[] lineContents;
        String lineTitle = "", lineDescription = "", returnDescription = "";

        // Try to open text file
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("descriptions.txt")));
            String line;

            // While not EOF
            while((line = reader.readLine()) != null){
                // Get lines
                lineContents = line.split("=");
                lineTitle = lineContents[0];
                lineDescription = lineContents[1];

                // Get description for matching title
                if(lineTitle.equals(a_title)){
                    returnDescription = lineDescription;
                    break;
                }
            }
        // If can't open file => catch exception
        } catch (IOException e){
            e.printStackTrace();
        }

        // Try to close the reader
        try {
            reader.close();

        // If can't close => catch exception
        } catch (IOException e){
            e.printStackTrace();
        }

        // return description for achievement object
        return returnDescription;
    }

    /**/
    /*
    public boolean setMainProfile()

    NAME

        setMainProfile - checks to see if there is a character object located in the savedCharacterProfile.txt file
                          upon returning true, will set the mainCharacter character object to the character object
                          located within the file

    SYNOPSIS

        public boolean setMainProfile()
            FileInputStream fileIn          --> input stream
            ObjectInputStream inputReader   --> object input stream reader
            mainCharacter                   --> character object whose achievements are being viewed

    DESCRIPTION

        setMainProfile is a method that checks to see if there is a character object stored within the
        serialization file of a character. If there is a character located in that file, it will return
        true. If there is no character object found, it will return false.

    RETURNS

        True if character object found, False if no character object found

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public boolean setMainProfile(){
        // Try to open file
        try{
            FileInputStream fileIn = openFileInput("savedCharacterProfile.txt");
            ObjectInputStream inputReader = new ObjectInputStream(fileIn);
            mainCharacter = (Character) inputReader.readObject();
            inputReader.close();
            fileIn.close();
            return true;

        }
        // If can't => catch exception, return false
        catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

}
