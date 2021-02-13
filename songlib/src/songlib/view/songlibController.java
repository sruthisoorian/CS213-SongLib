package songlib.view;

//import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
	@FXML ListView<String> songLV;
	
	private ObservableList<String> obsList;
	
	public void start () {
		obsList = FXCollections.observableArrayList("nasty - Ariana Grande",  "my future - Billie Eilish");
		songLV.setItems(obsList);
	}

}
