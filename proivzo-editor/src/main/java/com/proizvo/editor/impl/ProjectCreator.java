package com.proizvo.editor.impl;

import static com.proizvo.editor.util.Lists.insert;
import static com.proizvo.editor.util.Numbers.unzip;
import static com.proizvo.editor.util.Strings.asArray;
import static com.proizvo.editor.util.Strings.repeat;
import static com.proizvo.editor.impl.SecondCreator.*;
import static com.proizvo.editor.impl.ThirdCreator.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMDocumentType;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.helger.css.decl.CSSDeclaration;
import com.helger.css.decl.CSSExpression;
import com.helger.css.decl.CSSFontFaceRule;
import com.helger.css.decl.CascadingStyleSheet;
import com.helger.css.writer.CSSWriter;
import com.proizvo.editor.api.IProject;
import com.proizvo.editor.data.Action;
import com.proizvo.editor.data.Actor;
import com.proizvo.editor.data.Actors;
import com.proizvo.editor.data.Airship;
import com.proizvo.editor.data.Armor;
import com.proizvo.editor.data.Armors;
import com.proizvo.editor.data.AttackMotion;
import com.proizvo.editor.data.Battler;
import com.proizvo.editor.data.Bgm;
import com.proizvo.editor.data.Class;
import com.proizvo.editor.data.Classes;
import com.proizvo.editor.data.CommonEvent;
import com.proizvo.editor.data.CommonEvents;
import com.proizvo.editor.data.Conditions;
import com.proizvo.editor.data.Damage;
import com.proizvo.editor.data.DropItem;
import com.proizvo.editor.data.Effect;
import com.proizvo.editor.data.Enemies;
import com.proizvo.editor.data.Enemy;
import com.proizvo.editor.data.Event;
import com.proizvo.editor.data.Item;
import com.proizvo.editor.data.Items;
import com.proizvo.editor.data.Learning;
import com.proizvo.editor.data.Map;
import com.proizvo.editor.data.MapInfo;
import com.proizvo.editor.data.MapInfos;
import com.proizvo.editor.data.Member;
import com.proizvo.editor.data.Page;
import com.proizvo.editor.data.Skill;
import com.proizvo.editor.data.Skills;
import com.proizvo.editor.data.Sound;
import com.proizvo.editor.data.State;
import com.proizvo.editor.data.States;
import com.proizvo.editor.data.System;
import com.proizvo.editor.data.Terms;
import com.proizvo.editor.data.Trait;
import com.proizvo.editor.data.Troop;
import com.proizvo.editor.data.Troops;
import com.proizvo.editor.data.Weapon;
import com.proizvo.editor.data.Weapons;
import com.proizvo.editor.util.Files;

public class ProjectCreator {

    private static final Locale locale;
    static final ResourceBundle texts;

    static {
        locale = new Locale("de", "DE");
        texts = ResourceBundle.getBundle("TextBundle", locale);
    }

    public static IProject createNew(String gameTitle, String projectName,
            String storageLocation) {
        try {
            String templ = "template/";
            String tcode = templ + "code/";
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
            newJson(insert(new MapInfos(), null, new MapInfo(1, false,
                    "MAP001", 1, 0, 411.5, 334)), new File(data,
                    "MapInfos.json"));
            newJson(newStdMap(), new File(data, "Map001.json"));
            newJson(newArmors(), new File(data, "Armors.json"));
            newJson(newActors(), new File(data, "Actors.json"));
            newJson(newCommons(), new File(data, "CommonEvents.json"));
            newJson(newWeapons(), new File(data, "Weapons.json"));
            newJson(newEnemies(), new File(data, "Enemies.json"));
            newJson(newItems(), new File(data, "Items.json"));
            newJson(newTroops(), new File(data, "Troops.json"));
            newJson(newStates(), new File(data, "States.json"));
            newJson(newSkills(), new File(data, "Skills.json"));
            newJson(newSystem(gameTitle), new File(data, "System.json"));
            newJson(newClasses(), new File(data, "Classes.json"));
            newJson(newTilesets(), new File(data, "Tilesets.json"));
            newJson(newAnimations(), new File(data, "Animations.json"));
            File fonts = Files.mkdir(projDir, "fonts");
            writeFontsCSS(new File(fonts, "gamefont.css"));
            copyRes(templ + "game.ttf", new File(fonts, "mplus-1m-regular.ttf"));
            File icon = Files.mkdir(projDir, "icon");
            copyRes(templ + "proj_icon.png", new File(icon, "icon.png"));
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
            copyRes(tcode + "main.js", new File(js, "main.js"));
            copyRes(tcode + "plugins.js", new File(js, "plugins.js"));
            copyRes(tcode + "rpg_core.js", new File(js, "rpg_core.js"));
            copyRes(tcode + "rpg_managers.js", new File(js, "rpg_managers.js"));
            copyRes(tcode + "rpg_objects.js", new File(js, "rpg_objects.js"));
            copyRes(tcode + "rpg_scenes.js", new File(js, "rpg_scenes.js"));
            copyRes(tcode + "rpg_sprites.js", new File(js, "rpg_sprites.js"));
            copyRes(tcode + "rpg_windows.js", new File(js, "rpg_windows.js"));
            File libs = Files.mkdir(js, "libs");
            copyRes(templ + "pixi.min.js", new File(libs, "pixi.js"));
            copyRes(templ + "lz-string.min.js", new File(libs, "lz-string.js"));
            copyRes(templ + "fpsmeter.min.js", new File(libs, "fpsmeter.js"));
            File plugins = Files.mkdir(js, "plugins");
            File movies = Files.mkdir(projDir, "movies");
            return new RPGMakerMVProj(gpf);
        } catch (IOException e) {
            throw new RuntimeException("Could not create project!", e);
        }
    }

