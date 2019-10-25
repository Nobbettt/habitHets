package view;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used as an observer class to store listeners for events
 */
public class EventObserver {
    static List<ViewListener> listeners = new ArrayList<>();

    /**
     * This method calls every listener in list of listeners
     * @param id
     */
    static void listenersReact(int id){
        for (ViewListener listener : listeners){
            listener.actOnUpdate(String.valueOf(id));
        }
    }

    /**
     *
     * @param listener
     */
    public static void addListener(ViewListener listener){
        listeners.add(listener);
    }
}
