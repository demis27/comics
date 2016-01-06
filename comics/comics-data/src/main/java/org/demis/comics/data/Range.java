package org.demis.comics.data;

public class Range {

    private int page;

    private int size;

    public Range(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public Range() {

    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStart() {
        return page * size;
    }

    public int getEnd() {
        return ((page + 1) * size) - 1;
    }
}
