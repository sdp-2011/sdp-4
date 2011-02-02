import cv
from imageProcFunctions import *
from navigation import *


cv.NamedWindow("Original:", cv.CV_WINDOW_AUTOSIZE)
cv.NamedWindow("Processed:", cv.CV_WINDOW_AUTOSIZE)
cam = cv.CaptureFromCAM(0)

while (True):
	image = cv.QueryFrame(cam)
	cropRect = (75, 80, 554, 340)
	image = cv.SetImageROI(image, cropRect)
	orig = cv.CloneImage(image)
	processed = cv.CloneImage(orig)

	ballCenter = findObject(image, "RED")
	blueCenter = findObject(image, "BLUE")
	yellowCenter = findObject(image, "YELLOW")
		
	cv.Circle(processed, (int(ballCenter[0]), int(ballCenter[1])), 2, cv.RGB(0,0,0),-1)	
	cv.Circle(processed, (int(blueCenter[0]), int(blueCenter[1])), 2, cv.RGB(0,0,0),-1)	
	cv.Circle(processed, (int(yellowCenter[0]), int(yellowCenter[1])), 2, cv.RGB(0,0,0),-1)
	
	cv.ShowImage("Original:", orig)
	cv.ShowImage("Processed:", processed)

	cv.WaitKey(25)
