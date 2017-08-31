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

public enum QreyesFormat {

	/**
	 * Rectangle Border with Rectangle Point.
	 */
	R_BORDER_R_POINT,

	/**
	 * Rectangle Border with Circle Point.
	 */
	R_BORDER_C_POINT,

	/**
	 * Circle Border with Rectangle Point.
	 */
	C_BORDER_R_POINT,

	/**
	 * Circle Border with Circle Point.
	 */
	C_BORDER_C_POINT,

	/**
	 * RoundRectangle Border with Rectangle Point.
	 */
	R2_BORDER_R_POINT,

	/**
	 * RoundRectangle Border with Circle Point.
	 */
	R2_BORDER_C_POINT,

	/**
	 * Diagonal RoundRectangle Border with Rectangle Point.
	 */
	DR2_BORDER_R_POINT,

	/**
	 * Diagonal RoundRectangle Border with Circle Point.
	 */
	DR2_BORDER_C_POINT;

}