    private static Classes newClasses() throws JsonSyntaxException, IOException {
        Classes cs = new Classes();
        cs.add(null);
        Class c = new Class();
        c.setId(1);
        c.setName(texts.getString("class1name"));
        c.setNote("");
        c.setExpParams(30, 20, 30, 30);
        c.setLearnings(new Learning(1, "", 8), new Learning(1, "", 10));
        c.setTraits(new Trait(23, 0, 1), new Trait(22, 0, 0.95), new Trait(22,
                1, 0.05), new Trait(22, 2, 0.04), new Trait(41, 1, 0),
                new Trait(51, 2, 0), new Trait(52, 1, 0), new Trait(52, 3, 0),
                new Trait(52, 5, 0));
        c.setParams(
                unzip(100, 9, -1865711103, 210125601, -932957671, -2042420848,
                        1681004812, 1126273224, 840502406, 563136612,
                        420251203, 6, -1295307598, 749914924, -886263093,
                        -1295307598, 749914924, -886263093, 6, -1295307598,
                        749914924, -886263093, -1295307598, 749914924,
                        -886263093, -1296911694),
                unzip(100, 7, -1589465856, 169093200, 1352745605, -2062937048,
                        676372802, 1116015124, 338186401, 4, -1431655766,
                        -1431655766, -1431655766, -1431655766, 4, -1431655766,
                        -1431655766, -1431655766, -1431655766, -1970632054),
                unzip(100, 4, 303108849, 303108625, 303108625, 303108625, 2,
                        1701143909, -1785358955, 2, -1785358955, -1785358955,
                        -2122219135),
                unzip(100, 5, -2112845312, 277356808, -2011101054, 554205712,
                        142741640, 2, 1701143909, -1785358955, 2, -1785358955,
                        -1785358955, -2122219135),
                unzip(100, 5, -2112845312, 277356808, -2011101054, 554205712,
                        142741640, 2, 1701143909, -1785358955, 2, -1785358955,
                        -1785358955, -2122219135),
                unzip(100, 5, -2112845312, 277356808, -2011101054, 554205712,
                        142741640, 2, 1701143909, -1785358955, 2, -1785358955,
                        -1785358955, -2122219135),
                unzip(100, 6, -2096613376, 813893680, 137397000, -2096615293,
                        813893680, 137397000, 2, -1145324613, -1145324613, 2,
                        -1145324613, -1145324613, -2105310589),
                unzip(100, 6, -2096613376, 813893680, 137397000, -2096615293,
                        813893680, 137397000, 2, -1145324613, -1145324613, 2,
                        -1145324613, -1145324613, -2105310589));
        cs.add(c);
        c = new Class(c);
        c.setId(2);
        c.setName(texts.getString("class2name"));
        c.setNote("");
        c.setLearnings(new Learning[0]);
        c.setTraits(new Trait(23, 0, 1), new Trait(22, 0, 0.95), new Trait(22,
                1, 0.05), new Trait(22, 2, 0.04), new Trait(41, 1, 0),
                new Trait(51, 4, 0), new Trait(52, 1, 0), new Trait(52, 3, 0),
                new Trait(52, 4, 0), new Trait(52, 5, 0), new Trait(52, 6, 0));
        c.setParams(
                unzip(100, 9, -1865711103, 210125601, -932957671, -2042420848,
                        1681004812, 1126273224, 840502406, 563136612,
                        420251203, 6, -1295307598, 749914924, -886263093,
                        -1295307598, 749914924, -886263093, 6, -1295307598,
                        749914924, -886263093, -1295307598, 749914924,
                        -886263093, -1296911694),
                unzip(100, 7, -1589465856, 169093200, 1352745605, -2062937048,
                        676372802, 1116015124, 338186401, 4, -1431655766,
                        -1431655766, -1431655766, -1431655766, 4, -1431655766,
                        -1431655766, -1431655766, -1431655766, -1970632054),
                unzip(100, 4, 303108849, 303108625, 303108625, 303108625, 2,
                        1701143909, -1785358955, 2, -1785358955, -1785358955,
                        -2122219135),
                unzip(100, 5, -2112845312, 277356808, -2011101054, 554205712,
                        142741640, 2, 1701143909, -1785358955, 2, -1785358955,
                        -1785358955, -2122219135),
                unzip(100, 5, -2112845312, 277356808, -2011101054, 554205712,
                        142741640, 2, 1701143909, -1785358955, 2, -1785358955,
                        -1785358955, -2122219135),
                unzip(100, 5, -2112845312, 277356808, -2011101054, 554205712,
                        142741640, 2, 1701143909, -1785358955, 2, -1785358955,
                        -1785358955, -2122219135),
                unzip(100, 6, -2096613376, 813893680, 137397000, -2096615293,
                        813893680, 137397000, 2, -1145324613, -1145324613, 2,
                        -1145324613, -1145324613, -2105310589),
                unzip(100, 6, -2096613376, 813893680, 137397000, -2096615293,
                        813893680, 137397000, 2, -1145324613, -1145324613, 2,
                        -1145324613, -1145324613, -2105310589));
        cs.add(c);
        c = new Class(c);
        c.setId(3);
        c.setName(texts.getString("class3name"));
        c.setNote("");
        c.setTraits(new Trait(23, 0, 1), new Trait(22, 0, 0.95), new Trait(22,
                1, 0.05), new Trait(22, 2, 0.04), new Trait(41, 1, 0),
                new Trait(51, 6, 0), new Trait(52, 1, 0), new Trait(52, 2, 0));
        c.setLearnings(new Learning(1, "", 9));
        c.setParams(
                unzip(100, 9, -1865711103, 210125601, -932957671, -2042420848,
                        1681004812, 1126273224, 840502406, 563136612,
                        420251203, 6, -1295307598, 749914924, -886263093,
                        -1295307598, 749914924, -886263093, 6, -1295307598,
                        749914924, -886263093, -1295307598, 749914924,
                        -886263093, -1296911694),
                unzip(100, 7, -1589465856, 169093200, 1352745605, -2062937048,
                        676372802, 1116015124, 338186401, 4, -1431655766,
                        -1431655766, -1431655766, -1431655766, 4, -1431655766,
                        -1431655766, -1431655766, -1431655766, -1970632054),
                unzip(100, 4, 303108849, 303108625, 303108625, 303108625, 2,
                        1701143909, -1785358955, 2, -1785358955, -1785358955,
                        -2122219135),
                unzip(100, 5, -2112845312, 277356808, -2011101054, 554205712,
                        142741640, 2, 1701143909, -1785358955, 2, -1785358955,
                        -1785358955, -2122219135),
                unzip(100, 5, -2112845312, 277356808, -2011101054, 554205712,
                        142741640, 2, 1701143909, -1785358955, 2, -1785358955,
                        -1785358955, -2122219135),
                unzip(100, 5, -2112845312, 277356808, -2011101054, 554205712,
                        142741640, 2, 1701143909, -1785358955, 2, -1785358955,
                        -1785358955, -2122219135),
                unzip(100, 6, -2096613376, 813893680, 137397000, -2096615293,
                        813893680, 137397000, 2, -1145324613, -1145324613, 2,
                        -1145324613, -1145324613, -2105310589),
                unzip(100, 6, -2096613376, 813893680, 137397000, -2096615293,
                        813893680, 137397000, 2, -1145324613, -1145324613, 2,
                        -1145324613, -1145324613, -2105310589));
        cs.add(c);
        c = new Class(c);
        c.setId(4);
        c.setName(texts.getString("class4name"));
        c.setNote("");
        c.setTraits(new Trait(23, 0, 1), new Trait(22, 0, 0.95), new Trait(22,
                1, 0.05), new Trait(22, 2, 0.04), new Trait(41, 1, 0),
                new Trait(51, 7, 0), new Trait(52, 1, 0), new Trait(52, 2, 0));
        c.setLearnings(new Learning(1, "", 8));
        c.setParams(
                unzip(100, 9, -1865711103, 210125601, -932957671, -2042420848,
                        1681004812, 1126273224, 840502406, 563136612,
                        420251203, 6, -1295307598, 749914924, -886263093,
                        -1295307598, 749914924, -886263093, 6, -1295307598,
                        749914924, -886263093, -1295307598, 749914924,
                        -886263093, -1296911694),
                unzip(100, 7, -1589465856, 169093200, 1352745605, -2062937048,
                        676372802, 1116015124, 338186401, 4, -1431655766,
                        -1431655766, -1431655766, -1431655766, 4, -1431655766,
                        -1431655766, -1431655766, -1431655766, -1970632054),
                unzip(100, 4, 303108849, 303108625, 303108625, 303108625, 2,
                        1701143909, -1785358955, 2, -1785358955, -1785358955,
                        -2122219135),
                unzip(100, 5, -2112845312, 277356808, -2011101054, 554205712,
                        142741640, 2, 1701143909, -1785358955, 2, -1785358955,
                        -1785358955, -2122219135),
                unzip(100, 5, -2112845312, 277356808, -2011101054, 554205712,
                        142741640, 2, 1701143909, -1785358955, 2, -1785358955,
                        -1785358955, -2122219135),
                unzip(100, 5, -2112845312, 277356808, -2011101054, 554205712,
                        142741640, 2, 1701143909, -1785358955, 2, -1785358955,
                        -1785358955, -2122219135),
                unzip(100, 6, -2096613376, 813893680, 137397000, -2096615293,
                        813893680, 137397000, 2, -1145324613, -1145324613, 2,
                        -1145324613, -1145324613, -2105310589),
                unzip(100, 6, -2096613376, 813893680, 137397000, -2096615293,
                        813893680, 137397000, 2, -1145324613, -1145324613, 2,
                        -1145324613, -1145324613, -2105310589));
        cs.add(c);
        return cs;
    }

