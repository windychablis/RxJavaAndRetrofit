package com.lilosoft.outsidescreen.bean;

/**
 * Created by chablis on 2016/11/14.
 */

public class NewContext {

    /**
     * channel_id : bd882375b41f4ed28c8cb23814fe067c
     * createdate : 2016-11-15
     * title : 这是一个测试新闻
     * sub_title : test
     * author : admin
     * source : 手写
     * contents : &lt;p>
     这只是一条测试新闻能不能显示的数据&lt;/p>

     * pic :
     * smallpic :
     * docids :
     */

    private String channel_id;
    private String createdate;
    private String title;
    private String sub_title;
    private String author;
    private String source;
    private String contents;
    private String pic;
    private String smallpic;
    private String docids;

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getSmallpic() {
        return smallpic;
    }

    public void setSmallpic(String smallpic) {
        this.smallpic = smallpic;
    }

    public String getDocids() {
        return docids;
    }

    public void setDocids(String docids) {
        this.docids = docids;
    }

    @Override
    public String toString() {
        return "NewContext{" +
                "channel_id='" + channel_id + '\'' +
                ", createdate='" + createdate + '\'' +
                ", title='" + title + '\'' +
                ", sub_title='" + sub_title + '\'' +
                ", author='" + author + '\'' +
                ", source='" + source + '\'' +
                ", contents='" + contents + '\'' +
                ", pic='" + pic + '\'' +
                ", smallpic='" + smallpic + '\'' +
                ", docids='" + docids + '\'' +
                '}';
    }
}
