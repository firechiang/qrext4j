/**
 * Copyright (c) 2016-~, Bosco.Liao (bosco_liao@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.iherus.codegen.qrcode;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * QR-Code eyes renderer.
 *
 * @author Bosco.Liao
 * @since 1.3.0
 */
public interface QreyesRenderer {

	/**
	 * Render code-eyes by format.
	 * 
	 * @param image
	 * @param format
	 * @param position
	 * @param slave
	 * @param border
	 * @param point
	 */
	void render(BufferedImage image, QreyesFormat format, QreyesPosition position, Color slave, Color border,
			Color point);

	/**
	 * Set code-eyes render shape.
	 * 
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param arcw
	 * @param arch
	 * @return
	 */
	default Shape getPointShape(double x, double y, double w, double h, double arcw, double arch) {
		return new Rectangle2D.Double(x, y, w, h);
	}

	/**
	 * Check the format match target renderer.
	 * 
	 * @param format
	 */
	default void checkFormat(QreyesFormat format) {
		return;
	}

}
