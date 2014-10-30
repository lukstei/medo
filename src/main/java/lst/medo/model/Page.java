package lst.medo.model;

/**
 * 1-indexed page
 */
public class Page {
    int page;
    int itemsPerPage;

    public Page(int page, int itemsPerPage) {
        this.page = page;
        this.itemsPerPage = itemsPerPage;
    }

    public int getPage() {
        return page;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public int getOffset() {
        return (page - 1) * itemsPerPage;
    }

    public Page withPageNum(int page) {
        return new Page(page, itemsPerPage);
    }
}
