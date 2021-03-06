package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.Facade;

import java.io.IOException;

/**
 * This class is responsible for the graphical representation of the events in the week- and single day view in the application
 */
class EventView extends AnchorPane {
    @FXML
    private Label titleLabel;
    @FXML
    private Label timeLabel;
    private int id;
    private Facade facade;

    /**
     * Imports fxml file and sets this class as the fx controller and root of the fxml file
     * loads the file and checks for exception
     * Sets local id for the received event
     * Updates the labels of the view to the content that corresponds to the received id
     */
    EventView(int id) {
        this.id = id;
        facade = new Facade();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/event.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        titleLabel.setText(facade.getEventTitle(id));
        timeLabel.setText(facade.getEventStarttimeString(id));
    }

    /**
     * Is called upon click on event element and displays edit interface to user
     */
    @FXML
    public void editEventClicked(){
        EventObserver.listenersReact(id);
    }
}
