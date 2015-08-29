package menu;

import java.io.IOException;

/**
 * Represents one task in the menu.
 *
 * @param <T> value to be returned after executing the task
 */
public interface Task<T> {
    /**
     * Executes the task.
     *
     * @return the return value of the task (may be Void)
     * @throws IOException problems with file
     */
    T run() throws IOException;
}