import cv
from math import * 

def euclid_distance(pt1, pt2):
    distance = sqrt((pt1[0] - pt2[0])**2 + (pt1[1] - pt2[1])**2)	   
    if (distance < 2):
	return 0
    return distance

def calculate_speed(old_point, new_point, time):
    return (euclid_distance(new_point, old_point) / time)
	
def calculate_bearing(source_point, dest_point):
    return (((atan2(dest_point[1] - source_point[1], dest_point[0] - source_point[0]) * (180/pi)) + 90) + 360) % 360
