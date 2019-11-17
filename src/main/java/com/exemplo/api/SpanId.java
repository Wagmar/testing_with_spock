package com.exemplo.api;

import com.google.common.primitives.Longs;
import lombok.experimental.UtilityClass;

import java.security.SecureRandom;
@UtilityClass
public class SpanId {

    public static long generateRandomLong() {
        byte[] bytes = new byte[8];
        nextBytes(bytes, 0, bytes.length);
        return Longs.fromByteArray(bytes);
    }


    public static void nextBytes(byte[] data, int offset, int length) {
        int rnd;
            for (int numInts = length / (Integer.SIZE / 8); numInts > 0; numInts--) {
            rnd = teste();
            data[offset++] = (byte) (rnd >> 24);
            data[offset++] = (byte) (rnd >> 16);
            data[offset++] = (byte) (rnd >>  8);
            data[offset++] = (byte) (rnd      );
        }
        rnd = teste();
            switch (length % (Integer.SIZE / 8)) {
            case 1:
                data[offset] = (byte) rnd;
                break;
            case 2:
                data[offset++] = (byte) (rnd >>  8);
                data[offset  ] = (byte) (rnd      );
                break;
            case 3:
                data[offset++] = (byte) (rnd >> 16);
                data[offset++] = (byte) (rnd >>  8);
                data[offset  ] = (byte) (rnd      );
                break;

            default:
                break;
        }
    }

    private static int teste() {
        SecureRandom random = new SecureRandom();
        return random.nextInt();
    }

}
