import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
//import javafx.scene.control.Label;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TestJavaFX extends Application
{
    public double x;
    public double y;

    public void start(Stage mainStage)
    {
        mainStage.setTitle("My First JavaFX APP");
        Pane root = new Pane();
        Scene mainScene = new Scene(root, 800, 600);
        mainStage.setScene(mainScene);
        //Label message = new Label("Hello World");
        Canvas canvas = new Canvas(800,600);
        GraphicsContext context = canvas.getGraphicsContext2D();
        Image kirby = new Image("Images/kirby.png",100,100,true,true);
        context.drawImage(kirby,10,10);


        root.getChildren().add(canvas);
        //message.setLayoutX(400);
        //message.setLayoutY(300);

        x = 10;
        y = 10;

        AnimationTimer timer = new AnimationTimer()
        {
            public void handle(long nanoTime)
            {
                //Clear canvas
                context.setFill(Color.ALICEBLUE);
                context.fillRect(0,0,800,600);
                x += 2;
                y += 1;
                context.drawImage(kirby,x,y);
            }
        };

        timer.start();
        mainStage.show();


    }

    public static void main(String[] args)
    {
        try
        {
            launch(args);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            System.exit(0);
        }

    }


}
