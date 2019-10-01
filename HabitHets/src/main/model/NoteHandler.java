package main.model;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class NoteHandler implements IHandler {

    public static NoteHandler instant;
    public static List<Note> notesList;

    private NoteHandler(){
        this.notesList = new ArrayList<>();


    }

    public static NoteHandler getInstance(){
        if(instant == null) {
            instant = new NoteHandler();
            return instant;

        }else {
            return instant;

        }
    }

    public List<Note> getNotes(){
        return notesList;
    }

    @Override
    public void add() {
        //Get input from database
        notesList.add(Factory.createNote("title", "desc", LocalDateTime.now()));
    }

    @Override
    public void remove(int id) {
        for(Note note : notesList){
            if(note.getId() == id){
                notesList.remove(note);
                return;
            }
        }
    }

    public Note getNoteDate(LocalDateTime d){
            for(Note n : notesList){
                if(n.getDay().getDayOfYear() == d.getDayOfYear() && n.getDay().getYear() == d.getYear()){
                    return n;
                }
        }return null;
    }
}

