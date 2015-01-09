package lst.medo.model;

/**
 * Represents a 1-indexed page.
 */
public class Page {
    int page;
    int itemsPerPage;

    public Page(int page, int itemsPerPage) {
        this.page = page;
        this.itemsPerPage = itemsPerPage;
    }

    /**
     * @return current page
     */
    public int getPage() {
        return page;
    }

    /**
     * @return item count per page
     */
    public int getItemsPerPage() {
        return itemsPerPage;
    }

    /**
     * @return first item index of the page
     */
    public int getOffset() {
        return (page - 1) * itemsPerPage;
    }

    /**
     * Creates a new page instance with the given page.
     *
     * @param page new page
     * @return new page instance
     */
    public Page withPageNum(int page) {
        return new Page(page, itemsPerPage);
    }
}
