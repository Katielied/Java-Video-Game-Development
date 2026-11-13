import javafx.scene.image.Image;

import java.io.File;

/*
Talk about sprite sheets, that will lead to animations.
We will define which parts of the image will be rendered.
Tilemaps can also be used.
We are going to allow us to pick and choose certain parts of an image to use in order to create layouts
or animations.
We will be using the rectangle class we created to use these.
We may have a single sprite that shares a single texture.

We will call on the Rectangle class to store an image inside the rectangle.
We then can define which part of the rectangle should be visible in the game. (Sprite sheets, animations, etc.)
 */
public class Texture
{

    public Rectangle region;
    public Image image;

    public Texture()
    {


    }

    public Texture(String imageFileName)
    {
        image = new Image(imageFileName);
        region = new Rectangle();
        double width = image.getWidth();
        double height = image.getHeight();
        region.setValues(0,0,width,height);
    }

    public static Texture load(String imageFileName)
    {

        Texture tex = new Texture();
        //String fileName = new File(imageFileName).toURI().toString();
        tex.image = new Image( imageFileName );
        tex.region = new Rectangle();
        double width = tex.image.getWidth();
        double height = tex.image.getHeight();
        tex.region.setValues(0,0,width,height);
        return tex;
    }

}
