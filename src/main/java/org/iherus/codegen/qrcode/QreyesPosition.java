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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class QreyesPosition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 431898735159262499L;

	private static final PointPosition POS = new PointPosition();

	private int leftStartX;

	private int leftEndX;

	private int topStartY;

	private int topEndY;

	private int rightStartX;

	private int rightEndX;

	private int bottomStartY;

	private int bottomEndY;

	/**
	 * basic parameters.
	 */
	private int modules;

	private int[] topLeftOnBit;

	/**
	 * 
	 */
	public QreyesPosition() {
		super();
	}

	/**
	 * @param modules
	 * @param topLeftOnBit
	 */
	public QreyesPosition(int modules, int[] topLeftOnBit) {
		super();
		this.modules = modules;
		this.topLeftOnBit = topLeftOnBit;
	}

	/**
	 * @param leftStartX
	 * @param leftEndX
	 * @param topStartY
	 * @param topEndY
	 * @param rightStartX
	 * @param rightEndX
	 * @param bottomStartY
	 * @param bottomEndY
	 * @param modules
	 * @param topLeftOnBit
	 */
	public QreyesPosition(int leftStartX, int leftEndX, int topStartY, int topEndY, int rightStartX, int rightEndX,
			int bottomStartY, int bottomEndY, int modules, int[] topLeftOnBit) {
		this(modules, topLeftOnBit);
		this.setPosition(leftStartX, leftEndX, topStartY, topEndY, rightStartX, rightEndX, bottomStartY, bottomEndY);
	}

	public int getLeftStartX() {
		return leftStartX;
	}

	public void setLeftStartX(int leftStartX) {
		this.leftStartX = leftStartX;
	}

	public int getLeftEndX() {
		return leftEndX;
	}

	public void setLeftEndX(int leftEndX) {
		this.leftEndX = leftEndX;
	}

	public int getTopStartY() {
		return topStartY;
	}

	public void setTopStartY(int topStartY) {
		this.topStartY = topStartY;
	}

	public int getTopEndY() {
		return topEndY;
	}

	public void setTopEndY(int topEndY) {
		this.topEndY = topEndY;
	}

	public int getRightStartX() {
		return rightStartX;
	}

	public void setRightStartX(int rightStartX) {
		this.rightStartX = rightStartX;
	}

	public int getRightEndX() {
		return rightEndX;
	}

	public void setRightEndX(int rightEndX) {
		this.rightEndX = rightEndX;
	}

	public int getBottomStartY() {
		return bottomStartY;
	}

	public void setBottomStartY(int bottomStartY) {
		this.bottomStartY = bottomStartY;
	}

	public int getBottomEndY() {
		return bottomEndY;
	}

	public void setBottomEndY(int bottomEndY) {
		this.bottomEndY = bottomEndY;
	}

	public int getModules() {
		return modules;
	}

	public void setModules(int modules) {
		this.modules = modules;
	}

	public int[] getTopLeftOnBit() {
		return topLeftOnBit;
	}

	public void setTopLeftOnBit(int[] topLeftOnBit) {
		this.topLeftOnBit = topLeftOnBit;
	}

	public QreyesPosition setPosition(int leftStartX, int leftEndX, int topStartY, int topEndY, int rightStartX,
			int rightEndX, int bottomStartY, int bottomEndY) {
		this.leftStartX = leftStartX;
		this.leftEndX = leftEndX;
		this.topStartY = topStartY;
		this.topEndY = topEndY;
		this.rightStartX = rightStartX;
		this.rightEndX = rightEndX;
		this.bottomStartY = bottomStartY;
		this.bottomEndY = bottomEndY;
		return this;
	}

	public final int getModuleWidth(int imgWidth) {
		return (imgWidth - 2 * (topLeftOnBit[0])) / modules;
	}

	public final int getModuleHeight(int imgHeight) {
		return (imgHeight - 2 * (topLeftOnBit[1])) / modules;
	}

	public final int getBorderSize(int imgWidth) {
		return getModuleWidth(imgWidth);
	}

	public final int[] topLeftRect() {
		return new int[] { leftStartX, topStartY, leftEndX - leftStartX, topEndY - topStartY };
	}

	public final int[] topRightRect() {
		return new int[] { rightStartX, topStartY, rightEndX - rightStartX, topEndY - topStartY };
	}

	public final int[] bottomLeftRect() {
		return new int[] { leftStartX, bottomStartY, leftEndX - leftStartX, bottomEndY - bottomStartY };
	}

	private static final String clearCommand = "topLeftPoint" + "topRightPoint" + "bottomLeftPoint";

	public final int[] topLeftPoint() {
		Map<String, Object> m = POS.traceGet("topLeftPoint", clearCommand);
		return new int[] { (int) m.get("leftStartX"), (int) m.get("topStartY"),
				(int) m.get("leftEndX") - (int) m.get("leftStartX"),
				(int) m.get("topEndY") - (int) m.get("topStartY") };
	}

	public final int[] topRightPoint() {
		Map<String, Object> m = POS.traceGet("topRightPoint", clearCommand);
		return new int[] { (int) m.get("rightStartX"), (int) m.get("topStartY"),
				(int) m.get("rightEndX") - (int) m.get("rightStartX"),
				(int) m.get("topEndY") - (int) m.get("topStartY") };
	}

	public final int[] bottomLeftPoint() {
		Map<String, Object> m = POS.traceGet("bottomLeftPoint", clearCommand);
		return new int[] { (int) m.get("leftStartX"), (int) m.get("bottomStartY"),
				(int) m.get("leftEndX") - (int) m.get("leftStartX"),
				(int) m.get("bottomEndY") - (int) m.get("bottomStartY") };
	}

	public QreyesPosition focusPoint(int imgWidth, int imgHeight) {

		if (POS.executed()) return this;

		int w = getModuleWidth(imgWidth), h = getModuleHeight(imgHeight);
		int _leftStartX = topLeftOnBit[0] + w * QreyesRenderStrategy.POINT.getStart();
		int _leftEndX = topLeftOnBit[0] + w * QreyesRenderStrategy.POINT.getEnd();
		int _topStartY = topLeftOnBit[1] + h * QreyesRenderStrategy.POINT.getStart();
		int _topEndY = topLeftOnBit[1] + h * QreyesRenderStrategy.POINT.getEnd();
		int _rightStartX = topLeftOnBit[0] + w * (modules - QreyesRenderStrategy.POINT.getEnd());
		int _rightEndX = imgWidth - topLeftOnBit[0] - w * QreyesRenderStrategy.POINT.getStart();
		// correct 1 pixel offset
		int _bottomStartY = imgHeight - topLeftOnBit[1] - h * QreyesRenderStrategy.POINT.getEnd() - 1;
		int _bottomEndY = imgHeight - topLeftOnBit[1] - h * QreyesRenderStrategy.POINT.getStart() - 1;

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("leftStartX", _leftStartX);
		map.put("leftEndX", _leftEndX);
		map.put("topStartY", _topStartY);
		map.put("topEndY", _topEndY);
		map.put("rightStartX", _rightStartX);
		map.put("rightEndX", _rightEndX);
		map.put("bottomStartY", _bottomStartY);
		map.put("bottomEndY", _bottomEndY);

		POS.putAll(map);
		POS.count();
		return this;
	}

	private static class PointPosition extends ThreadLocal<Map<String, Object>> {

		private static final String RCK = PointPosition.class.getName() + "_record_check_methods.";

		private static final String COUNTER_KEY = PointPosition.class.getName() + "_method_execute_count.";

		@Override
		protected Map<String, Object> initialValue() {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(RCK, "");
			return map;
		}

		protected void putAll(Map<String, Integer> map) {
			get().putAll(map);
		}

		protected void count() {
			Object o = get().get(COUNTER_KEY);
			int c = o == null ? 0 : Integer.parseInt(o.toString());
			get().put(COUNTER_KEY, ++c);
		}

		protected boolean executed() {
			return !(null == get().get(COUNTER_KEY));
		}

		protected Map<String, Object> traceGet(String trace, String clearCommand) {
			get().put(RCK, get().get(RCK) + trace);
			Map<String, Object> result = get();
			if (result.get(RCK).toString().equals(clearCommand)) {
				remove();
			}
			return result;
		}

	}

}
