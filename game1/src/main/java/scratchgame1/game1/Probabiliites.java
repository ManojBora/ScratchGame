package scratchgame1.game1;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

class Probabilities {
	 @JsonProperty("standard_symbols")
	    private List<Probability> standardSymbols;

	    @JsonProperty("bonus_symbols")
	    private BonusSymbols bonusSymbols;

	    

   

   public List<Probability> getStandardSymbols() {
       return standardSymbols;
   }

   public void setStandardSymbols(List<Probability> standardSymbols) {
       this.standardSymbols = standardSymbols;
   }

   public BonusSymbols getBonusSymbols() {
       return bonusSymbols;
   }

   public void setBonusSymbols(BonusSymbols bonusSymbols) {
       this.bonusSymbols = bonusSymbols;
   }

  
}
