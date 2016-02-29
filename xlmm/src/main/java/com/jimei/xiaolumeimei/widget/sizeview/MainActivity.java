package com.jimei.xiaolumeimei.widget.sizeview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.widget.sizeview.adapter.CustomeTableViewAdapter;
import com.jimei.xiaolumeimei.widget.sizeview.item.CellTypeEnum;
import com.jimei.xiaolumeimei.widget.sizeview.item.HeadItemCell;
import com.jimei.xiaolumeimei.widget.sizeview.item.ItemCell;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity {

	private LayoutInflater inflater;
	private TextView headLayout = null;
	private int[] arrHeadWidth = null;
	private ListView listView;
	private CustomeTableViewAdapter adapter = null;
	private ArrayList<HashMap<String,Object>>  lists = new ArrayList<HashMap<String,Object>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lst_view_ui);


		headLayout = (TextView)findViewById(R.id.linearlayout_head);
		listView = (ListView) findViewById(R.id.listview);
		inflater = LayoutInflater.from(this);

		this.testData();//测试数据
	}
	private View getVerticalLine(){
		return inflater.inflate(R.layout.atom_line_v_view, null);
	}
	private void addVLine(){
		LinearLayout v_line = (LinearLayout)getVerticalLine();
		v_line.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		//headLayout.addView(v_line);
	}
	private void addHead(HashMap headMap){
		arrHeadWidth = new int[headMap.size()];
		int width = 0;
		for(int i=0;i<headMap.size();i++){
			HeadItemCell itemCell = (HeadItemCell)headMap.get(i+"");
			String name = itemCell.getCellValue();
			width = Dp2Px(this,itemCell.getWidth());
			//setHeadName(name,width);
			arrHeadWidth[i] = width;
			if(i!=headMap.size()-1){
				this.addVLine();
			}
		}
	}
	//private void setHeadName(String name,int width){
	//	TextView headView = (TextView)inflater.inflate(R.layout.atom_head_text_view, null);
	//	if(headView!=null){
	//		String viewName = "<b>" + name + "</b>";
	//		headView.setText(Html.fromHtml(name));
	//		headView.setWidth(width);
	//		headLayout.addView(headView);
	//	}
	//}

	private int Dp2Px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}


	private void testData(){
		HashMap headMap = new HashMap();
		this.testAddHead(headMap,"列1");
		this.testAddHead(headMap,"列2");
		this.testAddHead(headMap,"列3");
		this.testAddHead(headMap,"列4");
		this.testAddHead(headMap,"列5");

		this.addHead(headMap);

		HashMap contentMap = new HashMap();
		this.testAddContent(contentMap);

		adapter = new CustomeTableViewAdapter(this, lists,listView
				,false,this.arrHeadWidth);
		adapter.notifyDataSetChanged();
	}
	private void testAddHead(HashMap headMap,String headName){
		HeadItemCell itemCell = new HeadItemCell(headName,100);
		headMap.put(headMap.size()+"", itemCell);
	}
	private void testAddContent(HashMap contentMap){
		HashMap rowMap1 = new HashMap();
		lists.add(rowMap1);
		this.testAddRows(rowMap1, 1, "1-1(1)", CellTypeEnum.LABEL);
		this.testAddRows(rowMap1, 1, "1-2(1)", CellTypeEnum.STRING);
		this.testAddRows(rowMap1, 2, "1-3(2)", CellTypeEnum.STRING);
		this.testAddRows(rowMap1, 1, "1-4(1)", CellTypeEnum.DIGIT);
		rowMap1.put("rowtype", "css1");//表样标示放在内容添加后再添加

		HashMap rowMap2 = new HashMap();
		lists.add(rowMap2);
		this.testAddRows(rowMap2, 1, "2-1(1)", CellTypeEnum.LABEL);
		this.testAddRows(rowMap2, 3, "2-2(3)", CellTypeEnum.STRING);
		this.testAddRows(rowMap2, 1, "2-3(1)", CellTypeEnum.DIGIT);
		rowMap2.put("rowtype", "css2");


		HashMap rowMap3 = new HashMap();
		lists.add(rowMap3);
		this.testAddRows(rowMap3, 1, "3-1(1)", CellTypeEnum.LABEL);
		this.testAddRows(rowMap3, 3, "3-2(3)", CellTypeEnum.LABEL);
		this.testAddRows(rowMap3, 1, "3-3(1)", CellTypeEnum.DIGIT);
		rowMap3.put("rowtype", "css3");


		HashMap rowMap4 = new HashMap();
		lists.add(rowMap4);
		this.testAddRows(rowMap4, 1, "4-1(1)", CellTypeEnum.LABEL);
		this.testAddRows(rowMap4, 1, "4-2(1)", CellTypeEnum.STRING);
		this.testAddRows(rowMap4, 2, "4-3(2)", CellTypeEnum.STRING);
		this.testAddRows(rowMap4, 1, "4-4(1)", CellTypeEnum.DIGIT);
		rowMap4.put("rowtype", "css1");
	}
	private void testAddRows(HashMap rowMap,int colSpan,String cellValue,CellTypeEnum cellType){
		ItemCell itemCell = new ItemCell(cellValue,cellType,colSpan);
		rowMap.put(rowMap.size()+"", itemCell);
	}
}
