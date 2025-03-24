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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PlayerSelectionScreen extends ScreenAdapter{
    private final TicTacToe game;
    private Stage stage;
    private Skin skin;

    public PlayerSelectionScreen(TicTacToe game, int curPlayer) {//checkpoint 1
        this.game = game;
        stage = new Stage();
        //load skin
        skin = new Skin(Gdx.files.internal("skins/glassy/glassy-ui.json"));
        
        Gdx.input.setInputProcessor(stage);
        //add title saying something like "select player"
       //if you would like a background color behind the title, you can use the helper method in the Constants file
       //check out the documentation linked in the readme / on canvas
       Container<Label> titleLabel = Constants.createLabelWithBackgrounColor("select player", Color.TEAL, skin);

       //add buttons to select from the player types listed in constants.java. If there isSimulated is true, don't let human be an option. 
      
       Table MyTable = new Table();
        MyTable.setFillParent(true);
        MyTable.center();
        MyTable.add(titleLabel).pad(10).row();

       if(game.getIsSimulated() == false) {
        TextButton humanButton = new TextButton("Human", skin);
        humanButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Player newPlayer = new Human();
                game.setPlayer(curPlayer, newPlayer);
            }
        });
        MyTable.add(humanButton).pad(8).row();
       } 

       TextButton randomAIButton = new TextButton("Random AI", skin);
       randomAIButton.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            Player newPlayer = new RandomAI();
            game.setPlayer(curPlayer, newPlayer);
        }
    });
    
       TextButton slightlySmartAIButton = new TextButton("Slightly Smart AI", skin);
       slightlySmartAIButton.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            Player newPlayer = new SlightlySmartAI();
            game.setPlayer(curPlayer, newPlayer);
        }
    });
       TextButton smartAIButton = new TextButton("Smart AI", skin);
       smartAIButton.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            Player newPlayer = new SmartAI();
                game.setPlayer(curPlayer, newPlayer);
        }
    });
        
    
        MyTable.add(randomAIButton).pad(8).row();
        MyTable.add(slightlySmartAIButton).pad(8).row();
        MyTable.add(smartAIButton).pad(8).row();

        stage.addActor(MyTable);
      
       //curplayer will either be 0 or 1
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}