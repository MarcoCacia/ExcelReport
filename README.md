# ExcelReport
	/*
	 * COSA SERVE:
	 * 
	 * Avrei bisogno di una applicazione che lanciata (doppio click) va a
	 * leggere tutti gli excel presenti nella cartella Windows (quindi i RIL) e
	 * crea un report con questi campi:
	 * 
	 * DATA -- NOME PERSONA -- ORE LAVORATE - GG SOLARI - FERIE/PERMESSI
	 * 
	 * Su excel sono rispettivamente i campi:
	 * 
	 * Prospetto Presenze - D6 (Data)
	 * 
	 * Prospetto Presenze - E2 (nome)
	 * 
	 * Prospetto Presenza  - I45 (ore lavorate)
	 * 
	 * Prospetto Presenza - I41 (gg solari lavorate)
	 * 
	 * MODULO C - E19 (Ferie) è in un altro sheet
	 * 
	 * Prospetto presenze - conteggio M (malattie)
	 */
	 
	 /*Bug*/
	 
	  - Gestire le eccezioni in modo tale che non blocchino il programma
	  - Aggiungere colonna noma file al report
	  - Gestire xlsx e xlsm
	  - Normalizzare la colonna ore lavorate (es. 152:0 -> 152,0)
	  
	  /*TODO*/
	  nel bat/configurazione inserire dei parametri $PATH_INIZIALE, $PATH_FINALE, $MESE, e il report sarà report_$MESE
	  
	  /*Next step*/
	  - gestire le configurazioni
	  - COLONNA ORE STRAORDINARIO: ORE TOT. LAVORATE  – GG SOLARI * 8h
	  - gestire commesse
	 	
