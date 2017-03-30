package com.kente.file;

import java.io.File;

import com.kente.os.OsInfo;

public class CreateFile {
    
    /**
     * 根据系统创建文件
     * @return
     */
    public static void createFileByOS () {
        if (OsInfo.isLinux()) {
            File file = new File("~/kente/usd_cny");
            file.mkdirs();
        } else if (OsInfo.isWindows()) {
            File file = new File("C:/kente/usd_cny");
            file.mkdirs();
        }
    }
    
    
}
