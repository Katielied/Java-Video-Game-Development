import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Group extends Entity
{
    private ArrayList<Entity> list;

    public Group()
    {
        list = new ArrayList<Entity>();
    }

    public void add(Entity e)
    {
        list.add(e);
        e.parent = this;
    }

    public void remove(Entity e)
    {
        this.list.remove(e);
        e.parent = null;
    }

    public ArrayList<Entity> getList()
    {
        return new ArrayList<Entity>(list);
    }

    public int size()
    {
        return list.size();
    }

    public void draw(GraphicsContext context)
    {
        for(Entity e : list)
        {
            e.draw(context);
        }
    }

    public void update(double dt)
    {
        for (Entity e : this.list)
        {
            e.update(dt);
        }
    }
}
