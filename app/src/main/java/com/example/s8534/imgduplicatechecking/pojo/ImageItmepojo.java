package com.example.s8534.imgduplicatechecking.pojo;

public class ImageItmepojo {
    private String name_item, style_item, size_item, path_item;
    private String img_item,w_h_item,text_zu;

    public String getText_zu() {
        return text_zu;
    }

    public void setText_zu(String text_zu) {
        this.text_zu = text_zu;
    }

    public String getW_h_item() {
        return w_h_item;
    }

    public void setW_h_item(String w_h_item) {
        this.w_h_item = w_h_item;
    }

    public ImageItmepojo() {
    }

    public String getName_item() {
        return name_item;
    }

    public void setName_item(String name_item) {
        this.name_item = name_item;
    }

    public String getStyle_item() {
        return style_item;
    }

    public void setStyle_item(String style_item) {
        this.style_item = style_item;
    }

    public String getSize_item() {
        return size_item;
    }

    public void setSize_item(String size_item) {
        this.size_item = size_item;
    }

    public String getPath_item() {
        return path_item;
    }

    public void setPath_item(String path_item) {
        this.path_item = path_item;
    }

    public String getImg_item() {
        return img_item;
    }

    public void setImg_item(String img_item) {
        this.img_item = img_item;
    }

    @Override
    public String toString() {
        return "ImageItmepojo{" +
                "name_item='" + name_item + '\'' +
                ", style_item='" + style_item + '\'' +
                ", size_item='" + size_item + '\'' +
                ", path_item='" + path_item + '\'' +
                ", img_item='" + img_item + '\'' +
                ", w_h_item='" + w_h_item + '\'' +
                ", text_zu='" + text_zu + '\'' +
                '}';
    }
}
