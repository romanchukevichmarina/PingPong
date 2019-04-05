    
package applicalion;
	

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;


public class Main extends Application {
	
	// ðàçìåð ïîëÿ
	private static final int width = 800;
	private static final int height = 600;
	
	// øèðèíà è âûñîòà ðàêåòêà
	private static final int RACKET_WIDTH = 10;
	private static final int RACKET_HEIGHT = 90;
	
	// ðàäèóñ ìÿ÷à
	private static final int BALL_RAD = 30;
	
	// íà÷àëüíûå êîîðäèíàòû ðàêåòêè èãðîêà
	double playerX=0;
	double playerY = height/2;
	
	// íà÷àëüíûå êîîðäèíàòû ðàêåòêè êîìïà
	double compX = width - RACKET_WIDTH;
	double compY = height/2;
	
	// êîîðäèíàòû ìÿ÷à
	double ballX = width/2;
	double ballY = height/2;
	
	// èíñòðóìåíò ðèñîâàíè¤
	GraphicsContext gc;
	
	// ñêîðîñòü ìÿ÷à
	double ballYSpeed = 3;
	double ballXSpeed = 3;
	int a = 0;
	int b = 0;
	int count = 0;

	
	// èãðîâîé öèêë
	boolean gameStarted;
	
	private void drawTable() {
		// ðèñóåì ïîëå
		gc.setFill(Color.GREEN);
		gc.fillRect(0, 0, width, height);			
		// ðèñóåì ðàçäåëèòåëüíóþ ëèíèþ
		gc.setFill(Color.YELLOW);
		gc.fillRect(width/2, 0, 2, height);
		//
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(2.5);
		gc.strokeText("0 : " + count, width/2, 30);	
		//
		gc.setFill(Color.YELLOW);
		// ðèñóåì ì¤÷(ballX <= (height +2.24*BALL_RAD))
		if(gameStarted) {
			//____________________________
			if (ballY+BALL_RAD == height) {
				a = 1;
			} 
			if (ballY == 0) {
				a = 0;
			} 
			if (ballX+BALL_RAD >= width-5 ) {
				b = 1;
			} 
			if ((ballY > playerY-RACKET_HEIGHT) && (ballY < playerY+RACKET_HEIGHT ) && (ballX >= 0) && (ballX <= 5)) {
				b = 0;
			}
			
			//____________________________
			if (a == 1 ) {
				ballYSpeed = -1.5;
			}
			if (a == 0 ) {
				ballYSpeed = 1.5;
			}
			if (b == 1) {
				ballXSpeed = -1.5;
			}
			if (b == 0) {
				ballXSpeed = 1.5;
			}
			
			//_____________________________
			ballX+=ballXSpeed;
			ballY+=ballYSpeed;
			
			if (ballX < -30) {
				gameStarted = false;
				ballX = width/2;
				ballY = height/2;
				ballYSpeed = 3;
				ballXSpeed = 3;
				count+=1;
				
				
				
			}
			// ëîãèêà - êîìï îòáèâàåò ìÿ÷
			if(ballX < width) {
				compY = ballY - RACKET_HEIGHT/2;
			}
			gc.fillOval(ballX, ballY, BALL_RAD, BALL_RAD);
		} else {
			gc.setStroke(Color.YELLOW);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setLineWidth(1);
			gc.strokeText("Click to start", width/2, height/2);			
		}
	
		// ðèñóåì ðàêåòêè
		gc.fillRect(playerX, playerY, RACKET_WIDTH, RACKET_HEIGHT);
		gc.fillRect(compX, compY, RACKET_WIDTH, RACKET_HEIGHT);	
	}
	
	@Override
	public void start(Stage root) {
		Canvas canvas = new Canvas(width,height);
		gc = canvas.getGraphicsContext2D();
		drawTable();		
		Timeline t1 = new Timeline(new KeyFrame(Duration.millis(10), e->drawTable()));
		t1.setCycleCount(Timeline.INDEFINITE);
		
		canvas.setOnMouseClicked(e -> gameStarted = true);
		canvas.setOnMouseMoved(e -> playerY = e.getY());
		
		root.setScene(new Scene(new StackPane(canvas)));
		root.setTitle("Ping-pong");
		root.show();
		t1.play();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}