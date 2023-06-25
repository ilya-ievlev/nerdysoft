package org.example.util;

import java.util.HashSet;
import java.util.Set;

public class SingletonVirtualProductCodeManager {
    //this implementation of singleton should work in multithreading environment and satisfy the lazy initialization requirement
    //this implementation of singleton was created by Computer Science Professor - Bill Pugh
    private final Set<String> codeList = new HashSet<>();

    private SingletonVirtualProductCodeManager() {
    }


    private static class SingletonHolder {
        private static final SingletonVirtualProductCodeManager instance = new SingletonVirtualProductCodeManager();
    }


    public static SingletonVirtualProductCodeManager getInstance() {
        return SingletonHolder.instance;
    }

    public void useCode(String code) {
        codeList.add(code);
    }

    public boolean isCodeUsed(String code) {
        return codeList.contains(code);
    }
}
