\\pesquisa sequencial

public boolean pesquisa(Time time) {
		 
		boolean resp = false ;
		 		
		if(n == 0) {
		 		
			resp = false ;
		 				 		
		}else if ( n > 0) {
		 		
			for(int i = 0 ; i < n  ; i++ ) {
		 					
				if(array[i].getPais().equals(time.getPais()) ) { 
		 								 		
		            cont = i ;
			        i = n ;
		 			resp = true ;		 					     
		 		}
		 		
		 	}		 		
        }	
		 	
		return resp ;
	
}