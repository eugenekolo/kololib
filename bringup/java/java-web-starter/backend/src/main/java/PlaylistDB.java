/********************************************************************************
* Al Gore Rhythms 
* Playlist Application Project
*
* This file implemenets the Playlist database.
*
* @author: Eugene Kolo
* @email: eugene@kolobyte.com
* @version: 1.0
* @since: November 25, 2015
********************************************************************************/

package starter;

import java.util.PriorityQueue; // This is default implemented as a MinPriorityQueue

public class PlaylistDB {
    public PriorityQueue<Playlist> _playlistDB;

    public PlaylistDB() {
        _playlistDB = new PriorityQueue<Playlist>();
    }

    public void addPlaylist(Playlist playlist) {
        _playlistDB.add(playlist);
    }
}