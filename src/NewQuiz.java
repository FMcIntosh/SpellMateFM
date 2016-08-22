import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by Fraser McIntosh on 18/08/2016.
 */
public class NewQuiz {
    private Stage window;
    private Scene startScene, spellWordScene, faultedScene, failedScene, noWordsScene;
    private int _sceneWidth, _sceneHeight;
    private boolean _isReview;
    private QuizLogic logic;
    public TextField input;
    private Label wordCountLabel;

    NewQuiz(String wordsFile, boolean isReview, Stage window) {
        logic = new QuizLogic(wordsFile);
        _isReview = isReview;
        this.window= window;
    }

    public void setUp() {
        _sceneWidth = 500;
        _sceneHeight = 500;
    }

    public void display() {
        setUp();
        buildScenes();

        //Window
        if(logic._hasWords) {
            window.setScene(startScene);
        } else {
            window.setScene(noWordsScene);
        }
    }

    private void buildScenes() {
        // ----------------------------------------------- Start quiz scene
        //Components
        //Change this for review
        Label label = new Label("Spelling quiz");
        //Logic for if this is a review quiz
        if(_isReview){
            label = new Label("Review");
        }
        Button startButton = new Button("Start Quiz");
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startQuiz();
            }
        });

        //Layout
        VBox layout1 = new VBox(10);
        layout1.getChildren().addAll(label, startButton);
        layout1.setAlignment(Pos.CENTER);

        //Scene
        startScene = new Scene(layout1, _sceneWidth, _sceneHeight);

        // ----------------------------------------------- New quiz scene
        //Components 2
        Label label1 = new Label("New Spelling Quiz");
        wordCountLabel = new Label("Enter Word " + (logic._currentWordNumber + 1) +" of " + logic._numWordsInQuiz);
        input = new TextField();
        input.setPromptText("Spell word here");
        Button checkButton = new Button ("Submit");
        checkButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                checkAnswer(input.getText());
            }
        });

        Button sayButton = new Button ("Say Word");
        // Change this for review
        sayButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                logic.sayWord(logic._currentWord);
            }
        });
        //Layout
        HBox layout2 = new HBox();

        if(_isReview) {
            Button spellButton = new Button("Spell Out Word");
            spellButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    logic.spellOutWord();
                }
            });
            layout2.getChildren().addAll(spellButton);
            label1.setText("Review Quiz");
        }
        layout2.getChildren().addAll(sayButton, input, checkButton);

        //Logic for if this is a review quiz

        layout2.setAlignment(Pos.CENTER);
        VBox outerLayout = new VBox(10);
        outerLayout.setPadding(new Insets(30, 0, 0 , 0));
        outerLayout.getChildren().addAll(label1,wordCountLabel, layout2);
        outerLayout.setAlignment(Pos.CENTER);
        spellWordScene = new Scene(outerLayout, _sceneWidth, _sceneHeight);



        // ----------------------------------------------- Incorrect First scene
        //Components
        Label label4 = new Label("Incorrect");
        Button againButton = new Button("Try Again");
        againButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.setScene(spellWordScene);
            }
        });
        //Layout
        VBox layout4 = new VBox(10);
        layout4.getChildren().addAll(label4, againButton);
        layout4.setAlignment(Pos.CENTER);

        faultedScene = new Scene(layout4, _sceneWidth, _sceneHeight);

        // ----------------------------------------------- noWords scene
        //Components

        againButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                failed();
            }
        });

        Button nextButton = new Button("Next Question");
        nextButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                newQuestion();
            }
        });
        //Layout
        VBox layout5 = new VBox(10);
        layout5.getChildren().addAll(label4, nextButton);
        layout5.setAlignment(Pos.CENTER);

        failedScene = new Scene(layout5, _sceneWidth, _sceneHeight);

        // ----------------------------------------------- noWords scene
        //Components

        Label label6 = new Label("There are no words to be quizzed on!");
        Button okButton = new Button("OK");
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                finish();
            }
        });

        //Layout
        VBox layout7 = new VBox(10);
        layout7.getChildren().addAll(label6, okButton);
        layout7.setAlignment(Pos.CENTER);

        noWordsScene = new Scene(layout7, _sceneWidth, _sceneHeight);
    }


    private void startQuiz() {
        if(logic._hasWords) {
            newQuestion();
        } else {
            displayNoWords();
        }
    }

    private void newQuestion() {
        logic.isSecondAttempt = false;
        logic.nextWord();
        wordCountLabel.setText("Enter Word " + logic._currentWordNumber +" of " + logic._numWordsInQuiz);
        window.setScene(spellWordScene);
    }

    private void failed() {
        if(logic.isSecondAttempt) {
            if (logic.isLastAttempt) {
            } else {
                window.setScene(failedScene);
            }
        } else {
            logic.isSecondAttempt = true;
            window.setScene(spellWordScene);
        }

    }


    private void checkAnswer(String attempt){
        //allow for uppercase
        attempt = attempt.toLowerCase();
        input.clear();
        input.setPromptText("Spell word here");
        //Validate
        if(!attempt.matches("[a-zA-Z]+")){
            displayInvalidInput();
        }  else {
            boolean isCorrect = logic.checkAnswer(attempt);
            if (isCorrect) {
                displayCorrectScene();
            } else {
                displayIncorrectScene();
            }
        }
    }

    private void displayIncorrectScene(){
        logic.sayWord("Incorrect");
        if(logic.isSecondAttempt) {
            Scene scene = buildFailedScene(logic.isLastAttempt);
            window.setScene(scene);
        } else {
            window.setScene(faultedScene);
        }
    }

    private void displayCorrectScene(){
        logic.sayWord("Correct");

        //Scene
        Scene scene = buildCorrectScene(logic.isLastAttempt);
        window.setScene(scene);
    }

    private void displayNoWords() {
        window.setScene(noWordsScene);
    }

    public Scene buildCorrectScene(boolean isLastAttempt) {
        //Components
        Label label3 = new Label("Correct");

        Button nextButton = new Button();
        if(isLastAttempt) {
            nextButton.setText("Finish");
            nextButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    finish();
                }
            });
        } else {
            nextButton.setText("Next Question");
            nextButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    newQuestion();
                }
            });
        }
        //Layout
        VBox layout3 = new VBox(10);
        layout3.getChildren().addAll(label3, nextButton);
        layout3.setAlignment(Pos.CENTER);

        return new Scene(layout3, _sceneWidth, _sceneHeight);
    }

    public Scene buildFailedScene(boolean isLastAttempt) {
        //Components
        Label label3 = new Label("Incorrect");

        Button nextButton = new Button();
        if(isLastAttempt) {
            nextButton.setText("Finish");
            nextButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    finish();
                }
            });
        } else {
            nextButton.setText("Next Question");
            nextButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    newQuestion();
                }
            });
        }
        //Layout
        VBox layout3 = new VBox(10);
        layout3.getChildren().addAll(label3, nextButton);
        layout3.setAlignment(Pos.CENTER);

        return new Scene(layout3, _sceneWidth, _sceneHeight);
        // faultedScene;
    }

    public void displayInvalidInput() {
        new InvalidInput().display();
    }

    public void finish() {
        Main.setMenu();
    }


}