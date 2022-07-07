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

    public boolean match(Label label) {
        return getValue().match(label);
    }

    public void add(String txt) {
        if (txt.isBlank()) {
            return;
        }
        var text = new Text(txt);
        add(text.toNode());
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
        return toString(0);
    }

    public String toString(int times) {
        var tab = Constant.TAB.repeat(times);
        var value = getValue();
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
}
