package com.rongxiaoli;

import java.util.ArrayList;
import java.util.List;

public class ModuleLoader {
    //public Module[] ModuleList;

    public List<Module> ModuleList;
    public void ModuleInit(){
        for (Module SingleModule :
                ModuleList) {
            SingleModule.Init();
        }
    }
    public ModuleLoader(){
        ModuleList = new ArrayList<Module>();
    }
}
