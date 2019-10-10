package main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import main.model.CalendarAble;

import java.io.IOException;
import java.util.List;

public class YearView extends AnchorPane implements ViewAble {
    @FXML private ScrollPane yearScroll;
    @FXML private GridPane yearGrid;
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
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                Label month = new Label("month things");// new month or something instead todo
                yearGrid.add(month, i, j);
                // months.add(month); add created month to list months which doesnt exist todo
            }
        }
    }

    @Override
    public void updateView(List<? extends CalendarAble> months) {/*
        for(MonthView month : months) {
            // update months with method implemented in monthView
        }*/
        System.out.println(months.get(0).getDateString());
    }

    @Override
    public void updateTimeLine(int hour, int minute) {
        // nothing to see here, just an dumb method required by the interface to be implemented
    }
}
