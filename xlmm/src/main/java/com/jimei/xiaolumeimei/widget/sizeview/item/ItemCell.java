package com.jimei.xiaolumeimei.widget.sizeview.item;

public class ItemCell {
  private String cellValue = "";//单元格的值
  private int cellSpan = 1; //单元格跨列
  private CellTypeEnum cellType = CellTypeEnum.LABEL; //单元格类型
  private int colNum = 0;  //单元格列号
  //private int rowType = 0; //行类型

  private boolean isChange = false;//是否被编辑

  public ItemCell(String cellValue, CellTypeEnum cellType, int cellSpan) {
    this.cellValue = cellValue;
    this.cellType = cellType;
    this.cellSpan = cellSpan;
  }

  public ItemCell(String cellValue, CellTypeEnum cellType) {
    this(cellValue, cellType, 1);
  }

  public int getColNum() {
    return this.colNum;
  }

  public void setColNum(int colNum) {
    this.colNum = colNum;
  }

  //	public void setRowType(int rowType){
  //		this.rowType = rowType;
  //	}
  //	public int getRowType(){
  //		return this.rowType;
  //	}
  public String getCellValue() {
    return cellValue;
  }

  public void setCellValue(String value) {
    this.cellValue = value;
  }

  public CellTypeEnum getCellType() {
    return cellType;
  }

  public int getCellSpan() {
    return cellSpan;
  }

  public boolean getIsChange() {
    return this.isChange;
  }

  public void setIsChange(boolean isChange) {
    this.isChange = isChange;
  }
}
