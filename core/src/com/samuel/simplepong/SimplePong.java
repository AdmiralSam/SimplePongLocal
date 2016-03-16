package com.samuel.simplepong;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.LinkedList;
import java.util.Queue;

public class SimplePong extends ApplicationAdapter implements ReadyListener {
    public static Texture PongTexture;
    public static Texture ButtonTexture;
    private State currentState;
    private Button host, find, cancel;
    public static Paddle serverPaddle, clientPaddle;
    private Stage stage;
    private LatencyManager latencyManager;
    private PersonController person;
    private NetworkController network;
    public static Ball ball;

    private Button modeSwitch;
    public static boolean clientPredictionOn=false;

    public static float timestamp;
    public static Queue<ClientState> stateQueue;
    private boolean timeReady=false;

    @Override
    public void dispose() {
        super.dispose();
        if (latencyManager != null) {
            latencyManager.shutDown();
        }
    }

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        stage = new Stage(new StretchViewport(1000, 500));
        Gdx.input.setInputProcessor(stage);
        PongTexture = new Texture("simplePong.png");
        ButtonTexture = new Texture("testTexture.png");
        stage.addActor(new Background());
        currentState = State.Menu;

        host = new Button(0, 0, 500, 250);
        find = new Button(512, 0, 500, 150);
        cancel = new Button(0, 512, 500, 250);
        modeSwitch=new Button(0, 512, 500, 250);

        serverPaddle = new Paddle(100, 250);
        clientPaddle = new Paddle(900, 250);
        ball = new Ball();
        host.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                hostClick();
                return true;
            }
        });
        find.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                findClick();
                return true;
            }
        });
        cancel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                cancelClick();
                return true;
            }
        });
        switchState(State.Menu);
    }

    @Override
    public void render() {
        if (latencyManager != null) {
            latencyManager.update(Gdx.graphics.getDeltaTime());
        }

        if(latencyManager != null && timeReady) {

            if(latencyManager.getSide()) {

                stage.act(Gdx.graphics.getDeltaTime());
                latencyManager.storeLocation(new Packet(serverPaddle.getLocation(), clientPaddle.getLocation()
                        , ball.x,ball.y,ball.dx,ball.dy, timestamp));
            }
            else
            {
                if(clientPredictionOn) stage.act(Gdx.graphics.getDeltaTime());
                if(SimplePong.clientPredictionOn)
                    stateQueue.add(new ClientState(ball,clientPaddle,serverPaddle,timestamp));
                Gdx.app.debug("QueueElementNumber", SimplePong.stateQueue.size() + " ");
            }

            timestamp+=Gdx.graphics.getDeltaTime();
        }

        stage.draw();

    }

    private void switchState(State state) {
        //perform cleanup for current state
        switch (currentState) {
            case Menu:
                host.remove();
                find.remove();
                break;
            case Waiting:
                cancel.remove();
                break;
        }

        //perform setup for new state
        switch (state) {
            case Menu:
                stage.addActor(host);
                stage.addActor(find);
                break;
            case Waiting:
                stage.addActor(cancel);
                break;
            case Game:
                stage.addActor(serverPaddle);
                stage.addActor(clientPaddle);
                stage.addActor(ball);

                if (latencyManager.getSide()) {
                    person = new PersonController(latencyManager, serverPaddle);
                    serverPaddle.addListener(person);
                    //clientPaddle.addListener(person);
                    network = new NetworkController(clientPaddle);
                    latencyManager.networkManager.setActorListener(network);
                } else {

                    stage.addActor(modeSwitch);
                    modeSwitch.addListener(new InputListener() {
                        @Override
                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                            modeSwitchClick();
                            return true;
                        }
                    });

                    person = new PersonController(latencyManager, clientPaddle);
                    clientPaddle.addListener(person);
                    network = new NetworkController(clientPaddle, serverPaddle, ball);
                    latencyManager.networkManager.setActorListener(network);
                }
                break;
        }
        currentState = state;
    }

    private void modeSwitchClick(){
        if(clientPredictionOn) {clientPredictionOn=false;modeSwitch.setColor(0,512);}
        else {clientPredictionOn=true;modeSwitch.setColor(512,0);}
    }

    private void hostClick() {
        latencyManager = new LatencyManager(new ServerManager(this), 0.5f, 0.1f);
        latencyManager.startUp();
        switchState(State.Waiting);
    }

    private void findClick() {
        latencyManager = new LatencyManager(new ClientManager(this), 0.5f, 0.1f);
        latencyManager.startUp();
        stateQueue=new LinkedList<ClientState>();
        switchState(State.Waiting);
    }

    private void cancelClick() {
        latencyManager.shutDown();
        latencyManager = null;
        switchState(State.Menu);
    }

    @Override
    public void onReady(boolean succeeded) {
        if (!succeeded) {
            latencyManager.shutDown();
            latencyManager = null;
            timeReady=false;
            timestamp=0;
        }
        switchState(succeeded ? State.Game : State.Menu);
        timeReady=true;
    }

    private enum State {Menu, Waiting, Game}

}
