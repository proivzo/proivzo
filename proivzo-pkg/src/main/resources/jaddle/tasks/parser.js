function log(txt) {
	document.write(txt+'<br />');
}

function atrim(array) { 
	var res = []; 
	for (var i = 0; i < array.length; i++) { 
		var item = array[i]; 
		if (item == null || item.length == 0) 
			continue; 
		res.push(item); 
	} 
	return res; 
}

function iterate(array, func) {
	for (var i in array) {
		var item = array[i];
		if (item == null) 
			continue;
		func(item);
	}
}

function parseStates(obj) {
	iterate(obj, parseState);
}

function parseState(obj) {
	var usedIcon = obj.iconIndex;
}

function parseTilesets(obj) {
	iterate(obj, parseTileset);
}

function parseTileset(obj) {
	var tilesetNames = atrim(obj.tilesetNames);
}

function parseTroops(obj) {
	iterate(obj, parseTroop);
}

function parseTroop(obj) {
	// NO-OP!
}

function parseClasses(obj) {
	iterate(obj, parseClass);
}

function parseClass(obj) {
	// NO-OP!
}

function parseArmors(obj) {
	iterate(obj, parseArmor);
}

function parseArmor(obj) {
	var usedIcon = obj.iconIndex;
}

function parseSkills(obj) {
	iterate(obj, parseSkill);
}

function parseSkill(obj) {
	var usedIcon = obj.iconIndex;
}

function parseAnimations(obj) {
	iterate(obj, parseAnimation);
}

function parseAnimation(obj) {
	var an1 = obj.animation1Name;
	var an2 = obj.animation2Name;
	parseTimings(obj.timings);
}

function parseTimings(obj) {
	iterate(obj, parseTiming);
}

function parseTiming(obj) {
    if (obj.se == null)
		return;
	var animationSound = obj.se.name;
}

function parseMap(obj) {
	var bb1 = obj.battleback1Name;
	var bb2 = obj.battleback2Name;
	var bgm = obj.bgm.name;
	var bgs = obj.bgs.name;
	var plx = obj.parallaxName;
}

var parseMap001 = parseMap;
var parseMap002 = parseMap;

function parseEnemies(obj) {
	iterate(obj, parseEnemy);
}

function parseEnemy(obj) {
	var icon = obj.battlerName;
}

function parseWeapons(obj) {
	iterate(obj, parseWeapon);
}

function parseWeapon(obj) {
	var usedIcon = obj.iconIndex;
}

function parseItems(obj) {
	iterate(obj, parseItem);
}

function parseItem(obj) {
	var usedIcon = obj.iconIndex;
}

function parseMapInfos(obj) {
	iterate(obj, parseMapInfo);
}

function parseMapInfo(obj) {
	// NO-OP!
}

function parseSystem(obj) {
	var bgmName = obj.airship.bgm.name;
	var chrName = obj.airship.characterName;
	var bbn = obj.battleBgm.name
	var bb1n = obj.battleback1Name
	var bb2n = obj.battleback2Name
	var btn = obj.battlerName
	var bbbn = obj.boat.bgm.name
	var bcn = obj.boat.characterName
	var dm = obj.defeatMe.name
	var gm = obj.gameoverMe.name
	var sbn = obj.ship.bgm.name
	var scn = obj.ship.characterName
	parseSounds(obj.sounds);
	var t1n = obj.title1Name;
	var t2n = obj.title2Name;
	var tbn = obj.titleBgm.name;
	var vmn = obj.victoryMe.name;
}

function parseSounds(obj) {
	iterate(obj, parseSound);
}

function parseSound(obj) {
	var soundName = obj.name;
}

function parseActors(obj) {
	iterate(obj, parseActor);
}

function parseActor(obj) {
	var bn = obj.battlerName;
	var cn = obj.characterName;
	var fn = obj.faceName;
}

function parseCommonEvents(obj) {
	iterate(obj, parseCommonEvent);
}

function parseCommonEvent(obj) {
	// NO-OP!
}

function parseEverything() {
	parseStates(states);
	parseTilesets(tilesets);
	parseTroops(troops);
	parseClasses(classes);
	parseArmors(armors);
	parseSkills(skills);
	parseMap002(map002);
	parseAnimations(animations);
	parseMap001(map001);
	parseEnemies(enemies);
	parseWeapons(weapons);
	parseItems(items);
	parseMapInfos(mapinfos);
	parseSystem(system);
	parseActors(actors);
	parseCommonEvents(commonevents);
}

