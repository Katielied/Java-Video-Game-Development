import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;

public class TennisPong extends Game
{
    public Label p1ScoreLabel;
    public Label p2ScoreLabel;
    public Label youWinLabel;
    public int p1Score;
    public int p2Score;
    public Sprite tennisBall;
    public Sprite player1;
    public Sprite player2;
    public String playerWon;
    public Sprite player1Winner;
    public Sprite player2Winner;
    public double rotateValue;
    public Sprite player1Hits;
    public Sprite player2Hits;
    public Sprite player1Run;
    public Sprite player2Run;
    public AudioClip ballHitAudio;
    public AudioClip winPointAudio;
    public AudioClip winnerAudio;
    public Sprite player1WinningAnim;
    public Sprite player2WinningAnim;
    public boolean gameOver;
    public double hitTime = 0;


    public void initialize()
    {
        setWindowSize(800,600);
        Sprite background = new Sprite();
        Texture bgTex = new Texture(
                "images/PongImages/tennisCourtBackground.png");
        background.setTexture(bgTex);
        background.setSize(800,600);
        background.setPosition(400,300);
        group.add(background);

        gameOver = false;
        hitTime = 0;

        /**
         * Setting up player 1 and player 2 main sprites
         * They should both be bound to the screen, and have specific physics to move fast enough to each ball.
         */

        player1 = new Sprite();
        Texture player1Tex = new Texture(
                "images/PongImages/player1.png");
        player1.setTexture(player1Tex);
        player1.setSize(80,110);
        player1.setPosition(100,300);
        player1.setPhysics(new Physics(
                200,200,20));
        player1.addAction(Action.boundToScreen(800,600));
        player1.setPhysics( new Physics(2000, 1000, 4000) );
        group.add(player1);

        player2 = new Sprite();
        Texture player2Tex = new Texture(
                "images/PongImages/player2.png");
        player2.setTexture(player2Tex);
        player2.setSize(80,110);
        player2.setPosition(700,300);
        player2.addAction(Action.boundToScreen(800,600));
        player2.setPhysics( new Physics(2000, 1000, 4000) );
        group.add(player2);

        /**
         * Setting up the tenniball sprite
         * It should start at a random motion angle from the middle,
         * Making sure that it does not shoot straight up and stop the game.
         * It should start at a low speed, as it will speed up as time goes on.
         * Rotation should start slow as well - as its action.
         */

        tennisBall = new Sprite();
        tennisBall.setTexture(new Texture("images/PongImages/tennisBall.png"));
        tennisBall.setSize(30,30);
        tennisBall.setPosition(400,300);
        tennisBall.setPhysics(new Physics(0,1000,0));

        boolean isVertical;
        double angle;

        do{
            angle = 2 * Math.PI * Math.random();
            double degrees = Math.toDegrees(angle);

            isVertical = (degrees > 70 && degrees < 110 || degrees > 250 && degrees < 290 );

        }while(isVertical);

        double moveSpeed = 120;
        tennisBall.physics.setSpeed(moveSpeed);
        tennisBall.physics.setMotionAngle(Math.toDegrees(angle));
        tennisBall.addAction(Action.boundToScreen(800,600));

        rotateValue = 0.2;
        tennisBall.addAction(Action.forever(Action.rotateBy(30,rotateValue)));
        group.add(tennisBall);

        /**
         * Setting up the different animations for the players - Hitting, Running, and Winning.
         * All animations will follow the same x and y values as the normal sprite, but be invisible, only be visible when specific actions are done.
         */

        player1Hits = new Sprite();
        player1Hits.setAnimation(new Animation("images/PongImages/player1HitsAnim.png",1,3,0.2,false));
        player1Hits.animation.paused = true;
        player1Hits.setSize(110,110);
        player1Hits.setPosition(player1.position.x,player1.position.y);
        player1Hits.visible = false;
        group.add(player1Hits);

        player2Hits = new Sprite();
        player2Hits.setAnimation(new Animation("images/PongImages/player2HitsAnim.png",1,3,0.2,false));
        player2Hits.animation.paused = true;
        player2Hits.setSize(110,110);
        player2Hits.setPosition(player2.position.x,player2.position.y);
        player2Hits.visible = false;
        group.add(player2Hits);

        /**
         * Labels for their scores and for winning the game.
         * The scores will be updated through the score variables.
         * Winner label will get updated from when the player wins the game.
         */

        p1ScoreLabel = new Label("Comic Sans MS Bold", 30);
        p1ScoreLabel.setText("Player 1: " + p1Score);
        p1ScoreLabel.fontColor = Color.WHITE;
        p1ScoreLabel.borderColor = Color.BLACK;
        p1ScoreLabel.setPosition(100, 50);
        group.add(p1ScoreLabel);

        p2ScoreLabel = new Label("Comic Sans MS Bold", 30);
        p2ScoreLabel.setText("Player 2: " + p2Score);
        p2ScoreLabel.fontColor = Color.WHITE;
        p2ScoreLabel.borderColor = Color.BLACK;
        p2ScoreLabel.setPosition(550, 50);
        group.add(p2ScoreLabel);

        youWinLabel = new Label("Comic Sans MS Bold", 60);
        youWinLabel.setText(playerWon + " Wins!");
        youWinLabel.fontColor = Color.GOLD;
        youWinLabel.borderColor = Color.BLACK;
        youWinLabel.setPosition(220,290);
        youWinLabel.visible = false;
        group.add(youWinLabel);

        playerWon = "";

        player1Winner = new Sprite();
        player1Winner.setTexture(new Texture("images/PongImages/player1Wins.png"));
        player1Winner.setSize(80,110);
        player1Winner.setPosition(player1.position.x,player1.position.y);
        player1Winner.visible = false;
        group.add(player1Winner);

        player2Winner = new Sprite();
        player2Winner.setTexture(new Texture("images/PongImages/player2Wins.png"));
        player2Winner.setSize(80,110);
        player2Winner.setPosition(player2.position.x,player2.position.y);
        player2Winner.visible = false;
        group.add(player2Winner);

        player1Run = new Sprite();
        player1Run.setAnimation(new Animation("images/PongImages/player1RunningAnim.png",1,5,0.1,true));
        player1Run.animation.paused = true;
        player1Run.setSize(105,135);
        player1Run.setPosition(player1.position.x,player1.position.y);
        player1Run.visible = false;
        group.add(player1Run);

        player2Run = new Sprite();
        player2Run.setAnimation(new Animation("images/PongImages/player2RunningAnim.png",1,5,0.1,true));
        player2Run.animation.paused = true;
        player2Run.setSize(90,120);
        player2Run.setPosition(player2.position.x,player2.position.y);
        player2Run.visible = false;
        group.add(player2Run);

        /**
         * Setting up audios for hitting the ball, winning a point, and winning the match.
         */

        ballHitAudio = new AudioClip("file:Audio/ballHit.wav");
        winPointAudio = new AudioClip("file:Audio/winningPoint.wav");
        winnerAudio = new AudioClip("file:Audio/winnerSound.wav");

        /**
         * Setting up the winning animations for each of the players.
         * Making sure that they are not visible.
         */

        player1WinningAnim = new Sprite();
        player1WinningAnim.setAnimation(new Animation("Images/PongImages/winningAnimPlayer1.png",1,7,0.2,true));
        player1WinningAnim.setPosition(player1.position.x,player1.position.y);
        player1WinningAnim.setSize(100,160);
        player1WinningAnim.animation.paused = false;
        player1WinningAnim.visible = false;
        group.add(player1WinningAnim);

        player2WinningAnim = new Sprite();
        player2WinningAnim.setAnimation(new Animation("Images/PongImages/winningAnimPlayer2.png",1,8,0.2,true));
        player2WinningAnim.setPosition(player2.position.x,player2.position.y);
        player2WinningAnim.setSize(110,180);
        player2WinningAnim.visible = false;
        group.add(player2WinningAnim);
    }

