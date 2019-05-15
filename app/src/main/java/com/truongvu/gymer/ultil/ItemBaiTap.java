package com.truongvu.gymer.ultil;

public class ItemBaiTap {
   private String id,image,link,content,title, author;

    public ItemBaiTap() {

    }

    public ItemBaiTap(String id, String image, String link, String content, String title, String author) {
        this.id = id;
        this.image = image;
        this.link = link;
        this.content = content;
        this.title = title;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
