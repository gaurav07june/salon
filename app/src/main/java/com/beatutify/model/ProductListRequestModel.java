package com.beatutify.model;

/**
 * Created by gaurav.singh on 4/11/2018.
 */

public class ProductListRequestModel {

    private int limit;

    private String title;

    private String token;

    private int page;

    private int salonId;

    public int getLimit ()
    {
        return limit;
    }

    public void setLimit (int limit)
    {
        this.limit = limit;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getToken ()
    {
        return token;
    }

    public void setToken (String token)
    {
        this.token = token;
    }

    public int getPage ()
    {
        return page;
    }

    public void setPage (int page)
    {
        this.page = page;
    }

    public int getSalonId ()
    {
        return salonId;
    }

    public void setSalonId (int salonId)
    {
        this.salonId = salonId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [limit = "+limit+", title = "+title+", token = "+token+", page = "+page+", salonId = "+salonId+"]";
    }
}
