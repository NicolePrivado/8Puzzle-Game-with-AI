package eightpuzzle;

import eightpuzzle.GameStage;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Cell {
	private int value;
	private Image img;
	private ImageView imgView;
	private GameStage gameStage;
	private int row, col;

	protected final static int CELL_WIDTH =70;
	protected final static Image TILE_ONE = new Image("images/one.jpg",CELL_WIDTH,CELL_WIDTH,false,false);
	protected final static Image TILE_TWO = new Image("images/two.jpg",CELL_WIDTH,CELL_WIDTH,false,false);
	protected final static Image TILE_THREE = new Image("images/three.jpg",CELL_WIDTH,CELL_WIDTH,false,false);
	protected final static Image TILE_FOUR = new Image("images/four.jpg",CELL_WIDTH,CELL_WIDTH,false,false);
	protected final static Image TILE_FIVE = new Image("images/five.jpg",CELL_WIDTH,CELL_WIDTH,false,false);
	protected final static Image TILE_SIX = new Image("images/six.jpg",CELL_WIDTH,CELL_WIDTH,false,false);
	protected final static Image TILE_SEVEN = new Image("images/seven.jpg",CELL_WIDTH,CELL_WIDTH,false,false);
	protected final static Image TILE_EIGHT = new Image("images/eight.jpg",CELL_WIDTH,CELL_WIDTH,false,false);
	protected final static Image TILE_EMPTY = new Image("images/empty.jpg",CELL_WIDTH,CELL_WIDTH,false,false);

	public Cell(int value, GameStage gameStage){		//cells that can be clicked
		this.value = value;
		this.gameStage = gameStage;

		// load image depending on the value
		switch(this.value) {
			case 0: this.img = TILE_EMPTY; break;
			case 1: this.img = TILE_ONE; break;
			case 2: this.img = TILE_TWO; break;
			case 3: this.img = TILE_THREE; break;
			case 4: this.img = TILE_FOUR; break;
			case 5: this.img = TILE_FIVE; break;
			case 6: this.img = TILE_SIX; break;
			case 7: this.img = TILE_SEVEN; break;
			case 8: this.img = TILE_EIGHT; break;
		}

		this.setImageView();
		this.setMouseHandler();
	}

	public Cell(int value){						// cannot be clicked
		this.value = value;
		// load image depending on the value
		switch(this.value) {
			case 0: this.img = TILE_EMPTY; break;
			case 1: this.img = TILE_ONE; break;
			case 2: this.img = TILE_TWO; break;
			case 3: this.img = TILE_THREE; break;
			case 4: this.img = TILE_FOUR; break;
			case 5: this.img = TILE_FIVE; break;
			case 6: this.img = TILE_SIX; break;
			case 7: this.img = TILE_SEVEN; break;
			case 8: this.img = TILE_EIGHT; break;
		}
		this.setImageView();
	}

	private void setImageView() {
		// initialize the image view of this cell
		this.imgView = new ImageView();
		this.imgView.setImage(this.img);
		this.imgView.setLayoutX(0);
		this.imgView.setLayoutY(0);
		this.imgView.setPreserveRatio(true);
		this.imgView.setFitWidth(CELL_WIDTH);
		this.imgView.setFitHeight(CELL_WIDTH);

	}
	//initialize location of cell [row,col]
	protected void initRowCol(int i, int j) {
		this.row = i;
		this.col = j;
	}

	private void setMouseHandler(){
		Cell cell =this;
		this.imgView.setOnMouseClicked(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e) {
				while(true){
					int [][] puzzle = cell.gameStage.getGameBoard();
					if(cell.canMove(puzzle)){								//if cell has empty neighbor cell
						System.out.println("Tile clicked");
						if(Solution.isSolved(puzzle)){						// if puzzle solved
							gameStage.flashGameOver();
							System.out.println("PUZZLE SOLVED");
						}
					}
					break;
				}
			}
		});
	}

	private boolean canMove( int[][]puzzle){			//checks if the cell clicked can move
		int row = this.row;
		int col = this.col;

		if(this.value!=0){
			if((row+1)<3 && puzzle[row+1][col]==0){
				this.swapCell(gameStage.getCell(row+1, col),gameStage.getGameBoard());  //if cell can move, swap the cells
				return(true);
			}
			else if((row-1)>=0 && puzzle[row-1][col]==0){
				this.swapCell(gameStage.getCell(row-1, col),gameStage.getGameBoard());
				return(true);
			}
			else if((col+1)<3 && puzzle[row][col+1]==0){
				this.swapCell(gameStage.getCell(row, col+1),gameStage.getGameBoard());
				return(true);
			}
			else if((col-1)>= 0 && puzzle[row][col-1]==0){
				this.swapCell(gameStage.getCell(row, col-1),gameStage.getGameBoard());
				return(true);
			}
			else return(false);
		}
		else return(false);
	}

	protected void swapCell(Cell cellNew, int[][]puzzle) {
		puzzle[this.row][this.col] = cellNew.value;		//swap values in the gameBoard
		puzzle[cellNew.row][cellNew.col] = this.value;

		this.imgView.setImage(cellNew.img);		//swap image view in board
		cellNew.imgView.setImage(this.img);

		int tempValue = this.value;				//temp cell attributes
		Image tempImg = this.img;

		this.img = cellNew.img;					//swap cell image
		cellNew.img = tempImg;

		this.value = cellNew.value;				//swap cell value
		cellNew.value = tempValue;
	}

	protected ImageView getImageView(){
		return this.imgView;
	}

	protected Image getImage(){
		return this.img;
	}

	protected int getValue(){
		return this.value;
	}

	protected int getRow(){
		return this.row;
	}

	protected int getCol(){
		return this.col;
	}
}
