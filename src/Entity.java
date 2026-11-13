import javafx.scene.canvas.GraphicsContext;

public abstract class Entity
{
    public Group parent;

    public abstract void draw(GraphicsContext context);
    public abstract void update(double dt);

    public void removeSelf()
    {
        parent.remove(this);
    }
}
