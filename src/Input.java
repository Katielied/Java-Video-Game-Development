import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import java.util.ArrayList;

/**
 * A structure for storing and updating keyboard state:
 *  which keys are currently pressed or just pressed/released.
 */
public class Input
{
    public ArrayList<String> justPressedQueue;
    public ArrayList<String> justReleasedQueue;
    public ArrayList<String> justPressedList;
    public ArrayList<String> stillPressedList;
    public ArrayList<String> justReleasedList;

    /**
     * Initialize object and activate event listeners.
     * @param listeningScene the window Scene that has focus during the game
     */
    public Input(Scene listeningScene) {
        justPressedQueue = new ArrayList<String>();
        justReleasedQueue = new ArrayList<String>();
        justPressedList = new ArrayList<String>();
        stillPressedList = new ArrayList<String>();
        justReleasedList = new ArrayList<String>();

        // Example Strings: UP, LEFT, Q, DIGIT1, SPACE, SHIFT
        listeningScene.setOnKeyPressed(
                (KeyEvent event) ->
                {
                    String keyName = event.getCode().toString();
                    justPressedQueue.add(keyName);
                }
        );

        listeningScene.setOnKeyReleased(
                (KeyEvent event) ->
                {
                    String keyName = event.getCode().toString();
                    justReleasedQueue.add(keyName);
                }
        );
    }

    public void update()
    {
        justPressedList.clear();
        justReleasedList.clear();

        for(String keyName : justPressedQueue)
        {
            if(!stillPressedList.contains(keyName))
            {
                justPressedList.add(keyName);
                stillPressedList.add(keyName);
            }
        }

        for(String keyName : justReleasedQueue)
        {
            stillPressedList.remove(keyName);
            justReleasedList.add(keyName);
        }

        justPressedQueue.clear();
        justReleasedQueue.clear();

    }

    //discrete
    public boolean isKeyJustPressed(String keyName)
    {
        return justPressedList.contains(keyName);
    }

    //continuous
    public boolean isKeyPressed(String keyName)
    {
        return stillPressedList.contains(keyName);
    }

    public boolean isKeyJustReleased(String keyName)
    {
        return justReleasedList.contains(keyName);
    }
}
