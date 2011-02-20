import cv
import time
from imageProcFunctions import *
from navigation import *
from server import *
from worldstate import *
from setup import *

def draw_on_image(image, object_descriptors):

    cv.Circle(image, object_descriptors[0][0], 2, cv.RGB(0,0,0),-1)	
    cv.Circle(image, object_descriptors[1][0], 2, cv.RGB(0,0,0),-1)	
    cv.Circle(image, object_descriptors[2][0], 2, cv.RGB(0,0,0),-1)
    	
    cv.ShowImage("Original:", orig)
    cv.ShowImage("Processed:", image)
	
def update_worldstate(object_descriptors):
	
    WorldState.lock.acquire()
    WorldState.ball["position"]["x"] = object_descriptors[0][0][0]
    WorldState.ball["position"]["y"] = object_descriptors[0][0][1]
    WorldState.blue["position"]["x"] = object_descriptors[1][0][0]
    WorldState.blue["position"]["y"] = object_descriptors[1][0][1]
    WorldState.blue["rotation"] = object_descriptors[1][1]
    WorldState.yellow["position"]["x"] = object_descriptors[2][0][0]
    WorldState.yellow["position"]["y"] = object_descriptors[2][0][1]
    WorldState.yellow["rotation"] = object_descriptors[2][1]	
    WorldState.lock.release()

setup_system()
cam = cv.CaptureFromCAM(0)

last_time = 0

while (True):
    
    start = time.time()
    image = cv.QueryFrame(cam)
    cropRect = (75, 80, 554, 325)
    cv.SetImageROI(image, cropRect)
    orig = cv.CloneImage(image)
    processed = cv.CloneImage(orig)
    
    object_descriptors = [find_object(image,"RED"), find_object(image,"BLUE"), find_object(image,"YELLOW")]
    print "Blue orientation: ", object_descriptors[1][1]
    print "Yellow orientation:", object_descriptors[2][1]
    
    draw_on_image(processed, object_descriptors)
    
    update_worldstate(object_descriptors)
    
    end = time.time()
    fps = 0.9 * (1/(end-start)) + 0.1 * last_time
    print "FPS:", fps
    last_time = fps 
    cv.WaitKey(25)
