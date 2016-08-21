import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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

    NewQuiz(String wordsFile, boolean isReview) {
        logic = new QuizLogic(wordsFile);
        _isReview = isReview;
    }

    public void setUp() {
        _sceneWidth = 300;
        _sceneHeight = 300;

        //Block user interaction with other windows until this window is
        // dealt with
        window.initModality(Modality.APPLICATION_MODAL);
        //Logic for if this is a review quiz
        if(_isReview) {
            window.setTitle("Review");
        } else {
            window.setTitle("Spelling Quiz");
        }

        window.setMinWidth(250);
    }

    public void display() {
        window = new Stage();
        setUp();
        buildScenes();

        //Window
        if(logic._hasWords) {
            window.setScene(startScene);
        } else {
            window.setScene(noWordsScene);
        }
        //Needs to be closed before returning
        window.showAndWait();
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
        input = new TextField();
        input.setPromptText("Spell word here");
        Button checkButton = new Button ("Check");
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
        VBox layout2 = new VBox(2);
        layout2.getChildren().addAll(sayButton, input, checkButton);

        //Logic for if this is a review quiz
        if(_isReview) {
            Button spellButton = new Button("Spell Out Word");
            spellButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    logic.spellOutWord();
                }
            });
            layout2.getChildren().addAll(spellButton);
        }
        layout2.setAlignment(Pos.CENTER);

        spellWordScene = new Scene(layout2, _sceneWidth, _sceneHeight);



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
        System.out.println("Quiz started");
        if(logic._hasWords) {
            newQuestion();
        } else {
            displayNoWords();
        }
    }

    private void newQuestion() {
        logic.isSecondAttempt = false;
        logic.nextWord();
        window.setScene(spellWordScene);
        System.out.println("Started a new Question");
    }

    private void failed() {
        System.out.println("Word has been answered incorrectly");
        if(logic.isSecondAttempt) {
            if (logic.isLastAttempt) {
                System.out.println("last logic stuff");
            } else {
                window.setScene(failedScene);
            }
        } else {
            logic.isSecondAttempt = true;
            window.setScene(spellWordScene);
        }

    }


    private void checkAnswer(String attempt){
        System.out.println("checked");
        boolean isCorrect = logic.checkAnswer(attempt);
        System.out.println("Is the attempt correct? " + isCorrect);
        if(isCorrect) {
            displayCorrectScene();
        } else {
            displayIncorrectScene();
        }
    }

    private void displayIncorrectScene(){
        System.out.println("is this the second attempt? " + logic.isSecondAttempt);
        if(logic.isSecondAttempt) {
            Scene scene = buildFailedScene(logic.isLastAttempt);
            window.setScene(scene);
        } else {
            window.setScene(faultedScene);
        }
    }

    private void displayCorrectScene(){

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


    public void finish() {
        System.out.println("Close window");
        window.close();
    }
    //public Scene buildFaultedScene() {}


}
