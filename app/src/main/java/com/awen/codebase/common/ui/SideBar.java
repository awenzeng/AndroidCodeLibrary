package com.awen.codebase.common.ui;



import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.awen.codebase.R;



public class SideBar extends ImageButton {  
	
    private char[] index;  
    Context context;                //环境
    TextView txtOverlay;            //淡出显示的TextView
    private ListView list;          //显示的Listview
    float height;
    private SectionIndexer sectionIndexter = null;  
    public SideBar(Context _context)
    {  
    	
        super(_context);  
        init();  
        
    }  
    //当创建字母条是，最先执行这个构造函数
    public SideBar(Context context, AttributeSet attrs)
    {  
        super(context, attrs);  
        init();  
    }  
 
    public SideBar(Context context, AttributeSet attrs, int defStyle) 
    {  
        super(context, attrs, defStyle);  
        init();  
    }  
    
    private void init() {  
        index = new char[] {'#','A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',  
                'T', 'U', 'V', 'W', 'X', 'Y', 'Z','@'};  
        setBackgroundResource(R.drawable.siderbar);
    }  
    /**
     * 设置ListView和显示的TextView
     * @param _list
     * @param _txtOverlay
     */
    public void setListView(ListView _list,TextView _txtOverlay)
    {  
        list = _list; 
        txtOverlay=_txtOverlay;
        sectionIndexter = (SectionIndexer) _list.getAdapter();  
    }  
    
    public boolean onTouchEvent(MotionEvent event) {  
        super.onTouchEvent(event); 
        int i = (int) event.getY();  
        int idx = i /(int)height;  
        if (idx >= index.length) {  
            idx = index.length - 1;  
        } else if (idx < 0) {  
            idx = 0;  
        }  
        
       if( event.getAction() == MotionEvent.ACTION_MOVE){
    	 if(list.getAdapter()!=null){
    	 txtOverlay.setText(index[idx]+" "); 
         txtOverlay.setVisibility(View.VISIBLE); 
         if (sectionIndexter == null) {  
                 sectionIndexter = (SectionIndexer) list.getAdapter();  
             }  
             int position = sectionIndexter.getPositionForSection(index[idx]);  
             if (position == -1) {  
                 return true;  
             }  
               list.setSelection(position); 
        }
    	 }
        else if(event.getAction() == MotionEvent.ACTION_UP){
        	txtOverlay.setVisibility(View.INVISIBLE);
        }
       
        return true;  
    }  
    
    //画字母条
    protected void onDraw(Canvas canvas) {  
        Paint paint = new Paint();  
        paint.setColor(0xFFFFFFFF); 
        paint.setAntiAlias(true);
        paint.setTextSize(16);  
        paint.setTextAlign(Paint.Align.CENTER);  
        height = (getMeasuredHeight()) / index.length;
	if (height <= 0)
		return;
        float widthCenter = getMeasuredWidth()/2;  
        for (int i = 0; i < index.length; i++) {  
            canvas.drawText(String.valueOf(index[i]), widthCenter, height + (i * height), paint);  
        }  
        super.onDraw(canvas);  
    }  
}  