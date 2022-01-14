import java.io.Serializable;
import java.util.Scanner;

/**
 * @author Harsh Sharma, hs627@drexel.edu
 * The purpose of this ShortAnswer class is to extend off the basic Essay class capabilities.
 * It is a specific implementation of the Essay with restricted character limit factor.
 * Uses the constructor to set ShortAnswer based options to the Essay superclass.
 * Implements the Serializable interface along with every other class in this program for serialization purposes.
 */
public class ShortAnswer extends Essay implements Serializable {
    // ----- Variable Declarations ---------------------------------------------------------
    // Version control for Serialization
    private static final long serialVersionUID = 1L;
    private static Scanner reader = new Scanner(System.in);
    // Character Limit attribute to handle input in range of the limit
    private int charLimit;

    // ----- Instance variables and methods --------------------------------------------------
    public ShortAnswer() {
        this.setNumOfResponses(1);
    }

    public int getCharLimit() {
        return this.charLimit;
    }

    public void setCharLimit(int charLimit) {
        this.charLimit = charLimit;
    }

    public void addQuestion() {
        System.out.println("------------------------------------");
        System.out.println("Enter the prompt for your short answer question:");
        String user_input = reader.nextLine();
        this.setPrompt(user_input);

        System.out.println("Enter the character limit for the question");
        user_input = reader.nextLine();

        while (!(inputHandler.isInteger(user_input))) {
            System.out.println("Enter the character limit for the question:");
            user_input = reader.nextLine();
        }

        this.setCharLimit(Integer.parseInt(user_input));
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

        System.out.println("Do you wish to modify the character limit? Enter yes/no");
        System.out.println("Currently, the character limit is: " + this.getCharLimit());
        user_choice = reader.nextLine();

        if (user_choice.toUpperCase().equals("YES")) {
            System.out.println("Enter a new number:");
            user_choice = reader.nextLine();

            while (!(inputHandler.isInteger(user_choice))) {
                System.out.println("Enter a new number:");
                user_choice = reader.nextLine();
            }

            int new_choice = Integer.parseInt(user_choice);
            this.setCharLimit(new_choice);
            System.out.println("Character limit has been changed successfully");
        }
    }

    @Override
    public String getQuestionType() {
        return "ShortAnswer";
    }

    @Override
    public void displayQuestion() {
        System.out.println(this.getQuestionNum() + ") " + this.getPrompt());
        System.out.println("This is a short answer question which needs " + this.getNumOfResponses() + " response/s");
        System.out.println("This is a short answer question with a character limit of " + this.getCharLimit() + " characters");
    }

    @Override
    public Boolean isValid (String response) {
        if (response.length() <= getCharLimit()) {
            return Boolean.TRUE;
        }
        else {
            System.out.println("Type in up to " + getCharLimit() + " characters (anything in excess will not be accepted):");
            return Boolean.FALSE;
        }
    }
}