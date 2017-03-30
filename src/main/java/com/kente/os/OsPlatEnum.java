package com.kente.os;

public enum OsPlatEnum {
    
    Any("any"),
    Linux("Linux"),
    Mac_OS("Mac_OS"),
    Mac_OS_X("Mac OS X"),
    Windows("Windows"),
    OS2("OS/2"),
    Solaris("Solaris"),
    SunOS("SunOS"),
    MPEix("MPE/ix"),
    HP_UX("HP-UX"),
    AIX("AIX"),
    OS390("OS/390"),
    FreeBSD("FreeBSD"),
    Irix("Irix"),
    Digital_Unix("Digital_Unix"),
    NetWare_411("NetWare"),
    OSF1("OSF1"),
    OpenVMS("OpenVMS"),
    Others("Others");
    
    private OsPlatEnum(String osName) {
        this.osName = osName;
    }
    
    @Override
    public String toString() {
        return osName;
    }
    
    private String osName;
}
