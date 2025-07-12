


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
//
//        newNote.setOnAction(e-> createNewNote());
//        saveNote.setOnAction(e-> saveCurrentNote());
//        deleteNote.setOnAction(e-> deleCurrentNote());


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

        //TODO  note selection




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


        buttonPanel.getChildren().addAll(saveButton, clearButton);

        rightPanel.getChildren().addAll(
                titleLabel, titleField,
                contentLabel, noteEditor,
                buttonPanel
        );





        return rightPanel;
    }


    public static void main(String[] args) {
        launch(args);
    }




}