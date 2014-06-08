
import java.util.*;

class TwoThousandAndFortyEight {
	public static void main(String[] a){
		(new TwoThousandAndFortyEight()).gamePlay(); // starts the game
	}
	//creates an array containing all 16 tiles
	int[][] board=new int[4][4]; 
	// the main game method
	void gamePlay(){ 
		int x;
		newDigit();
		do{
			drawBoard();
			resolve();
			newDigit();
		}while((x=notDone())>0);
		drawBoard();
		finalResult(x);
	}
	//checks if there
	int freePositionsAvailable(){
		for(int[]y:board)
			for(int x:y)
				if(x<2)return 1;
		return 0;
	} 
	//checks if the game is finished or there are moves left
	int notDone(){
		int x,y;
		for(y=0;y<4;y++){
			for(x=0;x<4;){
				if(x<3  &&  board[y][x]==board[y][x+1]  ||  y<3  &&  board[y][x]==board[y+1][x])return 1; // there are moves left 	// there may not be free positions, but there are couples of repeating digits
				if(board[y][x++]>2047)return -1; // winning the game
			}
		}
		return freePositionsAvailable();
	}
	//checks the final result
	void finalResult(int x){
		if(x<0){
			printOnScreen("You win!",true);
		}else{
			printOnScreen("Game over",true);
		}
	}
	//checks the input and executes the corresponding method (if the in put is "u", the method "up()" is executed etc.)
	void resolve(){
		do{
			switch((new Scanner(System.in)).nextLine().charAt(0)){
				case 'u':
					if (up()>0)return;
					break;
				case 'd':
					if (down()>0)return;
					break;
				case 'l':
					if (left()>0)return;
					break;
				case 'r':
					if (right()>0)return;
					break;
				case 'z':
					board[0][0]=2048; // instant win;
					return;
			}
		} while(true);
	}
	//the four methods below are the 4 possible moves (up(), down(), left() and right())
	int up(){
		int didMove=0;
		int nextSpot;
		int[][] nb = new int[4][4];
		for(int x=0;x<4;x++){
			nextSpot=0;
			for(int y=0;y<4;y++){
				if(board[y][x]>0){
					if(nb[nextSpot][x]<1){
						nb[nextSpot][x]=board[y][x];
						didMove+=y-nextSpot;
					}else if(nb[nextSpot][x]==board[y][x]){
						nb[nextSpot++][x]*=2;
						didMove++;
					}else{
						nb[++nextSpot][x]=board[y][x];
						didMove+=y-nextSpot;
					}
				}
			}
		}
		board=nb;
		return didMove;
	}
	int down(){
		int didMove=0;
		int nextSpot;
		int[][] nb = new int[4][4];
		for(int x=0;x<4;x++){
			nextSpot=3;
			for(int y=3;y>=0;y--){
				if(board[y][x]>0){
					if(nb[nextSpot][x]<1){
						nb[nextSpot][x]=board[y][x];
						didMove+=nextSpot-y;
					}else if(nb[nextSpot][x]==board[y][x]){
						nb[nextSpot--][x]*=2;
						didMove++;
					}else{
						nb[--nextSpot][x]=board[y][x];
						didMove+=nextSpot-y;
					}
				}
			}
		}
		board=nb;
		return didMove;
	}
	int left(){
		int didMove=0;
		int nextSpot;
		int[][] nb = new int[4][4];
		for(int x=0;x<4;x++){
			nextSpot=0;
			for(int y=0;y<4;y++){
				if(board[x][y]>0){
					if(nb[x][nextSpot]<1){
						nb[x][nextSpot]=board[x][y];
						//didMove+=y-nextSpot;
						didMove++;
					}else if(nb[x][nextSpot]==board[x][y]){
						nb[x][nextSpot++]*=2;
						didMove++;
					}else{
						nb[x][++nextSpot]=board[x][y];
						//didMove+=y-nextSpot;
						didMove++;
					}
				}
			}
		}
		board=nb;
		return didMove;
	}
	int right(){
		int didMove=0;
		int nextSpot;
		int[][] nb = new int[4][4];
		for(int x=0;x<4;x++){
			nextSpot=3;
			for(int y=3;y>=0;y--){
				if(board[x][y]>0){
					if(nb[x][nextSpot]<1){
						nb[x][nextSpot]=board[x][y];
						didMove+=nextSpot-y;
					}else if(nb[x][nextSpot]==board[x][y]){
						nb[x][nextSpot--]*=2;
						didMove++;
					}else{
						nb[x][--nextSpot]=board[x][y];
						didMove+=nextSpot-y;
					}
				}
			}
		}
		board=nb;
		return didMove;
	}
	//generates a random number between 0 and 3 
	int randomGenerator(){
		return (new Random()).nextInt(4);
	}
	//places a new digit at random position on the board 
	void newDigit(){
		if (freePositionsAvailable()<1) return; // first it checks if there are free position on the board
		int x,y;
		do{
			x=randomGenerator();y=randomGenerator(); // generates a new position with random x and y
		}while(board[x][y]>0); //checks if there is already some number on this position - if yes, then it enters the loop again and generates a new random position etc.
		board[x][y]=2; //places the number 2 on the random position
	}
	//simple method to print on the console
	void printOnScreen(String a, boolean nl){
		System.out.print(a+(nl?"\n":""));
	}
	
	String buffer(){
		return "|     |     |     |     |";
	}
	void drawBoard(){
		printOnScreen("-------------------------",true);
		String p[] = new String[4];
		for(int[]y:board){
			p[0]=p[2]=buffer();
			p[1]="";
			for(int x=0;x<4;){
				p[1]+=formatDigits(y[x++]);
			}
			p[1]+="|";
			p[3]="-------------------------";
			for (String q:p){
				printOnScreen(q,true);
			}
		}
	}
	//formats a string
	String formatDigits(int a){
		return String.format("|%5d",a);
	}
}