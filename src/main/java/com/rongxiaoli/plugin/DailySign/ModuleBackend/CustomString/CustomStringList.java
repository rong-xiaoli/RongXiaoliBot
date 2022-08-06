package com.rongxiaoli.plugin.DailySign.ModuleBackend.CustomString;

public class CustomStringList {
    /**
     * This is a group-only function.
     */
    public class CustomString {
        public int StringID;
        public int GroupID;
        private String CustomString;
        private long Cost;

        public boolean setCustomString(String customString) {
            this.CustomString = customString;
            if (this.Cost >= this.Cost * 2) {
                return false;
            }
            this.Cost = this.Cost * 2;
            return true;
        }

        public long getCost() {
            return this.Cost;
        }

        public String getCustomString() {
            return this.CustomString;
        }
    }
}
