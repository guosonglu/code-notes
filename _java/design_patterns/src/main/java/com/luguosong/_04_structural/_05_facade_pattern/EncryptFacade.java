package com.luguosong._04_structural._05_facade_pattern;

/**
 * 加密外观类，充当外观类
 *
 * @author 10545
 * @date 2022/5/16 22:19
 */
public class EncryptFacade {
    //维持对子系统对象的引用
    private FileReader reader;
    private CipherMachine cipher;
    private FileWriter writer;

    public EncryptFacade() {
        reader = new FileReader();
        cipher = new CipherMachine();
        writer = new FileWriter();
    }

    //调用子系统对象的业务方法
    public void fileEncrypt(String fileNameSrc, String fileNameDes) {
        String plainStr = reader.read(fileNameSrc);
        String encryptStr = cipher.encrypt(plainStr);
        writer.write(encryptStr, fileNameDes);
    }
}
