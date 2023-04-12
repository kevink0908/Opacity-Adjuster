// Name: Kevin Kim
// Course: CS 2450
// Project: Assignment 3 Task B
// Date: 4/9/2023
// Description: This program allows the user to select
//				an image file via a FileChooser dialog.

package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TaskB extends Application
{   

	// set the width and height values for the scene.
	private final double SCENE_WIDTH = 1000.0;
	private final double SCENE_HEIGHT = 1000.0;
	
	private Button select = new Button("Select an Image File");
    private ImageView myImageView;

	public static void main(String[] args)
	{
		launch(args);
	}     

	@Override
	public void start(Stage primaryStage)
	{
		// set the title for the stage.
    	primaryStage.setTitle("Assignment 3 Task B: Opacity Adjuster");
		
    	// provide a Slider control that allows the user
    	// to adjust the image's opacity.
    	Slider slider = new Slider(0, 1, 0.5);
    	
        // enable the marks and  labels for the Slider control.
    	slider.setShowTickMarks(true);
    	slider.setShowTickLabels(true);
    	
    	// set the major and minor tick units along with the slider's width.
    	slider.setMajorTickUnit(0.1);
    	slider.setMinorTickCount(1);
    	slider.setSnapToTicks(true);
    	slider.setPrefWidth(50);
    	
    	// check to see if the user pressed the "Select an Image File" button.
    	select.setOnAction(e ->
    	{
    		FileChooser fileChooser = new FileChooser();

    		// allow JPG and PNG file extensions.
    		FileChooser.ExtensionFilter JPG = 
    				new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
    		FileChooser.ExtensionFilter jpg = 
    				new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
    		FileChooser.ExtensionFilter PNG = 
    				new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
    		FileChooser.ExtensionFilter png = 
    				new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
    		FileChooser.ExtensionFilter HEIC = 
    				new FileChooser.ExtensionFilter("HEIC files (*.HEIC)", "*.HEIC");
    		FileChooser.ExtensionFilter heic = 
    				new FileChooser.ExtensionFilter("heic files (*.heic)", "*.heic");
    		fileChooser.getExtensionFilters().addAll(JPG, jpg, PNG, png, HEIC, heic);

        	// allow the user to select an image file via a FileChooser dialog.
    		File file = fileChooser.showOpenDialog(null);

        	// check to see if the user pressed the "Cancel" button.
        	if (file == null)
        	{
        		// if so, simply exit the program.
        		System.exit(0);
        	}

        	// otherwise, try to display the image.
    		try 
    		{
    			BufferedImage bufferedImage = ImageIO.read(file);
    			Image selectedImage = SwingFXUtils.toFXImage(bufferedImage, null);
    			myImageView.setImage(selectedImage);
    		} 
    		catch (IOException ex) 
    		{
    			Logger.getLogger(TaskB.class.getName()).log(Level.SEVERE, null, ex);
    		}
    	});
    	
    	// then, let the user adjust the image's opacity.
    	FadeTransition ft = new FadeTransition(Duration.millis(1000),myImageView);

    	slider.valueProperty().addListener((observable, oldVal, newVal) ->{
           ft.setFromValue(oldVal.doubleValue());
           ft.setToValue(newVal.doubleValue());
           ft.setNode(myImageView);
           ft.playFromStart();
    	});

    	myImageView = new ImageView();  
    	myImageView.setFitWidth(500);
    	myImageView.setPreserveRatio(true);

        // Create the scene and display it.
    	VBox vBox = new VBox(10);
    	vBox.getChildren().addAll(myImageView, select, slider);
    	vBox.setAlignment(Pos.BOTTOM_CENTER);
    	vBox.setStyle("-fx-background-color: BLACK");
    	Scene scene = new Scene(vBox, SCENE_WIDTH, SCENE_HEIGHT);

    	primaryStage.setScene(scene);
    	primaryStage.show();

    }
}
