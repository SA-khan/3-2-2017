package com.example.saad.jspart3;

/**
 * Created by AKiniyalocts on 2/23/15.
 */
public class Constants {
    /*
      Logging flag
     */
    public static final boolean LOGGING = false;

    /*
      Your imgur client id. You need this to upload to imgur.

      More here: https://api.imgur.com/
     */
    public static final String MY_IMGUR_CLIENT_ID = "7b83ad4a8bf0d35";
    public static final String MY_IMGUR_CLIENT_SECRET = "bf6988536dbf0c19b222c1193260e863e7d41f4f";

    /*
      Redirect URL for android.
     */
    public static final String MY_IMGUR_REDIRECT_URL = "";

    /*
      Client Auth
     */
    public static String getClientAuth() {
        return "Client-ID " + MY_IMGUR_CLIENT_ID;
    }

}
