package com.jdy.angel.ebook.core;

import com.jdy.angel.ebook.core.labels.Label;
import com.jdy.angel.ebook.core.labels.Mark;
import com.jdy.angel.ebook.core.labels.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author Aglet
 * @create 2022/7/5 20:59
 */
public abstract class Node {
	protected List<Node> children;

	public static Node of(Label label, Text text) {
		var node = label.toNode();
		node.add(text.toNode());
		return node;
	}

	public boolean match(Label label) {
		return getLabel().match(label);
	}

	public void add(String txt) {
		if (txt.isBlank()) {
			return;
		}
		var text = new Text(txt);
		add(text.toNode());
	}

	public void add(Node node) {
		var label = node.getLabel();
		if (label.isEmpty()) {
			return;
		}
		if (children == null) {
			children = new ArrayList<>();
		}
//        node.getValue().register(ids);
		children.add(node);
	}

	public abstract Label getLabel();

	@Override
	public String toString() {
		return toString(0);
	}

	public String toString(int times) {
		var tab = Constant.TAB.repeat(times);
		var value = getLabel();
		if (children == null || children.isEmpty()) {
			return tab + value.toString();
		}
		var delimiter = value.getDelimiter();
		var prefix = tab + value.getPrefix() + delimiter;
		var suffix = delimiter + (delimiter.isEmpty() ? delimiter : tab) + value.getSuffix();
		var joiner = new StringJoiner(delimiter, prefix, suffix);
		var next = value.getChildTabs(times);
		for (var child : children) {
			joiner.add(child.toString(delimiter.isEmpty() ? 0 : next));
		}
		return joiner.toString();
	}

	public Node fetch(String content) {
		var value = getLabel();
		if (value instanceof Mark mark) {
			var s = mark.get("id");
			if (Objects.equals(s, content)) {
				return this;
			}
		}
		if (children != null) {
			for (Node child : children) {
				var fetch = child.fetch(content);
				if (fetch != null) {
					return fetch;
				}
			}
		}
		return null;
	}

	public List<Node> getChildren() {
		return children;
	}

	public boolean isText() {
		var value = getLabel();
		return value instanceof Text;
	}

	public void addAll(List<Node> list) {
		if (children == null) {
			this.children = list;
		} else {
			this.children.addAll(list);
		}
	}

	public String get(String key) {
		return getLabel().get(key);
	}

	public Node getNode(String name) {
		var value = getLabel();
		if (value.match(name)) {
			return this;
		}
		if (children == null || children.isEmpty()) {
			return null;
		}
		for (Node child : children) {
			var node = child.getNode(name);
			if (node != null) {
				return node;
			}
		}
		throw new IllegalStateException("Not found node named : " + name);
	}
	public void set(String origin, Object host) {
	}
}
