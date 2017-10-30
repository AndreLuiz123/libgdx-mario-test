package br.ufjf.dcc.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import br.ufjf.dcc.Scenes.Hud;
import br.ufjf.dcc.TesteAndre;

/**
 * Created by Andre Luiz on 21/10/2017.
 */

public class Brick extends InteractiveTiledObject{

    public Brick(World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(TesteAndre.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick","Collision");
        setCategoryFilter(TesteAndre.DESTROYED_BIT);
        getCell().setTile(null);
        Hud.addScore(200);
    }


}
