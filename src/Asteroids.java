import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;

public class Asteroids extends Game
{
    public Sprite spaceship;
    public Group rockGroup;
    public Texture rockTex;
    public Group laserGroup;
    public Texture laserTex;
    public Animation explosionAnim;
    public Sprite shields;
    public AudioClip laserSound;
    public AudioClip explosionSound;
    public Label youWinLabel;
    public Label youLoseLabel;
    public boolean gameOver;
    public double timeLimit;
    public Label timeLimitLabel;
    public int score;
    public Label scoreLabel;

    public void initialize()
    {
        gameOver = false;
        setTitle("Asteroids");
        setWindowSize(800,600);
        timeLimit = 40.0;
        score = 0;

        Sprite background = new Sprite();
        Texture bgTex = new Texture(
                "images/space.png");
        background.setTexture(bgTex);
        background.setPosition(400,300);
        group.add(background);

        spaceship = new Sprite();
        Texture ssTex = new Texture(
                "images/spaceship.png");
        spaceship.setTexture(ssTex);
        spaceship.setPosition(400,300);
        spaceship.setPhysics(new Physics(
                200,200,20));
        spaceship.addAction(Action.wrapToScreen(800,600));
        group.add(spaceship);

        rockGroup = new Group();
        group.add((rockGroup));
        int rockCount = 7;
        rockTex = new Texture("images/asteroid.png");
        for(int i = 0; i < rockCount; i++)
        {
            Sprite rock = new Sprite();
            rock.setTexture(rockTex);
            rock.setSize(100,100);
            double angle = 2 * Math.PI *Math.random();
            double x = spaceship.position.x + 250 * Math.cos(angle);
            double y = spaceship.position.y + 250 * Math.sin(angle);
            rock.setPosition(x,y);
            rock.setPhysics(new Physics(0,100,0));

            double moveSpeed = 30 * Math.random() + 90;
            rock.physics.setSpeed(moveSpeed);
            rock.physics.setMotionAngle(Math.toDegrees(angle));

            double rotateSpeed = 2 * Math.random() +1;
            rock.addAction(Action.forever(Action.rotateBy(360,rotateSpeed)));

            rock.addAction(Action.wrapToScreen(800,600));

            rockGroup.add(rock);
        }

        laserGroup = new Group();
        group.add(laserGroup);
        laserTex = new Texture("images/laser.png");

        explosionAnim = new Animation("images/explosion.png", 6, 6, .01, false);

        shields = new Sprite();
        Texture shieldTex = new Texture("images/shields.png");
        shields.setTexture(shieldTex);
        shields.setSize(120,120);
        group.add(shields);

        laserSound = new AudioClip("file:Audio/laserBlast.wav");
        explosionSound = new AudioClip("file:Audio/rockExplosion.wav");

        youWinLabel = new Label("Comic Sans MS Bold", 80);
        youWinLabel.setText("You Win!");
        youWinLabel.fontColor = Color.GREEN;
        youWinLabel.borderColor = Color.DARKGREEN;
        youWinLabel.setPosition(400,300);
        youWinLabel.alignment = "CENTER";
        youWinLabel.visible = false;
        group.add(youWinLabel);

        youLoseLabel = new Label("Comic Sans MS Bold", 80);
        youLoseLabel.setText("You Lose ;(");
        youLoseLabel.fontColor = Color.RED;
        youLoseLabel.borderColor = Color.DARKRED;
        youLoseLabel.setPosition(400,300);
        youLoseLabel.alignment = "CENTER";
        youLoseLabel.visible = false;
        group.add(youLoseLabel);

        timeLimitLabel = new Label("Comic Sans MS Bold", 30);
        timeLimitLabel.setText("Time Left: " + (int)timeLimit);
        timeLimitLabel.fontColor = Color.WHITE;
        timeLimitLabel.borderColor = Color.BLACK;
        timeLimitLabel.setPosition(550, 50);
        group.add(timeLimitLabel);

        scoreLabel = new Label("Comic Sans MS Bold", 30);
        scoreLabel.setText("Score: " + score);
        scoreLabel.fontColor = Color.WHITE;
        scoreLabel.borderColor = Color.BLACK;
        scoreLabel.setPosition(100, 50);
        group.add(scoreLabel);
    }

