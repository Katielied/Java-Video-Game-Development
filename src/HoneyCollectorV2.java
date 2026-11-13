import javafx.scene.paint.Color;

public class HoneyCollectorV2 extends Game
{
    Sprite grass;
    Group honeyGroup;
    Sprite bee;
    Group beehiveGroup;
    //lock for z button
    boolean zUsed = false;

    Label instructionLabel;
    Label winLabel;
    Label honeyCountLabel;


    public void initialize()
    {
        setTitle("Starfish Collector");
        setWindowSize(800,600);

        grass = new Sprite();
        grass.setTexture(new Texture("images/honeyCollectorImages/grass.png"));
        grass.setSize(800,600);
        grass.setPosition(400,300);
        group.add(grass);

        beehiveGroup = new Group();
        Texture beehiveTexture = new Texture("images/honeyCollectorImages/beehive.png");
        int beehiveCount = 4;
        for (int i = 0; i < beehiveCount; i++)
        {
            Sprite beehive = new Sprite();
            double x = Math.random() * 600 + 100;
            double y = Math.random() * 400 + 100;
            beehive.setPosition(x,y);
            beehive.setTexture(beehiveTexture);
            beehive.setSize(80,80);
            beehive.boundary.setSize(20,20);
            beehiveGroup.add(beehive);
        }

        group.add(beehiveGroup);

        honeyGroup = new Group();
        Texture honeyTexture = new Texture("images/honeyCollectorImages/honey.png");
        int honeyCount = 20;
        for (int i = 0; i < honeyCount; i++)
        {
            Sprite honey = new Sprite();
            double x = Math.random() * 600 + 100;
            double y = Math.random() * 400 + 100;
            honey.setPosition(x,y);
            honey.setTexture(honeyTexture);
            honey.setSize( (int)(Math.random() * 40 + 60), (int)(Math.random() * 40 + 60));
            honeyGroup.add(honey);

            boolean beehiveOverlap;
            do
            {
                beehiveOverlap = false;
                x = Math.random() * 600 + 100;
                y = Math.random() * 400 + 100;
                honey.setPosition(x, y);
                for (Entity entity : beehiveGroup.getList())
                {
                    Sprite beehive = (Sprite)entity;
                    if (beehive.overlaps(honey))
                        beehiveOverlap = true;
                }
            } while( beehiveOverlap );

            honey.addAction(Action.forever(Action.sequence(Action.moveBy(0,5,0.5),
                    Action.moveBy(0,-5,0.5))));

        }
        group.add(honeyGroup);

        bee = new Sprite();
        bee.setPosition(400,300);
        bee.setTexture(new Texture("images/honeyCollectorImages/bee.png"));
        bee.setSize(85,85);
        bee.boundary.setSize(20,20);

        bee.setPhysics( new Physics(1000, 500, 1000) );
        bee.addAction(Action.boundToScreen(800,600));
        group.add(bee);

        int butterflyCount = 6;
        Animation butterflyAnim = new Animation("images/honeyCollectorImages/butterfly.png", 4, 10, 0.2, true);
        for(int i = 0; i < butterflyCount; i++)
        {
            Sprite butterfly = new Sprite();
            butterfly.setPosition(Math.random() * 800, Math.random() * 600);

            butterfly.setAnimation(butterflyAnim);
            butterfly.setSize(50,50);
            butterfly.setPhysics(new Physics(0, 200, 0));
            double speed = Math.random() *100 + 100;
            butterfly.physics.setSpeed(speed);
            double angle = Math.random() *360;
            butterfly.physics.setMotionAngle(angle);
            butterfly.setAngle(angle);
            butterfly.addAction(Action.wrapToScreen(800, 600));
            group.add(butterfly);
        }

        instructionLabel = new Label("Comic Sans MS Bold", 35);
        instructionLabel.setText("Press 'Z' To Teleport!");
        instructionLabel.fontColor = Color.LIGHTBLUE;
        instructionLabel.setBorder(2, Color.DARKBLUE);
        instructionLabel.setPosition(100,35);
        instructionLabel.alignment = "LEFT";
        group.add(instructionLabel);

        winLabel = new Label("Comic Sans MS Bold", 80);
        winLabel.setText("You Win!");
        winLabel.fontColor = Color.LIGHTGREEN;
        winLabel.setBorder(2, Color.DARKGREEN);
        winLabel.setPosition(400, 300);
        winLabel.alignment = "CENTER";
        winLabel.visible = false;
        group.add(winLabel);

        honeyCountLabel = new Label("Comic Sans MS Bold", 48);
        String text = "Honey Left: " + honeyGroup.size();
        honeyCountLabel.setText( text );
        honeyCountLabel.setPosition(780, 550);
        honeyCountLabel.alignment = "RIGHT";
        honeyCountLabel.fontColor = Color.ORANGE;
        honeyCountLabel.setBorder(2, Color.BLACK);
        group.add(honeyCountLabel);
    }


    public void update()
    {

        if (winLabel.visible)
            return;

        if (input.isKeyPressed("RIGHT"))
            bee.physics.accelerateAtAngle(0);

        if (input.isKeyPressed("LEFT"))
            bee.physics.accelerateAtAngle(180);

        if (input.isKeyPressed("UP"))
            bee.physics.accelerateAtAngle(270);

        if (input.isKeyPressed("DOWN"))
            bee.physics.accelerateAtAngle(90);

        if ( bee.physics.getSpeed() > 0 )
            bee.setAngle(bee.physics.getMotionAngle());


        if(input.isKeyJustPressed("Z") && !zUsed)
        {
            bee.setPosition(Math.random() * 600 + 100, Math.random() * 400 + 100);
            zUsed = true;
        }

        if(!input.isKeyPressed("Z"))
        {
            zUsed = false;
        }

        for(Entity entity : beehiveGroup.getList())
        {
            Sprite beehive = (Sprite)entity;
            bee.preventOverlap(beehive);
        }

        for(Entity entity : honeyGroup.getList())
        {
            Sprite honey = (Sprite)entity;
            if ( bee.overlaps(honey))
            {
                honeyGroup.remove(honey);
                String text = "Honey Left: " + honeyGroup.size();
                honeyCountLabel.setText( text );
            }
        }

        if (honeyGroup.size()==0)
        {
            winLabel.visible = true;
            instructionLabel.visible = false;
            for (Entity entity : beehiveGroup.getList()) {
                Sprite beehive = (Sprite) entity;
                beehiveGroup.remove(beehive);
            }
        }

        bee.addAction(Action.boundToScreen(800,600));
    }
}

