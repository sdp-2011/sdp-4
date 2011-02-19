import cv
from imageProcFunctions import *
from navigation import *
from server import *
from worldstate import *
from setup import *

def setup_system():
	create_windows()
	add_trackbars()	
	Server().start()
	
def create_windows():
	cv.NamedWindow("Original:", cv.CV_WINDOW_AUTOSIZE)
	cv.NamedWindow("Processed:", cv.CV_WINDOW_AUTOSIZE)
	cv.NamedWindow("BlueBlack:",cv.CV_WINDOW_AUTOSIZE)
	cv.NamedWindow("YellowBlack:",cv.CV_WINDOW_AUTOSIZE)
	cv.NamedWindow("Red:",cv.CV_WINDOW_AUTOSIZE)
	cv.NamedWindow("Blue:",cv.CV_WINDOW_AUTOSIZE)
	cv.NamedWindow("Yellow:",cv.CV_WINDOW_AUTOSIZE)
	cv.NamedWindow("White:",cv.CV_WINDOW_AUTOSIZE)
	
def add_trackbars():
	# Red trackbars
	cv.CreateTrackbar("H Lower:", "Red:", 0, 255, onHLowerRChange)
	cv.CreateTrackbar("S Lower:", "Red:", 125, 255, onSLowerRChange)
	cv.CreateTrackbar("V Lower:", "Red:", 125, 255, onVLowerRChange)
	cv.CreateTrackbar("H Upper:", "Red:", 12, 255, onHUpperRChange)
	cv.CreateTrackbar("S Upper:", "Red:", 255, 255, onSUpperRChange)
	cv.CreateTrackbar("V Upper:", "Red:", 255, 255, onVUpperRChange)
	
	# Blue trackbars
	cv.CreateTrackbar("H Lower:", "Blue:", 80, 255, onHLowerBChange)
	cv.CreateTrackbar("S Lower:", "Blue:", 70, 255, onSLowerBChange)
	cv.CreateTrackbar("V Lower:", "Blue:", 70, 255, onVLowerBChange)
	cv.CreateTrackbar("H Upper:", "Blue:", 220, 255, onHUpperBChange)
	cv.CreateTrackbar("S Upper:", "Blue:", 255, 255, onSUpperBChange)
	cv.CreateTrackbar("V Upper:", "Blue:", 255, 255, onVUpperBChange)

	# Yellow trackbars
	cv.CreateTrackbar("H Lower:", "Yellow:", 22, 255, onHLowerYChange)
	cv.CreateTrackbar("S Lower:", "Yellow:", 115, 255, onSLowerYChange)
	cv.CreateTrackbar("V Lower:", "Yellow:", 125, 255, onVLowerYChange)
	cv.CreateTrackbar("H Upper:", "Yellow:", 50, 255, onHUpperYChange)
	cv.CreateTrackbar("S Upper:", "Yellow:", 255, 255, onSUpperYChange)
	cv.CreateTrackbar("V Upper:", "Yellow:", 255, 255, onVUpperYChange)

	# White trackbars
	cv.CreateTrackbar("H Lower:", "Processed:", 0, 255, onHLowerBkChange)
	cv.CreateTrackbar("S Lower:", "Processed:", 0, 255, onSLowerBkChange)
	cv.CreateTrackbar("V Lower:", "Processed:", 237, 255, onVLowerBkChange)
	cv.CreateTrackbar("H Upper:", "Processed:", 255, 255, onHUpperBkChange)
	cv.CreateTrackbar("S Upper:", "Processed:", 40, 255, onSUpperBkChange)
	cv.CreateTrackbar("V Upper:", "Processed:", 255, 255, onVUpperBkChange)
