import java.io.*;
import java.util.*;

/**
 * @author Harsh Sharma, hs627@drexel.edu
 * The purpose of this Survey class is to offer users the different functionalities associated with surveys.
 * It consists of creation, loading, displaying, saving, modifying, taking and tabulation of a survey instance.
 * Survey class extends off the MenuOption to successfully redirect the user if they choose to go to the previous menu.
 * Implements the Serializable interface along with every other class in this program for serialization purposes.
 */
public class Survey extends MenuOption implements Serializable {
    // ----- Variable Declarations ---------------------------------------------------------
    // Version control for Serialization
    private static final long serialVersionUID = 1L;
    // Name of the base directory to be created (Either "Survey" or "Test" based on instantiation)
    public String dirName;
    // Path of the directory where a created survey is supposed to be saved
    public String saveDirPath;
    private static Scanner reader = new Scanner(System.in);
    // Name of the survey that is created
    private String name;
    // Boolean variable to serve as a flag which checks for whether a survey file is loaded or not
    public Boolean isLoaded = Boolean.FALSE;
    // ArrayList of Question type to store all the questions associated with the survey
    protected ArrayList<Question> questions = new ArrayList<>();
    // Response instantiated object that deals with the functionality of taking a survey
    protected Response response = new Response();
    //  ArrayList of Response type to store all the responses associated with the survey
    protected ArrayList<Response> responses = new ArrayList<>();
    protected InputHandling inputHandler = new InputHandling();

    // ----- Instance variables and methods --------------------------------------------------
    public Survey() {
        // Initialize the base directory to be named as "Survey" as this is a survey instance
        this.dirName = "Survey";
        // Utilizing file separators to specify a path where Survey objects can be saved
        this.saveDirPath = "Survey" + File.separator;
        Question question = new Question();
        // Indicating that when the survey is initially created, it is empty and has no questions
        question.totalQuestion = 0;
    }

    /**
     * Landing menu for the Survey object where it is provided with the different functionalities
     * available for a survey. Invokes the different methods based on the user selection.
     * User selection is once again validated through InputHandler class instance methods.
     */
    public void displayMenu() {
        System.out.println("------------------------------------");
        System.out.println("Survey Menu");
        System.out.println("1) Create a new Survey");
        System.out.println("2) Display an existing Survey");
        System.out.println("3) Load an existing Survey");
        System.out.println("4) Save the current Survey");
        System.out.println("5) Taking Survey Options");
        System.out.println("6) Modifying the current Survey");
        System.out.println("7) Tabulate a Survey");
        System.out.println("8) Return to previous menu");

        String user_input = reader.nextLine();

        while (!(inputHandler.isIntInRange(user_input, 8))) {
            user_input = reader.nextLine();
        }

        Integer userSelection = Integer.parseInt(user_input);

        if (userSelection == 1) {
            // Creating a new survey object if user chooses to create a new survey
            survey = new Survey();
            // Using setter methods to set the name of the survey
            survey.setName();
            // Displaying the survey name and invoking the method which has the menu of questions to create
            System.out.println("Survey name: " + survey.getName());
            survey.create();
        }
        else if (userSelection == 2) {
            // Displaying a survey which is loaded by the user
            survey.display();
            // Recursive calls to this method so that the user can repeat the process
            displayMenu();
        }
        else if (userSelection == 3) {
            // Loading the instantiated survey object with the loaded Survey type object
            survey = survey.load();
            displayMenu();
        }
        else if (userSelection == 4) {
            // Storing the survey object through serialization
            survey.store(survey);
            displayMenu();
        }
        else if (userSelection == 5) {
            // Navigating the user to the taking survey menu to
            // offer functionalities regarding responses for the loaded survey
            survey.takeSurveyMenu(survey);
            displayMenu();
        }
        else if (userSelection == 6) {
            // Invoking the modify method to appropriately modify attributes of the survey's questions
            survey.modify();
            displayMenu();
        }
        else if (userSelection == 7) {
            // Invoking the tabulation method to appropriately display the
            // tabulated results of existing responses to a loaded survey
            survey.tabulate(survey);
            displayMenu();
        }
        else if (userSelection == 8) {
            // Navigate to previous menu
            displayMainMenu();
        }
    }

