import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Harsh Sharma, hs627@drexel.edu
 * The purpose of this Question class is to provide an abstract interface layout consisting of base functionalities
 * that are common amongst all of the different types of questions.
 * It consists of creation, modification, displaying, and validation of the questions.
 * Provides a basic set of abstract methods to implement in the concrete classes of questions.
 * Implements the Serializable interface along with every other class in this program for serialization purposes.
 */
public class Question implements Serializable {
    // ----- Variable Declarations ---------------------------------------------------------
    // Version control for Serialization
    private static final long serialVersionUID = 1L;
    private static Scanner reader = new Scanner(System.in);
    private String prompt;
    // Indicates what type of question is created
    protected String questionType;
    protected int questionNum;
    protected static int totalQuestion;
    // Attribute to handle multiple responses capability
    private int numOfResponses;
    // ArrayList of character choices available for selection (input validation)
    protected ArrayList<Character> alphabetChoices = new ArrayList<Character>();
    protected InputHandling inputHandler = new InputHandling();

    // ----- Instance variables and methods --------------------------------------------------
    /**
     * Constructor that sets the default number of responses to 1 unless changed later
     */
    public Question() {
        this.setNumOfResponses(1);
    }

    public void addQuestion() {
    }

    public void modifyQuestion() {
    }

    public String getPrompt() {
        return this.prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public int getNumOfResponses() {
        return this.numOfResponses;
    }

    public void setNumOfResponses(int numOfResponses) {
        this.numOfResponses = numOfResponses;
    }

    public void setNumOfResponses() {
        System.out.println("------------------------------------");
        System.out.println("Enter the allowed number of multiple responses:");
        this.setNumOfResponses(Integer.parseInt(reader.nextLine()));
    }

    public int getQuestionNum() {
        return this.questionNum;
    }

    public void incrementQuestionNum() {
        this.questionNum = this.getTotalQuestion() + 1;
    }

    public int getTotalQuestion() {
        return this.totalQuestion;
    }

    public int incrementTotalQuestion() {
        return this.totalQuestion = this.getTotalQuestion() + 1;
    }

    public void displayPrompt() {
        System.out.println(this.getPrompt());
    }

    public String getQuestionType() {
        return this.questionType;
    }

    public ArrayList<Character> getAlphabetChoices() {
        return this.alphabetChoices;
    }

    public void setAlphabetChoices(ArrayList<Character> alphabetArr) {
        this.alphabetChoices = alphabetArr;
    }

    public void displayQuestion() {
        System.out.println(this.getQuestionNum() + ") " + this.getPrompt());
    }

    public Boolean isValid(String response) {
        return Boolean.FALSE;
    }
}