package com.rongxiaoli.backend.Math;

import java.security.SecureRandom;
import java.util.Random;

public class NewRandom extends SecureRandom {
    private SecureRandom randomSecure;
    private Random randomFast;
    public NewRandom() {
        this.randomSecure = new SecureRandom();
        this.randomFast = new Random();
    }
    public NewRandom(long seed) {
        this.randomSecure = new SecureRandom();
        this.randomSecure.setSeed(seed);
        this.randomFast = new Random(seed);
    }

    /**
     * Give out a number in half gaussian distribution. Start from 0.
     * @return Secure random number.
     */
    public double nextHalfRangedGaussian() {
        double baseNumber = randomSecure.nextGaussian();
        if (baseNumber < 0) {
            return -baseNumber;
        } else return baseNumber;
    }
    /**
     * Give out a number in half gaussian distribution. Start from start.
     * @param start Start number.
     * @return Secure random number.
     */
    public double nextHalfRangedGaussian(double start) {
        return start + nextHalfRangedGaussian();
    }
    /**
     * Give out a number in half gaussian distribution, using given sigma and miu.
     * @param mean Parameter "mean" in nextGaussian().
     * @param stddev Parameter "stddev in nextGaussian().
     * @return Secure random number.
     */
    public double nextHalfRangedGaussian(double mean, double stddev) {
        double baseNumber = randomSecure.nextGaussian(mean, stddev);
        if (baseNumber < 0) {
            return -baseNumber;
        } else return baseNumber;
    }

    /**
     * Give out a number in half gaussian distribution, using given sigma and miu. Start from start.
     * @param mean Parameter "mean" in nextGaussian().
     * @param stddev Parameter "stddev in nextGaussian().
     * @param start Start number.
     * @return Secure random number.
     */
    public double nextHalfRangedGaussian(double mean, double stddev, double start) {
        return start + nextHalfRangedGaussian(mean, stddev);
    }
    /**
     * Give out a number in half gaussian distribution. Start from 0.
     * @return Fast random number.
     */
    public double fastNextHalfRangedGaussian() {
        double baseNumber = randomFast.nextGaussian();
        if (baseNumber < 0) {
            return -baseNumber;
        } else return baseNumber;
    }
    /**
     * Give out a number in half gaussian distribution. Start from start.
     * @param start Start number.
     * @return Fast random number.
     */
    public double fastNextHalfRangedGaussian(double start) {
        return start + fastNextHalfRangedGaussian();
    }
    /**
     * Give out a number in half gaussian distribution, using given sigma and miu.
     * @param mean Parameter "mean" in nextGaussian().
     * @param stddev Parameter "stddev in nextGaussian().
     * @return Fast random number.
     */
    public double fastNextHalfRangedGaussian(double mean, double stddev) {
        double baseNumber = randomFast.nextGaussian(mean, stddev);
        if (baseNumber < 0) {
            return -baseNumber;
        } else return baseNumber;
    }
    /**
     * Give out a number in half gaussian distribution, using given sigma and miu. Start from start.
     * @param mean Parameter "mean" in nextGaussian().
     * @param stddev Parameter "stddev in nextGaussian().
     * @param start Start number.
     * @return Secure random number.
     */
    public double fastNextHalfRangedGaussian(double mean, double stddev, double start) {
        return start + fastNextHalfRangedGaussian(mean, stddev);
    }
    public int nextInt() {
        return randomSecure.nextInt();
    }
    public int nextInt(int bound) {
        return randomSecure.nextInt(bound);
    }
    public long nextLong() {
        return randomSecure.nextLong();
    }
    public boolean nextBoolean() {
        return randomSecure.nextBoolean();
    }
    public float nextFloat() {
        return randomSecure.nextFloat();
    }
    public double nextDouble() {
        return randomSecure.nextDouble();
    }
    public synchronized double nextGaussian() {
        return randomSecure.nextGaussian();
    }
    public float nextFloat(float bound) {
        return randomSecure.nextFloat(bound);
    }
    public float nextFloat(float origin, float bound) {
        return randomSecure.nextFloat(origin, bound);
    }
    public double nextDouble(double bound) {
        return randomSecure.nextDouble(bound);
    }
    public double nextDouble(double origin, double bound) {
        return randomSecure.nextDouble(origin, bound);
    }
    public int nextInt(int origin, int bound) {
        return randomSecure.nextInt(origin, bound);
    }
    public long nextLong(long bound) {
        return randomSecure.nextLong(bound);
    }
    public long nextLong(long origin, long bound) {
        return randomSecure.nextLong(origin, bound);
    }
    public double nextGaussian(double mean, double stddev) {
        return randomSecure.nextGaussian(mean, stddev);
    }
    public double nextExponential() {
        return randomSecure.nextExponential();
    }
}
