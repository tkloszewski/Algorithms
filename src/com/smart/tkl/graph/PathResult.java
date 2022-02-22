package com.smart.tkl.graph;

import java.util.List;

public class PathResult {

    private List<GraphNode> path;
    private Integer cost;

    public PathResult(List<GraphNode> path) {
        this.path = path;
    }

    public PathResult(List<GraphNode> path, Integer cost) {
        this.path = path;
        this.cost = cost;
    }

    public List<GraphNode> getPath() {
        return path;
    }

    public void setPath(List<GraphNode> path) {
        this.path = path;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(int i = 0; i < path.size() ; i++) {
            sb.append(path.get(i).getName());
            if (i != path.size() - 1) {
                sb.append(" => ");
            }
        }
        sb.append("], cost: ").append(this.cost);
        return sb.toString();
    }
}
