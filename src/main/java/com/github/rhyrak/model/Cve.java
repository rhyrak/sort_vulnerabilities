package com.github.rhyrak.model;

import java.util.ArrayList;
import java.util.Date;

public class Cve {
    private String id;
    private String sourceIdentifier;
    private Date published;
    private Date lastModified;
    private String vulnStatus;
    private ArrayList<Description> descriptions;
    private Metrics metrics;
    private ArrayList<Weakness> weaknesses;
    private ArrayList<Configuration> configurations;
    private ArrayList<Reference> references;
    private String evaluatorSolution;
    private String evaluatorImpact;

    public Metrics getMetrics() {
        return metrics;
    }

    public String getId() {
        return id;
    }
}
