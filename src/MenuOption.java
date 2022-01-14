import java.io.IOException;
import java.util.Scanner;

/**
 * @author Harsh Sharma, hs627@drexel.edu
 * The purpose of this MenuOption class is to act as the main driver for the program.
 * It creates and initializes the survey and test objects which carry on their own functionalities
 * specified separately in their own classes.
 */

public class MenuOption {
    // ----- Static variables ----------------------------------------------------------------
    // Scanner instance to get user input from the console
    private static Scanner reader = new Scanner(System.in);
    // Survey and test objects that are initialized in main() based on user choice
    public static Survey survey;
    public static Test test;

    // ----- Instance methods ----------------------------------------------------------------

    /**
     * Display the main menu options and handle user input accordingly.
     * Initialize survey/test objects based on input and handle improper input.
     */
    public static void displayMainMenu() {
        System.out.println("------------------------------------");
        System.out.println("Please select a survey/test functionality to proceed:");
        System.out.println("1) Survey");
        System.out.println("2) Test");
        System.out.println("3) Quit");

        String user_input = reader.nextLine();

        // Create an instance of the InputHandling class to handle improper inputs
        InputHandling inputHandler = new InputHandling();
        // Continue asking for user input till appropriate choice is selected
        while (!(inputHandler.isIntInRange(user_input, 3))) {
            user_input = reader.nextLine();
        }

        Integer userSelection = Integer.parseInt(user_input);

        // Display appropriate menus for the proceeding functionalities in the chosen object
        if (userSelection == 1) {
            survey = new Survey();
            survey.displayMenu();
        }
        else if (userSelection == 2) {
            test = new Test();
            test.displayMenu();
        }
        else {
            System.exit(0);
        }
    }

    // ----- Main method ----------------------------------------------------------------
    public static void main(String [] args) throws IOException {
        displayMainMenu();
    }
}