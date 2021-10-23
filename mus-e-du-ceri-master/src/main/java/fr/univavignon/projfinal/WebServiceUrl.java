package fr.univavignon.projfinal;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

public class WebServiceUrl {

    private static final String HOST = "demo-lia.univ-avignon.fr";
    private static final String PATH = "cerimuseum";



    private static Uri.Builder commonBuilder() {
        Uri.Builder builder = new Uri.Builder();

        builder.scheme("https")
                .authority(HOST)
                .appendPath(PATH);
        return builder;
    }

    // get ids
    // https://demo-lia.univ-avignon.fr/cerimuseum/ids
    private static final String PATH_1 = "ids";

    // Build URL to get all id of object
    public static URL buildSearchId() throws MalformedURLException {
        Uri.Builder builder = commonBuilder();
        builder.appendPath(PATH_1);
        URL url = new URL(builder.build().toString());
        return url;
    }

    // get categories
    // https://demo-lia.univ-avignon.fr/cerimuseum/categories
    private static final String PATH_2 = "categories";

    // Build URL to get all category of object
    public static URL buildSearchCategory() throws MalformedURLException {
        Uri.Builder builder = commonBuilder();
        builder.appendPath(PATH_2);
        URL url = new URL(builder.build().toString());
        return url;
    }

    // get catalogs
    // https://demo-lia.univ-avignon.fr/cerimuseum/catalog
    private static final String PATH_3 = "catalog";

    // Build URL to get all catalog of object
    public static URL buildSearchCatalog() throws MalformedURLException {
        Uri.Builder builder = commonBuilder();
        builder.appendPath(PATH_3);
        URL url = new URL(builder.build().toString());
        return url;
    }

    // get item
    // https://demo-lia.univ-avignon.fr/cerimuseum/items/itemID
    private static final String PATH_4 = "items";

    // Build URL to get item
    public static URL buildSearchItem(String itemID) throws MalformedURLException {
        Uri.Builder builder = commonBuilder();
        builder.appendPath(PATH_4).appendPath(itemID);
        URL url = new URL(builder.build().toString());
        return url;
    }

    // get image PNG de petite taille
    // https://demo-lia.univ-avignon.fr/cerimuseum/items/itemID/thumbnail
    private static final String PATH_5 = "thumbnail";

    // Build URL to get image PNG de petite taille
    public static URL buildSearchItemImagePNG(String itemID) throws MalformedURLException {
        Uri.Builder builder = commonBuilder();
        builder.appendPath(PATH_4).appendPath(itemID).appendPath(PATH_5);
        URL url = new URL(builder.build().toString());
        return url;
    }

    // get image JPEG de grande taille
    // https://demo-lia.univ-avignon.fr/cerimuseum/items/itemID/images/<imageID>
    private static final String PATH_6 = "images";

    // Build URL to get image JPEG de grande taille
    public static URL buildSearchItem(String itemID,String imageID) throws MalformedURLException {
        Uri.Builder builder = commonBuilder();
        builder.appendPath(PATH_4).appendPath(itemID).appendPath(PATH_6).appendPath(imageID);
        URL url = new URL(builder.build().toString());
        return url;
    }

    // get demos
    // https://demo-lia.univ-avignon.fr/cerimuseum/demos
    private static final String PATH_7 = "demos";

    // Build URL to get item
    public static URL buildSearchDemos() throws MalformedURLException {
        Uri.Builder builder = commonBuilder();
        builder.appendPath(PATH_7);
        URL url = new URL(builder.build().toString());
        return url;
    }
}
