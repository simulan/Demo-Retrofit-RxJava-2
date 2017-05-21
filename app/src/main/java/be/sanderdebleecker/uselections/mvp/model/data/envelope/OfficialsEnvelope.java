package be.sanderdebleecker.uselections.mvp.model.data.envelope;

import java.util.List;

import be.sanderdebleecker.uselections.mvp.model.view.OfficialVM;

/**
 * Model for returned data from Google Civic Api
 * Created by Sander De Bleecker on 10/05/2017.
 */
public class OfficialsEnvelope {
    //Divisions divisions;
    //List<Office> offices;
    private List<OfficialVM> officials;

    public List<OfficialVM> getOfficials () {
        return officials;
    }
    public void setOfficials (List<OfficialVM> officials) {
        this.officials = officials;
    }
}


/*
* Obsolete Subtypes
 */

/*
class Divisions {
    @SerializedName("divisions")
    @Expose
    private Map<String,Division> divisions = new HashMap<>();
}
class Division {
    String name;
    String alsoKnownAs;
    int officeIndices;
}
class Office {
    String name;
    String divisionId;
    String[] levels;
    String[] roles;
    List<Source>[] sources;
    int[] officialindices;
}

*/