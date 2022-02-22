package com.smart.tkl.graph.triangle;

import java.util.ArrayList;
import java.util.List;

public class TriangleBuilder {

    public TriangleNode buildFromTable(int[] nums) {
        List<TriangleNode> nodes = new ArrayList<>(nums.length);
        for(int num : nums) {
            nodes.add(new TriangleNode(num));
        }

        for(int i = 0, step = 1; i < nodes.size() - step - 1; i += step, step++) {
            for(int j = i; j < i + step; j++) {
                TriangleNode currentNode = nodes.get(j);
                currentNode.addAdjacent(nodes.get(j + step));
                currentNode.addAdjacent(nodes.get(j + step + 1));
            }
        }

        return nodes.get(0);
    }

}
