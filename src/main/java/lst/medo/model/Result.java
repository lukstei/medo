package lst.medo.model;

import java.util.List;

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

    public int getCount() {
        return count;
    }
}
