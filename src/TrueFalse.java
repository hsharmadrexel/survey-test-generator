import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Harsh Sharma, hs627@drexel.edu
 * The purpose of this TrueFalse class is to extend off the basic MultipleChoice class capabilities.
 * It is a specific implementation of the MultipleChoice with restricted choices.
 * Uses the constructor to set TrueFalse based options to the MultipleChoice superclass.
 * Implements the Serializable interface along with every other class in this program for serialization purposes.
 */
public class TrueFalse extends MultipleChoice implements Serializable {
    // ----- Variable Declarations ---------------------------------------------------------
    // Version control for Serialization
    private static final long serialVersionUID = 1L;
    private static Scanner reader = new Scanner(System.in);

    // ----- Instance variables and methods --------------------------------------------------
    /**
     * Constructor class which instantiates a special type of a MCQ with special restrictions by default.
     */
    public TrueFalse() {
        this.setNumOfChoices(2);
        this.setNumOfResponses(1);
        this.setChoices(new ArrayList<>(Arrays.asList("T", "F")));
        this.setAlphabetChoices(new ArrayList<>(Arrays.asList('T', 'F')));
    }

    public void addQuestion() {
        System.out.println("------------------------------------");
        System.out.println("Enter the prompt for your True/False question:");
        this.setPrompt(reader.nextLine());
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

    public void displayChoices() {
        System.out.println("T/F");
    }

    @Override
    public String getQuestionType() {
        return "TrueFalse";
    }
}
