package me.darkeet.android.jni;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import com.google.webp.libwebp;
import java.nio.ByteBuffer;

/**
 * Name: WebpManager
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/12/1 16:31
 * Desc: webp格式图片处理
 * <p/>
 * https://gist.github.com/markbeaton/3719812
 * https://stackoverflow.com/questions/7032695/webp-for-android
 */
public class WebpManager {

    private WebpManager() {
    }

    static {
        System.loadLibrary("webp");
    }

    /**
     * Decodes byte array to bitmap
     *
     * @param encoded Byte array with WebP bitmap data
     * @return Decoded bitmap
     */
    public static Bitmap webpToBitmap(byte[] encoded) {
        int[] width = new int[]{0};
        int[] height = new int[]{0};
        byte[] decoded = libwebp.WebPDecodeARGB(encoded, encoded.length, width, height);

        int[] pixels = new int[decoded.length / 4];
        ByteBuffer.wrap(decoded).asIntBuffer().get(pixels);

        return Bitmap.createBitmap(pixels, width[0], height[0], Bitmap.Config.ARGB_8888);
    }

    /**
     * Encodes bitmap into byte array
     *
     * @param bitmap  Bitmap
     * @param quality Quality, should be between 0 and 100
     * @return Encoded byte array
     */
    public static byte[] bitmapToWebp(Bitmap bitmap, int quality) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        if (bitmap.getConfig().equals(Config.ARGB_8888)) {
            int bytes = height * bitmap.getRowBytes();

            ByteBuffer buffer = ByteBuffer.allocate(bytes);
            bitmap.copyPixelsToBuffer(buffer);
            byte[] pixels = buffer.array();

            return libwebp.WebPEncodeRGBA(pixels, width, height, width * 4, quality);
        } else {
            byte[] pixels = new byte[width * height * 4];

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    int pixel = bitmap.getPixel(i, j);
                    int index = (j * width + i) * 4;
                    pixels[index] = (byte) (pixel & 0xff);
                    pixels[index + 1] = (byte) (pixel >> 8 & 0xff);
                    pixels[index + 2] = (byte) (pixel >> 16 & 0xff);
                    pixels[index + 3] = (byte) (pixel >> 24 & 0xff);
                }
            }

            return libwebp.WebPEncodeBGRA(pixels, width, height, width * 4, quality);
        }
    }
}
