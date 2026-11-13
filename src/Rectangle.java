import java.util.Arrays;

public class Rectangle
{
    // 1 Establish Variables
    public double left;
    public double top;
    public double width;
    public double height;
    public double right;
    public double bottom;

    // 2 (Start Empty)  4 Establishes inital Constructor and sets default values of 0 using setValues
    public Rectangle()
    {

        setValues(0,0,0,0);

    }

    //  5 Converts Rectangles defaults to match what is defined by user
    public Rectangle (double left, double top, double width, double height)
    {

        setValues(left, top, width, height);

    }

    // 3 Establishes how values will be set for Rectangles
    public void setValues(double left, double top, double width, double height)
    {

        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
        this.right = left + width;
        this.bottom = top + height;

    }

    // 6 Allows us to move position while maintaining height and width
    public void setPosition(double left, double top)
    {

        setValues(left, top, this.width, this.height);

    }

    public void setSize(double width, double height)
    {
        setValues(this.left,this.top,width,height);
    }

    // 7 Checks to see whether a rectangle is Overlapping
    public boolean overlaps(Rectangle other)
    {

        boolean noOverlap = (other.right <= this.left)
                || (this.right <= other.left)
                ||(other.bottom <= this.top)
                ||(this.bottom <= other.top);

        return !noOverlap;
        // This ultimately checks to see if any are not over lapping and then we negate this return to that it is
    }

    public Vector getMinimumTranslationVector(Rectangle other)
    {
        Vector[] differences = {
                new Vector(other.right-this.left,0),
                new Vector(other.left - this.right,0),
                new Vector(0,other.bottom-this.top),
                new Vector(0,other.top - this.bottom)
        };

        Arrays.sort(differences);
        return differences[0];
    }

}
