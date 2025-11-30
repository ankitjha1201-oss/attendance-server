package com.example.attendance_server.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.io.ByteArrayOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public final class QRUtil {

    private QRUtil() {}

    /**
     * Generate PNG bytes for a QR code for the given text.
     */
    public static byte[] generateQrPngBytes(String text, int width, int height) throws Exception {
        QRCodeWriter qrWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = qrWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", baos);
            return baos.toByteArray();
        }
    }

    /**
     * Convenience: return Base64 string suitable for data URI.
     */
    public static String generateQrBase64DataUri(String text, int width, int height) throws Exception {
        byte[] png = generateQrPngBytes(text, width, height);
        String base64 = Base64.getEncoder().encodeToString(png);
        return "data:image/png;base64," + base64;
    }
}
