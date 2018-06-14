package com.shao.utilslibrary.view.bar;


/**
 * Description:软键盘监听
 * Company:
 * Author:Zhangshaopeng
 * Email :1377785991@qq.com
 * Data:2018/6/14
 */
public interface OnKeyboardListener {
    /**
     * On keyboard change.
     *
     * @param isPopup        the is popup  是否弹出
     * @param keyboardHeight the keyboard height  软键盘高度
     */
    void onKeyboardChange(boolean isPopup, int keyboardHeight);
}
