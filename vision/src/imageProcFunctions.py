import cv
import math

# Start ugly global value storage (fix this rubbish :( )
redLowerH = 0.00 * 256
redLowerS = 0.50 * 256
redLowerV = 0.50 * 256

redUpperH = 0.05 * 256
redUpperS = 1.00 * 256
redUpperV = 1.00 * 256

blueLowerH = 0.30 * 256
blueLowerS = 0.25 * 256
blueLowerV = 0.25 * 256

blueUpperH = 0.80 * 256
blueUpperS = 1.00 * 256
blueUpperV = 1.00 * 256

yellowLowerH = 0.09 * 256
yellowLowerS = 0.45 * 256
yellowLowerV = 0.50 * 256

yellowUpperH = 0.20 * 256
yellowUpperS = 1.00 * 256
yellowUpperV = 1.00 * 256

blackLowerH = 0.00 * 256
blackLowerS = 0.00 * 256
blackLowerV = 0.00 * 256

blackUpperH = 1.00 * 256
blackUpperS = 1.00 * 256
blackUpperV = 0.45 * 256
# End value storage

def onHLowerRChange(position):	redLowerH = (float(position)/100) * 256
def onSLowerRChange(position):	redLowerS = (float(position)/100) * 256
def onVLowerRChange(position):	redLowerV = (float(position)/100) * 256
def onHUpperRChange(position):	redUpperH = (float(position)/100) * 256
def onSUpperRChange(position):	redUpperS = (float(position)/100) * 256
def onVUpperRChange(position):	redUpperV = (float(position)/100) * 256

def onHLowerBChange(position):	blueLowerH = (float(position)/100) * 256
def onSLowerBChange(position):	blueLowerS = (float(position)/100) * 256
def onVLowerBChange(position):	blueLowerV = (float(position)/100) * 256
def onHUpperBChange(position):	blueUpperH = (float(position)/100) * 256
def onSUpperBChange(position):	blueUpperS = (float(position)/100) * 256
def onVUpperBChange(position):	blueUpperV = (float(position)/100) * 256

def onHLowerYChange(position):	yellowLowerH = (float(position)/100) * 256
def onSLowerYChange(position):	yellowLowerS = (float(position)/100) * 256
def onVLowerYChange(position):	yellowLowerV = (float(position)/100) * 256
def onHUpperYChange(position):	yellowUpperH = (float(position)/100) * 256
def onSUpperYChange(position):	yellowUpperS = (float(position)/100) * 256
def onVUpperYChange(position):	yellowUpperV = (float(position)/100) * 256

def onHLowerBkChange(position):	blackLowerH = (float(position)/100) * 256
def onSLowerBkChange(position):	blackLowerS = (float(position)/100) * 256
def onVLowerBkChange(position):	blackLowerV = (float(position)/100) * 256
def onHUpperBkChange(position):	blackUpperH = (float(position)/100) * 256
def onSUpperBkChange(position):	blackUpperS = (float(position)/100) * 256
def onVUpperBkChange(position):	blackUpperV = (float(position)/100) * 256

cv.CreateTrackbar("H Lower:", "Red:", 0, 100, onHLowerRChange)
cv.CreateTrackbar("S Lower:", "Red:", 0, 100, onSLowerRChange)
cv.CreateTrackbar("V Lower:", "Red:", 0, 100, onVLowerRChange)
cv.CreateTrackbar("H Upper:", "Red:", 0, 100, onHUpperRChange)
cv.CreateTrackbar("S Upper:", "Red:", 0, 100, onSUpperRChange)
cv.CreateTrackbar("V Upper:", "Red:", 0, 100, onVUpperRChange)

cv.CreateTrackbar("H Lower:", "Blue:", 0, 100, onHLowerBChange)
cv.CreateTrackbar("S Lower:", "Blue:", 0, 100, onSLowerBChange)
cv.CreateTrackbar("V Lower:", "Blue:", 0, 100, onVLowerBChange)
cv.CreateTrackbar("H Upper:", "Blue:", 0, 100, onHUpperBChange)
cv.CreateTrackbar("S Upper:", "Blue:", 0, 100, onSUpperBChange)
cv.CreateTrackbar("V Upper:", "Blue:", 0, 100, onVUpperBChange)

cv.CreateTrackbar("H Lower:", "Yellow:", 0, 100, onHLowerYChange)
cv.CreateTrackbar("S Lower:", "Yellow:", 0, 100, onSLowerYChange)
cv.CreateTrackbar("V Lower:", "Yellow:", 0, 100, onVLowerYChange)
cv.CreateTrackbar("H Upper:", "Yellow:", 0, 100, onHUpperYChange)
cv.CreateTrackbar("S Upper:", "Yellow:", 0, 100, onSUpperYChange)
cv.CreateTrackbar("V Upper:", "Yellow:", 0, 100, onVUpperYChange)

cv.CreateTrackbar("H Lower:", "Black:", 0, 100, onHLowerBkChange)
cv.CreateTrackbar("S Lower:", "Black:", 0, 100, onSLowerBkChange)
cv.CreateTrackbar("V Lower:", "Black:", 0, 100, onVLowerBkChange)
cv.CreateTrackbar("H Upper:", "Black:", 0, 100, onHUpperBkChange)
cv.CreateTrackbar("S Upper:", "Black:", 0, 100, onSUpperBkChange)
cv.CreateTrackbar("V Upper:", "Black:", 0, 100, onVUpperBkChange)

