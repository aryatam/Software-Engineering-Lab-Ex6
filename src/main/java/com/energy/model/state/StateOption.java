package com.energy.model.state;

public enum StateOption {
    ACTIVE(1, "Active", new ActiveState()),
    ECO(2, "Eco Mode", new EcoModeState()),
    SHUTDOWN(3, "Shutdown", new ShutdownState());

    private final int code;
    private final String title;
    private final SystemState state;

    StateOption(int code, String title, SystemState state) {
        this.code = code;
        this.title = title;
        this.state = state;
    }

    public int code() { return code; }
    public String title() { return title; }
    public SystemState state() { return state; }

    public static StateOption fromCode(int code) {
        for (var o : values()) if (o.code == code) return o;
        throw new IllegalArgumentException("Invalid state option: " + code);
    }
}
