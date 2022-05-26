package com.luguosong._04_structural._02_bridge_pattern;

/**
 * Linux操作系统实现类，充当具体实现类
 *
 * @author 10545
 * @date 2022/5/4 23:22
 */
public class LinuxImp implements ImageImp {
    @Override
    public void doPaint(Matrix m) {
        //调用Linux系统的绘制函数绘制像素矩阵
        System.out.print("在Linux操作系统中显示图像:");
    }
}
