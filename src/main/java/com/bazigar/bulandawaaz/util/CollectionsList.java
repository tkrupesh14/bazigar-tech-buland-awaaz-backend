package com.bazigar.bulandawaaz.util;

import java.util.Arrays;

public class CollectionsList {

    private Long totalItems;
    private Integer currentPage;
    private Integer itemsPerPage;
    private Collection[] items;

    public CollectionsList() {
    }

    public CollectionsList(Long totalItems, Integer currentPage, Integer itemsPerPage, Collection[] items) {
        this.totalItems = totalItems;
        this.currentPage = currentPage;
        this.itemsPerPage = itemsPerPage;
        this.items = items;
    }

    public Long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Long totalItems) {
        this.totalItems = totalItems;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(Integer itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public Collection[] getItems() {
        return items;
    }

    public void setItems(Collection[] items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "CollectionsList{" +
                "totalItems=" + totalItems +
                ", currentPage=" + currentPage +
                ", itemsPerPage=" + itemsPerPage +
                ", items=" + Arrays.toString(items) +
                '}';
    }
}
