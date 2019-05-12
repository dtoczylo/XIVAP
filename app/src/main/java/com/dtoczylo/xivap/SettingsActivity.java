/**/
/*

NAME

        Settings Activity Controller

PURPOSE

        This is the controller that handles the search character activity. This is a major aspect of the
        app and is responsible for retrieving a characters data for activity manipulation. There are a
        number of async tasks that will search for a character, then generate 8 different URLS to
        get that players achievement data. Each achievement is stored as an achievement object
        for the character object.

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

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class SettingsActivity extends AppCompatActivity {
    // Set up view id's
    TextView testing = null;
    de.hdodenhof.circleimageview.CircleImageView charImage = null;
    Button setProfileButton = null;
    private ProgressBar progressBar;
    private ProgressBar progressBar2;

    // Set up character content for URL generation
    String charFirstName = null;
    String charLastName = null;
    String charNameContent = null;
    String charID = "EMPTY";
    String charServerContent = null;
    String charImageString = "";

    // Set up character object to store or create new character object
    Character mainCharProfile = new Character();


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
            testing         --> text view id from activity
            charImage       --> image view id from activity
            setProfileButton --> button view id from activity
            progressBar     --> progress bar view id from activity
            progressBar2    --> progress bar view id from activity
            String[] serverNames    --> string array of server names
            Spinner spinner --> spinner of server names
            ArrayAdapter<String> adapter --> array adapter for spinner values


    DESCRIPTION

        The onCreate method that is called when the activity is starting. It will create the view to reflect
        the search of a character. It pre-populate text and image views and sets the spinner to reflect
        each of the servers in alphabetical order.

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
        // Set up XML for activity
        setContentView(R.layout.activity_settings);
        // Set screen orientation
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Populate view id's from activity on create
        testing = findViewById(R.id.textView_charName1);
        charImage = findViewById(R.id.circleView_characterIcon1);
        setProfileButton = findViewById(R.id.button_setMainProfile);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar2 = findViewById(R.id.progressBar14);

        progressBar.setVisibility(View.INVISIBLE);
        progressBar2.setVisibility(View.INVISIBLE);

        // Set string to hold server names
        String[] serverNames = new String[]{"Adamantoise", "Balmung", "Cactuar", "Coeurl", "Faerie",
                "Gilgamesh", "Goblin", "Jenova", "Mateus", "Midgardsormr", "Sargatanas", "Siren",
                "Zalera", "Behemoth", "Brynhildr", "Diabolos", "Excalibur", "Exodus", "Famfrit",
                "Hyperion", "Lamia", "Leviathan", "Malboro", "Ultros", "Cerberus", "Lich", "Louisoix",
                "Moogle", "Odin", "Omega", "Phoenix", "Ragnarok", "Shiva", "Zodiark", "Aegis", "Atomos",
                "Carbuncle", "Garuda", "Gungnir", "Kujata", "Ramuh", "Tonberry", "Typhon", "Unicorn",
                "Alexander", "Bahamut", "Durandal", "Fenrir", "Ifrit", "Ridill", "Tiamat", "Ultima",
                "Valefor", "Yojimbo", "Zeromus", "Anima", "Asura", "Belias", "Chocobo", "Hades",
                "Ixion", "Mandragora", "Masamune", "Pandaemonium", "Shinryu", "Titan"};

        // Sort servers for spinner alphabetically
        Arrays.sort(serverNames);


        // Set spinner
        Spinner serverSpinner = (Spinner) findViewById(R.id.spinner_Server);
        serverSpinner.setPrompt("SERVER");

        // Bind spinner
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
        // Initializations
        String charFirstName = null;
        String charLastName = null;
        String BASE_URL = null;

        // Initializations for views
        EditText nameText = findViewById(R.id.editText_name);

        // Set character content to entered text on screen
        charNameContent = nameText.getText().toString();
        charNameContent = charNameContent.replaceAll("[^a-zA-Z\\s]", "");
        nameText.setText(charNameContent);

        // Get spinner selection
        Spinner serverSpinner = (Spinner) findViewById(R.id.spinner_Server);
        charServerContent = serverSpinner.getSelectedItem().toString();

        // Generate first name
        charFirstName = charNameContent.split(" ")[0];

        // Generate URL on first and last name
        if (charNameContent.contains(" ")){
            charLastName = charNameContent.split(" ")[1];

            BASE_URL = "https://na.finalfantasyxiv.com/lodestone/character/?q=" + charFirstName + "+" + charLastName + "&worldname=" + charServerContent + "&classjob=&race_tribe=&blog_lang=ja&blog_lang=en&blog_lang=de&blog_lang=fr&order=";
            charImage.setImageResource(R.drawable.searching);
            testing.setText("SEARCHING \uD83D\uDD0E PLEASE WAIT");

        // Generate URL on first name only
        }else{

            BASE_URL = "https://na.finalfantasyxiv.com/lodestone/character/?q=" + charFirstName + "&worldname=" + charServerContent + "&classjob=&race_tribe=&blog_lang=ja&blog_lang=en&blog_lang=de&blog_lang=fr&order=";
            charImage.setImageResource(R.drawable.searching);
            testing.setText("SEARCHING \uD83D\uDD0E PLEASE WAIT");
        }

        // Call async task to handle the connection and result
        new Crawler().execute(BASE_URL);

    }

    /**/
    /*
    public void setMainProfile()

    NAME

        setMainProfile - this method is used to set a main character profile after the set set profile button
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
            Crawler data            --> async task that gets the achievement point value for character to set
            Character mainCharProfile   --> class character object

    DESCRIPTION

        The setMainProfile function is used within the SettingsActivity in order to set a main
        profile that will be used to display the data in various activities. It will set the character
        objects attributes based on the entered text and then will generate 10 urls for achievement
        pages. Each page requires it's own async activity since each page will handle those results
        differently.

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void setMainProfile(View v){

        // If no character profile set => Remove current profile ** ADDED SECRET FEATURE **
        if(charID.equals("EMPTY")){
            forceClear();
            mainCharProfile.Clear();
            Toast toast = Toast.makeText(getApplicationContext(), "Removed: Main Profile Cleared", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        // If there is a character profile there, clear it out
        else{
            forceClear();

            // Change the display to reflect the setting of a profile
            setProfileButton.setText("SETTING ... ");
            progressBar.setVisibility(View.VISIBLE);
            charImage.setVisibility(View.INVISIBLE);
            testing.setVisibility(View.INVISIBLE);

            // Start to set the character objects attributes from text entered
            mainCharProfile.SetName(charNameContent);
            mainCharProfile.SetServer(charServerContent);
            mainCharProfile.SetID(charID);
            mainCharProfile.SetIcon(charImageString);

            // Initialize all crawlers for achievement parsing
            CrawlerBattle battle = new CrawlerBattle();
            CrawlerData data = new CrawlerData();
            CrawlerPvP pvp = new CrawlerPvP();
            CrawlerChar character = new CrawlerChar();
            CrawlerItems items = new CrawlerItems();
            CrawlerCrafting crafting = new CrawlerCrafting();
            CrawlerGathering gathering = new CrawlerGathering();
            CrawlerQuests quests = new CrawlerQuests();
            CrawlerExploration exploration = new CrawlerExploration();
            CrawlerGC gc = new CrawlerGC();

            // Achievements: Battle
            String battleURL = "https://na.finalfantasyxiv.com/lodestone/character/" + charID + "/achievement/kind/1/";
            battle.execute(battleURL);

            // Achievements: PvP
            String pvpURL = "https://na.finalfantasyxiv.com/lodestone/character/" + charID + "/achievement/kind/2/";
            pvp.execute(pvpURL);

            // Achievements: Character
            String charURL = "https://na.finalfantasyxiv.com/lodestone/character/" + charID + "/achievement/kind/3/";
            character.execute(charURL);

            // Achievements: Items
            String itemsURL = "https://na.finalfantasyxiv.com/lodestone/character/" + charID + "/achievement/kind/4/";
            items.execute(itemsURL);

            // Achievements: Crafting
            String craftingURL = "https://na.finalfantasyxiv.com/lodestone/character/" + charID + "/achievement/kind/5/";
            crafting.execute(craftingURL);

            // Achievements: Gathering
            String gatheringURL = "https://na.finalfantasyxiv.com/lodestone/character/" + charID + "/achievement/kind/6/";
            gathering.execute(gatheringURL);

            // Achievements: Quests
            String questsURL = "https://na.finalfantasyxiv.com/lodestone/character/" + charID + "/achievement/kind/8/";
            quests.execute(questsURL);

            // Achievements: Exploration
            String explorationURL = "https://na.finalfantasyxiv.com/lodestone/character/" + charID + "/achievement/kind/11/";
            exploration.execute(explorationURL);

            // Achievements: GC
            String gcURL = "https://na.finalfantasyxiv.com/lodestone/character/" + charID + "/achievement/kind/12/";
            gc.execute(gcURL);

            // Character: Info
            String characterURL = "https://na.finalfantasyxiv.com/lodestone/character/" + charID + "/";
            data.execute(characterURL);

        }
    }

    /**/
    /*
    public void returnButton()

    NAME

        returnButton - returns SettingsActivity to MainActivity view

    SYNOPSIS

        public void returnButton(View view)
            View view       --> parameter view
            progressBar2    --> set the progress bar to visible
            Intent intent   --> intent for new activity

    DESCRIPTION

        The return button allows to return to the Main Activity view from the Settings Activity view.
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
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
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
        This is a private CrawlerData class that is used within the character search activity screen.
        When a character is found, this task is called in order to perform the connection of the link
        being sent it.

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
            for the character search

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
            to get the searched characters back.

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
        This is a private CrawlerData class that is used within the character search activity screen.
        When a character is found, this task is called in order to perform the connection of the link
        being sent it, and then changing the view of the screen & writing to the serialization file
        upon completion. This class is responsible for setting a character profile's data such as
        levels, race, etc. Used for display in the character viewer.

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
            for the character search

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
            to get the searched character profile data.

        SYNOPSIS

            protected void onPostExecute(Void result)
                Void result          --> result of connection
                String foundTitle    --> string to hold character title
                String foundRace     --> string to hold character race
                String foundGuardian --> string to hold character guardian
                String foundGuardianIcon  --> string to hold character guardian icon
                String foundCity     --> string to hold character city
                String foundCityIcon --> string to hold character city icon
                String foundGC       --> string to hold character gc
                String foundGCIcon   --> string to hold character gc icon
                String foundFC       --> string to hold character fc
                String pld, war, dk  --> strings to hold character tank levels
                String pldIcon, warIcon, dkIcon --> strings to hold character tank icons
                String whm, ast, sch  --> strings to hold character healer levels
                String whmIcon, astIcon, schIcon --> strings to hold character healer icons
                String mnk, drg, nin, sam --> strings to hold character mele levels
                String mnkIcon, drgIcon, ninIcon, samIcon  --> strings to hold character mele icons
                String brd, mch, blm, smn, rdm, blu     --> strings to hold character ranged levels
                String brdIcon, mchIcon, blmIcon, smnIcon, rdmIcon, bluIcon  --> strings to hold character ranged icons
                Elements title  --> element holding character title
                Character mainCharProfile   --> character object
                Element blocks --> elements holding character general information
                Element titles --> elements holding general information titles
                Element name   --> elements holding general information data names
                Element fc     --> elements holding fc image
                Elements fcNameBlock --> elements holding fc name
                Element fcIcon  --> element holding fc icon

        DESCRIPTION

            onPostExecute will parse the HTML and find all the important information relating to
            a character from the given URL. It will store all levels, character fc, character gc,
            icons, guardian, race, title and mount and minion count. I should have broken this up
            into smaller sections, but I'd rather not break it before it's due so I will simply
            let you know, that I KNOW, this is poor design.

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
            if(document != null){

                // Initialize all important variables to set at end of post execute
                String foundTitle, foundRace, foundGuardian, foundGuardianIcon, foundCity, foundCityIcon, foundGC, foundGCIcon, foundFC;

                String pld = "", war = "", dk = "";
                String whm = "", ast = "", sch = "";
                String mnk = "", drg = "", nin = "", sam = "";
                String brd = "", mch = "", blm = "", smn = "", rdm = "", blu = "";

                String pldIcon = "", warIcon = "", dkIcon = "";
                String whmIcon = "", astIcon = "", schIcon = "";
                String mnkIcon = "", drgIcon = "", ninIcon = "", samIcon = "";
                String brdIcon = "", mchIcon = "", blmIcon = "", smnIcon = "", rdmIcon = "", bluIcon = "";

                // Get the element that holds the characters title & store it
                Elements title = document.select("p.frame__chara__title");
                foundTitle = title.text();
                mainCharProfile.SetTitle(foundTitle);

                // Get the elements holding the character general information
                Elements blocks = document.select("div.character-block__box");
                for(Element element : blocks){
                    // For each block in this section ..
                    Elements titles = element.select("p.character-block__title");
                    Elements name = element.select("p.character-block__name");
                    for(Element elementTitle: titles){
                        String titleName = elementTitle.text();

                        // Get the race
                        if(titleName.equals("Race/Clan/Gender")){
                            foundRace = name.text();
                            foundRace = foundRace.replace("/", "");
                            mainCharProfile.SetRace(foundRace);
                        }

                        // Get the guardian
                        if(titleName.equals("Nameday")){
                            Element parent = element.parent();
                            Elements parentIcon = parent.select("img");
                            foundGuardianIcon = parentIcon.attr("abs:src");
                            mainCharProfile.SetGuardianIcon(foundGuardianIcon);

                            foundGuardian = name.text();
                            mainCharProfile.SetGuardian(foundGuardian);
                        }

                        // Get the city
                        if(titleName.equals("City-state")){
                            Element parent = element.parent();
                            Elements parentIcon = parent.select("img");
                            foundCityIcon = parentIcon.attr("abs:src");
                            mainCharProfile.SetCityIcon(foundCityIcon);

                            foundCity = name.text();
                            mainCharProfile.SetCity(foundCity);
                        }

                        // Get the gc
                        if(titleName.equals("Grand Company")){
                            Element parent = element.parent();
                            Elements parentIcon = parent.select("img");
                            foundGCIcon = parentIcon.attr("abs:src");
                            mainCharProfile.SetGCIcon(foundGCIcon);

                            foundGC = name.text();
                            mainCharProfile.SetGC(foundGC);
                        }
                    }
                }

                // If character in a free company ..
                String foundFCIconBase, foundFCIcon;
                // Get the element holding free company crest
                Elements fc = document.select("div.character__freecompany__crest__image");

                // If not empty
                if(!fc.isEmpty()){

                    // Set the image icon base of the fc
                    Element fcIconBase = fc.select("img").first();
                    foundFCIconBase = fcIconBase.attr("abs:src");
                    mainCharProfile.SetFCIconBase(foundFCIconBase);

                    // Set the image icon
                    Element fcIcon = fc.select("img").last();
                    foundFCIcon = fcIcon.attr("abs:src");
                    mainCharProfile.SetFCIcon(foundFCIcon);

                    // Get the fc name
                    Elements fcNameBlock = document.select("div.character__freecompany__name");
                    Elements fcNameBlock2 = fcNameBlock.select("h4");
                    Elements fcName = fcNameBlock2.select("a");
                    foundFC = fcName.text();
                    mainCharProfile.SetFC(foundFC);
                }
                // Else, not in a fc => make empty
                else{
                    mainCharProfile.SetFCIconBase("");
                    mainCharProfile.SetFCIcon("");
                    mainCharProfile.SetFC("");
                }


                // Get the minion and mount information
                Integer minionCount = 0;
                Integer mountCount = 0;

                // Get the element holding minion list
                Elements minionBlock = document.select("div.character__minion");
                Elements minionList = minionBlock.select("ul.character__icon__list");
                Elements minionList2 = minionList.select("li");
                for (Element minion : minionList2){
                    minionCount = minionCount + 1;
                }

                // Set minion count
                mainCharProfile.SetMinionTotal(minionCount);

                // Get the element holding mount list
                Elements mountBlock = document.select("div.character__mounts");
                Elements mountList = mountBlock.select("ul.character__icon__list");
                Elements mountList2 = mountList.select("li");
                for (Element mount : mountList2){
                    mountCount = mountCount + 1;
                }

                // Set mount total
                mainCharProfile.SetMountTotal(mountCount);

                // Get the character large image & set
                Elements image = document.select("div.character__detail__image");
                Elements get = image.select("a");
                String foundImage = get.attr("abs:href");
                mainCharProfile.SetImage(foundImage);


                // Find the element holding all levels for character
                Elements levelBlocks = document.select("div.character__level.clearfix");
                for (Element levelBlock : levelBlocks){
                    Elements levelList = levelBlock.select("div.character__level__list");
                    Elements levels = levelList.select("ul");
                    Elements rows = levels.select("li");
                    for (Element row : rows){
                        Elements classTitle = row.select("img.js__tooltip");
                        String stringClassTitle = classTitle.attr("data-tooltip");

                        // Depending on class name in element, set in appropriate string
                        if(stringClassTitle.equals("Paladin / Gladiator") || stringClassTitle.equals("Gladiator")){
                            pldIcon = classTitle.attr("abs:src");
                            pld = row.text();
                        }

                        else if(stringClassTitle.equals("Warrior / Marauder") || stringClassTitle.equals("Marauder")){
                            warIcon = classTitle.attr("abs:src");
                            war = row.text();
                        }

                        else if(stringClassTitle.equals("Dark Knight")){
                            dkIcon = classTitle.attr("abs:src");
                            dk = row.text();
                        }

                        else if(stringClassTitle.equals("White Mage / Conjurer") || stringClassTitle.equals("Conjurer")){
                            whmIcon = classTitle.attr("abs:src");
                            whm = row.text();
                        }

                        else if(stringClassTitle.equals("Scholar")){
                            schIcon = classTitle.attr("abs:src");
                            sch = row.text();
                        }

                        else if(stringClassTitle.equals("Astrologian")){
                            astIcon = classTitle.attr("abs:src");
                            ast = row.text();
                        }

                        else if(stringClassTitle.equals("Monk / Pugilist") || stringClassTitle.equals("Pugilist")){
                            mnkIcon = classTitle.attr("abs:src");
                            mnk = row.text();
                        }

                        else if(stringClassTitle.equals("Dragoon / Lancer") || stringClassTitle.equals("Lancer")){
                            drgIcon = classTitle.attr("abs:src");
                            drg = row.text();
                        }

                        else if(stringClassTitle.equals("Ninja / Rogue") || stringClassTitle.equals("Rogue")){
                            ninIcon = classTitle.attr("abs:src");
                            nin = row.text();
                        }

                        else if(stringClassTitle.equals("Samurai")){
                            samIcon = classTitle.attr("abs:src");
                            sam = row.text();
                        }

                        else if(stringClassTitle.equals("Bard / Archer") || stringClassTitle.equals("Archer")){
                            brdIcon = classTitle.attr("abs:src");
                            brd = row.text();
                        }

                        else if(stringClassTitle.equals("Machinist")){
                            mchIcon = classTitle.attr("abs:src");
                            mch = row.text();
                        }

                        else if(stringClassTitle.equals("Black Mage / Thaumaturge") || stringClassTitle.equals("Thaumaturge")){
                            blmIcon = classTitle.attr("abs:src");
                            blm = row.text();
                        }

                        else if(stringClassTitle.equals("Summoner / Arcanist") || stringClassTitle.equals("Arcanist")){
                            smnIcon = classTitle.attr("abs:src");
                            smn = row.text();
                        }

                        else if(stringClassTitle.equals("Red Mage")){
                            rdmIcon = classTitle.attr("abs:src");
                            rdm = row.text();
                        }

                        else if(stringClassTitle.equals("Blue Mage (Limited Job)")){
                            bluIcon = classTitle.attr("abs:src");
                            blu = row.text();
                        }
                    }
                }

                // Set the main character object for levels and icons for job classes
                mainCharProfile.SetTanks(pld, war,dk);
                mainCharProfile.SetTankIcons(pldIcon, warIcon, dkIcon);
                mainCharProfile.SetHealers(whm, sch, ast);
                mainCharProfile.SetHealerIcons(whmIcon, schIcon, astIcon);
                mainCharProfile.SetMele(mnk, drg, nin, sam);
                mainCharProfile.SetMeleIcons(mnkIcon, drgIcon, ninIcon, samIcon);
                mainCharProfile.SetRanged(brd, mch, blm, smn, rdm, blu);
                mainCharProfile.SetRangedIcons(brdIcon, mchIcon, blmIcon, smnIcon, rdmIcon, bluIcon);


            }

            // Try to open savedCharacterProfile.txt to store the object
            try {
                FileOutputStream fileOut = openFileOutput("savedCharacterProfile.txt", MODE_PRIVATE);
                ObjectOutputStream outputWriter = new ObjectOutputStream(fileOut);
                outputWriter.writeObject(mainCharProfile);
                outputWriter.close();

                // Display toast on success
                Toast toast = Toast.makeText(getApplicationContext(), "Success: Main Profile Set", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

            }

            // Else => catch exception
            catch (Exception e){
                e.printStackTrace();
            }

            // Change view to reflect the profile was set
            setProfileButton.setText("PROFILE SET");
            progressBar.setVisibility(View.INVISIBLE);
            charImage.setVisibility(View.VISIBLE);
            testing.setVisibility(View.VISIBLE);
        }

    }

    /**/
    /*

    NAME

        CrawlerBattle Async Task

    PURPOSE
        This is a private CrawlerData class that is used within the character search activity screen.
        This async task is called when a character profile is being set and needs to store all the
        battle achievements associated with that character object.

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    private class CrawlerBattle extends AsyncTask<String, Void, Void>{
        Document document = null;

        /**/
        /*
        public void doInBackground()

        NAME

            doInBackground - this method performs what to do in the background upon being called for
            for the character search

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

            onPostExecute

        SYNOPSIS

            protected void onPostExecute(Void result)
                Void result          --> result of connection
                Achievement[] battleAchievements    --> stores the HTML battle achievements as achievements objects
                String pointValue       --> store the point value of achievement points for player
                Elements achievementPoints      --> element holding achievement points
                Elements achievementListing     --> elements holding achievement name
                String complete                 --> element holding if the achievement was obtained or not
                Elements achievementPointListing  --> element holding the achievement point
                Character mainCharProfile   --> character object to set data to

        DESCRIPTION

            onPostExecute will take in a battle achievement URL and scan the HTML for the
            battle achievements. These are all listed in this page and will contain different
            div elements depending on if the achievement was obtained or not. This is used
            to store every achievement in this category for the character object.

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
            // Initialize the achievement array
            Achievement[] battleAchievements = new Achievement[350];

            // Set the array to empty achievement objects
            for(int i = 0; i < battleAchievements.length; i++){
                battleAchievements[i] = new Achievement();
            }

            // Set point value
            String pointValue = "";
            int count = 0;


            if(document != null){
                // Find achievement points
                Elements achievementPoints = document.select("p.achievement__point");
                pointValue = achievementPoints.text();

                // Find all achievements block
                Elements achievementListing = document.select("p.entry__activity__txt");

                // For every achievement element..
                for(Element entry: achievementListing){

                    // Store the name
                    String name = entry.text();
                    battleAchievements[count].SetTitle(name);

                    // Store the completion
                    String complete = entry.parent().parent().attr("class");
                    if(complete.equals("entry__achievement entry__achievement--complete")){
                        battleAchievements[count].SetObtained("Yes");
                    }

                    // Store the incompletion
                    if(complete.equals("entry__achievement")){
                        battleAchievements[count].SetObtained("No");
                    }
                    count++;
                }

                count = 0;

                // Get achievement listing points
                Elements achievementPointListing = document.select("p.entry__achievement__number");
                for(Element points : achievementPointListing){
                    String point = points.text();
                    battleAchievements[count].SetPoint(point);
                    count++;
                }

                // Get achievement listing icons
                count = 0;
                Elements achievementIconListing = document.select("div.entry__achievement__frame");
                for(Element icon : achievementIconListing){
                    Elements iconImg = icon.select("img");
                    String iconString = iconImg.attr("abs:src");
                    battleAchievements[count].SetIcon(iconString);
                    count++;
                }
            }

            // Set main character profile points and battle achievements
            mainCharProfile.SetPoints(pointValue);
            mainCharProfile.SetAllBattle(battleAchievements);
        }
    }

    /**/
    /*

    NAME

        CrawlerPvP Async Task

    PURPOSE
        This is a private CrawlerPvP class that is used within the character search activity screen.
        This async task is called when a character profile is being set and needs to store all the
        pvp achievements associated with that character object.

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    private class CrawlerPvP extends AsyncTask<String, Void, Void>{
        Document document = null;

        /**/
        /*
        public void doInBackground()

        NAME

            doInBackground - this method performs what to do in the background upon being called for
            for the character search

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

            onPostExecute

        SYNOPSIS

            protected void onPostExecute(Void result)
                Void result          --> result of connection
                Achievement[] pvpAchievements    --> stores the HTML pvp achievements as achievements objects
                Elements achievementPoints      --> element holding achievement points
                Elements achievementListing     --> elements holding achievement name
                String complete                 --> element holding if the achievement was obtained or not
                Elements achievementPointListing  --> element holding the achievement point
                Character mainCharProfile   --> character object to set data to

        DESCRIPTION

            onPostExecute will take in a pvp achievement URL and scan the HTML for the
            pvp achievements. These are all listed in this page and will contain different
            div elements depending on if the achievement was obtained or not. This is used
            to store every achievement in this category for the character object.

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
            // Set achievement object array
            Achievement[] pvpAchievements = new Achievement[200];

            // Initialize all empty achievements
            for(int i = 0; i < pvpAchievements.length; i++){
                pvpAchievements[i] = new Achievement();
            }
            int count = 0;

            if(document != null){
                // Find the listing of achievements element
                Elements achievementListing = document.select("p.entry__activity__txt");
                for(Element entry: achievementListing){

                    // Store the name of each achievement
                    String name = entry.text();
                    pvpAchievements[count].SetTitle(name);

                    // Store the completion/incompletion of the achievement
                    String complete = entry.parent().parent().attr("class");
                    if(complete.equals("entry__achievement entry__achievement--complete")){
                        pvpAchievements[count].SetObtained("Yes");
                    }
                    if(complete.equals("entry__achievement")){
                        pvpAchievements[count].SetObtained("No");
                    }
                    count++;
                }

                // Store the point numbers
                count = 0;
                Elements achievementPointListing = document.select("p.entry__achievement__number");
                for(Element points : achievementPointListing){
                    String point = points.text();
                    pvpAchievements[count].SetPoint(point);
                    count++;
                }

                // Store the icons
                count = 0;
                Elements achievementIconListing = document.select("div.entry__achievement__frame");
                for(Element icon : achievementIconListing){
                    Elements iconImg = icon.select("img");
                    String iconString = iconImg.attr("abs:src");
                    pvpAchievements[count].SetIcon(iconString);
                    count++;
                }

            }
            // Set character objects pvp achievements
            mainCharProfile.SetAllPvP(pvpAchievements);
        }
    }

    /**/
    /*

    NAME

        CrawlerChar Async Task

    PURPOSE
        This is a private CrawlerChar class that is used within the character search activity screen.
        This async task is called when a character profile is being set and needs to store all the
        character achievements associated with that character object.

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    private class CrawlerChar extends AsyncTask<String, Void, Void>{
        Document document = null;

        /**/
        /*
        public void doInBackground()

        NAME

            doInBackground - this method performs what to do in the background upon being called for
            for the character search

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

            onPostExecute

        SYNOPSIS

            protected void onPostExecute(Void result)
                Void result          --> result of connection
                Achievement[] charAchievements    --> stores the HTML character achievements as achievements objects
                Elements achievementPoints      --> element holding achievement points
                Elements achievementListing     --> elements holding achievement name
                String complete                 --> element holding if the achievement was obtained or not
                Elements achievementPointListing  --> element holding the achievement point
                Character mainCharProfile   --> character object to set data to

        DESCRIPTION

            onPostExecute will take in a character achievement URL and scan the HTML for the
            character achievements. These are all listed in this page and will contain different
            div elements depending on if the achievement was obtained or not. This is used
            to store every achievement in this category for the character object.

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
            // Initialize character achievement object array
            Achievement[] charAchievements = new Achievement[350];

            // Set empty achievement objects in array
            for(int i = 0; i < charAchievements.length; i++){
                charAchievements[i] = new Achievement();
            }
            int count = 0;

            if(document != null){
                // Get achievement listing element
                Elements achievementListing = document.select("p.entry__activity__txt");
                for(Element entry: achievementListing){

                    // Store name
                    String name = entry.text();
                    charAchievements[count].SetTitle(name);

                    // Store complete/incomplete
                    String complete = entry.parent().parent().attr("class");
                    if(complete.equals("entry__achievement entry__achievement--complete")){
                        charAchievements[count].SetObtained("Yes");
                    }
                    if(complete.equals("entry__achievement")){
                        charAchievements[count].SetObtained("No");
                    }
                    count++;
                }

                // Store point value
                count = 0;
                Elements achievementPointListing = document.select("p.entry__achievement__number");
                for(Element points : achievementPointListing){
                    String point = points.text();
                    charAchievements[count].SetPoint(point);
                    count++;
                }

                // Store icon url
                count = 0;
                Elements achievementIconListing = document.select("div.entry__achievement__frame");
                for(Element icon : achievementIconListing){
                    Elements iconImg = icon.select("img");
                    String iconString = iconImg.attr("abs:src");
                    charAchievements[count].SetIcon(iconString);
                    count++;
                }

            }
            // Set main character objects character achievements
            mainCharProfile.SetAllCharacter(charAchievements);
        }
    }

    /**/
    /*

    NAME

        CrawlerItems Async Task

    PURPOSE
        This is a private CrawlerItems class that is used within the character search activity screen.
        This async task is called when a character profile is being set and needs to store all the
        items achievements associated with that character object.

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    private class CrawlerItems extends AsyncTask<String, Void, Void>{
        Document document = null;

        /**/
        /*
        public void doInBackground()

        NAME

            doInBackground - this method performs what to do in the background upon being called for
            for the character search

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

            onPostExecute

        SYNOPSIS

            protected void onPostExecute(Void result)
                Void result          --> result of connection
                Achievement[] itemsAchievements    --> stores the HTML items achievements as achievements objects
                String pointValue       --> store the point value of achievement points for player
                Elements achievementPoints      --> element holding achievement points
                Elements achievementListing     --> elements holding achievement name
                String complete                 --> element holding if the achievement was obtained or not
                Elements achievementPointListing  --> element holding the achievement point
                Character mainCharProfile   --> character object to set data to

        DESCRIPTION

            onPostExecute will take in a items achievement URL and scan the HTML for the
            items achievements. These are all listed in this page and will contain different
            div elements depending on if the achievement was obtained or not. This is used
            to store every achievement in this category for the character object.

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
            // Initialize array of achievements for item achievement objects
            Achievement[] itemsAchievements = new Achievement[300];

            // Set achievement objects
            for(int i = 0; i < itemsAchievements.length; i++){
                itemsAchievements[i] = new Achievement();
            }
            int count = 0;

            if(document != null){
                // Get element holding achievements
                Elements achievementListing = document.select("p.entry__activity__txt");
                for(Element entry: achievementListing){
                    // Get name
                    String name = entry.text();
                    itemsAchievements[count].SetTitle(name);

                    // Get complete/incomplete
                    String complete = entry.parent().parent().attr("class");
                    if(complete.equals("entry__achievement entry__achievement--complete")){
                        itemsAchievements[count].SetObtained("Yes");
                    }
                    if(complete.equals("entry__achievement")){
                        itemsAchievements[count].SetObtained("No");
                    }
                    count++;
                }

                // Get point value
                count = 0;
                Elements achievementPointListing = document.select("p.entry__achievement__number");
                for(Element points : achievementPointListing){
                    String point = points.text();
                    itemsAchievements[count].SetPoint(point);
                    count++;
                }

                // Get icon string url
                count = 0;
                Elements achievementIconListing = document.select("div.entry__achievement__frame");
                for(Element icon : achievementIconListing){
                    Elements iconImg = icon.select("img");
                    String iconString = iconImg.attr("abs:src");
                    itemsAchievements[count].SetIcon(iconString);
                    count++;
                }

            }
            // Set character objects items achievements
            mainCharProfile.SetAllItems(itemsAchievements);
        }
    }

    /**/
    /*

    NAME

        CrawlerCrafting Async Task

    PURPOSE
        This is a private CrawlerCrafting class that is used within the character search activity screen.
        This async task is called when a character profile is being set and needs to store all the
        crafting achievements associated with that character object.

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    private class CrawlerCrafting extends AsyncTask<String, Void, Void>{
        Document document = null;

        /**/
        /*
        public void doInBackground()

        NAME

            doInBackground - this method performs what to do in the background upon being called for
            for the character search

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

            onPostExecute

        SYNOPSIS

            protected void onPostExecute(Void result)
                Void result          --> result of connection
                Achievement[] craftingAchievements    --> stores the HTML crafting achievements as achievements objects
                Elements achievementPoints      --> element holding achievement points
                Elements achievementListing     --> elements holding achievement name
                String complete                 --> element holding if the achievement was obtained or not
                Elements achievementPointListing  --> element holding the achievement point
                Character mainCharProfile   --> character object to set data to

        DESCRIPTION

            onPostExecute will take in a crafting achievement URL and scan the HTML for the
            crafting achievements. These are all listed in this page and will contain different
            div elements depending on if the achievement was obtained or not. This is used
            to store every achievement in this category for the character object.

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
            // Initialize achievement object array
            Achievement[] craftingAchievements = new Achievement[200];

            // Set all achievement objects in array
            for(int i = 0; i < craftingAchievements.length; i++){
                craftingAchievements[i] = new Achievement();
            }
            int count = 0;

            if(document != null){
                // Get achievement element listing
                Elements achievementListing = document.select("p.entry__activity__txt");
                for(Element entry: achievementListing){

                    // Store achievement name
                    String name = entry.text();
                    craftingAchievements[count].SetTitle(name);

                    // Store complete/incomplete
                    String complete = entry.parent().parent().attr("class");
                    if(complete.equals("entry__achievement entry__achievement--complete")){
                        craftingAchievements[count].SetObtained("Yes");
                    }
                    if(complete.equals("entry__achievement")){
                        craftingAchievements[count].SetObtained("No");
                    }
                    count++;
                }

                // Store point value
                count = 0;
                Elements achievementPointListing = document.select("p.entry__achievement__number");
                for(Element points : achievementPointListing){
                    String point = points.text();
                    craftingAchievements[count].SetPoint(point);
                    count++;
                }

                // Store icon url
                count = 0;
                Elements achievementIconListing = document.select("div.entry__achievement__frame");
                for(Element icon : achievementIconListing){
                    Elements iconImg = icon.select("img");
                    String iconString = iconImg.attr("abs:src");
                    craftingAchievements[count].SetIcon(iconString);
                    count++;
                }

            }
            // Set main character object crafting achievements
            mainCharProfile.SetAllCrafting(craftingAchievements);
        }
    }

    /**/
    /*

    NAME

        CrawlerGathering Async Task

    PURPOSE
        This is a private CrawlerGathering class that is used within the character search activity screen.
        This async task is called when a character profile is being set and needs to store all the
        gathering achievements associated with that character object.

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    private class CrawlerGathering extends AsyncTask<String, Void, Void>{
        Document document = null;

        /**/
        /*
        public void doInBackground()

        NAME

            doInBackground - this method performs what to do in the background upon being called for
            for the character search

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

            onPostExecute

        SYNOPSIS

            protected void onPostExecute(Void result)
                Void result          --> result of connection
                Achievement[] gatheringAchievements    --> stores the HTML gathering achievements as achievements objects
                Elements achievementPoints      --> element holding achievement points
                Elements achievementListing     --> elements holding achievement name
                String complete                 --> element holding if the achievement was obtained or not
                Elements achievementPointListing  --> element holding the achievement point
                Character mainCharProfile   --> character object to set data to

        DESCRIPTION

            onPostExecute will take in a gathering achievement URL and scan the HTML for the
            gathering achievements. These are all listed in this page and will contain different
            div elements depending on if the achievement was obtained or not. This is used
            to store every achievement in this category for the character object.

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
            // Initialize achievement objects array
            Achievement[] gatheringAchievements = new Achievement[150];

            // Set each achievement object to new
            for(int i = 0; i < gatheringAchievements.length; i++){
                gatheringAchievements[i] = new Achievement();
            }
            int count = 0;

            if(document != null){
                // Get achievement listing element
                Elements achievementListing = document.select("p.entry__activity__txt");
                for(Element entry: achievementListing){

                    // Store name
                    String name = entry.text();
                    gatheringAchievements[count].SetTitle(name);

                    // Store complete/incomplete
                    String complete = entry.parent().parent().attr("class");
                    if(complete.equals("entry__achievement entry__achievement--complete")){
                        gatheringAchievements[count].SetObtained("Yes");
                    }
                    if(complete.equals("entry__achievement")){
                        gatheringAchievements[count].SetObtained("No");
                    }
                    count++;
                }

                // Store achievement point value
                count = 0;
                Elements achievementPointListing = document.select("p.entry__achievement__number");
                for(Element points : achievementPointListing){
                    String point = points.text();
                    gatheringAchievements[count].SetPoint(point);
                    count++;
                }

                // Store icon url
                count = 0;
                Elements achievementIconListing = document.select("div.entry__achievement__frame");
                for(Element icon : achievementIconListing){
                    Elements iconImg = icon.select("img");
                    String iconString = iconImg.attr("abs:src");
                    gatheringAchievements[count].SetIcon(iconString);
                    count++;
                }

            }
            // Set character object for gathering achievements
            mainCharProfile.SetAllGathering(gatheringAchievements);
        }
    }

    /**/
    /*

    NAME

        CrawlerQuests Async Task

    PURPOSE
        This is a private CrawlerQuests class that is used within the character search activity screen.
        This async task is called when a character profile is being set and needs to store all the
        quest achievements associated with that character object.

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    private class CrawlerQuests extends AsyncTask<String, Void, Void>{
        Document document = null;

        /**/
        /*
        public void doInBackground()

        NAME

            doInBackground - this method performs what to do in the background upon being called for
            for the character search

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

            onPostExecute

        SYNOPSIS

            protected void onPostExecute(Void result)
                Void result          --> result of connection
                Achievement[] questsAchievements    --> stores the HTML quests achievements as achievements objects
                Elements achievementPoints      --> element holding achievement points
                Elements achievementListing     --> elements holding achievement name
                String complete                 --> element holding if the achievement was obtained or not
                Elements achievementPointListing  --> element holding the achievement point
                Character mainCharProfile   --> character object to set data to

        DESCRIPTION

            onPostExecute will take in a quests achievement URL and scan the HTML for the
            quests achievements. These are all listed in this page and will contain different
            div elements depending on if the achievement was obtained or not. This is used
            to store every achievement in this category for the character object.

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
            // Initialize achievement array
            Achievement[] questsAchievements = new Achievement[350];

            // Set each achievement object in array
            for(int i = 0; i < questsAchievements.length; i++){
                questsAchievements[i] = new Achievement();
            }
            int count = 0;

            if(document != null){
                // Get element listing of achievements
                Elements achievementListing = document.select("p.entry__activity__txt");

                // For every achievement
                for(Element entry: achievementListing){
                    // Store name
                    String name = entry.text();
                    questsAchievements[count].SetTitle(name);

                    // Store complete/incomplete
                    String complete = entry.parent().parent().attr("class");
                    if(complete.equals("entry__achievement entry__achievement--complete")){
                        questsAchievements[count].SetObtained("Yes");
                    }
                    if(complete.equals("entry__achievement")){
                        questsAchievements[count].SetObtained("No");
                    }
                    count++;
                }

                // Store point value
                count = 0;
                Elements achievementPointListing = document.select("p.entry__achievement__number");
                for(Element points : achievementPointListing){
                    String point = points.text();
                    questsAchievements[count].SetPoint(point);
                    count++;
                }

                // Store icon url
                count = 0;
                Elements achievementIconListing = document.select("div.entry__achievement__frame");
                for(Element icon : achievementIconListing){
                    Elements iconImg = icon.select("img");
                    String iconString = iconImg.attr("abs:src");
                    questsAchievements[count].SetIcon(iconString);
                    count++;
                }

            }

            // Set character object for quest achievements
            mainCharProfile.SetAllQuests(questsAchievements);
        }
    }

    /**/
    /*

    NAME

        CrawlerExploration Async Task

    PURPOSE
        This is a private CrawlerExploration class that is used within the character search activity screen.
        This async task is called when a character profile is being set and needs to store all the
        exploration achievements associated with that character object.

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    private class CrawlerExploration extends AsyncTask<String, Void, Void>{
        Document document = null;

        /**/
        /*
        public void doInBackground()

        NAME

            doInBackground - this method performs what to do in the background upon being called for
            for the character search

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

            onPostExecute

        SYNOPSIS

            protected void onPostExecute(Void result)
                Void result          --> result of connection
                Achievement[] explorationAchievements    --> stores the HTML exploration achievements as achievements objects
                Elements achievementPoints      --> element holding achievement points
                Elements achievementListing     --> elements holding achievement name
                String complete                 --> element holding if the achievement was obtained or not
                Elements achievementPointListing  --> element holding the achievement point
                Character mainCharProfile   --> character object to set data to

        DESCRIPTION

            onPostExecute will take in a exploration achievement URL and scan the HTML for the
            exploration achievements. These are all listed in this page and will contain different
            div elements depending on if the achievement was obtained or not. This is used
            to store every achievement in this category for the character object.

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
            // Initialize achievement array
            Achievement[] explorationAchievements = new Achievement[200];

            // Set achievement objects to new
            for(int i = 0; i < explorationAchievements.length; i++){
                explorationAchievements[i] = new Achievement();
            }
            int count = 0;

            if(document != null){
                // Get element holding achievement listing
                Elements achievementListing = document.select("p.entry__activity__txt");

                // For every achievement
                for(Element entry: achievementListing){

                    // Store name
                    String name = entry.text();
                    explorationAchievements[count].SetTitle(name);

                    // Store complete/incomplete
                    String complete = entry.parent().parent().attr("class");
                    if(complete.equals("entry__achievement entry__achievement--complete")){
                        explorationAchievements[count].SetObtained("Yes");
                    }
                    if(complete.equals("entry__achievement")){
                        explorationAchievements[count].SetObtained("No");
                    }
                    count++;
                }

                // Store point value
                count = 0;
                Elements achievementPointListing = document.select("p.entry__achievement__number");
                for(Element points : achievementPointListing){
                    String point = points.text();
                    explorationAchievements[count].SetPoint(point);
                    count++;
                }

                // Store icon url
                count = 0;
                Elements achievementIconListing = document.select("div.entry__achievement__frame");
                for(Element icon : achievementIconListing){
                    Elements iconImg = icon.select("img");
                    String iconString = iconImg.attr("abs:src");
                    explorationAchievements[count].SetIcon(iconString);
                    count++;
                }

            }

            // Set character object for exploration achievements
            mainCharProfile.SetAllExploration(explorationAchievements);
        }
    }

    /**/
    /*

    NAME

        CrawlerGC Async Task

    PURPOSE
        This is a private CrawlerGC class that is used within the character search activity screen.
        This async task is called when a character profile is being set and needs to store all the
        gc achievements associated with that character object.

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    private class CrawlerGC extends AsyncTask<String, Void, Void>{
        Document document = null;

        /**/
        /*
        public void doInBackground()

        NAME

            doInBackground - this method performs what to do in the background upon being called for
            for the character search

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

            onPostExecute

        SYNOPSIS

            protected void onPostExecute(Void result)
                Void result          --> result of connection
                Achievement[] gcAchievements    --> stores the HTML gc achievements as achievements objects
                Elements achievementPoints      --> element holding achievement points
                Elements achievementListing     --> elements holding achievement name
                String complete                 --> element holding if the achievement was obtained or not
                Elements achievementPointListing  --> element holding the achievement point
                Character mainCharProfile   --> character object to set data to

        DESCRIPTION

            onPostExecute will take in a gc achievement URL and scan the HTML for the
            gc achievements. These are all listed in this page and will contain different
            div elements depending on if the achievement was obtained or not. This is used
            to store every achievement in this category for the character object.

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
            // Initialize achievement object array
            Achievement[] gcAchievements = new Achievement[100];

            // Create new achievements in array
            for(int i = 0; i < gcAchievements.length; i++){
                gcAchievements[i] = new Achievement();
            }
            int count = 0;

            if(document != null){
                // Get element holding achievement listing
                Elements achievementListing = document.select("p.entry__activity__txt");

                for(Element entry: achievementListing){

                    // Store name
                    String name = entry.text();
                    gcAchievements[count].SetTitle(name);

                    // Store complete/incomplete
                    String complete = entry.parent().parent().attr("class");
                    if(complete.equals("entry__achievement entry__achievement--complete")){
                        gcAchievements[count].SetObtained("Yes");
                    }
                    if(complete.equals("entry__achievement")){
                        gcAchievements[count].SetObtained("No");
                    }
                    count++;
                }

                // Store point value
                count = 0;
                Elements achievementPointListing = document.select("p.entry__achievement__number");
                for(Element points : achievementPointListing){
                    String point = points.text();
                    gcAchievements[count].SetPoint(point);
                    count++;
                }

                // Store icon url
                count = 0;
                Elements achievementIconListing = document.select("div.entry__achievement__frame");
                for(Element icon : achievementIconListing){
                    Elements iconImg = icon.select("img");
                    String iconString = iconImg.attr("abs:src");
                    gcAchievements[count].SetIcon(iconString);
                    count++;
                }

            }

            // Set character object for gc achievements
            mainCharProfile.SetAllGC(gcAchievements);
        }
    }
}
