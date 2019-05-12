/**/
/*

NAME

        Friend Search Activity Controller

PURPOSE
        This is a friend search activity controller similar to the settings activity controller. It will
        accept and cross reference a character name against a server on FFXIV and return the result back
        that matches the searched name. The user is able to set the character found as a friend object that
        gets stored in a separate serialization file, savedFriends.txt, so that this will not change upon
        the creation/set of a new main character profile being viewed. The library CircleImageView is
        utilized for this controller: https://github.com/hdodenhof/CircleImageView to display the icons
        in a circle image with a border. There are two async. crawlers: one that generates the search
        character URL, and one that generates the profile URL of the searched player.

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
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Button;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import android.widget.TextView;
import android.os.AsyncTask;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class FriendSearchActivity extends AppCompatActivity {
    // Initializations to hold view ids
    TextView testing = null;
    de.hdodenhof.circleimageview.CircleImageView charImage = null;
    Button setProfileButton = null;
    ProgressBar progressBar;
    ProgressBar progressBar2;

    // Strings to hold character information
    String charNameContent = null;
    String charID = "EMPTY";
    String charServerContent = null;
    String charImageString = "";

    // Friend object array of old friends & new friends
    Friend[] friends;
    int count = 0;
    Friend[] newFriendList;


    /**/
    /*
    public void onCreate()

    NAME

        onCreate - this formats the view of the activity on creation

    SYNOPSIS

        public void onCreate(Bundle savedInstanceState)
            Bundle savedInstanceState   --> if the activity of being re-initialized after previously being shut down this bundle
                                            contains the data it most recently supplied in onSaveInstanceState(Bundle)
            testing                     --> testing view id (character name field)
            charImage                   --> character image view id
            setProfileButton            --> set profile button view id
            progressBar                 --> progress bar view id for search
            progressBar2                --> progress bar view id for view transitions
            friends                     --> friend array of friends, set to 5 as default
            String[] serverNames        --> string for spinner server values
            Spinner serverSpinner       --> spinner for server names
            ArrayAdapter<String> adapter--> array adapter for friends

    DESCRIPTION

        The onCreate method that is called when the activity is starting. It will create the view to reflect
        the friend objects in a given friend array. If not initialized it will show 5 empty friend cards.
        If they are populated, these fields will show friend objects and their point values in a recycler
        viewer from highest point values to lowest.

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
        setContentView(R.layout.activity_friend);
        // Set screen orientation
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Initializations
        testing = findViewById(R.id.friend_textView_charName1);
        charImage = findViewById(R.id.friend_circleView_characterIcon1);
        setProfileButton = findViewById(R.id.friend_button_setMainProfile);
        progressBar = findViewById(R.id.friend_progressBar);
        progressBar2 = findViewById(R.id.friend_progressBar14);

        progressBar.setVisibility(View.INVISIBLE);
        progressBar2.setVisibility(View.INVISIBLE);

        friends = new Friend[5];

        // Set server names
        String[] serverNames = new String[]{"Adamantoise", "Balmung", "Cactuar", "Coeurl", "Faerie",
                "Gilgamesh", "Goblin", "Jenova", "Mateus", "Midgardsormr", "Sargatanas", "Siren",
                "Zalera", "Behemoth", "Brynhildr", "Diabolos", "Excalibur", "Exodus", "Famfrit",
                "Hyperion", "Lamia", "Leviathan", "Malboro", "Ultros", "Cerberus", "Lich", "Louisoix",
                "Moogle", "Odin", "Omega", "Phoenix", "Ragnarok", "Shiva", "Zodiark", "Aegis", "Atomos",
                "Carbuncle", "Garuda", "Gungnir", "Kujata", "Ramuh", "Tonberry", "Typhon", "Unicorn",
                "Alexander", "Bahamut", "Durandal", "Fenrir", "Ifrit", "Ridill", "Tiamat", "Ultima",
                "Valefor", "Yojimbo", "Zeromus", "Anima", "Asura", "Belias", "Chocobo", "Hades",
                "Ixion", "Mandragora", "Masamune", "Pandaemonium", "Shinryu", "Titan"};

        // Sort server names alphabetically
        Arrays.sort(serverNames);

        // If there are friends in friend file
        if(setFriendArray() == true){
            // Set friends for friend array
            setFriendArray();
            newFriendList = new Friend[friends.length + 1];

            for(int i = 0; i < newFriendList.length; i++){
                newFriendList[i] = new Friend();
            }
        // If there are NO friends => create brand new friend array of friend objects that are empty
        }else{
            for(int i = 0; i < friends.length; i++){
                friends[i] = new Friend();
            }
        }

        // Set spinner values
        Spinner serverSpinner = (Spinner) findViewById(R.id.friend_spinner_Server);
        serverSpinner.setPrompt("SERVER");
        // Set adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_theme, serverNames);
        adapter.setDropDownViewResource(R.layout.spinner_theme);
        serverSpinner.setAdapter(adapter);
    }

    /**/
    /*
    public void searchCharacter()

    NAME

        searchCharacter - this method is used to generate a URL from the entered text then calls
        a Crawler class to retrieve the HTML from the link generated

    SYNOPSIS

       public void searchCharacter(View v)
            View v                  --> receives a view to get data for searching
            String charFirstName    --> string to hold the searched characters first name
            String charLastName     --> string to hold the searched characters last name
            String BASE_URL         --> string to hold the URL of the searched player to extract ID
            EditText nameText       --> view id of the text box where user enters name
            charNameContent         --> string to that is holding the entered text from user to parse
            Spinner serverSpinner   --> spinner that holds all server names
            Private Crawler         --> this is an async class that gets & receives the html from the link



    DESCRIPTION

        The searchCharacter method takes the current screen upon hitting the search button,
        and then will grab the entered data from the user to generate a url to crawl. This
        method is responsible for extracting the user ID for the character for the display
        and using regex to split the first and last name of the entered text. The box
        to enter text is a single box, so it is required to separate the first and last
        name in order to generate a search URL to get.

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void searchCharacter(View v) {
        // Set important variables
        String charFirstName = null;
        String charLastName = null;
        String BASE_URL = null;

        // Get text from view
        EditText nameText = findViewById(R.id.friend_editText_name);
        charNameContent = nameText.getText().toString();
        charNameContent = charNameContent.replaceAll("[^a-zA-Z\\s]", "");
        nameText.setText(charNameContent);

        // Set the server based on the spinner selection
        Spinner serverSpinner = (Spinner) findViewById(R.id.friend_spinner_Server);
        charServerContent = serverSpinner.getSelectedItem().toString();

        // Split the first name
        charFirstName = charNameContent.split(" ")[0];

        // Remove the space that is now at the start of the string if a first and last name was entered correctly
        if (charNameContent.contains(" ")){

            // Generate SEARCH URL based on first and last name
            charLastName = charNameContent.split(" ")[1];

            BASE_URL = "https://na.finalfantasyxiv.com/lodestone/character/?q=" + charFirstName + "+" + charLastName + "&worldname=" + charServerContent + "&classjob=&race_tribe=&blog_lang=ja&blog_lang=en&blog_lang=de&blog_lang=fr&order=";
            charImage.setImageResource(R.drawable.searching);
            testing.setText("SEARCHING \uD83D\uDD0E PLEASE WAIT");

        // If there happens to not be a space, this is here to make sure the app doesn't crash upon making a mistake.
        }else{

            BASE_URL = "https://na.finalfantasyxiv.com/lodestone/character/?q=" + charFirstName + "&worldname=" + charServerContent + "&classjob=&race_tribe=&blog_lang=ja&blog_lang=en&blog_lang=de&blog_lang=fr&order=";
            charImage.setImageResource(R.drawable.searching);
            testing.setText("SEARCHING \uD83D\uDD0E PLEASE WAIT");
        }

        // Create the async task to request the html from the generated url string
        new Crawler().execute(BASE_URL);

    }

    /**/
    /*
    public void setMainProfile()

    NAME

        setMainProfile - this method is used to set a friend profile after the set friend button
        has been selected

    SYNOPSIS

       public void setMainProfile(View v)
            View v                  --> receives a view to get data for searching
            String charID           --> string that holds the character ID
            Toast toast             --> toast to display message to user if they didn't enter anything
            Button setProfileButton --> view id of the set profile button
            ProgressBar progressBar --> view id of the progress bar for the screen
            ImageView charImage     --> image view id of the icon of user
            ImageView testing       --> image view id of the box to display user information
            Friend[] friend         --> friend object array to hold friends
            Friend[] newFriendList  --> new friend object array to add a new friend while keeping old friends
            int count               --> holds the number of friends
            Crawler data            --> async task that gets the achievement point value for friend to set

    DESCRIPTION

        The setMainProfile function is used within the FriendSearchActivity in order to set a friend
        profile that will be used to display the friends within the Comparison Activity. It initially
        sets up empty friend profiles if there are no friends present in the serialization file
        that holds friend objects. If there are friend objects, it will save those friends and create
        new friend objects dynamically. That way no friends are lost, overwritten or deleted.

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void setMainProfile(View v){

        // If there was no character ID selected ..
        if(charID.equals("EMPTY")){
            Toast toast = Toast.makeText(getApplicationContext(), "Enter A Character Name", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        // If there was a character ID with a number, change view to reflect attempting to set
        else{
            setProfileButton.setText("ATTEMPTING TO SET ... ");
            progressBar.setVisibility(View.VISIBLE);
            charImage.setVisibility(View.INVISIBLE);
            testing.setVisibility(View.INVISIBLE);

            // For each friend object in our friends array ..
            for(int i = 0; i < friends.length; i++){
                // If any of those friend objects are not empty ..
                if(!friends[i].GetCharacterName().equals("Empty")){
                    // Add to the number of friends
                    count += 1;
                    // User newFriendList as the new array
                    newFriendList[i] = friends[i];
                }
            }
            // Store the new friends already known information: name, server, icon
            newFriendList[count].SetCharacterName(charNameContent);
            newFriendList[count].SetCharacterServer(charServerContent);
            newFriendList[count].SetCharacterIcon(charImageString);

            // Use the crawler to get the achievement point value
            CrawlerData data = new CrawlerData();

            // Generate the URL for the searched friend
            String characterURL = "https://na.finalfantasyxiv.com/lodestone/character/" + charID + "/achievement/";

            // Call the crawler
            data.execute(characterURL);
        }
    }

    /**/
    /*
    public void returnButton()

    NAME

        returnButton - returns ComparisonActivity to MainActivity view

    SYNOPSIS

        public void returnButton(View view)
            View view       --> parameter view
            progressBar2    --> set the progress bar to visible
            Intent intent   --> intent for new activity

    DESCRIPTION

        The return button allows to return to the Comparison Activity view from the Friend Search Activity view.
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
        progressBar2.setVisibility(View.VISIBLE);
        Intent intent = new Intent(FriendSearchActivity.this, ComparisonActivity.class);
        startActivity(intent);
    }

    /**/
    /*
    public void forceClear()

    NAME

        forceClear - force clears the friend serialization file for utility purposes and testing

    SYNOPSIS

        public void forceClear()
            FileOutputStream fileOut         --> file output stream
            OutputStreamWriter outputWriter  --> output object stream writer

    DESCRIPTION

        This method is used as a utility to force clear the savedFriends.txt file

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void forceClear(){
        // Try to open file
        try{
            FileOutputStream fileOut = openFileOutput("savedFriends.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileOut);
            // Clear the file
            outputWriter.write("");
            outputWriter.close();
        }
        // If can't open => catch exception
        catch (Exception e){
            e.printStackTrace();
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

    NAME

        Crawler Async Task

    PURPOSE
        This is a private CrawlerData class that is used within the friend search activity screen.
        When a friend is found, this task is called in order to perform the connection of the link
        being sent it, and then changing the view of the screen & writing to the serialization file
        upon completion.

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    private class Crawler extends AsyncTask<String, Void, Void> {
        Document document = null;

        /**/
        /*
        public void doInBackground()

        NAME

            doInBackground - this method performs what to do in the background upon being called for
            for the friend search

        SYNOPSIS

            protected Void doInBackground(String... params)
                String... params    --> can receive a number of string url parameters
                String url          --> string to hold the given parameter
                Document document   --> document of the HTML of the url

        DESCRIPTION

            The doInBackground task tries to connect to the given URL in the background. Upon being
            successful it will trigger the onPostExecute function to alter the data/change the view.
            The library JSOUP is being used here to do the network connection.

        RETURNS

            Nothing

        AUTHOR

            Desiree Toczylowski

        DATE

        5/8/2019

        */
        /**/
        @Override
        protected Void doInBackground(String... params) {
            // Set parameter URL as string
            String url = params[0];

            // Try to connect to the URL using JSOUP
            try{
                document = Jsoup.connect(url).get();

            // If can't connect => catch exception
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        /**/
        /*
        public void onPostExecute()

        NAME

            onPostExecute - this method is called upon a successful connection of the url
            and upon the document being populated with the HTML, this will parse that data
            to get the searched friends back.

        SYNOPSIS

            protected void onPostExecute(Void result)
                Void result          --> result of connection
                Elements results     --> within the document, find the div called "parts__total"
                String resultsString --> hold the text of the parts_total div
                Document document    --> document of HTML data
                String charID        --> character ID
                String charImage     --> character Image
                TextView testing     --> text box with display message for screen
                String lowerCaseNameToFind --> String holding a lower case name
                String normalCaseNameToFind --> String holding an unaltered case name
                String charNameContent   --> user entered text
                Elements div2   --> elements a.entry__link
                Elements div    --> elements div.entry__chara__face
                Elements get    --> div's image attribute

        DESCRIPTION

            onPostExecute will parse the HTML and find a searched character. If the character is
            found it will change the view to reflect the found characters name, icon, server, and
            ID. This class uses the library Picasso to update the ImageView (testing) with the
            icon of the user from the web search.

        RETURNS

            Nothing

        AUTHOR

            Desiree Toczylowski

        DATE

        5/8/2019

        */
        /**/
        @Override
        protected void onPostExecute(Void result) {

            // On successful connection .. find div that holds the amount of users searched
            Elements results = document.select("div.parts__total");
            // Set the total to the results string
            String resultsString = results.text();

            if(document != null){

                // If the count returned was 0 ..
                if(resultsString.equals("0 Total")){
                    charID = "EMPTY";
                    charImage.setImageResource(R.drawable.searching);
                    testing.setText("NOT FOUND\n\nYOU MAY NOT HAVE ENTERED\nA VALID NAME+SERVER COMBINATION");
                }
                // If not => get characters that returned from the search
                else{

                    // To avoid confusion and name conventions, all names are converted to lower-case for comparison
                    String lowerCaseNameToFind;
                    String normalCaseNameToFind;
                    String lowerCaseSearchName = charNameContent.toLowerCase();

                    // Get each character element block
                    Elements div2 = document.select("a.entry__link");

                    // For each character results ..
                    for (Element element : div2){
                        // Get their character ID
                        charID = element.attr("abs:href");
                        charID = charID.replaceAll("[^\\d.]", "");
                        charID = charID.replace(".", "");

                        // Find the icon div
                        Elements div = element.select("div.entry__chara__face");

                        // Get the image of that div
                        Elements get = div.select("img");
                        normalCaseNameToFind = get.attr("alt");
                        lowerCaseNameToFind = normalCaseNameToFind.toLowerCase();


                        // If the searched name is the same as the resulted character in list
                        if(lowerCaseNameToFind.equalsIgnoreCase(lowerCaseSearchName)){
                            // Set the character image icon
                            charImageString = get.attr("abs:src");

                            // Use Picasso to insert picture into image view
                            Picasso.get()
                                    .load(charImageString)
                                    .into(charImage);
                            testing.setText("\uD83D\uDD11 ID: ");
                            testing.append(charID);
                            testing.append("\n\uD83D\uDD12 NAME: ");
                            testing.append(normalCaseNameToFind);
                            testing.append("\n\uD83C\uDF10 SERVER: ");
                            testing.append(charServerContent);

                            break;
                        // If any other error shows, ask user if they are entering data correctly
                        }else {
                            charID = "EMPTY";
                            charImage.setImageResource(R.drawable.searching);
                            testing.setText("NOT FOUND\n\nYOU MAY NOT HAVE ENTERED\nA VALID NAME+SERVER COMBINATION");
                        }

                    }
                }

            }

        }

    }

    /**/
    /*

    NAME

        CrawlerData Async Task

    PURPOSE
        This is a private CrawlerData class that is used within the friend search activity screen.
        When a friend is found, this task is called in order to perform the connection of the link
        being sent it, and then changing the view of the screen & writing to the serialization file
        upon completion. This class is responsible for setting a Friend profile achievement
        points upon hitting the Set Friend Profile button.

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    private class CrawlerData extends AsyncTask<String, Void, Void> {
        Document document = null;

        /**/
        /*
        public void doInBackground()

        NAME

            doInBackground - this method performs what to do in the background upon being called for
            for the friend search

        SYNOPSIS

            protected Void doInBackground(String... params)
                String... params    --> can receive a number of string url parameters
                String url          --> string to hold the given parameter
                Document document   --> document of the HTML of the url

        DESCRIPTION

            The doInBackground task tries to connect to the given URL in the background. Upon being
            successful it will trigger the onPostExecute function to alter the data/change the view.
            The library JSOUP is being used here to do the network connection.

        RETURNS

            Nothing

        AUTHOR

            Desiree Toczylowski

        DATE

        5/8/2019

        */
        /**/
        @Override
        protected Void doInBackground(String... params) {

            String url = params[0];

            try{
                document = Jsoup.connect(url).get();
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }


        /**/
        /*
        public void onPostExecute()

        NAME

            onPostExecute - this method is called upon a successful connection of the url
            and upon the document being populated with the HTML, this will parse that data
            to get the searched friends achievement points back.

        SYNOPSIS

            protected void onPostExecute(Void result)
                Void result          --> result of connection
                String pointValue    --> string to hold searched point value of friend
                Elements achievementPoints --> get the element holding achievement point from HTML
                newFriendList   --> friend array of friend objects
                FileOutputStream fileOut   --> file output stream
                ObjectOutputStream outputWriter   --> object output stream
                Toast toast     --> toast for successful set
                Button setProfileButton --> view id of the set profile button
                progressBar  --> view id of the progress bar
                charImage --> view id of the character image
                testing --> view id of the character data

        DESCRIPTION

            onPostExecute will parse the HTML and find a searched friends achievement points.
            It will then write this friend to the savedFriends.txt file adding them to
            the previous friend list.

        RETURNS

            Nothing

        AUTHOR

            Desiree Toczylowski

        DATE

        5/8/2019

        */
        /**/
        @Override
        protected void onPostExecute(Void result) {
            // Set point value
            String pointValue = "";

            if(document != null){
                // Find the characters element that holds their point value and set the friend object's points
                Elements achievementPoints = document.select("p.achievement__point");
                pointValue = achievementPoints.text();
                newFriendList[count].SetCharacterPoints(pointValue);
            }

            // Clear the friend file to prepare for re-write
            forceClear();

            // Try to open the file
            try {
                FileOutputStream fileOut = openFileOutput("savedFriends.txt", MODE_PRIVATE);
                ObjectOutputStream outputWriter = new ObjectOutputStream(fileOut);
                // Write the new friend array to file
                outputWriter.writeObject(newFriendList);
                outputWriter.close();

                // Display toast on successful set
                Toast toast = Toast.makeText(getApplicationContext(), "Success: Friend Profile Set", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

            }
            // Else => catch exception
            catch (Exception e){
                e.printStackTrace();
            }

            // Change the view of the screen to reflect the friend was set
            setProfileButton.setText("PROFILE SET");
            progressBar.setVisibility(View.INVISIBLE);
            charImage.setVisibility(View.VISIBLE);
            testing.setVisibility(View.VISIBLE);
        }

    }

    /**/
        /*
        public boolean setFriendArray()

        NAME

            setFriendArray - this will read in the current friend array that is in the savedFriends.txt
            file


        SYNOPSIS

            public boolean setFriendArray()
                FileInputStream fileIn      --> file input stream
                ObjectInputStream inputReader  --> object input stream
                friends         --> friend object array to set from serialized objects

        DESCRIPTION

            This method is used to retrieve the friends from the serialization file holding them.
            This is used in order to keep previous friend objects when adding new ones to the list.

        RETURNS

            True, if success on reading from file and False if not successful

        AUTHOR

            Desiree Toczylowski

        DATE

        5/8/2019

        */
    /**/
    public boolean setFriendArray(){
        // Try to open file
        try{
            FileInputStream fileIn = openFileInput("savedFriends.txt");
            ObjectInputStream inputReader = new ObjectInputStream(fileIn);
            // Set friends to friend array in file
            friends = (Friend[]) inputReader.readObject();
            inputReader.close();
            fileIn.close();
            // Return true
            return true;

        }
        // Else catch exception => return false
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
