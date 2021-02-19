//Bria Whitt
//Sruthi Soorian
package songlib.view;

//import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
	
	Song addedSong; //added song
	
	public void start () {
		//confirm and cancel buttons are disabled, so are all text fields
		cancelB.setDisable(true);
		confirmB.setDisable(true);
		songTF.setDisable(true);
		artistTF.setDisable(true);
		albumTF.setDisable(true);
		yearTF.setDisable(true);
		
		//Initialize ArrayList and ListView
		obsList = FXCollections.observableArrayList();
		songLV.setItems(obsList);
		
		//get the listeners working for all songs in ListView
		songLV.getSelectionModel().selectedIndexProperty().addListener((obsList, oldVal, newVal) -> displayCurrentSong());
		
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
			
			if(obsList.isEmpty() == false) {
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
	
	public void displayCurrentSong() {
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
	}// end of displayCurrentSong method
	
	@FXML
	public void addClick(ActionEvent e) {
		actselect = 1;
		
		cancelB.setDisable(false);
		confirmB.setDisable(false);
		confirmB.setText("Confirm Add");
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
		confirmB.setText("Confirm Edit");
		addB.setDisable(true);
		editB.setDisable(true);
		deleteB.setDisable(true);
		songTF.setDisable(false);
		artistTF.setDisable(false);
		albumTF.setDisable(false);
		yearTF.setDisable(false);
		songLV.setDisable(true);
		//select a song
		Song toEdit = songLV.getSelectionModel().getSelectedItem();
		//auto input info into text field
		songTF.setText(toEdit.getTitle());
		artistTF.setText(toEdit.getArtist());
		if(toEdit.getAlbum().compareTo("[Album N/A]")==0) {
			albumTF.setText("");
		}else {
			albumTF.setText(toEdit.getAlbum());
		}
		if(toEdit.getYear().compareTo("[Year N/A]")==0) {
			yearTF.setText("");
		}else {
			yearTF.setText(toEdit.getYear());
		}
			
	}//end of editClick
	
	@FXML
	public void deleteClick(ActionEvent e) {
		actselect = 3;
		
		cancelB.setDisable(false);
		confirmB.setDisable(false);
		confirmB.setText("Confirm Delete");
		addB.setDisable(true);
		editB.setDisable(true);
		deleteB.setDisable(true);
		songTF.setDisable(false);
		artistTF.setDisable(false);
		albumTF.setDisable(false);
		yearTF.setDisable(false);
		songTF.setEditable(false);
		artistTF.setEditable(false);
		albumTF.setEditable(false);
		yearTF.setEditable(false);
		
		songLV.setDisable(true);
		//select a song
		Song toDelete = songLV.getSelectionModel().getSelectedItem();
		//auto input info into text field
		songTF.setText(toDelete.getTitle());
		artistTF.setText(toDelete.getArtist());
		if(toDelete.getAlbum().compareTo("[Album N/A]")==0) {
			albumTF.setText("");
		}else {
			albumTF.setText(toDelete.getAlbum());
		}
		if(toDelete.getYear().compareTo("[Year N/A]")==0) {
			yearTF.setText("");
		}else {
			yearTF.setText(toDelete.getYear());
		}
	}
	
	@FXML
	public void cancelClick(ActionEvent e) {
		reset();
		
	}//end of cancelClick
	
	@FXML
	public void confirmClick(ActionEvent e) {
		if (actselect == 1) {
			addEvent();
		}else if(actselect == 2) {
			editEvent();
		}else if(actselect == 3) {
			deleteEvent();
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
		confirmB.setText("Confirm");
		addB.setDisable(false);
		editB.setDisable(false);
		deleteB.setDisable(false);
		songTF.setDisable(true);
		artistTF.setDisable(true);
		albumTF.setDisable(true);
		yearTF.setDisable(true);
		songLV.setDisable(false);
		songTF.setEditable(true);
		artistTF.setEditable(true);
		albumTF.setEditable(true);
		yearTF.setEditable(true);
	}
	
	public void deleteEvent () {
		//delete selected song
		Song toDelete = songLV.getSelectionModel().getSelectedItem();
		int idx = delFindSongIndex(obsList, toDelete);
		obsList.remove(toDelete);
		//if there is a next song, display that
		if(obsList.size() >= idx+1) {
			selectedSong = obsList.get(idx);
			songLV.requestFocus();
			songLV.getSelectionModel().select(idx);
			songLV.getFocusModel().focus(idx);
			displayCurrentSong();
		}else if (obsList.size() < idx+1 && obsList.size() > 0){//if there is no next, display previous
			selectedSong = obsList.get(idx-1);
			songLV.requestFocus();
			songLV.getSelectionModel().select(idx-1);
			songLV.getFocusModel().focus(idx-1);
			displayCurrentSong();
		}else if (obsList.size() == 0) {
			songL.setText("[Song N/A]");
			artistL.setText("[Artist N/A]");
			albumL.setText("[Album N/A]");
			yearL.setText("[Year N/A]");
		}
		writeFile();
	}
	
	public void addEvent () {
		String songtext = songTF.getText();
		String artisttext = artistTF.getText();
		String albumtext = albumTF.getText();
		String yeartext = yearTF.getText();
		
		
		if(songTF.getText().isEmpty() || artistTF.getText().isEmpty()) {
			System.out.println("Alert: Need Song & Artist");  //Create Alert 1
			makeAlert(1);
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
		int idx = addFindSongIndex(obsList, addSong);
		
		if(idx == -1) {
			System.out.println("Alert"); //Create Alert 2
			makeAlert(2);
			return -1;
		}
		else {
			obsList.add(idx, addSong);
			writeFile();
			selectedSong = obsList.get(idx);
			songLV.requestFocus();
			songLV.getSelectionModel().select(idx);
			songLV.getFocusModel().focus(idx);
		}
		
		return 0;
	}//end of addSong method
	
	public int addFindSongIndex(ObservableList<Song> obl, Song s) {
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
	}//end of addFindSongIndex
	
	public int delFindSongIndex(ObservableList<Song> obl, Song s) {
		int i;
		for(i = 0; i < obl.size(); i++) {
			if(obl.get(i).compareTo(s) == 0) {
				return i;
			}
		}
		return i;
	}//end of delFindSongIndex
	
	//after they hit confirm
		public void editEvent () {
			Song toEdit = songLV.getSelectionModel().getSelectedItem();
			//user edits song
			//save info in a temp
			Song temp = new Song(songTF.getText(), artistTF.getText(), albumTF.getText(), yearTF.getText());
			if(temp.getTitle().isEmpty() || temp.getArtist().isEmpty()) {
				makeAlert(4);
				return;
			}
			if(temp.getAlbum().isEmpty()) {
				temp.setAlbum("[Album N/A]");
			}
			if(temp.getYear().isEmpty()) {
				temp.setYear("[Year N/A]");
			}
			//make sure it does not conflict
			//if title and artist info in text field exists in observable list, do not allow
			int check = editFindSongIndex(obsList, temp);
			if(check == -1) {
				System.out.println("Error: Song already exists!"); //Create Alert 3
				makeAlert(3);
				return;
			}
			//delete song from list
			obsList.remove(toEdit);
			//add back the song w new info
			addSong(temp);
		}//end of editEvent method
	
	public int editFindSongIndex(ObservableList<Song> obl, Song s) {
		int i;
		for(i = 0; i < obl.size(); i++) {
			if(obl.get(i).compareTo(s) == 0 //skips comparing selected song for edit
					&& obl.get(i).compareTo(songLV.getSelectionModel().getSelectedItem())!=0) {
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
	}//end
	
	public void writeFile() {
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
		
	}//end of writeFile method
	
	public void makeAlert(int alertType) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning Message");
		String alertHeaderMessage = "";
		String alertContentMessage = "";
		
		if(alertType == 1) {
			alertHeaderMessage = "Unable to Add Song";
			alertContentMessage = "You must provide a title and artist to add a song.";
		}
		else if(alertType == 2) {
			alertHeaderMessage = "Duplicate Song";
			alertContentMessage = "This song already exists in your song library.";
		}
		else if(alertType == 3) {
			alertHeaderMessage = "Duplicate Song";
			alertContentMessage = "Another song already exists with this title and artist.";
		}
		else if(alertType == 4) {
			alertHeaderMessage = "Unable to Edit Song";
			alertContentMessage = "Songs must have a title and artist";
		}
		
		alert.setHeaderText(alertHeaderMessage);
		alert.setContentText(alertContentMessage);
		alert.showAndWait();
		return;
		
	}//end of makeAlert method

}//end of Controller class
