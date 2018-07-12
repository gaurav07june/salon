package com.beatutify.model;

import java.util.List;

/**
 * Created by gaurav.singh on 4/11/2018.
 */

public class ProductListResponseModel {
    private ProductsPageData pageData;

    private List<SalonProduct> productList;

    public ProductsPageData getPageData ()
    {
        return pageData;
    }

    public void setPageData (ProductsPageData pageData)
    {
        this.pageData = pageData;
    }

    public List<SalonProduct> getProductList ()
    {
        return productList;
    }

    public void setProductList (List<SalonProduct> productList)
    {
        this.productList = productList;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [pageData = "+pageData+", productList = "+productList+"]";
    }
}
