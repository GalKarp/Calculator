package application;
	
import java.io.IOException;

import javafx.animation.FillTransition;
import javafx.animation.FillTransitionBuilder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.util.Duration;


public class Main extends Application {
	double xOffset;
	double yOffset;

	
	@Override
	public void start(final Stage primaryStage) {
		try {
			primaryStage.initStyle(StageStyle.TRANSPARENT);//For transparency
			Parent root = FXMLLoader.load(getClass().getResource("Calculator2.fxml"));
			Parent root2 = FXMLLoader.load(getClass().getResource("try.fxml"));

			
			Scene scene = new Scene(root,630,500);
			Scene scene2 = new Scene(root2,630,500);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			Controller.setPrimaryScene(primaryStage);
			Controller.saveSceneSize(scene.getHeight(), scene.getWidth());
			Controller.setPrimaryScene(primaryStage);

			scene.setFill(null);//for transparency
			
			

			
			//drag
			scene.setOnMousePressed(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	                xOffset =  event.getSceneX();
	                yOffset =  event.getSceneY();
	            }
	        });
	        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	                primaryStage.setX(event.getScreenX() - xOffset);
	                primaryStage.setY(event.getScreenY() - yOffset);
	            }
	        });
			//end drag
	        			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}


}

