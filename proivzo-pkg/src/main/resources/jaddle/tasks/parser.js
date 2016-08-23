(function() {
	var imports = new JavaImporter(java.io, org.apache.commons.io);

	with (imports) {
		var dataExts = [ 'json' ];
		var codeExts = [ 'js', 'css', 'ttf', 'html', 'rpgproject' ];
		var picExts = [ 'jpg', 'jpeg', 'png' ];
		var sndExts = [ 'ogg', 'm4a' ];

		var copy = function(root, src, dst) {
			var shorted = (src+'').replace(root+File.separator,'');
			var target = new File(dst, shorted);
			FileUtils.copyFile(src, target);
		};

		var log = function (text) {
			print(text);
		};

		var read = function (file) {
			var text = FileUtils.readFileToString(file, 'UTF8');
			text = 'function() { return ' + text + '; }';
			return ctx.engine.eval(text);
		};

		var unique = function(array) {
			var n = []; 
			for(var i = 0; i < array.length; i++) 
				if (n.indexOf(array[i]) == -1) 
					n.push(array[i]);
			return n;
		};

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

		function iterate(r, array, func) {
			for (var i in array) {
				var item = array[i];
				if (item == null) 
					continue;
				func(r, item);
			}
		}

		function parseStates(x, obj) {
			iterate(x, obj, parseState);
		}

		function parseState(x, obj) {
			x.icons.push(obj.iconIndex);
		}

		function parseTilesets(x, obj) {
			iterate(x, obj, parseTileset);
		}

		function parseTileset(x, obj) {
			for each (var tilesetName in atrim(obj.tilesetNames))
				x.tilesets.push(tilesetName);
		}

		function parseTroops(x, obj) {
			iterate(x, obj, parseTroop);
		}

		function parseTroop(x, obj) {
			// NO-OP!
		}

		function parseClasses(x, obj) {
			iterate(x, obj, parseClass);
		}

		function parseClass(x, obj) {
			// NO-OP!
		}

		function parseArmors(x, obj) {
			iterate(x, obj, parseArmor);
		}

		function parseArmor(x, obj) {
			x.icons.push(obj.iconIndex);
		}

		function parseSkills(x, obj) {
			iterate(x, obj, parseSkill);
		}

		function parseSkill(x, obj) {
			x.icons.push(obj.iconIndex);
		}

		function parseAnimations(x, obj) {
			iterate(x, obj, parseAnimation);
		}

		function parseAnimation(x, obj) {
			x.animations.push(obj.animation1Name);
			x.animations.push(obj.animation2Name);
			parseTimings(x, obj.timings);
		}

		function parseTimings(x, obj) {
			iterate(x, obj, parseTiming);
		}

		function parseTiming(x, obj) {
			if (obj.se == null)
				return;
			x.sounds.push(obj.se.name);
		}

		function parseMap(x, obj) {
			x.images.push(obj.battleback1Name);
			x.images.push(obj.battleback2Name);
			x.sounds.push(obj.bgm.name);
			x.sounds.push(obj.bgs.name);
			x.images.push(obj.parallaxName);
		}

		var parseMap001 = parseMap;
		var parseMap002 = parseMap;

		function parseEnemies(x, obj) {
			iterate(x, obj, parseEnemy);
		}

		function parseEnemy(x, obj) {
			x.icons.push(obj.battlerName);
		}

		function parseWeapons(x, obj) {
			iterate(x, obj, parseWeapon);
		}

		function parseWeapon(x, obj) {
			x.icons.push(obj.iconIndex);
		}

		function parseItems(x, obj) {
			iterate(x, obj, parseItem);
		}

		function parseItem(x, obj) {
			x.icons.push(obj.iconIndex);
		}

		function parseMapInfos(x, obj) {
			iterate(x, obj, parseMapInfo);
		}

		function parseMapInfo(x, obj) {
			// NO-OP!
		}

		function parseSystem(x, obj) {
			x.images.push(obj.airship.bgm.name);
			x.images.push(obj.airship.characterName);
			x.sounds.push(obj.battleBgm.name);
			x.images.push(obj.battleback1Name);
			x.images.push(obj.battleback2Name);
			x.images.push(obj.battlerName);
			x.sounds.push(obj.boat.bgm.name);
			x.images.push(obj.boat.characterName);
			x.sounds.push(obj.defeatMe.name);
			x.sounds.push(obj.gameoverMe.name);
			x.sounds.push(obj.ship.bgm.name);
			x.images.push(obj.ship.characterName);
			parseSounds(x, obj.sounds);
			x.images.push(obj.title1Name);
			x.images.push(obj.title2Name);
			x.sounds.push(obj.titleBgm.name);
			x.sounds.push(obj.victoryMe.name);
		}

		function parseSounds(x, obj) {
			iterate(x, obj, parseSound);
		}

		function parseSound(x, obj) {
			x.sounds.push(obj.name);
		}

		function parseActors(x, obj) {
			iterate(x, obj, parseActor);
		}

		function parseActor(x, obj) {
			x.images.push(obj.battlerName);
			x.images.push(obj.characterName);
			x.images.push(obj.faceName);
		}

		function parseCommonEvents(x, obj) {
			iterate(x, obj, parseCommonEvent);
		}

		function parseCommonEvent(x, obj) {
			// NO-OP!
		}

		function parseEverything(r, map) {
			parseStates(r, map['States']);
			parseTilesets(r, map['Tilesets']);
			parseTroops(r, map['Troops']);
			parseClasses(r, map['Classes']);
			parseArmors(r, map['Armors']);
			parseSkills(r, map['Skills']);
			parseMap002(r, map['Map002']);
			parseAnimations(r, map['Animations']);
			parseMap001(r, map['Map001']);
			parseEnemies(r, map['Enemies']);
			parseWeapons(r, map['Weapons']);
			parseItems(r, map['Items']);
			parseMapInfos(r, map['MapInfos']);
			parseSystem(r, map['System']);
			parseActors(r, map['Actors']);
			parseCommonEvents(r, map['CommonEvents']);
		}

		var optimize = function(work, src, dst) {
			var data = { };
			print("Parsing all files in '" + src + "'...");
			var dataFiles = FileUtils.listFiles(src, dataExts, true);
			for (var i = 0; i < dataFiles.size(); i++) {
				var dataFile = dataFiles.get(i);
				var dataName = FilenameUtils.getBaseName(dataFile);
				print("Parsing file about '" + dataName + "'...");
				var dataCode = read(dataFile);
				data[dataName] = dataCode();
			}
			var refs = { icons: [], tilesets: [], images: ['icon'], 
						 sounds: [], animations: [] };
			parseEverything(refs, data);
			var icons = unique(refs.icons);
			var tilesets = unique(refs.tilesets);
			var images = unique(refs.images);
			var sounds = unique(refs.sounds);
			var animations = unique(refs.animations);
			log('Found '+icons.length+' icons!');
			log('Found '+tilesets.length+' tilesets!');
			log('Found '+images.length+' images!');
			log('Found '+sounds.length+' sounds!');
			log('Found '+animations.length+' animations!');
			print("Optimizing '" + src + "' into '" + dst + "'...");
			var cntSounds = 0;
			var cntImages = 0;
			var cntDatas = 0;
			var cntCodes = 0;
			var cntRemoves = 0;
			var exts = null;
			var files = FileUtils.listFiles(src, exts, true);
			var cntFiles = files.size();
			for (var i = 0; i < cntFiles; i++) {
				var file = files.get(i);
				var name = FilenameUtils.getBaseName(file);
				var ext = FilenameUtils.getExtension(file);
				if (ext == sndExts[1] && sounds.indexOf(name) >= 0) {
					copy(src, file, dst);
					cntSounds++;
				} else if (picExts.indexOf(ext) >= 0 && (images.indexOf(name) >= 0 
						|| tilesets.indexOf(name) >= 0
						|| animations.indexOf(name) >= 0)) {
					copy(src, file, dst);
					cntImages++;
				} else if (dataExts.indexOf(ext) >= 0) {
					copy(src, file, dst);
					cntDatas++;
				} else if (codeExts.indexOf(ext) >= 0) {
					copy(src, file, dst);
					cntCodes++;
				} else
					cntRemoves++;
			}
			log(' '+cntSounds+' sounds, '+cntImages+' images, '+cntDatas+' datas, '
                +cntCodes+' codes, '+cntRemoves+' removed, '+cntFiles+' in total');
		};

		return {
			exec : function(work, args, env) {
				for (var i = 0; i < args.length; i = i + 2) {
					var from = new File(args[i]);
					var to = new File(args[i + 1]);
					optimize(work, from, to);
				}
			}
		}
	}
});
