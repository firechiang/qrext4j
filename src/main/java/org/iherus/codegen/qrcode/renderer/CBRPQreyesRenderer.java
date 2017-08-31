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
package org.iherus.codegen.qrcode.renderer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import org.iherus.codegen.qrcode.QreyesFormat;
import org.iherus.codegen.qrcode.QreyesPosition;
import org.iherus.codegen.qrcode.QreyesRenderer;
import org.iherus.codegen.utils.ReflectionUtils;

/**
 * Deprecated reason: low recognition rate
 *
 * @author Bosco.Liao
 * @since 1.3.0
 */
@Deprecated
public class CBRPQreyesRenderer implements QreyesRenderer {

	@Override
	public void render(BufferedImage image, QreyesFormat format, QreyesPosition position, Color slave, Color border,
			Color point) {

		checkFormat(format);

		int width = image.getWidth(), height = image.getHeight();
		int borderSize = position.getBorderSize(width);

		final String[] directions = { "topLeft", "topRight", "bottomLeft" };

		Graphics2D graphics = image.createGraphics();
		graphics.setBackground(slave);
		for (String direction : directions) {

			// clear area by slave color
			int[] rect = (int[]) ReflectionUtils.invokeMethod(position, direction + "Rect");
			graphics.clearRect(rect[0], rect[1], rect[2], rect[3]);

			// draw code-eyes border
			Shape shape = new Ellipse2D.Double(rect[0] + borderSize / 2, rect[1] + borderSize / 2, rect[2] - borderSize,
					rect[3] - borderSize);

			graphics.setColor(slave);
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			graphics.fill(shape);
			graphics.setStroke(new BasicStroke(borderSize));
			graphics.setColor(border);
			graphics.draw(shape);

			// draw code-eyes point
			rect = (int[]) ReflectionUtils.invokeMethod(position.focusPoint(width, height), direction + "Point");
			shape = getPointShape(rect[0], rect[1], rect[2], rect[3], 0, 0);
			graphics.setColor(point);
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			graphics.fill(shape);

			// reset border setting
			graphics.setStroke(new BasicStroke(0));
			graphics.setColor(point);
			graphics.draw(shape);
		}

		graphics.dispose();
		image.flush();
	}

	@Override
	public void checkFormat(QreyesFormat format) {
		if (QreyesFormat.C_BORDER_R_POINT != format) {
			throw new IllegalArgumentException("Can only render C_BORDER_R_POINT, but got " + format);
		}
	}

}
