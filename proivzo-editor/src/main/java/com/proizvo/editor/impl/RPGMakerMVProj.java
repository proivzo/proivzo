package com.proizvo.editor.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.proizvo.editor.api.IProject;
import com.proizvo.editor.data.Actors;
import com.proizvo.editor.data.MapInfo;
import com.proizvo.editor.data.MapInfos;
import com.proizvo.editor.data.Maps;
import com.proizvo.editor.data.Map;
import com.proizvo.editor.data.Tilesets;
import com.proizvo.editor.tiles.TileSetImage;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileUtils;

public class RPGMakerMVProj implements IProject {

    private final Gson json = (new GsonBuilder()).create();

    private final File projectFile;
    private final File projectBase;
    private final String projectName;
    private final String projectKind;
    private final String projectVersion;

    public RPGMakerMVProj(File file) throws IOException {
        this.projectFile = file;
        this.projectBase = file.getParentFile();
        this.projectName = projectBase.getName();
        String text = FileUtils.readFileToString(file, "UTF8");
        String[] parts = text.split(" ");
        this.projectKind = parts[0];
        this.projectVersion = parts[1];
    }

    @Override
    public String getProjectFile() {
        return projectFile.getAbsolutePath();
    }

    @Override
    public String getBaseDirectory() {
        return projectBase.getAbsolutePath();
    }

    @Override
    public String getVersion() {
        return projectVersion;
    }

    @Override
    public String getKind() {
        return projectKind;
    }

    @Override
    public String getName() {
        return projectName;
    }

    @Override
    public String getIcon() {
        return (new File(projectBase, "icon/icon.png")).getAbsolutePath();
    }

    public MapInfos getMapInfos() throws IOException {
        File mif = new File(projectBase, "data/MapInfos.json");
        try (FileReader in = new FileReader(mif)) {
            return json.fromJson(in, MapInfos.class);
        }
    }

    public Map getMap(String name) throws IOException {
        try (FileReader in = new FileReader(new File(projectBase,
                "data/" + name + ".json"))) {
            return json.fromJson(in, Map.class);
        }
    }

    public Maps getMaps() throws IOException {
        Maps maps = new Maps();
        for (MapInfo mi : getMapInfos()) {
            if (mi == null) {
                continue;
            }
            maps.add(getMap(mi.getName()));
        }
        return maps;
    }

    public Actors getActors() throws IOException {
        try (FileReader in = new FileReader(new File(projectBase,
                "data/Actors.json"))) {
            return json.fromJson(in, Actors.class);
        }
    }

    public Tilesets getTileSets() throws IOException {
        try (FileReader in = new FileReader(new File(projectBase,
                "data/Tilesets.json"))) {
            return json.fromJson(in, Tilesets.class);
        }
    }

    public TileSetImage getTileSet(String name) throws IOException {
        if (name == null || name.isEmpty()) {
            return null;
        }
        String baseName = String.format("img/tilesets/%s", name);
        String descFile = baseName + ".txt";
        File descPath = new File(projectBase, descFile);
        List<String> lines = FileUtils.readLines(descPath, "UTF8");
        String imgFile = baseName + ".png";
        String imgPath = (new File(projectBase, imgFile)).getAbsolutePath();
        Image img = Toolkit.getDefaultToolkit().getImage(imgPath);
        return new TileSetImage(img, lines);
    }
}
