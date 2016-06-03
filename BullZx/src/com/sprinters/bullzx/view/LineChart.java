package com.sprinters.bullzx.view;

import java.util.ArrayList;
import java.util.List;

import com.sprinters.bullzx.entity.DateValueEntity;
import com.sprinters.bullzx.entity.LineEntity;
import com.sprinters.bullzx.event.IGestureDetector;
import com.sprinters.bullzx.event.IZoomable;
import com.sprinters.bullzx.event.OnZoomGestureListener;
import com.sprinters.bullzx.event.ZoomGestureDetector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.MotionEvent;

public class LineChart extends GridChart implements IZoomable {
	public static final int DEFAULT_LINE_ALIGN_TYPE = ALIGN_TYPE_JUSTIFY;

	private List<LineEntity<DateValueEntity>> linesData;

	private int maxPointNum;

	private double minValue;

	private double maxValue;
	
	private int lineAlignType = DEFAULT_LINE_ALIGN_TYPE;

	public static final boolean DEFAULT_AUTO_CALC_VALUE_RANGE = true;
	private boolean autoCalcValueRange = DEFAULT_AUTO_CALC_VALUE_RANGE;
	
	protected OnZoomGestureListener onZoomGestureListener = new OnZoomGestureListener();
	protected IGestureDetector zoomGestureDetector = new ZoomGestureDetector<IZoomable>(this);

	public LineChart(Context context) {
		super(context);
	}

