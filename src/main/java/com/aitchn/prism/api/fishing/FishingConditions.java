package com.aitchn.prism.api.fishing;

import java.util.Map;
import java.util.Objects;

/** Catch availability is a filter, never an extra weight multiplier. Underground bypasses both filters. */
public record FishingConditions(Time time, Weather weather) {
    public enum Time { ANY, DAY, NIGHT }
    public enum Weather { ANY, CLEAR, PRECIPITATION, THUNDER }
    public static final FishingConditions UNRESTRICTED = new FishingConditions(Time.ANY, Weather.ANY);

    public FishingConditions {
        Objects.requireNonNull(time, "time");
        Objects.requireNonNull(weather, "weather");
    }

    public boolean matches(FishingEnvironment environment) {
        Objects.requireNonNull(environment, "environment");
        if (environment.underground()) return true;
        boolean timeMatches = time == Time.ANY || environment.dayTime() >= 0 &&
                (time == Time.DAY ? environment.dayTime() < 12_000 : environment.dayTime() >= 12_000);
        boolean weatherMatches = switch (weather) {
            case ANY -> true;
            case CLEAR -> environment.weather() == FishingEnvironment.Weather.CLEAR;
            case PRECIPITATION -> environment.weather() == FishingEnvironment.Weather.PRECIPITATION
                    || environment.weather() == FishingEnvironment.Weather.THUNDER;
            case THUNDER -> environment.weather() == FishingEnvironment.Weather.THUNDER;
        };
        return timeMatches && weatherMatches;
    }

    static FishingConditions parse(Map<String, Object> values) {
        return new FishingConditions(parseEnum(values.getOrDefault("active-time", "any"), Time.class, "active-time"),
                parseEnum(values.getOrDefault("weather", "any"), Weather.class, "weather"));
    }

    private static <E extends Enum<E>> E parseEnum(Object value, Class<E> type, String key) {
        for (E entry : type.getEnumConstants()) {
            if (entry.name().toLowerCase(java.util.Locale.ROOT).equals(value)) return entry;
        }
        throw new IllegalArgumentException("Invalid fishing " + key + ": " + value);
    }
}
