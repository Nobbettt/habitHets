package view;

import java.util.ArrayList;
import java.util.List;

public class EventObserver {
    static List<ViewListener> listeners = new ArrayList<>();

    static void listenersReact(int id){
        for (ViewListener listener : listeners){
            listener.actOnUpdate(String.valueOf(id));
        }
    }

    public static void addListener(ViewListener listener){
        listeners.add(listener);
    }
}
