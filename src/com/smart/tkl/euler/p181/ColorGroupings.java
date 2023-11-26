package com.smart.tkl.euler.p181;

public class ColorGroupings {

    private final int[] colors;
    private final Object[] solutions;

    public ColorGroupings(int[] colors) {
        this.colors = colors;
        this.solutions = createMultiDimensionArray(colors);
        setValueAtZeroIndices(this.solutions, this.colors.length, 1);
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        int[] colors = new int[]{4, 0};
        ColorGroupings colorGroupings = new ColorGroupings(colors);
        long result = colorGroupings.count();
        long time2 = System.currentTimeMillis();
        System.out.println("Result: " + result);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long count() {
        count(new int[colors.length], 0 ,0);
        return getValueAt(this.solutions, this.colors);
    }

    private void count(int[] colorGroup, int colorsInGroup, int pos) {
        if(pos < this.colors.length - 1) {
            for(int colorCount = 0; colorCount <= this.colors[pos]; colorCount++) {
                colorGroup[pos] = colorCount;
                count(colorGroup, colorsInGroup + colorCount, pos + 1);
            }
        }
        else {
            for(int lastColorCount = colorsInGroup > 0 ? 0 : 1; lastColorCount <= this.colors[pos]; lastColorCount++) {
                colorGroup[pos] = lastColorCount;
                countForColorGroup(colorGroup, new int[colorGroup.length], 0);
            }
        }
    }

    private void countForColorGroup(int[] colorGroup, int[] selectedColors, int pos) {
        if(pos < this.colors.length - 1) {
            for(int color = colorGroup[pos]; color <= this.colors[pos]; color++) {
                selectedColors[pos] = color;
                countForColorGroup(colorGroup, selectedColors, pos + 1);
            }
        }
        else {
            for(int lastSelectedColor = colorGroup[pos]; lastSelectedColor <= this.colors[pos]; lastSelectedColor++) {
                selectedColors[pos] = lastSelectedColor;
                long currentValue = getValueAt(this.solutions, selectedColors);
                long previousValue = getValueAt(this.solutions, selectedColors, colorGroup);
                if (previousValue > 0) {
                    setValueAt(this.solutions, selectedColors, currentValue + previousValue);
                }
            }
        }
    }

    private static Object[] createMultiDimensionArray(int[] dimensions) {
        return createMultiDimensionArray(dimensions, 0);
    }

    private static Object[] createMultiDimensionArray(int[] dimensions, int pos) {
        if(pos == dimensions.length - 1) {
            Object[] result = new Long[dimensions[pos] + 1];
            for(int i = 0; i <= dimensions[pos]; i++) {
                result[i] = 0L;
            }
            return result;
        }
        else {
            Object[] result = new Object[dimensions[pos] + 1];
            for(int i = 0; i <= dimensions[pos]; i++) {
                result[i] = createMultiDimensionArray(dimensions, pos + 1);
            }
            return result;
        }
    }

    private static long getValueAt(Object[] multiArray, int[] indices) {
        Object[] ref = multiArray;
        for(int i = 0; i < indices.length - 1; i++) {
            ref = (Object[])ref[indices[i]];
        }
        return (long)ref[indices[indices.length - 1]];
    }

    private static long getValueAt(Object[] multiArray, int[] indices, int[] subtractIndices) {
        Object[] ref = multiArray;
        for(int i = 0; i < indices.length - 1; i++) {
            ref = (Object[])ref[indices[i] - subtractIndices[i]];
        }
        return (long)ref[indices[indices.length - 1] - subtractIndices[indices.length - 1]];
    }

    private static void setValueAt(Object[] multiArray, int[] indices, long value) {
        Object[] ref = multiArray;
        for(int i = 0; i < indices.length - 1; i++) {
            ref = (Object[])ref[indices[i]];
        }
        ref[indices[indices.length - 1]] = value;
    }

    private static void setValueAtZeroIndices(Object[] multiArray, int indicesLength, long value) {
        Object[] ref = multiArray;
        for(int i = 0; i < indicesLength - 1; i++) {
            ref = (Object[])ref[0];
        }
        ref[0] = value;
    }
}
