package main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import main.model.CalendarAble;
import main.model.Month;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class YearView extends AnchorPane implements ViewAble {
    @FXML private ScrollPane yearScroll;
    @FXML private GridPane yearGrid;
    List<MonthInYear> monthInYears = new ArrayList<>();
    // private List<MonthView> months = new ArrayList(); guess its gonna be something like this.... :/ todo

    public YearView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/year.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        setUpYear();
    }

    private void setUpYear() {
        yearScroll.setFitToWidth(true);
        yearScroll.setFitToHeight(true);
        yearScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        yearScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        yearGrid.setGridLinesVisible(true); // remove soon
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                MonthInYear miy = new MonthInYear();
                monthInYears.add(miy);
                yearGrid.add(miy, j, i);
                // months.add(month); add created month to list months which doesnt exist todo
            }
        }
    }

    @Override
    public void updateView(List<? extends CalendarAble> months) {
        for(int i=0; i< monthInYears.size(); i++){
            Month m = (Month) months.get(i);
            monthInYears.get(i).updateView(m.getDays(), m.getString());
        }
        /*
        for(MonthView month : months) {
            // update months with method implemented in monthView
        }*/

    }

    @Override
    public void updateTimeLine(int hour, int minute) {
        // nothing to see here, just an dumb method required by the interface to be implemented
    }
}
