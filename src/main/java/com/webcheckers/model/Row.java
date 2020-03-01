package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

public class Row implements Iterable<Space>
{
    private final static int DIMENSIONS = 8;
    private ArrayList<Space> spaces;
    private final int index;

    public Row(int index, boolean isFirst)
    {
        this.index = index;
        this.spaces = new ArrayList<Space>();
    }



    @Override
    public Iterator<Space> iterator()
    {
        return null;
    }
}
