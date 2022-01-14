import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Harsh Sharma, hs627@drexel.edu
 * The purpose of this Matching class is to extend off the basic Question class capabilities.
 * It consists of the concrete implementations of a few abstract methods from the super class.
 * Consists of a few more attributes and methods to accommodate for the columns.
 * Implements the Serializable interface along with every other class in this program for serialization purposes.
 */
public class Matching extends Question implements Serializable {
    // ----- Variable Declarations ---------------------------------------------------------
    // Version control for Serialization
    private static final long serialVersionUID = 1L;
    private static Scanner reader = new Scanner(System.in);
    // ArrayList of String to represent the columns
    private ArrayList<String> leftColumn = new ArrayList<>();
    private ArrayList<String> rightColumn = new ArrayList<>();
    // Total number of choices
    private int numOfChoices;

    // ----- Instance variables and methods --------------------------------------------------
    /**
     * Constructor class which sets the number of responses restricted to the number of choices entered by the user.
     */
    public Matching() {
        this.setNumOfResponses(this.getNumOfChoices());
    }

    public int getNumOfChoices() {
        return this.numOfChoices;
    }

    public void setNumOfChoices(int numOfChoices) {
        this.numOfChoices = numOfChoices;
    }

    public void setAlphabetChoices() {
        int charint = 10;
        int redix = 16;

        for (int index = 0; index < this.getNumOfChoices(); index++) {
            char alphabet = Character.toUpperCase(Character.forDigit(charint,redix));
            this.alphabetChoices.add(alphabet);
            charint += 1;
        }
    }

    public ArrayList<String> getLeftColumn() {
        return this.leftColumn;
    }

    public ArrayList<String> getRightColumn() {
        return this.rightColumn;
    }

    public void setLeftColumn() {
        for (int choice = 0; choice < this.getNumOfChoices(); choice++) {
            System.out.println("Enter Left Column Choice #" + (choice+1));
            String choiceText = reader.nextLine();
            this.getLeftColumn().add(choiceText);
        }
    }

    public void setRightColumn() {
        for (int choice = 0; choice < this.getNumOfChoices(); choice++) {
            System.out.println("Enter Right Column Choice #" + (choice+1));
            String choiceText = reader.nextLine();
            this.getRightColumn().add(choiceText);
        }
    }

    public void replaceColumn(ArrayList<String> column, int index, String newVal) {
        column.set(index, newVal);
    }

    public void addQuestion() {
        System.out.println("------------------------------------");
        System.out.println("Enter the prompt for your matching question:");
        this.setPrompt(reader.nextLine());

        System.out.println("Enter the number of choices for your matching question:");
        this.setNumOfChoices(Integer.parseInt(reader.nextLine()));
        this.setNumOfResponses(this.getNumOfChoices());

        this.setLeftColumn();
        this.setRightColumn();
        this.setAlphabetChoices();
        this.incrementQuestionNum();
        this.incrementTotalQuestion();
    }

    /**
     * Display columns where both left and right columns are formatted with alphabets
     * or numerically as needed.
     */
    public void displayColumns() {
        int charint = 10;
        int redix = 16;
        int num = 1;
        String formattedleftString = new String();
        String formattedrightString = new String();

        for (int index = 0; index < this.getNumOfChoices(); index++) {
            char leftAlphabet = Character.toUpperCase(Character.forDigit(charint,redix));
            formattedleftString = (leftAlphabet + ") " + this.getLeftColumn().get(index));
            formattedrightString = (num + ") " + this.getRightColumn().get(index));
            String output = String.format("%1$-20s %2$-20s", formattedleftString, formattedrightString);
            System.out.println(output);
            charint += 1;
            num += 1;
        }
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

        System.out.println("Do you wish to modify the column choices? Enter yes/no");
        user_choice = reader.nextLine();
        if (user_choice.toUpperCase().equals("YES")) {
            System.out.println("Which choice do you wish to modify? (Type the letter or number appropriately)");
            this.displayColumns();
            char user_input = reader.nextLine().toUpperCase().charAt(0);
            int choice = (int) user_input;
            // ASCII based int values comparison to detect whether alphabet or numerical input was given
            if (choice > 48 & choice < 58) {
                System.out.println("Enter a new choice:");
                String new_choice = reader.nextLine();
                this.replaceColumn(this.getRightColumn(), choice - 49, new_choice);
                System.out.println("Choice has been changed successfully");
            }
            else {
                System.out.println("Enter a new choice:");
                String new_choice = reader.nextLine();
                this.replaceColumn(this.getLeftColumn(), choice - 65, new_choice);
                System.out.println("Choice has been changed successfully");
            }
        }
    }

    @Override
    public Boolean isValid (String response) {
        String regex = String.format("[A-Za-z][1-9" + getNumOfChoices() + "]{1}");

        if (response.matches(regex)) {
            Character c = response.charAt(0);
            if (this.getAlphabetChoices().contains(c)) {
                Integer value = null;
                try {
                    String [] arrString = response.split(c.toString());
                    value = Integer.parseInt(response.valueOf(arrString[1]));
                    return (inputHandler.isIntInRange(arrString[1], this.getNumOfChoices()));
                }
                catch (NumberFormatException exp) {
                    System.out.println("Please enter a valid number");
                    return Boolean.FALSE;
                }
            }
            else {
                System.out.println("Please enter a valid alphabet from the given choices above");
                return Boolean.FALSE;
            }
        }
        else {
            System.out.println("Please enter a valid alphabet choice followed by a valid number choice");
            return Boolean.FALSE;
        }
    }

    public void displayQuestion() {
        super.displayQuestion();
        this.displayColumns();
    }

    @Override
    public String getQuestionType() {
        return "Matching";
    }
}
