package com.jimei.xiaolumeimei.widget.sizeview.item;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jimei.xiaolumeimei.R;
import java.util.ArrayList;

public class CustomeTableItem extends LinearLayout {
  private Context context = null;
  private boolean isRead = false;//是否只读
  private ArrayList<View> viewList = new ArrayList();//行的表格列表
  private int[] headWidthArr = null;//表头的列宽设置
  private String rowType = "0";//行的样式id

  public CustomeTableItem(Context context) {
    super(context);
  }

  public CustomeTableItem(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public CustomeTableItem(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  /*
   * rowType:行的样式，字符任意，相同样式的行不需要再创建了
   * itemCells:单元格信息
   * headWidthArr:每列宽度
   * isRead:是否只读，如果是只读，则所有的输入都不生效
   */
  public void buildItem(Context context, String rowType, ArrayList<ItemCell> itemCells,
      int[] headWidthArr, boolean isRead) {
    this.setOrientation(LinearLayout.VERTICAL);//第一层布局垂直
    this.context = context;
    this.headWidthArr = headWidthArr.clone();
    this.rowType = rowType;

    this.addCell(itemCells);
  }

  private void addCell(ArrayList<ItemCell> itemCells) {
    this.removeAllViews();
    LinearLayout secondLayout = new LinearLayout(context);
    secondLayout.setOrientation(LinearLayout.HORIZONTAL);
    secondLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
        LayoutParams.WRAP_CONTENT));
    this.addView(secondLayout);
    int cellIndex = 0;
    for (int i = 0; i < itemCells.size(); i++) {
      ItemCell itemCell = itemCells.get(i);
      int endIndex = cellIndex + itemCell.getCellSpan();//所占行数

      int width = getCellWidth(cellIndex, endIndex);//行宽度
      cellIndex = endIndex;
      if (itemCell.getCellType() == CellTypeEnum.STRING) {
        EditText view = (EditText) getInputView();
        view.setText(itemCell.getCellValue());
        view.setWidth(width);
        this.setEditView(view);
        secondLayout.addView(view);
        viewList.add(view);
      } else if (itemCell.getCellType() == CellTypeEnum.DIGIT) {
        EditText view = (EditText) getInputView();
        view.setText(itemCell.getCellValue());
        view.setWidth(width);
        this.setEditView(view);
        this.setOnKeyBorad(view);
        secondLayout.addView(view);
        viewList.add(view);
      } else if (itemCell.getCellType() == CellTypeEnum.LABEL) {
        TextView view = (TextView) getLabelView();
        view.setText(itemCell.getCellValue());
        view.setWidth(width);
        secondLayout.addView(view);
        viewList.add(view);
      }
      if (i != itemCells.size() - 1) {//插入竖线
        LinearLayout v_line = (LinearLayout) getVerticalLine();
        v_line.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT));
        secondLayout.addView(v_line);
      }
    }
  }

  public void refreshData(ArrayList<ItemCell> itemCells) {
    for (int i = 0; i < itemCells.size(); i++) {
      ItemCell itemCell = itemCells.get(i);
      if (itemCell.getCellType() == CellTypeEnum.LABEL) {
        TextView view = (TextView) viewList.get(i);
        view.setText(itemCell.getCellValue());
      } else if (itemCell.getCellType() == CellTypeEnum.DIGIT) {
        EditText view = (EditText) viewList.get(i);
        view.setText(itemCell.getCellValue());
        this.setEditView(view);
        this.setOnKeyBorad(view);
      } else if (itemCell.getCellType() == CellTypeEnum.STRING) {
        EditText view = (EditText) viewList.get(i);
        view.setText(itemCell.getCellValue());
        this.setEditView(view);
      }
    }
  }

  private View getVerticalLine() {
    return LayoutInflater.from(context).inflate(R.layout.atom_line_v_view, null);
  }

  private int getCellWidth(int cellStart, int cellEnd) {
    int width = 0;
    for (int i = cellStart; i < cellEnd; i++) {
      width = this.headWidthArr[i] + width;
    }
    return width;
  }

  private View getLabelView() {
    return (View) LayoutInflater.from(context).inflate(R.layout.atom_text_view, null);
  }

  private View getInputView() {
    return (View) LayoutInflater.from(context).inflate(R.layout.atom_edttxt_view, null);
  }

  private void setEditView(EditText edtText1) {
    if (this.isRead) {
      edtText1.setEnabled(false);
    } else {

    }
  }

  private void setOnKeyBorad(EditText edtText1) {
    //数字键盘
    if (!this.isRead) {//非只读

    }
  }

  public String getRowType() {
    return rowType;
  }
}
