package eightpuzzle;

import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class ButtonHandler {
	static Button solve, bfs, astar, dfs, next, openFile, solveAgain;
	static String actions;

	protected static void handleButtons(GameStage GS){	//creation of buttons and setting mouse handlers
		handleSolve();
		handleBFS(GS);
		handleDFS(GS);
		handleAstar(GS);
		handleNext(GS);
		handleSelectFile(GS);
	}

	protected static void handleBFS(GameStage GS){
		bfs = new Button("BFS");					//BFS button
		bfs.setLayoutX(GameStage.WINDOW_WIDTH*0.15);
		bfs.setLayoutY(GameStage.WINDOW_HEIGHT*0.7);

		bfs.setOnMouseClicked(new EventHandler<MouseEvent>() {		//BFS CLICKED
			public void handle(MouseEvent arg0) {
				next.setVisible(false);
				solve.setVisible(true);
				solve.setDisable(false);
				GS.getGC().clearRect(0,0, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
				GS.getGC().fillText("Solvable. You can do this!", GameStage.WINDOW_WIDTH*0.195, GameStage.WINDOW_HEIGHT*0.18);
				System.out.println("BFS");
				bfs.setStyle("-fx-background-color: #2e856e ;"		//changing of button graphics
								+ "-fx-border-width: 3 8 3 8" );
				bfs.setLayoutX(GameStage.WINDOW_WIDTH*0.143);
				bfs.setLayoutY(GameStage.WINDOW_HEIGHT*0.697);
				dfs.setStyle("-fx-background-color: #8abaae ;"
						+ "-fx-border-width: 1 6 1 6" );
				dfs.setLayoutX(GameStage.WINDOW_WIDTH*0.66);
				dfs.setLayoutY(GameStage.WINDOW_HEIGHT*0.7);
				astar.setStyle("-fx-background-color: #8abaae ;"
						+ "-fx-border-width: 1 6 1 6" );
				astar.setLayoutX(GameStage.WINDOW_WIDTH*0.349);
				astar.setLayoutY(GameStage.WINDOW_HEIGHT*0.7);
				solve.setOnMouseClicked(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent arg0) {
						System.out.println("BFS Solution");
						GS.getRoot().getChildren().removeAll(bfs,dfs,astar);
						clickedSolution(GS);  //changing puzzle and displayed buttons
						int n = Solution.BFSearch(GS.getGameBoard());
						setSolutionActions();       // getting the actions from puzzle.out
						GS.getGC().fillText("Path Cost: "+ n, GameStage.WINDOW_WIDTH*0.15, GameStage.WINDOW_HEIGHT*0.93);
					}
				});
			}
		});
	}
	protected static void handleDFS(GameStage GS){
		dfs = new Button("DFS");					//DFS button
		dfs.setLayoutX(GameStage.WINDOW_WIDTH*0.66);
		dfs.setLayoutY(GameStage.WINDOW_HEIGHT*0.7);

		dfs.setOnMouseClicked(new EventHandler<MouseEvent>() {		//DFS CLICKED
			public void handle(MouseEvent arg0) {
				next.setVisible(false);
				solve.setVisible(true);
				solve.setDisable(false);
				System.out.println("DFS");
				GS.getGC().clearRect(0,0, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
				GS.getGC().fillText("Solvable. You can do this!", GameStage.WINDOW_WIDTH*0.195, GameStage.WINDOW_HEIGHT*0.18);
				dfs.setStyle("-fx-background-color: #2e856e ;"		//changing of button graphics
						+ "-fx-border-width: 3 8 3 8" );
				dfs.setLayoutX(GameStage.WINDOW_WIDTH*0.653);
				dfs.setLayoutY(GameStage.WINDOW_HEIGHT*0.697);
				bfs.setStyle("-fx-background-color: #8abaae ;"
						+ "-fx-border-width: 1 6 1 6" );
				bfs.setLayoutX(GameStage.WINDOW_WIDTH*0.15);
				bfs.setLayoutY(GameStage.WINDOW_HEIGHT*0.7);
				astar.setStyle("-fx-background-color: #8abaae ;"
						+ "-fx-border-width: 1 6 1 6" );
				astar.setLayoutX(GameStage.WINDOW_WIDTH*0.349);
				astar.setLayoutY(GameStage.WINDOW_HEIGHT*0.7);
				solve.setOnMouseClicked(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent arg0) {
						System.out.println("DFS Solution");
						GS.getRoot().getChildren().removeAll(bfs,dfs,astar);
						clickedSolution(GS);	//changing puzzle and displayed buttons
						int n = Solution.DFSearch(GS.getGameBoard());
						setSolutionActions();		//getting the actions from puzzle.out
						GS.getGC().fillText("Path Cost: "+ n, GameStage.WINDOW_WIDTH*0.15, GameStage.WINDOW_HEIGHT*0.93);
					}
				});
			}
		});
	}
	protected static void handleAstar(GameStage GS){
		astar = new Button("A*Search");			//A* button
		astar.setLayoutX(GameStage.WINDOW_WIDTH*0.349);
		astar.setLayoutY(GameStage.WINDOW_HEIGHT*0.7);

		astar.setOnMouseClicked(new EventHandler<MouseEvent>() {		//A*Search CLICKED
			public void handle(MouseEvent arg0) {
				next.setVisible(false);
				solve.setVisible(true);
				solve.setDisable(false);
				System.out.println("A* Search");
				GS.getGC().clearRect(0,0, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
				GS.getGC().fillText("Solvable. You can do this!", GameStage.WINDOW_WIDTH*0.195, GameStage.WINDOW_HEIGHT*0.18);
				astar.setStyle("-fx-background-color: #2e856e ;"		//changing of button graphics
						+ "-fx-border-width: 3 8 3 8;" );
				astar.setLayoutX(GameStage.WINDOW_WIDTH*0.342);
				astar.setLayoutY(GameStage.WINDOW_HEIGHT*0.697);
				bfs.setStyle("-fx-background-color: #8abaae ;"
						+ "-fx-border-width: 1 6 1 6" );
				bfs.setLayoutX(GameStage.WINDOW_WIDTH*0.15);
				bfs.setLayoutY(GameStage.WINDOW_HEIGHT*0.7);
				dfs.setStyle("-fx-background-color: #8abaae ;"
						+ "-fx-border-width: 1 6 1 6" );
				dfs.setLayoutX(GameStage.WINDOW_WIDTH*0.66);
				dfs.setLayoutY(GameStage.WINDOW_HEIGHT*0.7);
				solve.setOnMouseClicked(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent arg0) {
						System.out.println("A* Solution");
						GS.getRoot().getChildren().removeAll(bfs,dfs,astar);
						clickedSolution(GS);	//changing puzzle and displayed buttons
						int n = Solution.AStar(GS.getGameBoard());
						setSolutionActions();		//getting the actions from puzzle.out
						GS.getGC().fillText("Path Cost: "+ n, GameStage.WINDOW_WIDTH*0.15, GameStage.WINDOW_HEIGHT*0.93);
					}
				});
			}
		});
	}

	protected static void handleSolve(){
		solve = new Button("Solution");			//Solution button
		solve.setLayoutX(GameStage.WINDOW_WIDTH*0.36);
		solve.setLayoutY(GameStage.WINDOW_HEIGHT*0.82);
		solve.setDisable(true);
	}

	protected static void handleNext(GameStage GS){
		next = new Button("Next");
		next.setLayoutX(GameStage.WINDOW_WIDTH*0.40);
		next.setLayoutY(GameStage.WINDOW_HEIGHT*0.82);
		next.setVisible(false);

		next.setOnMouseClicked(new EventHandler<MouseEvent>() {		//next clicked
			public void handle(MouseEvent arg0) {
					moveTile(GS.getGameBoard(),GS);
			}
		});
	}
	protected static void handleSelectFile(GameStage GS){
		openFile = new Button("Select file...");	//Select file button
		openFile.setLayoutX(GameStage.WINDOW_WIDTH*0.335);
		openFile.setLayoutY(GameStage.WINDOW_HEIGHT*0.05);

		openFile.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				FileChooser fileChooser = new FileChooser();		// https://docs.oracle.com/javase/8/javafx/api/javafx/stage/FileChooser.html
				fileChooser.setTitle("Select Puzzle Board");
				File selectedFile = fileChooser.showOpenDialog(GS.getStage());

				GS.setPuzzleBoard(selectedFile);
				GS.restartGame();
			}
		});
	}
	protected static void handleTryAgain(GameStage GS){
		solveAgain = new Button("Solve Again");
		solveAgain.setLayoutX(GameStage.WINDOW_WIDTH*0.33);
		solveAgain.setLayoutY(GameStage.WINDOW_HEIGHT*0.74);
		solveAgain.setStyle("-fx-background-color: #528aae ;");

		solveAgain.setOnMouseClicked(new EventHandler<MouseEvent>() {		//next clicked
			public void handle(MouseEvent arg0) {
				GS.restartGame();
			}
		});
	}

	private static void clickedSolution(GameStage GS){	//when solution button is clicked
		GS.initGameBoard();
		GS.createMap2();
		GS.getRoot().getChildren().addAll(bfs,dfs,astar);
		solve.setVisible(false);
		next.setVisible(true);
	}

	private static void setSolutionActions(){		// assigning to String actions the scanned line from puzzle.out
		try{
			FileReader fr = new FileReader("./src/eightpuzzle/puzzle.out");
			Scanner inFile = new Scanner(fr);

			actions = inFile.nextLine();//scanning line
			inFile.close();
		}
		catch(Exception e){}

	}

	private static void moveTile(int[][] puzzle,GameStage GS){		//moving the tiles by clicking next
		int i=0,j=0;
		for(int a=0;a<3;a++){		//find index of empty cell
			for(int b=0;b<3;b++){
				if(puzzle[a][b]==0){
					i=a;
					j=b;
					break;
				}
			}
		}
		char x = actions.charAt(0);
		System.out.print(x+" ");
		Cell empty = GS.getCell(i,j);
		if(x==Solution.UP){	        //if the action read from the puzzle.out is UP
			empty.swapCell(GS.getCell(i-1,j),puzzle);
		}
		else if(x==Solution.RIGHT){
			empty.swapCell(GS.getCell(i,j+1),puzzle);
		}
		else if(x==Solution.DOWN){
			empty.swapCell(GS.getCell(i+1,j),puzzle);
		}
		else{
			empty.swapCell(GS.getCell(i,j-1),puzzle);
		}
		if(Solution.isSolved(puzzle)){
			System.out.println(); System.out.println("PUZZLE SOLVED");
			GS.flashGameOver();
		}
		if(actions.length()>1){
			actions=actions.substring(2,actions.length());
		}
	}
}
