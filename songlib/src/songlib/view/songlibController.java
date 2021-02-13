package songlib.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

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
	@FXML ListView<Integer> songLV;

}
