(function() {
	var imports = new JavaImporter(java.awt.image, java.io, java.util,
			javax.imageio, org.apache.commons.io, com.jakewharton.byteunits,
			java.lang, com.mortennobel.imagescaling);

	with (imports) {
		var exts = [ 'jpg', 'jpeg', 'png' ];

		var resizeFile = function(input, dstWidth, dstHeight) {
			var fmt = FilenameUtils.getExtension(input.getName()).toUpperCase();
			var srcImg = ImageIO.read(input);
			var srcWidth = srcImg.getWidth();
			var srcHeight = srcImg.getHeight();
			var srcSize = BitUnit.format(input.length());
			print(" << '" + input + "', " + srcSize + ", " + fmt + ", "
					+ srcWidth + "x" + srcHeight);
			if (srcWidth <= dstWidth || srcHeight <= dstHeight)
				return;
			var output = input;
			var mro = new MultiStepRescaleOp(dstWidth, dstHeight);
			var destImg = mro.filter(srcImg, null);
			var destWidth = destImg.getWidth();
			var destHeight = destImg.getHeight();
			ImageIO.write(destImg, fmt, output);
			var destSize = BitUnit.format(output.length());
			print(" >> '" + output + "', " + destSize + ", " + fmt + ", "
					+ destWidth + "x" + destHeight);
		};

		var resizeFolder = function(folder, resolution) {
			var res = resolution.split('x');
			var width = Integer.parseInt(res[0]);
			var height = Integer.parseInt(res[1]);
			var files = FileUtils.listFiles(folder, exts, true);
			for (var i = 0; i < files.size(); i++) {
				var file = files.get(i);
				resizeFile(file, width, height);
			}
		};

		return {
			exec : function(work, args, env) {
				for (var i = 0; i < args.length; i++) {
					var arg = args[i];
					resizeFolder(work, arg);
				}
			}
		}
	}
});