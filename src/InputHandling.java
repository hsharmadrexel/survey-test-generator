import java.io.Serializable;

/**
 * @author Harsh Sharma, hs627@drexel.edu
 * The purpose of this InputHandling class is to provide basic error and input handling methods to other classes.
 * Implements the Serializable interface along with every other class in this program for serialization purposes.
 */

public class InputHandling implements Serializable {

    // ----- Instance variables and methods --------------------------------------------------
    public InputHandling() {}

    public Boolean isInteger (String response) {
        try {
            Integer value = null;
            value = Integer.parseInt(response);
            if (value > 0) {
                return Boolean.TRUE;
            }
            else {
                System.out.println("Please enter a positive integer");
                return Boolean.FALSE;
            }
        }
        catch (NumberFormatException exp) {
            System.out.println("Please enter a numerical input");
            return Boolean.FALSE;
        }
    }

    public Boolean isIntInRange (String response, int max) {
        if (isInteger(response)) {
            Integer value = null;
            value = Integer.parseInt(response);
            if (value <= max) {
                return Boolean.TRUE;
            }
            else {
                System.out.println("Please enter an integer that is in the appropriate range");
                return Boolean.FALSE;
            }
        }
        else {
            return Boolean.FALSE;
        }
    }
}
