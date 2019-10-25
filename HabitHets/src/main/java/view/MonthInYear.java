package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for the graphical representation of the months in a year in year view in the application
 */
class MonthInYear extends AnchorPane  {

    @FXML private GridPane gridPane;
    @FXML private Label monthLabel;
    private List<Label> days = new ArrayList<>();

    /**
     * Imports fxml file and sets this class as the fx controller and root of the fxml file
     * loads the file and checks for exception
     */
    MonthInYear() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/monthInYear.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }
        setup();
    }

    /**
     * Update method called from year view to update the months content
     * @param list
     * @param monthName
     */
    void updateView(List<LocalDateTime> list, String monthName) {
        LocalDateTime firstDay = list.get(0);
        int k = firstDay.getDayOfWeek().getValue()-1;
        int dc = 0;
        monthLabel.setText(monthName);
        for (int e=0; e<k; e++){
            days.get(e).setText(" ");
        }
        for (int i = k; i < days.size(); i++){
            if (dc< list.size()){
                LocalDateTime tmpDay = list.get(dc);
                Integer dayNb = tmpDay.getDayOfMonth();
                days.get(i).setText(dayNb.toString());
                dc++;
            }
        }
    }

    /**
     * Is called from the constructor
     * Sets up the view and prepares it to make it possible to just update the existing content in future
     */
    private void setup(){
        for (int i = 1; i < gridPane.getRowCount(); i++) {
            for (int j = 0; j < gridPane.getColumnCount(); j++) {
                AnchorPane a = new AnchorPane();
                Label label = new Label();
                label.setAlignment(Pos.CENTER);
                label.setTextFill(Color.valueOf("#FFFF"));
                days.add(label);
                a.getChildren().add(label);
                gridPane.add(a,j,i);
                fitItem(a, label);
            }
        }

    }

    /**
     * Helper method that takes a parent and child node and fits the child to the parents width and height, (anchors it)
     * @param parent
     * @param child
     */
    private void fitItem(AnchorPane parent, Node child) {
            setTopAnchor(child, 0.0);
            setRightAnchor(child, 0.0);
            setBottomAnchor(child, 0.0);
            setLeftAnchor(child, 0.0);
        }
}