    /**
     * Method that provides users with the available questions that can be created.
     * Initializes the appropriate type of question object based on user selection.
     * Returns the question object that is created in the process.
     */
    public Question questionMenu() {
        // Using setter method to change the flag value to TRUE since during
        // the creation of the survey, there is no need to load a survey
        this.setIsLoaded(Boolean.TRUE);
        System.out.println("------------------------------------");
        System.out.println("Questions Menu");
        System.out.println("1) Add a new T/F question");
        System.out.println("2) Add a new multiple-choice question");
        System.out.println("3) Add a new short answer question");
        System.out.println("4) Add a new essay question");
        System.out.println("5) Add a new date question");
        System.out.println("6) Add a new matching question");
        System.out.println("7) Return to previous menu");

        String user_input = reader.nextLine();

        while (!(inputHandler.isIntInRange(user_input, 7))) {
            user_input = reader.nextLine();
        }

        Integer userSelection = Integer.parseInt(user_input);

        Question question = null;

        // Appropriately instantiating the specific question objects
        if (userSelection == 1) {
            question = new TrueFalse();
        }
        else if (userSelection == 2) {
            question = new MultipleChoice();
        }
        else if (userSelection == 3) {
            question = new ShortAnswer();
        }
        else if (userSelection == 4) {
            question = new Essay();
        }
        else if (userSelection == 5) {
            question = new ValidDate();
        }
        else if (userSelection == 6) {
            question = new Matching();
        }
        else if (userSelection == 7) {
            // If the user chooses to go to the previous menu, his changes are saved and survey is created
            this.store(this);
            // Change the flag to allow for a survey to be loaded in order to use functionalities
            this.setIsLoaded(Boolean.FALSE);
            displayMenu();
        }
        // Based on the created question object, invoke the method to
        // add the question details and ask the input needed to create the question
        question.addQuestion();
        // Using setter method to add the created question object to the ArrayList of question objects
        setQuestions(question);
        return question;
    }

    /**
     * A generic create method which the inheriting class (Test) can use to override
     * its own behavior. Recursively invokes the questionMenu() method till the user
     * decides to finish with the creation of the survey and go to the previous menu.
     */
    public void create() {
        questionMenu();
        create();
    }

    /**
     * Method to appropriately display a survey that is loaded
     * Survey's contents such as its questions and any other
     * useful information is displayed along with the question.
     */
    public void display() {
        // Only allows the user to display the survey if it is loaded
        if (isLoaded()) {
            System.out.println("------------------------------------");
            System.out.println("Displaying " + dirName + ": " + this.getName());
            // Going through the Questions ArrayList to display each question in the survey
            for (Question question: this.getQuestions()) {
                question.displayQuestion();
                System.out.println("");
            }
        }
        // Handling the case where the user has not loaded a survey
        else {
            System.out.println("You must have a " + dirName.toLowerCase() + " loaded in order to display it.");
        }
    }

    /**
     * A Boolean getter method which is an attribute flag for ensuring
     * whether the survey object has been loaded or not
     */
    public Boolean isLoaded() {
        return this.isLoaded;
    }

