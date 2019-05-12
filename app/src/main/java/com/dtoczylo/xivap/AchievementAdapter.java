/**/
/*

NAME

        Achievement Adapter Class

PURPOSE
        This is an Achievement Adapter Class used to display achievements for a Recycler View within the
        Category(Battle, Character, Crafting, Exploration, Gathering, GC, Items, PvP and Quest) Activities.
        The adaptor class is responsible for displaying a "Card" for the achievement and changes the card
        view based on it's obtained/unattained status. The attributes for an achievement card include:
        background color, achievement name, status, icon, point, description and border width for the icon.

AUTHOR

        Desiree Toczylowski

DATE

        5/8/2019

*/
/**/

package com.dtoczylo.xivap;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.AchievementViewHolder> {
    private ArrayList<Achievement> dataList;

    /**/
    /*
    public AchievementAdapter()

    NAME

        AchievementAdapter - This sets the achievement adapter that generates each card view from a data list of achievements
        relative to a character object

    SYNOPSIS

        public AchievementAdapter(ArrayList<Achievement> a_dataList)
            ArrayList<Achievement> a_dataList     --> this is an array list of achievement objects called dataList
            this.dataList                         --> this is the adaptors achievement list that needs to be generated into a card

    DESCRIPTION

        The achievement adapter class is used within different category views and requires a list of achievement
        objects to parse. This receives an array list of achievement objects (a_dataList) and sets the achievement
        adaptor dataList to the given parameter and generates a card view for each to be used within a Recycler View.

    RETURNS

        Nothing, it is setting a dataList for an Achievement Adapter

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public AchievementAdapter(ArrayList<Achievement> a_dataList){
        this.dataList = a_dataList;
    }

    /**/
    /*
    public AchievementViewHolder onCreateViewHolder()

    NAME

        AchievementViewHolder onCreateViewHolder - this is what happens on the creation of view
        utilizing an achievement adaptor class. It initializes the View Holder.

    SYNOPSIS

        public AchievementViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
            ViewGroup parent              --> view group into which view will be added after it is bound to an
                                        adaptor position
            int viewType                  --> the view type of the new view
            LayoutInflater layoutInflater --> instantiates a layout XML file into the corresponding View object
            View view                     --> generates an interface for the view from the layout inflater
            AchievementViewHolder(view)   --> returns a new view holder that holds a view of the given view type


    DESCRIPTION

        OnCreateViewHolder is called right when the adaptor is created and is used to initialize the view holder and
        generate the new view with the adaptor data.

    RETURNS

        Returns a new view holder

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    @Override
    public AchievementViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_achievement, parent, false);
        return new AchievementViewHolder(view);
    }

    /**/
    /*
    public void onBindViewHolder()

    NAME

        onBindViewHolder - called by a recycler view to display data at a specified position. This method updates
        the contents of the itemView to reflect the item at a given position

    SYNOPSIS

        public void onBindViewHolder(AchievementViewHolder holder, int position)
            AchievementViewHolder holder    --> the view holder which should be updated to represent the contents of the item
                                                at the given position in the data set
            int position                    --> the position of the item within the adapter's data set
            String status                   --> get the data set (achievement) status ("Yes"/"No")


    DESCRIPTION

        onBindViewHolder is a built in class for an adapter class. This method is used to display
        the data at the specific position. For each achievement within the recycler view this
        will display each of the achievement cards for viewing at the respective position on
        the screen.

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    @Override
    public void onBindViewHolder(AchievementViewHolder holder, int position){
        // Retrieve an achievement object (holder) status
        String status = dataList.get(position).GetObtained();

        // If the achievement has been obtained by the player ..
        if(status.equals("Yes")){
            // Set Background of card
            holder.cardBackground.setCardBackgroundColor(Color.parseColor("#E6d5c6b8"));
            // Set achievement name
            holder.achievementName.setText(dataList.get(position).GetTitle());
            // Set achievement status to complete for viewing
            holder.achievementStatus.setText("Completed");
            // Set the color of the icon border
            holder.achievementIcon.setBorderColor(Color.parseColor("#FFFFFF"));

        // Else the achievement has NOT been obtained by the player ..
        }else{
            // Set background of card
            holder.cardBackground.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            // Set achievement name
            holder.achievementName.setText(dataList.get(position).GetTitle());
            // Set status to incomplete for viewing
            holder.achievementStatus.setText("Incomplete");
            // Set the color of the icon border
            holder.achievementIcon.setBorderColor(Color.parseColor("#FBFBFB"));
        }

        // Set the achievement point value
        holder.achievementPoint.setText("⭐" + dataList.get(position).GetPoint());
        // Set the achievement icon image
        holder.achievementIcon.setImageResource(SetIcon(dataList.get(position).getIcon()));
        // Set the achievement description
        holder.achievementDescription.setText(dataList.get(position).GetDescription());
        // Set the border width of the icon
        holder.achievementIcon.setBorderWidth(5);
    }

    /**/
    /*
    public int getItemCount()

    NAME

        getItemCount - this returns the amount of achievement's in the data list

    SYNOPSIS

        public int getItemCount()
            dataList.size       --> returns the size of the data list


    DESCRIPTION

        getItemCount is a method used to return the size of the achievement list being displayed
        within the recycler view.

    RETURNS

        Returns an integer size

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    @Override
    public int getItemCount(){
        return dataList.size();
    }

    /**/
    /*
    public int SetIcon()

    NAME

        SetIcon - this will set a given URL string to a saved icon within the app to make
        the display of icons for achievements faster.

    SYNOPSIS

        public int SetIcon(String a_url)
            dataList.size       --> returns the size of the data list
            int resourceID      --> contains the resource ID of the icon


    DESCRIPTION

        SetIcon is used to return a resource ID of a given drawable icon assocaited with an
        achievement icon. The URL of the icon is received and compared to return the appropriate
        resource ID for that achievement. This method slows down the time it takes to display
        icons since they are not being downloaded, they are being displays as a resource from
        the local app files.

    RETURNS

        Returns an integer resource id number

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public int SetIcon(String a_url){
        // Initialize resource ID
        int resourceID = 0;

        // Switch method to parse the URL of an achievement object icon
        switch (a_url){
            case "https://img.finalfantasyxiv.com/lds/pc/global/images/itemicon/71/716038a99395c8ce4a936d2b110e5ea491817689.png?4.56":
                resourceID = R.drawable.crushenemies;
                break;
            case "https://img.finalfantasyxiv.com/lds/pc/global/images/itemicon/13/13d8e8a2ef5c94ea1d71a8f84099cde45fb8999a.png?4.56":
                resourceID = R.drawable.bodiesfloor;
                break;
            case "https://img.finalfantasyxiv.com/lds/pc/global/images/itemicon/ed/ed2c99c5517f550fb7f9a5a0639db96553e64d86.png?4.56":
                resourceID = R.drawable.logpld;
                break;
            case "https://img.finalfantasyxiv.com/lds/pc/global/images/itemicon/bf/bf16585169850ca46ecd9d3e3a946b8ae8547733.png?4.56":
                resourceID = R.drawable.logmnk;
                break;
            case "https://img.finalfantasyxiv.com/lds/pc/global/images/itemicon/fc/fc3fcef14dc06a4995c6c3c33099c2e375c7a6e9.png?4.56":
                resourceID = R.drawable.logpld;
                break;
            case "https://img.finalfantasyxiv.com/lds/pc/global/images/itemicon/64/648453ce341355f946adc7aea017c32eb5b68e09.png?4.56":
                resourceID = R.drawable.logdrg;
                break;
            case "https://img.finalfantasyxiv.com/lds/pc/global/images/itemicon/af/af32e79d064c849b0af8716d408e2a389c4d4a83.png?4.56":
                resourceID = R.drawable.logbrd;
                break;
            case "https://img.finalfantasyxiv.com/lds/pc/global/images/itemicon/a8/a81f3fc18baa1fc9cf3c0a5dbb65d677c2b26ab1.png?4.56":
                resourceID = R.drawable.lognin;
                break;
            case "https://img.finalfantasyxiv.com/lds/pc/global/images/itemicon/59/5952281933e401e95c899e3f526154f2fcf7aef1.png?4.56":
                resourceID = R.drawable.logwhm;
                break;
            case "https://img.finalfantasyxiv.com/lds/pc/global/images/itemicon/05/058a5823cbf62cffce0c0440a8aa4b18ef53fc8c.png?4.56":
                resourceID = R.drawable.logsmn;
                break;
            case "https://img.finalfantasyxiv.com/lds/pc/global/images/itemicon/12/12893813a3497f9d5096e5ebdd68715b711bf441.png?4.56":
                resourceID = R.drawable.logblm;
                break;
            case "https://img.finalfantasyxiv.com/lds/pc/global/images/itemicon/f9/f9eb0a43cb351d63722abe103a86591b7fce1c83.png?4.56":
                resourceID = R.drawable.fateachieve;
                break;
            case "https://img.finalfantasyxiv.com/lds/pc/global/images/itemicon/dc/dc5ad3ef107c45c4040375a526a87531858fe680.png?4.56":
                resourceID = R.drawable.datedestiny;
                break;
            case "https://img.finalfantasyxiv.com/lds/pc/global/images/itemicon/d2/d24bb569ff04d813bf7f19d6ccdf1906699cd02f.png?4.56":
                resourceID = R.drawable.buddies;
                break;
            case "https://img.finalfantasyxiv.com/lds/pc/global/images/itemicon/ef/ef1fe5228816143c4b1c37832fa4ea911da2a144.png?4.56":
                resourceID = R.drawable.strangers;
                break;
            case "https://img.finalfantasyxiv.com/lds/pc/global/images/itemicon/b5/b5f99a1cb360775812dbc2870ca8286b567d1764.png?4.56":
                resourceID = R.drawable.tanklesspld;
                break;
            default:
                resourceID = 0;
        }

        // Returns the appropriate resource ID
        return resourceID;

    }


    /**/
    /*

    NAME

        AchievementViewHolder class

    PURPOSE
        This is an Achievement ViewHolder class that is applied to a the achievement adapter class.
        This is holds the main components of an Achievement Card View

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    class AchievementViewHolder extends RecyclerView.ViewHolder {
        // Text view holders
        TextView achievementName, achievementPoint, achievementStatus, achievementDescription;

        // Icon holder
        de.hdodenhof.circleimageview.CircleImageView achievementIcon;

        // Card view holder
        CardView cardBackground;

        /**/
        /*
        AchievementViewHolder()

        NAME

            AchievementViewHolder - this is the class that is sent into the adapter class. It accepts a view
            and then find the id of the view to be used within the adapter.

        SYNOPSIS
             AchievementViewHolder(View itemView)
                View itemView           --> an item view
                cardBackground          --> the id of the card
                achievementName         --> the id of the text view for the achievement name
                achievementPoint        --> the id of the text view for the achievement point
                achievementIcon         --> the id of the circle image view for the achievement icon
                achievementStatus       --> the id of the text view for the achievement status
                achievementDescription  --> the id of the text view for the achievement description

        DESCRIPTION

            SetIcon is used to return a resource ID of a given drawable icon associated with an
            achievement icon. The URL of the icon is received and compared to return the appropriate
            resource ID for that achievement. This method slows down the time it takes to display
            icons since they are not being downloaded, they are being displays as a resource from
            the local app files.

        RETURNS

            Nothing

        AUTHOR

            Desiree Toczylowski

        DATE

            5/8/2019

        */
        /**/
        AchievementViewHolder(View itemView){
            super(itemView);
            cardBackground = (CardView) itemView.findViewById(R.id.card);
            achievementName = (TextView) itemView.findViewById(R.id.achievementTitle);
            achievementPoint = (TextView) itemView.findViewById(R.id.achievementPoint);
            achievementIcon = (de.hdodenhof.circleimageview.CircleImageView) itemView.findViewById(R.id.achievementIcon);
            achievementStatus = itemView.findViewById(R.id.achievement_status);
            achievementDescription = itemView.findViewById(R.id.achievement_description);
        }
    }
}
