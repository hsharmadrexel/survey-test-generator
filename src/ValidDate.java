import java.io.Serializable;
import java.util.Scanner;

/**
 * @author Harsh Sharma, hs627@drexel.edu
 * The purpose of this ValidDate class is to extend off the basic Question class capabilities.
 * It consists of the concrete implementations of a few abstract methods from the super class.
 * Consists of a pattern attribute which enforces the user to enter date in the specified pattern.
 * Implements the Serializable interface along with every other class in this program for serialization purposes.
 */

public class ValidDate extends Question implements Serializable {
    // ----- Variable Declarations ---------------------------------------------------------
    // Version control for Serialization
    private static final long serialVersionUID = 1L;
    private static Scanner reader = new Scanner(System.in);
    // Final constant string variable to specify pattern that won't be changed during the execution
    private final String pattern = "YYYY-MM-DD";

    // ----- Instance variables and methods --------------------------------------------------
    public ValidDate(){
        this.setNumOfResponses(1);
    }

    public String getPattern() {
        return this.pattern;
    }

    public void addQuestion() {
        System.out.println("------------------------------------");
        System.out.println("Enter the prompt for your valid date question:");
        this.setPrompt(reader.nextLine());
        this.setNumOfResponses(1);
        this.incrementQuestionNum();
        this.incrementTotalQuestion();
    }

    public void modifyQuestion() {
        System.out.println("------------------------------------");
        this.displayPrompt();
        System.out.println("Do you wish to modify the prompt? Enter yes/no");
        String user_choice = reader.nextLine();
        if (user_choice.toUpperCase().equals("YES")) {
            System.out.println("Enter a new prompt:");
            String new_prompt = reader.nextLine();
            this.setPrompt(new_prompt);
            System.out.println("Prompt has been changed for this question successfully");
        }
    }

    @Override
    public String getQuestionType() {
        return "ValidDate";
    }

    public void displayQuestion() {
        super.displayQuestion();
        System.out.println("A date should be entered in the following format: YYYY-MM-DD");
    }

    @Override
    public Boolean isValid (String response) {
        String regex = String.format("([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))");

        if (response.matches(regex)) {
            return Boolean.TRUE;
        }
        else {
            System.out.println("Please enter a valid date in the format YYYY-MM-DD");
            return Boolean.FALSE;
        }
    }
}