    /**
     * Load method which loads the survey from the given directory path
     * Handles cases which might throw exceptions, cases where no survey
     * exists. Deserializes into a survey object which is returned upon
     * loading the chosen survey.
     */
    public Survey load() {
        ArrayList<File> files = new ArrayList<>();

        // Creating a new file type from the base directory folder path
        File surveyFolder = new File(saveDirPath);
        // If the base folder is deleted, then handle the error appropriately and break the process
        if(!surveyFolder.exists()) {
            System.out.println("No " + dirName.toLowerCase() + " to load from the specified path");
            System.out.println("Redirecting to the main menu");
            displayMenu();
            return null;
        }
        else {
            // Get an array of files in the Survey base folder
            File[] listOfSurveys = surveyFolder.listFiles();

            int fileNum = 1;
            System.out.println("------------------------------------");

            // For-loop to iterate through the list of the surveys and display each file name for user to choose
            // Makes sure to only display Survey objects in case if other files are added to the directory externally
            for (File surveyFile : listOfSurveys) {
                String name = surveyFile.getName();
                if (surveyFile.isFile() & name.contains(dirName)) {
                    System.out.println(fileNum + ". " + surveyFile.getName());
                    files.add(surveyFile);
                    fileNum += 1;
                }
            }

            // Handling exception error where no surveys exist. Informs the user and redirects to menu options.
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
            // String with updated path that is the full pathname to load the chosen survey file
            String updatedPath = saveDirPath + files.get(chosenFile - 1).getName();

            Survey loadedObject = new Survey();
            FileInputStream fis = null;
            ObjectInputStream ois = null;

            // Deserialization process that creates a new file stream with the specified path to
            // load the chosen file. Casts the loaded object to a compatible Survey type upon reading
            try {
                fis = new FileInputStream(updatedPath);
                ois = new ObjectInputStream(fis);
                loadedObject = (Survey) ois.readObject();
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

            // Updating the flag of the loaded survey object through a setter method after loading it
            loadedObject.setIsLoaded(Boolean.TRUE);
            return loadedObject;
        }
    }

    /**
     * Helper method to create a file. Used to create the base directory.
     */
    public static boolean createDirectory(String directoryPath){
        File dir = new File(directoryPath);
        if(!dir.exists())
            return dir.mkdirs();
        return dir.isDirectory();
    }

    /**
     * Store method which allows the users to serialize their survey object
     * contents into a file object. Can only be performed if the survey is
     * loaded first.
     * @param survey Survey object to be serialized or saved
     */
    public void store (Survey survey) {
        if (isLoaded()) {
            // Using helper method above to create the base directory folder
            createDirectory(dirName);
            // Specifying a naming convention for the saved file.
            // It includes the text "Survey" to allow for filtering
            // between loading of Survey, Test, or Response objects.
            String savePath = this.saveDirPath + dirName + " - " + this.getName();
            FileOutputStream fos = null;
            ObjectOutputStream oos = null;
            try {
                fos = new FileOutputStream(savePath);
                oos = new ObjectOutputStream(fos);
                oos.writeObject(survey);
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
        }
        else {
            System.out.println("You must have a " + dirName.toLowerCase() + " loaded in order to save it.");
        }
    }

    /**
     * Take Survey method which allows the users to take actions with
     * already created responses to the survey or fill out a new response
     * to the loaded survey appropriately. Can only be performed if the survey is
     * loaded first.
     * @param survey Survey object in current use (that is loaded)
     */
    public void takeSurveyMenu(Survey survey) {
        if(this.isLoaded()) {
            System.out.println("------------------------------------");
            System.out.println("Taking " + dirName + " Menu");
            System.out.println("1) Fill out a new response to the current " + dirName.toLowerCase());
            System.out.println("2) Display filled " + dirName.toLowerCase() + " with response");
            System.out.println("3) Load a response file");
            System.out.println("4) Modify the current loaded response");
            System.out.println("5) Return to previous menu");

            String userinput = reader.nextLine();

            while (!(inputHandler.isIntInRange(userinput, 5))) {
                userinput = reader.nextLine();
            }

            int user_input = Integer.parseInt(userinput);

            // Fill out a new response to the loaded survey, set response file name to be saved
            if (user_input == 1) {
                // Instantiate a new response object to record a new response
                response = new Response();
                response.setResponseName();
                System.out.println("------------------------------------");
                System.out.println("Taking " + dirName.toLowerCase() + ": " + survey.getName());
                // Add a new response to the loaded or current working survey
                response.addNewResponse(survey);
                // Store the survey after adding a response since responses need to be accessed
                // for the tabulation of this instance of the survey.
                survey.store(survey);
                takeSurveyMenu(survey);
            }

            // Display the loaded response file in the given survey.
            else if (user_input == 2) {
                response.displayResponse(survey);
                takeSurveyMenu(survey);
            }
            // Load a previously saved response object.
            // Responses available to the user to load a
            // response is filtered based on the working survey
            else if (user_input == 3) {
                response = response.loadResponse(survey);
                takeSurveyMenu(survey);
            }
            // Modify the working response object
            // Automatically saves after modifying
            else if (user_input == 4) {
                response.modifyResponse(survey.getQuestions());
                response.storeResponse(survey, response);
                takeSurveyMenu(survey);
            }
            else if (user_input == 5) {
                displayMenu();
            }
        }
        else {
            System.out.println("Please load a " + dirName.toLowerCase() + " first before accessing its responses");
            displayMenu();
        }
    }

    /**
     * Modify method which allows the users to modify attributes of
     * pre-existing questions in the survey only if the survey is loaded.
     */
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
            // Using the Questions ArrayList to get the desired question object to alter
            Question question_to_modify = this.getQuestions().get(question_num - 1);
            question_to_modify.modifyQuestion();
        }
        else {
            System.out.println("You must have a survey loaded in order to modify it.");
        }
    }

