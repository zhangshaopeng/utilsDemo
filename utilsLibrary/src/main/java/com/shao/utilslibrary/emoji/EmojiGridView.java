package com.shao.utilslibrary.emoji;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.SimpleAdapter;


import com.shao.utilslibrary.utils.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Description:表情列表
 * Company:
 * Author:Zhangshaopeng
 * Email :1377785991@qq.com
 * Data:2018/6/27
 */
public class EmojiGridView extends GridView {


    public EmojiGridView(Context context) {
        super(context);
    }

    public EmojiGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGridView(context);
        initWidthAndHeight();
    }

    public EmojiGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void initGridView(Context context) {
        try {
            addexpression(context, EmojiUtil.getEmojiList(context), this);// 调用生情表情的方法
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 行列间距
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initWidthAndHeight() {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);


        int width = wm.getDefaultDisplay().getWidth();
        int spacing = (int) ((width - (width * 0.06 * 8)) / 9);
        Log.i("spacing", spacing + "");
        this.setPadding(20, 20, 20, 20);
        this.setVerticalSpacing(spacing);
        this.setHorizontalSpacing(spacing);
    }


    /**
     * 根据SMILE列表找到对应的SORCE ID，生成simpleAdatper 传到GrideView中
     * 生成表情的方法
     *
     * @param context  要传入的上下文
     * @param emojis   表情集合
     * @param gridView 要显示器的grildView
     * @throws Exception 异常
     */
    public void addexpression(Context context, List<Emoji> emojis, GridView gridView) throws Exception {

        // 通过反射把资源文件中的图片取出来放在GridView上
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < emojis.size(); i++) {
            Emoji emoji = emojis.get(i);
            if (emoji != null) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                Field f = (Field) R.mipmap.class.getDeclaredField(emoji.getName());
                int j = f.getInt(R.mipmap.class);
                map.put("ItemImage", j);// 添加图像资源的ID

                lstImageItem.add(map);

            }
        }

        // 生成适配器的ImageItem <====> 动态数组的元素，两者一一对应
        SimpleAdapter saImageItems = new SimpleAdapter(context, lstImageItem,// 数据来源
                R.layout.brow_item,
                // 动态数组与ImageItem对应的子项
                new String[]{"ItemImage"},
                // ImageItem的XML文件里面的一个ImageView
                new int[]{R.id.iv_brow});
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));//解决点击GridView背景变黑的情况
        gridView.setNumColumns(8);
        gridView.setAdapter(saImageItems);
    }

}
