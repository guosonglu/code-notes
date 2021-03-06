package com.luguosong._03_creational._05_prototype_pattern.shallow_clone;

/**
 * 附件类
 *
 * @author 10545
 * @date 2022/3/24 21:50
 */
public class Attachment {
    private String name; //附件名

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void download() {
        System.out.println("下载附件，文件名为：" + name);
    }
}
