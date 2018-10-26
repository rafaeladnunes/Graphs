\\ordenação 



public void selecao() {
     	for (int i = 0; i<n; i++) {
         	int menor = i;
         	for (int j = (i + 1); j <n; j++){
            	cont ++;
            	if( array[menor].getNumPon() == array[j].getNumPon())
            	{
               		
               		if ( array[menor].getPais().compareToIgnoreCase(array[j].getPais() ) > 0) 
                 	 	menor = j;
                  
            	}
           
            	if ( array[menor].getNumPon() > array[j].getNumPon()) 
               		menor = j;
               
         
         	}
         	swap(menor,i);
      	}

   	}