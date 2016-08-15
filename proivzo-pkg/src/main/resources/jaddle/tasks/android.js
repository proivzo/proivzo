(function() {
	var imports = new JavaImporter(java.io);

	var p = function (text, vars) {
		for each (var raw in vars.keySet()) {
			var key = '$' + raw.toUpperCase() + '$';
			var val = vars.get(raw);
			while (text.contains(key))
				text = text.replace(key, val);
		}
		return text;
	};

	var q = function (texts, vars) {
		for (var i = 0; i < texts.length; i++)
			texts[i] = p(texts[i], vars);
		return texts; 
	};

	var log = function (text) {
		print(text);
	};

	var os = function (name) {
		return sys.OS.contains(name);
	};

	with (imports) {
		return {
			exec: function(w, args, e, tasks) {
				var setenv = tasks.getTask('setenv').exec;
				var wget = tasks.getTask('wget').exec;
				var unzip = tasks.getTask('unzip').exec;
				var find = tasks.getTask('find').exec;
				var chmod = tasks.getTask('chmod').exec;
				var shell = tasks.getTask('shell').exec;
				var copy = tasks.getTask('copy').exec;

				var tmpDir = e.get('TEMP_DIR');
				var tolDir = e.get('TOOL_DIR');
				var wrkDir = w;

				log('=== Node JS download ===');
				setenv(w, ['NJSV','6.3.1'], e);
				var d = null;
				if (os('windows'))
					d = wget(tolDir, [p('https://nodejs.org/dist/v$NJSV$/node-v$NJSV$-win-x64.zip',e)], e);
				else if (os('mac_osx'))
					d = wget(tolDir, [p('https://nodejs.org/dist/v$NJSV$/node-v$NJSV$-darwin-x64.tar.gz',e)], e);
				else if (os('linux'))
					d = wget(tolDir, [p('https://nodejs.org/dist/v$NJSV$/node-v$NJSV$-linux-x64.tar.xz',e)], e);

				log('=== Node JS extract ===');
				var nodeHome = unzip(tolDir, d, e)[0][0];
				chmod(w, [nodeHome+'/bin'], e);

				log('=== Cordova install ===');
				var nodeExe = find(tolDir, [os('windows') ? 'node.exe' : 'node'], e)[0];
				setenv(w, ['NODE',nodeExe+''], e);
				var cordovaExe = find(tolDir, ['cordova'], e)[0];
				setenv(w, ['CORV','latest'], e);

				if (!cordovaExe) {
					if (os('unix'))
						shell(nodeHome, q(['$NODE$','lib/node_modules/npm/cli.js','install','-g','cordova@$CORV$'],e), e);
					else if (os('windows'))
						shell(nodeHome, q(['$NODE$','node_modules/npm/cli.js','install','-g','cordova@$CORV$'],e), e);
					cordovaExe = find(nodeHome, ['cordova'], e)[0];
				}

				log('=== New Cordova project ===');
				setenv(w, ['CORDOVA',cordovaExe+''], e);
				var scwd = 'JustAwebbyApp';
				setenv(w, ['SCWD',scwd], e);
				shell(tmpDir, q(['$NODE$','$CORDOVA$','create','$SCWD$','io.proizvo.game','Game'],e), e);

				log('=== Copy game resources ===');
				var appDir = new File(tmpDir, scwd);
				setenv(w, ['CWD',appDir+''], e);
				setenv(w, ['TWD','target'], e);
				copy(w, q(['$TWD$','$CWD$/www'],e), e);

				log('=== Android SDK download ===');
				setenv(w, ['ANDV','24.4.1'], e);
				d = null;
				if (os('windows'))
					d = wget(tolDir, [p('https://dl.google.com/android/android-sdk_r$ANDV$-windows.zip',e)], e);
				else if (os('mac_osx'))
					d = wget(tolDir, [p('https://dl.google.com/android/android-sdk_r$ANDV$-macosx.zip',e)], e);
				else if (os('linux'))
					d = wget(tolDir, [p('https://dl.google.com/android/android-sdk_r$ANDV$-linux.tgz',e)], e);

				log('=== Android SDK extract ===');
				var androidHome = unzip(tolDir, d, e)[0][0];
				chmod(w, [androidHome+'/tools/android'], e);
				var androidExe = find(tolDir, [os('windows') ? 'android.bat' : 'android'], e)[0];
				setenv(w, ['ANDROID',androidExe+''], e);

				log('=== Add Android platform ===');
				e.put('PATH', e.get('PATH') + File.pathSeparator + nodeExe.getParentFile());
				shell(appDir, q(['$NODE$','$CORDOVA$','platform','add','android'],e), e);

				log('=== Update Android SDK ===');
				e.put('STD_IN', 'y');
				shell(appDir, q(['$ANDROID$','update','sdk','--no-ui','--filter','build-tools-24.0.1,android-23'],e), e);
                                
				log('=== Build Android app ===');
				e.put('ANDROID_HOME', androidHome);
				chmod(w, [androidHome+'/build-tools'], e);
				shell(appDir, q(['$NODE$','$CORDOVA$','build','android'],e), e);
                                
				log('=== Serve Android app ===');
				serve(appDir, q(['android.apk','platforms/android/build/outputs/apk/android-debug.apk'],e), e);
			}
		}
	}
});