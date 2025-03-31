import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Scanner;

public class FractalGenerator extends Application {
    private static int WIDTH = 800;
    private static int HEIGHT = 800;
    private static int DEPTH;
    public static void main(String[] args) {

        //Retrieve depth of Koch snowflake to draw from the user. The koch snowflake will branch this many times
        Scanner input = new Scanner(System.in);
        System.out.println("What depth should the snowflake go to?");
        DEPTH = input.nextInt();

        //Launches canvas to draw the fractal
        launch(args);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Fractal Generator");

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //calls recursive method with user's submitted depth value to draw the koch snowflake
        drawFractal(gc, WIDTH / 3, HEIGHT / 2, WIDTH / 3, 0, DEPTH);

        //Displays canvas in a vbox. Then, places vbox centered in scene shown on primaryStage
        VBox vbox = new VBox();
        vbox.getChildren().add(canvas);
        Scene scene = new Scene(vbox, WIDTH, HEIGHT);
        vbox.setAlignment(Pos.CENTER);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //Recursive method to draw one side of the Koch snowflake
    private void drawFractal(GraphicsContext gc, double x, double y, double size, double angle, int depth) {

        //the base case for this method is a depth of 0. If 0 is fed to the method then it will return a straight line
        // drawn at the angle supplied.
        if (depth == 0) {
            double endX = x + size * Math.cos(Math.toRadians(angle));
            double endY = y + size * Math.sin(Math.toRadians(angle));
            gc.strokeLine(x, y, endX, endY);
        }

        //the recursive call is made if the depth is any number >0. It will return a line with a 60-degree angle in it
        // that spans the middle third of the length of the line supplied. The middle 3rd length of each line is found
        // by dividing the size(which is the width of the line) by 3.0. The angle to draw the line is carried by the
        // angle value by continually feeding it to the next recursive call. Each time this recursive call is made it
        // adds 4 more calls to the stack.
        else {
            size /= 3.0;
            drawFractal(gc, x, y, size, angle, depth - 1);
            x += size * Math.cos(Math.toRadians(angle));
            y += size * Math.sin(Math.toRadians(angle));
            drawFractal(gc, x, y, size, angle - 60, depth - 1);
            x += size * Math.cos(Math.toRadians(angle - 60));
            y += size * Math.sin(Math.toRadians(angle - 60));
            drawFractal(gc, x, y, size, angle + 60, depth - 1);
            x += size * Math.cos(Math.toRadians(angle + 60));
            y += size * Math.sin(Math.toRadians(angle + 60));
            drawFractal(gc, x, y, size, angle, depth - 1);
        }
    }
}
