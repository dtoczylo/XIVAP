/**/
/*

NAME

        Character Class

PURPOSE
        This is a character object model class. The entire application relies on the storage
        of a Character object and this is the model that will hold parsed HTML values for
        serialization to be manipulated within other controllers.

AUTHOR

        Desiree Toczylowski

DATE

        5/8/2019

*/
/**/
package com.dtoczylo.xivap;

import java.io.Serializable;

public class Character implements Serializable {

    private String characterName;
    private String characterServer;
    private String characterIcon;
    private String characterTitle;
    private String characterLink;
    private String characterID;
    private String characterRace;
    private String characterGuardian;
    private String characterCity;
    private String characterGC;
    private String characterFC;
    private String characterPoints;
    private String characterImage;
    private String characterGuardianIcon;
    private String characterCityIcon;
    private String characterGCIcon;
    private String characterFCIcon;
    private String characterFCIconBase;
    private Integer characterMountTotal;
    private Integer characterMinionTotal;
    private String[] characterTank;
    private String[] characterHealer;
    private String[] characterMele;
    private String[] characterRanged;
    private String[] characterTankIcons;
    private String[] characterHealerIcons;
    private String[] characterMeleIcons;
    private String[] characterRangedIcons;
    private String[] category;
    private Achievement[] allBattle;
    private Achievement[] allPvP;
    private Achievement[] allCharacter;
    private Achievement[] allItems;
    private Achievement[] allCrafting;
    private Achievement[] allGathering;
    private Achievement[] allQuests;
    private Achievement[] allExploration;
    private Achievement[] allGC;
    private Friend[] friends;


