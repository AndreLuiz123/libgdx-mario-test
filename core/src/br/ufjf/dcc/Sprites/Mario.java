package br.ufjf.dcc.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
//Não sei como essa biblioteca foi parar ai, mas aparentemente ela nao existe e nao esta permitindo o jogo rodar. Quando eu retiro ela o jogo funciona como deveria
//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import javax.xml.soap.SAAJResult;

import br.ufjf.dcc.Screens.PlayScreen;
import br.ufjf.dcc.TesteAndre;

/**
 * Created by Andre Luiz on 14/10/2017.
 */

public class Mario extends Sprite {
    public enum State {FALLING, JUMPING, STANDING, RUNNING};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2Body;
    private TextureRegion marioStand;
    private Animation<TextureRegion> marioRun;
    private Animation<TextureRegion> marioJump;
    private float stateTimer;
    private boolean runningRight;

    public Mario(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = world;
        currentState=State.STANDING;
        previousState=State.STANDING;
        stateTimer=0;
        runningRight=true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i=1; i<4; i++)
            frames.add(new TextureRegion(getTexture(),i*16,0,16,16));
        marioRun=new Animation<TextureRegion>(0.1f,frames);
        frames.clear();

        for(int i=4; i<6; i++)
            frames.add(new TextureRegion(getTexture(),i*16,0,16,16));
        marioJump=new Animation<TextureRegion>(0.1f,frames);

        defineMario();

        marioStand=new TextureRegion(getTexture(),0,0,16,16);
        setBounds(0,0,16/TesteAndre.PPM,16/TesteAndre.PPM);
        setRegion(marioStand);
    }

        public  void update(float dt){
            setPosition(b2Body.getPosition().x - getWidth()/2, b2Body.getPosition().y - getHeight()/2);
            setRegion(getFrame(dt));
        }

        public TextureRegion getFrame(float dt){
            currentState=getState();

            TextureRegion region;

            switch (currentState){
                case JUMPING:
                    //no tutorial não precisa de colocar o "(Texture Region)", por que isso é exigido aqui?
                    region = (TextureRegion) marioJump.getKeyFrame(stateTimer);
                    break;
                case RUNNING:
                    region = (TextureRegion) marioRun.getKeyFrame(stateTimer, true);
                    break;
                case FALLING:
                case STANDING:
                    default:
                        region = marioStand;
                        break;
            }

            if((b2Body.getLinearVelocity().x<0 || !runningRight)&& !region.isFlipX()){
                region.flip(true, false);
                runningRight=false;
            }else{
                if((b2Body.getLinearVelocity().x>0 || runningRight)&& region.isFlipX()){
                    region.flip(true,false);
                    runningRight=true;
                }

            }

            stateTimer = currentState == previousState ? stateTimer + dt : 0;
            previousState=currentState;

            return  region;

        }

        public State getState(){
            if(b2Body.getLinearVelocity().y>0|| (b2Body.getLinearVelocity().y<0 && previousState==State.JUMPING)){
                return State.JUMPING;
            }else
            if(b2Body.getLinearVelocity().y<0){
                return State.FALLING;
            }else
                if(b2Body.getLinearVelocity().x != 0)
                    return State.RUNNING;
            else
                return  State.STANDING;


        }



        public void defineMario(){
            BodyDef bdef = new BodyDef();
            bdef.position.set(32/ TesteAndre.PPM,32/ TesteAndre.PPM);
            bdef.type = BodyDef.BodyType.DynamicBody;
            b2Body= world.createBody(bdef);

            FixtureDef fdef= new FixtureDef();
            CircleShape shape = new CircleShape();
            shape.setRadius(5/ TesteAndre.PPM);
            fdef.filter.categoryBits = TesteAndre.MARIO_BIT;
            fdef.filter.maskBits = TesteAndre.DEFAULT_BIT|TesteAndre.COIN_BIT|TesteAndre.BRICK_BIT;

            fdef.shape=shape;
            b2Body.createFixture(fdef);

            EdgeShape head = new EdgeShape();

            head.set(new Vector2(-2/TesteAndre.PPM,7/TesteAndre.PPM),new Vector2(2/TesteAndre.PPM,7/TesteAndre.PPM));
            fdef.shape=head;
            fdef.isSensor=true;
            b2Body.createFixture(fdef).setUserData("head");
    }

    }