    private static Armors newArmors() {
        Armors as = new Armors();
        as.add(null);
        Armor a = new Armor();
        a.setId(1);
        a.setName(texts.getString("armor1name"));
        a.setAtypeId(5);
        a.setDescription("");
        a.setEtypeId(2);
        a.setIconIndex(128);
        a.setNote("");
        a.setParams(new int[]{0, 0, 0, 10, 0, 0, 0, 0});
        a.setPrice(300);
        a.setTraits(new Trait(22, 1, 0));
        as.add(a);
        a = new Armor();
        a.setId(2);
        a.setName(texts.getString("armor2name"));
        a.setAtypeId(1);
        a.setDescription("");
        a.setEtypeId(3);
        a.setTraits(new Trait(22, 1, 0));
        a.setIconIndex(130);
        a.setNote("");
        a.setParams(new int[]{0, 0, 0, 10, 0, 0, 0, 0});
        a.setPrice(300);
        as.add(a);
        a = new Armor();
        a.setId(3);
        a.setName(texts.getString("armor3name"));
        a.setAtypeId(1);
        a.setDescription("");
        a.setEtypeId(4);
        a.setTraits(new Trait(22, 1, 0));
        a.setIconIndex(135);
        a.setNote("");
        a.setParams(new int[]{0, 0, 0, 10, 0, 0, 0, 0});
        a.setPrice(300);
        as.add(a);
        a = new Armor();
        a.setId(4);
        a.setName(texts.getString("armor4name"));
        a.setAtypeId(1);
        a.setDescription("");
        a.setEtypeId(5);
        a.setTraits(new Trait(22, 1, 0));
        a.setIconIndex(145);
        a.setNote("");
        a.setParams(new int[]{0, 0, 0, 0, 10, 0, 0, 0});
        a.setPrice(300);
        as.add(a);
        return as;
    }

