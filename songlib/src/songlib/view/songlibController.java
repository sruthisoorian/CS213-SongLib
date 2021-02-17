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
	
	private ObservableList<Song> obsList;
	
	private Song selectedSong;
	
	public void start () {
		obsList = FXCollections.observableArrayList();
		songLV.setItems(obsList);
		
		songLV.getSelectionModel().selectedIndexProperty().addListener((obsList, oldVal, newVal) -> displaySongs());
		
		try {
			
			File songFile = new File("src/songFile.csv");
			
			if(!songFile.exists()) {
				songFile.createNewFile();
			}
			
			BufferedReader bf = new BufferedReader(new FileReader(songFile));
			
			String line;
			
			while((line = bf.readLine())!= null) {
				String [] parser = line.split(",");
				
				Song s = new Song(parser[0], parser[1], parser[2], parser[3]);
				
				obsList.add(s);
			}
			
			songLV.setItems(obsList);
			
			if(!obsList.isEmpty()) {
				selectedSong = obsList.get(0);
				songLV.requestFocus();
				songLV.getSelectionModel();
				songLV.getFocusModel();
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
	}

}
