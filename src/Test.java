import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * @author Harsh Sharma, hs627@drexel.edu
 * The purpose of this Test class is to offer users the different functionalities associated with tests.
 * It consists of the functionalities and attributes specified in the survey class along with the correct answer,
 * grading added. Overrides some of the methods specified from Survey to accommodate for additional features.
 * Implements the Serializable interface along with every other class in this program for serialization purposes.
 */
public class Test extends Survey implements Serializable {
    // ----- Variable Declarations ---------------------------------------------------------
    private static Scanner reader = new Scanner(System.in);
    // Correct Response instance which creates a set of correct responses to match against
    protected Response correctResponses = new Response();
    // Chosen Response instance which is based on the response that the user decides to choose while grading
    protected Response chosenResponse = new Response();

    // ----- Instance variables and methods --------------------------------------------------
    public Test() {
        super.dirName = "Test";
        super.saveDirPath = "Test" + File.separator;
        super.isLoaded = Boolean.FALSE;
        Question question = new Question();
        question.totalQuestion = 0;
    }

    @Override
    public void displayMenu() {
        System.out.println("------------------------------------");
        System.out.println("Test Menu");
        System.out.println("1) Create a new Test");
        System.out.println("2) Display an existing Test without correct answers");
        System.out.println("3) Display an existing Test with correct answers");
        System.out.println("4) Load an existing Test");
        System.out.println("5) Save the current Test");
        System.out.println("6) Taking Test Options");
        System.out.println("7) Modify the current Test");
        System.out.println("8) Tabulate a Test");
        System.out.println("9) Grade a Test");
        System.out.println("10) Return to previous menu");

        String user_input = reader.nextLine();

        while (!(inputHandler.isIntInRange(user_input, 10))) {
            user_input = reader.nextLine();
        }

        Integer userSelection = Integer.parseInt(user_input);

        if (userSelection == 1) {
            test = new Test();
            test.setName();
            System.out.println("Test name: " + test.getName());
            test.create();
        }
        else if (userSelection == 2) {
            test.display();
            displayMenu();
        }
        else if (userSelection == 3) {
            test.displayWithAnswer();
            displayMenu();
        }
        else if (userSelection == 4) {
            test = test.load();
            displayMenu();
        }
        else if (userSelection == 5) {
            test.store(test);
            displayMenu();
        }
        else if (userSelection == 6) {
            test.takeSurveyMenu(test);
            displayMenu();
        }
        else if (userSelection == 7) {
            test.modify();
            displayMenu();
        }
        else if (userSelection == 8) {
            test.tabulate(test);
            displayMenu();
        }
        else if (userSelection == 9) {
            test.grade();
            displayMenu();
        }
        else if (userSelection == 10) {
            displayMainMenu();
        }
    }

    /**
     * Same method as described in the Survey Class comments.
     * Has additional functionality of adding a correct answer.
     */
    @Override
    public void create() {
        String answer;
        Question question = this.questionMenu();
        System.out.println("Please enter a correct answer for the question:");
        ArrayList<String> responseList = new ArrayList<>();
        question.getNumOfResponses();
        for (int resp_count = 0; resp_count < question.getNumOfResponses(); resp_count++) {
            answer = reader.nextLine();
            while (!(question.isValid(answer))) {
                question.displayQuestion();
                answer = reader.nextLine();
            }
            responseList.add(answer);
        }
        // Adding the correct response after validating it above
        correctResponses.addResponse(responseList);
        create();
    }

    /**
     * Very similar to the Survey's display method with the added
     * aspect of displaying the correct answer array. Iterates through
     * the correct response array to appropriately output it.
     */
    public void displayWithAnswer() {
        if (super.isLoaded()) {
            System.out.println("------------------------------------");
            System.out.println("Displaying Correct Responses for: " + this.getName());
            int i = 1;
            for (Question question: test.getQuestions()) {
                question.displayQuestion();
                System.out.println("The correct answer is:");
                correctResponses.getResponseArray(i);
                for (String answer: correctResponses.getResponseArray(i)) {
                    System.out.println(answer);
                }
                i = i + 1;
                System.out.println("");
            }
        }
        else {
            System.out.println("You must have a test loaded in order to display it.");
        }
    }

