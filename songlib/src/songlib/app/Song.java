package songlib.app;

public class Song implements Comparable<Song> {
	
	private String title;
	private String artist;
	private String album;
	private String year;
	
	
	//constructors
	public Song(String title, String artist, String album, String year) {
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}
	
	public Song(String title, String artist) {
		this.title = title;
		this.artist = artist;
	}
	
	//getters
	public String getTitle() {
		return this.title;
	}
	
	public String getArtist() {
		return this.artist;
	}
	
	public String getAlbum() {
		return this.album;
	}
	
	public String getYear() {
		return this.year;
	}
	
	//setters
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	public void setAlbum(String album) {
		this.album = album;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	//Overridden toString: returns string version of SO
	@Override
	public String toString() {
		return "Song: " + title + "     Artist: " + artist;
	}
	
	//Overridden compareTo: Checks title and then artist for duplicates.
	@Override
	public int compareTo(Song song) {
		if(this.title.toLowerCase().compareTo(song.title.toLowerCase()) == 0) { //if songs match
			return this.artist.toLowerCase().compareTo(song.artist.toLowerCase()); //check artist and return output
		}
		else { //if songs don't match
			return this.title.toLowerCase().compareTo(song.title.toLowerCase()); //check songs and return output
		}
	}
	
	

}