    private static Map newStdMap() {
        Map map = new Map();
        map.setBattleback1Name("");
        map.setBattleback2Name("");
        map.setEvents(new String[]{null, null});
        map.setBgm(new Bgm("", 0, 100, 90));
        map.setBgs(new Bgm("", 0, 100, 90));
        map.setDisplayName("");
        map.setEncounterList(new String[0]);
        map.setEncounterStep(30);
        map.setNote("");
        map.setParallaxName("");
        map.setParallaxShow(true);
        map.setTilesetId(1);
        int h, w;
        map.setHeight(h = 13);
        map.setWidth(w = 17);
        int[] data = new int[(h * w) * 6];
        for (int i = 0; i < (h * w); i++) {
            data[i] = 2816;
        }
        for (int i = 0; i < ((h * w) * 5); i++) {
            data[(h * w) + i] = 0;
        }
        map.setData(data);
        return map;
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

    private static void copyRes(String src, File dest) {
        copyRes(ProjectCreator.class, src, dest);
    }

    private static void copyRes(java.lang.Class<?> clazz, String src, File dest) {
        copyRes(clazz.getClassLoader(), src, dest);
    }

    private static void copyRes(ClassLoader loader, String src, File dest) {
        try (InputStream in = loader.getResourceAsStream(src)) {
            try (FileOutputStream out = new FileOutputStream(dest)) {
                IOUtils.copy(in, out);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not read resource!", e);
        }
    }

    private static void writeFontsCSS(File dest) throws IOException {
        CascadingStyleSheet css = new CascadingStyleSheet();
        CSSFontFaceRule ffr = new CSSFontFaceRule();
        ffr.addDeclaration(new CSSDeclaration("font-family", CSSExpression
                .createSimple("GameFont")));
        ffr.addDeclaration(new CSSDeclaration("src", CSSExpression
                .createURI("mplus-1m-regular.ttf")));
        css.addRule(ffr);
        CSSWriter writer = new CSSWriter();
        try (FileWriter out = new FileWriter(dest)) {
            writer.writeCSS(css, out);
        }
    }

    private static void newJson(Object obj, File file) throws IOException {
        Gson json = (new GsonBuilder())/* .setPrettyPrinting() */.create();
        FileUtils.write(file, json.toJson(obj), "UTF-8");
    }

    private static CommonEvents newCommons() {
        CommonEvents evs = new CommonEvents();
        evs.add(null);
        CommonEvent e = new CommonEvent();
        e.setId(1);
        e.setName("");
        e.setSwitchId(1);
        e.setList(new Event(0, 0, new int[0]));
        evs.add(e);
        e = new CommonEvent();
        e.setId(2);
        e.setName("");
        e.setSwitchId(1);
        e.setList(new Event(0, 0, new int[0]));
        evs.add(e);
        e = new CommonEvent();
        e.setId(3);
        e.setName("");
        e.setSwitchId(1);
        e.setList(new Event(0, 0, new int[0]));
        evs.add(e);
        e = new CommonEvent();
        e.setId(4);
        e.setName("");
        e.setSwitchId(1);
        e.setList(new Event(0, 0, new int[0]));
        evs.add(e);
        return evs;
    }

    private static Actors newActors() {
        Actors acts = new Actors();
        acts.add(null);
        Actor a = new Actor();
        a.setId(1);
        a.setName(texts.getString("actor1name"));
        a.setBattlerName("Actor1_1");
        a.setCharacterIndex(0);
        a.setCharacterName("Actor1");
        a.setClassId(1);
        a.setEquips(new int[]{1, 1, 2, 3, 0});
        a.setFaceIndex(0);
        a.setFaceName("Actor1");
        a.setTraits(new String[0]);
        a.setInitialLevel(1);
        a.setMaxLevel(99);
        a.setNickname("");
        a.setNote("");
        a.setProfile("");
        acts.add(a);
        a = new Actor();
        a.setId(2);
        a.setName(texts.getString("actor2name"));
        a.setBattlerName("Actor1_8");
        a.setCharacterIndex(7);
        a.setCharacterName("Actor1");
        a.setClassId(2);
        a.setEquips(new int[]{2, 1, 2, 3, 0});
        a.setFaceIndex(7);
        a.setFaceName("Actor1");
        a.setTraits(new String[0]);
        a.setInitialLevel(1);
        a.setMaxLevel(99);
        a.setNickname("");
        a.setNote("");
        a.setProfile("");
        acts.add(a);
        a = new Actor();
        a.setId(3);
        a.setName(texts.getString("actor3name"));
        a.setBattlerName("Actor3_8");
        a.setCharacterIndex(7);
        a.setCharacterName("Actor3");
        a.setClassId(3);
        a.setEquips(new int[]{3, 0, 2, 3, 4});
        a.setFaceIndex(7);
        a.setFaceName("Actor3");
        a.setTraits(new String[0]);
        a.setInitialLevel(1);
        a.setMaxLevel(99);
        a.setNickname("");
        a.setNote("");
        a.setProfile("");
        acts.add(a);
        a = new Actor();
        a.setId(4);
        a.setName(texts.getString("actor4name"));
        a.setBattlerName("Actor2_7");
        a.setCharacterIndex(6);
        a.setCharacterName("Actor2");
        a.setClassId(4);
        a.setEquips(new int[]{4, 0, 2, 3, 4});
        a.setFaceIndex(6);
        a.setFaceName("Actor2");
        a.setTraits(new String[0]);
        a.setInitialLevel(1);
        a.setMaxLevel(99);
        a.setNickname("");
        a.setNote("");
        a.setProfile("");
        acts.add(a);
        return acts;
    }

    private static Weapons newWeapons() {
        Weapons ws = new Weapons();
        ws.add(null);
        Weapon w = new Weapon();
        w.setId(1);
        w.setAnimationId(6);
        w.setDescription("");
        w.setEtypeId(1);
        w.setTraits(new Trait(31, 1, 0), new Trait(22, 0, 0));
        w.setIconIndex(97);
        w.setName(texts.getString("weapon1name"));
        w.setNote("");
        w.setParams(new int[]{0, 0, 10, 0, 0, 0, 0, 0});
        w.setPrice(500);
        w.setWtypeId(2);
        ws.add(w);
        w = new Weapon();
        w.setId(2);
        w.setAnimationId(6);
        w.setDescription("");
        w.setEtypeId(1);
        w.setTraits(new Trait(31, 1, 0), new Trait(22, 0, 0));
        w.setIconIndex(99);
        w.setName(texts.getString("weapon2name"));
        w.setNote("");
        w.setParams(new int[]{0, 0, 10, 0, 0, 0, 0, 0});
        w.setPrice(500);
        w.setWtypeId(4);
        ws.add(w);
        w = new Weapon();
        w.setId(3);
        w.setAnimationId(1);
        w.setDescription("");
        w.setEtypeId(1);
        w.setTraits(new Trait(31, 1, 0), new Trait(22, 0, 0));
        w.setIconIndex(101);
        w.setName(texts.getString("weapon3name"));
        w.setNote("");
        w.setParams(new int[]{0, 0, 10, 0, 0, 0, 0, 0});
        w.setPrice(500);
        w.setWtypeId(6);
        ws.add(w);
        w = new Weapon();
        w.setId(4);
        w.setAnimationId(11);
        w.setDescription("");
        w.setEtypeId(1);
        w.setTraits(new Trait(31, 1, 0), new Trait(22, 0, 0));
        w.setIconIndex(102);
        w.setName(texts.getString("weapon4name"));
        w.setNote("");
        w.setParams(new int[]{0, 0, 10, 0, 0, 0, 0, 0});
        w.setPrice(500);
        w.setWtypeId(7);
        ws.add(w);
        return ws;
    }

    private static Enemies newEnemies() {
        Enemies es = new Enemies();
        es.add(null);
        Enemy e = new Enemy();
        e.setId(1);
        e.setName(texts.getString("enemy1name"));
        e.setActions(new Action(0, 0, 0, 5, 1));
        e.setBattlerName("Bat");
        e.setDropItems(new DropItem(1, 1, 0), new DropItem(1, 1, 0),
                new DropItem(1, 1, 0));
        e.setTraits(new Trait(22, 0, 0.95), new Trait(22, 1, 0.05), new Trait(
                31, 1, 0));
        e.setGold(0);
        e.setNote("");
        e.setParams(new int[]{200, 0, 30, 30, 30, 30, 30, 30});
        es.add(e);
        e = new Enemy();
        e.setId(2);
        e.setName(texts.getString("enemy2name"));
        e.setActions(new Action(0, 0, 0, 5, 1));
        e.setBattlerName("Slime");
        e.setDropItems(new DropItem(1, 1, 0), new DropItem(1, 1, 0),
                new DropItem(1, 1, 0));
        e.setTraits(new Trait(22, 0, 0.95), new Trait(22, 1, 0.05), new Trait(
                31, 1, 0));
        e.setGold(0);
        e.setNote("");
        e.setParams(new int[]{250, 0, 30, 30, 30, 30, 30, 30});
        es.add(e);
        e = new Enemy();
        e.setId(3);
        e.setName(texts.getString("enemy3name"));
        e.setActions(new Action(0, 0, 0, 5, 1));
        e.setBattlerName("Orc");
        e.setDropItems(new DropItem(1, 1, 0), new DropItem(1, 1, 0),
                new DropItem(1, 1, 0));
        e.setTraits(new Trait(22, 0, 0.95), new Trait(22, 1, 0.05), new Trait(
                31, 1, 0));
        e.setGold(0);
        e.setNote("");
        e.setParams(new int[]{300, 0, 30, 30, 30, 30, 30, 30});
        es.add(e);
        e = new Enemy();
        e.setId(4);
        e.setName(texts.getString("enemy4name"));
        e.setActions(new Action(0, 0, 0, 5, 1));
        e.setBattlerName("Minotaur");
        e.setDropItems(new DropItem(1, 1, 0), new DropItem(1, 1, 0),
                new DropItem(1, 1, 0));
        e.setTraits(new Trait(22, 0, 0.95), new Trait(22, 1, 0.05), new Trait(
                31, 1, 0));
        e.setGold(0);
        e.setNote("");
        e.setParams(new int[]{500, 0, 30, 30, 30, 30, 30, 30});
        es.add(e);
        return es;
    }

    private static States newStates() {
        States st = new States();
        st.add(null);
        State s = new State();
        s.setId(1);
        s.setChanceByDamage(100);
        s.setIconIndex(1);
        s.setMaxTurns(1);
        s.setMinTurns(1);
        s.setMotion(3);
        s.setPriority(100);
        s.setRestriction(4);
        s.setStepsToRemove(100);
        s.setTraits(new Trait(23, 9, 0));
        s.setReleaseByDamage(false);
        s.setMessage1(texts.getString("state1msg1"));
        s.setMessage2(texts.getString("state1msg2"));
        s.setMessage3(texts.getString("state1msg3"));
        s.setMessage4(texts.getString("state1msg4"));
        s.setName(texts.getString("state1name"));
        s.setNote(texts.getString("state1note"));
        st.add(s);
        s = new State(s);
        s.setId(2);
        s.setTraits(new Trait(62, 1, 0));
        s.setRestriction(0);
        s.setReleaseByDamage(null);
        s.setRemoveAtBattleEnd(true);
        s.setPriority(0);
        s.setMotion(0);
        s.setRemoveByRestriction(true);
        s.setAutoRemovalTiming(2);
        s.setDescription("");
        s.setIconIndex(0);
        s.setMessage1(texts.getString("state2msg1"));
        s.setMessage2(texts.getString("state2msg2"));
        s.setMessage3(texts.getString("state2msg3"));
        s.setMessage4(texts.getString("state2msg4"));
        s.setName(texts.getString("state2name"));
        s.setNote(texts.getString("state2note"));
        st.add(s);
        s = new State(s);
        s.setId(3);
        s.setAutoRemovalTiming(0);
        s.setRemoveByRestriction(false);
        s.setTraits(new Trait(14, 1, 0));
        s.setMessage1(texts.getString("state3msg1"));
        s.setMessage2(texts.getString("state3msg2"));
        s.setMessage3(texts.getString("state3msg3"));
        s.setMessage4(texts.getString("state3msg4"));
        s.setName(texts.getString("state3name"));
        s.setNote(texts.getString("state3note"));
        st.add(s);
        s = new State(s);
        s.setId(4);
        s.setDescription(null);
        s.setRemoveAtBattleEnd(false);
        s.setReleaseByDamage(false);
        s.setIconIndex(2);
        s.setMotion(1);
        s.setOverlay(1);
        s.setPriority(50);
        s.setTraits(new Trait(22, 7, -0.1));
        s.setMessage1(texts.getString("state4msg1"));
        s.setMessage2(texts.getString("state4msg2"));
        s.setMessage3(texts.getString("state4msg3"));
        s.setMessage4(texts.getString("state4msg4"));
        s.setName(texts.getString("state4name"));
        s.setNote(texts.getString("state4note"));
        st.add(s);
        s = new State(s);
        s.setId(5);
        s.setAutoRemovalTiming(1);
        s.setIconIndex(3);
        s.setMaxTurns(5);
        s.setMinTurns(3);
        s.setOverlay(2);
        s.setPriority(60);
        s.setRemoveAtBattleEnd(true);
        s.setTraits(new Trait(22, 0, -0.5));
        s.setMessage1(texts.getString("state5msg1"));
        s.setMessage2(texts.getString("state5msg2"));
        s.setMessage3(texts.getString("state5msg3"));
        s.setMessage4(texts.getString("state5msg4"));
        s.setName(texts.getString("state5name"));
        s.setNote(texts.getString("state5note"));
        st.add(s);
        s = new State(s);
        s.setId(6);
        s.setIconIndex(4);
        s.setOverlay(3);
        s.setPriority(65);
        s.setTraits(new Trait(42, 1, 0));
        s.setMessage1(texts.getString("state6msg1"));
        s.setMessage2(texts.getString("state6msg2"));
        s.setMessage3(texts.getString("state6msg3"));
        s.setMessage4(texts.getString("state6msg4"));
        s.setName(texts.getString("state6name"));
        s.setNote(texts.getString("state6note"));
        st.add(s);
        s = new State(s);
        s.setId(7);
        s.setChanceByDamage(50);
        s.setIconIndex(5);
        s.setMaxTurns(4);
        s.setMinTurns(2);
        s.setOverlay(4);
        s.setPriority(70);
        s.setRemoveByDamage(true);
        s.setRestriction(1);
        s.setTraits(new Trait[0]);
        s.setMessage1(texts.getString("state7msg1"));
        s.setMessage2(texts.getString("state7msg2"));
        s.setMessage3(texts.getString("state7msg3"));
        s.setMessage4(texts.getString("state7msg4"));
        s.setName(texts.getString("state7name"));
        s.setNote(texts.getString("state7note"));
        st.add(s);
        s = new State(s);
        s.setId(8);
        s.setIconIndex(6);
        s.setOverlay(5);
        s.setPriority(75);
        s.setRestriction(2);
        s.setMessage1(texts.getString("state8msg1"));
        s.setMessage2(texts.getString("state8msg2"));
        s.setMessage3(texts.getString("state8msg3"));
        s.setMessage4(texts.getString("state8msg4"));
        s.setName(texts.getString("state8name"));
        s.setNote(texts.getString("state8note"));
        st.add(s);
        s = new State(s);
        s.setId(9);
        s.setIconIndex(7);
        s.setOverlay(6);
        s.setPriority(80);
        s.setRestriction(3);
        s.setMessage1(texts.getString("state9msg1"));
        s.setMessage2(texts.getString("state9msg2"));
        s.setMessage3(texts.getString("state9msg3"));
        s.setMessage4(texts.getString("state9msg4"));
        s.setName(texts.getString("state9name"));
        s.setNote(texts.getString("state9note"));
        st.add(s);
        s = new State(s);
        s.setId(10);
        s.setChanceByDamage(100);
        s.setIconIndex(8);
        s.setMaxTurns(5);
        s.setMinTurns(3);
        s.setMotion(2);
        s.setOverlay(7);
        s.setPriority(90);
        s.setReleaseByDamage(true);
        s.setRestriction(4);
        s.setTraits(new Trait(22, 1, -1));
        s.setMessage1(texts.getString("state10msg1"));
        s.setMessage2(texts.getString("state10msg2"));
        s.setMessage3(texts.getString("state10msg3"));
        s.setMessage4(texts.getString("state10msg4"));
        s.setName(texts.getString("state10name"));
        s.setNote(texts.getString("state10note"));
        st.add(s);
        return st;
    }

    private static Troops newTroops() {
        Troops ts = new Troops();
        ts.add(null);
        Troop t = new Troop();
        t.setId(1);
        t.setMembers(new Member(1, 336, 436, false), new Member(1, 480, 436,
                false));
        t.setName(texts.getString("troop1name"));
        t.setPages(new Page(new Conditions(50, 1, false, 50, 0, false, 1,
                false, 0, 0, false, false), 0, new Event(0, 0, new int[0])));
        ts.add(t);
        t = new Troop();
        t.setId(2);
        t.setMembers(new Member(2, 337, 436, false), new Member(2, 480, 436,
                false));
        t.setName(texts.getString("troop2name"));
        t.setPages(new Page(new Conditions(50, 1, false, 50, 0, false, 1,
                false, 0, 0, false, false), 0, new Event(0, 0, new int[0])));
        ts.add(t);
        t = new Troop();
        t.setId(3);
        t.setMembers(new Member(3, 408, 436, false));
        t.setName(texts.getString("troop3name"));
        t.setPages(new Page(new Conditions(50, 1, false, 50, 0, false, 1,
                false, 0, 0, false, false), 0, new Event(0, 0, new int[0])));
        ts.add(t);
        t = new Troop();
        t.setId(4);
        t.setMembers(new Member(4, 408, 436, false));
        t.setName(texts.getString("troop4name"));
        t.setPages(new Page(new Conditions(50, 1, false, 50, 0, false, 1,
                false, 0, 0, false, false), 0, new Event(0, 0, new int[0])));
        ts.add(t);
        return ts;
    }

    private static Items newItems() {
        Items is = new Items();
        is.add(null);
        Item it = new Item();
        it.setId(1);
        it.setName(texts.getString("item1name"));
        it.setAnimationId(41);
        it.setConsumable(true);
        it.setDescription("");
        it.setIconIndex(176);
        it.setItypeId(1);
        it.setNote("");
        it.setPrice(50);
        it.setRepeats(1);
        it.setScope(7);
        it.setSuccessRate(100);
        it.setDamage(new Damage(false, 0, "0", 0, 20));
        it.setEffects(new Effect(11, 0, 0, 500));
        is.add(it);
        it = new Item();
        it.setId(2);
        it.setName(texts.getString("item2name"));
        it.setAnimationId(41);
        it.setConsumable(true);
        it.setDescription("");
        it.setIconIndex(176);
        it.setItypeId(1);
        it.setNote("");
        it.setPrice(100);
        it.setRepeats(1);
        it.setScope(7);
        it.setSuccessRate(100);
        it.setDamage(new Damage(false, 0, "0", 0, 20));
        it.setEffects(new Effect(12, 0, 0, 200));
        is.add(it);
        it = new Item();
        it.setId(3);
        it.setName(texts.getString("item3name"));
        it.setAnimationId(45);
        it.setConsumable(true);
        it.setDescription("");
        it.setIconIndex(176);
        it.setItypeId(1);
        it.setNote("");
        it.setPrice(200);
        it.setRepeats(1);
        it.setScope(7);
        it.setSuccessRate(100);
        it.setDamage(new Damage(false, 0, "0", 0, 20));
        it.setEffects(new Effect(22, 4, 1, 0), new Effect(22, 5, 1, 0),
                new Effect(22, 6, 1, 0), new Effect(22, 7, 1, 0), new Effect(
                        22, 8, 1, 0), new Effect(22, 9, 1, 0), new Effect(22,
                        10, 1, 0));
        is.add(it);
        it = new Item();
        it.setId(4);
        it.setName(texts.getString("item4name"));
        it.setIconIndex(176);
        it.setItypeId(1);
        it.setNote("");
        it.setPrice(300);
        it.setRepeats(1);
        it.setScope(9);
        it.setSuccessRate(100);
        it.setAnimationId(49);
        it.setConsumable(true);
        it.setDescription("");
        it.setDamage(new Damage(false, 0, "b.mhp / 2", 3, 20));
        it.setEffects(new Effect(22, 1, 1, 0));
        is.add(it);
        return is;
    }

    private static Skills newSkills() {
        Skills sk = new Skills();
        sk.add(null);
        Skill s = new Skill();
        s.setId(1);
        s.setAnimationId(-1);
        s.setDamage(new Damage(true, -1, "a.atk * 4 - b.def * 2", 1, 20));
        s.setDescription("");
        s.setEffects(new Effect(21, 0, 1, 0));
        s.setHitType(1);
        s.setIconIndex(76);
        s.setOccasion(1);
        s.setRepeats(1);
        s.setScope(1);
        s.setSuccessRate(100);
        s.setTpGain(10);
        s.setMessage1(texts.getString("skill1msg1"));
        s.setMessage2(texts.getString("skill1msg2"));
        s.setName(texts.getString("skill1name"));
        s.setNote(texts.getString("skill1note"));
        sk.add(s);
        s = new Skill(s);
        s.setId(2);
        s.setAnimationId(0);
        s.setDamage(new Damage(false, 0, "0", 0, 20));
        s.setEffects(new Effect(21, 2, 1, 0));
        s.setHitType(0);
        s.setIconIndex(81);
        s.setScope(11);
        s.setSpeed(2000);
        s.setMessage1(texts.getString("skill2msg1"));
        s.setMessage2(texts.getString("skill2msg2"));
        s.setName(texts.getString("skill2name"));
        s.setNote(texts.getString("skill2note"));
        sk.add(s);
        s = new Skill(s);
        s.setId(3);
        s.setAnimationId(-1);
        s.setDamage(new Damage(true, -1, "a.atk * 4 - b.def * 2", 1, 20));
        s.setEffects(new Effect(21, 0, 1, 0));
        s.setHitType(1);
        s.setIconIndex(76);
        s.setRepeats(2);
        s.setScope(1);
        s.setSpeed(0);
        s.setTpGain(5);
        s.setMessage1(texts.getString("skill3msg1"));
        s.setMessage2(texts.getString("skill3msg2"));
        s.setName(texts.getString("skill3name"));
        s.setNote(texts.getString("skill3note"));
        sk.add(s);
        s = new Skill(s);
        s.setId(4);
        s.setRepeats(1);
        s.setScope(4);
        s.setMessage1(texts.getString("skill4msg1"));
        s.setMessage2(texts.getString("skill4msg2"));
        s.setName(texts.getString("skill4name"));
        s.setNote(texts.getString("skill4note"));
        sk.add(s);
        s = new Skill(s);
        s.setId(5);
        s.setScope(5);
        s.setTpGain(4);
        s.setMessage1(texts.getString("skill5msg1"));
        s.setMessage2(texts.getString("skill5msg2"));
        s.setName(texts.getString("skill5name"));
        s.setNote(texts.getString("skill5note"));
        sk.add(s);
        s = new Skill(s);
        s.setId(6);
        s.setAnimationId(0);
        s.setDamage(new Damage(false, 0, "0", 0, 20));
        s.setEffects(new Effect(41, 0, 0, 0));
        s.setHitType(0);
        s.setIconIndex(82);
        s.setScope(11);
        s.setTpGain(0);
        s.setMessage1(texts.getString("skill6msg1"));
        s.setMessage2(texts.getString("skill6msg2"));
        s.setName(texts.getString("skill6name"));
        s.setNote(texts.getString("skill6note"));
        sk.add(s);
        s = new Skill(s);
        s.setId(7);
        s.setEffects(new Effect[0]);
        s.setIconIndex(81);
        s.setScope(0);
        s.setTpGain(10);
        s.setMessage1(texts.getString("skill7msg1"));
        s.setMessage2(texts.getString("skill7msg2"));
        s.setName(texts.getString("skill7name"));
        s.setNote(texts.getString("skill7note"));
        sk.add(s);
        s = new Skill(s);
        s.setId(8);
        s.setAnimationId(41);
        s.setDamage(new Damage(false, 0, "200 + a.mat", 3, 20));
        s.setIconIndex(72);
        s.setMpCost(5);
        s.setOccasion(0);
        s.setScope(7);
        s.setStypeId(1);
        s.setMessage1(texts.getString("skill8msg1"));
        s.setMessage2(texts.getString("skill8msg2"));
        s.setName(texts.getString("skill8name"));
        s.setNote(texts.getString("skill8note"));
        sk.add(s);
        s = new Skill(s);
        s.setId(9);
        s.setAnimationId(66);
        s.setDamage(new Damage(false, 2, "100 + a.mat * 2 - b.mdf * 2", 1, 20));
        s.setHitType(2);
        s.setIconIndex(64);
        s.setOccasion(1);
        s.setScope(1);
        s.setMessage1(texts.getString("skill9msg1"));
        s.setMessage2(texts.getString("skill9msg2"));
        s.setName(texts.getString("skill9name"));
        s.setNote(texts.getString("skill9note"));
        sk.add(s);
        s = new Skill(s);
        s.setId(10);
        s.setAnimationId(78);
        s.setDamage(new Damage(false, 4, "100 + a.mat * 2 - b.mdf * 2", 1, 20));
        s.setIconIndex(66);
        s.setScope(2);
        s.setMessage1(texts.getString("skill10msg1"));
        s.setMessage2(texts.getString("skill10msg2"));
        s.setName(texts.getString("skill10name"));
        s.setNote(texts.getString("skill10note"));
        sk.add(s);
        return sk;
    }

    private static System newSystem(String gameTitle) {
        System s = new System();
        s.setGameTitle(gameTitle);
        s.setWindowTone(new int[]{0, 0, 0, 0});
        s.setWeaponTypes(asArray(texts, "weaponTypes"));
        s.setVersionId(14969328);
        s.setVictoryMe(new Sound("Victory1", 0, 100, 90));
        s.setVariables(repeat(21, ""));
        s.setMenuCommands(repeat(6, true));
        s.setTestTroopId(4);
        s.setTitle1Name(texts.getString("title1Name"));
        s.setTitle2Name(texts.getString("title2Name"));
        s.setTitleBgm(new Bgm("Theme6", 0, 100, 90));
        s.setArmorTypes(asArray(texts, "armorTypes"));
        s.setSkillTypes(asArray(texts, "skillTypes"));
        s.setBattleback1Name("Grassland");
        s.setBattleback2Name("Grassland");
        s.setBattlerHue(0);
        s.setBattlerName("Dragon");
        s.setEquipTypes(asArray(texts, "equipTypes"));
        s.setElements(asArray(texts, "elements"));
        s.setCurrencyUnit("G");
        s.setEditMapId(1);
        s.setSwitches(repeat(21, ""));
        s.setStartMapId(1);
        s.setStartX(8);
        s.setStartY(6);
        s.setOptDisplayTp(true);
        s.setOptDrawTitle(true);
        s.setOptFollowers(true);
        s.setPartyMembers(new int[]{1, 2, 3, 4});
        s.setBattleBgm(new Bgm("Battle1", 0, 100, 90));
        s.setDefeatMe(new Sound("Defeat1", 0, 100, 90));
        s.setGameoverMe(new Sound("Gameover1", 0, 100, 90));
        s.setLocale(locale.getLanguage() + "_" + locale.getCountry());
        s.setMagicSkills(new int[]{1});
        s.setTestBattlers(new Battler(1, new int[]{1, 1, 2, 3, 0}, 1),
                new Battler(2, new int[]{2, 1, 2, 3, 0}, 1), new Battler(3,
                        new int[]{3, 0, 2, 3, 4}, 1), new Battler(4,
                        new int[]{4, 0, 2, 3, 4}, 1));
        s.setBoat(new Airship(new Bgm("Ship1", 0, 100, 90), 0, "Vehicle", 0, 0,
                0));
        s.setAirship(new Airship(new Bgm("Ship3", 0, 100, 90), 3, "Vehicle", 0,
                0, 0));
        s.setShip(new Airship(new Bgm("Ship2", 0, 100, 90), 1, "Vehicle", 0, 0,
                0));
        Terms t;
        s.setTerms(t = new Terms());
        t.setBasic(asArray(texts, "termsBasic"));
        t.setCommands(asArray(texts, "termsCommands"));
        t.setParams(asArray(texts, "termsParams"));
        TreeMap<String, String> tm;
        t.setMessages(tm = new TreeMap<>());
        String tmk = "msg_";
        for (String key : Collections.list(texts.getKeys())) {
            if (key.startsWith(tmk)) {
                tm.put(key.substring(tmk.length()), texts.getString(key));
            }
        }
        texts.getKeys();
        s.setAttackMotions(new AttackMotion(0, 0), new AttackMotion(1, 1),
                new AttackMotion(1, 2), new AttackMotion(1, 3),
                new AttackMotion(1, 4), new AttackMotion(1, 5),
                new AttackMotion(1, 6), new AttackMotion(2, 7),
                new AttackMotion(2, 8), new AttackMotion(2, 9),
                new AttackMotion(0, 10), new AttackMotion(0, 11),
                new AttackMotion(0, 12));
        s.setSounds(new Sound("Cursor2", 0, 100, 90), new Sound("Decision1", 0,
                100, 90), new Sound("Cancel2", 0, 100, 90), new Sound(
                "Buzzer1", 0, 100, 90), new Sound("Equip1", 0, 100, 90),
                new Sound("Save", 0, 100, 90), new Sound("Load", 0, 100, 90),
                new Sound("Battle1", 0, 100, 90), new Sound("Run", 0, 100, 90),
                new Sound("Attack3", 0, 100, 90), new Sound("Damage4", 0, 100,
                        90), new Sound("Collapse1", 0, 100, 90), new Sound(
                        "Collapse2", 0, 100, 90), new Sound("Collapse3", 0,
                        100, 90), new Sound("Damage5", 0, 100, 90), new Sound(
                        "Collapse4", 0, 100, 90), new Sound("Recovery", 0, 100,
                        90), new Sound("Miss", 0, 100, 90), new Sound(
                        "Evasion1", 0, 100, 90), new Sound("Evasion2", 0, 100,
                        90), new Sound("Reflection", 0, 100, 90), new Sound(
                        "Shop1", 0, 100, 90), new Sound("Item3", 0, 100, 90),
                new Sound("Item3", 0, 100, 90));
        return s;
    }
}
