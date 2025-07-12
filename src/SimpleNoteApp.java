


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javax.swing.*;

public class SimpleNoteApp extends Application {
    private ListView<String> noteList;
    private TextArea noteEditor;
    private TextField titleField;



    @Override
    public void start(Stage stage) throws Exception {

        // create the main layout
        BorderPane root = new BorderPane();

        // create menu bar
        MenuBar menuBar = createMenubar();
        root.setTop(menuBar);

        // create left panel note list
        VBox leftPanel = createLeftPanel();
        root.setLeft(leftPanel);

        // create right panel note editors
        VBox rightPanel = createRightPanel();
        root.setCenter(rightPanel);

        // create the scene
        Scene scene = new Scene(root, 800,800);

        // set up the stage
        stage.setTitle("Note app");
        stage.setScene(scene);
        stage.show();


    }

    private MenuBar createMenubar(){
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem newNote = new MenuItem("New Note");
        MenuItem saveNote = new MenuItem("Save Note");
        MenuItem deleteNote = new MenuItem("Delete Note");


        newNote.setOnAction(e-> createNewNote());
        saveNote.setOnAction(e-> saveCurrentNote());
        deleteNote.setOnAction(e-> deleteCurrentNote());


        fileMenu.getItems().addAll(newNote, saveNote, deleteNote);
        menuBar.getMenus().add(fileMenu);
        return menuBar;
    }
    private VBox createLeftPanel(){
        VBox leftPanel = new VBox(10);
        leftPanel.setPadding(new Insets(20));
        leftPanel.setPrefWidth(250);

        Label listLabel = new Label("Notes");
        listLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        noteList = new ListView<>();
        noteList.getItems().addAll("My first Note", "Shopping list", "Meeting Notes");

        //  note selection
        noteList.getSelectionModel().selectedItemProperty().addListener(
                ((observableValue, oldValue, newValue) ->{

                    if (newValue!= null){
                        loadNoteContent(newValue);
                    }
                }
                        )
        );





        leftPanel.getChildren().addAll(listLabel, noteList);



        return leftPanel;
    }
    private VBox createRightPanel(){
        VBox rightPanel = new VBox(10);
        rightPanel.setPadding(new Insets(40));

        Label titleLabel = new Label("Note Title");
        titleField = new TextField();
        titleField.setPromptText("Enter note Title ");

        Label contentLabel = new Label("Note Content");
        noteEditor = new TextArea();
        noteEditor.setPromptText("write your note here ");
        noteEditor.setWrapText(true);

        HBox buttonPanel = new HBox(10);
        Button saveButton = new Button("Save");
        saveButton.setPrefWidth(75);
        Button clearButton = new Button("Clear");
        clearButton.setPrefWidth(75);

//        set action on button
            saveButton.setOnAction(e-> saveCurrentNote());
            clearButton.setOnAction(e-> clearEditor());
        buttonPanel.getChildren().addAll(saveButton, clearButton);

        rightPanel.getChildren().addAll(
                titleLabel, titleField,
                contentLabel, noteEditor,
                buttonPanel
        );
        return rightPanel;
    }

    public void createNewNote(){
        titleField.clear();
        noteEditor.clear();
        noteList.getSelectionModel().clearSelection();
        titleField.requestFocus();
    }
    public void saveCurrentNote(){
        String title = titleField.getText().trim();
        String content = noteEditor.getText();

        if (title.isEmpty()){
//            show alert
            showAlert("Error","Please enter a note title ");
            return;
        }
        if (!noteList.getItems().contains(title)){
            noteList.getItems().add(title);

        }

        // TODO save in the database

        showAlert("Success", "Note saved Successfully! ");
    }
    public void deleteCurrentNote(){
        String selected = noteList.getSelectionModel().getSelectedItem();
        if (selected!= null){
            noteList.getItems().remove(selected);
            clearEditor();
        }
    }
    public void clearEditor(){
        titleField.clear();
        noteEditor.clear();
    }
    public void loadNoteContent(String noteTitle){
        titleField.setText(noteTitle);

        // todo load content from database
        noteEditor.setText("content for: "+noteTitle);
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }





}