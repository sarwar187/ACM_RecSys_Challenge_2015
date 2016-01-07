package recsys;

public class ItemCount implements Comparable {

    int itemId;
    Double score;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    ItemCount() {
        itemId = 0;
        score = 0d;
    }
    
    ItemCount(Integer itemId, Double score){
        this.itemId = itemId;
        this.score = score;
    }
    @Override
    public int compareTo(Object o) {
        ItemCount ic = (ItemCount) o;
        if (this.score > ic.score)
            return -1;
        else if (this.score < ic.score)
            return 1;
        else
            return 0;
    }

}
