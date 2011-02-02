import cv
from imageProcFunctions import *
from navigation import *


cv.NamedWindow("Original:", cv.CV_WINDOW_AUTOSIZE)
cv.NamedWindow("Processed:", cv.CV_WINDOW_AUTOSIZE)
cam = cv.CaptureFromCAM(0)

while (True):
	image = cv.QueryFrame(cam)
	cropRect = (75, 80, 554, 340)
	cv.SetImageROI(image, cropRect)
	orig = cv.CloneImage(image)
	processed = cv.CloneImage(orig)

	ballCenter = findObject(image,"RED")
	ballCenter = (int(ballCenter[0]), int(ballCenter[1]))
	blueCenter = findObject(image,"BLUE")
	blueCenter = (int(blueCenter[0]), int(blueCenter[1]))
	yellowCenter = findObject(image,"YELLOW")
	yellowCenter = (int(yellowCenter[0]), int(yellowCenter[1]))
	
	blueImage = cv.CloneImage(orig)	
	blueCropRect = (blueCenter[0] + 50, blueCenter[1] + 55, 50, 50)
	cv.SetImageROI(blueImage, blueCropRect)
	cv.NamedWindow("BlueBlack:",cv.CV_WINDOW_AUTOSIZE)
	cv.ShowImage("BlueBlack:", blueImage)	
	blueBlack = findObject(blueImage, "BLACK")
	blueBlack = (int(blueBlack[0]) + blueCropRect[0], int(blueBlack[1]) + blueCropRect[1])	
	
	yellowImage = cv.CloneImage(orig)
	yellowCropRect = (yellowCenter[0] + 50, yellowCenter[1] + 55, 50, 50)
	cv.SetImageROI(yellowImage, yellowCropRect)
	cv.NamedWindow("YellowBlack:",cv.CV_WINDOW_AUTOSIZE)
	cv.ShowImage("YellowBlack:", yellowImage)
	yellowBlack = findObject(yellowImage, "BLACK")
	yellowBlack = (int(yellowBlack[0]) + yellowCropRect[0], int(yellowBlack[1]) + yellowCropRect[1])
		
	cv.Circle(processed, ballCenter, 2, cv.RGB(0,0,0),-1)	
	cv.Circle(processed, blueCenter, 2, cv.RGB(0,0,0),-1)	
	cv.Circle(processed, yellowCenter, 2, cv.RGB(0,0,0),-1)
	
	cv.Line(processed, (blueBlack[0] - 75, blueBlack[1] - 80), blueCenter, cv.RGB(255,0,0))
	cv.Line(processed, (yellowBlack[0] - 75, yellowBlack[1] - 80) , yellowCenter, cv.RGB(0,255,0))
	
	cv.ShowImage("Original:", orig)
	cv.ShowImage("Processed:", processed)

	cv.WaitKey(25)
