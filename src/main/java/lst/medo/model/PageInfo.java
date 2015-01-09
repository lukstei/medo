package lst.medo.model;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * Page helper class
 */
public class PageInfo {
    Page page;
    int total;

    public PageInfo(Page page, int total) {
        this.page = page;
        this.total = total;
    }

    public Page getPage() {
        return page;
    }

    /**
     * @return index of the first item of the page
     */
    public int getFirstItemOfPage() {
        return Math.min(total, (page.getPage() - 1) * page.getItemsPerPage() + 1);
    }

    /**
     * @return index of the last item of the page
     */
    public int getLastItemOfPage() {
        return Math.min(page.getPage() * page.getItemsPerPage(), total);
    }

    /**
     * @return total items
     */
    public int getTotal() {
        return total;
    }

    /**
     * @return last page
     */
    public int getLastPage() {
        return total / page.getItemsPerPage() + (total % page.getItemsPerPage() == 0 ? 0 : 1);
    }

    /**
     * @return true if this page has a previous page
     */
    public boolean getHasPrevious() {
        return page.getPage() > 1;
    }

    /**
     * @return true if this page has a next page
     */
    public boolean getHasNext() {
        return page.getPage() < getLastPage();
    }

    /**
     * @return previous page
     */
    public int getPreviousPage() {
        return page.getPage() - 1;
    }

    /**
     * @return next page
     */
    public int getNextPage() {
        return page.getPage() + 1;
    }

    /**
     * @return next page
     */
    public int getCurrentPage() {
        return page.getPage();
    }
}
