package br.ufjf.dcc;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import br.ufjf.dcc.Screens.PlayScreen;


public class TesteAndre extends Game {
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	public static final float PPM = 100;
	public static  final short DEFAULT_BIT=1;
	public static final short MARIO_BIT=2;
	public static final short BRICK_BIT=4;
	public static final short COIN_BIT=8;
	public static final short DESTROYED_BIT=16;

	public SpriteBatch batch;
	Texture img;
	int x = 0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
//		img.dispose();
	}
}
