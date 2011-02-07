import cv
from imageProcFunctions import *
from navigation import *


cv.NamedWindow("Original:", cv.CV_WINDOW_AUTOSIZE)
cv.NamedWindow("Processed:", cv.CV_WINDOW_AUTOSIZE)
cv.NamedWindow("BlueBlack:",cv.CV_WINDOW_AUTOSIZE)
cv.NamedWindow("YellowBlack:",cv.CV_WINDOW_AUTOSIZE)
cv.NamedWindow("Red:",cv.CV_WINDOW_AUTOSIZE)
cv.NamedWindow("Blue:",cv.CV_WINDOW_AUTOSIZE)
cv.NamedWindow("Yellow:",cv.CV_WINDOW_AUTOSIZE)
cv.NamedWindow("Black:",cv.CV_WINDOW_AUTOSIZE)
cv.NamedWindow("Box:", cv.CV_WINDOW_AUTOSIZE)

mods = [0.0, 0.4, 0.4, 0.05, 1.0, 1.0, 0.3, 0.3, 0.5, 0.8, 1.0, 1.0, 0.1, 0.36, 0.37, 0.2, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.45]

# Start ugly global value storage (fix this rubbish :( )

def onHLowerRChange(position):	mods[0] = position/255.0
def onSLowerRChange(position):	mods[1] = position/255.0
def onVLowerRChange(position):	mods[2] = position/255.0
def onHUpperRChange(position):	mods[3] = position/255.0
def onSUpperRChange(position):	mods[4] = position/255.0
def onVUpperRChange(position):	mods[5] = position/255.0

def onHLowerBChange(position):	mods[6] = position/255.0
def onSLowerBChange(position):	mods[7] = position/255.0
def onVLowerBChange(position):	mods[8] = position/255.0
def onHUpperBChange(position):	mods[9] = position/255.0
def onSUpperBChange(position):	mods[10] = position/255.0
def onVUpperBChange(position):	mods[11] = position/255.0

def onHLowerYChange(position):	mods[12] = position/255.0
def onSLowerYChange(position):	mods[13] = position/255.0
def onVLowerYChange(position):	mods[14] = position/255.0
def onHUpperYChange(position):  mods[15] = position/255.0
def onSUpperYChange(position):	mods[16] = position/255.0
def onVUpperYChange(position):	mods[17] = position/255.0

def onHLowerBkChange(position):	mods[18] = position/255.0
def onSLowerBkChange(position):	mods[19] = position/255.0
def onVLowerBkChange(position):	mods[20] = position/255.0
def onHUpperBkChange(position):	mods[21] = position/255.0
def onSUpperBkChange(position): mods[22] = position/255.0
def onVUpperBkChange(position):	mods[23] = position/255.0

cv.CreateTrackbar("H Lower:", "Red:", 0, 255, onHLowerRChange)
cv.CreateTrackbar("S Lower:", "Red:", 125, 255, onSLowerRChange)
cv.CreateTrackbar("V Lower:", "Red:", 125, 255, onVLowerRChange)
cv.CreateTrackbar("H Upper:", "Red:", 12, 255, onHUpperRChange)
cv.CreateTrackbar("S Upper:", "Red:", 255, 255, onSUpperRChange)
cv.CreateTrackbar("V Upper:", "Red:", 255, 255, onVUpperRChange)

cv.CreateTrackbar("H Lower:", "Blue:", 80, 255, onHLowerBChange)
cv.CreateTrackbar("S Lower:", "Blue:", 70, 255, onSLowerBChange)
cv.CreateTrackbar("V Lower:", "Blue:", 70, 255, onVLowerBChange)
cv.CreateTrackbar("H Upper:", "Blue:", 220, 255, onHUpperBChange)
cv.CreateTrackbar("S Upper:", "Blue:", 255, 255, onSUpperBChange)
cv.CreateTrackbar("V Upper:", "Blue:", 255, 255, onVUpperBChange)

cv.CreateTrackbar("H Lower:", "Yellow:", 22, 255, onHLowerYChange)
cv.CreateTrackbar("S Lower:", "Yellow:", 115, 255, onSLowerYChange)
cv.CreateTrackbar("V Lower:", "Yellow:", 125, 255, onVLowerYChange)
cv.CreateTrackbar("H Upper:", "Yellow:", 50, 255, onHUpperYChange)
cv.CreateTrackbar("S Upper:", "Yellow:", 255, 255, onSUpperYChange)
cv.CreateTrackbar("V Upper:", "Yellow:", 255, 255, onVUpperYChange)