    public void update()
    {
        if(gameOver)
            return;

        if(timeLimit > 0 && !gameOver)
        {
            //Subtracting 1 second every 60 frames.
            timeLimit -= 1.0/60.0;
            //Max is being used to prevent the timer from showing negative numbers.
            timeLimitLabel.setText("Time: " + (int)Math.max(0,timeLimit));
        }

        if(timeLimit <= 0 && rockGroup.size() > 0)
        {
            gameOver = true;
            youLoseLabel.visible = true;

            Sprite explosion = new Sprite();
            explosion.setAnimation(explosionAnim.clone());
            explosion.setPosition(spaceship.position.x,spaceship.position.y);
            explosion.addAction(Action.sequence(Action.isAnimationFinished(),Action.removeSelf()));
            group.add(explosion);
            explosionSound.play();
            spaceship.removeSelf();
            shields.removeSelf();

            for(Entity rockR : rockGroup.getList())
            {
                rockR.removeSelf();
            }

            for(Entity laserR : laserGroup.getList())
            {
                laserR.removeSelf();
            }

            return;
        }

       shields.setPosition(spaceship.position.x,spaceship.position.y);
       shields.setAngle(spaceship.angle);

        if(input.isKeyPressed("LEFT"))
            spaceship.rotateBy(-3);
        if(input.isKeyPressed("RIGHT"))
            spaceship.rotateBy(3);
        if(input.isKeyPressed("UP"))
            spaceship.physics.accelerateAtAngle(spaceship.angle);
        if(input.isKeyJustPressed("SPACE") && rockGroup.size() != 0)
        {
            Sprite laser = new Sprite();
            laser.setTexture(laserTex);
            laser.setPosition(spaceship.position.x, spaceship.position.y);
            laser.setPhysics(new Physics(0,400,0));
            laser.physics.setSpeed(400);
            laser.physics.setMotionAngle(spaceship.angle);
            laser.addAction(Action.wrapToScreen(800,600));
            laserGroup.add(laser);
            laserSound.play();

            laser.addAction(Action.sequence(Action.delay(0.5),
                    Action.fadeOut(0.5), Action.removeSelf()));
        }

        for(Entity rockE : rockGroup.getList())
        {

            Sprite rock = (Sprite)rockE;

            if(shields.overlaps(rock) && shields.opacity > 0)
            {
                score += 50;
                scoreLabel.setText("Score: " + score);
                rock.removeSelf();
                shields.opacity -= 0.25;
                Sprite explosion = new Sprite();
                explosion.setAnimation(explosionAnim.clone());
                explosion.setPosition(rock.position.x,rock.position.y);
                explosion.addAction(Action.sequence(Action.isAnimationFinished(),Action.removeSelf()));
                group.add(explosion);
                explosionSound.play();
            }

            if(rock.overlaps(spaceship) && rockGroup.size() != 0)
            {
                gameOver = true;
                youLoseLabel.visible = true;

                Sprite explosion = new Sprite();
                explosion.setAnimation(explosionAnim.clone());
                explosion.setPosition(spaceship.position.x,spaceship.position.y);
                explosion.addAction(Action.sequence(Action.isAnimationFinished(),Action.removeSelf()));
                group.add(explosion);
                explosionSound.play();
                spaceship.removeSelf();

                for(Entity rockR : rockGroup.getList())
                {
                    rockR.removeSelf();
                }

                for(Entity laserR : laserGroup.getList())
                {
                    laserR.removeSelf();
                }

                return;
            }

            for(Entity laserE : laserGroup.getList())
            {
                Sprite laser = (Sprite)laserE;

                if (rock.overlaps(laser))
                {
                    if(rock.width == 100 && rock.height == 100)
                    {
                        score += 70;
                        scoreLabel.setText("Score: " + score);
                    }
                    else if (rock.width == 50 && rock.height == 50)
                    {
                        score += 90;
                        scoreLabel.setText("Score: " + score);
                    }
                    else if (rock.width == 25 && rock.height == 25)
                    {
                        score += 120;
                        scoreLabel.setText("Score: " + score);
                    }

                    rock.removeSelf();
                    laser.removeSelf();
                    Sprite explosion = new Sprite();
                    explosion.setAnimation(explosionAnim.clone());
                    explosion.setPosition(rock.position.x,rock.position.y);
                    explosion.addAction(Action.sequence(Action.isAnimationFinished(),Action.removeSelf()));
                    group.add(explosion);
                    explosionSound.play();


                    if(rock.width == 100)
                    {
                        double randCheck = Math.random();
                        if (randCheck >= .50)
                        {
                            for(int i = 0; i < 2; i++)
                            {
                                Sprite rockSmall = new Sprite();
                                rockSmall.setTexture(rockTex);
                                rockSmall.setSize(50,50);
                                rockSmall.addAction(Action.wrapToScreen(800,600));
                                rockSmall.setPhysics(new Physics(0,300,0));
                                rockSmall.physics.setSpeed(2 * rock.physics.getSpeed());
                                rockSmall.physics.setMotionAngle(rock.physics.getMotionAngle() + 90 * Math.random() - 45);
                                rockSmall.setPosition(rock.position.x, rock.position.y);
                                rockGroup.add(rockSmall);
                            }
                        }
                    }

                    if(rock.width == 50)
                    {
                        double randCheck = Math.random();
                        if (randCheck >= .50)
                        {
                            for(int i = 0; i < 2; i++)
                            {
                                Sprite rockSmall = new Sprite();
                                rockSmall.setTexture(rockTex);
                                rockSmall.setSize(25,25);
                                rockSmall.addAction(Action.wrapToScreen(800,600));
                                rockSmall.setPhysics(new Physics(0,300,0));
                                rockSmall.physics.setSpeed(2 * rock.physics.getSpeed());
                                rockSmall.physics.setMotionAngle(rock.physics.getMotionAngle() + 90 * Math.random() - 45);
                                rockSmall.setPosition(rock.position.x, rock.position.y);
                                rockGroup.add(rockSmall);
                            }
                        }
                    }
                }
            }
        }

        if(rockGroup.size() == 0)
        {
            score += ((int)timeLimit * 100);
            scoreLabel.setText("Score: " + score);

            gameOver = true;
            youWinLabel.visible = true;
            spaceship.removeSelf();
            shields.removeSelf();
            if(laserGroup.size() > 0)
            {
                for (Entity laserE : laserGroup.getList())
                {
                    laserE.removeSelf();
                }
            }
        }




    }


}
