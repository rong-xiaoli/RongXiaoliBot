package com.rongxiaoli.module.setu;

import java.util.List;

public class LoliconAPIRespond {
    public static class Urls
    {
        private String regular;
        private String original;
        public void setRegular(String regular) {
            this.regular=regular;
        }
        public void setOriginal(String original){
            this.original = original;
        }
        public String getRegular() {
            return this.regular;
        }
        public String getOriginal(){
            return this.original;
        }
    }
    public class Data
    {
        private int pid;

        private int p;

        private int uid;

        private String title;

        private String author;

        private boolean r18;

        private int width;

        private int height;

        private List<String> tags;

        private String ext;

        private long uploadDate;

        private Urls urls;

        public void setPid(int pid){
            this.pid = pid;
        }
        public int getPid(){
            return this.pid;
        }
        public void setP(int p){
            this.p = p;
        }
        public int getP(){
            return this.p;
        }
        public void setUid(int uid){
            this.uid = uid;
        }
        public int getUid(){
            return this.uid;
        }
        public void setTitle(String title){
            this.title = title;
        }
        public String getTitle(){
            return this.title;
        }
        public void setAuthor(String author){
            this.author = author;
        }
        public String getAuthor(){
            return this.author;
        }
        public void setR18(boolean r18){
            this.r18 = r18;
        }
        public boolean getR18(){
            return this.r18;
        }
        public void setWidth(int width){
            this.width = width;
        }
        public int getWidth(){
            return this.width;
        }
        public void setHeight(int height){
            this.height = height;
        }
        public int getHeight(){
            return this.height;
        }
        public void setTags(List<String> tags){
            this.tags = tags;
        }
        public List<String> getTags(){
            return this.tags;
        }
        public void setExt(String ext){
            this.ext = ext;
        }
        public String getExt(){
            return this.ext;
        }
        public void setUploadDate(long uploadDate){
            this.uploadDate = uploadDate;
        }
        public long getUploadDate(){
            return this.uploadDate;
        }
        public void setUrls(Urls urls){
            this.urls = urls;
        }
        public Urls getUrls(){
            return this.urls;
        }
    }
    private String error;

    private List<Data> data;

    public void setError(String error){
        this.error = error;
    }
    public String getError(){
        return this.error;
    }
    public void setData(List<Data> data){
        this.data = data;
    }
    public List<Data> getData(){
        return this.data;
    }
}
