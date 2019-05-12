/**/
/*

NAME

        Tracker Activity Controller

PURPOSE

        This is the controller that handles the tracker character activity. This activity within the app
        analyzes character data found within a character object in the serialization file and displays the
        data to the screen. This will break each achievement into categories and view statistics per
        category depending on the character data.

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
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class TrackerActivity extends AppCompatActivity {

    // Initialize character object
    Character mainCharacter = new Character();

    // Initialize misc. variables
    int battleAchievementTotal = 304;
    int overallOutOfTotal = 0, overallPoints = 0;

    // Initialize view progress bar
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
            de.hdodenhof.circleimageview.CircleImageView icon   --> CircleImage library to hold the character icon
            ProgressBar progressBar     --> progress bar for loading
            TextView loggedInStatus     --> text view id for logged in status
            TextView achievementPoints  --> text view id for achievement points
            ImageView CATEGORYNAMEBox      --> image view id for the given category box
            ImageView CATEGORYNAMEBar       --> image view id for the given category bar
            TextView CATEGORYTitle          --> text view id for the given category title
            TextView CATEGORYPercentage     --> text view id for the given category percentage
            TextView battleDisplayObtained  --> text view id for the obtained/unattained achievement category objects
            TextView overall                --> text view id for overall information
            TextView overall2               --> text view id for overall information (lower pane)
            float overallAchievements       --> float for calculating overall achievement value
            float overallAchievementPercentage  --> float for calculating overall achievement percentage
            int displayOverallAchievementPercentage --> int for displaying the percentage

    DESCRIPTION

        The onCreate method that is called when the activity is starting. It will create the view to reflect
        the search of a character. It pre-populate text and image views to reflect the appropriate category
        and the category percentages. Each box reflects a different category and will generate statistics
        depending on the character objects obtained/unattained achievements.

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
        setContentView(R.layout.activity_tracker);
        // Set screen orientation
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Set all view ids within the XML to display data
        progressBar = findViewById(R.id.progressBar4);
        progressBar.setVisibility(View.INVISIBLE);

        // General view ids
        de.hdodenhof.circleimageview.CircleImageView icon = findViewById(R.id.circleView_characterIcon2);
        TextView loggedInStatus = findViewById(R.id.textView_loggedInStatusIcon2);
        TextView loggedInStatusIcon = findViewById(R.id.textView_loggedInStatusIcon2);
        TextView achievementPoints = findViewById(R.id.textview_achievementPoint);

        // Category box view ids
        ImageView battleBox = findViewById(R.id.imageView6);
        ImageView pvpBox = findViewById(R.id.imageView7);
        ImageView charBox = findViewById(R.id.imageView8);
        ImageView itemsBox = findViewById(R.id.imageView9);
        ImageView craftBox = findViewById(R.id.imageView10);
        ImageView gatherBox = findViewById(R.id.imageView11);
        ImageView questBox = findViewById(R.id.imageView12);
        ImageView exploreBox = findViewById(R.id.imageView13);
        ImageView gcBox = findViewById(R.id.imageView14);

        // Category box view ids
        ImageView battleBar = findViewById(R.id.imageView16);
        ImageView pvpBar = findViewById(R.id.imageView17);
        ImageView charBar = findViewById(R.id.imageView18);
        ImageView itemsBar = findViewById(R.id.imageView19);
        ImageView craftBar = findViewById(R.id.imageView20);
        ImageView gatherBar = findViewById(R.id.imageView21);
        ImageView questBar = findViewById(R.id.imageView23);
        ImageView exploreBar = findViewById(R.id.imageView24);
        ImageView gcBar = findViewById(R.id.imageView25);

        // Category battle
        TextView battleTitle = findViewById(R.id.textview_battleTitle);
        TextView battlePercentage = findViewById(R.id.textview_battlePercent);
        TextView battleDisplayObtained = findViewById(R.id.textView_battleIcon);

        // Category pvp
        TextView pvpTitle = findViewById(R.id.textview_pvpTitle);
        TextView pvpPercentage = findViewById(R.id.textview_pvpPercent);
        TextView pvpDisplayObtained = findViewById(R.id.textView_pvpIcon);

        // Category title
        TextView charTitle = findViewById(R.id.textview_charTitle);
        TextView charPercentage = findViewById(R.id.textview_charPercent);
        TextView charDisplayObtained = findViewById(R.id.textView_charIcon);

        // Category items
        TextView itemsTitle = findViewById(R.id.textview_itemsTitle);
        TextView itemsPercentage = findViewById(R.id.textview_itemsPercent);
        TextView itemsDisplayObtained = findViewById(R.id.textView_itemsIcon);

        // Category crafting
        TextView craftingTitle = findViewById(R.id.textview_craftingTitle);
        TextView craftingPercentage = findViewById(R.id.textview_craftingPercent);
        TextView craftingDisplayObtained = findViewById(R.id.textView_craftingIcon);

        // Category gathering
        TextView gatheringTitle = findViewById(R.id.textview_gatheringTitle);
        TextView gatheringPercentage = findViewById(R.id.textview_gatheringPercent);
        TextView gatheringDisplayObtained = findViewById(R.id.textView_gatheringIcon);

        // Category quests
        TextView questsTitle = findViewById(R.id.textview_questsTitle);
        TextView questsPercentage = findViewById(R.id.textview_questsPercent);
        TextView questsDisplayObtained = findViewById(R.id.textView_questsIcon);

        // Category exploration
        TextView explorationTitle = findViewById(R.id.textview_explorationTitle);
        TextView explorationPercentage = findViewById(R.id.textview_explorationPercent);
        TextView explorationDisplayObtained = findViewById(R.id.textView_explorationIcon);

        // Category gc
        TextView gcTitle = findViewById(R.id.textview_gcTitle);
        TextView gcPercentage = findViewById(R.id.textview_gcPercent);
        TextView gcDisplayObtained = findViewById(R.id.textView_gcIcon);

        // Overal view data
        TextView overall = findViewById(R.id.textview_overall);
        TextView overall2 = findViewById(R.id.textview_overall2);
        float overallAchievements = 0;
        float overallAchievementPercentage = 0;
        int displayOverallAchievementPercentage = 0;


        // Check if character object is set =>
        if(setMainProfile() == true){
            // Load in character icon using Picasso
            Picasso.get()
                    .load(mainCharacter.GetIcon())
                    .into(icon);

            // Set point value
            String pointValue = mainCharacter.GetPoints();

            // If point value is not empty
            if(!pointValue.equals("")){

                // Prepare screen for data display: points
                achievementPoints.setText("\uD83C\uDF1F");
                achievementPoints.append(mainCharacter.GetPoints());
                achievementPoints.append("\uD83C\uDF1F");

                // Categories
                setBoxView(battleTitle, battlePercentage, battleDisplayObtained, "Battle");
                setBoxView(pvpTitle, pvpPercentage, pvpDisplayObtained, "PvP");
                setBoxView(charTitle, charPercentage, charDisplayObtained, "Character");
                setBoxView(itemsTitle, itemsPercentage, itemsDisplayObtained, "Items");
                setBoxView(craftingTitle, craftingPercentage, craftingDisplayObtained, "Crafting");
                setBoxView(gatheringTitle, gatheringPercentage, gatheringDisplayObtained, "Gathering");
                setBoxView(questsTitle, questsPercentage, questsDisplayObtained, "Quests");
                setBoxView(explorationTitle, explorationPercentage, explorationDisplayObtained, "Exploration");
                setBoxView(gcTitle, gcPercentage, gcDisplayObtained, "GC");

                // Percent Overall
                overallAchievements = Float.parseFloat(mainCharacter.GetPoints());
                overallAchievementPercentage = (overallAchievements / overallPoints) * 100;
                displayOverallAchievementPercentage = Math.round(overallAchievementPercentage);
                overall.setText("Overall Percentage: \n" + Integer.toString(displayOverallAchievementPercentage) + "%");
                overall2.setText("Total Points Possible: \n / " +Integer.toString(overallPoints));

            } else {
                // Else profile is private, remove all boxes from view
                achievementPoints.setText("PRIVATE");
                battleBox.setVisibility(View.GONE);
                pvpBox.setVisibility(View.GONE);
                charBox.setVisibility(View.GONE);
                itemsBox.setVisibility(View.GONE);
                craftBox.setVisibility(View.GONE);
                gatherBox.setVisibility(View.GONE);
                questBox.setVisibility(View.GONE);
                exploreBox.setVisibility(View.GONE);
                gcBox.setVisibility(View.GONE);

                battleBar.setVisibility(View.GONE);
                pvpBar.setVisibility(View.GONE);
                charBar.setVisibility(View.GONE);
                itemsBar.setVisibility(View.GONE);
                craftBar.setVisibility(View.GONE);
                gatherBar.setVisibility(View.GONE);
                questBar.setVisibility(View.GONE);
                exploreBar.setVisibility(View.GONE);
                gcBar.setVisibility(View.GONE);
            }

        // Else character object not found => no profile set
        } else {
            loggedInStatus.setText("NO PROFILE SET");
            icon.setImageResource(R.drawable.searching);
        }

    }

    /**/
    /*
    public boolean setMainProfile()

    NAME

        setMainProfile - this method is used to set a main character profile from the serialization
        file

    SYNOPSIS

       public boolean setMainProfile()
            FileInputStream fileIn  --> file input reader
            ObjectInputStream inputReader   --> object input stream
            Character mainCharacter         --> character object

    DESCRIPTION

        The setMainProfile function is used to set the main character object in this view
        from the character object in the serialization file if there is one.

    RETURNS

        True if object successfully set from file
        False if object no successfully set from file

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public boolean setMainProfile(){
        try{
            FileInputStream fileIn = openFileInput("savedCharacterProfile.txt");
            ObjectInputStream inputReader = new ObjectInputStream(fileIn);
            mainCharacter = (Character) inputReader.readObject();
            inputReader.close();
            fileIn.close();
            return true;

        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
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
    public void returnButton()

    NAME

        returnButton - returns TrackerActivity to MainActivity view

    SYNOPSIS

        public void returnButton(View view)
            View view       --> parameter view
            progressBar     --> set the progress bar to visible
            Intent intent   --> intent for new activity

    DESCRIPTION

        The return button allows to return to the Main Activity view from the Tracker Activity view.
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
        Intent intent = new Intent(TrackerActivity.this, MainActivity.class);
        startActivity(intent);
    }

    /**/
    /*
    public void battleButton()

    NAME

        battleButton - changes activity from TrackerActivity to CategoryBattleActivity view

    SYNOPSIS

        public void battleButton(View view)
            View view       --> parameter view
            progressBar     --> set the progress bar to visible
            Intent intent   --> intent for new activity

    DESCRIPTION

        The battleButton allows to move from the Tracker Activity view to the CategoryBattleActivity view
        Setting the progress bar to visible for the transition.

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void battleButton(View view) {
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = new Intent(TrackerActivity.this, CategoryBattleActivity.class);
        startActivity(intent);
    }

    /**/
    /*
    public void pvpButton()

    NAME

        pvpButton - changes activity from TrackerActivity to CategoryPvPActivity view

    SYNOPSIS

        public void pvpButton(View view)
            View view       --> parameter view
            progressBar     --> set the progress bar to visible
            Intent intent   --> intent for new activity

    DESCRIPTION

        The pvpButton button allows to move from the Tracker Activity view to the CategoryPvPActivity view
        Setting the progress bar to visible for the transition.

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void pvpButton(View view) {
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = new Intent(TrackerActivity.this, CategoryPvPActivity.class);
        startActivity(intent);
    }

    /**/
    /*
    public void characterButton()

    NAME

        characterButton - changes activity from TrackerActivity to CategoryCharacterActivity view

    SYNOPSIS

        public void characterButton(View view)
            View view       --> parameter view
            progressBar     --> set the progress bar to visible
            Intent intent   --> intent for new activity

    DESCRIPTION

        The pvpButton button allows to move from the Tracker Activity view to the CategoryCharacterActivity view
        Setting the progress bar to visible for the transition.

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void characterButton(View view) {
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = new Intent(TrackerActivity.this, CategoryCharacterActivity.class);
        startActivity(intent);
    }

    /**/
    /*
    public void itemsButton()

    NAME

        itemsButton - changes activity from TrackerActivity to CategoryItemsActivity view

    SYNOPSIS

        public void itemsButton(View view)
            View view       --> parameter view
            progressBar     --> set the progress bar to visible
            Intent intent   --> intent for new activity

    DESCRIPTION

        The itemsButton button allows to move from the Tracker Activity view to the CategoryItemsActivity view
        Setting the progress bar to visible for the transition.

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void itemsButton(View view) {
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = new Intent(TrackerActivity.this, CategoryItemsActivity.class);
        startActivity(intent);
    }

    /**/
    /*
    public void craftingButton()

    NAME

        craftingButton - changes activity from TrackerActivity to CategoryCraftingActivity view

    SYNOPSIS

        public void craftingButton(View view)
            View view       --> parameter view
            progressBar     --> set the progress bar to visible
            Intent intent   --> intent for new activity

    DESCRIPTION

        The craftingButton button allows to move from the Tracker Activity view to the CategoryCraftingActivity view
        Setting the progress bar to visible for the transition.

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void craftingButton(View view) {
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = new Intent(TrackerActivity.this, CategoryCraftingActivity.class);
        startActivity(intent);
    }

    /**/
    /*
    public void gatheringButton()

    NAME

        gatheringButton - changes activity from TrackerActivity to CategoryGatheringActivity view

    SYNOPSIS

        public void gatheringButton(View view)
            View view       --> parameter view
            progressBar     --> set the progress bar to visible
            Intent intent   --> intent for new activity

    DESCRIPTION

        The gatheringButton button allows to move from the Tracker Activity view to the CategoryGatheringActivity view
        Setting the progress bar to visible for the transition.

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void gatheringButton(View view) {
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = new Intent(TrackerActivity.this, CategoryGatheringActivity.class);
        startActivity(intent);
    }

    /**/
    /*
    public void questsButton()

    NAME

        questsButton - changes activity from TrackerActivity to CategoryQuestsActivity view

    SYNOPSIS

        public void questsButton(View view)
            View view       --> parameter view
            progressBar     --> set the progress bar to visible
            Intent intent   --> intent for new activity

    DESCRIPTION

        The questsButton button allows to move from the Tracker Activity view to the CategoryQuestsActivity view
        Setting the progress bar to visible for the transition.

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void questsButton(View view) {
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = new Intent(TrackerActivity.this, CategoryQuestsActivity.class);
        startActivity(intent);
    }

    /**/
    /*
    public void explorationButton()

    NAME

        explorationButton - changes activity from TrackerActivity to CategoryExplorationActivity view

    SYNOPSIS

        public void explorationButton(View view)
            View view       --> parameter view
            progressBar     --> set the progress bar to visible
            Intent intent   --> intent for new activity

    DESCRIPTION

        The explorationButton button allows to move from the Tracker Activity view to the CategoryExplorationActivity view
        Setting the progress bar to visible for the transition.

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void explorationButton(View view) {
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = new Intent(TrackerActivity.this, CategoryExplorationActivity.class);
        startActivity(intent);
    }

    /**/
    /*
    public void gcButton()

    NAME

        gcButton - changes activity from TrackerActivity to CategoryGCActivity view

    SYNOPSIS

        public void gcButton(View view)
            View view       --> parameter view
            progressBar     --> set the progress bar to visible
            Intent intent   --> intent for new activity

    DESCRIPTION

        The gcButton button allows to move from the Tracker Activity view to the CategoryGCActivity view
        Setting the progress bar to visible for the transition.

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void gcButton(View view) {
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = new Intent(TrackerActivity.this, CategoryGCActivity.class);
        startActivity(intent);
    }

    /**/
    /*
    public void setBoxView()

    NAME

        setBoxView - this is responsible for setting up each category box with the data to be
        analyzed and displayed within the activity XML

    SYNOPSIS

        public void setBoxView(TextView a_title, TextView a_percentageBox, TextView a_icon, String a_category)
            TextView a_title                    --> a text view id for title
            TextView a_percentageBox            --> a text view id for percentage
            TextView a_icon                     --> a text view id for icon
            String a_category                   --> a string category name
            float totalAchievements             --> float of total achievements value
            float totalAchievementsPercentage   --> float of total achievements percentage
            int displayTotalAchievementPercentage  --> int to display the achievements percentage
            Achievement [] achievementCategoryData  --> achievement array of category data
            int length                          --> int length of achievements
            int total                           --> int total obtained achievements
            String status                       --> string status: complete or incomplete
            int overallPoints                   --> keep track of all possible achievement points

    DESCRIPTION

        The setBoxView is responsible for receiving different image/text view ids to properly set
        depending on the category being sent it. The category string will direct the proper output
        within the switch statement.

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void setBoxView(TextView a_title, TextView a_percentageBox, TextView a_icon, String a_category){
        // Initialize achievement percentage values
        float totalAchievements = 0;
        float totalAchievementsPercentage = 0;
        int displayTotalAchievementPercentage = 0;

        // Initialize achievement array
        Achievement [] achievementCategoryData;

        // Set counters/trackers for the achievements lenghts/total
        int length = 0, total = 0;
        String status = "";

        // Depending on category name ..
        switch (a_category){
            case "Battle":
                // Get all character battle achievements
                achievementCategoryData = mainCharacter.GetAllBattle();

                // Get length of all achievements in category (all)
                length = achievementCategoryData.length;

                // Set title of category for box view)
                a_title.setText(mainCharacter.GetCategory(0));

                // Set icon of category for box view
                a_icon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.battlecategory, 0, 0, 0);

                // For each achievement ..
                for(int i = 0; i < length; i++){
                    // Get status
                    status = achievementCategoryData[i].GetObtained();

                    // Increase overall point value
                    overallPoints += Integer.parseInt(achievementCategoryData[i].GetPoint());

                    // Increase total obtained
                    if(status.equals("Yes")){
                        total += 1;
                    }
                }

                // Perform percentage calculation for category
                totalAchievements = (float) total;
                totalAchievementsPercentage = (totalAchievements / battleAchievementTotal) * 100;
                displayTotalAchievementPercentage = Math.round(totalAchievementsPercentage);

                // Set the box view in XML
                a_icon.setText(Integer.toString(displayTotalAchievementPercentage) + "%");
                a_percentageBox.setText("» " + Integer.toString(total) + " / " + Integer.toString(battleAchievementTotal) + " «");
                break;

            case "PvP":
                // Get all character PvP achievements
                int overAllTotalBattle = 0;
                achievementCategoryData = mainCharacter.GetAllPvP();

                // Get length of all achievements in category (all)
                length = achievementCategoryData.length;

                // Set title of category for box view)
                a_title.setText(mainCharacter.GetCategory(1));

                // Set icon of category for box view
                a_icon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pvpcategory, 0, 0, 0);

                // For each achievement ..
                for(int i = 0; i < length; i++){

                    // Get status
                    status = achievementCategoryData[i].GetObtained();

                    // Increase overall point value
                    overallPoints += Integer.parseInt(achievementCategoryData[i].GetPoint());

                    // Increase total obtained
                    if(status.equals("Yes")){
                        total += 1;
                        overAllTotalBattle += 1;
                    }
                    if(status.equals("No")){
                        overAllTotalBattle += 1;
                    }

                }

                // Perform percentage calculation for category
                totalAchievements = (float) total;
                totalAchievementsPercentage = (totalAchievements / overAllTotalBattle) * 100;
                displayTotalAchievementPercentage = Math.round(totalAchievementsPercentage);

                // Set the box view in XML
                a_icon.setText(Integer.toString(displayTotalAchievementPercentage) + "%");
                a_percentageBox.setText("» " + Integer.toString(total) + " / " + Integer.toString(overAllTotalBattle) + " «");
                break;

            case "Character":
                // Get all character Character achievements
                int overAllTotalChar = 0;
                achievementCategoryData = mainCharacter.GetAllCharacter();

                // Get length of all achievements in category (all)
                length = achievementCategoryData.length;

                // Set title of category for box view)
                a_title.setText(mainCharacter.GetCategory(2));

                // Set icon of category for box view
                a_icon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.charcategory, 0, 0, 0);

                // For each achievement ..
                for(int i = 0; i < length; i++){

                    // Get status
                    status = achievementCategoryData[i].GetObtained();

                    // Increase overall point value
                    overallPoints += Integer.parseInt(achievementCategoryData[i].GetPoint());

                    // Increase total obtained
                    if(status.equals("Yes")){
                        total += 1;
                        overAllTotalChar += 1;
                    }
                    if(status.equals("No")){
                        overAllTotalChar += 1;
                    }

                }

                // Perform percentage calculation for category
                totalAchievements = (float) total;
                totalAchievementsPercentage = (totalAchievements / overAllTotalChar) * 100;
                displayTotalAchievementPercentage = Math.round(totalAchievementsPercentage);

                // Set the box view in XML
                a_icon.setText(Integer.toString(displayTotalAchievementPercentage) + "%");
                a_percentageBox.setText("» " + Integer.toString(total) + " / " + Integer.toString(overAllTotalChar) + " «");
                break;

            case "Items":
                // Get all character Items achievements
                int overAllTotalItems = 0;
                achievementCategoryData = mainCharacter.GetAllItems();

                // Get length of all achievements in category (all)
                length = achievementCategoryData.length;

                // Set title of category for box view)
                a_title.setText(mainCharacter.GetCategory(3));

                // Set icon of category for box view
                a_icon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.itemscategory, 0, 0, 0);

                // For each achievement ..
                for(int i = 0; i < length; i++){

                    // Get status
                    status = achievementCategoryData[i].GetObtained();

                    // Increase overall point value
                    overallPoints += Integer.parseInt(achievementCategoryData[i].GetPoint());

                    // Increase total obtained
                    if(status.equals("Yes")){
                        total += 1;
                        overAllTotalItems += 1;
                    }
                    if(status.equals("No")){
                        overAllTotalItems += 1;
                    }

                }

                // Perform percentage calculation for category
                totalAchievements = (float) total;
                totalAchievementsPercentage = (totalAchievements / overAllTotalItems) * 100;
                displayTotalAchievementPercentage = Math.round(totalAchievementsPercentage);

                // Set the box view in XML
                a_icon.setText(Integer.toString(displayTotalAchievementPercentage) + "%");
                a_percentageBox.setText("» " + Integer.toString(total) + " / " + Integer.toString(overAllTotalItems) + " «");
                break;

            case "Crafting":
                // Get all character Crafting achievements
                int overAllTotalCrafting = 0;
                achievementCategoryData = mainCharacter.GetAllCrafting();

                // Get length of all achievements in category (all)
                length = achievementCategoryData.length;

                // Set title of category for box view)
                a_title.setText(mainCharacter.GetCategory(4));

                // Set icon of category for box view
                a_icon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.craftingcategory, 0, 0, 0);

                // For each achievement ..
                for(int i = 0; i < length; i++){

                    // Get status
                    status = achievementCategoryData[i].GetObtained();

                    // Increase overall point value
                    overallPoints += Integer.parseInt(achievementCategoryData[i].GetPoint());

                    // Increase total obtained
                    if(status.equals("Yes")){
                        total += 1;
                        overAllTotalCrafting += 1;
                    }
                    if(status.equals("No")){
                        overAllTotalCrafting += 1;
                    }

                }

                // Perform percentage calculation for category
                totalAchievements = (float) total;
                totalAchievementsPercentage = (totalAchievements / overAllTotalCrafting) * 100;
                displayTotalAchievementPercentage = Math.round(totalAchievementsPercentage);

                // Set the box view in XML
                a_icon.setText(Integer.toString(displayTotalAchievementPercentage) + "%");
                a_percentageBox.setText("» " + Integer.toString(total) + " / " + Integer.toString(overAllTotalCrafting) + " «");
                break;

            case "Gathering":

                // Get all character Gathering achievements
                int overAllTotalGathering = 0;
                achievementCategoryData = mainCharacter.GetAllGathering();

                // Get length of all achievements in category (all)
                length = achievementCategoryData.length;

                // Set title of category for box view)
                a_title.setText(mainCharacter.GetCategory(5));

                // Set icon of category for box view
                a_icon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.gatheringcategory, 0, 0, 0);

                // For each achievement ..
                for(int i = 0; i < length; i++){
                    // Get status
                    status = achievementCategoryData[i].GetObtained();

                    // Increase overall point value
                    overallPoints += Integer.parseInt(achievementCategoryData[i].GetPoint());

                    // Increase total obtained
                    if(status.equals("Yes")){
                        total += 1;
                        overAllTotalGathering += 1;
                    }
                    if(status.equals("No")){
                        overAllTotalGathering += 1;
                    }

                }

                // Perform percentage calculation for category
                totalAchievements = (float) total;
                totalAchievementsPercentage = (totalAchievements / overAllTotalGathering) * 100;
                displayTotalAchievementPercentage = Math.round(totalAchievementsPercentage);

                // Set the box view in XML
                a_icon.setText(Integer.toString(displayTotalAchievementPercentage) + "%");
                a_percentageBox.setText("» " + Integer.toString(total) + " / " + Integer.toString(overAllTotalGathering) + " «");
                break;

            case "Quests":

                // Get all character Quests achievements
                int overAllTotalQuests = 0;
                achievementCategoryData = mainCharacter.GetAllQuests();

                // Get length of all achievements in category (all)
                length = achievementCategoryData.length;

                // Set title of category for box view)
                a_title.setText(mainCharacter.GetCategory(6));

                // Set icon of category for box view
                a_icon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.questscategory, 0, 0, 0);

                // For each achievement ..
                for(int i = 0; i < length; i++){
                    // Get status
                    status = achievementCategoryData[i].GetObtained();

                    // Increase overall point value
                    overallPoints += Integer.parseInt(achievementCategoryData[i].GetPoint());

                    // Increase total obtained
                    if(status.equals("Yes")){
                        total += 1;
                        overAllTotalQuests += 1;
                    }
                    if(status.equals("No")){
                        overAllTotalQuests += 1;
                    }

                }

                // Perform percentage calculation for category
                totalAchievements = (float) total;
                totalAchievementsPercentage = (totalAchievements / overAllTotalQuests) * 100;
                displayTotalAchievementPercentage = Math.round(totalAchievementsPercentage);

                // Set the box view in XML
                a_icon.setText(Integer.toString(displayTotalAchievementPercentage) + "%");
                a_percentageBox.setText("» " + Integer.toString(total) + " / " + Integer.toString(overAllTotalQuests) + " «");
                break;

            case "Exploration":
                // Get all character Exploration achievements
                int overAllTotalExploration = 0;
                achievementCategoryData = mainCharacter.GetAllExploration();

                // Get length of all achievements in category (all)
                length = achievementCategoryData.length;

                // Set title of category for box view)
                a_title.setText(mainCharacter.GetCategory(7));

                // Set icon of category for box view
                a_icon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.explorationcategory, 0, 0, 0);

                // For each achievement ..
                for(int i = 0; i < length; i++){
                    // Get status
                    status = achievementCategoryData[i].GetObtained();

                    // Increase overall point value
                    overallPoints += Integer.parseInt(achievementCategoryData[i].GetPoint());

                    // Increase total obtained
                    if(status.equals("Yes")){
                        total += 1;
                        overAllTotalExploration += 1;
                    }
                    if(status.equals("No")){
                        overAllTotalExploration += 1;
                    }

                }

                // Perform percentage calculation for category
                totalAchievements = (float) total;
                totalAchievementsPercentage = (totalAchievements / overAllTotalExploration) * 100;
                displayTotalAchievementPercentage = Math.round(totalAchievementsPercentage);

                // Set the box view in XML
                a_icon.setText(Integer.toString(displayTotalAchievementPercentage) + "%");
                a_percentageBox.setText("» " + Integer.toString(total) + " / " + Integer.toString(overAllTotalExploration) + " «");
                break;

            case "GC":
                // Get all character GC achievements
                int overAllTotalGC = 0;
                achievementCategoryData = mainCharacter.GetAllGC();

                // Get length of all achievements in category (all)
                length = achievementCategoryData.length;

                // Set title of category for box view)
                a_title.setText(mainCharacter.GetCategory(8));

                // Set icon of category for box view
                a_icon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.gccategory, 0, 0, 0);

                // For each achievement ..
                for(int i = 0; i < length; i++){
                    // Get status
                    status = achievementCategoryData[i].GetObtained();

                    // Increase overall point value
                    overallPoints += Integer.parseInt(achievementCategoryData[i].GetPoint());

                    // Increase total obtained
                    if(status.equals("Yes")){
                        total += 1;
                        overAllTotalGC += 1;
                    }
                    if(status.equals("No")){
                        overAllTotalGC += 1;
                    }

                }

                // Perform percentage calculation for category
                totalAchievements = (float) total;
                totalAchievementsPercentage = (totalAchievements / overAllTotalGC) * 100;
                displayTotalAchievementPercentage = Math.round(totalAchievementsPercentage);

                // Set the box view in XML
                a_icon.setText(Integer.toString(displayTotalAchievementPercentage) + "%");
                a_percentageBox.setText("» " + Integer.toString(total) + " / " + Integer.toString(overAllTotalGC) + " «");
                break;
            default:

        }

    }

}
