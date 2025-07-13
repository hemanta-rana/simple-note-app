
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

public class SimpleNoteApp extends Application {
    private ListView<String> noteList;
    private TextArea noteEditor;
    private TextField titleField;
    private Label time;

    DatabaseManager databaseManager = new DatabaseManager();

    @Override
    public void start(Stage stage) throws Exception {
        databaseManager.createNoteTable();
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


        loadFromDatabase();
        // create the scene
        Scene scene = new Scene(root, 800,600);

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
//        noteList.getItems().addAll("");

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

        time = new Label("Date");

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
                time,buttonPanel
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
            databaseManager.addNote(new Note(title, content));

        } else {
            databaseManager.updateNote(new Note(title, content));
        }




        // TODO save in the database

        showAlert("Success", "Note saved Successfully! ");
    }
    public void deleteCurrentNote(){
        String selected = noteList.getSelectionModel().getSelectedItem();

        if (selected!= null){
            noteList.getItems().remove(selected);
            databaseManager.deleteNote(selected);
            clearEditor();
        }
    }
    public void clearEditor(){
        titleField.clear();
        noteEditor.clear();
    }
    public void loadNoteContent(String noteTitle){
        titleField.setText(noteTitle);
//        boolean found = false;
        for (Note note: databaseManager.showAllNote()){
            if (note.getTitle().equals(noteTitle)){
                noteEditor.setText(note.getDescription());
                time.setText("Note saved : "+note.getTime());
                break;
            }
        }
    }
    public void loadFromDatabase(){
        try{
            noteList.getItems().clear();
            for (Note note: databaseManager.showAllNote()){
                noteList.getItems().add(note.getTitle());
            }
        }catch (Exception e){
            showAlert("Error","failed to load notes");
        }
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