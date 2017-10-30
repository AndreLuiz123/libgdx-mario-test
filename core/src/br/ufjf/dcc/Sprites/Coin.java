package br.ufjf.dcc.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import br.ufjf.dcc.Scenes.Hud;
import br.ufjf.dcc.TesteAndre;

/**
 * Created by Andre Luiz on 21/10/2017.
 */

public class Coin extends InteractiveTiledObject{

    private TiledMapTileSet tileSet;
    private final int BLANK_COIN = 28;

    public Coin(World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds);
        tileSet = map.getTileSets().getTileSet("NES - Super Mario Bros - Tileset");
        fixture.setUserData(this);
        setCategoryFilter(TesteAndre.COIN_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Coin","Collision");
        getCell().setTile(tileSet.getTile(BLANK_COIN));
        //Tentei fazer o mario ganhar ponto apenas uma vez quando acerta um objeto do tipo coin, mas n consegui. Tenho uma ideia de ocmo fazer isso, mas é uma gambiarra
        //if(getCell().getTile()==tileSet.getTile(BLANK_COIN))
        Hud.addScore(100);
    }


}
