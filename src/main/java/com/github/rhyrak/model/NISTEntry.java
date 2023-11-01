package com.github.rhyrak.model;

public class NISTEntry implements Comparable<NISTEntry> {
    private final Vulnerability entry;

    public NISTEntry(Vulnerability entry) {
        this.entry = entry;
    }

    public double getBaseScore() {
        return entry.getCve().getMetrics().getCvssMetricV2().get(0).getCvssData().getBaseScore();
    }

    public double getImpactScore() {
        return entry.getCve().getMetrics().getCvssMetricV2().get(0).getImpactScore();
    }

    public double getExploitabilityScore() {
        return entry.getCve().getMetrics().getCvssMetricV2().get(0).getExploitabilityScore();
    }

    public String getId() {
        return entry.getCve().getId();
    }

    @Override
    public int compareTo(NISTEntry o) {
        if (this.getBaseScore() != o.getBaseScore())
            return this.getBaseScore() > o.getBaseScore() ? 1 : -1;
        if (this.getImpactScore() != o.getImpactScore())
            return this.getImpactScore() > o.getImpactScore() ? 1 : -1;
        if (this.getExploitabilityScore() != o.getExploitabilityScore())
            return this.getExploitabilityScore() > o.getExploitabilityScore() ? 1 : -1;

        return this.getId().compareTo(o.getId());
    }
}
