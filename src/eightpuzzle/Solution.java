package eightpuzzle;

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

import eightpuzzle.State;

public class Solution {

	private static ArrayList<State> frontier = new ArrayList<State>();
	private static ArrayList<State> explored = new ArrayList<State>();   //list of puzzles that is already explored
	private static State currentState;
	protected static final int[][] solved= {{1,2,3},{4,5,6},{7,8,0}};
	protected static final char UP = 'U';
	protected static final char RIGHT = 'R';
	protected static final char DOWN = 'D';
	protected static final char LEFT = 'L';
	protected static final char NA = 'X';

	private static State setInitialState(int[][]puzzle){ //setting the initial state (null parent)
		int row=0,col=0;
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				if(puzzle[i][j]==0){
					row = i;
					col = j;
					break;
				}
			}
		}
		State state = new State(puzzle,row,col,NA,null);
		return(state);
	}

	protected static int BFSearch(int[][] puzzle){		// Queue structure
		frontier.clear();
		explored.clear();
		State initial = setInitialState(puzzle);
		frontier.add(initial);				//enqueue

		while(frontier.size()!=0){
			currentState = frontier.get(0);
			frontier.remove(0);				//dequeue
			explored.add(currentState);
			if(isSolved(currentState.getPuzzle())){			//check if puzzle is the goal puzzle
				//found
				break;
			}
			else{
				for(char a: currentState.getActions()){
					State nextState = currentState.getResult(a);
			    	 if(!(inList(nextState,explored))&&!(inList(nextState,frontier))){ //check if next state's puzzle has been explored
			    		 frontier.add(nextState);		//enqueue
			    	 }
				}
			}
		}
		int n = pathCost(currentState);
		System.out.println("Path Cost	: "+ n );;  //writing to puzzle.out
		System.out.println("Explored States	: "+ explored.size());
		return n;
	}

	protected static int DFSearch(int[][] puzzle){			//Stack structure
		frontier.clear();
		explored.clear();
		State initial = setInitialState(puzzle);
		frontier.add(initial);							//push

		while(frontier.size()!=0){
			currentState = frontier.get(frontier.size()-1);
			frontier.remove(frontier.size()-1);				//pop
			explored.add(currentState);
			if(isSolved(currentState.getPuzzle())){			//check if puzzle is the goal puzzle
				//found
				break;
			}
			else{
				for(char a: currentState.getActions()){
					State nextState = currentState.getResult(a);
			    	 if(!(inList(nextState,explored))&&!(inList(nextState,frontier))){ //check if next state's puzzle has been explored
			    		 frontier.add(nextState);	//push
			    	 }
				}
			}
		}
		int n = pathCost(currentState);
		System.out.println("Path Cost	: "+ n );;  //writing to puzzle.out
		System.out.println("Explored States	: "+ explored.size());
		return n;
	}

	protected static int AStar(int[][] puzzle){
		frontier.clear();						//openList
		explored.clear();						//closedList
		State initial = setInitialState(puzzle);
		frontier.add(initial);

		while(frontier.size()!=0){
			currentState = frontier.get(findMinF(frontier));
			frontier.remove(findMinF(frontier));				//removeMinF
			explored.add(currentState);
			if(isSolved(currentState.getPuzzle())){			//check if puzzle is the goal puzzle
				//found
				break;
			}
			for(char a: currentState.getActions()){
				State nextState = currentState.getResult(a);
				//check if next state's puzzle is not in openlist or closedlist. Or if it's in the openlist(frontier), check if its g is lower than the duplicate's g
		    	 if((!(inList(nextState,explored))&&!(inList(nextState,frontier)))||(inList(nextState,frontier) && (nextState.getG() < getDuplicate(nextState,frontier).getG()))){
		    		 frontier.add(nextState);	//add to openlist
		    	 }
			}
		}
		int n = pathCost(currentState);
		System.out.println("Path Cost	: "+ n );;  //writing to puzzle.out
		System.out.println("Explored States	: "+ explored.size());
		return n;
	}

	protected static boolean isSolved(int[][] puzzle){			//Goal test
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				if(puzzle[i][j]!=solved[i][j]){	//check if puzzle is solved
					return(false);
				}
			}
		}
		return(true);
	}

	private static boolean inList(State state, ArrayList<State> list ){		//check if state is in frontier
		for(State x : list){
			if(checkSamePuzzle(x.getPuzzle(),state.getPuzzle())){
				return(true);
			}
		}
		return(false);
	}

	private static boolean checkSamePuzzle(int[][] puzzle1, int[][] puzzle2){	// compare 2 puzzles
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				if(puzzle1[i][j]!=puzzle2[i][j]){
					return(false);
				}
			}
		}
		return(true);
	}

	private static int findMinF(ArrayList<State> list){   //finding the index of the state in openlist that has the lowest value of f
		int min=list.get(0).getF();
		int index = 0;

		for(int i=1;i<list.size();i++){
			int tempF = list.get(i).getF();
			if( tempF < min){
				min = tempF;
				index = i;
			}
		}
		return index;
	}

	private static State getDuplicate(State state, ArrayList<State> list){  //finding in openlist the duplicate state of the given state
		State dupl = list.get(0);
		if(inList(state,list)){				//check if in frontier
			for(State x : list){
				if(checkSamePuzzle(x.getPuzzle(),state.getPuzzle())){
					dupl = x;
					break;
				}
			}
			return(dupl);
		}
		else {
			return(null);
		}
	}

	private static int pathCost(State state){			//tracing actions and writing to puzzle.out backwards
		ArrayList<Character> actions = new ArrayList<Character>();  //list of actions done solving the puzzle
		State current = state;
		int n = 0;
		while(current.getParent()!=null){				//while current is not the initial state
			actions.add(current.getAction());   //append
			current= current.getParent();
			n++;
		}
		try {		//writing the actions to the file backwards(reversed)
	      FileWriter output = new FileWriter("./src/eightpuzzle/puzzle.out");
	      for(int i=(actions.size()-1);i>=0;i--){
	    	  if(i==0){								//if last
	    		  output.write(actions.get(i));
	    	  }
	    	  else{
	    		  output.write(actions.get(i)+" ");
	    	  }
	      }
	      System.out.println("Actions successfully written.");
	      output.close();
	    } catch (IOException e) {
	    	System.out.println("Error in writing actions to puzzle.out.");
	    }
		return n;
	}
}
