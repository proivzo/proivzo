package com.proizvo.editor.data;

public class System {

    private Airship airship;
    private String[] armorTypes;
    private AttackMotion[] attackMotions;
    private Bgm battleBgm;
    private String battleback1Name;
    private String battleback2Name;
    private int battlerHue;
    private String battlerName;
    private Airship boat;
    private String currencyUnit;
    private Sound defeatMe;
    private int editMapId;
    private String[] elements;
    private String[] equipTypes;
    private String gameTitle;
    private Sound gameoverMe;
    private String locale;
    private int[] magicSkills;
    private boolean[] menuCommands;
    private boolean optDisplayTp;
    private boolean optDrawTitle;
    private boolean optExtraExp;
    private boolean optFloorDeath;
    private boolean optFollowers;
    private boolean optSideView;
    private boolean optSlipDeath;
    private boolean optTransparent;
    private int[] partyMembers;
    private Airship ship;
    private String[] skillTypes;
    private Sound[] sounds;
    private int startMapId;
    private int startX;
    private int startY;
    private String[] switches;
    private Terms terms;
    private Battler[] testBattlers;
    private int testTroopId;
    private String title1Name;
    private String title2Name;
    private Bgm titleBgm;
    private String[] variables;
    private long versionId;
    private Sound victoryMe;
    private String[] weaponTypes;
    private int[] windowTone;

    public void setSwitches(String[] switches) {
        this.switches = switches;
    }

    public void setStartMapId(int startMapId) {
        this.startMapId = startMapId;
    }

    public void setBattleBgm(Bgm battleBgm) {
        this.battleBgm = battleBgm;
    }

    public void setDefeatMe(Sound defeatMe) {
        this.defeatMe = defeatMe;
    }

    public void setGameoverMe(Sound gameoverMe) {
        this.gameoverMe = gameoverMe;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void setMagicSkills(int[] magicSkills) {
        this.magicSkills = magicSkills;
    }

    public void setSkillTypes(String[] skillTypes) {
        this.skillTypes = skillTypes;
    }

    public void setTestBattlers(Battler... testBattlers) {
        this.testBattlers = testBattlers;
    }

    public void setElements(String[] elements) {
        this.elements = elements;
    }

    public void setPartyMembers(int[] partyMembers) {
        this.partyMembers = partyMembers;
    }

    public void setOptFollowers(boolean optFollowers) {
        this.optFollowers = optFollowers;
    }

    public void setOptDrawTitle(boolean optDrawTitle) {
        this.optDrawTitle = optDrawTitle;
    }

    public void setAttackMotions(AttackMotion... attackMotions) {
        this.attackMotions = attackMotions;
    }

    public void setSounds(Sound... sounds) {
        this.sounds = sounds;
    }

    public void setTerms(Terms terms) {
        this.terms = terms;
    }

    public void setShip(Airship ship) {
        this.ship = ship;
    }

    public void setMenuCommands(boolean[] menuCommands) {
        this.menuCommands = menuCommands;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public void setAirship(Airship airship) {
        this.airship = airship;
    }

    public void setBoat(Airship boat) {
        this.boat = boat;
    }

    public void setOptDisplayTp(boolean optDisplayTp) {
        this.optDisplayTp = optDisplayTp;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setBattleback1Name(String battleback1Name) {
        this.battleback1Name = battleback1Name;
    }

    public void setBattleback2Name(String battleback2Name) {
        this.battleback2Name = battleback2Name;
    }

    public void setBattlerHue(int battlerHue) {
        this.battlerHue = battlerHue;
    }

    public void setBattlerName(String battlerName) {
        this.battlerName = battlerName;
    }

    public void setEquipTypes(String[] equipTypes) {
        this.equipTypes = equipTypes;
    }

    public void setCurrencyUnit(String currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    public void setEditMapId(int editMapId) {
        this.editMapId = editMapId;
    }

    public void setWindowTone(int[] windowTone) {
        this.windowTone = windowTone;
    }

    public void setWeaponTypes(String[] weaponTypes) {
        this.weaponTypes = weaponTypes;
    }

    public void setVersionId(long versionId) {
        this.versionId = versionId;
    }

    public void setVictoryMe(Sound victoryMe) {
        this.victoryMe = victoryMe;
    }

    public void setVariables(String[] variables) {
        this.variables = variables;
    }

    public void setTitle1Name(String title1Name) {
        this.title1Name = title1Name;
    }

    public void setTitle2Name(String title2Name) {
        this.title2Name = title2Name;
    }

    public void setTitleBgm(Bgm titleBgm) {
        this.titleBgm = titleBgm;
    }

    public void setTestTroopId(int testTroopId) {
        this.testTroopId = testTroopId;
    }

    public void setArmorTypes(String[] armorTypes) {
        this.armorTypes = armorTypes;
    }
}
