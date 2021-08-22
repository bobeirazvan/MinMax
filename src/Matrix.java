
public class Matrix {
    
	char board[][] ;
	int M , N ;
	int best1 ;
	int best2 ;
	int main ;
	char maximize ;
	char minimize ;
	
	Matrix(int M,int N) {
		
		board = new char[M][N];
		for(int i = 0 ; i < M ; i ++)
		 for(int j = 0 ; j < N ; j++)
			board[i][j] = '_' ;
		this.M = M ;
		this.N = N ;
	}
	
	public char getBoardValue(int i,int j) { 
		return board[i][j];
	}
	
	public void showMatrix(char copy_board[][]) {
		
		for(int i = 0 ; i < M ; i ++) {
		  for(int j = 0 ; j < N ; j++)	
		      System.out.print(copy_board[i][j]);
	      System.out.println();
	}
		
	}
	public void setBordValue(int i,int j,char caracter) {
		board[i][j] = caracter ;
	}
	public int evaluate(char copy_board[][]) {
		
		for(int i = 0 ; i < 3 ; i ++) { 
			
			if(copy_board[i][0] == copy_board[i][1] && copy_board[i][1] == copy_board[i][2]) {
				   if(copy_board[i][0] == maximize)
					  return 1 ; 
				   else 
				   if(copy_board[i][0] == minimize)
					  return 0;  
				}
				if(copy_board[0][i] == copy_board[1][i] && copy_board[1][i] == copy_board[2][i]) {
				   if(copy_board[0][i] == maximize)
					  return 1 ; 
				   else 
				   if(copy_board[0][i] == minimize)
					  return 0;  
			    }
					
				}
				if(copy_board[0][0] == copy_board[1][1] && copy_board[1][1] == copy_board[2][2]) {
					if(copy_board[0][0] == maximize)
					   return 1 ; 
					else 
					if(copy_board[0][0] == minimize)
					   return 0;  	
				}
				if(copy_board[0][2] == copy_board[1][1] && copy_board[1][1] == copy_board[2][0]) {
					if(copy_board[0][2] == maximize)
					   return 1 ; 
					else 
					if(copy_board[0][2] == minimize)
					   return 0;  	
				}
		  	
	    return -1 ;
			
	}
	
	int minimax(char copy_board[][],int depth,int player) {
		
		int winner = evaluate(copy_board) ; 
		
		if(winner == 1)
		   return 10 ;
		if(winner == 0) 
		   return -10 ; 
		if(winner == -1 && Depth(copy_board) == 9)
		   return 0 ; 

		if(player == minimize) {
		   best1 = 100 ;	
		   for(int i = 0 ; i < M ; i ++)
			   for(int j = 0 ; j < N ; j++)	
				   if(copy_board[i][j] == '_') { 
					  copy_board[i][j] = minimize ;
				   best1 = Math.min(best1,minimax(copy_board,depth+1,maximize)); 	  
				   copy_board[i][j] = '_';	   
				   }
		   return best1 ; 	         	 
		}  
		if(player == maximize) {  
	       best2 = -100 ;		
			   for(int i = 0 ; i < M ; i ++)
				   for(int j = 0 ; j < N ; j++)	
					   if(copy_board[i][j] == '_') { 
						  copy_board[i][j] = maximize;
					   best2 = Math.max(best2,minimax(copy_board,depth+1,minimize)); 	  
					   copy_board[i][j] = '_';	   
					   }		         
		}
		return best2 ; 
		
	}
	int Depth(char copy_board[][]) {
		
		int number = 0;
		
		for(int i = 0 ; i < M ; i ++) 
		 for(int j = 0 ; j < N ; j++)
			 if(copy_board[i][j] != '_')
				number = number + 1 ;
		return number ;
		       
	}
	int findBestMove(char copy_board[][]) {
		
		int Best = -100; 
		int posBest = 0 ;
		int depth = Depth(copy_board);
		
		for(int i = 0 ; i < M ; i ++) {
		 for(int j = 0 ; j < N ; j++)	
		  if(copy_board[i][j] == '_') { 
			 copy_board[i][j] = maximize;
			 int optSolForThisPos = minimax(copy_board,depth,minimize);
			 System.out.print(i);
			 System.out.print(" ");
			 System.out.println(j);
			 System.out.println(optSolForThisPos);
			 if(optSolForThisPos > Best) {
				Best = optSolForThisPos ;
				posBest = 3*i + j;
			 }
			 copy_board[i][j] = '_'; 
		  }
		}
		System.out.println();
        return posBest ;
		
	}
}

