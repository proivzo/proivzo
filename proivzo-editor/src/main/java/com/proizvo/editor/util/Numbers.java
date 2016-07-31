package com.proizvo.editor.util;

import me.lemire.integercompression.differential.IntegratedBinaryPacking;
import me.lemire.integercompression.differential.IntegratedIntCompressor;
import me.lemire.integercompression.differential.IntegratedVariableByte;
import me.lemire.integercompression.differential.SkippableIntegratedComposition;

public class Numbers {

    private Numbers() {
    }

    private static final IntegratedIntCompressor iic;

    static {
        iic = new IntegratedIntCompressor(new SkippableIntegratedComposition(
                new IntegratedBinaryPacking(), new IntegratedVariableByte()));
    }

    public static int[] unzip(int... compressed) {
        return iic.uncompress(compressed);
    }
}
