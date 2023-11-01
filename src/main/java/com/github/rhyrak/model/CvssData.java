package com.github.rhyrak.model;

public class CvssData {
    private String version;
    private String vectorString;
    private String accessVector;
    private String accessComplexity;
    private String authentication;
    private String confidentialityImpact;
    private String integrityImpact;
    private String availabilityImpact;
    private double baseScore;

    public double getBaseScore() {
        return baseScore;
    }
}
