package com.powerledger.sse_implementation.model;

import java.util.List;

public class Diagnosis {
    private String name;
    private double confidence;
    private String icd10;
    private String reasoning;
    private List<String> evidence;

    public Diagnosis(String name, double confidence, String icd10,
                     String reasoning, List<String> evidence) {
        this.name = name;
        this.confidence = confidence;
        this.icd10 = icd10;
        this.reasoning = reasoning;
        this.evidence = evidence;
    }

    public String getName() {
        return name;
    }

    public double getConfidence() {
        return confidence;
    }

    public String getIcd10() {
        return icd10;
    }

    public String getReasoning() {
        return reasoning;
    }

    public List<String> getEvidence() {
        return evidence;
    }
}
