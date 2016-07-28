package com.proizvo.editor.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.proizvo.editor.api.IProject;
import com.proizvo.editor.util.Files;

public class ProjectCreator {

    public static IProject createNew(String gameTitle, String projectName,
            String storageLocation) {
        try {
            File projDir = new File(storageLocation, projectName);
            projDir.mkdir();
            String encoding = "UTF8";
            File gpf = new File(projDir, "Game.rpgproject");
            FileUtils.write(gpf, "RPGMV 1.0.0", encoding);
            File audio = Files.mkdir(projDir, "audio");
            File bgm = Files.mkdir(audio, "bgm");
            File bgs = Files.mkdir(audio, "bgs");
            File me = Files.mkdir(audio, "me");
            File se = Files.mkdir(audio, "se");
            File data = Files.mkdir(projDir, "data");
            File fonts = Files.mkdir(projDir, "fonts");
            File icon = Files.mkdir(projDir, "icon");
            File img = Files.mkdir(projDir, "img");
            File animations = Files.mkdir(img, "animations");
            File battlebacks1 = Files.mkdir(img, "battlebacks1");
            File battlebacks2 = Files.mkdir(img, "battlebacks2");
            File characters = Files.mkdir(img, "characters");
            File enemies = Files.mkdir(img, "enemies");
            File faces = Files.mkdir(img, "faces");
            File parallaxes = Files.mkdir(img, "parallaxes");
            File pictures = Files.mkdir(img, "pictures");
            File sv_actors = Files.mkdir(img, "sv_actors");
            File sv_enemies = Files.mkdir(img, "sv_enemies");
            File system = Files.mkdir(img, "system");
            File tilesets = Files.mkdir(img, "tilesets");
            File titles1 = Files.mkdir(img, "titles1");
            File titles2 = Files.mkdir(img, "titles2");
            File js = Files.mkdir(projDir, "js");
            File libs = Files.mkdir(js, "libs");
            File plugins = Files.mkdir(js, "plugins");
            File movies = Files.mkdir(projDir, "movies");
            return new RPGMakerMVProj(gpf);
        } catch (IOException e) {
            throw new RuntimeException("Could not create project!", e);
        }
    }
}
