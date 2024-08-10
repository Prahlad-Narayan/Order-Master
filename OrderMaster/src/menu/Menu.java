package menu;

import java.util.ArrayList;

import helper.TreeInterface;

public class Menu<T> implements TreeInterface<T> {
	
	private Node root;
	
	private class Node {
		private T item;
		private ArrayList<Node> children;
		
		private Node(T item) {
			this.item = item;
			this.children = new ArrayList<Node>();
		}
		
		Node searchParent(T item) {
			for(Node child : this.children) {
				if(child.item.equals(item)) {
					return this;
				}
			}
			
			for(Node child : this.children) {				
				Node parent = child.searchParent(item);
				if(parent != null) {
					return parent;
				}
			}
			
			return null;
		}
		
		Node search(T item) {
			if(this.item.equals(item)) {
				return this;
			}
			
			for(Node child : this.children) {
				Node foundNode = child.search(item);
				if(foundNode != null) {
					return foundNode;
				}
			}
			
			return null;
		}
		
		void addChild(T child) {
			Node childNode = new Node(child);
			this.children.add(childNode);
		}
		
		void removeChild(int index) {
			this.children.remove(index);
		}
		
		private void recursiveString(String singlePath, StringBuilder overall) {
			singlePath += this.item + " -> ";
			for(Node child : this.children) {
				if(child.children.size() == 0) {
					singlePath += child.item;
					overall.append(singlePath + "\n");
				} else {
					child.recursiveString(singlePath, overall);
				}
			}			
		}
	}
	
	public Menu(T item) {
		this.root = new Node(item);
	}

	@Override
	public T getRootItem() {
		return this.root.item;
	}

	@Override
	public void addChild(T parent, T child) throws Exception {
		Node parentFound = this.root.search(parent);
		if(parentFound == null) {
			throw new Exception("Invalid Parent");
		}
		
		parentFound.addChild(child);
	}

	@Override
	public boolean remove(T item) throws Exception {
		Node parentFound = this.root.searchParent(item);
		if(parentFound == null) {
			throw new Exception("Invalid Parent");
		}
		
		ArrayList<Node> children = parentFound.children;
		
		for(int i = 0; i < children.size(); i++) {
			if(children.get(i).item.equals(item)) {
				parentFound.removeChild(i);
				return true;
			}
		}
		
		return false;
	}
	
	public ArrayList<T> getChildren(T parent) throws Exception {
		Node parentFound = this.root.search(parent);
		if(parentFound == null) {
			throw new Exception("Invalid Parent");
		}
		
		ArrayList<T> genericChildren = new ArrayList<T>();
		ArrayList<Node> children = parentFound.children;
		
		for(int i = 0; i < children.size(); i++) {
			genericChildren.add(children.get(i).item);
		}
		
		return genericChildren;
	}
	
	public String toString() {
		StringBuilder overall = new StringBuilder("");
		this.root.recursiveString("", overall);
		return overall.toString();
	}
}
