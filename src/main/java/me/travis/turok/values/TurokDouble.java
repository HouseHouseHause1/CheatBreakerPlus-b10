/*
 * Decompiled with CFR 0.151.
 */
package me.travis.turok.values;

import java.math.BigDecimal;
import java.math.RoundingMode;
import me.travis.turok.values.TurokString;

public class TurokDouble {
    private TurokString name;
    private TurokString tag;
    private double value;
    private double max;
    private double min;

    public TurokDouble(TurokString name, TurokString tag, double _double, double min, double max) {
        this.name = name;
        this.tag = tag;
        this.value = _double;
        this.max = max;
        this.min = min;
    }

    public void set_value(double _double) {
        this.value = _double;
    }

    public void set_slider_value(double _double) {
        this.value = _double >= this.max ? this.max : (_double <= this.min ? this.min : _double);
    }

    public TurokString get_name() {
        return this.name;
    }

    public TurokString get_tag() {
        return this.tag;
    }

    public double get_value() {
        return this.value;
    }

    public static double round(double abs_1) {
        BigDecimal decimal = new BigDecimal(abs_1);
        decimal = decimal.setScale(2, RoundingMode.HALF_UP);
        return decimal.doubleValue();
    }

    public static double floor(double abs_1, double abs_2) {
        abs_1 = Math.floor(abs_1);
        abs_2 = Math.floor(abs_2);
        if (abs_1 == 0.0 || abs_2 == 0.0) {
            return abs_1 + abs_2;
        }
        return TurokDouble.floor(abs_1, abs_1 % abs_2);
    }

    public static double step(double abs_1, double abs_2) {
        double floor_requested = TurokDouble.floor(abs_1, abs_2);
        if (floor_requested > abs_2) {
            floor_requested = abs_2 / 20.0;
        }
        if (abs_2 > 10.0) {
            floor_requested = Math.round(floor_requested);
        }
        if (floor_requested == 0.0) {
            floor_requested = abs_2;
        }
        return floor_requested;
    }
}

