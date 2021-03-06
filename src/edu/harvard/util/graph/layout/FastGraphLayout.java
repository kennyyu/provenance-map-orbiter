/*
 * A Collection of Miscellaneous Utilities
 *
 * Copyright 2011
 *      The President and Fellows of Harvard College.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the University nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE UNIVERSITY AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE UNIVERSITY OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */

package edu.harvard.util.graph.layout;

import edu.harvard.util.graph.*;

import java.util.*;


/**
 * A two-dimensional graph layout - fast, but memory-hungry implementation
 * 
 * @author Peter Macko
 */
public class FastGraphLayout extends GraphLayout {

	private static final long serialVersionUID = 7963405699924481830L;
	
	private Vector<GraphLayoutNode> nodes;
	private Vector<GraphLayoutEdge> edges;
	
	
	/**
	 * Create an instance of class FastGraphLayout
	 * 
	 * @param graph the graph
	 * @param algorithm the graph layout algorithm
	 * @param description the layout description
	 */
	public FastGraphLayout(BaseGraph graph, GraphLayoutAlgorithm algorithm, String description) {
		super(graph, algorithm, description);
		
		this.nodes = new Vector<GraphLayoutNode>(graph.getMaxNodeIndex() + 1);
		this.edges = new Vector<GraphLayoutEdge>(graph.getMaxEdgeIndex() + 1);
		
		for (int i = 0; i <= graph.getMaxNodeIndex(); i++) this.nodes.add(null);
		for (int i = 0; i <= graph.getMaxEdgeIndex(); i++) this.edges.add(null);
	}

	
	/**
	 * Return the graph nodes
	 *
	 * @return the collection of nodes
	 */
	public Collection<GraphLayoutNode> getLayoutNodes() {
		return nodes;
	}
	

	/**
	 * Return the graph edges
	 *
	 * @return the collection of edges
	 */
	public Collection<GraphLayoutEdge> getLayoutEdges() {
		return edges;
	}
	
	
	/**
	 * Get a node by its index
	 * 
	 * @param index the node index
	 * @return the node
	 */
	public GraphLayoutNode getLayoutNode(int index) {
		if (index >= nodes.size()) return null;
		return nodes.get(index);
	}
	
	
	/**
	 * Get an edge by its index
	 * 
	 * @param index the edge index
	 * @return the edge
	 */
	public GraphLayoutEdge getLayoutEdge(int index) {
		if (index >= edges.size()) return null;
		return edges.get(index);
	}
	
	
	/**
	 * Add a layout node without checking for errors
	 * 
	 * @param node the layout node to add
	 */
	protected void addLayoutNodeFast(GraphLayoutNode node) {
		nodes.set(node.getIndex(), node);
	}
	
	
	/**
	 * Add a layout edge without checking for errors
	 * 
	 * @param edge the layout edge to add
	 */
	protected void addLayoutEdgeFast(GraphLayoutEdge edge) {
		edges.set(edge.getBaseEdge().getIndex(), edge);
	}
}
