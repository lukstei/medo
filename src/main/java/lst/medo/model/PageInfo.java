package lst.medo.model;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class PageInfo {
    Page page;
    int total;
    Function<Integer, String> pageToUrl;

    public PageInfo(Page page, int total) {
        this.page = page;
        this.total = total;
    }

    public Page getPage() {
        return page;
    }

    public int getFirstItemOfPage() {
        return Math.min(total, (page.getPage() - 1) * page.getItemsPerPage() + 1);
    }

    public int getLastItemOfPage() {
        return Math.min(page.getPage() * page.getItemsPerPage(), total);
    }

    public int getTotal() {
        return total;
    }

    public int getLastPage() {
        return total / page.getItemsPerPage() + (total % page.getItemsPerPage() == 0 ? 0 : 1);
    }

    public boolean getHasPrevious() {
        return page.getPage() > 1;
    }

    public boolean getHasNext() {
        return page.getPage() < getLastPage();
    }

    public int getPreviousPage() {
        return page.getPage() - 1;
    }

    public int getNextPage() {
        return page.getPage() + 1;
    }

    public int getCurrentPage() {
        return page.getPage();
    }

    public Function<Integer, String> getPageToUrl() {
        return pageToUrl;
    }

    public void setPageToUrl(Function<Integer, String> pageToUrl) {
        this.pageToUrl = pageToUrl;
    }
}
