package com.energy.model.pricing;

public enum PricingOption {
    STANDARD(1, "Standard", new StandardPricing()),
    PEAK_HOURS(2, "Peak Hours", new PeakHoursPricing()),
    GREEN_MODE(3, "Green Mode", new GreenModePricing());

    private final int code;
    private final String title;
    private final EnergyPricingStrategy strategy;

    PricingOption(int code, String title, EnergyPricingStrategy strategy) {
        this.code = code;
        this.title = title;
        this.strategy = strategy;
    }

    public int code() { return code; }
    public String title() { return title; }
    public EnergyPricingStrategy strategy() { return strategy; }

    public static PricingOption fromCode(int code) {
        for (var o : values()) if (o.code == code) return o;
        throw new IllegalArgumentException("Invalid pricing option: " + code);
    }
}
