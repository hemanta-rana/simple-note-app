import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Note {
    private int id ;
    private String title;
    private  String description;
    private  ZonedDateTime time;

    public Note( String title, String description){
        this.title = title;
        this.description = description;
        this.time = ZonedDateTime.now();

    }
    public int getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }

    public  String getTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        return time.format(formatter);

    }


}
