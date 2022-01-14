import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Harsh Sharma, hs627@drexel.edu
 * The purpose of this Response class is to allow users to create a response to a specified survey/test.
 * It consists of creation, loading, displaying and modifying of a survey instance.
 * This class is aggregated by the Survey class.
 * Implements the Serializable interface along with every other class in this program for serialization purposes.
 */
public class Response implements Serializable {
    // ----- Variable Declarations ---------------------------------------------------------
    // Version control for Serialization
    private static final long serialVersionUID = 1L;
    private static Scanner reader = new Scanner(System.in);
    private String responseName;
    // ArrayList of the responses to all the questions in the survey/test
    private ArrayList<ArrayList<String>> questionsResponses = new ArrayList<>();
    private static Boolean isLoaded = Boolean.FALSE;
    private InputHandling inputHandler = new InputHandling();

    // ----- Instance methods ----------------------------------------------------------------
    public Response() {}

    public ArrayList<ArrayList<String>> getQuestionsResponsesArray() {
        return this.questionsResponses;
    }

    public void setIsLoaded(Boolean boolVal) {
        this.isLoaded = boolVal;
    }

    public Boolean isLoaded() {
        return this.isLoaded;
    }

    /**
     * Adding a new response results in the displaying of the question and then asking user to fill in their input.
     * If the input is not valid, then it will continue to ask till user provides a valid input.
     */
    public void addNewResponse(Survey survey) {
        Response response = new Response();
        String new_answer;
        this.setIsLoaded(Boolean.TRUE);
        for (Question question: survey.getQuestions()) {
            question.displayQuestion();
            ArrayList<String> responseList = new ArrayList<>();
            question.getNumOfResponses();
            for (int resp_count = 0; resp_count < question.getNumOfResponses(); resp_count++) {
                new_answer = reader.nextLine();
                while (!(question.isValid(new_answer))) {
                    question.displayQuestion();
                    new_answer = reader.nextLine();
                }
                responseList.add(new_answer);
            }
            this.addResponse(responseList);
            System.out.println("");
        }
        this.storeResponse(survey, this);
        this.setIsLoaded(Boolean.FALSE);
    }