	public LineChart(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public LineChart(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	protected void calcDataValueRange() {
		double maxValue = Double.MIN_VALUE;
		double minValue = Double.MAX_VALUE;
		
		for (int i = 0; i < this.linesData.size(); i++) {
			LineEntity<DateValueEntity> line = (LineEntity<DateValueEntity>) linesData
					.get(i);
			if (line == null) {
				continue;
			}
			if (line.isDisplay() == false) {
				continue;
			}
			List<DateValueEntity> lineData = line.getLineData();
			if (lineData == null) {
				continue;
			}
			
			for (int j = 0; j < lineData.size(); j++) {
				DateValueEntity entity;
				if (axisYPosition == AXIS_Y_POSITION_LEFT) {
					entity = line.getLineData().get(j);
				} else {
					entity = line.getLineData().get(lineData.size() - 1 - j);
				}

				if (entity.getValue() < minValue) {
					minValue = entity.getValue();
				}
				if (entity.getValue() > maxValue) {
					maxValue = entity.getValue();
				}
			}
		}

		this.maxValue = maxValue;
		this.minValue = minValue;
	}

	protected void calcValueRangePaddingZero() {
		double maxValue = this.maxValue;
		double minValue = this.minValue;

		if ((long) maxValue > (long) minValue) {
			if ((maxValue - minValue) < 10. && minValue > 1.) {
				this.maxValue = (long) (maxValue + 1);
				this.minValue = (long) (minValue - 1);
			} else {
				this.maxValue = (long) (maxValue + (maxValue - minValue) * 0.1);
				this.minValue = (long) (minValue - (maxValue - minValue) * 0.1);

				if (this.minValue < 0) {
					this.minValue = 0;
				}
			}
		} else if ((long) maxValue == (long) minValue) {
			if (maxValue <= 10 && maxValue > 1) {
				this.maxValue = maxValue + 1;
				this.minValue = minValue - 1;
			} else if (maxValue <= 100 && maxValue > 10) {
				this.maxValue = maxValue + 10;
				this.minValue = minValue - 10;
			} else if (maxValue <= 1000 && maxValue > 100) {
				this.maxValue = maxValue + 100;
				this.minValue = minValue - 100;
			} else if (maxValue <= 10000 && maxValue > 1000) {
				this.maxValue = maxValue + 1000;
				this.minValue = minValue - 1000;
			} else if (maxValue <= 100000 && maxValue > 10000) {
				this.maxValue = maxValue + 10000;
				this.minValue = minValue - 10000;
			} else if (maxValue <= 1000000 && maxValue > 100000) {
				this.maxValue = maxValue + 100000;
				this.minValue = minValue - 100000;
			} else if (maxValue <= 10000000 && maxValue > 1000000) {
				this.maxValue = maxValue + 1000000;
				this.minValue = minValue - 1000000;
			} else if (maxValue <= 100000000 && maxValue > 10000000) {
				this.maxValue = maxValue + 10000000;
				this.minValue = minValue - 10000000;
			}
		} else {
			this.maxValue = 0;
			this.minValue = 0;
		}
	}

	protected void calcValueRangeFormatForAxis() {
		int rate = 1;

		if (this.maxValue < 3000) {
			rate = 1;
		} else if (this.maxValue >= 3000 && this.maxValue < 5000) {
			rate = 5;
		} else if (this.maxValue >= 5000 && this.maxValue < 30000) {
			rate = 10;
		} else if (this.maxValue >= 30000 && this.maxValue < 50000) {
			rate = 50;
		} else if (this.maxValue >= 50000 && this.maxValue < 300000) {
			rate = 100;
		} else if (this.maxValue >= 300000 && this.maxValue < 500000) {
			rate = 500;
		} else if (this.maxValue >= 500000 && this.maxValue < 3000000) {
			rate = 1000;
		} else if (this.maxValue >= 3000000 && this.maxValue < 5000000) {
			rate = 5000;
		} else if (this.maxValue >= 5000000 && this.maxValue < 30000000) {
			rate = 10000;
		} else if (this.maxValue >= 30000000 && this.maxValue < 50000000) {
			rate = 50000;
		} else {
			rate = 100000;
		}

		if (this.latitudeNum > 0 && rate > 1
				&& (long) (this.minValue) % rate != 0) {
		
			this.minValue = (long) this.minValue
					- (long) (this.minValue) % rate;
		}
		
		if (this.latitudeNum > 0
				&& (long) (this.maxValue - this.minValue)
						% (this.latitudeNum * rate) != 0) {
			
			this.maxValue = (long) this.maxValue
					+ this.latitudeNum * rate
					- (long) (this.maxValue - this.minValue) % (this.latitudeNum * rate);
		}
	}

	protected void calcValueRange() {
		if (null == this.linesData) {
			this.maxValue = 0;
			this.minValue = 0;
			return;
		}
		if (this.linesData.size() > 0) {
			this.calcDataValueRange();
			this.calcValueRangePaddingZero();
		} else {
			this.maxValue = 0;
			this.minValue = 0;
		}
		this.calcValueRangeFormatForAxis();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (autoCalcValueRange) {
			calcValueRange();
		}

		initAxisY();
		initAxisX();

		super.onDraw(canvas);
		drawLines(canvas);

	}

	protected void drawLines(Canvas canvas) {
		if (null == this.linesData) {
			return;
		}
		// distance between two points
		float lineLength;
		// start point‘s X
		float startX;

		// draw lines
		for (int i = 0; i < linesData.size(); i++) {
			LineEntity<DateValueEntity> line = (LineEntity<DateValueEntity>) linesData
					.get(i);
			if (line == null) {
				continue;
			}
			if (line.isDisplay() == false) {
				continue;
			}
			List<DateValueEntity> lineData = line.getLineData();
			if (lineData == null) {
				continue;
			}

			Paint mPaint = new Paint();
			mPaint.setColor(line.getLineColor());
			mPaint.setAntiAlias(true);
			// start point
			PointF ptFirst = null;
			if (axisYPosition == AXIS_Y_POSITION_LEFT) {
	            if (lineAlignType == ALIGN_TYPE_CENTER) {
	                lineLength= (dataQuadrant.getQuadrantPaddingWidth() / maxPointNum);
	                startX = dataQuadrant.getQuadrantPaddingStartX() + lineLength / 2;
	            }else {
	                lineLength= (dataQuadrant.getQuadrantPaddingWidth() / (maxPointNum - 1));
	                startX = dataQuadrant.getQuadrantPaddingStartX();
	            }
				
				for (int j = 0; j < maxPointNum; j++) {
					float value = lineData.get(j).getValue();
					// calculate Y
					float valueY = (float) ((1f - (value - minValue)
							/ (maxValue - minValue)) * dataQuadrant.getQuadrantPaddingHeight())
							+ dataQuadrant.getQuadrantPaddingStartY();

					// if is not last point connect to previous point
					if (j > 0) {
						canvas.drawLine(ptFirst.x, ptFirst.y, startX, valueY,
								mPaint);
					}
					// reset
					ptFirst = new PointF(startX, valueY);
					startX = startX + lineLength;
				}
			} else {
	            if (lineAlignType == ALIGN_TYPE_CENTER) {
	                lineLength= (dataQuadrant.getQuadrantPaddingWidth() / maxPointNum);
	                startX = dataQuadrant.getQuadrantPaddingEndX() - lineLength / 2;
	            }else {
	                lineLength= (dataQuadrant.getQuadrantPaddingWidth() / (maxPointNum - 1));
	                startX = dataQuadrant.getQuadrantPaddingEndX();
	            }
	            
				for (int j = maxPointNum - 1; j >= 0; j--) {
					float value = lineData.get(j).getValue();
					// calculate Y
					float valueY = (float) ((1f - (value - minValue)
							/ (maxValue - minValue)) * dataQuadrant.getQuadrantPaddingHeight())
							+ dataQuadrant.getQuadrantPaddingStartY();

					// if is not last point connect to previous point
					if (j < maxPointNum - 1) {
						canvas.drawLine(ptFirst.x, ptFirst.y, startX, valueY,
								mPaint);
					}
					// reset
					ptFirst = new PointF(startX, valueY);
					startX = startX - lineLength;
				}
			}
		}
	}

	@Override
	public String getAxisXGraduate(Object value) {

		float graduate = Float.valueOf(super.getAxisXGraduate(value));
		int index = (int) Math.floor(graduate * maxPointNum);

		if (index >= maxPointNum) {
			index = maxPointNum - 1;
		} else if (index < 0) {
			index = 0;
		}

		if (null == this.linesData) {
			return "";
		}
		LineEntity<DateValueEntity> line = (LineEntity<DateValueEntity>) linesData
				.get(0);
		if (line == null) {
			return "";
		}
		if (line.isDisplay() == false) {
			return "";
		}
		List<DateValueEntity> lineData = line.getLineData();
		if (lineData == null) {
			return "";
		}
		return String.valueOf(lineData.get(index).getDate());
	}

	@Override
	public String getAxisYGraduate(Object value) {
		float graduate = Float.valueOf(super.getAxisYGraduate(value));
		return String.valueOf((int) Math.floor(graduate * (maxValue - minValue)
				+ minValue));
	}
	
	public float longitudePostOffset(){
		if (lineAlignType == ALIGN_TYPE_CENTER) {
			float lineLength = dataQuadrant.getQuadrantPaddingWidth() / maxPointNum;
			return (this.dataQuadrant.getQuadrantPaddingWidth() - lineLength)/ (longitudeTitles.size() - 1);
	    }else{
			return this.dataQuadrant.getQuadrantPaddingWidth()/ (longitudeTitles.size() - 1);
	    }
	}
	
	public float longitudeOffset(){
		if (lineAlignType == ALIGN_TYPE_CENTER) {
			float lineLength = dataQuadrant.getQuadrantPaddingWidth() / maxPointNum;
			return dataQuadrant.getQuadrantPaddingStartX() + lineLength / 2;
		}else{
			return dataQuadrant.getQuadrantPaddingStartX();
		}
	}

	protected void initAxisY() {
		this.calcValueRange();
		List<String> titleY = new ArrayList<String>();
		float average = (int) ((maxValue - minValue) / this.getLatitudeNum());
		;
		// calculate degrees on Y axis
		for (int i = 0; i < this.getLatitudeNum(); i++) {
			String value = String.valueOf((int) Math.floor(minValue + i
					* average));
			if (value.length() < super.getLatitudeMaxTitleLength()) {
				while (value.length() < super.getLatitudeMaxTitleLength()) {
					value = " " + value;
				}
			}
			titleY.add(value);
		}
		// calculate last degrees by use max value
		String value = String.valueOf((int) Math.floor(((int) maxValue)));
		if (value.length() < super.getLatitudeMaxTitleLength()) {
			while (value.length() < super.getLatitudeMaxTitleLength()) {
				value = " " + value;
			}
		}
		titleY.add(value);

		super.setLatitudeTitles(titleY);
	}

	protected void initAxisX() {
		List<String> titleX = new ArrayList<String>();
		if (null != linesData && linesData.size() > 0) {
			float average = maxPointNum / this.getLongitudeNum();
			for (int i = 0; i < this.getLongitudeNum(); i++) {
				int index = (int) Math.floor(i * average);
				if (index > maxPointNum - 1) {
					index = maxPointNum - 1;
				}
				titleX.add(String.valueOf(
						linesData.get(0).getLineData().get(index).getDate())
						.substring(4));
			}
			titleX.add(String.valueOf(
					linesData.get(0).getLineData().get(maxPointNum - 1)
							.getDate()).substring(4));
		}
		super.setLongitudeTitles(titleX);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//valid
		if (!isValidTouchPoint(event.getX(),event.getY())) {
			return false;
		}
		if (null == linesData || linesData.size() == 0) {
			return false;
		}
		
		return zoomGestureDetector.onTouchEvent(event);
	}

	protected float calcDistance(MotionEvent event) {
		if(event.getPointerCount() <= 1) {
			return 0f;
		}else{
			float x = event.getX(0) - event.getX(1);
			float y = event.getY(0) - event.getY(1);
			return FloatMath.sqrt(x * x + y * y);
		}
	}

	public void zoomIn() {
		if (null == linesData || linesData.size() <= 0) {
			return;
		}
		if (maxPointNum > 10) {
			maxPointNum = maxPointNum - ZOOM_STEP;
		}
		
		this.postInvalidate();
	}

	public void zoomOut() {
		if (null == linesData || linesData.size() <= 0) {
			return;
		}
		if (maxPointNum < linesData.get(0).getLineData().size() - 1 - ZOOM_STEP) {
			maxPointNum = maxPointNum + ZOOM_STEP;
		}
		
		this.postInvalidate();
	}

	/**
	 * @return the linesData
	 */
	public List<LineEntity<DateValueEntity>> getLinesData() {
		return linesData;
	}

	/**
	 * @param linesData
	 *            the linesData to set
	 */
	public void setLinesData(List<LineEntity<DateValueEntity>> linesData) {
		this.linesData = linesData;
	}

	/**
	 * @return the maxPointNum
	 */
	public int getMaxPointNum() {
		return maxPointNum;
	}

	/**
	 * @param maxPointNum
	 *            the maxPointNum to set
	 */
	public void setMaxPointNum(int maxPointNum) {
		this.maxPointNum = maxPointNum;
	}

	/**
	 * @return the minValue
	 */
	public double getMinValue() {
		return minValue;
	}

	/**
	 * @param minValue
	 *            the minValue to set
	 */
	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

	/**
	 * @return the maxValue
	 */
	public double getMaxValue() {
		return maxValue;
	}

	/**
	 * @param maxValue
	 *            the maxValue to set
	 */
	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

	/**
	 * @return the autoCalcValueRange
	 */
	public boolean isAutoCalcValueRange() {
		return autoCalcValueRange;
	}

	/**
	 * @param autoCalcValueRange
	 *            the autoCalcValueRange to set
	 */
	public void setAutoCalcValueRange(boolean autoCalcValueRange) {
		this.autoCalcValueRange = autoCalcValueRange;
	}

	/**
	 * @return the lineAlignType
	 */
	public int getLineAlignType() {
		return lineAlignType;
	}

	/**
	 * @param lineAlignType the lineAlignType to set
	 */
	public void setLineAlignType(int lineAlignType) {
		this.lineAlignType = lineAlignType;
	}

	public void setOnZoomGestureListener(OnZoomGestureListener listener) {
		this.onZoomGestureListener = listener;
	}

	public OnZoomGestureListener getOnZoomGestureListener() {
		return onZoomGestureListener;
	}
}
