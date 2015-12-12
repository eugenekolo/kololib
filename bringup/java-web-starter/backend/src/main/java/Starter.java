/********************************************************************************
* Kolo Java Web Starter
*
* Main with the Spark web routes
*
* @author: Eugene Kolo
* @email: eugene@kolobyte.com
* @version: 1.0
* @since: Dec 12, 2015
********************************************************************************/

package starter;

import java.util.HashMap;
import java.util.ArrayList;

import static spark.Spark.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Starter {

    public static PlaylistDB mPlaylistDB;

    public static void main(String[] args) {

        /****************************
        * Initialize data structures 
        ****************************/

        /****************************
        * Spark Configuration
        ****************************/
        setPort(5000); // Run on port 5000
        setIpAddress("127.0.0.1"); // Run on localhost
        externalStaticFileLocation("../frontend"); // Serve HTML from the frontend directory

        System.out.println("[*] Started backend webserver");


        /****************************
        * Route programming
        ****************************/

        post("/api/addPlaylist", (req, res) -> {
            HashMap<String, String> json = jsonToMap(req.body());
            String popularity = json.get("popularity");
            String songListString = json.get("songList");
            String[] songListStringList = songListString.split(" ");

            /* Create songs for each provided song id */
            ArrayList<Song> songList = new ArrayList<Song>();
            for (String id : songListStringList) {
                Song song = new Song(id);
                songList.add(song);
            }

            Playlist playlist = new Playlist(Integer.parseInt(popularity), songList);
            mPlaylistDB.addPlaylist(playlist);

            res.status(200);
            return "Successfully added playlist";
        });

        get("/api/getPlaylistDB", (req, res) -> {
            return "Implement me";
        });


    } // END MAIN METHOD


    /****************************
    /* Helper functions         
    ****************************/
    /**
    * Converts an POJO to a JSON string 
    */
    public static String objToJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

   /** 
    * Converts a JSON string to a {String: String} Map.
    */
    public static HashMap<String, String> jsonToMap(String json) {
        HashMap<String,String> map = new Gson().fromJson(json, 
            new TypeToken<HashMap<String, String>>(){}.getType());
        return map;
    }

    /** 
    * Converts a JSON string to a {String: {String: String}} Map
    */
    public static HashMap<String, HashMap<String,String>> jsonToMapMap(String json) {
        HashMap<String, HashMap<String,String>> map = new Gson().fromJson(json, 
            new TypeToken<HashMap<String, HashMap<String,String>>>(){}.getType());
        return map;
    }

} // END ALGORE CLASS