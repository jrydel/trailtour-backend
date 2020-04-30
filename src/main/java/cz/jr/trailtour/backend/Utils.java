package cz.jr.trailtour.backend;

/**
 * Created by Jiří Rýdel on 4/19/20, 9:48 PM
 */
public class Utils {

    public static String convertMessages(Throwable t) {
        String message = t.getMessage();
        if (t.getCause() != null) {
            message = String.join(" - ", message, convertMessages(t.getCause()));
        }
        return message;
    }
}