cv.CreateTrackbar("H Lower:", "Processed:", 0, 255, onHLowerBkChange)
cv.CreateTrackbar("S Lower:", "Processed:", 0, 255, onSLowerBkChange)
cv.CreateTrackbar("V Lower:", "Processed:", 0, 255, onVLowerBkChange)
cv.CreateTrackbar("H Upper:", "Processed:", 255, 255, onHUpperBkChange)
cv.CreateTrackbar("S Upper:", "Processed:", 255, 255, onSUpperBkChange)
cv.CreateTrackbar("V Upper:", "Processed:", 110, 255, onVUpperBkChange)


cam = cv.CaptureFromCAM(0)

def findObject(img, colour, mods):
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
		print mods[0]
		redLower = cv.Scalar(mods[0]*256, mods[1]*256, mods[2]*256)
		redUpper = cv.Scalar(mods[3]*256, mods[4]*256, mods[5]*256)
		cv.InRangeS(hsv, redLower, redUpper, mask)		
		cv.ShowImage("Red:",mask)
	elif (colour == "BLUE"):
		blueLower = cv.Scalar(mods[6]*256, mods[7]*256, mods[8]*256)
		blueUpper = cv.Scalar(mods[9]*256, mods[10]*256, mods[11]*256)
		cv.InRangeS(hsv, blueLower, blueUpper, mask)
		cv.ShowImage("Blue:",mask)
	elif (colour == "YELLOW"):
		yellowLower = cv.Scalar(mods[12]*256, mods[13]*256, mods[14]*256)
		yellowUpper = cv.Scalar(mods[15]*256, mods[16]*256, mods[17]*256)
		cv.InRangeS(hsv, yellowLower, yellowUpper, mask)
		cv.ShowImage("Yellow:",mask)
		#print [yellowLowerH, yellowUpperH, yellowLowerS, yellowUpperS, yellowLowerV, yellowUpperV]
	elif (colour == "BLACK"):
		blackLower = cv.Scalar(mods[18]*256, mods[19]*256, mods[20]*256)
		blackUpper = cv.Scalar(mods[21]*256, mods[22]*256, mods[23]*256)
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
		
		#drawRect(minRect, img)
		#tempImage = cv.CreateImage(size, 8, 1)
		#cv.CvtColor(img, tempImage, cv.CV_RGB2GRAY )
		#findCircles(tempImage)
		
		if M00 == 0:
			M00 = 0.01
		
		return(round(M10/M00),round(M01/M00))
		
	return (0, 0)

while (True):
	image = cv.QueryFrame(cam)
	cropRect = (75, 80, 554, 340)
	cv.SetImageROI(image, cropRect)
	orig = cv.CloneImage(image)
	processed = cv.CloneImage(orig)

	ballCenter = findObject(image,"RED", mods)
	ballCenter = (int(ballCenter[0]), int(ballCenter[1]))
	blueCenter = findObject(image,"BLUE", mods)
	blueCenter = (int(blueCenter[0]), int(blueCenter[1]))
	yellowCenter = findObject(image,"YELLOW", mods)
	yellowCenter = (int(yellowCenter[0]), int(yellowCenter[1]))
	
	yellowImage = cv.CloneImage(orig)
	yellowCropRect = (yellowCenter[0] + 50, yellowCenter[1] + 55, 50, 50)
	cv.SetImageROI(yellowImage, yellowCropRect)
	cv.ShowImage("YellowBlack:", yellowImage)
	yellowBlack = findObject(yellowImage, "BLACK", mods)
	yellowBlack = (int(yellowBlack[0]) + yellowCropRect[0] - 75, int(yellowBlack[1]) + yellowCropRect[1] - 80)
	
	blueImage = cv.CloneImage(orig)	
	blueCropRect = (blueCenter[0] + 50, blueCenter[1] + 55, 50, 50)
	cv.SetImageROI(blueImage, blueCropRect)
	cv.ShowImage("BlueBlack:", blueImage)	
		
	blueBlack = findObject(blueImage, "BLACK", mods)
	blueBlack = (int(blueBlack[0]) + blueCropRect[0] - 75, int(blueBlack[1]) + blueCropRect[1] - 80)		
		
	cv.Circle(processed, ballCenter, 2, cv.RGB(0,0,0),-1)	
	cv.Circle(processed, blueCenter, 2, cv.RGB(0,0,0),-1)	
	cv.Circle(processed, yellowCenter, 2, cv.RGB(0,0,0),-1)
	
	cv.Line(processed, blueBlack, blueCenter, cv.RGB(255,0,0))
	cv.Line(processed, yellowBlack, yellowCenter, cv.RGB(0,255,0))
	
	print "Bearing of blue:", calculateBearing(blueBlack, blueCenter)
	print "Bearing of yellow:", calculateBearing(yellowBlack, yellowCenter)
	
#	print mods

#	cv.CreateTrackbar("H","Processed:",0,1,on_change)
		
	cv.ShowImage("Original:", orig)
	cv.ShowImage("Processed:", processed)

	cv.WaitKey(25)


