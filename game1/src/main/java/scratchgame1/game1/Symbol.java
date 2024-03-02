package scratchgame1.game1;

import com.fasterxml.jackson.annotation.JsonProperty;

class Symbol {
	@JsonProperty("reward_multiplier")
    private Double rewardMultiplier;
    private String type;
    private int extra;
    private String impact;

    

    public Double getRewardMultiplier() {
        return rewardMultiplier;
    }

    public void setRewardMultiplier(Double rewardMultiplier) {
        this.rewardMultiplier = rewardMultiplier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getExtra() {
        return extra;
    }

    public void setExtra(int extra) {
        this.extra = extra;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }
}