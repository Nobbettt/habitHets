package main.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * NoteOrganizer class holds all logic for Note, in order to create a more modular class structure.
 * Therefore the Open-Closed principle is exerted.
 */

public class NoteOrganizer implements IHandler {
    private static NoteOrganizer instant;
    private static List<Note> notesList;

    private NoteOrganizer() {
        notesList = new ArrayList<>();
    }

    /**
     * Control if object is created, so that there may only be one at a time.
     * If there is an instant, another one will not be created.
     * If there is not, an instant will be created.
     */
    public static NoteOrganizer getInstance() {
        if (instant == null) {
            instant = new NoteOrganizer();
            return instant;

        } else {
            return instant;
        }
    }

    public static List<Note> getNotes() {
        return notesList;
    }


    /**
     * Method that removes a Note from a list of Notes, using the id set when creating a Note from Factory.
     * @param id, which is how we locate which note to remove.
     */
    @Override
    public void remove(int id) {
        for (Note note : notesList) {
            if (note.getId() == id) {
                notesList.remove(note);
                return;
            }
        }
    }

    /**
     * Method to locate which date a Note has, in order to place a Note in it's corresponding day.
     * @param d, is the day on which we would like to inspect if there is a Note affiliated with that date.
     * @return
     */
    public Note getNoteDate(LocalDate d){
            for(Note n : notesList){
                if(n.getDay().getYear() == d.getYear() && n.getDay().getDayOfYear() == d.getDayOfYear()){
                    return n;
                }
        }
        return null;
    }

    public boolean isNoteOnDay(LocalDateTime ldt){
        for (Note note : notesList){
            if (note.getDay().getYear() == ldt.getYear() && note.getDay().getDayOfYear() == ldt.getDayOfYear()){
                return true;
            }
        }
        return false;
    }

    static void setNotesList(List<Note> notesList) {
        NoteOrganizer.notesList = notesList;
    }

    /**
     * Method that adds a Note to a list of Notes, using a Factory pattern.
     * Will later be made to get input from database, but for now uses fixed values for testing purposes.
     */
    public static void addNote(String s, LocalDate date) {
        notesList.add(Factory.createNote(s, date));
    }
}