    /**
     * A method which is used to load all the response files associated
     * with the current working survey. Adds all the loaded response
     * objects to an ArrayList of Response objects that is used later
     * for tabulation processing.
     * @param survey Survey object in current use (that is loaded)
     */
    public void loadFiles(Survey survey) {
        ArrayList<File> files = new ArrayList<>();
        // Clearing any other response objects that have been previously added
        // to the ArrayList. Addresses the case where a response is modified
        responses.clear();

        File surveyFolder = new File(survey.saveDirPath);
        if(!surveyFolder.exists()) {
            System.out.println("No responses to load from the specified path");
            System.out.println("Redirecting to the main menu");
            survey.takeSurveyMenu(survey);
        }
        else {
            File[] listOfResponses = surveyFolder.listFiles();
            for (File surveyFile : listOfResponses) {
                String name = surveyFile.getName();
                if (surveyFile.isFile() & name.contains(survey.getName() + "Response")) {
                    files.add(surveyFile);
                }
            }

            if (files.size() == 0) {
                System.out.println("No responses are created for this " + survey.dirName + " yet. Feel free to start one!");
                System.out.println("Redirecting to the main menu");
                survey.displayMenu();
            }

            for (File resp_obj : files) {
                String updatedPath = survey.saveDirPath + resp_obj.getName();
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
                // Adding the loaded response to array of responses
                survey.responses.add(loadedResponse);
            }
        }
    }

