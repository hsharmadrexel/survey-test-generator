import java.io.Serializable;
import java.util.Scanner;

/**
 * @author Harsh Sharma, hs627@drexel.edu
 * The purpose of this Essay class is to extend off the basic Question class capabilities.
 * It consists of the concrete implementations of a few abstract methods from the super class.
 * Consists of one more attribute to accommodate for multiple responses.
 * Implements the Serializable interface along with every other class in this program for serialization purposes.
 */
public class Essay extends Question implements Serializable {
    // ----- Variable Declarations ---------------------------------------------------------
    // Version control for Serialization
    private static final long serialVersionUID = 1L;
    private static Scanner reader = new Scanner(System.in);
    // Handles multiple responses
    private int numOfResponses;

    // ----- Instance variables and methods --------------------------------------------------
    public Essay(){
    }

    public int getNumOfResponses() {
        return this.numOfResponses;
    }

    public void setNumOfResponses(int numOfResponses) {
        this.numOfResponses = numOfResponses;
    }

    public void setNumOfResponses() {
        String user_input;
        System.out.println("------------------------------------");
        System.out.println("Enter the allowed number of multiple responses:");

        user_input = reader.nextLine();

        while (!(inputHandler.isInteger(user_input))) {
            System.out.println("Enter the allowed number of multiple responses:");
            user_input = reader.nextLine();
        }

        this.numOfResponses = Integer.parseInt(user_input);
    }

    public void addQuestion() {
        System.out.println("------------------------------------");
        System.out.println("Enter the prompt for your essay question:");
        this.setPrompt(reader.nextLine());
        this.setNumOfResponses();
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

        System.out.println("Do you wish to modify the number of responses allowed? Enter yes/no");
        System.out.println("Currently, the number of responses allowed for this question is: " + this.getNumOfResponses());
        user_choice = reader.nextLine();

        if (user_choice.toUpperCase().equals("YES")) {
            System.out.println("Enter a new number:");
            user_choice = reader.nextLine();

            while (!(inputHandler.isInteger(user_choice))) {
                System.out.println("Enter a new number:");
                user_choice = reader.nextLine();
            }

            int new_choice = Integer.parseInt(user_choice);
            this.setNumOfResponses(new_choice);
            System.out.println("Number of responses allowed have been changed successfully");
        }
    }

    @Override
    public String getQuestionType() {
        return "Essay";
    }

    public void displayQuestion() {
        super.displayQuestion();
        System.out.println("This is an essay question which needs " + this.getNumOfResponses() + " response/s");
    }

    @Override
    public Boolean isValid(String response) {
        return Boolean.TRUE;
    }
}