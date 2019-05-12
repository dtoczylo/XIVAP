/**/
/*

NAME

        Friend Adapter Class

PURPOSE
        This is a Friend Adapter Class used to display friends for a Recycler View within the
        Comparison Activity. The adapter class is responsible for displaying a "Card" for the friend.
        The attributes for an friend card include: name, card background, server, achievement points,
        icon, and border width/color of icon

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
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {
    private ArrayList<Friend> dataList;
    private Friend[] friends;

    /**/
    /*
    public FriendAdapter()

    NAME

        FriendAdapter - This sets the friend adapter that generates each card view from a data list of friends
        relative

    SYNOPSIS

        public FriendAdapter(ArrayList<Friend> a_dataList)
            ArrayList<Friend> a_dataList     --> this is an array list of friend objects called dataList
            this.dataList                    --> this is the adaptors friend list that needs to be generated into a card
            friends                          --> new friend array of friend objects, set to data list of friend objects

    DESCRIPTION

        The friend adapter class is used within the comparison activity and requires a list of friends
        objects to display. This receives an array list of friend objects (a_dataList) and sets the friend
        adaptor dataList to the given parameter and generates a card view for each to be used within a Recycler View.

    RETURNS

        Nothing, it is setting a dataList for a Friend Adapter

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public FriendAdapter(ArrayList<Friend> a_dataList){
        this.dataList = a_dataList;
        friends = new Friend[5];
        friends = dataList.toArray(friends);
    }

    /**/
    /*
    public FriendViewHolder onCreateViewHolder()

    NAME

        FriendViewHolder onCreateViewHolder - this is what happens on the creation of view
        utilizing a friend adaptor class. It initializes the View Holder.

    SYNOPSIS

        public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
            ViewGroup parent              --> view group into which view will be added after it is bound to an
                                              adaptor position
            int viewType                  --> the view type of the new view
            LayoutInflater layoutInflater --> instantiates a layout XML file into the corresponding View object
            View view                     --> generates an interface for the view from the layout inflater
            FriendViewHolder(view)        --> returns a new view holder that holds a view of the given view type


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
    public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_friend, parent, false);
        return new FriendViewHolder(view);
    }

    /**/
    /*
    public void onBindViewHolder()

    NAME

        onBindViewHolder - called by a recycler view to display data at a specified position. This method updates
        the contents of the itemView to reflect the item at a given position

    SYNOPSIS

        public void onBindViewHolder(FriendViewHolder holder, int position)
            FriendViewHolder holder         --> the view holder which should be updated to represent the contents of the item
                                                at the given position in the data set
            int position                    --> the position of the item within the adapter's data set


    DESCRIPTION

        onBindViewHolder is a built in class for an adapter class. This method is used to display
        the data at the specific position. For each friend within the recycler view this
        will display each of the friend cards for viewing at the respective position on
        the screen. This method utilizes the image downloading library known as Picasso.
        https://square.github.io/picasso/ which downloades the icon image and saves it
        places it in the respective view id holder.

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    @Override
    public void onBindViewHolder(FriendViewHolder holder, final int position){

        // Initialize friend card values
        holder.cardBackground.setCardBackgroundColor(Color.parseColor("#E6d5c6b8"));
        holder.charName.setText(dataList.get(position).GetCharacterName());
        holder.charServer.setText(dataList.get(position).GetCharacterServer());
        holder.charrAchievementPoint.setText("⭐" + dataList.get(position).GetCharacterPoints());
        holder.charIcon.setBorderColor(Color.parseColor("#FFFFFF"));
        holder.charIcon.setBorderWidth(5);

        // Try to place icon into icon view id
        try {
            Picasso.get().load(dataList.get(position).GetCharacterIcon()).into(holder.charIcon);
        }
        // Else => catch exception, set icon to searching
        catch(Exception e){
            e.printStackTrace();
            holder.charIcon.setImageResource(R.drawable.searching);
        }

        // Delete method to show the activity of deletion within the recycler viewer
        holder.delete.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(position >= 0){
                    Friend removedFriend = dataList.get(position);
                    dataList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, dataList.size());
                }
            }
        });
    }

    /**/
    /*
    public int getItemCount()

    NAME

        getItemCount - this returns the amount of friends in the data list

    SYNOPSIS

        public int getItemCount()
            dataList.size       --> returns the size of the data list


    DESCRIPTION

        getItemCount is a method used to return the size of the friend list being displayed
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
    public ArrayList<Friend> getDataList()

    NAME

        getDataList - this returns the friends in the adapter

    SYNOPSIS

        public ArrayList<Friend> getDataList()
            dataList       --> returns the data list


    DESCRIPTION

        getDataList is a method used to return the friends in the adapter class. If changing
        activities, the data list could have changed so we store it and then return the active
        list when needed.

    RETURNS

        Returns an array list of friends, dataList

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public ArrayList<Friend> getDataList() {
        return dataList;
    }

    /**/
    /*

    NAME

        FriendViewHolder class

    PURPOSE
        This is a Friend ViewHolder class that is applied to a the friend adapter class.
        This is holds the main components of a Friend Card View

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    class FriendViewHolder extends RecyclerView.ViewHolder {
        TextView charName, charrAchievementPoint, charServer;
        de.hdodenhof.circleimageview.CircleImageView charIcon;
        Button delete;
        CardView cardBackground;


        /**/
        /*
        FriendViewHolder()

        NAME

            FriendViewHolder - this is the class that is sent into the adapter class. It accepts a view
            and then find the id of the view to be used within the adapter.

        SYNOPSIS
             FriendViewHolder(View itemView)
                View itemView           --> an item view
                cardBackground          --> the id of the card
                charName                --> the id of the text view for the friend character name
                charrAchievementPoint   --> the id of the text view for the friend character point
                charIcon                --> the id of the circle image view for the friend character icon
                charServer              --> the id of the text view for the friend character server
                delete                  --> the id of the item view for delete button

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
        FriendViewHolder(View itemView){
            super(itemView);
            cardBackground = (CardView) itemView.findViewById(R.id.card);
            charName = (TextView) itemView.findViewById(R.id.friendName);
            charrAchievementPoint = (TextView) itemView.findViewById(R.id.achievementPoint);
            charIcon = (de.hdodenhof.circleimageview.CircleImageView) itemView.findViewById(R.id.friendIcon);
            charServer = itemView.findViewById(R.id.friendServer);
            delete = itemView.findViewById(R.id.button2);
        }
    }
}
