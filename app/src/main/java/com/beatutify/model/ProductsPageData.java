package com.beatutify.model;

/**
 * Created by gaurav.singh on 4/11/2018.
 */

public class ProductsPageData {
    private int totalCount;

    private int currentPage;

    public int getTotalCount ()
    {
        return totalCount;
    }

    public void setTotalCount (int totalCount)
    {
        this.totalCount = totalCount;
    }

    public int getCurrentPage ()
    {
        return currentPage;
    }

    public void setCurrentPage (int currentPage)
    {
        this.currentPage = currentPage;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [totalCount = "+totalCount+", currentPage = "+currentPage+"]";
    }
}
