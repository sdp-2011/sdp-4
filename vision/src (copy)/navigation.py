import cv
from math import * 

def euclidDistance(pt1, pt2):
	return sqrt((pt1[0] - pt2[0])^2 + (pt1[1] - pt2[1])^2)	

def calculateSpeed(oldPoint, newPoint, time):
	return (euclidDistance(newPoint, oldPoint) / time)
	
def calculateBearing(sourcePoint, destPoint):
	return atan2(destPoint[1] - sourcePoint[1],	destPoint[0] - sourcePoint[0]) * (180/pi)
