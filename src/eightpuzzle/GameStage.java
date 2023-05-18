package eightpuzzle;

import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameStage {
	private Group root;
	private GraphicsContext gc;
	private Scene scene;
	private Stage stage;
	private Canvas canvas;
	private GridPane map;
	private int[][] gameBoard;
	private ImageView imgView;
	private ArrayList<Cell> tiles;
	private File puzzleBoard;

	protected final static int MAX_CELLS = 9;
	protected final static int MAP_NUM_ROWS = 3;
	protected final static int MAP_NUM_COLS = 3;
	protected final static int MAP_WIDTH = 250;
	protected final static int MAP_HEIGHT = 250;
	protected final static int WINDOW_WIDTH =302;
	protected final static int WINDOW_HEIGHT = 490;
	protected final static Image SOLVED = new Image("images/solved.jpg",216,216,false,false);

	public GameStage (){
		this.root = new Group();
		this.root.getStylesheets().add(getClass().getResource("puzzle.css").toExternalForm());  //css for the buttons
		this.scene = new Scene(root, WINDOW_WIDTH,WINDOW_HEIGHT,Color.WHITE);
		this.canvas = new Canvas(WINDOW_WIDTH,WINDOW_HEIGHT);
		this.map = new GridPane();
		this.tiles = new ArrayList<Cell>();
		this.gameBoard = new int[MAP_NUM_ROWS][MAP_NUM_COLS];
		this.puzzleBoard = new File("./src/eightpuzzle/puzzle.in");
	}
	//method to add the stage elements
	public void setStage(Stage stage) {
		this.stage = stage;
		this.gc = canvas.getGraphicsContext2D();
		Font theFont = Font.font("Century Gothic",15);//set font type, style and size
		this.gc.setFont(theFont);
		this.gc.setFill(Color.GRAY);

		this.initGameBoard();	//initialize game board based on puzzleBoard
		this.createMap();

		ButtonHandler.handleButtons(this);
		//set stage elements here
		this.root.getChildren().addAll(canvas,map,ButtonHandler.openFile);
		if(checkSolvability(this.gameBoard)){		//if puzzle is solvable, display the buttons
			this.root.getChildren().addAll(ButtonHandler.solve,ButtonHandler.next,ButtonHandler.dfs,ButtonHandler.bfs,ButtonHandler.astar);
		}
		this.stage.setTitle("Eight Puzzle");
		this.stage.setScene(this.scene);
		this.stage.show();
	}

	protected void initGameBoard(){
		try{
			//reading puzzleboard
			FileReader fr = new FileReader(this.puzzleBoard);
			Scanner inFile = new Scanner(fr);
			for(int i=0;i<MAP_NUM_ROWS;i++){
				String line = inFile.nextLine();			//scanning line
				String[] lineArray = line.split(" ");		//splitting the string
				for(int j=0;j<MAP_NUM_COLS;j++){
				this.gameBoard[i][j] = Integer.parseInt(lineArray[j]);	//string to int
				}
			}
			inFile.close();
		}
		catch(Exception e){}
		this.checkSolvability(this.gameBoard);
	}

	private boolean checkSolvability(int[][] gameBoard){		//idea/algorithm from https://math.stackexchange.com/q/838818
		int inversionCounter = 0;			//counter for inversions found
		ArrayList<Integer> numbers;			//1D array for the numbers
		numbers = new ArrayList<Integer>();

		for(int i=0;i<MAP_NUM_ROWS;i++){
			for(int j=0;j<MAP_NUM_COLS;j++){
				if(gameBoard[i][j] != 0){				// if the value of a tile is not zero, it will be appended to the arraylist
					numbers.add(gameBoard[i][j]);
				}
			}
		}
		for(int i=0;i<7;i++){
			for(int j=i+1;j<8;j++){
				if(numbers.get(i) > numbers.get(j)){	// checking the arraylist if there are inversions
					inversionCounter++;
				}
			}
		}
		if(inversionCounter % 2 == 0){		//prompt for solvability
			this.gc.fillText("Solvable. You can do this!", WINDOW_WIDTH*0.195, WINDOW_HEIGHT*0.18);
			return(true);
		}
		else{
			this.gc.fillText("Non-solvable.", WINDOW_WIDTH*0.34, WINDOW_HEIGHT*0.18);
			this.gc.fillText("No matter how hard you try.", WINDOW_WIDTH*0.16, WINDOW_HEIGHT*0.73);
			return(false);
		}
	}

	protected void createMap(){			// create map with clickable tiles
		this.tiles.clear();
		//create 9 number cells
		for(int i=0;i<MAP_NUM_ROWS;i++){
			for(int j=0;j<MAP_NUM_COLS;j++){

				// Instantiate cells
				Cell num = new Cell(gameBoard[i][j],this);
				num.initRowCol(i,j);

				//add each tile to the array list tiles
				this.tiles.add(num);
			}
		}
		this.setGridPaneProperties();
		this.setGridPaneContents(this.tiles);
	}

	protected void createMap2(){			//create map for non-clickable tiles
		this.tiles.clear();
		//create 9 number cells
		for(int i=0;i<MAP_NUM_ROWS;i++){
			for(int j=0;j<MAP_NUM_COLS;j++){

				// Instantiate cells
				Cell num = new Cell(gameBoard[i][j]);
				num.initRowCol(i,j);

				//add each tile to the array list tiles
				this.tiles.add(num);
			}
		}
		this.setGridPaneProperties();
		this.setGridPaneContents(this.tiles);
	}

	//method to set size and location of the grid pane
	private void setGridPaneProperties(){
		this.map.setPrefSize(MAP_WIDTH, MAP_HEIGHT);
		//set the map to x and y location;
		this.map.setLayoutX(WINDOW_WIDTH*0.14);
	    this.map.setLayoutY(WINDOW_HEIGHT*0.22);
	    this.map.setVgap(4);
	    this.map.setHgap(4);
	}

	//method to add row and column constraints of the grid pane
	private void setGridPaneContents(ArrayList<Cell> tiles){
		 //loop that will set the constraints of the elements in the grid pane
	     int counter=0;
	     for(int row=0;row<MAP_NUM_ROWS;row++){
	    	 for(int col=0;col<MAP_NUM_COLS;col++){
	    		 // map each cell's constraints
	    		 GridPane.setConstraints(tiles.get(counter).getImageView(),col,row);
	    		 counter++;
	    	 }
	     }
	   //loop to add each cell to the gridpane/map
	     for(Cell num: tiles){
	    	 this.map.getChildren().add(num.getImageView());
	     }
	}

	protected void restartGame(){
		root.getChildren().clear();
		map.getChildren().clear();
		gc.clearRect(0,0, WINDOW_WIDTH, WINDOW_HEIGHT);
		setStage(this.stage);
	}
	private void setGameOver(){  //setting the completed image puzzle and the text
		imgView = new ImageView();
		imgView.setImage(SOLVED);
		imgView.setLayoutX(GameStage.WINDOW_WIDTH*0.14);
		imgView.setLayoutY(GameStage.WINDOW_HEIGHT*0.22);
		imgView.setPreserveRatio(true);
		imgView.setFitWidth(216);
		imgView.setFitHeight(216);
		gc.clearRect(0,0, WINDOW_WIDTH, WINDOW_HEIGHT);
		Font theFont = Font.font("Century Gothic",16);//set font type, style and size
		gc.setFont(theFont);
		gc.setFill(Color.CADETBLUE);
		gc.fillText("Congratulations! You won!", GameStage.WINDOW_WIDTH*0.152, GameStage.WINDOW_HEIGHT*0.18);
	}

	protected void flashGameOver(){		//when win
		root.getChildren().clear();
		setGameOver();
		//changed root children (win)
		ButtonHandler.handleTryAgain(this);
		root.getChildren().addAll(canvas,imgView,ButtonHandler.solveAgain);
	}

	protected int[][] getGameBoard(){	//return puzzle
		return this.gameBoard;
	}

	protected Cell getCell(int row, int col){	//getting the cell based on given row and col
		int value = gameBoard[row][col];
		Cell cell = tiles.get(0);
		for(Cell num: tiles){
	    	 if(num.getValue()==value){
	    		 cell=num;
	    	 }
	     }
		return(cell);
	}
	protected void setPuzzleBoard(File file){
		this.puzzleBoard = file;
	}
	protected Stage getStage(){
		return(this.stage);
	}
	protected Group getRoot(){
		return(this.root);
	}
	protected GraphicsContext getGC(){
		return(this.gc);
	}
}
