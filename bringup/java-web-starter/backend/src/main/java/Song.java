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

public class Song implements Comparable {
    public String _title;
    public String _author;
    public Integer _popularity;
    public Playlist _bestPlaylist;

    public Song(String title) {
        _title = title;
        _popularity = 0;
        _author = null;
    }

    public Song(String title, String author) {
        _title = title;
        _popularity = 0;
        _author = author;
    }

    public Song(String title, Integer popularity) {
        _title = title;
        _popularity = _popularity;
        _author = null;
    }

    public Song(String title, String author, Integer popularity) {
        _title = title;
        _popularity = _popularity;
        _author = author;
    }


    public String getTitle() {
        return _title;
    }

    public void setTitle(String title) {
        _title = title;
    }


    public String getAuthor() {
        return _author;
    }

    public void setAuthor(String author) {
        _author = author;
    }


    public Integer getPopularity() {
        return _popularity;
    }

    public void setPopularity(Integer popularity) {
        _popularity = popularity;
    }

    /**
    * Let song be comparable by its popularity.
    */
    @Override
    public int compareTo(Object other) {
        if (_popularity < ((Song)other).getPopularity()) {
            return -1;
        }
        if (_popularity > ((Song)other).getPopularity()) {
            return 1;
        }
        return 0;
    }   
}