package alex.imagegoogle.adapters;

/**
 * Сreate by alex
 */


public class GoogleImageBean {
    String thumbUrl;    //URL of Image
    String title;        //Title of Image

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String url) {
        this.thumbUrl = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}