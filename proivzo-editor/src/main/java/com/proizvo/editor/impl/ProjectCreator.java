package com.proizvo.editor.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMDocumentType;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.helger.css.decl.CSSDeclaration;
import com.helger.css.decl.CSSExpression;
import com.helger.css.decl.CSSFontFaceRule;
import com.helger.css.decl.CascadingStyleSheet;
import com.helger.css.writer.CSSWriter;
import com.proizvo.editor.api.IProject;
import com.proizvo.editor.data.Actor;
import com.proizvo.editor.data.Actors;
import com.proizvo.editor.data.Armor;
import com.proizvo.editor.data.Armors;
import com.proizvo.editor.data.Bgm;
import com.proizvo.editor.data.CommonEvent;
import com.proizvo.editor.data.CommonEvents;
import com.proizvo.editor.data.Enemies;
import com.proizvo.editor.data.Enemy;
import com.proizvo.editor.data.Event;
import com.proizvo.editor.data.Map;
import com.proizvo.editor.data.MapInfo;
import com.proizvo.editor.data.MapInfos;
import com.proizvo.editor.data.Trait;
import com.proizvo.editor.data.Weapon;
import com.proizvo.editor.data.Weapons;
import com.proizvo.editor.data.Action;
import com.proizvo.editor.data.Damage;
import com.proizvo.editor.data.DropItem;
import com.proizvo.editor.data.Effect;
import com.proizvo.editor.data.Item;
import com.proizvo.editor.data.Items;
import com.proizvo.editor.util.Files;

import static com.proizvo.editor.util.Lists.*;

public class ProjectCreator {

    private static final Locale locale;
    private static final ResourceBundle texts;

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
            newJson(insert(new MapInfos(), null, new MapInfo(1, false, "MAP001", 1, 0,
                    411.5, 334)), new File(data, "MapInfos.json"));
            newJson(newStdMap(), new File(data, "Map001.json"));
            newJson(newArmors(), new File(data, "Armors.json"));
            newJson(newActors(), new File(data, "Actors.json"));
            newJson(newCommons(), new File(data, "CommonEvents.json"));
            newJson(newWeapons(), new File(data, "Weapons.json"));
            newJson(newEnemies(), new File(data, "Enemies.json"));
            newJson(newItems(), new File(data, "Items.json"));
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

    private static void copyRes(Class<?> clazz, String src, File dest) {
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
        ffr.addDeclaration(new CSSDeclaration("font-family",
                CSSExpression.createSimple("GameFont")));
        ffr.addDeclaration(new CSSDeclaration("src",
                CSSExpression.createURI("mplus-1m-regular.ttf")));
        css.addRule(ffr);
        CSSWriter writer = new CSSWriter();
        try (FileWriter out = new FileWriter(dest)) {
            writer.writeCSS(css, out);
        }
    }

    private static void newJson(Object obj, File file) throws IOException {
        Gson json = (new GsonBuilder())/*.setPrettyPrinting()*/.create();
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
        e.setDropItems(new DropItem(1, 1, 0),
                new DropItem(1, 1, 0), new DropItem(1, 1, 0));
        e.setTraits(new Trait(22, 0, 0.95), new Trait(22, 1, 0.05),
                new Trait(31, 1, 0));
        e.setGold(0);
        e.setNote("");
        e.setParams(new int[]{200, 0, 30, 30, 30, 30, 30, 30});
        es.add(e);
        e = new Enemy();
        e.setId(2);
        e.setName(texts.getString("enemy2name"));
        e.setActions(new Action(0, 0, 0, 5, 1));
        e.setBattlerName("Slime");
        e.setDropItems(new DropItem(1, 1, 0),
                new DropItem(1, 1, 0), new DropItem(1, 1, 0));
        e.setTraits(new Trait(22, 0, 0.95), new Trait(22, 1, 0.05),
                new Trait(31, 1, 0));
        e.setGold(0);
        e.setNote("");
        e.setParams(new int[]{250, 0, 30, 30, 30, 30, 30, 30});
        es.add(e);
        e = new Enemy();
        e.setId(3);
        e.setName(texts.getString("enemy3name"));
        e.setActions(new Action(0, 0, 0, 5, 1));
        e.setBattlerName("Orc");
        e.setDropItems(new DropItem(1, 1, 0),
                new DropItem(1, 1, 0), new DropItem(1, 1, 0));
        e.setTraits(new Trait(22, 0, 0.95), new Trait(22, 1, 0.05),
                new Trait(31, 1, 0));
        e.setGold(0);
        e.setNote("");
        e.setParams(new int[]{300, 0, 30, 30, 30, 30, 30, 30});
        es.add(e);
        e = new Enemy();
        e.setId(4);
        e.setName(texts.getString("enemy4name"));
        e.setActions(new Action(0, 0, 0, 5, 1));
        e.setBattlerName("Minotaur");
        e.setDropItems(new DropItem(1, 1, 0),
                new DropItem(1, 1, 0), new DropItem(1, 1, 0));
        e.setTraits(new Trait(22, 0, 0.95), new Trait(22, 1, 0.05),
                new Trait(31, 1, 0));
        e.setGold(0);
        e.setNote("");
        e.setParams(new int[]{500, 0, 30, 30, 30, 30, 30, 30});
        es.add(e);
        return es;
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
        it.setEffects(new Effect(22, 4, 1, 0),
                new Effect(22, 5, 1, 0), new Effect(22, 6, 1, 0),
                new Effect(22, 7, 1, 0), new Effect(22, 8, 1, 0),
                new Effect(22, 9, 1, 0), new Effect(22, 10, 1, 0));
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
}
