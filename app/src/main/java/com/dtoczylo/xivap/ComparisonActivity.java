/**/
/*

NAME

        Comparison Activity

PURPOSE
        This is the comparison controller class that is responsible for controlling the
        view of the achievement comparison feature.

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
import android.widget.ProgressBar;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;

import static java.util.Arrays.sort;

public class ComparisonActivity extends AppCompatActivity {

    // Initializations
    Friend[] friends;
    ProgressBar progressBar;

    private ArrayList<Friend> friendArrayList;
    private ArrayList<Friend> sortedFriendArrayList;
    private RecyclerView recyclerView;
    private FriendAdapter adapter;

    /**/
    /*
    public void onCreate()

    NAME

        onCreate - this formats the view of the activity on creation

    SYNOPSIS

        public void onCreate(Bundle savedInstanceState)
            Bundle savedInstanceState               --> if the activity of being re-initialized after previously being shut down this bundle
                                                        contains the data it most recently supplied in onSaveInstanceState(Bundle)
            ArrayList<Friend> friendArrayList       --> creates a new array adapter
            ArrayList<Friend> sortedFriendArrayList --> array list of sorted friends
            RecyclerView recyclerView               --> recycler viewer
            FriendAdapter adapter                   --> friend adapter
            Friend[] friends                        --> friend array of friends
            ProgressBar progressBar                 --> progress bar

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
        setContentView(R.layout.activity_comparison);
        // Set screen orientation
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // FOR TESTING PURPOSES IF NEED TO CLEAR SERIALIZATION FILE
        //forceClear();

        // Initializations
        friends = new Friend[5];
        progressBar = findViewById(R.id.progressBar15);
        progressBar.setVisibility(View.INVISIBLE);

        friendArrayList = new ArrayList<>();
        sortedFriendArrayList = new ArrayList<>();


        // Check if friend array has been set
        if(setFriendArray() == true){
            // If true => set the friend array
            setFriendArray();
        }else{
            // If false => generate a new friend array
            for(int i = 0; i < friends.length; i++){
                friends[i] = new Friend();
            }
        }

        // Sort friends by highest achievement points to lowest
        sortedFriendArrayList = sortFriends();

        // Bind the recycler viewer for display
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new FriendAdapter(sortedFriendArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ComparisonActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // Hide progress bar
        progressBar.setVisibility(View.INVISIBLE);

    }


    /**/
    /*
    public void findFriends()

    NAME

        findFriends - finds friends for the serialization file, savedFriends.txt and then sets
                      the friend array to the friend objects found in the array

    SYNOPSIS

        public void findFriends(View v)
            View v          --> parameter view
            progressBar     --> set the progress bar to visible/hidden
            Intent intent   --> intent for new activity
            friendArrayList --> adapter that will bind all the friends
            friends         --> friend array of friends, converts array list to array

    DESCRIPTION

        The findFriends method gets all the friend objects in the adapter then will write to the file,
        savedFriends.txt to store them. It will clear the file first, then write to avoid any overwriting.

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void findFriends(View v) {
        progressBar.setVisibility(View.VISIBLE);

        friendArrayList = adapter.getDataList();
        friends = friendArrayList.toArray(new Friend[friendArrayList.size()]);
        forceClear();
        writeToFile();

        Intent intent = new Intent(ComparisonActivity.this, FriendSearchActivity.class);
        startActivity(intent);
    }


    /**/
    /*
    public void returnButton()

    NAME

        returnButton - returns ComparisonActivity to MainActivity view

    SYNOPSIS

        public void returnButton(View view)
            View view       --> parameter view
            progressBar     --> set the progress bar to visible
            Intent intent   --> intent for new activity

    DESCRIPTION

        The return button allows to return to the Main Activity view from the Comparison Activity view.
        Before returning to the screen the friend array is re-set to adjust for deletion/addition of new
        friends in the list.

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

        friendArrayList = adapter.getDataList();
        friends = friendArrayList.toArray(new Friend[friendArrayList.size()]);
        forceClear();
        writeToFile();

        Intent intent = new Intent(ComparisonActivity.this, MainActivity.class);
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
    public void writeToFile()

    NAME

        writeToFile - writes to the savedFriends.txt file to store friend objects

    SYNOPSIS

        public void writeToFile()
            FileOutputStream fileOut        --> file output stream
            ObjectOutputStream outputWriter --> object output stream


    DESCRIPTION

        This method is used to open the file, savedFriends.txt, and write to the file to populate
        friend objects.

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void writeToFile(){
        // Try to open file
        try {
            FileOutputStream fileOut = openFileOutput("savedFriends.txt", MODE_PRIVATE);
            ObjectOutputStream outputWriter = new ObjectOutputStream(fileOut);
            // Write friend array of friend objects
            outputWriter.writeObject(friends);
            outputWriter.close();
        }
        // Else file couldn't open => Catch exception
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**/
    /*
    public void setFriendArray()

    NAME

        setFriendArray - sets the friend array for the savedFriends.txt file

    SYNOPSIS

        public boolean setFriendArray()
            FileInputStream fileIn        --> file input stream
            ObjectInputStream inputReader --> object input stream
            friends                       --> friend array of friend objects to inject

    DESCRIPTION

        This method is used to set the friend object array to the friend objects found in the
        serialization file savedFriends.txt

    RETURNS

        True if friend object array found, False if no friend objects found

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
            // Write to friend array
            friends = (Friend[]) inputReader.readObject();
            inputReader.close();
            fileIn.close();
            return true;

        }
        // If can't open => catch exception
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**/
    /*
    public void sortFriends()

    NAME

        sortFriends - sets the friend array for the savedFriends.txt file

    SYNOPSIS

        public ArrayList<Friend> sortFriends()
            ArrayList<Friend> rankedArrayList        --> array list of ranked friends
            Integer[] pointsArray                    --> integer point value array
            int charPoints                           --> integer character point value

    DESCRIPTION

        This method is used to sort friend objects by their point values. It will return
        an array list for the friend adapter to view them from highest point value to
        lowest.

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public ArrayList<Friend> sortFriends(){
        // Initializations
        ArrayList<Friend> rankedArrayList;
        Integer[] pointsArray;

        rankedArrayList = new ArrayList<>();
        pointsArray = new Integer[friends.length];

        int charPoints;

        // Get character points from friend objects
        for(int i = 0; i < friends.length; i++){
            charPoints = Integer.parseInt(friends[i].GetCharacterPoints());
            pointsArray[i] = charPoints;
        }

        // Sort the points array
        sort(pointsArray, Collections.reverseOrder());

        // If the point value matches the value of the friend object, reorder the list
        for(int j = 0; j < friends.length; j++){
            for(int i = 0; i < friends.length; i++){
                charPoints = Integer.parseInt(friends[i].GetCharacterPoints());
                if(charPoints == pointsArray[j]){
                    rankedArrayList.add(friends[i]);
                    break;
                }
            }
        }

        // Return the sorted ranked array list of friend objects by point values highest to lowest
        return rankedArrayList;
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

}
