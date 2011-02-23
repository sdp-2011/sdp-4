import cv
import time
from imageProcFunctions import *
from navigation import *
from server import *
from worldstate import *
from setup import *

def draw_on_image(image, center_points, other_points):
	cv.Circle(image, center_points[0], 2, cv.RGB(0,0,0),-1)	
	cv.Circle(image, center_points[1], 2, cv.RGB(0,0,0),-1)	
	cv.Circle(image, center_points[2], 2, cv.RGB(0,0,0),-1)
	
	cv.Line(image, other_points[0], center_points[1], cv.RGB(255,0,0))
	#cv.Line(image, yellowBlack, center_points[2], cv.RGB(0,255,0))
		
	cv.ShowImage("Original:", orig)
	cv.ShowImage("Processed:", image)
	
def update_worldstate(center_points, other_points):
	
	WorldState.lock.acquire()
	WorldState.ball["position"]["x"] = center_points[0][0]
	WorldState.ball["position"]["y"] = center_points[0][1]
	WorldState.blue["position"]["x"] = center_points[1][0]
	WorldState.blue["position"]["y"] = center_points[1][1]
	WorldState.blue["rotation"] = (int(calculateBearing(other_points[0], center_points[1]))-180) % 360
	WorldState.yellow["position"]["x"] = center_points[2][0]
	WorldState.yellow["position"]["y"] = center_points[2][1]
	WorldState.yellow["rotation"] = int(calculateBearing(other_points[1], center_points[2]))	
	WorldState.lock.release()

setup_system()
cam = cv.CaptureFromCAM(0)

def whitePoint(mask):
	return cv.Avg(mask)



		if (colour == "BLACK"):
			
			x = 0
			y = 0
			
			#for i in mask[0]:
			#	x = x +i

#			for j in mask[1]:
##
#				y = y + j

								
			print x
			M00 = 0
			M10 = 0
			M01 = 0

	#		contourLow = cv.ApproxPoly(contours, storage, cv.CV_POLY_APPROX_DP)
			moments = cv.Moments(mask, 1)
			M00 = M00 + cv.GetSpatialMoment(moments,0,0)
			M10 = M10 + cv.GetSpatialMoment(moments,1,0)
			M01 = M01 + cv.GetSpatialMoment(moments,0,1)

			if M00 == 0:
				M00 = 0.01

			return(round(M10/M00),round(M01/M00))

		else:
while (True):
	start = time.time()
	image = cv.QueryFrame(cam)
	cropRect = (75, 80, 554, 325)
	cv.SetImageROI(image, cropRect)
	orig = cv.CloneImage(image)
	processed = cv.CloneImage(orig)

	ballCenter = findObject(image,"RED")
	ballCenter = (int(ballCenter[0]), int(ballCenter[1]))
	blueCenter = findObject(image,"BLUE")
	blueCenter = (int(blueCenter[0]), int(blueCenter[1]))
	yellowCenter = findObject(image,"YELLOW")
	yellowCenter = (int(yellowCenter[0]), int(yellowCenter[1]))
	
	yellowImage = cv.CloneImage(orig)
	yellowCropRect = (yellowCenter[0] + 45, yellowCenter[1] + 45, 65, 65)
	cv.SetImageROI(yellowImage, yellowCropRect)
	cv.ShowImage("YellowBlack:", yellowImage)

	yellowBlack = findObject(yellowImage, "BLACK")
	yellowBlack = (int(yellowBlack[0]) + yellowCropRect[0] - 75, int(yellowBlack[1]) + yellowCropRect[1] - 80)
	
	blueImage = cv.CloneImage(orig)	
	blueCropRect = (blueCenter[0] + 45, blueCenter[1] + 45, 65, 65)
	cv.SetImageROI(blueImage, blueCropRect)
	cv.ShowImage("BlueBlack:", blueImage)	
		
	blueWhite = findObject(blueImage, "BLACK")
	blueWhite = (int(blueWhite[0]) + blueCropRect[0] - 75, int(blueWhite[1]) + blueCropRect[1] - 80)	
	
	center_points = (ballCenter, blueCenter, yellowCenter)
	other_points = (blueWhite, yellowBlack)
	draw_on_image(processed, center_points, other_points)
	
	print "Bearing of blue:", (int(calculateBearing(other_points[0], center_points[1]))-180) % 360
	print "Sod blue, red is at:", ballCenter[0], ballCenter[1]
#	print "Bearing of yellow:",  (int(calculateBearing(blueWhite,blueCenter) + 90) + 360) % 360
	
	update_worldstate(center_points, other_points)

	cv.WaitKey(25)
	now = time.time()
	print 1/(now-start)
	