    public void update()
    {
        /**
         * If Statements for when an animation is finished. Making sure that after the player is done hitting or running,
         * They return to their normal state.
         */

        if(player1Hits.animation.isFinished())
        {
            player1Hits.visible = false;
            player1.visible = true;
        }

        if(player2Hits.animation.isFinished())
        {
            player2Hits.visible = false;
            player2.visible = true;
        }

        if(player1Run.animation.isFinished())
        {
            player1Run.visible = false;
            player1.visible = true;
        }

        if(player2Run.animation.isFinished())
        {
            player2Run.visible = false;
            player2.visible = true;
        }

        boolean isMoving2 = false;

        /**
         * Movements for each of the players.
         */

        if(input.isKeyPressed("UP"))
        {
            player2.physics.accelerateAtAngle(270);
            isMoving2 = true;
        }
        if(input.isKeyPressed("DOWN"))
        {
            player2.physics.accelerateAtAngle(90);
            isMoving2 = true;
        }

        boolean isMoving = false;

        if(input.isKeyPressed("W")) {
            player1.physics.accelerateAtAngle(270);
            isMoving = true;
        }
        if(input.isKeyPressed("S")) {
            player1.physics.accelerateAtAngle(90);
            isMoving = true;
        }

        /**
         * If statments to make sure no animations overlap.
         */

        if(!player1Hits.visible)
        {
            if(isMoving)
            {
                player1.visible = false;
                player1Run.visible = true;
                player1Run.animation.paused = false;
            }
            else
            {
                player1.visible = true;
                player1Run.visible = false;
                player1Run.animation.paused = true;
                player1Run.animation.elapsedTime = 0;
            }
        }

        if(!player2Hits.visible)
        {
            if(isMoving2)
            {
                player2.visible = false;
                player2Run.visible = true;
                player2Run.animation.paused = false;
            }
            else
            {
                player2.visible = true;
                player2Run.visible = false;
                player2Run.animation.paused = true;
                player2Run.animation.elapsedTime = 0;
            }
        }

        /**
         * Setting and updating the positions of the animations.
         */

        player1Hits.setPosition(player1.position.x,player1.position.y);
        player2Hits.setPosition(player2.position.x,player2.position.y);
        player1Run.setPosition(player1.position.x,player1.position.y);
        player2Run.setPosition(player2.position.x,player2.position.y);
        player1WinningAnim.setPosition(player1.position.x,player1.position.y);
        player2WinningAnim.setPosition(player2.position.x,player2.position.y);

        /**
         * Handling when the player hits a ball.
         * The ball audio should play
         * the hitting animation should run and then return to its original state.
         * the ball should bounce back in the other direction
         * The rotate and speed should increase.
         */

        if(tennisBall.overlaps(player1) && hitTime <= 0)
        {
            hitTime = 0.5;

            player1.visible = false;
            player1Run.visible = false;
            player1Hits.visible = true;

            ballHitAudio.play();

            player1Hits.animation.elapsedTime = 0;
            player1Hits.animation.paused = false;

            rotateValue -= 0.02;
            if(rotateValue < 0.01)
                rotateValue = 0.01;

            tennisBall.actionList.clear();
            tennisBall.addAction(Action.forever(Action.rotateBy(30,rotateValue)));

            double currentAngle = tennisBall.physics.getMotionAngle();
            tennisBall.physics.setMotionAngle(180-currentAngle);

            double newSpeed = tennisBall.physics.getSpeed() * 1.2;
            tennisBall.physics.setSpeed(newSpeed);

        }

        if(tennisBall.overlaps(player2) && hitTime <= 0)
        {

            hitTime = 0.5;
            player2.visible = false;
            player2Run.visible = false;
            player2Hits.visible = true;

            ballHitAudio.play();

            rotateValue -= 0.02;
            if(rotateValue < 0.01)
                rotateValue = 0.01;

            tennisBall.actionList.clear();
            tennisBall.addAction(Action.forever(Action.rotateBy(30,rotateValue)));

            player2Hits.animation.elapsedTime = 0;
            player2Hits.animation.paused = false;

            double currentAngle = tennisBall.physics.getMotionAngle();
            tennisBall.physics.setMotionAngle(180-currentAngle);

            double newSpeed = tennisBall.physics.getSpeed() * 1.2;
            tennisBall.physics.setSpeed(newSpeed);

        }

        /**
         * Handling when the tenniball hits one of the walls or the barriers of the screen.
         * Which is not the winning side.
         * The ball hitting audio should play
         * The balls angle should also get pushed back into the screen.
         */

        if(tennisBall.position.y <= 15)
        {
            ballHitAudio.play();
            double currentAngle = tennisBall.physics.getMotionAngle();
            tennisBall.physics.setMotionAngle(360 - currentAngle);
        }

        if(tennisBall.position.y >= 570)
        {
            ballHitAudio.play();
            double currentAngle = tennisBall.physics.getMotionAngle();
            tennisBall.physics.setMotionAngle(360 - currentAngle);

        }

        /**
         * Handling when a player scores a point.
         * The winning point audio should play.
         * The tennisball should be reset to the center, with a new random angle and original rotation and speed values.
         * That players score should increase by 1
         * That players label should be updated with the new score.
         */

        if(tennisBall.position.x <= 15)
        {
            winPointAudio.play();

            tennisBall.setPosition(400,300);
            tennisBall.setPhysics(new Physics(0,1000,0));

            rotateValue = 0.2;
            tennisBall.actionList.clear();
            tennisBall.addAction(Action.forever(Action.rotateBy(30,rotateValue)));

            double angle;
            double moveSpeed = 120;
            boolean isVertical;

            do{
                angle = 2 * Math.PI * Math.random();
                double degrees = Math.toDegrees(angle);

                isVertical = (degrees > 70 && degrees < 110 || degrees > 250 && degrees < 290 );

            }while(isVertical);

            tennisBall.physics.setSpeed(moveSpeed);
            tennisBall.physics.setMotionAngle(Math.toDegrees(angle));

            rotateValue = 0.2;
            p2Score += 1;
            p2ScoreLabel.setText("Player 2: " + p2Score);

        }

        if(tennisBall.position.x >= 785)
        {
            winPointAudio.play();

            tennisBall.setPosition(400,300);
            tennisBall.setPhysics(new Physics(0,1000,0));

            rotateValue = 0.2;
            tennisBall.actionList.clear();
            tennisBall.addAction(Action.forever(Action.rotateBy(30,rotateValue)));

            double angle;
            double moveSpeed = 120;
            boolean isVertical;

            do{
                angle = 2 * Math.PI * Math.random();
                double degrees = Math.toDegrees(angle);

                isVertical = (degrees > 70 && degrees < 110 || degrees > 250 && degrees < 290 );

            }while(isVertical);

            tennisBall.physics.setSpeed(moveSpeed);
            tennisBall.physics.setMotionAngle(Math.toDegrees(angle));

            rotateValue = 0.2;
            p1Score += 1;
            p1ScoreLabel.setText("Player 1: " + p1Score);
        }

        /**
         * If Statements to handle the winner
         * Gameover used so that the animations can run correctly.
         * All sprites should be removed
         * The winning animation should be shown
         * The winning label should be shown as well, updated as the winner player.
         */

        if(p1Score == 8 && !gameOver)
        {

            gameOver = true;

            playerWon = "Player 1";
            youWinLabel.setText(playerWon + " Wins!");
            youWinLabel.visible = true;
            player1.removeSelf();
            player2.removeSelf();
            player2Run.removeSelf();
            player1Run.removeSelf();
            player1Hits.removeSelf();
            player2Hits.removeSelf();
            tennisBall.removeSelf();
            player1WinningAnim.visible = true;
            player1WinningAnim.animation.paused = false;
            player1WinningAnim.animation.elapsedTime = 0;
            winnerAudio.play();

        }

        if(p2Score == 8 && !gameOver)
        {
            gameOver = true;

            playerWon = "Player 2";
            youWinLabel.setText(playerWon + " Wins!");
            youWinLabel.visible = true;
            player1.removeSelf();
            player2.removeSelf();
            player2Run.removeSelf();
            player1Run.removeSelf();
            player1Hits.removeSelf();
            player2Hits.removeSelf();
            tennisBall.removeSelf();
            player2WinningAnim.visible = true;
            winnerAudio.play();

        }

        /**
         * Creating a reset button for the tennisball in case it gets stuck.
         * Using the same logic used when resetting the tennisball after winning a point.
         */

        if(input.isKeyPressed("R"))
        {
            tennisBall.setPosition(400,300);
            tennisBall.setPhysics(new Physics(0,1000,0));

            rotateValue = 0.2;
            tennisBall.actionList.clear();
            tennisBall.addAction(Action.forever(Action.rotateBy(30,rotateValue)));

            double angle;
            double moveSpeed = 120;
            boolean isVertical;

            do{
                angle = 2 * Math.PI * Math.random();
                double degrees = Math.toDegrees(angle);

                isVertical = (degrees > 70 && degrees < 110 || degrees > 250 && degrees < 290 );

            }while(isVertical);

            tennisBall.physics.setSpeed(moveSpeed);
            tennisBall.physics.setMotionAngle(Math.toDegrees(angle));

            rotateValue = 0.2;
        }

        //roughly 1 frame
        hitTime -= 0.016;

    }

}
