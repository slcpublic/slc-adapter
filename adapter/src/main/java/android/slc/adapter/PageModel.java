/**
 * Copyright 2019 bejson.com
 */
package android.slc.adapter;

import java.util.ArrayList;
import java.util.List;

public class PageModel<T> {
    private int current;
    private int pages;
    private Boolean isLastPage;
    private List<T> records;
    private boolean searchCount;
    private int size;
    private int total;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public boolean isSearchCount() {
        return searchCount;
    }

    public void setSearchCount(boolean searchCount) {
        this.searchCount = searchCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean getIsLastPage() {
        if (isLastPage == null) {
            return current >= pages;
        }
        return isLastPage;
    }

    public void setIsLastPage(Boolean isLastPage) {
        isLastPage = isLastPage;
    }

    public List<T> getList() {
        return records;
    }

    public void setList(List<T> records) {
        this.records = records;
    }

    public List<T> getListNoNull() {
        if (records == null) {
            records = new ArrayList<>();
        }
        return records;
    }

}