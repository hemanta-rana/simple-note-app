import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private final String db_url = "jdbc:sqlite:note.db";

    public Connection connect() {
        try {
            Connection connection = DriverManager.getConnection(db_url);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void createNoteTable() {
        String sql = "CREATE TABLE IF NOT EXISTS notes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL, " +
                "description TEXT NOT NULL, " +
                "time TEXT NOT NULL" +
                ");";

        // Use try-with-resources to auto-close connection and statement
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            if (conn != null) {
                stmt.execute(sql);
                System.out.println("Table is ready!");
            } else {
                System.out.println("Failed to establish database connection.");
            }

        } catch (SQLException e) {
            System.out.println("SQL error occurred:");
            e.printStackTrace();
        }


    }
    public boolean addNote(Note note){
        String sql = "INSERT INTO notes ( title, description,time) VALUES" +
                "(?,?,?)";

        try(Connection connection = connect();
            PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setString(1,note.getTitle());
            pstmt.setString(2, note.getDescription());
            pstmt.setString(3, note.getTime());
            pstmt.executeUpdate();

            System.out.println("saved successfully ");
            return true;
        }catch (SQLException e){
            e.printStackTrace();

            System.out.println("sql error");
            return false;
        }
    }

    public List<Note> showAllNote(){
        ArrayList<Note> notes = new ArrayList<>();

        String sql = "SELECT * FROM notes";

        try (Connection conn = connect();
       Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)){

            while (rs.next()){
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
//                String time = rs.getString("time");
                notes.add(new Note(title, description));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return notes;

    }


}
