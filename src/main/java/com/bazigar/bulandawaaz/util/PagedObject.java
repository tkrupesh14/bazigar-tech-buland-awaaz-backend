package com.bazigar.bulandawaaz.util;

public class PagedObject {
    private Object[] objects;
    private Integer currentPage;
    private Integer totalPages;

    public PagedObject(Object[] objects, Integer currentPage, Integer totalPages) {
        this.objects = objects;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }

    public Object[] getObjects() {
        return objects;
    }

    public void setObjects(Object[] objects) {
        this.objects = objects;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