    /**
     * A method which is used to tabulate all the response files associated
     * with the current working survey. Uses the above method to populate
     * the ArrayList of deserialized response objects.
     * @param survey Survey object in current use (that is loaded)
     */
    public void tabulate(Survey survey) {
        if (isLoaded()) {
            System.out.println("------------------------------------");
            System.out.println("Displaying tabulated results for: " + this.getName());
            int size = survey.response.getQuestionsResponsesArray().size();
            // ArrayList of the HashMap that will consist of all the question HashMaps tabulated
            ArrayList<Map> tabulated = new ArrayList<>();

            // Accessing the source folder to get updated response objects prior to tabulation
            survey.loadFiles(survey);

            for (int ques = 1; ques < (size + 1); ques++) {
                // HashMap to map each answer (key) for the question to a tally value
                Map<ArrayList<String>, Integer> questionTabulated = new HashMap<>();
                Question question = survey.getQuestions().get(ques - 1);

                // Checking the question type. If it is a MCQ or TF, then add all the available choices marked as 0 tally.
                if (((question.getQuestionType() == "MultipleChoice") && (question.getNumOfResponses() == 1)) || (question.getQuestionType() == "TrueFalse")) {
                    for (Character alphabet : question.getAlphabetChoices()) {
                        ArrayList<String> alphabetArr = new ArrayList<>(Arrays.asList(alphabet.toString()));
                        questionTabulated.put(alphabetArr, 0);
                    }
                }

                // Access the response objects in the ArrayList of Responses
                for (Response response : survey.responses) {
                    // Get the response associated with a specific question
                    ArrayList<String> answer = response.getResponseArray(ques);
                    // Updating the tally if the response is already a part of the HashMap key.
                    if (questionTabulated.containsKey(answer)) {
                        questionTabulated.put(answer, (questionTabulated.get(answer) + 1));
                    } else {
                        // Adding the new response with its tally to the HashMap
                        questionTabulated.put(answer, 1);
                    }
                }
                // Add the question-based HashMap to the ArrayList that represents the
                // collective survey-based tabulation object
                tabulated.add(questionTabulated);
            }

            // Displaying each of the questions in the survey and their associated tabulation
            for (Question question : this.getQuestions()) {
                System.out.println();
                int ques_num = question.getQuestionNum();
                // Displaying the prompt
                System.out.println(ques_num + ") " + question.getPrompt());
                int index = question.getQuestionNum() - 1;

                // Get the HashMap associated with the question that consists of the tally of responses given
                Map<ArrayList<String>, Integer> questionMap = tabulated.get(index);

                for (ArrayList<String> resp : questionMap.keySet()) {
                    // Obtaining the tally count for the given answer
                    Integer tally = questionMap.get(resp);
                    // Handles MCQ, TF outputs differently as opposed to others.
                    if ((question.getQuestionType() == "MultipleChoice") || (question.getQuestionType() == "TrueFalse")) {
                        if (question.getNumOfResponses() == 1) {
                            for (String resp_txt : resp) {
                                System.out.println(resp_txt + ": " + tally);
                            }
                        }
                        else {
                            // Takes care of displaying tabulation for MCQ questions allowing multiple responses
                            String concatenated = new String();
                            for (String resp_txt : resp) {
                                concatenated += resp_txt;
                            }
                            System.out.println(concatenated + ": " + tally);
                        }
                    }
                    else if ((question.getQuestionType() == "ShortAnswer")) {
                        for (String resp_txt : resp) {
                            System.out.println(resp_txt + " " + tally);
                        }
                    }
                    else if ((question.getQuestionType() == "Essay")) {
                        for (String resp_txt : resp) {
                            System.out.println(resp_txt);
                        }
                        System.out.println();
                    }
                    else if ((question.getQuestionType() == "Date")) {
                        for (String resp_txt : resp) {
                            System.out.println(resp_txt);
                        }
                        System.out.println(tally);
                        System.out.println();
                    }
                    else {
                        System.out.println(tally);
                        for (String resp_txt : resp) {
                            System.out.println(resp_txt);
                        }
                        System.out.println();
                    }
                }
            }
        }
        else {
            System.out.println("Please load a " + dirName.toLowerCase() + " first before accessing its tabulation");
        }
    }

    public void setName() {
        System.out.println("Please indicate the name:");
        String name = reader.nextLine();
        while (name.trim().isEmpty()) {
            System.out.println("Name field cannot be blank");
            System.out.println("Please indicate the name:");
            name = reader.nextLine();
        }
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Question> getQuestions() {
        return this.questions;
    }

    public void setQuestions(Question question) {
        this.getQuestions().add(question);
    }

    public void setIsLoaded(Boolean boolVal) {
        this.isLoaded = boolVal;
    }
}