package com.proizvo.editor.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMDocumentType;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

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
            File idx = new File(projDir, "index.html");
            writeIndex(idx, gameTitle);
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

    private static void write(Document doc, File file) {
        try (FileWriter out = new FileWriter(file)) {
            OutputFormat fmt = OutputFormat.createPrettyPrint();
            fmt.setXHTML(true);
            fmt.setSuppressDeclaration(true);
            fmt.setExpandEmptyElements(true);
            fmt.setIndentSize(4);
            XMLWriter xml = new XMLWriter(out, fmt);
            xml.write(doc);
            xml.flush();
            xml.close();
        } catch (IOException e) {
            throw new RuntimeException("Could not write document!", e);
        }
    }

    private static void writeIndex(File idx, String gameTitle) {
        Document doc = DocumentHelper.createDocument();
        doc.setDocType(new DOMDocumentType("html", null));
        Element root = doc.addElement("html");
        Element head = root.addElement("head");
        addMeta(head, "UTF-8");
        addMeta(head, "apple-mobile-web-app-capable", "yes");
        addMeta(head, "apple-mobile-web-app-status-bar-style",
                "black-translucent");
        addMeta(head, "viewport", "user-scalable=no");
        addLink(head, "icon", "icon/icon.png", "image/png");
        addLink(head, "apple-touch-icon", "icon/icon.png", null);
        addLink(head, "stylesheet", "fonts/gamefont.css", "text/css");
        head.addElement("title").setText(gameTitle);
        Element body = root.addElement("body");
        body.addAttribute("style", "background-color: black");
        for (String jsPath : Arrays.asList("js/libs/pixi.js",
                "js/libs/fpsmeter.js", "js/libs/lz-string.js",
                "js/rpg_core.js", "js/rpg_managers.js", "js/rpg_objects.js",
                "js/rpg_scenes.js", "js/rpg_sprites.js", "js/rpg_windows.js",
                "js/plugins.js", "js/main.js")) {
            addScript(body, jsPath);
        }
        write(doc, idx);
    }

    private static Element addLink(Element root, String rel, String href,
            String type) {
        Element link = root.addElement("link");
        link.addAttribute("rel", rel);
        link.addAttribute("href", href);
        link.addAttribute("type", type);
        return link;
    }

    private static Element addMeta(Element root, String key, String value) {
        Element meta = root.addElement("meta");
        meta.addAttribute("name", key);
        meta.addAttribute("content", value);
        return meta;
    }

    private static Element addMeta(Element root, String charset) {
        Element meta = root.addElement("meta");
        meta.addAttribute("charset", charset);
        return meta;
    }

    private static Element addScript(Element root, String path) {
        Element script = root.addElement("script");
        script.addAttribute("type", "text/javascript");
        script.addAttribute("src", path);
        return script;
    }
}