    /**
     * Very similar to the Survey's load method with the difference
     * being that the loaded object is changed from casting Survey
     * to Test since this is a test instance.
     */
    @Override
    public Test load() {
        Test loadedTest = new Test();
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        ArrayList<File> files = new ArrayList<>();

        File surveyFolder = new File(saveDirPath);
        if(!surveyFolder.exists()) {
            System.out.println("No " + dirName.toLowerCase() + " to load from the specified path");
            System.out.println("Redirecting to the main menu");
            displayMenu();
            return null;
        }
        else {
            File[] listOfSurveys = surveyFolder.listFiles();

            int fileNum = 1;
            System.out.println("------------------------------------");

            for (File surveyFile : listOfSurveys) {
                String name = surveyFile.getName();
                if (surveyFile.isFile() & name.contains(dirName)) {
                    System.out.println(fileNum + ". " + surveyFile.getName());
                    files.add(surveyFile);
                    fileNum += 1;
                }
            }

            if (files.size() == 0) {
                System.out.println("No " + dirName.toLowerCase() + "s are created yet. Feel free to start one!");
                System.out.println("Redirecting to the main menu");
                displayMenu();
                return null;
            }

            System.out.println("Please select a " + dirName.toLowerCase() + " file to load: (Type the number not the name)");
            String user_input = reader.nextLine();

            while (!(inputHandler.isIntInRange(user_input, files.size()))) {
                System.out.println("Please select a " + dirName.toLowerCase() + " file to load: (Type the number not the name)");
                user_input = reader.nextLine();
            }

            int chosenFile = Integer.parseInt(user_input);
            String updatedPath = saveDirPath + files.get(chosenFile - 1).getName();

            try {
                fis = new FileInputStream(updatedPath);
                ois = new ObjectInputStream(fis);
                loadedTest = (Test) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.exit(2);
            } finally {
                try {
                    if (ois != null)
                        ois.close();
                    if (fis != null)
                        fis.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            System.out.println(files.get(chosenFile - 1).getName() + " loaded successfully!");
            System.out.println("Redirecting to the main menu");

            loadedTest.setIsLoaded(Boolean.TRUE);
            return loadedTest;
        }
    }

    /**
     * Modify method similar to Survey's modify functionality with
     * added difference of modifying the correct response of a given question.
     */
    @Override
    public void modify() {
        if (isLoaded()) {
            System.out.println("------------------------------------");
            System.out.println("What question do you wish to modify? (Type the number)");
            String user_input = reader.nextLine();
            Integer max = questions.size();

            while (!(inputHandler.isIntInRange(user_input, max))) {
                System.out.println("What question do you wish to modify? (Type the number)");
                user_input = reader.nextLine();
            }

            int question_num = Integer.parseInt(user_input);
            Question question_to_modify = this.getQuestions().get(question_num - 1);
            question_to_modify.modifyQuestion();

            ArrayList<String> correctAns = correctResponses.getResponseArray(question_num);
            question_to_modify.displayQuestion();
            System.out.println("The correct answer is:");
            for (String answer: correctAns) {
                System.out.println(answer);
            }

            System.out.println("Do you wish to modify the correct answer? Enter yes/no");
            String user_choice = reader.nextLine();
            if (user_choice.toUpperCase().equals("YES")) {
                System.out.println("Enter a new correct answer:");
                ArrayList<String> responseList = new ArrayList<>();
                for (int resp_count = 0; resp_count < question_to_modify.getNumOfResponses(); resp_count++) {
                    String answer = reader.nextLine();
                    while (!(question_to_modify.isValid(answer))) {
                        question_to_modify.displayQuestion();
                        answer = reader.nextLine();
                    }
                    responseList.add(answer);
                }
                // Replaces the response object array with modified changes
                correctResponses.replaceQuestionsResponseArray(question_num, responseList);
                System.out.println("Correct answer modified successfully");
            }
        }
        else {
            System.out.println("You must have a test loaded in order to modify it.");
        }
    }

    /**
     * Added grading functionality for the test instance that allows the user
     * to load a chosen response to match against the correct set of responses
     * created for the test.
     */
    public void grade() {
        test = test.load();
        chosenResponse = chosenResponse.loadResponse(test);

        int totalQuestions = test.getQuestions().size();
        int totalScore = 0;
        int essayCount = 0;
        double weight = 100 / totalQuestions;

        for (int ques_num = 1; ques_num < (totalQuestions + 1); ques_num++) {
            Question question = test.getQuestions().get(ques_num - 1);
            if (question.getQuestionType() != "Essay") {
                ArrayList<String> userResponse = chosenResponse.getResponseArray(ques_num);
                // Sorting both of the ArrayLists to ensure equality of order prior to comparison
                Collections.sort(userResponse);
                ArrayList<String> correctResponse = test.correctResponses.getResponseArray(ques_num);
                Collections.sort(correctResponse);

                if (userResponse.equals(correctResponse)) {
                    totalScore += weight;
                }
            }
            else {
                essayCount += 1;
            }
        }
        System.out.println("You received a/an " + totalScore + " on the test. The test was worth 100 points, but only " + ((totalQuestions - essayCount) * weight) + " of those points could be auto graded because there was/were " + essayCount + " essay question/s");
    }
}