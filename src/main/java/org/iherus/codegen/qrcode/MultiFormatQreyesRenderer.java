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
import java.awt.image.BufferedImage;

import org.iherus.codegen.qrcode.renderer.CBCPQreyesRenderer;
import org.iherus.codegen.qrcode.renderer.CBRPQreyesRenderer;
import org.iherus.codegen.qrcode.renderer.DR2BCPQreyesRenderer;
import org.iherus.codegen.qrcode.renderer.DR2BRPQreyesRenderer;
import org.iherus.codegen.qrcode.renderer.R2BCPQreyesRenderer;
import org.iherus.codegen.qrcode.renderer.R2BRPQreyesRenderer;
import org.iherus.codegen.qrcode.renderer.RBCPQreyesRenderer;
import org.iherus.codegen.qrcode.renderer.RBRPQreyesRenderer;

public final class MultiFormatQreyesRenderer implements QreyesRenderer {

	@Override
	public void render(BufferedImage image, QreyesFormat format, QreyesPosition position, Color slave, Color border,
			Color point) {

		QreyesRenderer renderer;

		switch (format) {

		case R_BORDER_R_POINT:
			renderer = new RBRPQreyesRenderer();
			break;
		case R_BORDER_C_POINT:
			renderer = new RBCPQreyesRenderer();
			break;
		case C_BORDER_R_POINT:
			renderer = new CBRPQreyesRenderer();
			break;
		case C_BORDER_C_POINT:
			renderer = new CBCPQreyesRenderer();
			break;
		case R2_BORDER_R_POINT:
			renderer = new R2BRPQreyesRenderer();
			break;
		case R2_BORDER_C_POINT:
			renderer = new R2BCPQreyesRenderer();
			break;
		case DR2_BORDER_R_POINT:
			renderer = new DR2BRPQreyesRenderer();
			break;
		case DR2_BORDER_C_POINT:
			renderer = new DR2BCPQreyesRenderer();
			break;
		default:
			throw new IllegalArgumentException("No encoder available for format " + format);
		}

		renderer.render(image, format, position, slave, border, point);
	}

}
