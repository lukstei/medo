package lst.medo.model;

import java.util.List;

/**
 * Represents a result list
 * @param <T> type of results
 */
public class Result<T> {
    List<T> items;
    int count;

    public Result(List<T> items, int count) {
        this.items = items;
        this.count = count;
    }

    public List<T> getItems() {
        return items;
    }

    /**
     * Gets the total result count.
     * @return total result count
     */
    public int getCount() {
        return count;
    }
}
