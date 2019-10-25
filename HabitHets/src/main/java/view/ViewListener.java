package view;

/**
 * Is used to update the view without being dependent on its parent components
 */
public interface ViewListener {

    /**
     * is called upon when we want to update the view
     * @param msg
     */
    void actOnUpdate(String msg);

}