package com.github.phylogeny.bettercushionplacement.config;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

public class ConfigFolderBase implements ConfigNode {
    protected final List<ConfigNode> contents = Lists.newArrayList();
    protected String translationKey = "";

    @Override
    public String translationKey() {
        return translationKey;
    }

    @Override
    public Type type() {
        return Type.FOLDER;
    }

    protected <V extends ConfigNode> V addNode(V node) {
        contents.add(node);
        return node;
    }

    public List<ConfigNode> getContents() {
        return Collections.unmodifiableList(contents);
    }
}
