package pl.put.poznan.analyzer.commons;

/**
 * Available types of nodes
 */
public enum NodeType {
    /**
     * An extra node with negative number as id, it's used only in unweighted network (which is used in BFS).
     */
    ADDITIONAL,
    /**
     * A node where network starts, doesn't have any incoming connections.
     * Can be only one in the entire network
     */
    ENTRY,

    /**
     * A node where network ends, doesn't have any outcoming connections.
     * Can be only one in the entire network
     */
    EXIT,

    /**
     * A node inside network, which isn't an end or start of the network
     */
    REGULAR
}
