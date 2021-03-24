/*
 * Decompiled with CFR 0.151.
 */
package me.travis.turok.values;

import me.travis.turok.values.TurokString;

public class TurokFloat {
    private TurokString name;
    private TurokString tag;
    private float value;
    private float max;
    private float min;

    public TurokFloat(TurokString name, TurokString tag, float _float, float min, float max) {
        this.name = name;
        this.tag = tag;
        this.value = this.value;
        this.max = max;
        this.min = min;
    }

    public void set_value(float _float) {
        this.value = _float;
    }

    public void set_slider_value(float _float) {
        this.value = _float >= this.max ? this.max : (_float <= this.min ? this.min : _float);
    }

    public TurokString get_name() {
        return this.name;
    }

    public TurokString get_tag() {
        return this.tag;
    }

    public float get_value() {
        return this.value;
    }
}

