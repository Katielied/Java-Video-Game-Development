

public class TestGame extends Game
{

    Sprite kirby;
    public void initialize()
    {
        setTitle("Test Game");
        setWindowSize(800,600);
        System.out.println("Hello world");

        kirby = new Sprite();
        kirby.setPosition(10,10);
        kirby.setTexture(Texture.load("images/Kirby.png"));
        kirby.setSize(50,50);

        group.add(kirby);

    }
    public void update()
    {
        //kirby.position.addValues(2,1);

        if(input.isKeyPressed("RIGHT"))
        {
            kirby.position.addValues(2,0);
        }

        if(input.isKeyPressed("LEFT"))
        {
            kirby.position.addValues(-2,0);
        }

        if(input.isKeyPressed("UP"))
        {
            kirby.position.addValues(0,-2);
        }

        if(input.isKeyPressed("DOWN"))
        {
            kirby.position.addValues(0,2);
        }

        if(input.isKeyJustPressed("Z"))
        {
            kirby.position.setValues(0,0);
        }


    }

}