package com.rongxiaoli;

public class ModuleLoader {
    public Module[] ModuleList;

    public void ModuleInit(){
        for (Module SingleModule :
                ModuleList) {
            SingleModule.Init();
        }
    }
}
