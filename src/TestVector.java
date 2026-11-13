public class TestVector
{
    public static void main(String[] args)
    {
        System.out.println("Testing the Vector Class...");

        Vector v = new Vector(2,3);
        System.out.println(v); // < 2.0 , 3.0 >

        v.multiply(5); // < 10.0 , 15.0 >
        v.addValues(500,600); // < 510.0 , 615.0 >
        System.out.println(v);

        Vector w = new Vector(1,1);
        System.out.println(w.getLength()); //1.414
        System.out.println(w.getAngle()); //45.0
        System.out.println(w);

        v.setLength(1.414);
        v.setAngle(45);
        System.out.println(v); //About < 1, 1 >
    }
}
