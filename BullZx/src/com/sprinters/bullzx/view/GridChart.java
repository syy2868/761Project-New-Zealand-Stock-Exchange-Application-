package com.sprinters.bullzx.view;

import java.util.List;

import com.sprinters.bullzx.common.Axis;
import com.sprinters.bullzx.common.CrossLines;
import com.sprinters.bullzx.common.HorizontalAxis;
import com.sprinters.bullzx.common.IAxis;
import com.sprinters.bullzx.common.ICrossLines;
import com.sprinters.bullzx.common.IFlexableGrid;
import com.sprinters.bullzx.common.IQuadrant;
import com.sprinters.bullzx.common.Quadrant;
import com.sprinters.bullzx.common.VerticalAxis;
import com.sprinters.bullzx.event.IGestureDetector;
import com.sprinters.bullzx.event.ITouchable;
import com.sprinters.bullzx.event.OnTouchGestureListener;
import com.sprinters.bullzx.event.TouchGestureDetector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Style;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class GridChart extends AbstractBaseChart implements ITouchable, IFlexableGrid, ICrossLines {

	public static final int AXIS_X_POSITION_BOTTOM = 1 << 0;
	@Deprecated
	public static final int AXIS_X_POSITION_TOP = 1 << 1;
	public static final int AXIS_Y_POSITION_LEFT = 1 << 2;
	public static final int AXIS_Y_POSITION_RIGHT = 1 << 3;
	

	public static final int DEFAULT_AXIS_X_COLOR = Color.RED;

	public static final int DEFAULT_AXIS_Y_COLOR = Color.RED;
	public static final float DEFAULT_AXIS_WIDTH = 1f;

	public static final int DEFAULT_AXIS_X_POSITION = AXIS_X_POSITION_BOTTOM;

	public static final int DEFAULT_AXIS_Y_POSITION = AXIS_Y_POSITION_LEFT;

	public static final int DEFAULT_LONGITUDE_COLOR = Color.RED;

	public static final int DEFAULT_LAITUDE_COLOR = Color.RED;

	public static final float DEFAULT_AXIS_Y_TITLE_QUADRANT_WIDTH = 16f;

	// default margin of the axis to the bottom border
	public static final float DEFAULT_AXIS_X_TITLE_QUADRANT_HEIGHT = 16f;

	public static final int DEFAULT_CROSS_LINES_COLOR = Color.CYAN;
	public static final int DEFAULT_CROSS_LINES_FONT_COLOR = Color.CYAN;

	// default titles' max length for display of Y axis
	public static final int DEFAULT_LATITUDE_MAX_TITLE_LENGTH = 5;

	// default dashed line type
	public static final PathEffect DEFAULT_DASH_EFFECT = new DashPathEffect(
			new float[] { 6, 3, 6, 3 }, 1);

	public static final boolean DEFAULT_DISPLAY_CROSS_X_ON_TOUCH = true;

	public static final boolean DEFAULT_DISPLAY_CROSS_Y_ON_TOUCH = true;

	private int axisXColor = DEFAULT_AXIS_X_COLOR;

	private int axisYColor = DEFAULT_AXIS_Y_COLOR;

	private float axisWidth = DEFAULT_AXIS_WIDTH;

	protected int axisXPosition = DEFAULT_AXIS_X_POSITION;

	protected int axisYPosition = DEFAULT_AXIS_Y_POSITION;

	private int longitudeColor = DEFAULT_LONGITUDE_COLOR;

	private int latitudeColor = DEFAULT_LAITUDE_COLOR;

	protected float axisYTitleQuadrantWidth = DEFAULT_AXIS_Y_TITLE_QUADRANT_WIDTH;

	protected float axisXTitleQuadrantHeight = DEFAULT_AXIS_X_TITLE_QUADRANT_HEIGHT;

	private boolean displayLongitudeTitle = DEFAULT_DISPLAY_LONGITUDE_TITLE;
	
	private float longitudeWidth = DEFAULT_LONGITUDE_WIDTH;

	private boolean displayLatitudeTitle = DEFAULT_DISPLAY_LATITUDE_TITLE;
	
	private float latitudeWidth = DEFAULT_LATITUDE_WIDTH;

	protected int latitudeNum = DEFAULT_LATITUDE_NUM;

	// Numbers of grid‘s longitude line
	protected int longitudeNum = DEFAULT_LONGITUDE_NUM;

	private boolean displayLongitude = DEFAULT_DISPLAY_LONGITUDE;

	private boolean dashLongitude = DEFAULT_DASH_LONGITUDE;

	private boolean displayLatitude = DEFAULT_DISPLAY_LATITUDE;

	private boolean dashLatitude = DEFAULT_DASH_LATITUDE;

	private PathEffect dashEffect = DEFAULT_DASH_EFFECT;

	private int longitudeFontColor = DEFAULT_LONGITUDE_FONT_COLOR;

	private int longitudeFontSize = DEFAULT_LONGITUDE_FONT_SIZE;

	private int latitudeFontColor = DEFAULT_LATITUDE_FONT_COLOR;

	private int latitudeFontSize = DEFAULT_LATITUDE_FONT_SIZE;

	private int crossLinesColor = DEFAULT_CROSS_LINES_COLOR;

	private int crossLinesFontColor = DEFAULT_CROSS_LINES_FONT_COLOR;

	protected List<String> longitudeTitles;

	protected List<String> latitudeTitles;

	private int latitudeMaxTitleLength = DEFAULT_LATITUDE_MAX_TITLE_LENGTH;

	private boolean displayCrossXOnTouch = DEFAULT_DISPLAY_CROSS_X_ON_TOUCH;

	private boolean displayCrossYOnTouch = DEFAULT_DISPLAY_CROSS_Y_ON_TOUCH;

	protected PointF touchPoint;

	protected OnTouchGestureListener onTouchGestureListener = new OnTouchGestureListener();
	
	protected IGestureDetector touchGestureDetector = new TouchGestureDetector<ITouchable>(this);

	protected IQuadrant dataQuadrant = new Quadrant(this) {
		public float getQuadrantWidth() {
			return getWidth() - axisYTitleQuadrantWidth - 2 * borderWidth
					- axisWidth;
		}

		public float getQuadrantHeight() {
			return getHeight() - axisXTitleQuadrantHeight - 2 * borderWidth
					- axisWidth;
		}

		public float getQuadrantStartX() {
			if (axisYPosition == AXIS_Y_POSITION_LEFT) {
				return borderWidth + axisYTitleQuadrantWidth + axisWidth;
			} else {
				return borderWidth;
			}
		}
		
		public float getQuadrantStartY() {
			return borderWidth;
		}
	};
	
	protected IAxis axisX = new HorizontalAxis(this,AXIS_X_POSITION_TOP ,axisXTitleQuadrantHeight);
	
	protected IAxis axisY = new VerticalAxis(this, AXIS_Y_POSITION_LEFT, axisYTitleQuadrantWidth);
	
	protected ICrossLines crossLines = new CrossLines();
	
	public GridChart(Context context) {
		super(context);
	}

	public GridChart(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public GridChart(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		drawXAxis(canvas);
		drawYAxis(canvas);
		
		if (displayLongitude || displayLongitudeTitle) {
			drawLongitudeLine(canvas);
			drawLongitudeTitle(canvas);
		}
		if (displayLatitude || displayLatitudeTitle) {
			drawLatitudeLine(canvas);
			drawLatitudeTitle(canvas);
		}

		if (displayCrossXOnTouch || displayCrossYOnTouch) {
			drawHorizontalLine(canvas);
			drawVerticalLine(canvas);

		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!isValidTouchPoint(event.getX(),event.getY())) {
			return false;
		}
		return touchGestureDetector.onTouchEvent(event);
	}
	
	protected boolean isValidTouchPoint (float x , float y) {
		if (x < dataQuadrant.getQuadrantPaddingStartX()
				|| x > dataQuadrant.getQuadrantPaddingEndX()) {
			return false;
		}
		if (y < dataQuadrant.getQuadrantPaddingStartY()
				|| y > dataQuadrant.getQuadrantPaddingEndY()) {
			return false;
		}
		return true;
	}
	
	private void drawAlphaTextBox(PointF ptStart, PointF ptEnd, String content,
			int fontSize, Canvas canvas) {

		Paint mPaintBox = new Paint();
		mPaintBox.setColor(Color.WHITE);
		mPaintBox.setAlpha(80);
		mPaintBox.setStyle(Style.FILL);

		Paint mPaintBoxLine = new Paint();
		mPaintBoxLine.setColor(crossLinesColor);
		mPaintBoxLine.setAntiAlias(true);
		mPaintBoxLine.setTextSize(fontSize);

		// draw a rectangle
		canvas.drawRect(ptStart.x, ptStart.y, ptEnd.x, ptEnd.y, mPaintBox);

		// draw a rectangle' border
		canvas.drawLine(ptStart.x, ptStart.y, ptStart.x, ptEnd.y, mPaintBoxLine);
		canvas.drawLine(ptStart.x, ptEnd.y, ptEnd.x, ptEnd.y, mPaintBoxLine);
		canvas.drawLine(ptEnd.x, ptEnd.y, ptEnd.x, ptStart.y, mPaintBoxLine);
		canvas.drawLine(ptEnd.x, ptStart.y, ptStart.x, ptStart.y, mPaintBoxLine);

		mPaintBoxLine.setColor(crossLinesFontColor);
		// draw text
		canvas.drawText(content, ptStart.x, ptStart.y + fontSize, mPaintBoxLine);
	}

	public String getAxisXGraduate(Object value) {
		float valueLength = ((Float) value).floatValue()
				- dataQuadrant.getQuadrantPaddingStartX();
		return String.valueOf(valueLength / this.dataQuadrant.getQuadrantPaddingWidth());
	}

	public String getAxisYGraduate(Object value) {
		float valueLength = ((Float) value).floatValue()
				- dataQuadrant.getQuadrantPaddingStartY();
		return String
				.valueOf(1f - valueLength / this.dataQuadrant.getQuadrantPaddingHeight());
	}

	protected void drawVerticalLine(Canvas canvas) {

		if (!displayLongitudeTitle) {
			return;
		}
		if (!displayCrossXOnTouch) {
			return;
		}
		if (touchPoint == null) {
			return;
		}
		if (touchPoint.x <= 0) {
			return;
		}

		Paint mPaint = new Paint();
		mPaint.setColor(crossLinesColor);

		float lineVLength = dataQuadrant.getQuadrantHeight() + axisWidth;

		// TODO calculate points to draw
		PointF boxVS = new PointF(touchPoint.x - longitudeFontSize * 5f / 2f,
				borderWidth + lineVLength);
		PointF boxVE = new PointF(touchPoint.x + longitudeFontSize * 5f / 2f,
				borderWidth + lineVLength + axisXTitleQuadrantHeight);

		// draw text
		drawAlphaTextBox(boxVS, boxVE, getAxisXGraduate(touchPoint.x),
				longitudeFontSize, canvas);

		canvas.drawLine(touchPoint.x, borderWidth, touchPoint.x, lineVLength,
				mPaint);
	}

	protected void drawHorizontalLine(Canvas canvas) {

		if (!displayLatitudeTitle) {
			return;
		}
		if (!displayCrossYOnTouch) {
			return;
		}
		if (touchPoint == null) {
			return;
		}
		if (touchPoint.y <= 0) {
			return;
		}

		Paint mPaint = new Paint();
		mPaint.setColor(crossLinesColor);

		float lineHLength = dataQuadrant.getQuadrantWidth() + axisWidth;

		if (axisYPosition == AXIS_Y_POSITION_LEFT) {
			PointF boxHS = new PointF(borderWidth, touchPoint.y
					- latitudeFontSize / 2f - 2);
			PointF boxHE = new PointF(borderWidth + axisYTitleQuadrantWidth,
					touchPoint.y + latitudeFontSize / 2f + 2);

			// draw text
			drawAlphaTextBox(boxHS, boxHE, getAxisYGraduate(touchPoint.y),
					latitudeFontSize, canvas);

			canvas.drawLine(borderWidth + axisYTitleQuadrantWidth, touchPoint.y,
					borderWidth + axisYTitleQuadrantWidth + lineHLength,
					touchPoint.y, mPaint);
		} else {
			PointF boxHS = new PointF(super.getWidth() - borderWidth
					- axisYTitleQuadrantWidth, touchPoint.y - latitudeFontSize
					/ 2f - 2);
			PointF boxHE = new PointF(super.getWidth() - borderWidth,
					touchPoint.y + latitudeFontSize / 2f + 2);

			// draw text
			drawAlphaTextBox(boxHS, boxHE, getAxisYGraduate(touchPoint.y),
					latitudeFontSize, canvas);

			canvas.drawLine(borderWidth, touchPoint.y, borderWidth + lineHLength,
					touchPoint.y, mPaint);
		}

	}

	protected void drawXAxis(Canvas canvas) {

		float length = super.getWidth();
		float postY;
		if (axisXPosition == AXIS_X_POSITION_BOTTOM) {
			postY = super.getHeight() - axisXTitleQuadrantHeight - borderWidth
					- axisWidth / 2;
		} else {
			postY = super.getHeight() - borderWidth - axisWidth / 2;
		}

		Paint mPaint = new Paint();
		mPaint.setColor(axisXColor);
		mPaint.setStrokeWidth(axisWidth);

		canvas.drawLine(borderWidth, postY, length, postY, mPaint);

	}

	protected void drawYAxis(Canvas canvas) {

		float length = super.getHeight() - axisXTitleQuadrantHeight
				- borderWidth;
		float postX;
		if (axisYPosition == AXIS_Y_POSITION_LEFT) {
			postX = borderWidth + axisYTitleQuadrantWidth + axisWidth / 2;
		} else {
			postX = super.getWidth() - borderWidth - axisYTitleQuadrantWidth
					- axisWidth / 2;
		}

		Paint mPaint = new Paint();
		mPaint.setColor(axisXColor);
		mPaint.setStrokeWidth(axisWidth);

		canvas.drawLine(postX, borderWidth, postX, length, mPaint);
	}

	public float longitudePostOffset(){
		return this.dataQuadrant.getQuadrantPaddingWidth() / (longitudeTitles.size() - 1);
	}
	
	public float longitudeOffset(){
		return dataQuadrant.getQuadrantPaddingStartX();
	}
	
	protected void drawLongitudeLine(Canvas canvas) {
		if (null == longitudeTitles) {
			return;
		}
		if (!displayLongitude) {
			return;
		}
		int counts = longitudeTitles.size();
		float length = dataQuadrant.getQuadrantHeight();

		Paint mPaintLine = new Paint();
		mPaintLine.setStyle(Style.STROKE);
		mPaintLine.setColor(longitudeColor);
		mPaintLine.setStrokeWidth(longitudeWidth);
		mPaintLine.setAntiAlias(true);
		if (dashLongitude) {
			mPaintLine.setPathEffect(dashEffect);
		}
		if (counts > 1) {
			float postOffset = longitudePostOffset();
			float offset = longitudeOffset();

			for (int i = 0; i < counts; i++) {
				Path path = new Path();
				path.moveTo(offset + i * postOffset, borderWidth);
				path.lineTo(offset + i * postOffset, length);
				canvas.drawPath(path, mPaintLine);
			}
		}
	}

	protected void drawLongitudeTitle(Canvas canvas) {

		if (null == longitudeTitles) {
			return;
		}
		if (!displayLongitude) {
			return;
		}
		if (!displayLongitudeTitle) {
			return;
		}
		if (longitudeTitles.size() <= 1) {
			return;
		}

		Paint mPaintFont = new Paint();
		mPaintFont.setColor(longitudeFontColor);
		mPaintFont.setTextSize(longitudeFontSize);
		mPaintFont.setAntiAlias(true);

		float postOffset = longitudePostOffset();

		float offset = longitudeOffset();
		for (int i = 0; i < longitudeTitles.size(); i++) {
			if (0 == i) {
				canvas.drawText(longitudeTitles.get(i), offset + 2f,
						super.getHeight() - axisXTitleQuadrantHeight
								+ longitudeFontSize, mPaintFont);
			} else {
				canvas.drawText(longitudeTitles.get(i), offset + i * postOffset
						- (longitudeTitles.get(i).length()) * longitudeFontSize
						/ 2f, super.getHeight() - axisXTitleQuadrantHeight
						+ longitudeFontSize, mPaintFont);
			}
		}
	}

	protected void drawLatitudeLine(Canvas canvas) {

		if (null == latitudeTitles) {
			return;
		}
		if (!displayLatitude) {
			return;
		}
		if (!displayLatitudeTitle) {
			return;
		}
		if (latitudeTitles.size() <= 1) {
			return;
		}

		float length = dataQuadrant.getQuadrantWidth();
		
		Paint mPaintLine = new Paint();
		mPaintLine.setStyle(Style.STROKE);
		mPaintLine.setColor(latitudeColor);
		mPaintLine.setStrokeWidth(latitudeWidth);
		mPaintLine.setAntiAlias(true);
		if (dashLatitude) {
			mPaintLine.setPathEffect(dashEffect);
		}

		Paint mPaintFont = new Paint();
		mPaintFont.setColor(latitudeFontColor);
		mPaintFont.setTextSize(latitudeFontSize);
		mPaintFont.setAntiAlias(true);

		float postOffset = this.dataQuadrant.getQuadrantPaddingHeight()
				/ (latitudeTitles.size() - 1);

		float offset = super.getHeight() - borderWidth
				- axisXTitleQuadrantHeight - axisWidth
				- dataQuadrant.getPaddingBottom();

		if (axisYPosition == AXIS_Y_POSITION_LEFT) {
			float startFrom = borderWidth + axisYTitleQuadrantWidth + axisWidth;
			for (int i = 0; i < latitudeTitles.size(); i++) {
				Path path = new Path();
				path.moveTo(startFrom, offset - i * postOffset);
				path.lineTo(startFrom + length, offset - i * postOffset);
				canvas.drawPath(path, mPaintLine);
			}
		} else {
			float startFrom = borderWidth;
			for (int i = 0; i < latitudeTitles.size(); i++) {
				Path path = new Path();
				path.moveTo(startFrom, offset - i * postOffset);
				path.lineTo(startFrom + length, offset - i * postOffset);
				canvas.drawPath(path, mPaintLine);
			}
		}
	}

	protected void drawLatitudeTitle(Canvas canvas) {
		if (null == latitudeTitles) {
			return;
		}
		if (!displayLatitudeTitle) {
			return;
		}
		if (latitudeTitles.size() <= 1) {
			return;
		}
		Paint mPaintFont = new Paint();
		mPaintFont.setColor(latitudeFontColor);
		mPaintFont.setTextSize(latitudeFontSize);
		mPaintFont.setAntiAlias(true);

		float postOffset = this.dataQuadrant.getQuadrantPaddingHeight()
				/ (latitudeTitles.size() - 1);

		float offset = super.getHeight() - borderWidth
				- axisXTitleQuadrantHeight - axisWidth
				- dataQuadrant.getPaddingBottom();

		if (axisYPosition == AXIS_Y_POSITION_LEFT) {
			float startFrom = borderWidth;
			for (int i = 0; i < latitudeTitles.size(); i++) {
				if (0 == i) {
					canvas.drawText(latitudeTitles.get(i), startFrom,
							super.getHeight() - this.axisXTitleQuadrantHeight
									- borderWidth - axisWidth - 2f, mPaintFont);
				} else {
					canvas.drawText(latitudeTitles.get(i), startFrom, offset
							- i * postOffset + latitudeFontSize / 2f,
							mPaintFont);
				}
			}
		} else {
			float startFrom = super.getWidth() - borderWidth
					- axisYTitleQuadrantWidth;
			for (int i = 0; i < latitudeTitles.size(); i++) {

				if (0 == i) {
					canvas.drawText(latitudeTitles.get(i), startFrom,
							super.getHeight() - this.axisXTitleQuadrantHeight
									- borderWidth - axisWidth - 2f, mPaintFont);
				} else {
					canvas.drawText(latitudeTitles.get(i), startFrom, offset
							- i * postOffset + latitudeFontSize / 2f,
							mPaintFont);
				}
			}
		}

	}

	public int getAxisXColor() {
		return axisXColor;
	}

	public void setAxisXColor(int axisXColor) {
		this.axisXColor = axisXColor;
	}

	public int getAxisYColor() {
		return axisYColor;
	}

	public void setAxisYColor(int axisYColor) {
		this.axisYColor = axisYColor;
	}

	public float getAxisWidth() {
		return axisWidth;
	}

	public void setAxisWidth(float axisWidth) {
		this.axisWidth = axisWidth;
	}

	public int getLongitudeColor() {
		return longitudeColor;
	}

	public void setLongitudeColor(int longitudeColor) {
		this.longitudeColor = longitudeColor;
	}

	public int getLatitudeColor() {
		return latitudeColor;
	}

	public void setLatitudeColor(int latitudeColor) {
		this.latitudeColor = latitudeColor;
	}

	public float getAxisYTitleQuadrantWidth() {
		return axisYTitleQuadrantWidth;
	}

	public void setAxisYTitleQuadrantWidth(float axisYTitleQuadrantWidth) {
		this.axisYTitleQuadrantWidth = axisYTitleQuadrantWidth;
	}

	public float getAxisXTitleQuadrantHeight() {
		return axisXTitleQuadrantHeight;
	}

	public void setAxisXTitleQuadrantHeight(float axisXTitleQuadrantHeight) {
		this.axisXTitleQuadrantHeight = axisXTitleQuadrantHeight;
	}

	public boolean isDisplayLongitudeTitle() {
		return displayLongitudeTitle;
	}

	public void setDisplayLongitudeTitle(boolean displayLongitudeTitle) {
		this.displayLongitudeTitle = displayLongitudeTitle;
	}

	/**
	 * @return the displayAxisYTitle
	 */
	public boolean isDisplayLatitudeTitle() {
		return displayLatitudeTitle;
	}

	/**
	 * @param displayLatitudeTitle
	 *            the displayLatitudeTitle to set
	 */
	public void setDisplayLatitudeTitle(boolean displayLatitudeTitle) {
		this.displayLatitudeTitle = displayLatitudeTitle;
	}

	/**
	 * @return the latitudeNum
	 */
	public int getLatitudeNum() {
		return latitudeNum;
	}

	/**
	 * @param latitudeNum
	 *            the latitudeNum to set
	 */
	public void setLatitudeNum(int latitudeNum) {
		this.latitudeNum = latitudeNum;
	}

	/**
	 * @return the longitudeNum
	 */
	public int getLongitudeNum() {
		return longitudeNum;
	}

	/**
	 * @param longitudeNum
	 *            the longitudeNum to set
	 */
	public void setLongitudeNum(int longitudeNum) {
		this.longitudeNum = longitudeNum;
	}

	/**
	 * @return the displayLongitude
	 */
	public boolean isDisplayLongitude() {
		return displayLongitude;
	}

	/**
	 * @param displayLongitude
	 *            the displayLongitude to set
	 */
	public void setDisplayLongitude(boolean displayLongitude) {
		this.displayLongitude = displayLongitude;
	}

	/**
	 * @return the dashLongitude
	 */
	public boolean isDashLongitude() {
		return dashLongitude;
	}

	/**
	 * @param dashLongitude
	 *            the dashLongitude to set
	 */
	public void setDashLongitude(boolean dashLongitude) {
		this.dashLongitude = dashLongitude;
	}

	/**
	 * @return the displayLatitude
	 */
	public boolean isDisplayLatitude() {
		return displayLatitude;
	}

	/**
	 * @param displayLatitude
	 *            the displayLatitude to set
	 */
	public void setDisplayLatitude(boolean displayLatitude) {
		this.displayLatitude = displayLatitude;
	}

	/**
	 * @return the dashLatitude
	 */
	public boolean isDashLatitude() {
		return dashLatitude;
	}

	/**
	 * @param dashLatitude
	 *            the dashLatitude to set
	 */
	public void setDashLatitude(boolean dashLatitude) {
		this.dashLatitude = dashLatitude;
	}

	/**
	 * @return the dashEffect
	 */
	public PathEffect getDashEffect() {
		return dashEffect;
	}

	/**
	 * @param dashEffect
	 *            the dashEffect to set
	 */
	public void setDashEffect(PathEffect dashEffect) {
		this.dashEffect = dashEffect;
	}
	
	/**
	 * @return the longitudeWidth
	 */
	public float getLongitudeWidth() {
		return longitudeWidth;
	}

	/**
	 * @param longitudeWidth the longitudeWidth to set
	 */
	public void setLongitudeWidth(float longitudeWidth) {
		this.longitudeWidth = longitudeWidth;
	}

	/**
	 * @return the latitudeWidth
	 */
	public float getLatitudeWidth() {
		return latitudeWidth;
	}

	/**
	 * @param latitudeWidth the latitudeWidth to set
	 */
	public void setLatitudeWidth(float latitudeWidth) {
		this.latitudeWidth = latitudeWidth;
	}

	/**
	 * @return the longitudeFontColor
	 */
	public int getLongitudeFontColor() {
		return longitudeFontColor;
	}

	/**
	 * @param longitudeFontColor
	 *            the longitudeFontColor to set
	 */
	public void setLongitudeFontColor(int longitudeFontColor) {
		this.longitudeFontColor = longitudeFontColor;
	}

	/**
	 * @return the longitudeFontSize
	 */
	public int getLongitudeFontSize() {
		return longitudeFontSize;
	}

	/**
	 * @param longitudeFontSize
	 *            the longitudeFontSize to set
	 */
	public void setLongitudeFontSize(int longitudeFontSize) {
		this.longitudeFontSize = longitudeFontSize;
	}

	/**
	 * @return the latitudeFontColor
	 */
	public int getLatitudeFontColor() {
		return latitudeFontColor;
	}

	/**
	 * @param latitudeFontColor
	 *            the latitudeFontColor to set
	 */
	public void setLatitudeFontColor(int latitudeFontColor) {
		this.latitudeFontColor = latitudeFontColor;
	}

	/**
	 * @return the latitudeFontSize
	 */
	public int getLatitudeFontSize() {
		return latitudeFontSize;
	}

	/**
	 * @param latitudeFontSize
	 *            the latitudeFontSize to set
	 */
	public void setLatitudeFontSize(int latitudeFontSize) {
		this.latitudeFontSize = latitudeFontSize;
	}


	/**
	 * @return the longitudeTitles
	 */
	public List<String> getLongitudeTitles() {
		return longitudeTitles;
	}

	/**
	 * @param longitudeTitles
	 *            the longitudeTitles to set
	 */
	public void setLongitudeTitles(List<String> longitudeTitles) {
		this.longitudeTitles = longitudeTitles;
	}

	/**
	 * @return the latitudeTitles
	 */
	public List<String> getLatitudeTitles() {
		return latitudeTitles;
	}

	/**
	 * @param latitudeTitles
	 *            the latitudeTitles to set
	 */
	public void setLatitudeTitles(List<String> latitudeTitles) {
		this.latitudeTitles = latitudeTitles;
	}

	/**
	 * @return the latitudeMaxTitleLength
	 */
	public int getLatitudeMaxTitleLength() {
		return latitudeMaxTitleLength;
	}

	/**
	 * @param latitudeMaxTitleLength
	 *            the latitudeMaxTitleLength to set
	 */
	public void setLatitudeMaxTitleLength(int latitudeMaxTitleLength) {
		this.latitudeMaxTitleLength = latitudeMaxTitleLength;
	}


	/**
	 * @return the clickPostX
	 */
	@Deprecated
	public float getClickPostX() {
		if (touchPoint == null) {
			return 0f;
		}else{
			return touchPoint.x;
		}

	}

	/**
	 * @param clickPostX
	 *            the clickPostX to set
	 */
	@Deprecated
	public void setClickPostX(float clickPostX) {
		if (clickPostX >= 0) {
			this.touchPoint.x = clickPostX;
		}
	}

	/**
	 * @return the clickPostY
	 */
	@Deprecated
	public float getClickPostY() {
		if (touchPoint == null) {
			return 0f;
		} else {
			return touchPoint.y;
		}
	}

	/**
	 * @param touchPoint.y
	 *            the clickPostY to set
	 */
	@Deprecated
	public void setClickPostY(float clickPostY) {
		if (clickPostY >= 0) {
			this.touchPoint.y = clickPostY;
		}
	}

	/**
	 * @return the touchPoint
	 */
	public PointF getTouchPoint() {
		return touchPoint;
	}

	/**
	 * @param touchPoint
	 *            the touchPoint to set
	 */
	public void setTouchPoint(PointF touchPoint) {
		this.touchPoint = touchPoint;
	}

	/**
	 * @return the axisXPosition
	 */
	public int getAxisXPosition() {
		return axisXPosition;
	}

	/**
	 * @param axisXPosition
	 *            the axisXPosition to set
	 */
	public void setAxisXPosition(int axisXPosition) {
		this.axisXPosition = axisXPosition;
	}

	/**
	 * @return the axisYPosition
	 */
	public int getAxisYPosition() {
		return axisYPosition;
	}

	/**
	 * @param axisYPosition
	 *            the axisYPosition to set
	 */
	public void setAxisYPosition(int axisYPosition) {
		this.axisYPosition = axisYPosition;
	}

	/* (non-Javadoc)
	 *  
	 * @see com.sprinters.bullzx.event.ITouchable#touchDown() 
	 */
	public void touchDown(PointF pt) {
		this.touchPoint = pt;
		this.postInvalidate();
	}

	/* (non-Javadoc)
	 *  
	 * @see com.sprinters.bullzx.event.ITouchable#touchMoved() 
	 */
	public void touchMoved(PointF pt) {
		this.touchPoint = pt;
		this.postInvalidate();
	}

	/* (non-Javadoc)
	 *  
	 * @see com.sprinters.bullzx.event.ITouchable#touchUp() 
	 */
	public void touchUp(PointF pt) {
		this.touchPoint = pt;
		this.postInvalidate();
	}

	/* (non-Javadoc)
	 * 
	 * @param listener 
	 * @see com.sprinters.bullzx.event.ITouchable#setOnTouchGestureListener(com.sprinters.bullzx.event.OnTouchGestureListener) 
	 */
	public void setOnTouchGestureListener(OnTouchGestureListener listener) {
		this.onTouchGestureListener = listener;
	}

	/* (non-Javadoc)
	 * 
	 * @return 
	 * @see com.sprinters.bullzx.event.ITouchable#getOnTouchGestureListener() 
	 */
	public OnTouchGestureListener getOnTouchGestureListener() {
		return onTouchGestureListener;
	}

	/**
	 * @return the touchGestureDetector
	 */
	public IGestureDetector getTouchGestureDetector() {
		return touchGestureDetector;
	}

	/**
	 * @param touchGestureDetector the touchGestureDetector to set
	 */
	public void setTouchGestureDetector(IGestureDetector touchGestureDetector) {
		this.touchGestureDetector = touchGestureDetector;
	}

	/**
	 * @return the dataQuadrant
	 */
	public IQuadrant getDataQuadrant() {
		return dataQuadrant;
	}

	/**
	 * @param dataQuadrant the dataQuadrant to set
	 */
	public void setDataQuadrant(IQuadrant dataQuadrant) {
		this.dataQuadrant = dataQuadrant;
	}
	
	/**
	 * @return the paddingTop
	 */
	public float getDataQuadrantPaddingTop() {
		return dataQuadrant.getPaddingTop();
	}

	/**
	 * @param paddingTop
	 *            the paddingTop to set
	 */
	public void setDataQuadrantPaddingTop(float quadrantPaddingTop) {
		dataQuadrant.setPaddingTop(quadrantPaddingTop);
	}

	/**
	 * @return the paddingLeft
	 */
	public float getDataQuadrantPaddingLeft() {
		return dataQuadrant.getPaddingLeft();
	}

	/**
	 * @param paddingLeft
	 *            the paddingLeft to set
	 */
	public void setDataQuadrantPaddingLeft(float quadrantPaddingLeft) {
		dataQuadrant.setPaddingLeft(quadrantPaddingLeft);
	}

	/**
	 * @return the paddingBottom
	 */
	public float getDataQuadrantPaddingBottom() {
		return dataQuadrant.getPaddingBottom();
	}

	/**
	 * @param paddingBottom
	 *            the paddingBottom to set
	 */
	public void setDataQuadrantPaddingBottom(float quadrantPaddingBottom) {
		dataQuadrant.setPaddingBottom(quadrantPaddingBottom);
	}

	/**
	 * @return the paddingRight
	 */
	public float getDataQuadrantPaddingRight() {
		return dataQuadrant.getPaddingRight();
	}

	/**
	 * @param paddingRight
	 *            the paddingRight to set
	 */
	public void setDataQuadrantPaddingRight(float quadrantPaddingRight) {
		dataQuadrant.setPaddingRight(quadrantPaddingRight);
	}
	
	/**
	 * @return the crossLinesColor
	 */
	public int getCrossLinesColor() {
		return crossLinesColor;
	}

	/**
	 * @param crossLinesColor
	 *            the crossLinesColor to set
	 */
	public void setCrossLinesColor(int crossLinesColor) {
		this.crossLinesColor = crossLinesColor;
	}

	/**
	 * @return the crossLinesFontColor
	 */
	public int getCrossLinesFontColor() {
		return crossLinesFontColor;
	}

	/**
	 * @param crossLinesFontColor
	 *            the crossLinesFontColor to set
	 */
	public void setCrossLinesFontColor(int crossLinesFontColor) {
		this.crossLinesFontColor = crossLinesFontColor;
	}
	
	/**
	 * @return the displayCrossXOnTouch
	 */
	public boolean isDisplayCrossXOnTouch() {
		return displayCrossXOnTouch;
	}

	/**
	 * @param displayCrossXOnTouch
	 *            the displayCrossXOnTouch to set
	 */
	public void setDisplayCrossXOnTouch(boolean displayCrossXOnTouch) {
		this.displayCrossXOnTouch = displayCrossXOnTouch;
	}

	/**
	 * @return the displayCrossYOnTouch
	 */
	public boolean isDisplayCrossYOnTouch() {
		return displayCrossYOnTouch;
	}

	/**
	 * @param displayCrossYOnTouch
	 *            the displayCrossYOnTouch to set
	 */
	public void setDisplayCrossYOnTouch(boolean displayCrossYOnTouch) {
		this.displayCrossYOnTouch = displayCrossYOnTouch;
	}
}
