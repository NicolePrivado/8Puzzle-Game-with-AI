package eightpuzzle;

import java.util.ArrayList;

public class State {
	private int[][] puzzle;
	private int row,col;
	private char action;
	private State parent;
	private int g,h,f;

	public State(int[][] puzzle, int row, int col, char action, State parent){   //struct state
		this.puzzle = puzzle;
		this.row = row;			//row and col of the empty cell
		this.col = col;
		this.action = action;
		this.parent = parent;
		this.computeF();
	}

	public ArrayList<Character> getActions(){   //returns an arraylist of possible actions based on the location of the empty cell
		ArrayList<Character> actions= new ArrayList<Character>();
		int i = this.row;
		int j = this.col;
		if(i>0){
			actions.add(Solution.UP);
		}
		if(j<2){
			actions.add(Solution.RIGHT);
		}
		if(i<2){
			actions.add(Solution.DOWN);
		}
		if(j>0){
			actions.add(Solution.LEFT);
		}
		return(actions);
	}

	public State getResult(char action){		//returns the next state based on the action provided
		int[][]puzzle = this.puzzle;
		int[][]nextPuzzle = new int[3][3];
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				nextPuzzle[i][j]=puzzle[i][j];    //copying the current's puzzle to the next puzzle
			}
		}
		int i = this.row;
		int j = this.col;

		if(action==Solution.UP){
			nextPuzzle[i][j] = nextPuzzle[i-1][j];			//swapping of values
			nextPuzzle[i-1][j] = 0;
			State state = new State(nextPuzzle,i-1,j,Solution.UP,this);   //creating a new state
			return(state);
		}
		else if(action==Solution.RIGHT){
			nextPuzzle[i][j] = nextPuzzle[i][j+1];
			nextPuzzle[i][j+1] = 0;
			State state = new State(nextPuzzle,i,j+1,Solution.RIGHT,this);
			return(state);
		}
		else if(action==Solution.DOWN){
			nextPuzzle[i][j] = nextPuzzle[i+1][j];
			nextPuzzle[i+1][j] = 0;
			State state = new State(nextPuzzle,i+1,j,Solution.DOWN,this);
			return(state);
		}
		else{
			nextPuzzle[i][j] = nextPuzzle[i][j-1];
			nextPuzzle[i][j-1] = 0;
			State state = new State(nextPuzzle,i,j-1,Solution.LEFT,this);
			return(state);
		}
	}

	private void computeF(){
		this.computeG();
		this.computeH();
		this.f=this.g + this.h;
	}
	private void computeG(){
		int g ;
		if(this.parent==null){	//root
			g = 0;
		}
		else{
			g = this.parent.g+1;
		}
		this.g=g;
	}

	private void computeH(){
		int n,h =0;
		for(int i=0;i<3;i++){
			for(int  j=0;j<3;j++){
				n = this.puzzle[i][j];
				if(n!=0){
					h = h + (Math.abs(getCorrectRow(n)-i)+Math.abs(getCorrectCol(n)-j));
				}
			}
		}
		this.h=h;
	}

	private int getCorrectRow(int n){
		int [][] correct = Solution.solved;
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				 if(correct[i][j]==n){
					 return i;
				 }
			}
		}
		return 0;
	}

	private int getCorrectCol(int n){
		int [][] correct = Solution.solved;
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				 if(correct[i][j]==n){
					 return j;
				 }
			}
		}
		return 0;
	}

	protected int[][] getPuzzle(){
		return(this.puzzle);
	}

	protected int getEmptyRow(){
		return(this.row);
	}

	protected int getEmptyCol(){
		return(this.col);
	}

	protected char getAction(){
		return(this.action);
	}

	protected State getParent(){
		return(this.parent);
	}

	protected int getG(){
		return(this.g);
	}

	protected int getF(){
		return(this.f);
	}

	protected int getH(){
		return(this.h);
	}
}
