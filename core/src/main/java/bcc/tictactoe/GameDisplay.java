package bcc.tictactoe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class GameDisplay extends ScreenAdapter {
    private final TicTacToe game;
    private Stage stage;
    private Skin skin;

    //up to you to modify these if you'd like!
    private final float BOARD_X = 90;
    private final float BOARD_Y = 70;
    private final float BOARD_WIDTH = 300;
    private final float BOARD_HEIGHT = 300;
    Table boardTable;


    private boolean gameOver = false;
    private Container<Label> resultLabel;
    private TextButton playAgainButton;
    private Container<Label> curPlayerDisplay;
    private Container<Label> whosPlaying;
    private Container<Label> player1Record;
    private Container<Label> player2Record;

    


    public GameDisplay(TicTacToe game) {
        //set up the screen you you like
        this.game = game;
        skin = new Skin(Gdx.files.internal("skins/glassy/glassy-ui.json"));
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        

        Texture backgroundTexture = new Texture(Gdx.files.internal("space_tictactoe.png"));
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        player1Record = Constants.createLabelWithBackgrounColor(game.getPlayer1() + "(" + Mark.X + "): " + game.getPlayer1().getRecord(), Color.FIREBRICK, skin);
        player1Record.setPosition(200, 440);
        player1Record.pack();
        stage.addActor(player1Record);

        player2Record = Constants.createLabelWithBackgrounColor(game.getPlayer2() + "(" + Mark.O + "): " + game.getPlayer2().getRecord(), Color.FIREBRICK, skin);
        player2Record.setPosition(200, 400);
        player2Record.pack();
        stage.addActor(player2Record);

        resultLabel = Constants.createLabelWithBackgrounColor("Who's Gonna Win?", Color.FIREBRICK, skin);
        resultLabel.setPosition(100, 20);
        resultLabel.pack();
        stage.addActor(resultLabel);

        game.resetCurPlayer();
        curPlayerDisplay = Constants.createLabelWithBackgrounColor("Current Player: " + game.getCurPlayerMark(), Color.FIREBRICK, skin);
        curPlayerDisplay.setPosition(0, 440);
        curPlayerDisplay.pack();
        stage.addActor(curPlayerDisplay);

        whosPlaying = Constants.createLabelWithBackgrounColor(game.getCurPlayerObj() + ": " + Mark.X + " vs " + game.getPlayer2() + ": " + Mark.O, Color.FIREBRICK, skin);
        whosPlaying.setPosition(0, 360);
        whosPlaying.pack();
        stage.addActor(whosPlaying);

        game.setBoardState(new Board());
        initTableDisplay();
        updateBoardDisplay();
    }

    public void initTableDisplay() {// initializes tic tac toe board - no changes needed 
        boardTable = new Table();
        boardTable.setPosition(BOARD_X, BOARD_Y);
        boardTable.setSize(BOARD_WIDTH, BOARD_HEIGHT);
        Board board = new Board();
        game.setBoardState(board);
        // Set the background image.
        Texture backgroundTexture = new Texture(Gdx.files.internal("tictactoe_board.png"));
        TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(new TextureRegion(backgroundTexture));
        boardTable.setBackground(backgroundDrawable);

            
            

       
        // Force the table to layout from the top (so that row 0 appears at the top).
        boardTable.top();

        boardTable.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float clickX, float clickY) {
                // The click coordinates (clickX, clickY) are relative to the table's
                // bottom-left.
                float cellWidth = BOARD_WIDTH / 3;
                float cellHeight = BOARD_HEIGHT / 3;

                int col = (int) (clickX / cellWidth);
                int rowFromBottom = (int) (clickY / cellHeight);
                // Convert the y-coordinate to a row index where row 0 is at the top.
                int row = 2 - rowFromBottom;

                // Call your board click handler.
                handleBoardClick(row, col);
            }
        });
        stage.addActor(boardTable);
    }

    
    public void handleBoardClick(int row, int col) {
        
        //checkpoint 2
        //this position was clicked, play the move, then call handle move made
        if(game.getCurPlayer() == 0 && game.getBoardState().makeMove(row,col,Mark.X) == true) {
            game.getBoardState().makeMove(row, col, Mark.X);
            game.setCurPlayer(1);
            handleMoveMade();
            curPlayerDisplay.getActor().setText("Current Player: " + game.getCurPlayerMark());
        }
        else if (game.getCurPlayer() == 1 && game.getBoardState().makeMove(row,col,Mark.O) == true){
            game.getBoardState().makeMove(row, col, Mark.O); 
            game.setCurPlayer(0);
            handleMoveMade();
            curPlayerDisplay.getActor().setText("Current Player: " + game.getCurPlayerMark());
        }
        updateBoardDisplay();
    }

    public void handleMoveMade(){//checkpoint 2
        //call updateBoardDisplay
        updateBoardDisplay();

        //checkpoint 3 modification
        //if game is simulated, instead of having a popup by calling 
        //showresult, start the next game if we have not run all the simulations

        if(game.getIsSimulated() == true && game.getRound() < game.getNumberOfRounds()) {
            if(game.getBoardState().checkWin() == Mark.TIE) {
                game.getPlayer1().incrementTies();
                game.getPlayer2().incrementTies();
                player1Record.getActor().setText(game.getPlayer1() + "(" + Mark.X + "): " + game.getPlayer1().getRecord());
                player2Record.getActor().setText(game.getPlayer2() + "(" + Mark.O + "): " + game.getPlayer2().getRecord());
                game.incrementRound();
                resetGame();
            }

            if(game.getBoardState().checkWin() == Mark.X) {
                game.getPlayer1().incrementWins();
                game.getPlayer2().incrementLosses();
                player1Record.getActor().setText(game.getPlayer1() + "(" + Mark.X + "): " + game.getPlayer1().getRecord());
                player2Record.getActor().setText(game.getPlayer2() + "(" + Mark.O + "): " + game.getPlayer2().getRecord());
                game.incrementRound();
                resetGame();
            }

            if(game.getBoardState().checkWin() == Mark.O) {
                game.getPlayer1().incrementLosses();
                game.getPlayer2().incrementWins();
                player1Record.getActor().setText(game.getPlayer1() + "(" + Mark.X + "): " + game.getPlayer1().getRecord());
                player2Record.getActor().setText(game.getPlayer2() + "(" + Mark.O + "): " + game.getPlayer2().getRecord());
                game.incrementRound();
                resetGame();
            }
        }

        //check for a win or tie. If there is one, call showResult() with a message containing the winner, and update the player stats. 
       else {
        if(game.getBoardState().checkWin() == Mark.TIE) {
        game.getPlayer1().incrementTies();
        game.getPlayer2().incrementTies();
        player1Record.getActor().setText(game.getPlayer1() + "(" + Mark.X + "): " + game.getPlayer1().getRecord());
        player2Record.getActor().setText(game.getPlayer2() + "(" + Mark.O + "): " + game.getPlayer2().getRecord());
        showResult("Tie!");
       }

       if(game.getBoardState().checkWin() == Mark.X) {
        game.getPlayer1().incrementWins();
        game.getPlayer2().incrementLosses();
        player1Record.getActor().setText(game.getPlayer1() + "(" + Mark.X + "): " + game.getPlayer1().getRecord());
        player2Record.getActor().setText(game.getPlayer2() + "(" + Mark.O + "): " + game.getPlayer2().getRecord());
        showResult("X Wins!");
       }

       if(game.getBoardState().checkWin() == Mark.O) {
        game.getPlayer1().incrementLosses();
        game.getPlayer2().incrementWins();
        player1Record.getActor().setText(game.getPlayer1() + "(" + Mark.X + "): " + game.getPlayer1().getRecord());
        player2Record.getActor().setText(game.getPlayer2() + "(" + Mark.O + "): " + game.getPlayer2().getRecord());
        showResult("O Wins!");
       }
    }
        
        
    }

    private void showResult(String result) {
        // Create an overlay to show the result. Include a button to play again. 
        gameOver = true;
        resultLabel.getActor().setText(result);
        // when the button is clicked, it should dissappear - you can do this using the .remove() command. 
        playAgainButton = new TextButton("Play Again!",skin);
        playAgainButton.setPosition(100, 100);
        stage.addActor(playAgainButton);
        playAgainButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playAgainButton.remove();
                resetGame();
            }
        });
    }
    public void resetGame() {
        //update board state, current player, etc. 
        //System.out.println(game.getRound());
        game.getBoardState().reset();
        updateBoardDisplay();
        game.setCurPlayer(0);
        resultLabel.getActor().setText("Who's Gonna Win?");
        curPlayerDisplay.getActor().setText("Current Player: " + game.getCurPlayerMark());
        gameOver = false;
        System.out.println(game.getCurPlayerMark());
    }

    public void updateBoardDisplay() {//updates the board, you should call this if a move is made. No need to change. 
        boardTable.clearChildren();
        Mark[][] grid = game.getBoardState().getGrid();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Mark mark = grid[row][col];
                String text = "";
                if (mark == Mark.X) {
                    text = "X";
                } else if (mark == Mark.O) {
                    text = "O";
                }
                Label cellLabel = new Label(text, skin);
                cellLabel.setAlignment(Align.center);     // Center text within the label.
                cellLabel.setFontScale(5f); 
                boardTable.add(cellLabel).width(BOARD_WIDTH / 3).height(BOARD_HEIGHT / 3);
            }
            boardTable.row();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();

        //checkpoint 3 - if it is not a humans turn, automate the AI's move here
        
        if(!(gameOver)) {
            if(!(game.getCurPlayerObj() instanceof Human))  {
            game.getBoardState().makeMove(game.getCurPlayerObj().makeMove(game.getBoardState(),game.getCurPlayerMark()), game.getCurPlayerMark());
            handleMoveMade();
            game.nextPlayer();

            //game.getCurPlayerObj().makeMove(game.getBoardState(),game.getCurPlayerMark()), game.getCurPlayerMark()
        }
    }
        //call handleMoveMade afterwards
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}