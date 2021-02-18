package songlib.view;

//import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import songlib.app.Song;
import java.io.*;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class songlibController {
	@FXML Button addB;
	@FXML Button editB;
	@FXML TextField songTF;
	@FXML TextField albumTF;
	@FXML TextField artistTF;
	@FXML TextField yearTF;
	@FXML Button deleteB;
	@FXML Button cancelB;
	@FXML Button confirmB;
	@FXML Label songL;
	@FXML Label artistL;
	@FXML Label albumL;
	@FXML Label yearL;
	@FXML ListView<Song> songLV;
	
	private int actselect = 0; //action selected: 0 - none, 1 - add, 2 - edit, 3 - delete
	
	private ObservableList<Song> obsList;  //Arraylist of songs
	
	private Song selectedSong; //selected song
	
	Song addedSong; //addedsong
	
	public void start () {
		//confirm and cancel buttons are disabled, so are all textfields
		cancelB.setDisable(true);
		confirmB.setDisable(true);
		songTF.setDisable(true);
		artistTF.setDisable(true);
		albumTF.setDisable(true);
		yearTF.setDisable(true);
		
		//Initialize ArrayList and Listview
		obsList = FXCollections.observableArrayList();
		songLV.setItems(obsList);
		
		//get the listeners up and running
		songLV.getSelectionModel().selectedIndexProperty().addListener((obsList, oldVal, newVal) -> displaySongs());
		
		//create buffered reader object
		BufferedReader bf = null;
		
		try {
			
			File songFile = new File("src/songFile.csv");
			
			if(!songFile.exists()) {
				songFile.createNewFile();
			}
			
			bf = new BufferedReader(new FileReader(songFile));
			String line;
			
			while((line = bf.readLine())!= null) {
				String [] parser = line.split("@");
				
				Song s = new Song(parser[0], parser[1], parser[2], parser[3]);
				System.out.println(parser[0] + " " + parser[1] + " " +parser[2] + " " +parser[3]);
				
				obsList.add(s);
			}
			
			songLV.setItems(obsList);
			
			if(!obsList.isEmpty()) {
				selectedSong = obsList.get(0);
				songLV.requestFocus();
				songLV.getSelectionModel().select(0);
				songLV.getFocusModel().focus(0);
			}
			
			bf.close();
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	} //end of start method
	
	public void displaySongs() {
		Song s = songLV.getSelectionModel().getSelectedItem();
		
		selectedSong = s;
		
		if(selectedSong == null) {
			return;
		}
		else {
			songL.setText(selectedSong.getTitle());
			artistL.setText(selectedSong.getArtist());
			albumL.setText(selectedSong.getAlbum());
			yearL.setText(selectedSong.getYear());
		}
	}// end of displaySongs method
	
	@FXML
	public void addClick(ActionEvent e) {
		actselect = 1;
		
		cancelB.setDisable(false);
		confirmB.setDisable(false);
		addB.setDisable(true);
		editB.setDisable(true);
		deleteB.setDisable(true);
		songTF.setDisable(false);
		artistTF.setDisable(false);
		albumTF.setDisable(false);
		yearTF.setDisable(false);
		songTF.setText("");
		artistTF.setText("");
		albumTF.setText("");
		yearTF.setText("");
		
		
	}//end of addCLick
	
	@FXML
	public void editClick(ActionEvent e) {
		actselect = 2;
		
		cancelB.setDisable(false);
		confirmB.setDisable(false);
		addB.setDisable(true);
		editB.setDisable(true);
		deleteB.setDisable(true);
		songTF.setDisable(false);
		artistTF.setDisable(false);
		albumTF.setDisable(false);
		yearTF.setDisable(false);
		
	}//end of editClick
	
	@FXML
	public void deleteClick(ActionEvent e) {
		actselect = 3;
		
		cancelB.setDisable(false);
		confirmB.setDisable(false);
		addB.setDisable(true);
		editB.setDisable(true);
		deleteB.setDisable(true);
		songTF.setDisable(false);
		artistTF.setDisable(false);
		albumTF.setDisable(false);
		yearTF.setDisable(false);
		
	}//end of delete Click
	
	@FXML
	public void cancelClick(ActionEvent e) {
		reset();
		
	}//end of cancelClick
	
	@FXML
	public void confirmClick(ActionEvent e) {
		if (actselect == 1) {
			addEvent();
		}
		
		reset();
	}
	
	public void reset() {
		actselect = 0;
		
		songTF.setText("");
		artistTF.setText("");
		albumTF.setText("");
		yearTF.setText("");
		cancelB.setDisable(true);
		confirmB.setDisable(true);
		addB.setDisable(false);
		editB.setDisable(false);
		deleteB.setDisable(false);
		songTF.setDisable(true);
		artistTF.setDisable(true);
		albumTF.setDisable(true);
		yearTF.setDisable(true);
		
	}
	
	
	public void addEvent () {
		String songtext = songTF.getText();
		String artisttext = artistTF.getText();
		String albumtext = albumTF.getText();
		String yeartext = yearTF.getText();
		
		
		if(songTF.getText().isEmpty() || artistTF.getText().isEmpty()) {
			System.out.println("Alert: Need Song & Artist");
			return;
		}
		if(albumTF.getText().isEmpty()) {
			albumtext = "[Album N/A]";
		}
		if(yearTF.getText().isEmpty()) {
			yeartext = "[Year N/A]";
		}
		
		Song addedsong = new Song(songtext, artisttext, albumtext, yeartext);
		
		addSong(addedsong);	
	}//end of addEvent method
	
	public int addSong(Song addSong) {
		String title = addSong.getTitle();
		String artist = addSong.getArtist();
		int idx = findSongIndex(obsList, addSong);
		
		if(idx == -1) {
			System.out.println("Alert");
			return -1;
		}
		else {
			//put in confirm or cancel here
			obsList.add(idx, addSong);
			writeToFile();
		}
		
		return 0;
	}//end of addSong method
	
	public int findSongIndex(ObservableList<Song> obl, Song s) {
		int i;
		for(i = 0; i < obl.size(); i++) {
			if(obl.get(i).compareTo(s) == 0) {
				return -1;
			}
			else if(obl.get(i).compareTo(s) > 0) {
				return i;
			}
			else {
				continue;
			}
		}
		return i;
	}//end of findSongIndex
	
	public void writeToFile() {
		BufferedWriter bw = null;
		try {
			File f = new File("src/songFile.csv");
			
			if(!f.exists()) {
				f.createNewFile();
			}
			FileWriter fw = new FileWriter(f);
			bw = new BufferedWriter(fw);
			
			for(Song s: obsList) {
				bw.write(s.getTitle() + "@" + s.getArtist() + "@" + s.getAlbum() + "@" + s.getYear() + "\n" );
			}
			
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally {
			try {
				if(bw != null) {
					bw.close();
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}//end of writeToFile method
	
	

}//end of Controller class
