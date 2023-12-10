package top.rongxiaoli;

import net.mamoe.mirai.contact.Contact;

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Module class.
 */
public abstract class Module {
    private HashMap<String, Object> moduleSettingMap;
    public void setModuleSettingOrAdd(String moduleSetting, Object value) {
    if (moduleSettingMap.containsKey(moduleSetting)) {
        moduleSettingMap.replace(moduleSetting, value);
    } else moduleSettingMap.put(moduleSetting, value);
}
    public void setModuleSetting(String moduleSetting, Object value) {
        if (moduleSettingMap.containsKey(moduleSetting)) {
            moduleSettingMap.replace(moduleSetting, value);
        }
    }
    public Object readModuleSetting(String moduleSetting) {
        if (moduleSettingMap.containsKey(moduleSetting)) {
            return moduleSettingMap.get(moduleSetting);
        } else throw new NoSuchElementException("No " + "\"" + moduleSetting + "\"" + "found. ");
    }
    public Object readModuleSettingOrNull(String moduleSetting) {
        return moduleSettingMap.getOrDefault(moduleSetting, null);
    }
    private String PluginName = "DefaultPluginName";
    private String HelpContent = "Default plugin help content. ";
    private boolean IsEnabled;
    private boolean DebugMode;

    /**
     * Module initiate function.
     */
    public abstract void Init();

    /**
     * Module shutdown function.
     */
    public abstract void Shutdown();

    /**
     * Friend message process.
     */
    public abstract void FriendMain(String[] arrCommand, long Friend, Contact SubjectContact);

    /**
     * Group message process.
     */
    public abstract void GroupMain(String[] arrCommand, long Friend, long Group, Contact SubjectContact);

    /**
     * Plugin name. Use in logs.
     */
    public abstract String getPluginName();

    /**
     * Help content. Used in BotCommand.Modules.Help.
     */
    public abstract String getHelpContent();

    /**
     * True if enabled.
     */
    public abstract boolean isEnabled();

    /**
     * Set status.
     *
     * @param status Status
     */
    public abstract void setEnabled(boolean status);

    /**
     * Debug mode.
     */
    public abstract boolean isDebugMode();
}
