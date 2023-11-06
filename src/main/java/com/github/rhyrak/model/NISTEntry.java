package com.github.rhyrak.model;

public class NISTEntry implements Comparable<NISTEntry> {
    private final Vulnerability entry;
    private final boolean empty;

    public NISTEntry(Vulnerability entry) {
        this.entry = entry;
        empty = entry.getCve().getMetrics().getCvssMetricV2() == null;
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

    public boolean isEmpty() {
        return empty;
    }

    @Override
    public int compareTo(NISTEntry o) {
        if(this.isEmpty() && o.isEmpty()){
            return this.getId().compareTo(o.getId());
        }
        else if(this.isEmpty() || o.isEmpty()){
            if(o.isEmpty()){
                return -1;
            }
            else{
                return 1;
            }
        }
        else{
            if (this.getBaseScore() != o.getBaseScore())
                return this.getBaseScore() < o.getBaseScore() ? 1 : -1;
            if (this.getImpactScore() != o.getImpactScore())
                return this.getImpactScore() < o.getImpactScore() ? 1 : -1;
            if (this.getExploitabilityScore() != o.getExploitabilityScore())
                return this.getExploitabilityScore() < o.getExploitabilityScore() ? 1 : -1;

            return this.getId().compareTo(o.getId());
        }

    }

    @Override
    public String toString() {
        if (this.isEmpty())
            return "Empty " + getId();
        return getBaseScore() + " " + getImpactScore() + " " + getExploitabilityScore() + " " + getId();
    }
}
