package com.samuel.simplepong;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class SimplePong extends ApplicationAdapter implements ReadyListener{
    public static Texture PongTexture;
    public static Texture ButtonTexture;
    private State currentState;
    private Button host, find, cancel;
    private Paddle left, right;
    private Stage stage;
    private NetworkManager networkManager;
    private PersonController person;
    private NetworkController network;
    private Ball ball;

    @Override
    public void dispose() {
        super.dispose();
        if (networkManager != null) {
            networkManager.shutDown();
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
        left = new Paddle(100, 250);
        right = new Paddle(900, 250);
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
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        if (ball.x < -50 || ball.x > 1050) {
            ball.x = 500;
            ball.y = 250;
            ball.dx = 200;
            ball.dy = 200;
        } else {
            if (ball.y < 50) {
                ball.y = 50;
                ball.dy *= -1;
            }
            if (ball.y > 450) {
                ball.y = 450;
                ball.dy *= -1;
            }
            if (ball.x < 175 && ball.x > 125 && Math.abs(left.getLocation() - ball.y) < 150) {
                ball.x = 175;
                ball.dx *= -1;
            }
            if (ball.x > 825 && ball.x < 875 && Math.abs(right.getLocation() - ball.y) < 150) {
                ball.x = 825;
                ball.dx *= -1;
            }
        }
    }

    private void switchState(State state) {
        switch (currentState) {
            case Menu:
                host.remove();
                find.remove();
                break;
            case Waiting:
                cancel.remove();
                break;
        }
        switch (state) {
            case Menu:
                stage.addActor(host);
                stage.addActor(find);
                break;
            case Waiting:
                stage.addActor(cancel);
                break;
            case Game:
                stage.addActor(left);
                stage.addActor(right);
                stage.addActor(ball);
                if (networkManager.getSide()) {
                    person = new PersonController(networkManager, left);
                    left.addListener(person);
                    network = new NetworkController(right);
                    networkManager.setPaddleListener(network);
                } else {
                    person = new PersonController(networkManager, right);
                    right.addListener(person);
                    network = new NetworkController(left);
                    networkManager.setPaddleListener(network);
                }
                break;
        }
        currentState = state;
    }

    private void hostClick() {
        networkManager = new ServerManager(this);
        networkManager.startUp();
        switchState(State.Waiting);
    }

    private void findClick() {
        networkManager = new ClientManager(this);
        networkManager.startUp();
        switchState(State.Waiting);
    }

    private void cancelClick() {
        networkManager.shutDown();
        networkManager = null;
        switchState(State.Menu);
    }

    @Override
    public void onReady(boolean succeeded) {
        if (!succeeded) {
            networkManager.shutDown();
            networkManager = null;
        }
        switchState(succeeded ? State.Game : State.Menu);
    }

    private enum State {Menu, Waiting, Game}
}
