import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public abstract class Game extends Application implements Screen
{
    public Canvas canvas;
    public GraphicsContext context;
    public Group group;
    public Stage stage;
    public Input input;

    public void start(Stage mainStage)
    {
        mainStage.setTitle("Game");
        mainStage.setResizable(false);

        Pane root = new Pane();
        Scene mainScene = new Scene(root);
        mainStage.setScene(mainScene);
        mainStage.sizeToScene();
        canvas = new Canvas(512, 512);
        context = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);

        group = new Group();

        input = new Input(mainScene);

        Game self = this;
        AnimationTimer gameloop = new AnimationTimer()
        {
            public void handle(long nanotime)
            {
                input.update();

                self.update();
                self.group.update(1/60.0);

                self.context.setFill(Color.GRAY);
                self.context.fillRect(0,0,self.canvas.getWidth(),self.canvas.getHeight());

                //render game objects
                self.group.draw(self.context);
            }
        };

        mainStage.show();

        stage = mainStage;
        initialize();
        gameloop.start();

    }

    public void setTitle(String title)
    {
        stage.setTitle(title);
    }

    public void setWindowSize(int width, int height)
    {
        stage.setWidth(width);
        stage.setHeight(height);
        canvas.setWidth(width);
        canvas.setHeight(height);
    }
}
