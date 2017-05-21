package be.sanderdebleecker.uselections.mvp.model.data;

/**
 * Created by Sander De Bleecker on 15/05/2017.
 */

public class Official {
    String name;
    Address[] address;
    String party;
    String[] phones;
    String[] urls;
    String photoUrl;
    String[] emails;
    Channel[] channels;

    class Source {
        String name;
        boolean official;
    }
    class Address {
        String locationName;
        String line1;
        String line2;
        String line3;
        String city;
        String state;
        String zip;
    }
    class Channel {
        String type;
        String id;
    }
}

