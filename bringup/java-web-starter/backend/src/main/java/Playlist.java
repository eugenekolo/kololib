/********************************************************************************
* Kolo Java Web Starter
*
* Sample data structure
*
* @author: Eugene Kolo
* @email: eugene@kolobyte.com
* @version: 1.0
* @since: Dec 12, 2015
********************************************************************************/

package starter;

import java.util.ArrayList;

public class Playlist implements Comparable {
    public Integer _popularity;
    public ArrayList<Song> _songList;

    public Playlist(Integer popularity, ArrayList<Song> songList) {
        _popularity = popularity;
        _songList = songList;
    }


    public ArrayList<Song> getSongList() {
        return _songList;
    }

    public void setSongList(ArrayList<Song> songList) {
        _songList = songList;
    }


    public Integer getPopularity() {
        return _popularity;
    }

    public void setPopularity(Integer popularity) {
        _popularity = popularity;
    }
    

    /**
    * Let Playlist be comparable by its popularity.
    */
    @Override
    public int compareTo(Object other) {
        if (_popularity < ((Playlist)other).getPopularity()) {
            return -1;
        }
        if (_popularity > ((Playlist)other).getPopularity()) {
            return 1;
        }
        return 0;
    }   
}