import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Harsh Sharma, hs627@drexel.edu
 * The purpose of this MultipleChoice class is to extend off the basic Question class capabilities.
 * It consists of the concrete implementations of a few abstract methods from the super class.
 * Consists of a few more attributes and methods to accommodate for choices.
 * Implements the Serializable interface along with every other class in this program for serialization purposes.
 */
public class MultipleChoice extends Question implements Serializable {
    // ----- Variable Declarations ---------------------------------------------------------
    // Version control for Serialization
    private static final long serialVersionUID = 1L;
    private static Scanner reader = new Scanner(System.in);
    // Attribute for number of choices set by the user
    protected int numOfChoices;
    // Consists of the text contents of the choices set by the user
    protected ArrayList<String> choices = new ArrayList<String>();

    // ----- Instance variables and methods --------------------------------------------------
    public MultipleChoice() {
    }

    public ArrayList<String> getChoices() {
        return this.choices;
    }

    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }

    public void replaceChoice(int index, String new_choice) {
        this.choices.set(index, new_choice);
    }

    public void setAlphabetChoices() {
        int charint = 10;
        int redix = 16;

        // Utilizes the number of choices and ASCII to convert int to appropriate alphabet character choices
        for (int index = 0; index < this.getNumOfChoices(); index++) {
            char alphabet = Character.toUpperCase(Character.forDigit(charint,redix));
            this.alphabetChoices.add(alphabet);
            charint += 1;
        }
    }

    /**
     * Add Question method which is overridden from the super class.
     * Along with the basic question attributes, this method gets user input
     * and sets the additional choice related attributes to this instance.
     */
    public void addQuestion() {
        System.out.println("------------------------------------");
        System.out.println("Enter the prompt for your multiple-choice question:");
        this.setPrompt(reader.nextLine());

        System.out.println("Enter the number of choices for your multiple-choice question:");
        String user_input;
        user_input = reader.nextLine();

        while (!(inputHandler.isInteger(user_input))) {
            System.out.println("Enter the number of choices for your multiple-choice question:");
            user_input = reader.nextLine();
        }

        this.setNumOfChoices(Integer.parseInt(user_input));

        // Prompts user to specify choice contents based on the number of choices they have indicated
        for (int choice = 0; choice < this.getNumOfChoices(); choice++) {
            System.out.println("Enter choice #" + (choice+1));
            String choiceText = reader.nextLine();
            this.getChoices().add(choiceText);
        }

        // Handling multiple responses
        System.out.println("Enter the allowed number of choices to be selected:");
        user_input = reader.nextLine();

        while (!(inputHandler.isIntInRange(user_input, this.getNumOfChoices()))) {
            System.out.println("Enter the allowed number of choices to be selected:");
            user_input = reader.nextLine();
        }

        this.setNumOfResponses(Integer.parseInt(user_input));
        this.setAlphabetChoices();
        this.incrementQuestionNum();
        this.incrementTotalQuestion();
    }

    /**
     * Display Choices method is an additional method special to this type of question.
     * Uses string related parsing methods to format output the choices with their letter
     * choices.
     */
    public void displayChoices() {
        int charint = 10;
        int redix = 16;
        String formattedString = new String();
        String updatedString = new String();
        for (int index = 0; index < this.getNumOfChoices(); index++) {
            char alphabet = Character.toUpperCase(Character.forDigit(charint,redix));
            formattedString = alphabet + ") " + this.getChoices().get(index);
            formattedString = String.format("%1$-15s", formattedString);
            updatedString += formattedString;
            charint += 1;
        }

        System.out.println(updatedString);
    }

    public int getNumOfChoices() {
        return this.numOfChoices;
    }

    public void setNumOfChoices(int numOfChoices) {
        this.numOfChoices = numOfChoices;
    }

    public void modifyQuestion() {
        System.out.println("------------------------------------");
        this.displayPrompt();
        System.out.println("Do you wish to modify the prompt? Enter yes/no");
        String user_input = reader.nextLine();
        if (user_input.toUpperCase().equals("YES")) {
            System.out.println("Enter a new prompt:");
            String new_prompt = reader.nextLine();
            this.setPrompt(new_prompt);
            System.out.println("Prompt has been changed for this question successfully");
        }

        System.out.println("Do you wish to modify the choices? Enter yes/no");
        user_input = reader.nextLine();
        if (user_input.toUpperCase().equals("YES")) {
            System.out.println("Which choice do you wish to modify? (Type the letter)");
            this.displayChoices();
            user_input = reader.nextLine();

            while (!(this.isValid(user_input))) {
                System.out.println("Which choice do you wish to modify? (Type the letter)");
                user_input = reader.nextLine();
            }

            char char_input = reader.nextLine().toUpperCase().charAt(0);
            int choice = (int) char_input;
            System.out.println("Enter a new choice:");
            String new_choice = reader.nextLine();
            this.replaceChoice(choice - 65, new_choice);
            System.out.println("Choice has been changed successfully");
        }

        System.out.println("Do you wish to modify the number of choices allowed to be chosen? Enter yes/no");
        System.out.println("Currently, the number of responses allowed for this question is: " + this.getNumOfResponses());
        user_input = reader.nextLine();

        if (user_input.toUpperCase().equals("YES")) {
            System.out.println("Enter a new number:");
            user_input = reader.nextLine();

            while (!(inputHandler.isIntInRange(user_input, this.getNumOfChoices()))) {
                System.out.println("Enter a new number:");
                user_input = reader.nextLine();
            }

            int new_choice = Integer.parseInt(user_input);
            this.setNumOfResponses(new_choice);
            System.out.println("Number of responses allowed have been changed successfully");
        }
    }

    @Override
    public Boolean isValid (String response) {
        String regex = String.format("[A-Za-z]{1}");

        if (response.matches(regex)) {
            Character c = response.charAt(0);
            if (this.getAlphabetChoices().contains(c)) {
                return Boolean.TRUE;
            }
            else {
                System.out.println("Please choose a letter from the given choices");
                return Boolean.FALSE;
            }
        }
        else {
            System.out.println("Please enter an alphabet");
            return Boolean.FALSE;
        }
    }

    public void displayQuestion() {
        super.displayQuestion();
        this.displayChoices();
    }

    @Override
    public String getQuestionType() {
        return "MultipleChoice";
    }
}