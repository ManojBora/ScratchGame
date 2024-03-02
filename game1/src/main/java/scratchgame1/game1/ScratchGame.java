package scratchgame1.game1;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.HashMap;
import java.util.Map;

public class ScratchGame {
    public static void main(String[] args) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Configuration configuration = objectMapper.readValue(new File("config.json"), Configuration.class);
            Output output = new Output();

            String[][] matrix = generateMatrix(configuration);
            
            @SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the bet amount: ");
            int betAmount = scanner.nextInt();
            
            int reward=calculateReward(matrix, configuration, betAmount,output);
            if(betAmount==reward)
            {
            	System.out.println("LOST GAME");

            }else          
            	System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(output));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


	private static String[][] generateMatrix(Configuration configuration) {
		    int rows = configuration.getRows();
		    int columns = configuration.getColumns();
		    String[][] matrix = new String[rows][columns];
		    Random random = new Random();

		    double totalProbability_std = 21;
		    double totalProbability_bonus = 15;

            double randomNumber_std = random.nextDouble() * totalProbability_std;
            double randomNumber_bonus = random.nextDouble() * totalProbability_bonus;

		    // Generate matrix
		    for (int i = 0; i < rows; i++) {
		        for (int j = 0; j < columns; j++) {
		            int select = random.nextInt(2) + 1;

		            double cumulativeProbability = 0;
		            boolean symbolAssigned = false;
		            
		            switch(select) //random selection between standard and bonus symbol
		            {
		            
			            case 1:
				            for (Probability prob : configuration.getProbabilities().getStandardSymbols()) {
				                if (prob.getColumn() == j && prob.getRow() == i) {
				                    for (Map.Entry<String, Double> entry : prob.getSymbols().entrySet()) {
				                        cumulativeProbability += entry.getValue();
				                        if (randomNumber_std <= cumulativeProbability) {
				                            matrix[i][j] = entry.getKey();
				                            symbolAssigned = true;
				                            break;
				                        }
				                    }
				                }
					            if (symbolAssigned) break;
		
				            }
			                break;
			            case 2:    
		                    for (Map.Entry<String, Integer> entry : 
		                    	configuration.getProbabilities().getBonusSymbols().getSymbols().entrySet()) {
				                        
				                           cumulativeProbability += entry.getValue();
				                           if (randomNumber_bonus <= cumulativeProbability) {
				                               matrix[i][j] = entry.getKey();
				                               symbolAssigned = true;
				                               
				                                break;
				                            }
				            }
				            if (symbolAssigned) break;

		            }
		           
		            // If no symbol assigned (miss), fill with MISS symbol
		            if (!symbolAssigned) {
		                matrix[i][j] = "M"; 
		            }
		        }
		    }
            System.out.println("Input Matrix");

		    for (String[] row : matrix) {
	            System.out.println(Arrays.toString(row));
	        }
		    return matrix;
		}


    private static int calculateReward(String[][] matrix, Configuration configuration, int betAmount, Output output) {
        int reward = betAmount;
        HashMap<String, Integer> symbolCounts = new HashMap<>();
        HashMap<String, Integer> symbolCountsHorizontal = new HashMap<>();
        HashMap<String, Integer> symbolCountsVertical = new HashMap<>();
        HashMap<String, Integer> symbolCountLtr = new HashMap<>();
        HashMap<String, Integer> symbolCountRtl = new HashMap<>();

        Map<String, String[]> appliedWinningCombinations = new HashMap<>();
        output.setMatrix(matrix);

        // Initialize symbol counts
        for (String symbol : configuration.getSymbols().keySet()) {
            symbolCounts.put(symbol, 0);
        }
		/*
		 * for (Map.Entry<String, Integer> mapcount : symbolCounts.entrySet()) {
		 * System.out.println(mapcount.getKey() + " : " + mapcount.getValue()); }
		 */
        // Count symbols in the matrix
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                String symbol = matrix[i][j];
                String symbolString = String.valueOf(symbol);
                if (configuration.getSymbols().containsKey(symbolString)) {
                    symbolCounts.put(symbolString, symbolCounts.get(symbolString) + 1);
                }
            }
        }
         //count horizontally in matrix
    	int countsym=0;
        String baseSymbol = null;

        for (int i = 0; i < matrix.length; i++) {
        	countsym=0;
            for (int j = 0; j < matrix.length; j++) {
            	
                String symbol = matrix[i][j];
                if(j==0) {
                	baseSymbol=symbol;

                }
                if (configuration.getSymbols().containsKey(symbol)) {
                	if(baseSymbol.compareTo(symbol)==0)
                		countsym++;
                	if(countsym==3)
                		symbolCountsHorizontal.put(symbol, 1);
                }
            }
        }
		
		/*
		 * for (Map.Entry<String, Integer> mapcount : symbolCountsHorizontal.entrySet())
		 * { System.out.println(mapcount.getKey() + " -:- " + mapcount.getValue()); }
		 */
        
		//count vertically in matrix
	    	
	        for (int j = 0; j < matrix.length; j++) {
	        	countsym=0;
	            for (int i = 0; i < matrix.length; i++) {
	            	
	                String symbol = matrix[i][j];
	                if(i==0) {
	                	baseSymbol=symbol;

	                }
	                if (configuration.getSymbols().containsKey(symbol)) {
	                	if(baseSymbol.compareTo(symbol)==0)
	                		countsym++;
	                	if(countsym==3)
	                		symbolCountsVertical.put(symbol, 1);
	                }
	            }
	        }
			/*
			 * for (Map.Entry<String, Integer> mapcount : symbolCountsVertical.entrySet()) {
			 * System.out.println(mapcount.getKey() + " |:| " + mapcount.getValue()); }
			 */
	        
	        //count Diagonal TL to BR in matrix
        	countsym=0;
		    String diagonalBaseSymbol = matrix[0][0] ;

	        for (int i = 0; i < matrix.length; i++) {
			   String symbol = matrix[i][i];

			    if (configuration.getSymbols().containsKey(symbol)) {

			    	if(diagonalBaseSymbol==symbol)
			    		countsym++;
			    	if(countsym==3)
			    		symbolCountLtr.put(symbol, 1);
			    }
	        }
			
			
			/*
			 * for (Map.Entry<String, Integer> mapcount : symbolCountLtr.entrySet()) {
			 * System.out.println(mapcount.getKey() + " L:R " + mapcount.getValue()); }
			 */
	        
			//count Diagonal TR to BL in matrix
	        	countsym=0;
			    diagonalBaseSymbol = matrix[0][matrix.length - 1] ;

		        for (int i = 0; i < matrix.length; i++) {
				   String symbol = matrix[i][matrix.length - 1 - i];
				
				    if (configuration.getSymbols().containsKey(symbol)) {
				    	if(diagonalBaseSymbol.equals(symbol))
				    		countsym++;
				    	if(countsym==3)
				    		symbolCountRtl.put(symbol, 1);
				    }
		        }
				
				
				/*
				 * for (Map.Entry<String, Integer> mapcount : symbolCountRtl.entrySet()) {
				 * System.out.println(mapcount.getKey() + " R:L " + mapcount.getValue()); }
				 */
				 
	 // Apply standard rewards
        for (Map.Entry<String, Integer> entry : symbolCounts.entrySet()) {
            String symbol = entry.getKey();
            int count = entry.getValue();

            Symbol symbolInfo = configuration.getSymbols().get(symbol);

            if ("standard".equals(symbolInfo.getType()) && count >=3) {
                reward *= count * symbolInfo.getRewardMultiplier();
            }

        }	

        // Apply bonus symbols
        for (Map.Entry<String, Integer> entry : symbolCounts.entrySet()) {
            String symbol = entry.getKey();
            int symbolCount = entry.getValue();

            if (symbolCount > 0) {

                Symbol bonusSymbol = configuration.getSymbols().get(symbol);
                if (bonusSymbol != null && "bonus".equals(bonusSymbol.getType())) {
                    if ("multiply_reward".equals(bonusSymbol.getImpact())) {
                        reward *= bonusSymbol.getRewardMultiplier();
                        output.setAppliedBonusSymbol(symbol);

                    } else if ("extra_bonus".equals(bonusSymbol.getImpact())) {
                        reward += bonusSymbol.getExtra();
                        output.setAppliedBonusSymbol(symbol);

                    }else if("miss".equals(bonusSymbol.getImpact())) {
                    } 

                }
            }
        }


        // Apply winning combinations
        for (Map.Entry<String, WinCombination> entry : configuration.getWinCombinations().entrySet()) {	 
			 
            String key;
            if (entry.getValue().getWhen().compareTo("same_symbols")==0 
            		&& ((key=getKeyByValue(symbolCounts, entry.getValue().getCount()))!=null)) {

            	if(key.compareTo("MISS")!=0)
            	{
            		reward *= entry.getValue().getRewardMultiplier();
        	        addStringToArray(appliedWinningCombinations, key, entry.getKey());

            		if (symbolCountsHorizontal.containsKey(key)) {
            			reward *=2;
            	        addStringToArray(appliedWinningCombinations, key, "same_symbols_horizontally");

            		}
            		if ( symbolCountsVertical.containsKey(key)) {
            			reward *=2;
            	        addStringToArray(appliedWinningCombinations, key, "same_symbols_vertically");

            		}      			
            		if (symbolCountLtr.containsKey(key)) {
            			reward *=5;
            	        addStringToArray(appliedWinningCombinations, key, "same_symbols_diagonally_left_to_right");

            		}
            		if (symbolCountRtl.containsKey(key)) {
            			reward *=5;
            	        addStringToArray(appliedWinningCombinations, key, "same_symbols_diagonally_right_to_left");
            		}
            	            	  		  
            	}

            }
        }

        output.setReward(reward);

        output.setAppliedWinningCombinations(appliedWinningCombinations);

        return reward;
    }


    private static void addStringToArray(Map<String, String[]> appliedWinningCombinations, String key, String newValue) {
    	String[] array = appliedWinningCombinations.getOrDefault(key, new String[0]);

        String[] newArray = Arrays.copyOf(array, array.length + 1);

        newArray[newArray.length - 1] = newValue;

        appliedWinningCombinations.put(key, newArray);
    		
	}


	public static String getKeyByValue(HashMap<String, Integer> symbolCounts, int count) {
		 for (Map.Entry<String, Integer>  entry : symbolCounts.entrySet()) {
	            if (count==entry.getValue()) {
	                return entry.getKey();
	            }
	        }
	        return null;	
    }

}
