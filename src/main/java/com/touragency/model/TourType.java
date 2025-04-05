package com.touragency.model;

public enum TourType {
    VACATION(1, "Vacation"),
    EXCURSION(2, "Excursion"),
    SHOPPING(3, "Shopping");

    private final int id;
    private final String label;

    TourType(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static TourType fromId(int id) {
        for (TourType t : values()) {
            if (t.id == id) return t;
        }
        throw new IllegalArgumentException("Invalid TourType id: " + id);
    }

    public static TourType fromLabel(String label) {
        for (TourType t : values()) {
            if (t.label.equalsIgnoreCase(label)) return t;
        }
        throw new IllegalArgumentException("Invalid TourType label: " + label);
    }
}
