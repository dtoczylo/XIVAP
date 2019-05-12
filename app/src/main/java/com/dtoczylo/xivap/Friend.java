/**/
/*

NAME

        Friend Class

PURPOSE
        The Friend model class is responsible for creating Friend objects that can be used
        in a Recycler View to display friend objects by point value. A friend object will
        contain a name, server, point value, and icon.
AUTHOR

        Desiree Toczylowski

DATE

        5/8/2019

*/
/**/
package com.dtoczylo.xivap;

import java.io.Serializable;

public class Friend implements Serializable {

    private String characterName;
    private String characterServer;
    private String characterPoints;
    private String characterIcon;


    /**/
    /*
    public Friend()

    NAME

        Friend - This is the default constructor for the Friend Object Class

    SYNOPSIS

        public Friend()
            this.characterName      --> friend object character name
            this.characterIcon      --> friend object character icon url
            this.characterPoints    --> friend object character point value
            this.characterServer    --> friend object character server

    DESCRIPTION

        This constructor will assign the appropriate data to the friend upon
        parsing HTML.

    RETURNS

        Nothing, it is a constructor

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public Friend(){
        this.characterName = "Empty";
        this.characterIcon = "";
        this.characterPoints = "0";
        this.characterServer = "";
    }

    /**/
    /*
    public String GetCharacterName()

    NAME

        GetCharacterName - returns a character name of a friend object

    SYNOPSIS

        String GetCharacterName()
            characterName      --> The returned character name of a friend object

    DESCRIPTION

        This is a getter function that gets the name of the friend objects character name

    RETURNS

        Returns a string of a character name for a friend object

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String GetCharacterName() {
        return characterName;
    }

    /**/
    /*
    public String SetCharacterName()

    NAME

        SetCharacterName - sets the character name of a friend object

    SYNOPSIS

        public void SetCharacterName(String a_characterName)
            this.characterName      --> the current object's character name value
            a_characterName         --> the character name to be set to the friend object

    DESCRIPTION

        This is a setter function for the name of a friend object. It will accept
        a string (a_characterName) as a parameter, and set an friend object's character name (this.characterName)

    RETURNS

        Nothing, this is a setter function.

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetCharacterName(String a_characterName) {
        this.characterName = a_characterName;
    }

    /**/
    /*
    public String GetCharacterServer()

    NAME

        GetCharacterServer - returns a character server of a friend object

    SYNOPSIS

        String GetCharacterServer()
            characterName      --> The returned character server of a friend object

    DESCRIPTION

        This is a getter function that gets the server of the friend objects character server

    RETURNS

        Returns a string of a character server for a friend object

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String GetCharacterServer() {
        return characterServer;
    }

    /**/
    /*
    public String SetCharacterServer()

    NAME

        SetCharacterServer - sets the character server of a friend object

    SYNOPSIS

        public void SetCharacterServer(String a_characterServer)
            this.characterServer      --> the current object's character server value
            a_characterServer         --> the character server to be set to the friend object

    DESCRIPTION

        This is a setter function for the server of a friend object. It will accept
        a string (a_characterServer) as a parameter, and set an friend object's character server (this.characterServer)

    RETURNS

        Nothing, this is a setter function.

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetCharacterServer(String a_characterServer) {
        this.characterServer = a_characterServer;
    }

    /**/
    /*
    public String GetCharacterPoints()

    NAME

        GetCharacterPoints - returns a character point value of a friend object in a string

    SYNOPSIS

        String GetCharacterPoints()
            characterPoints      --> The returned character point value of a friend object in a string

    DESCRIPTION

        This is a getter function that gets the point value of the friend objects character point value
        in a string

    RETURNS

        Returns a string of a character point value for a friend object

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String GetCharacterPoints() {
        return characterPoints;
    }

    /**/
    /*
    public String SetCharacterPoints()

    NAME

        SetCharacterPoints - sets the character point value of a friend object as a string

    SYNOPSIS

        public void SetCharacterPoints(String a_characterPoints)
            this.characterPoints      --> the current object's character point value value
            a_characterPoints         --> the character point value to be set to the friend object

    DESCRIPTION

        This is a setter function for the point value of a friend object. It will accept
        a string (a_characterPoints) as a parameter, and set an friend object's character point value (this.characterPoints)

    RETURNS

        Nothing, this is a setter function.

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetCharacterPoints(String a_characterPoints) {
        this.characterPoints = a_characterPoints;
    }

    /**/
    /*
    public String GetCharacterIcon()

    NAME

        GetCharacterIcon - returns a character icon url of a friend object

    SYNOPSIS

        String GetCharacterIcon()
            characterIcon      --> The returned character icon url of a friend object

    DESCRIPTION

        This is a getter function that gets the icon url of the friend objects character icon url

    RETURNS

        Returns a string of a character icon url for a friend object

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String GetCharacterIcon() {
        return characterIcon;
    }

    /**/
    /*
    public String SetCharacterIcon()

    NAME

        SetCharacterIcon - sets the character icon url of a friend object

    SYNOPSIS

        public void SetCharacterIcon(String a_characterIcon)
            this.characterIcon      --> the current object's character icon url value
            a_characterIcon         --> the character icon url to be set to the friend object

    DESCRIPTION

        This is a setter function for the icon url of a friend object. It will accept
        a string (a_characterIcon) as a parameter, and set an friend object's character icon url (this.characterIcon)

    RETURNS

        Nothing, this is a setter function.

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetCharacterIcon(String a_characterIcon) {
        this.characterIcon = a_characterIcon;
    }
}