def drawRect(box2d, image):
	boxPoints = cv.BoxPoints(box2d)
	cv.Line(image,
		(int(boxPoints[0][0]), int(boxPoints[0][1])),
		(int(boxPoints[1][0]), int(boxPoints[1][1])),
		cv.CV_RGB(255,0,0))
	cv.Line(image,
		(int(boxPoints[1][0]), int(boxPoints[1][1])),
		(int(boxPoints[2][0]), int(boxPoints[2][1])),
		cv.CV_RGB(255,0,0))
	cv.Line(image,
		(int(boxPoints[2][0]), int(boxPoints[2][1])),
		(int(boxPoints[3][0]), int(boxPoints[3][1])),
		cv.CV_RGB(255,0,0))
	cv.Line(image,
		(int(boxPoints[3][0]), int(boxPoints[3][1])),
		(int(boxPoints[0][0]), int(boxPoints[0][1])),
		cv.CV_RGB(255,0,0))
	cv.ShowImage("Box:", image)

def findCircles(img):
	size = cv.GetSize(img)
	contourImage = cv.CreateImage(cv.GetSize(img), 8, 1)
	edgeImage = cv.CreateImage(cv.GetSize(img), 8, 1)
	cv.Copy(img, contourImage, None)
	
	storage2 = cv.CreateMat(1, 2, cv.CV_32FC3)
	
	#cv.Canny(contourImage, edgeImage, 1, 3, 5)
	
	circles = cv.HoughCircles(contourImage, storage2, cv.CV_HOUGH_GRADIENT, 2, 1, 1, 1000)
	
	if circles != None:
		print len(circles.hrange())
		for circle in circles.hrange():
			cv.Circle(img, (circle[0], circle[1]), circle[2], cv.CV_RGB(255,0,0), 1, 8, 0)
			
	tempImage = cv.CreateImage((size[0] * 4 ,size[1] * 4), 8, 1)
	cv.Resize(img, tempImage)
		
	cv.NamedWindow("Circles:", cv.CV_WINDOW_AUTOSIZE)
	cv.ShowImage("Circles:", tempImage)
	
def findObject(img, colour):
	'''
	Finds the objects in an image with given colour.

	Arguments:
	img	    -- the image to be processed
	colour  -- the colour to look for (red, blue or yellow)

	Returns:
	Point representing object's centre of mass

	'''

    # Convert to hsv
	size = cv.GetSize(img)
	tempImage = cv.CreateImage((size[0] / 2, size[1] / 2), 8, 3)
	
    # Reduce noise by down- and up-scaling input image
	cv.PyrDown(img, tempImage, 7)
	cv.PyrUp(tempImage, img, 7)
	hsv = cv.CreateImage(size, cv.IPL_DEPTH_8U, 3)
	cv.CvtColor(img, hsv, cv.CV_BGR2HSV)

    # Convert to binary image based on colour
	mask = cv.CreateMat(size[1], size[0], cv.CV_8UC1)
	maskSize = cv.GetSize(mask)
	if (colour == "RED"):
		redLower = cv.Scalar(redLowerH, redLowerS, redLowerV)
		redUpper = cv.Scalar(redUpperH, redUpperS, redUpperV)
		cv.InRangeS(hsv, redLower, redUpper, mask)		
		cv.ShowImage("Red:",mask)
	elif (colour == "BLUE"):
		blueLower = cv.Scalar(blueLowerH, blueLowerS, blueLowerV)
		blueUpper = cv.Scalar(blueUpperH, blueUpperS, blueUpperV)
		cv.InRangeS(hsv, blueLower, blueUpper, mask)
		cv.ShowImage("Blue:",mask)
	elif (colour == "YELLOW"):
		yellowLower = cv.Scalar(yellowLowerH, yellowLowerS, yellowLowerV)
		yellowUpper = cv.Scalar(yellowUpperH, yellowUpperS, yellowUpperV)
		cv.InRangeS(hsv, yellowLower, yellowUpper, mask)
		cv.ShowImage("Yellow:",mask)
	elif (colour == "BLACK"):
		blackLower = cv.Scalar(blackLowerH, blackLowerS, blackLowerV)
		blackUpper = cv.Scalar(blackUpperH, blackUpperS, blackUpperV)
		cv.InRangeS(hsv, blackLower, blackUpper, mask)
		cv.ShowImage("Black:",mask)

    # Count white pixels to make sure program doesn't crash if it finds nothing
	if (cv.CountNonZero(mask) < 3):
		return (0, 0)

    # Clean up the image to reduce anymore noise in the binary image
	cv.Smooth(mask, mask, cv.CV_GAUSSIAN, 9, 9, 0, 0)
	convKernel = cv.CreateStructuringElementEx(1, 1, 0, 0, cv.CV_SHAPE_ELLIPSE)
	cv.Erode(mask, mask, convKernel, 1)
	cv.Dilate(mask, mask, convKernel, 1)
	cv.Dilate(mask, mask, convKernel, 1)
	cv.Erode(mask, mask, convKernel, 1)

	contourImage = cv.CreateImage(size, 8, 1)
	cv.Copy(mask, contourImage, None)

	storage = cv.CreateMemStorage()
	contours = cv.FindContours(contourImage, storage, cv.CV_RETR_EXTERNAL, cv.CV_CHAIN_APPROX_SIMPLE)

	if len(contours) > 1:
		contourLow = cv.ApproxPoly(contours, storage, cv.CV_POLY_APPROX_DP)

		moments = cv.Moments(contourLow, 1)
		M00 = cv.GetSpatialMoment(moments,0,0)
		M10 = cv.GetSpatialMoment(moments,1,0)
		M01 = cv.GetSpatialMoment(moments,0,1)
		
		minRect = cv.MinAreaRect2(contourLow, storage)
		
		drawRect(minRect, img)
		#findCircles(mask)
		
		if M00 == 0:
			M00 = 0.01
		
		return(round(M10/M00),round(M01/M00))
		
	return (0, 0)
