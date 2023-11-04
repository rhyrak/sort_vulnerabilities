package com.github.rhyrak.model;

public class NISTEntry implements Comparable<NISTEntry> {
    private final Vulnerability entry;

    public NISTEntry(Vulnerability entry) {
        this.entry = entry;
    }

    public double getBaseScore() {
        try {
            return entry.getCve().getMetrics().getCvssMetricV2().get(0).getCvssData().getBaseScore();
        }
        catch(Exception e){
            return -1;
        }
    }

    public double getImpactScore() {
        try{
        return entry.getCve().getMetrics().getCvssMetricV2().get(0).getImpactScore();
        }
        catch(Exception e){
            return -1;
        }
    }

    public double getExploitabilityScore() {
        try {
            return entry.getCve().getMetrics().getCvssMetricV2().get(0).getExploitabilityScore();
        }
        catch(Exception e){
            return -1;
        }
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

        return o.getId().compareTo(this.getId());
    }

    public String toString(){
        return getBaseScore() + " " + getImpactScore() + " " + getImpactScore() + " " + getId();
    }

}
