package scratchgame1.game1;

import java.util.Map;

class Output {
    private String[][] matrix;
    private int reward;
    private Map<String, String[]> appliedWinningCombinations;
    private String appliedBonusSymbol;


    public Output(String[][] matrix, int reward) {
        this.matrix = matrix;
        this.reward = reward;
    }
    
	public Output() {
	}

	public String[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(String[][] matrix) {
        this.matrix = matrix;
    }

    public int getReward() {
    	
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

	public void setAppliedWinningCombinations(Map<String, String[]> appliedWinningCombinations) {
        this.appliedWinningCombinations = appliedWinningCombinations;
		
	}
	public Map<String, String[]> getAppliedWinningCombinations() {
        return appliedWinningCombinations;
    }

	public String getAppliedBonusSymbol() {
        return appliedBonusSymbol;
    }

    public void setAppliedBonusSymbol(String appliedBonusSymbol) {
        this.appliedBonusSymbol = appliedBonusSymbol;
    }

}