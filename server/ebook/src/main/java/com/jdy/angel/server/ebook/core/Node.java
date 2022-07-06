package com.jdy.angel.server.ebook.core;

import com.jdy.angel.server.ebook.core.labels.Label;
import com.jdy.angel.server.ebook.core.labels.Text;

import java.util.ArrayList;
import java.util.List;
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

    public Kind match(Label label) {
        return getValue().match(label);
    }

    public void add(Node node) {
        var label = node.getValue();
        if (label.isEmpty()) {
            return;
        }
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(node);
    }

    protected abstract Label getValue();


    @Override
    public String toString() {
        var value = getValue();
        if (children == null || children.isEmpty()) {
            return value.toString();
        }
        var joiner = new StringJoiner("\n", value.getPrefix() + "\n", "\n" + value.getSuffix());
        for (var child : children) {
            joiner.add(child.toString());
        }
        return joiner.toString();
    }
}