    /**
     * Same as Survey, Test functionalities. Refer to Survey comments for more info if needed.
     */
    public Response loadResponse(Survey survey) {
        ArrayList<File> files = new ArrayList<>();

        File surveyFolder = new File(survey.saveDirPath);
        if(!surveyFolder.exists()) {
            System.out.println("No responses to load from the specified path");
            System.out.println("Redirecting to the main menu");
            survey.takeSurveyMenu(survey);
            return null;
        }
        else {
            File[] listOfResponses = surveyFolder.listFiles();

            int fileNum = 1;
            System.out.println("------------------------------------");
            System.out.println("IMPORTANT: You will only be able to view response files associated with the current loaded " + survey.dirName.toLowerCase() + " from the main menu. \n If you wish to check responses of another " + survey.dirName.toLowerCase() + ", load it first in the main menu to view the response files associated");
            System.out.println("");

            for (File surveyFile : listOfResponses) {
                String name = surveyFile.getName();
                if (surveyFile.isFile() & name.contains(survey.getName() + "Response")) {
                    System.out.println(fileNum + ". " + surveyFile.getName());
                    files.add(surveyFile);
                    fileNum += 1;
                }
            }

            if (files.size() == 0) {
                System.out.println("No responses are created for this " + survey.dirName + " yet. Feel free to start one!");
                System.out.println("Redirecting to the main menu");
                survey.takeSurveyMenu(survey);
                return null;
            }

            System.out.println("Please select a response file to load: (Type the number not the name)");
            int chosenFile = Integer.parseInt(reader.nextLine());
            String updatedPath = survey.saveDirPath + files.get(chosenFile - 1).getName();

            Response loadedResponse = new Response();
            FileInputStream fis = null;
            ObjectInputStream ois = null;
            try {
                fis = new FileInputStream(updatedPath);
                ois = new ObjectInputStream(fis);
                loadedResponse = (Response) ois.readObject();
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

            loadedResponse.setIsLoaded(Boolean.TRUE);
            return loadedResponse;
        }
    }

    /**
     * Same as Survey, Test functionalities. Refer to Survey comments for more info if needed.
     */
    public void storeResponse(Survey survey, Response response) {
        if (isLoaded()) {
            survey.createDirectory(survey.dirName);
            String savePath = survey.saveDirPath + survey.getName() + "Response - " + response.getResponseName();
            FileOutputStream fos = null;
            ObjectOutputStream oos = null;
            try {
                fos = new FileOutputStream(savePath);
                oos = new ObjectOutputStream(fos);
                oos.writeObject(response);
            }
            catch(IOException e){
                e.printStackTrace();
                System.exit(2);
            }
            finally{
                try{
                    if(fos != null)
                        fos.close();
                    if(oos != null)
                        oos.close();
                }
                catch(IOException ex){
                    ex.printStackTrace();
                }
            }

            System.out.println("Response " + response.getResponseName() + " saved successfully!");
        }
        else {
            System.out.println("You must have a response file loaded in order to save it.");
        }
    }

    /**
     * Displays the question and its associated answer as part of the response
     */
    public void displayResponse(Survey survey) {
        if (this.isLoaded()) {
            System.out.println("------------------------------------");
            System.out.println("Displaying Response: " + this.getResponseName());
            int i = 1;
                for (Question question: survey.getQuestions()) {
                    question.displayQuestion();
                    System.out.println("Response:");
                    this.getResponseArray(i);
                    for (String answer: this.getResponseArray(i)) {
                        System.out.println(answer);
                    }
                    i = i + 1;
                    System.out.println("");
                }
            }
        else {
            System.out.println("You must have a response file loaded in order to display it.");
        }
    }

    public void modifyResponse(ArrayList<Question> questions) {
        if (this.isLoaded()) {
            System.out.println("------------------------------------");
            System.out.println("What response do you wish to modify? (Type the number)");
            String user_input;
            user_input = reader.nextLine();
            Integer max = questions.size();

            while (!(inputHandler.isIntInRange(user_input, max))) {
                System.out.println("What response do you wish to modify? (Type the number)");
                user_input = reader.nextLine();
            }

            int response_num = Integer.parseInt(user_input);
            Question question = questions.get(response_num - 1);
            ArrayList<String> response_to_modify = this.getResponseArray(response_num);
            question.displayQuestion();
            System.out.println("Your Response:");
            for (String answer: response_to_modify) {
                System.out.println(answer);
            }

            System.out.println("Enter your new response/s to the question");
            ArrayList<String> new_array_responses = new ArrayList<>();
            for (int i = 0; i < question.getNumOfResponses(); i++) {
                user_input = reader.nextLine();
                while (!(question.isValid(user_input))) {
                    question.displayQuestion();
                    user_input = reader.nextLine();
                }
                new_array_responses.add(user_input);
            }

            this.replaceQuestionsResponseArray(response_num, new_array_responses);
        }
        else {
            System.out.println("You must have a response file loaded in order to modify it.");
        }
    }

    public void addResponse(ArrayList<String> response) {
        this.getQuestionsResponsesArray().add(response);
    }

    public void displayAllResponses() {
        System.out.println(this.getQuestionsResponsesArray());
    }

    public ArrayList<String> getResponseArray(int question_num) {
        return (this.getQuestionsResponsesArray().get(question_num - 1));
    }

    public void replaceQuestionsResponseArray(int question_num, ArrayList<String> new_responses) {
        this.getQuestionsResponsesArray().set(question_num - 1, new_responses);
    }

    public void setResponseName() {
        System.out.println("Name the response file:");
        this.responseName = reader.nextLine();
        while ((this.getResponseName().contains("Test")) || (this.getResponseName().contains("Survey")) || (this.getResponseName().trim().isEmpty())) {
            System.out.println("The name cannot be empty or contain the phrases 'Test' and 'Survey'. Please choose a suitable name for the response file");
            System.out.println("Name the response file:");
            this.responseName = reader.nextLine();
        }
    }

    public String getResponseName() {
        return this.responseName;
    }
}