package be.sanderdebleecker.uselections.mvp.model.view;

/**
 * Viewable representation of Official
 * Created by Sander De Bleecker on 12/05/2017.
 */

public class OfficialVM {
    String name;
    String party;
    String photoUrl;

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getParty () {
        return party;
    }

    public void setParty (String party) {
        this.party = party;
    }

    public String getPhotoUrl () {
        return photoUrl;
    }

    public void setPhotoUrl (String photoUrl) {
        this.photoUrl = photoUrl;
    }
}

