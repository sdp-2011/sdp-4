import cv
import math

# End value storage




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
	
	storage2 = cv.CreateMat(1, 300*300, cv.CV_32FC3)
	
	cv.Canny(contourImage, edgeImage, 1, 3, 5)
	cv.Smooth(img, img, cv.CV_GAUSSIAN, 9, 9)
	circles = cv.HoughCircles(edgeImage, storage2, cv.CV_HOUGH_GRADIENT, 2, 1)
			
	if circles != None:
		print len(circles.hrange())
		for circle in circles.hrange():
			cv.Circle(img, (circle[0], circle[1]), circle[2], cv.CV_RGB(255,0,0), 1, 8, 0)
			
	cv.NamedWindow("Circles:", cv.CV_WINDOW_AUTOSIZE)
	cv.ShowImage("Circles:", edgeImage)
	
