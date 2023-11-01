package com.github.rhyrak.model;

public class CvssMetricV2 {
    private String source;
    private String type;
    private CvssData cvssData;
    private String baseSeverity;
    private double exploitabilityScore;
    private double impactScore;
    private boolean acInsufInfo;
    private boolean obtainAllPrivilege;
    private boolean obtainUserPrivilege;
    private boolean obtainOtherPrivilege;
    private boolean userInteractionRequired;

    public double getExploitabilityScore() {
        return exploitabilityScore;
    }

    public double getImpactScore() {
        return impactScore;
    }

    public CvssData getCvssData() {
        return cvssData;
    }
}
