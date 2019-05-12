/**/
/*

NAME

        Achievement Class

PURPOSE
        The Achievement model class is responsible for transforming parsed HTML into an Achievement object.
        When parsing an HTML page that has achievements, this will take data, such as Title, Point Value,
        Icon, and the Obtained status of that point in relation to a Character. It gets stored in the
        Character Object as an array of Achievement Objects depending on Category.

AUTHOR

        Desiree Toczylowski

DATE

        5/8/2019

*/
/**/

package com.dtoczylo.xivap;
import java.io.Serializable;


public class Achievement implements Serializable {
    private String title;
    private String point;
    private String description;
    private String icon;
    private String obtained;

    /**/
    /*
    public Achievement()

    NAME

        Achievement - This is the default constructor for the Achievements Class

    SYNOPSIS

        public Achievement()
            this.title          --> the title of the achievement
            this.point          --> the point value of the achievement
            this.description    --> the description of the achievement
            this.icon           --> the URL of the icon associated with the achievement
            this.obtained       --> the status of the achievement, Yes (obtained) or No (not obtained)

    DESCRIPTION

        This constructor will assign the appropriate data to the achievement upon
        parsing HTML.

    RETURNS

        Nothing, it is a constructor

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public Achievement(){
        this.title = "";
        this.point = "0";
        this.description = "";
        this.icon = "";
        this.obtained = "";
    }

    /**/
    /*
    public String GetTitle()

    NAME

        GetTitle - returns a title for an achievement object

    SYNOPSIS

        String GetTitle()
            this.title      --> The returned title of an achievement

    DESCRIPTION

        This is a getter for the title of an achievement object. GetTitle() will
        return the given achievement object's title.

    RETURNS

        Returns a string of the title of an achievement object

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String GetTitle(){return this.title;}

    /**/
    /*
    public String SetTitle()

    NAME

        SetTitle - sets the title value for an achievement object

    SYNOPSIS

        String SetTitle(String a_title)
            this.title      --> the current object's title value
            a_title         --> the title to be set to the achievement object

    DESCRIPTION

        This is a setter function for the title of an achievement object. It will accept
        a string (a_title) as a parameter, and set an achievement object's title (this.title)

    RETURNS

        Nothing, this is a setter function.

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetTitle(String a_title){
        this.title = a_title;
    }

    /**/
    /*
    public String GetPoint()

    NAME

        GetPoint - returns a point value for an achievement object

    SYNOPSIS

        String GetPoint()
            this.point      --> The returned point value of an achievement

    DESCRIPTION

        This is a getter for the point value of an achievement object. GetPoint() will
        return the given achievement object's point value. This value will be either
        5, 10 or 20. The storage of the value as a string makes sense because the point
        value never gets manipulated, only displayed for views. The string data type
        allows for easy output.

    RETURNS

        Returns a point value for an achievement object

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String GetPoint(){return this.point;}

    /**/
    /*
    public String SetPoint()

    NAME

        SetPoint - sets a point value for a given achievement object

    SYNOPSIS

        String SetPoint(String a_point)
            this.point      --> the point value of the given achievement object
            a_point         --> the parameter point value to be stored for an achievement as string

    DESCRIPTION

        This is a setter for a point value for a given achievement object. The string format allows
        for easy display for different Views. This number value does not get manipulated, it is simply
        stating the point value of an achievement to be processed in a View.

    RETURNS

        Nothing, this is a setter function for an achievements point value

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetPoint(String a_point){
        this.point = a_point;
    }

    /**/
    /*
    public String GetDescription()

    NAME

        GetDescription - returns a description for a given achievement object

    SYNOPSIS

        String GetDescription()
            this.description      --> the description of an achievement object

    DESCRIPTION

        This is a getter function for an achievement object's description. It will
        return the respective achievement object's description.

    RETURNS

        Returns a string of the description for the given achievement object.

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String GetDescription(){return this.description;}

    /**/
    /*
    public String SetDescription()

    NAME

        SetDescription - sets a description for an achievement object

    SYNOPSIS

        String SetDescription(String a_description)
            this.description    --> the description of an achievement object
            a_description       --> a description to be set for an achievement object

    DESCRIPTION

        This is a setter function for an achievement object. This sets a description
        for the respective achievement object.

    RETURNS

        Nothing, this is a setter function for the description of an achievement object.

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetDescription(String a_description){
        this.description = a_description;
    }

    /**/
    /*
    public String GetIcon()

    NAME

        GetIcon - returns a string of an icon url

    SYNOPSIS

        String GetIcon()
            this.icon      --> the returned icon url string of an achievement object

    DESCRIPTION

        This is a getter for the icon of an achievement object. GetIcon() will
        return the given achievement object's icon url string

    RETURNS

        Returns a string of the icon url of an achievement object

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String getIcon(){return this.icon;}

    /**/
    /*
    public String SetIcon()

    NAME

        SetIcon - sets an icon for an achievement object

    SYNOPSIS

        String SetIcon(String a_icon)
            this.icon      --> the achievement object's icon
            a_icon         --> an icon to be set for a given achievement object

    DESCRIPTION

        This is a setter function for the icon of an achievement object. This function
        will store the icon url of a given achievement object and store it in the achievement
        objects icon variable

    RETURNS

        Nothing, this is a setter function for an achievement object's icon

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetIcon(String a_icon){
        this.icon = a_icon;
    }

    /**/
    /*
    public String GetObtained()

    NAME

        GetObtained - returns a obtained status for a given achievement object

    SYNOPSIS

        String GetObtained()
            this.obtained      --> the returned obtained status of a given achievement object

    DESCRIPTION

        This is a getter function for an achievement object's obtained status. The status will
        be either "Yes" for obtained, or "No" for unattained

    RETURNS

        Returns a string of the obtained status of an achievement object

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public String GetObtained(){return this.obtained;}

    /**/
    /*
    public String SetObtained()

    NAME

        SetObtained - returns the status, Yes or No of an achievement objects status

    SYNOPSIS

        String SetObtained(String a_obtained)
            this.obtained      --> the achievement object's obtained status
            a_obtained         --> the obtained status to be set for an achievement object

    DESCRIPTION

        This is a setter function for an achievement objects obtained status. It will set an achievement's
        status to Yes or No depending if that achievement object has been obtained or unattained relative
        to the Character being parsed.

    RETURNS

        Nothing, this is a setter function for an achievement's status.

    AUTHOR

        Desiree Toczylowski

    DATE

        5/8/2019

    */
    /**/
    public void SetObtained(String a_obtained){
        this.obtained = a_obtained;
    }

}