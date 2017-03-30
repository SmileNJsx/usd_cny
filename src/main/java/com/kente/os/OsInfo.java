package com.kente.os;

public class OsInfo {
    
    private static String OS = System.getProperty("os.name").toLowerCase();
    private static OsInfo _InStance = new OsInfo();
    private OsPlatEnum osPlatEnum;
    
    /**
     * 单例
     */
    private OsInfo() {};
    
    public static boolean isLinux() {
        return OS.indexOf("linux") >= 0;
    }
    
    public static boolean isWindows() {
        return OS.indexOf("windows") >= 0;
    }
    
    /**
     * 获得操作系统名称
     * @return 操作系统
     */
    public static OsPlatEnum getOSName() {
        if (isLinux()) {
            _InStance.osPlatEnum = OsPlatEnum.Linux;
        } else if (isWindows()) {
            _InStance.osPlatEnum = OsPlatEnum.Windows;
        }
        
        return _InStance.osPlatEnum;
    }
}
