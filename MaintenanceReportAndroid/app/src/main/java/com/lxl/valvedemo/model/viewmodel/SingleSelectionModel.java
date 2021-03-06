package com.lxl.valvedemo.model.viewmodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SingleSelectionModel implements Serializable {

    public String selectId;//
    public int level = 0;
    public String itemStr;
    public String path;
    public boolean isCanSelect = true;
    public boolean isCanJump = false;
    public boolean isInquire = false;
    public List<SingleSelectionModel> selectList = new ArrayList<SingleSelectionModel>();
    public SingleSelectionModel parentModel = null;
    public String parseType = "";
    public String tableTitle = "";
}