    /**/
    /*
    public Character

    NAME

        Achievement - This is the default constructor for the Character Class

    SYNOPSIS

        public Character()
            this.characterName          --> stores a characters username
            this.characterServer        --> stores a characters server
            this.characterIcon          --> stores a characters icon url
            this.characterTitle         --> stores a characters title
            this.characterLink          --> stores a characters profile url
            this.characterID            --> stores a characters id number
            this.characterRace          --> stores a characters race
            this.characterGuardian      --> stores a characters guardian
            this.characterCity          --> stores a characters city
            this.characterGC            --> stores a characters grand company
            this.characterFC            --> stores a characters free company
            this.characterImage         --> stores a characters url image
            this.characterPoints        --> stores a characters achievement points
            this.characterGuardianIcon  --> stores a characters guardian icon url
            this.characterCityIcon      --> stores a characters city icon url
            this.characterGCIcon        --> stores a characters grand company icon url
            this.characterFCIcon        --> stores a characters fc icon url
            this.characterFCIconBase    --> stores a characters fc icon base url
            this.characterMinionTotal   --> stores a characters minion total
            this.characterMountTotal    --> stores a characters mount total
            this.characterTank          --> stores a characters tank levels
            this.characterTankIcons     --> stores a characters tank icon urls
            this.characterHealer        --> stores a characters healer levels
            this.characterHealerIcons   --> stores a characters healer icon urls
            this.characterMele          --> stores a characters mele levels
            this.characterMeleIcons     --> stores a characters mele icon urls
            this.characterRanged        --> stores a characters ranged levels
            this.characterRangedIcons   --> stores a characters ranged icon urls
            this.category               --> stores the string values of different achievement category names
            this.allBattle              --> stores a characters battle achievements
            this.allPvP                 --> stores a characters pvp achievements
            this.allCharacter           --> stores a characters character achievements
            this.allItems               --> stores a characters items achievements
            this.allCrafting            --> stores a characters crafting achievements
            this.allGathering           --> stores a characters gathering achievements
            this.allQuests              --> stores a characters quests achievements
            this.allExploration         --> stores a characters exploration achievements
            this.allGC                  --> stores a characters grand company achievements

    DESCRIPTION

        This constructor initializations all important attributes of a character object.

    RETURNS

        Nothing, it is a constructor

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public Character(){
        this.characterName = "";
        this.characterServer = "";
        this.characterIcon = "";
        this.characterTitle = "";
        this.characterLink = "";
        this.characterID = "";
        this.characterRace = "";
        this.characterGuardian = "";
        this.characterCity = "";
        this.characterGC = "";
        this.characterFC = "";
        this.characterImage = "";
        this.characterPoints = "-1";
        this.characterGuardianIcon = "";
        this.characterCityIcon = "";
        this.characterGCIcon = "";
        this.characterFCIcon = "";
        this.characterFCIconBase = "";
        this.characterMinionTotal = 0;
        this.characterMountTotal = 0;
        this.characterTank = new String[3];
        this.characterTankIcons = new String[3];
        this.characterHealer = new String[3];
        this.characterHealerIcons = new String[3];
        this.characterMele = new String[4];
        this.characterMeleIcons = new String[4];
        this.characterRanged = new String[6];
        this.characterRangedIcons = new String[6];

        this.category = new String [9];
        this.category[0] = "Battle";
        this.category[1] = "PvP";
        this.category[2] = "Character";
        this.category[3] = "Items";
        this.category[4] = "Crafting";
        this.category[5] = "Gathering";
        this.category[6] = "Quests";
        this.category[7] = "Exploration";
        this.category[8] = "Grand Company";

        this.allBattle = new Achievement[350];
        for(int i = 0; i < allBattle.length; i++){
            allBattle[i] = new Achievement();
        }
        this.allPvP = new Achievement[200];
        for(int i = 0; i < allPvP.length; i++){
            allPvP[i] = new Achievement();
        }

        this.allCharacter = new Achievement[350];
        for(int i = 0; i < allCharacter.length; i++){
            allCharacter[i] = new Achievement();
        }

        this.allItems = new Achievement[300];
        for(int i = 0; i < allItems.length; i++){
            allItems[i] = new Achievement();
        }

        this.allCrafting = new Achievement[200];
        for(int i = 0; i < allCrafting.length; i++){
            allCrafting[i] = new Achievement();
        }

        this.allGathering = new Achievement[150];
        for(int i = 0; i < allGathering.length; i++){
            allGathering[i] = new Achievement();
        }

        this.allQuests = new Achievement[350];
        for(int i = 0; i < allQuests.length; i++){
            allQuests[i] = new Achievement();
        }

        this.allExploration = new Achievement[200];
        for(int i = 0; i < allExploration.length; i++){
            allExploration[i] = new Achievement();
        }

        this.allGC = new Achievement[100];
        for(int i = 0; i < allGC.length; i++){
            allGC[i] = new Achievement();
        }

        this.friends = new Friend[5];
        for(int i = 0; i < friends.length; i++){
            friends[i] = new Friend();
        }
    }

    /**/
    /*
    public GetName()

    NAME

        GetName - this method returns the given character object's stored name

    SYNOPSIS

        public String GetName()
            characterName   --> character object's character name


    DESCRIPTION

        GetName returns a characters name

    RETURNS

        Returns a character objects string name

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String GetName(){
        return characterName;
    }

    /**/
    /*
    public SetName()

    NAME

        SetName - this method sets the given character object's stored name

    SYNOPSIS

        public void SetName(String a_name)
            String a_name        --> parameter string name to set
            this.characterName   --> character object's character name


    DESCRIPTION

        SetName sets a characters name

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetName(String a_name){
        this.characterName = a_name;
    }

    /**/
    /*
    public GetServer()

    NAME

        GetServer - this method gets the given character object's stored server

    SYNOPSIS

        public String GetServer(e)
            this.characterServer   --> character object's character server


    DESCRIPTION

        GetServer returns the character's server name in a string

    RETURNS

        Returns a string of the character's server

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String GetServer(){
        return characterServer;
    }

    /**/
    /*
    public SetServer()

    NAME

        SetServer - this method sets the given character object's stored server

    SYNOPSIS

        public void SetServer(String a_server)
            String a_server        --> parameter string server to set
            this.characterServer   --> character object's character server


    DESCRIPTION

        SetServer sets a characters server

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetServer(String a_server){
        this.characterServer = a_server;
    }

    /**/
    /*
    public SetID()

    NAME

        SetID - this method sets the given character object's stored ID as a string

    SYNOPSIS

        public void SetID(String a_id)
            String a_id        --> parameter string server to set
            this.characterID   --> character object's character ID


    DESCRIPTION

        SetID sets a characters ID

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetID(String a_id) { this.characterID = a_id; }

    /**/
    /*
    public GetIcon()

    NAME

        GetIcon - this method gets the given character object's stored server

    SYNOPSIS

        public String GetIcon()
            this.characterIcon   --> character object's character icon


    DESCRIPTION

        GetIcon returns the character's server icon url in a string

    RETURNS

        Returns a string of the character's icon url

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String GetIcon(){
        return characterIcon;
    }

    /**/
    /*
    public SetIcon()

    NAME

        SetIcon - this method sets the given character object's stored icon url as a string

    SYNOPSIS

        public void SetIcon(String a_icon)
            String a_icon        --> parameter string icon url to set
            this.characterIcon   --> character object's character icon url


    DESCRIPTION

        SetID sets a characters icon url

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetIcon(String a_icon){ this.characterIcon = a_icon; }

    /**/
    /*
    public GetTitle()

    NAME

        GetTitle - this method gets the given character object's stored title

    SYNOPSIS

        public String GetTitle()
            this.characterTitle   --> character object's character title


    DESCRIPTION

        GetTitle returns the character's title in a string

    RETURNS

        Returns a string of the character's title

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String GetTitle(){ return characterTitle; }

    /**/
    /*
    public SetTitle()

    NAME

        SetTitle - this method sets the given character object's stored title as a string

    SYNOPSIS

        public void SetTitle(String a_title)
            String a_title        --> parameter string title to set
            this.characterTitle   --> character object's character title


    DESCRIPTION

        SetID sets a characters title

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetTitle(String a_title){ this.characterTitle = a_title; }

    /**/
    /*
    public GetRace()

    NAME

        GetRace - this method gets the given character object's stored race

    SYNOPSIS

        public String GetRace()
            this.characterRace   --> character object's character race


    DESCRIPTION

        GetRace returns the character's race in a string

    RETURNS

        Returns a string of the character's race

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String GetRace(){
        return characterRace;
    }

    /**/
    /*
    public SetRace()

    NAME

        SetRace - this method sets the given character object's stored race as a string

    SYNOPSIS

        public void SetRace(String a_race)
            String a_race        --> parameter string race to set
            this.characterRace   --> character object's character race


    DESCRIPTION

        SetRace sets a characters race

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetRace(String a_race){ this.characterRace = a_race; }

    /**/
    /*
    public GetGuardian()

    NAME

        GetGuardian - this method gets the given character object's stored guardian

    SYNOPSIS

        public String GetGuardian()
            this.characterGuardian   --> character object's character guardian


    DESCRIPTION

        GetGuardian returns the character's guardian in a string

    RETURNS

        Returns a string of the character's guardian

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String GetGuardian(){
        return characterGuardian;
    }

    /**/
    /*
    public SetGuardian()

    NAME

        SetGuardian - this method sets the given character object's stored guardian as a string

    SYNOPSIS

        public void SetGuardian(String a_guardian)
            String a_guardian        --> parameter string guardian to set
            this.characterGuardian   --> character object's character guardian


    DESCRIPTION

        SetGuardian sets a characters guardian

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetGuardian(String a_guardian){ this.characterGuardian = a_guardian; }

    /**/
    /*
    public GetGuardianIcon()

    NAME

        GetGuardianIcon - this method gets the given character object's stored guardian icon url

    SYNOPSIS

        public String GetGuardianIcon()
            this.characterGuardianIcon   --> character object's character guardian icon url


    DESCRIPTION

        GetGuardianIcon returns the character's guardian icon url in a string

    RETURNS

        Returns a string of the character's guardian icon url

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String GetGuardianIcon(){
        return characterGuardianIcon;
    }

    /**/
    /*
    public SetGuardianIcon()

    NAME

        SetGuardianIcon - this method sets the given character object's stored guardian icon url as a string

    SYNOPSIS

        public void SetGuardianIcon(String a_guardianIcon)
            String a_guardianIcon        --> parameter string guardian icon url to set
            this.characterGuardianIcon   --> character object's character guardian icon url


    DESCRIPTION

        SetGuardianIcon sets a characters guardian icon url

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetGuardianIcon(String a_guardianIcon){ this.characterGuardianIcon = a_guardianIcon; }

    /**/
    /*
    public GetCity()

    NAME

        GetCity - this method gets the given character object's stored city

    SYNOPSIS

        public String GetCity()
            this.characterCity   --> character object's character city


    DESCRIPTION

        GetCity returns the character's city name in a string

    RETURNS

        Returns a string of the character's city

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String GetCity(){
        return characterCity;
    }

    /**/
    /*
    public SetCity()

    NAME

        SetCity - this method sets the given character object's stored city as a string

    SYNOPSIS

        public void SetCity(String a_city)
            String a_city        --> parameter string city to set
            this.characterCity   --> character object's character city


    DESCRIPTION

        SetCity sets a characters city

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetCity(String a_city){ this.characterCity = a_city; }

    /**/
    /*
    public GetCityIcon()

    NAME

        GetCityIcon - this method gets the given character object's stored city icon url

    SYNOPSIS

        public String GetCityIcon()
            this.characterCityIcon   --> character object's character city icon url


    DESCRIPTION

        GetCityIcon returns the character's city icon url in a string

    RETURNS

        Returns a string of the character's city icon url

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String GetCityIcon(){
        return characterCityIcon;
    }

    /**/
    /*
    public SetCityIcon()

    NAME

        SetCityIcon - this method sets the given character object's stored city icon url as a string

    SYNOPSIS

        public void SetCityIcon(String a_cityIcon)
            String a_cityIcon        --> parameter string city icon url to set
            this.characterCityIcon   --> character object's character city icon url


    DESCRIPTION

        SetCityIcon sets a characters city icon url

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetCityIcon(String a_cityIcon){ this.characterCityIcon = a_cityIcon; }

    /**/
    /*
    public GetGC()

    NAME

        GetGC - this method gets the given character object's stored grand company

    SYNOPSIS

        public String GetGC()
            this.characterGC   --> character object's character grand company


    DESCRIPTION

        GetGC returns the character's grand company name in a string

    RETURNS

        Returns a string of the character's grand company

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String GetGC(){
        return characterGC;
    }

    /**/
    /*
    public SetGC()

    NAME

        SetGC - this method sets the given character object's stored grand company as a string

    SYNOPSIS

        public void SetGC(String a_gc)
            String a_gc        --> parameter string grand company to set
            this.characterGC   --> character object's character grand company


    DESCRIPTION

        SetGC sets a characters grand company

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetGC(String a_gc){ this.characterGC = a_gc; }

    /**/
    /*
    public GetGCIcon()

    NAME

        GetGCIcon - this method gets the given character object's stored grand company

    SYNOPSIS

        public String GetGCIcon()
            this.characterGCIcon   --> character object's character grand company


    DESCRIPTION

        GetGCIcon returns the character's grand company in a string

    RETURNS

        Returns a string of the character's grand company

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String GetGCIcon(){
        return characterGCIcon;
    }

    /**/
    /*
    public SetGCIcon()

    NAME

        SetGCIcon - this method sets the given character object's stored grand company icon url as a string

    SYNOPSIS

        public void SetGCIcon(String a_gcIcon)
            String a_gcIcon        --> parameter string grand company icon url to set
            this.characterGCIcon   --> character object's character grand company icon url


    DESCRIPTION

        SetGCIcon sets a characters grand company icon url

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetGCIcon(String a_gcIcon){ this.characterGCIcon = a_gcIcon; }

    /**/
    /*
    public GetFC()

    NAME

        GetFC - this method gets the given character object's stored free company

    SYNOPSIS

        public String GetFC()
            this.characterFC   --> character object's character free company


    DESCRIPTION

        GetFC returns the character's free company in a string

    RETURNS

        Returns a string of the character's free company

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String GetFC(){
        return characterFC;
    }

    /**/
    /*
    public SetFC()

    NAME

        SetFC - this method sets the given character object's stored free company

    SYNOPSIS

        public void SetFC(String a_fc)
            String a_fc        --> parameter string free company to set
            this.characterFC   --> character object's character free company


    DESCRIPTION

        SetFC sets a characters free company

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetFC(String a_fc){ this.characterFC = a_fc; }

    /**/
    /*
    public GetFCIcon()

    NAME

        GetFCIcon - this method gets the given character object's stored free company icon url

    SYNOPSIS

        public String GetFCIcon()
            this.characterFCIcon   --> character object's character free company icon url


    DESCRIPTION

        GetFCIcon returns the character's free company icon url in a string

    RETURNS

        Returns a string of the character's free company icon url

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String GetFCIcon(){
        return characterFCIcon;
    }

    /**/
    /*
    public SetFCIcon()

    NAME

        SetFCIcon - this method sets the given character object's stored free company icon url as a string

    SYNOPSIS

        public void SetFCIcon(String a_fcIcon)
            String a_fcIcon        --> parameter string free company icon url to set
            this.characterFCIcon   --> character object's character free company icon url


    DESCRIPTION

        SetFCIcon sets a characters free company icon url

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetFCIcon(String a_fcIcon){ this.characterFCIcon = a_fcIcon; }

    /**/
    /*
    public GetFCIconBase()

    NAME

        GetFCIconBase - this method gets the given character object's stored free company base icon url

    SYNOPSIS

        public String GetFCIconBase()
            this.characterFCIconBase   --> character object's character free company base icon url


    DESCRIPTION

        GetFCIconBase returns the character's free company base icon url in a string

    RETURNS

        Returns a string of the character's free company base icon url

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String GetFCIconBase(){
        return characterFCIconBase;
    }

    /**/
    /*
    public SetFCIconBase()

    NAME

        SetFCIconBase - this method sets the given character object's stored free company base icon url as a string

    SYNOPSIS

        public void SetFCIconBase(String a_fcIconBase)
            String a_fcIconBase        --> parameter string server to set
            this.characterFCIconBase   --> character object's character ID


    DESCRIPTION

        SetFCIconBase sets a characters free company base icon url

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetFCIconBase(String a_fcIconBase){ this.characterFCIconBase = a_fcIconBase; }

    /**/
    /*
    public GetImage()

    NAME

        GetImage - this method gets the given character object's stored image url

    SYNOPSIS

        public String GetImage()
            this.characterImage   --> character object's character image url


    DESCRIPTION

        GetImage returns the character's image url in a string

    RETURNS

        Returns a string of the character's image url

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String GetImage(){
        return characterImage;
    }

    /**/
    /*
    public SetImage()

    NAME

        SetImage - this method sets the given character object's stored image url as a string

    SYNOPSIS

        public void SetImage(String a_image)
            String a_image        --> parameter string image url to set
            this.characterImage   --> character object's character image url


    DESCRIPTION

        SetImage sets a characters image url

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetImage(String a_image){ this.characterImage = a_image; }

    /**/
    /*
    public GetMinionTotal()

    NAME

        GetMinionTotal - this method gets the given character object's stored minion total

    SYNOPSIS

        public Integer GetMinionTotal()
            this.characterMinionTotal   --> character object's character minion total


    DESCRIPTION

        GetMinionTotal returns the character's minion total

    RETURNS

        Returns an integer of the character's minion total

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public Integer GetMinionTotal() { return characterMinionTotal; }

    /**/
    /*
    public SetMinionTotal()

    NAME

        SetMinionTotal - this method sets the given character object's stored minion total as an Integer

    SYNOPSIS

        public void SetMinionTotal(String a_count)
            String a_count              --> parameter minion total to set
            this.characterMinionTotal   --> character object's character minion total


    DESCRIPTION

        SetMinionTotal sets a characters minion total

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetMinionTotal(Integer a_count) { this.characterMinionTotal = a_count; }

    /**/
    /*
    public GetMountTotal()

    NAME

        GetMountTotal - this method gets the given character object's stored mount total

    SYNOPSIS

        public Integer GetMountTotal()
            this.characterMountTotal   --> character object's character mount total


    DESCRIPTION

        GetMountTotal returns the character's mount total in a Integer

    RETURNS

        Returns an Integer of the character's mount total

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public Integer GetMountTotal() { return characterMountTotal; }

    /**/
    /*
    public SetMountTotal()

    NAME

        SetMountTotal - this method sets the given character object's stored mount total as a Integer

    SYNOPSIS

        public void SetMountTotal(String a_count)
            String a_count             --> parameter string mount total to set
            this.characterMountTotal   --> character object's character mount total


    DESCRIPTION

        SetMountTotal sets a characters mount total

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetMountTotal(Integer a_count) { this.characterMountTotal = a_count; }

    /**/
    /*
    public GetPoints()

    NAME

        GetPoints - this method gets the given character object's stored achievement points

    SYNOPSIS

        public String GetPoints()
            this.characterPoints   --> character object's character achievement points


    DESCRIPTION

        GetPoints returns the character's achievement points in a string

    RETURNS

        Returns a string of the character's achievement points

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String GetPoints() { return this.characterPoints; }

    /**/
    /*
    public SetPoints()

    NAME

        SetPoints - this method sets the given character object's stored achievement points as a string

    SYNOPSIS

        public void SetPoints(String a_points)
            String a_points        --> parameter string achievement points to set
            this.characterPoints   --> character object's character achievement points


    DESCRIPTION

        SetPoints sets a characters achievement points

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetPoints(String a_points) { this.characterPoints = a_points; }

    /**/
    /*
    public GetTanks()

    NAME

        GetTanks - this method gets the given character object's stored tank levels in a string array

    SYNOPSIS

        public String[] GetTanks()
            this.characterTank   --> character object's tank levels in a string array


    DESCRIPTION

        GetTanks returns the character's tank levels in a string array

    RETURNS

        Returns a string array of the character's tank levels

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String[] GetTanks() { return this.characterTank; }

    /**/
    /*
    public SetTanks()

    NAME

        SetTanks - this method sets the given character object's stored tank levels in a string array

    SYNOPSIS

        public void SetTanks(String a_pld, String a_war, String a_dk)
            String a_pld           --> parameter paladin level to set
            String a_war           --> parameter warrior level to set
            String a_dk            --> parameter dark knight level to set
            this.characterTank[]   --> character object's character tank levels in a string array


    DESCRIPTION

        SetTanks sets a characters tank levels in a string array

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetTanks(String a_pld, String a_war, String a_dk) {
        this.characterTank[0] = a_pld;
        this.characterTank[1] = a_war;
        this.characterTank[2] = a_dk;
    }

    /**/
    /*
    public GetTankIcons()

    NAME

        GetTankIcons - this method gets the given character object's stored tank icon urls in a string array

    SYNOPSIS

        public String[] GetTankIcons()
            this.characterTankIcons   --> character object's tank icon urls in a string array


    DESCRIPTION

        GetTankIcons returns the character's tank icon urls in a string array

    RETURNS

        Returns a string array of the character's tank icon urls

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String[] GetTankIcons() { return this.characterTankIcons; }

    /**/
    /*
    public SetTankIcons()

    NAME

        SetTankIcons - this method sets the given character object's stored tank icon urls in a string array

    SYNOPSIS

        public void SetTankIcons(String a_pld, String a_war, String a_dk)
            String a_pld                --> parameter paladin tank icon url to set
            String a_war                --> parameter warrior tank icon url to set
            String a_dk                 --> parameter dark knight tank icon url to set
            this.characterTankIcons[]   --> character object's character tank icon urls in a string array


    DESCRIPTION

        SetTankIcons sets a characters tank icon urls in a string array

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetTankIcons(String a_pld, String a_war, String a_dk) {
        this.characterTankIcons[0] = a_pld;
        this.characterTankIcons[1] = a_war;
        this.characterTankIcons[2] = a_dk;
    }

    /**/
    /*
    public GetHealers()

    NAME

        GetHealers - this method gets the given character object's stored healer levels in a string array

    SYNOPSIS

        public String[] GetHealers()
            this.characterHealer   --> character object's healer levels in a string array


    DESCRIPTION

        GetHealers returns the character's healer levels in a string array

    RETURNS

        Returns a string array of the character's healer levels

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String[] GetHealers() { return this.characterHealer; }

    /**/
    /*
    public SetHealers()

    NAME

        SetHealers - this method sets the given character object's stored healer levels in a string array

    SYNOPSIS

        public void SetHealers(String a_whm, String a_sch, String a_ast)
            String a_whm             --> parameter white mage level to set
            String a_sch             --> parameter scholar level to set
            String a_ast             --> parameter ast level to set
            this.characterHealer[]   --> character object's character healer levels in a string array


    DESCRIPTION

        SetHealers sets a characters healer levels in a string array

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetHealers(String a_whm, String a_sch, String a_ast) {
        this.characterHealer[0] = a_whm;
        this.characterHealer[1] = a_sch;
        this.characterHealer[2] = a_ast;
    }

    /**/
    /*
    public GetHealerIcons()

    NAME

        GetHealerIcons - this method gets the given character object's stored healer icon urls in a string array

    SYNOPSIS

        public String[] GetHealerIcons()
            this.characterHealerIcons   --> character object's healer icon urls in a string array


    DESCRIPTION

        GetHealerIcons returns the character's healer icon urls in a string array

    RETURNS

        Returns a string array of the character's healer icon urls

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String[] GetHealerIcons() { return this.characterHealerIcons; }

    /**/
    /*
    public SetHealerIcons()

    NAME

        SetHealerIcons - this method sets the given character object's stored healer icon urls in a string array

    SYNOPSIS

        public void SetHealerIcons(String a_whm, String a_sch, String a_ast)
            String a_whm             --> parameter white mage icon urls to set
            String a_sch             --> parameter scholar icon urls to set
            String a_ast             --> parameter ast icon urls to set
            this.characterHealer[]   --> character object's character healer icon urls in a string array


    DESCRIPTION

        SetHealerIcons sets a characters healer icon urls in a string array

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetHealerIcons(String a_whm, String a_sch, String a_ast) {
        this.characterHealerIcons[0] = a_whm;
        this.characterHealerIcons[1] = a_sch;
        this.characterHealerIcons[2] = a_ast;
    }

    /**/
    /*
    public GetMele()

    NAME

        GetMele - this method gets the given character object's stored mele levels in a string array

    SYNOPSIS

        public String[] GetMele()
            this.characterMele   --> character object's mele levels in a string array


    DESCRIPTION

        GetMele returns the character's mele levels in a string array

    RETURNS

        Returns a string array of the character's mele levels

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String[] GetMele() { return this.characterMele; }

    /**/
    /*
    public SetMele()

    NAME

        SetMele - this method sets the given character object's stored mele levels in a string array

    SYNOPSIS

        public void SetMele(String a_mnk, String a_drg, String a_nin, String a_sam)
            String a_mnk           --> parameter monk level to set
            String a_drg           --> parameter dragoon level to set
            String a_nin            --> parameter ninja level to set
            String a_sam            --> parameter sam level to set
            this.characterMele[]   --> character object's character mele levels in a string array


    DESCRIPTION

        SetMele sets a characters mele levels in a string array

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetMele(String a_mnk, String a_drg, String a_nin, String a_sam) {
        this.characterMele[0] = a_mnk;
        this.characterMele[1] = a_drg;
        this.characterMele[2] = a_nin;
        this.characterMele[3] = a_sam;
    }

    /**/
    /*
    public GetMeleIcons()

    NAME

        GetMeleIcons - this method gets the given character object's stored mele icon urls in a string array

    SYNOPSIS

        public String[] GetMeleIcons()
            this.characterMeleIcons   --> character object's mele icon urls in a string array


    DESCRIPTION

        GetMeleIcons returns the character's mele icon urls in a string array

    RETURNS

        Returns a string array of the character's mele icon urls

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String[] GetMeleIcons() { return this.characterMeleIcons; }

    /**/
    /*
    public SetMeleIcons()

    NAME

        SetMeleIcons - this method sets the given character object's stored mele icon urls in a string array

    SYNOPSIS

        public void SetMeleIcons(String a_mnk, String a_drg, String a_nin, String a_sam)
            String a_mnk           --> parameter monk icon urls to set
            String a_drg           --> parameter dragoon icon urls to set
            String a_nin            --> parameter ninja icon urls to set
            String a_sam            --> parameter sam icon urls to set
            this.characterMeleIcons[]   --> character object's character mele icon urls in a string array


    DESCRIPTION

        SetMeleIcons sets a characters mele icon urls in a string array

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetMeleIcons(String a_mnk, String a_drg, String a_nin, String a_sam) {
        this.characterMeleIcons[0] = a_mnk;
        this.characterMeleIcons[1] = a_drg;
        this.characterMeleIcons[2] = a_nin;
        this.characterMeleIcons[3] = a_sam;
    }

    /**/
    /*
    public GetRanged()

    NAME

        GetRanged - this method gets the given character object's stored ranged levels in a string array

    SYNOPSIS

        public String[] GetRanged()
            this.characterRanged   --> character object's ranged levels in a string array


    DESCRIPTION

        GetRanged returns the character's ranged levels in a string array

    RETURNS

        Returns a string array of the character's ranged levels

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String[] GetRanged() { return this.characterRanged; }

    /**/
    /*
    public SetRanged()

    NAME

        SetRanged - this method sets the given character object's stored ranged levels in a string array

    SYNOPSIS

        public void SetRanged(String a_brd, String a_mch, String a_blm, String a_smn, String a_rdm, String a_blu)
            String a_brd           --> parameter bard level to set
            String a_mch           --> parameter mch level to set
            String a_blm            --> parameter blm level to set
            String a_smn           --> parameter smn level to set
            String a_rdm           --> parameter rdm level to set
            String a_blu           --> parameter blu level to set
            this.characterRanged[]   --> character object's character ranged levels in a string array


    DESCRIPTION

        SetRanged sets a characters ranged levels in a string array

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetRanged(String a_brd, String a_mch, String a_blm, String a_smn, String a_rdm, String a_blu) {
        this.characterRanged[0] = a_brd;
        this.characterRanged[1] = a_mch;
        this.characterRanged[2] = a_blm;
        this.characterRanged[3] = a_smn;
        this.characterRanged[4] = a_rdm;
        this.characterRanged[5] = a_blu;
    }

    /**/
    /*
    public GetRangedIcons()

    NAME

        GetRangedIcons - this method gets the given character object's stored ranged icon urls in a string array

    SYNOPSIS

        public String[] GetRangedIcons()
            this.characterRangedIcons   --> character object's ranged icon urls in a string array


    DESCRIPTION

        GetRangedIcons returns the character's ranged icon urls in a string array

    RETURNS

        Returns a string array of the character's ranged icon urls

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String[] GetRangedIcons() { return this.characterRangedIcons; }

    /**/
    /*
    public SetRangedIcons()

    NAME

        SetRangedIcons - this method sets the given character object's stored ranged icon urls in a string array

    SYNOPSIS

        public void SetRangedIcons(String a_brd, String a_mch, String a_blm, String a_smn, String a_rdm, String a_blu)
            String a_brd           --> parameter bard icon urls to set
            String a_mch           --> parameter mch icon urls to set
            String a_blm           --> parameter blm icon urls to set
            String a_smn           --> parameter smn icon urls to set
            String a_rdm           --> parameter rdm icon urls to set
            String a_blu           --> parameter blu icon urls to set
            this.characterRanged[] --> character object's character ranged icon urls in a string array


    DESCRIPTION

        SetRangedIcons sets a characters ranged icon urls in a string array

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetRangedIcons(String a_brd, String a_mch, String a_blm, String a_smn, String a_rdm, String a_blu) {
        this.characterRangedIcons[0] = a_brd;
        this.characterRangedIcons[1] = a_mch;
        this.characterRangedIcons[2] = a_blm;
        this.characterRangedIcons[3] = a_smn;
        this.characterRangedIcons[4] = a_rdm;
        this.characterRangedIcons[5] = a_blu;
    }

    /**/
    /*
    public GetAllBattle()

    NAME

        GetAllBattle - this method gets the given character object's stored battle achievements

    SYNOPSIS

        public Achievement[] GetAllBattle()
            this.allBattle   --> character object's character battle achievements


    DESCRIPTION

        GetAllBattle returns the character's battle achievements in an achievement array

    RETURNS

        Returns an achievement array of the character's battle achievements

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public Achievement[] GetAllBattle() {return this.allBattle;}

    /**/
    /*
    public SetAllBattle()

    NAME

        SetAllBattle - this method sets the given character object's stored battle achievements

    SYNOPSIS

        public void SetAllBattle(Achievement[] a_battle)
            Achievement[] a_battle   --> character object's achievement array of battle achievements
            this.allBattle           --> stored character object achievement array of battle achievements


    DESCRIPTION

        SetAllBattle sets the character's battle achievements in an achievement array

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetAllBattle(Achievement[] a_battle){ this.allBattle = a_battle; }

    /**/
    /*
    public GetAllPvP()

    NAME

        GetAllPvP - this method gets the given character object's stored pvp achievements

    SYNOPSIS

        public Achievement[] GetAllPvP()
            this.allPvP   --> character object's character pvp achievements


    DESCRIPTION

        GetAllPvP returns the character's pvp achievements in an achievement array

    RETURNS

        Returns an achievement array of the character's pvp achievements

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public Achievement[] GetAllPvP() { return this.allPvP; }

    /**/
    /*
    public SetAllPvP()

    NAME

        SetAllPvP - this method sets the given character object's stored pvp achievements

    SYNOPSIS

        public void SetAllPvP(Achievement[] a_pvp)
            Achievement[] a_pvp   --> character object's achievement array of pvp achievements
            this.allPvP           --> stored character object achievement array of pvp achievements


    DESCRIPTION

        SetAllPvP sets the character's pvp achievements in an achievement array

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetAllPvP(Achievement[] a_pvp){ this.allPvP = a_pvp; }

    /**/
    /*
    public GetAllCharacter()

    NAME

        GetAllCharacter - this method gets the given character object's stored character achievements

    SYNOPSIS

        public Achievement[] GetAllCharacter()
            this.allCharacter   --> character object's character character achievements


    DESCRIPTION

        GetAllCharacter returns the character's character achievements in an achievement array

    RETURNS

        Returns an achievement array of the character's character achievements

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public Achievement[] GetAllCharacter() { return this.allCharacter; }

    /**/
    /*
    public SetAllCharacter()

    NAME

        SetAllCharacter - this method sets the given character object's stored character achievements

    SYNOPSIS

        public void SetAllCharacter(Achievement[] a_character)
            Achievement[] a_character   --> character object's achievement array of character achievements
            this.allCharacter           --> stored character object achievement array of character achievements


    DESCRIPTION

        SetAllCharacter sets the character's character achievements in an achievement array

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetAllCharacter(Achievement[] a_character) { this.allCharacter = a_character; }

    /**/
    /*
    public GetAllItems()

    NAME

        GetAllItems - this method gets the given character object's stored items achievements

    SYNOPSIS

        public Achievement[] GetAllItems()
            this.allItems   --> character object's character items achievements


    DESCRIPTION

        GetAllItems returns the character's items achievements in an achievement array

    RETURNS

        Returns an achievement array of the character's items achievements

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public Achievement[] GetAllItems() { return this.allItems; }

    /**/
    /*
    public SetAllItems()

    NAME

        SetAllItems - this method sets the given character object's stored items achievements

    SYNOPSIS

        public void SetAllItems(Achievement[] a_items)
            Achievement[] a_items   --> character object's achievement array of items achievements
            this.allItems           --> stored character object achievement array of items achievements


    DESCRIPTION

        SetAllItems sets the character's items achievements in an achievement array

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetAllItems(Achievement[] a_items) { this.allItems = a_items; }

    /**/
    /*
    public GetAllCrafting()

    NAME

        GetAllCrafting - this method gets the given character object's stored crafting achievements

    SYNOPSIS

        public Achievement[] GetAllCrafting()
            this.allCrafting   --> character object's character crafting achievements


    DESCRIPTION

        GetAllCrafting returns the character's crafting achievements in an achievement array

    RETURNS

        Returns an achievement array of the character's crafting achievements

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public Achievement[] GetAllCrafting() { return this.allCrafting; }

    /**/
    /*
    public SetAllCrafting()

    NAME

        SetAllCrafting - this method sets the given character object's stored crafting achievements

    SYNOPSIS

        public void SetAllCrafting(Achievement[] a_crafting)
            Achievement[] a_crafting   --> character object's achievement array of crafting achievements
            this.allCrafting           --> stored character object achievement array of crafting achievements


    DESCRIPTION

        SetAllCrafting sets the character's crafting achievements in an achievement array

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetAllCrafting(Achievement[] a_crafting) { this.allCrafting = a_crafting; }

    /**/
    /*
    public GetAllGathering()

    NAME

        GetAllGathering - this method gets the given character object's stored gathering achievements

    SYNOPSIS

        public Achievement[] GetAllGathering()
            this.allGathering   --> character object's character gathering achievements


    DESCRIPTION

        GetAllGathering returns the character's gathering achievements in an achievement array

    RETURNS

        Returns an achievement array of the character's gathering achievements

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public Achievement[] GetAllGathering() { return this.allGathering; }

    /**/
    /*
    public SetAllGathering()

    NAME

        SetAllGathering - this method sets the given character object's stored gathering achievements

    SYNOPSIS

        public void SetAllGathering(Achievement[] a_gathering)
            Achievement[] a_gathering   --> character object's achievement array of gathering achievements
            this.allGathering           --> stored character object achievement array of gathering achievements


    DESCRIPTION

        SetAllGathering sets the character's gathering achievements in an achievement array

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetAllGathering(Achievement[] a_gathering) { this.allGathering = a_gathering; }

    /**/
    /*
    public GetAllQuests()

    NAME

        GetAllQuests - this method gets the given character object's stored quests achievements

    SYNOPSIS

        public Achievement[] GetAllQuests()
            this.allQuests   --> character object's character quests achievements


    DESCRIPTION

        GetAllQuests returns the character's quests achievements in an achievement array

    RETURNS

        Returns an achievement array of the character's quests achievements

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public Achievement[] GetAllQuests() { return this.allQuests; }

    /**/
    /*
    public SetAllQuests()

    NAME

        SetAllQuests - this method sets the given character object's stored quests achievements

    SYNOPSIS

        public void SetAllQuests(Achievement[] a_quests)
            Achievement[] a_quests   --> character object's achievement array of quests achievements
            this.allQuests           --> stored character object achievement array of quests achievements


    DESCRIPTION

        SetAllQuests sets the character's quests achievements in an achievement array

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetAllQuests(Achievement[] a_quests) { this.allQuests = a_quests; }

    /**/
    /*
    public GetAllExploration()

    NAME

        GetAllExploration - this method gets the given character object's stored exploration achievements

    SYNOPSIS

        public Achievement[] GetAllExploration()
            this.allExploration   --> character object's character exploration achievements


    DESCRIPTION

        GetAllExploration returns the character's exploration achievements in an achievement array

    RETURNS

        Returns an achievement array of the character's exploration achievements

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public Achievement[] GetAllExploration() { return this.allExploration; }


    /**/
    /*
    public SetAllExploration()

    NAME

        SetAllExploration - this method sets the given character object's stored exploration achievements

    SYNOPSIS

        public void SetAllExploration(Achievement[] a_exploration)
            Achievement[] a_exploration   --> character object's achievement array of exploration achievements
            this.allExploration           --> stored character object achievement array of exploration achievements


    DESCRIPTION

        SetAllExploration sets the character's exploration achievements in an achievement array

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetAllExploration(Achievement[] a_exploration) { this.allExploration = a_exploration; }

    /**/
    /*
    public GetAllGC()

    NAME

        GetAllGC - this method gets the given character object's stored gc achievements

    SYNOPSIS

        public Achievement[] GetAllGC()
            this.allGC   --> character object's character gc achievements


    DESCRIPTION

        GetAllGC returns the character's gc achievements in an achievement array

    RETURNS

        Returns an achievement array of the character's gc achievements

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public Achievement[] GetAllGC() { return this.allGC; }

    /**/
    /*
    public SetAllGC()

    NAME

        SetAllGC - this method sets the given character object's stored gc achievements

    SYNOPSIS

        public void SetAllGC(Achievement[] a_gc)
            Achievement[] a_gc   --> character object's achievement array of gc achievements
            this.allGC           --> stored character object achievement array of gc achievements


    DESCRIPTION

        SetAllGC sets the character's gc achievements in an achievement array

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetAllGC(Achievement[] a_gc) { this.allGC = a_gc; }

    /**/
    /*
    public GetCategory()

    NAME

        GetCategory - this method gets the given category name at a given index number

    SYNOPSIS

        public String GetCategory(int a_number)
            this.category[a_number]   --> character object's category name at a_number index


    DESCRIPTION

        GetCategory returns a string of a category for achievements stored in a string array

    RETURNS

        Returns a string of an achievement category name

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String GetCategory(int a_number) { return this.category[a_number]; }


    /**/
    /*
    public Clear()

    NAME

        Clear - this method gets the given category name at a given index number

    SYNOPSIS

        public void Clear()
            this.characterName      --> clears character object name
            this.characterServer    --> clears character object server
            this.characterIcon      --> clears character object icon url
            this.characterTitle     --> clears character object title
            this.characterLink      --> clears character object link url
            this.characterID        --> clears character object ic
            this.characterRace      --> clears character object race
            this.characterGuardian  --> clears character object guardian
            this.characterCity      --> clears character object city
            this.characterGC        --> clears character object grand company
            this.characterFC        --> clears character object free company
            this.characterImage     --> clears character object image url


    DESCRIPTION

        Clear clears out the character object

    RETURNS

        Nothing

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void Clear(){
        this.characterName = "";
        this.characterServer = "";
        this.characterIcon = "";
        this.characterTitle = "";
        this.characterLink = "";
        this.characterID = "";
        this.characterRace = "";
        this.characterGuardian = "";
        this.characterCity = "";
        this.characterGC = "";
        this.characterFC = "";
        this.characterImage = "";
    }

    /**/
    /*
    public toString()

    NAME

        toString - this method is used to print out a string of the characters name and server

    SYNOPSIS

         public String toString()
            characterName   --> character object stored name
            characterServer --> character object stored server



    DESCRIPTION

        toString returns a string of a character objects name and server

    RETURNS

        Returns a string of a character objects name and server

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    @Override
    public String toString(){
        return "Character [characterName=" + characterName + ", characterServer=" + characterServer + "]";
    }

}





