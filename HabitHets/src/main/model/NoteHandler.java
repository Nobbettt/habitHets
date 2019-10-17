package main.model;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * NoteHandler class holds all logic for Note, in order to create a more modular class structure.
 * Therefore the Open-Closed principle is exerted.
 */

public class NoteHandler implements IHandler {

    public static NoteHandler instant;
    public static List<Note> notesList;

    private NoteHandler(){
        this.notesList = new ArrayList<>();
    }

    /**
     * Control if object is created, so that there may only be one at a time.
     * If there is an instant, another one will not be created.
     * If there is not, an instant will be created.
     */

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

    /**
     * Method that adds a Note to a list of Notes, using a Factory pattern.
     * Will later be made to get input from database, but for now uses fixed values for testing purposes.
     */
    @Override
    public void add() {
        //Get input from database
    }

    /**
     * Method that removes a Note from a list of Notes, using the id set when creating a Note from Factory.
     * @param id, which is how we locate which note to remove.
     */
    @Override
    public void remove(int id) {
        for (Note note : notesList){
            if (note.getId() == id){
                notesList.remove(note);
                return;
            }
        }
        System.out.println("The ID: '" + id + "' does not exist");
    }

    /**
     * Method to locate which date a Note has, in order to place a Note in it's corresponding day.
     * @param d, is the day on which we would like to inspect if there is a Note affiliated with that date.
     * @return
     */
    public Note getNoteDate(LocalDateTime d){
            for(Note n : notesList){
                if(n.getDay().getDayOfYear() == d.getDayOfYear() && n.getDay().getYear() == d.getYear()){
                    return n;
                }
        }
        return null;
    }

    public Note addNote (String s, LocalDateTime date){

            notesList.add(Factory.createNote(s, date));
            return notesList.get(notesList.size() - 1);
        }
    }


