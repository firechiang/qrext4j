/*
 * Copyright 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.iherus.codegen.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;

import java.util.Map;

/**
 * This object renders a QR Code as a BitMatrix 2D array of greyscale values.
 * 
 * Update: 
 * 
 * 1.QR Code margin size custom.
 * 
 * 2.Add function "encodeX".
 * 
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class QrcodeWriter implements Writer {

	private static final int QUIET_ZONE_SIZE = 5;

	@Override
	public BitMatrix encode(String contents, BarcodeFormat format, int width, int height) throws WriterException {

		return encode(contents, format, width, height, null);
	}

	@Override
	public BitMatrix encode(String contents, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints)
			throws WriterException {

		if (contents.isEmpty()) {
			throw new IllegalArgumentException("Found empty contents");
		}

		if (format != BarcodeFormat.QR_CODE) {
			throw new IllegalArgumentException("Can only encode QR_CODE, but got " + format);
		}

		if (width < 0 || height < 0) {
			throw new IllegalArgumentException("Requested dimensions are too small: " + width + 'x' + height);
		}

		ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.L;
		int quietZone = QUIET_ZONE_SIZE;
		if (hints != null) {
			if (hints.containsKey(EncodeHintType.ERROR_CORRECTION)) {
				errorCorrectionLevel = ErrorCorrectionLevel
						.valueOf(hints.get(EncodeHintType.ERROR_CORRECTION).toString());
			}
			if (hints.containsKey(EncodeHintType.MARGIN)) {
				quietZone = Integer.parseInt(hints.get(EncodeHintType.MARGIN).toString());
			}
		}

		QRCode code = Encoder.encode(contents, errorCorrectionLevel, hints);
		return renderResult(code, width, height, quietZone);
	}
	
	public QRCodeBitMatrix encodeX(String contents, BarcodeFormat format, int width, int height,
			Map<EncodeHintType, ?> hints) throws WriterException {

		if (contents.isEmpty()) {
			throw new IllegalArgumentException("Found empty contents");
		}

		if (format != BarcodeFormat.QR_CODE) {
			throw new IllegalArgumentException("Can only encode QR_CODE, but got " + format);
		}

		if (width < 0 || height < 0) {
			throw new IllegalArgumentException("Requested dimensions are too small: " + width + 'x' + height);
		}

		ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.L;
		int quietZone = QUIET_ZONE_SIZE;
		if (hints != null) {
			if (hints.containsKey(EncodeHintType.ERROR_CORRECTION)) {
				errorCorrectionLevel = ErrorCorrectionLevel
						.valueOf(hints.get(EncodeHintType.ERROR_CORRECTION).toString());
			}
			if (hints.containsKey(EncodeHintType.MARGIN)) {
				quietZone = Integer.parseInt(hints.get(EncodeHintType.MARGIN).toString());
			}
		}

		QRCode code = Encoder.encode(contents, errorCorrectionLevel, hints);
		BitMatrix bitMatrix = renderResult(code, width, height, quietZone);

		return new QRCodeBitMatrix(code, bitMatrix);
		
	}

	// Note that the input matrix uses 0 == white, 1 == black, while the output matrix uses
	// 0 == black, 255 == white (i.e. an 8 bit greyscale bitmap).
	private static BitMatrix renderResult(QRCode code, int width, int height, int quietZone) {
		ByteMatrix input = code.getMatrix();
		if (input == null) {
			throw new IllegalStateException();
		}
		int inputWidth = input.getWidth();
		int inputHeight = input.getHeight();
		int outputWidth = Math.max(width, inputWidth);
		int outputHeight = Math.max(height, inputHeight);

		int multiple = Math.min(outputWidth / inputWidth, outputHeight / inputHeight);

		outputWidth += (quietZone * 2);
		outputHeight += (quietZone * 2);

		BitMatrix output = new BitMatrix(outputWidth, outputHeight);

		int offsetX = (outputWidth - (inputWidth * multiple)) / 2 - quietZone;
		int offsetY = (outputHeight - (inputHeight * multiple)) / 2 - quietZone;

		for (int inputY = 0, outputY = quietZone; inputY < inputHeight; inputY++, outputY += multiple) {
			// Write the contents of this row of the barcode
			for (int inputX = 0, outputX = quietZone; inputX < inputWidth; inputX++, outputX += multiple) {
				if (input.get(inputX, inputY) == 1) {
					output.setRegion(outputX + offsetX, outputY + offsetY, multiple, multiple);
				}
			}
		}

		return output;
	}
	
	
	static final class QRCodeBitMatrix {

		private final QRCode qrcode;

		private final BitMatrix bitMatrix;

		public QRCodeBitMatrix(QRCode qrcode, BitMatrix bitMatrix) {
			this.qrcode = qrcode;
			this.bitMatrix = bitMatrix;
		}

		public QRCode getQrcode() {
			return qrcode;
		}

		public BitMatrix getBitMatrix() {
			return bitMatrix;
		}

	}
	

}
