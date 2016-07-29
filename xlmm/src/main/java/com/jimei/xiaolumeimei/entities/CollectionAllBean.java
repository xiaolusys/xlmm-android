package com.jimei.xiaolumeimei.entities;

import java.util.List;

/**
 * Created by wisdom on 16/7/28.
 */
public class CollectionAllBean {

    /**
     * count : 79
     * next : http://192.168.1.57:8000/rest/v1/favorites?page=2
     * previous : null
     * results : []
     */

    private int count;
    private String next;
    private String previous;
    private List<CollectionBean> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<CollectionBean> getResults() {
        return results;
    }

    public void setResults(List<CollectionBean> results) {
        this.results = results;
    }
}
