package be.sanderdebleecker.uselections.mvp.model.data.envelope;

import java.util.List;

import be.sanderdebleecker.uselections.mvp.model.view.ElectionVM;

/**
 * Model for returned data from Google Civic Api
 * Created by Sander De Bleecker on 09/05/2017.
 */

public class ElectionsEnvelope {
    private List<ElectionVM> elections;

    public List<ElectionVM> getElections () {
        return elections;
    }
    public void setElections (List<ElectionVM> elections) {
        this.elections = elections;
    }
}
