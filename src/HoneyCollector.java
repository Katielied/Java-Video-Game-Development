public class HoneyCollector extends Game
{
    Sprite grass;
    Group honeyGroup;
    Sprite bee;
    Sprite winMessage;
    Group beehiveGroup;
    //lock for z button
   boolean zUsed = false;
   Sprite instructionText;

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
        int beehiveCount = 3;
        for (int i = 0; i < beehiveCount; i++)
        {
            Sprite beehive = new Sprite();
            double x = Math.random() * 600 + 100;
            double y = Math.random() * 400 + 100;
            beehive.setPosition(x,y);
            beehive.setTexture(beehiveTexture);
            beehive.setSize(95,95);
            beehiveGroup.add(beehive);
        }

        group.add(beehiveGroup);

        honeyGroup = new Group();
        Texture honeyTexture = new Texture("images/honeyCollectorImages/honey.png");
        int honeyCount = 10;
        for (int i = 0; i < honeyCount; i++)
        {
            Sprite honey = new Sprite();
            double x = Math.random() * 600 + 100;
            double y = Math.random() * 400 + 100;
            honey.setPosition(x,y);
            honey.setTexture(honeyTexture);
            honey.setSize(80,80);
            honeyGroup.add(honey);
        }
        group.add(honeyGroup);

        bee = new Sprite();
        bee.setPosition(50,50);
        bee.setTexture(new Texture("images/honeyCollectorImages/bee.png"));
        bee.setSize(100,100);
        group.add(bee);


        winMessage = new Sprite();
        winMessage.setPosition(400,300);
        winMessage.setTexture(new Texture("images/honeyCollectorImages/youWin.png"));
        winMessage.visible = false;
        winMessage.setSize(500,150);
        group.add(winMessage);

        instructionText = new Sprite();
        instructionText.setPosition(175, 18);
        instructionText.setTexture(new Texture("images/honeyCollectorImages/instructionText.png"));
        instructionText.visible = true;
        instructionText.setSize(450,100);
        group.add(instructionText);
    }


    public void update()
    {

        if (winMessage.visible)
            return;

        if(input.isKeyPressed("RIGHT"))
            bee.position.addValues(4,0);
        if(input.isKeyPressed("LEFT"))
            bee.position.addValues(-4,0);
        if(input.isKeyPressed("UP"))
            bee.position.addValues(0,-4);
        if(input.isKeyPressed("DOWN"))
            bee.position.addValues(0,4);

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
                honeyGroup.remove(honey);
        }


        if (honeyGroup.size()==0)
        {
            winMessage.visible = true;
            instructionText.visible = false;
            for (Entity entity : beehiveGroup.getList()) {
                Sprite beehive = (Sprite) entity;
                beehiveGroup.remove(beehive);
            }
        }
        
        bee.boundToScreen(800,600);
    }
}
