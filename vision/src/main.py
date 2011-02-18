import cv
import time
from imageProcFunctions import *
from navigation import *
from server import *
from worldstate import *
from setup import *

setup_system()

while (True):
	start = time.time()
	image = cv.QueryFrame(cam)
	cropRect = (75, 80, 554, 325)
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
	yellowCropRect = (yellowCenter[0] + 45, yellowCenter[1] + 45, 65, 65)
	cv.SetImageROI(yellowImage, yellowCropRect)
	cv.ShowImage("YellowBlack:", yellowImage)

	yellowBlack = findObject(yellowImage, "BLACK", mods)
	yellowBlack = (int(yellowBlack[0]) + yellowCropRect[0] - 75, int(yellowBlack[1]) + yellowCropRect[1] - 80)
	
	blueImage = cv.CloneImage(orig)	
	blueCropRect = (blueCenter[0] + 45, blueCenter[1] + 45, 65, 65)
	cv.SetImageROI(blueImage, blueCropRect)
	cv.ShowImage("BlueBlack:", blueImage)	
		
	blueWhite = findObject(blueImage, "BLACK", mods)
	blueWhite = (int(blueWhite[0]) + blueCropRect[0] - 75, int(blueWhite[1]) + blueCropRect[1] - 80)		
		
	cv.Circle(processed, ballCenter, 2, cv.RGB(0,0,0),-1)	
	cv.Circle(processed, blueCenter, 2, cv.RGB(0,0,0),-1)	
	cv.Circle(processed, yellowCenter, 2, cv.RGB(0,0,0),-1)
	
	cv.Line(processed, blueBlack, blueCenter, cv.RGB(255,0,0))
	cv.Line(processed, yellowBlack, yellowCenter, cv.RGB(0,255,0))
	
	print "Bearing of blue:", (int(calculateBearing(blueWhite,blueCenter) + 90) + 360) % 360
	print "Bearing of yellow:",  (int(calculateBearing(blueWhite,blueCenter) + 90) + 360) % 360
	
	cv.ShowImage("Original:", orig)
	cv.ShowImage("Processed:", processed)

	WorldState.lock.acquire()
	WorldState.ball["position"]["x"] = ballCenter[0]	
	WorldState.ball["position"]["y"] = ballCenter[1]
	WorldState.blue["position"]["x"] = blueCenter[0]
	WorldState.blue["position"]["y"] = blueCenter[1]
	WorldState.blue["rotation"] = (int(calculateBearing(blueBlack, blueCenter) + 90) + 360) % 360
	WorldState.lock.release()	

	cv.WaitKey(25)
	now = time.time()
	print 1/(now-start)


