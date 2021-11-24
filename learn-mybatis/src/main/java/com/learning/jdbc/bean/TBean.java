package com.learning.jdbc.bean;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/8
 */
public class TBean implements Serializable {

    private int id;

    private int c;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return "TBean{" +
                "id='" + id + '\'' +
                ", c='" + c + '\'' +
                '}';
    }
}
