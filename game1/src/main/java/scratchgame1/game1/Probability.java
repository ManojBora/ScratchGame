package scratchgame1.game1;

import java.util.Map;

class Probability {
    private int column;
    private int row;
    private Map<String, Double> symbols;

    

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Map<String, Double> getSymbols() {
        return symbols;
    }

    public void setSymbols(Map<String, Double> symbols) {
        this.symbols = symbols;
    }
}