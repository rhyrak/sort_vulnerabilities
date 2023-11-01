package com.github.rhyrak.model;

import java.util.ArrayList;
import java.util.Date;

public class Root {
    private int resultsPerPage;
    private int startIndex;
    private int totalResults;
    private String format;
    private String version;
    private Date timestamp;
    private ArrayList<Vulnerability> vulnerabilities;

    public int getResultsPerPage() {
        return resultsPerPage;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public String getFormat() {
        return format;
    }

    public String getVersion() {
        return version;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public ArrayList<Vulnerability> getVulnerabilities() {
        return vulnerabilities;
    }
}
