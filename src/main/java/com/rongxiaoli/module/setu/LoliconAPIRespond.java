package com.rongxiaoli.module.setu;

import java.util.List;

public class LoliconAPIRespond {
    private String error;
    private List<Data> data;

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Data> getData() {
        return this.data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Urls {
        private String regular;
        private String original;

        public String getRegular() {
            return this.regular;
        }

        public void setRegular(String regular) {
            this.regular = regular;
        }

        public String getOriginal() {
            return this.original;
        }

        public void setOriginal(String original) {
            this.original = original;
        }
    }

    protected static class Data {
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

        public int getPid() {
            return this.pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getP() {
            return this.p;
        }

        public void setP(int p) {
            this.p = p;
        }

        public int getUid() {
            return this.uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return this.author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public boolean getR18() {
            return this.r18;
        }

        public void setR18(boolean r18) {
            this.r18 = r18;
        }

        public int getWidth() {
            return this.width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return this.height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public List<String> getTags() {
            return this.tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public String getExt() {
            return this.ext;
        }

        public void setExt(String ext) {
            this.ext = ext;
        }

        public long getUploadDate() {
            return this.uploadDate;
        }

        public void setUploadDate(long uploadDate) {
            this.uploadDate = uploadDate;
        }

        public Urls getUrls() {
            return this.urls;
        }

        public void setUrls(Urls urls) {
            this.urls = urls;
        }
    }
}
