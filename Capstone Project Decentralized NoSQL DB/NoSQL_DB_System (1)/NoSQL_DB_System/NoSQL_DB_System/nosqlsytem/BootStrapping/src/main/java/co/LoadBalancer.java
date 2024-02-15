package co;

import java.util.ArrayList;
import java.util.List;



class LoadBalancer {
    private List<Node> nodes;
    private int currentIndex;

    public LoadBalancer() {
        this.nodes = new ArrayList<>();
        this.currentIndex = 0;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public Node getNextNode() {
        int size = nodes.size();
        if (size == 0) {
            throw new IllegalStateException("No nodes available for load balancing.");
        }

        int index = currentIndex;
        currentIndex = (currentIndex + 1) % size;
        return nodes.get(index);
    }
}

