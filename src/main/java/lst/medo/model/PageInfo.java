package lst.medo.model;

import java.util.function.Function;
import java.util.stream.IntStream;

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
        return total / page.getItemsPerPage() + total % page.getItemsPerPage() == 0 ? 0 : 1;
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

    public IntStream getPageLinks() {
        if (getLastPage() <= 5) {
            return IntStream.range(1, getLastPage() + 1);
        } else {
            if (getCurrentPage() <= 3) {
                return concat(IntStream.range(1, 5), IntStream.of(0), IntStream.of(getLastPage() + 1));
            } else if (getCurrentPage() < getLastPage() - 2) {
                return concat(IntStream.of(1), IntStream.of(0), IntStream.range(getCurrentPage() - 1, getCurrentPage() + 2), IntStream.of(getLastPage() + 1));
            } else {
                return concat(IntStream.of(1), IntStream.of(0), IntStream.range(getCurrentPage(), getLastPage() + 1));
            }
        }
    }

    private IntStream concat(IntStream... streams) {
        IntStream result = streams[0];
        for (int i = 1; i < streams.length; i++) {
            result = IntStream.concat(result, streams[i]);
        }
        return result;
    }
}
