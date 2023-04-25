package com.example.tictactoe;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ticTacToe extends Application {

    private final Button[][] buttons = new Button[3][3];

    private Label playerXScoreLabel , playerOScoreLabel;   //Global variables, we can edit them anywhere

    private int playerXScore = 0, playerOScore = 0;

    private boolean playerXTurn = true;  //Initially turn will be of X as I have set it true

    private BorderPane createContent(){
        BorderPane root = new BorderPane();

        root.setPadding(new Insets(10,20,10,20)); //padding for Top,right,bottom and left
        //TITLE
        Label titleLabel = new Label("Tic Tac Toe");  //so now label is created and now we need to add it to our root
        //label should be on top so we will use"setTop" to add it on top
        root.setTop(titleLabel);
        // BorderPane.setAlignment(titleLabel,Pos.CENTER);
        //Now, we'll put some style to this style to make it look better
        //we'll use "setStyle"
        titleLabel.setStyle("-fx-font-size : 35 pt; -fx-font-weight : bold;"); //this is syntax inside of styling

        //GAME BOARD
        GridPane gridPane = new GridPane(); // we've created a GridPane
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5); //giving horizontal gaps
        gridPane.setVgap(5); //giving vertical gaps
        //And in this GridPane we want to add my buttons
        //we'll take loop for 3*3 grid
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button();
                button.setPrefSize(100,100);  // to set the size of buttons
                button.setStyle("-fx-font-size : 24pt; -fx-font-weight : bold;");
                button.setOnAction(event->buttonClicked(button));  //method to put the events to be performed on button
                //now add this Button to the gridPane
                buttons[i][j] = button; //as we have created 2D array of buttons so i need to add my buttons here as well
                //so till here i've created a button and then add it in my grid and now we will add actual buttons to it
                gridPane.add(button,j,i); //add button on ith row and jth column
            }
        }
        //Now we need to add this grid to our UI
        root.setCenter(gridPane);
        // BorderPane.setAlignment(gridPane,Pos.CENTER);

        //SCORE
        //here we need tpo constantly update the score as a persn wins so for tht we'll create a label outside
        HBox scoreBoard = new HBox(20); //using Hbox for score. Hbox arranges nodes in a single row
        scoreBoard.setAlignment(Pos.CENTER);
        //Now we'll create these player objects
        playerXScoreLabel = new Label("Player X : 0");
        //using style for this as we did above
        playerXScoreLabel.setStyle("-fx-font-size : 16pt; -fx-font-weight : bold;");
        playerOScoreLabel = new Label("Player O : 0");
        //same style
        playerOScoreLabel.setStyle("-fx-font-size : 16pt; -fx-font-weight : bold;");
        //Now we need to add these to our HBox
        scoreBoard.getChildren().addAll(playerXScoreLabel,playerOScoreLabel);
        //now adding this scoreboard to our root // now this scoreboard we want should be at the bottem, so use "setBottom"
        root.setBottom(scoreBoard);
        return root;
    }

    private void buttonClicked(Button button){  //This is an event or functionality when button is clicked
        //writing logic to make sure we r putting 'x' or 'o'
        //if there is already some text on the button and we r still clicking in it so for tht we need to check first
        //if the button is empty then only we will perform our action else return
        if(button.getText().equals("")){
            if(playerXTurn){
                button.setText("X");
            }
            else{
                button.setText("O");
            }
            //Now change the turn variable
            //making X turn as false we will revert it if false then true and if true then false
            playerXTurn = !playerXTurn;

            checkWinner(); //On every click we'll check for winner
        }
        return;
        //Also I want this func to be called on click of every button
    }

    private void checkWinner(){ //There are 3 conditions to win
        //ROW
        //First we'll check for rows whether our button text is same or not, so iterating over row and corresponding column
        for (int row = 0; row < 3; row++) {
            if(   buttons[row][0].getText().equals(buttons[row][1].getText()) //checking 1st and 2nd column
                    && buttons[row][1].getText().equals(buttons[row][2].getText())//checking 2nd and 3rd column
                    && !buttons[row][0].getText().isEmpty()  //also if it blank it'll be same to avoid that we check this
            ) {
                //if all dis cndition met, then here we'll have a winner
                String winner = buttons[row][0].getText();  //this wins
                showWinnerDialog(winner);
                updateScore(winner);
                resetBoard();
                return;  //as this is completed so need to check everything else
            }
        }

        //COLUMN
        for (int col = 0; col < 3; col++) {
            if(   buttons[0][col].getText().equals(buttons[1][col].getText()) //checking 1st and 2nd column
                    && buttons[1][col].getText().equals(buttons[2][col].getText())//checking 2nd and 3rd column
                    && !buttons[0][col].getText().isEmpty()//also if it blank it'll be same,so to avoid that we check this
            ) {
                //if all this condition met, then here we'll have a winner
                String winner = buttons[0][col].getText();  //this wins
                showWinnerDialog(winner);
                updateScore(winner);
                resetBoard();
                return;
            }
        }

        //DIAGONAL
        //Now first diagonal we'll check i.e (0,0),(1,1),(2,2) if someone is winner or not
        if(   buttons[0][0].getText().equals(buttons[1][1].getText())
                && buttons[1][1].getText().equals(buttons[2][2].getText())
                && !buttons[0][0].getText().isEmpty()//also if it blank it'll be same,so to avoid that we check this

        ) {
            //if all this condition met, then here we'll have a winner
            String winner = buttons[0][0].getText();  //this wins
            showWinnerDialog(winner);
            updateScore(winner);
            resetBoard();
            return;
        }
        //Now here second diagonal also we'll check i.e (0,2),(1,1),(2,0)
        if(   buttons[2][0].getText().equals(buttons[1][1].getText())
                && buttons[1][1].getText().equals(buttons[0][2].getText())
                && !buttons[2][0].getText().isEmpty()//also if it blank it'll be same,so to avoid that we check this

        ) {
            //if all this condition met, then here we'll have a winner
            //System.out.println("Winner");
            String winner = buttons[2][0].getText();  //this wins
            showWinnerDialog(winner);
            updateScore(winner);
            resetBoard();
            return;
        }
        //TIE CONDITION
        boolean tie = true;
        for(Button row[] : buttons){
            for(Button button : row){
                if(button.getText().isEmpty()){ //if any value is empty then the tie will be false
                    tie = false;
                    break;  //break from loop
                }
            }
        }
        //Now if here tie is true here then announce "Tie" and reset the board
        if(tie){
            showTieDialog(); //Announcing tie
            resetBoard(); // resetting the board
        }

    }


    private void showWinnerDialog(String winner){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Winner");
        alert.setContentText("Congratulations "+winner+"! You won the game");
        alert.setHeaderText("");
        alert.showAndWait(); //it displays the text on screen
    }

    private void showTieDialog(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tie");
        alert.setContentText("Game Over !  It's a Tie.");
        alert.setHeaderText("");
        alert.showAndWait(); //it displays the text on screen
    }

    private void updateScore(String winner){
        if(winner.equals("X")){   //if X is winner
            playerXScore++;  //increment X score by 1
            playerXScoreLabel.setText("Player X: "+ playerXScore); //update the score
        }
        else{   //if O is winner
            playerOScore++;  //increment O score by 1
            playerOScoreLabel.setText("Player O: "+ playerOScore); //update the score
        }
    }

    private void resetBoard(){
        for(Button row[] : buttons){
            for(Button button : row){
                button.setText("");
            }
        }
    }

    @Override
    public void start(Stage stage) throws IOException {

        Scene scene = new Scene(createContent()); // here we have passed self made createContent() fn here instead of
        stage.setTitle("TIC TAC TOE");           //boiler and we'll bind all our UI controls from there and createContent
        stage.setScene(scene);                    // is  starting point of our application
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}