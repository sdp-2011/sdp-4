import cv
import time
from imageProcFunctions import *
from navigation import *
from server import *
from worldstate import *
from setup import *
import os
import curses

def draw_on_image(image, center_points, other_points):
	cv.Circle(image, center_points[0], 2, cv.RGB(0,0,0),-1)	
	cv.Circle(image, center_points[1], 2, cv.RGB(0,0,0),-1)	
	cv.Circle(image, center_points[2], 2, cv.RGB(0,0,0),-1)
	
	cv.Line(image, other_points[0], center_points[1], cv.RGB(255,0,0))
	cv.Line(image, other_points[1], center_points[2], cv.RGB(0,255,0))
		
	cv.ShowImage("Original:", orig)
	cv.ShowImage("Processed:", image)
	
def update_worldstate(center_points, other_points):
	
	WorldState.lock.acquire()
	WorldState.ball["position"]["x"] = center_points[0][0]
	WorldState.ball["position"]["y"] = center_points[0][1]
	WorldState.blue["position"]["x"] = center_points[1][0]
	WorldState.blue["position"]["y"] = center_points[1][1]
	WorldState.blue["rotation"] = (int(calculate_bearing(other_points[0], center_points[1])) - 180) % 360
	WorldState.yellow["position"]["x"] = center_points[2][0]
	WorldState.yellow["position"]["y"] = center_points[2][1]
	WorldState.yellow["rotation"] = (int(calculate_bearing(other_points[1], center_points[2])) - 180) % 360	
	WorldState.lock.release()

setup_system()
cam = cv.CaptureFromCAM(0)
os.system('/home/s0806628/sdp/vision/src/modv4l.sh')

stdscr = curses.initscr()
curses.start_color()

curses.init_pair(1, curses.COLOR_RED, curses.COLOR_BLACK)
curses.init_pair(2, curses.COLOR_BLUE, curses.COLOR_BLACK)
curses.init_pair(3, curses.COLOR_YELLOW, curses.COLOR_BLACK)

old_ball_position = (0,0)
old_blue_position = (0,0)
old_yellow_position = (0,0)

old_time = 0

while (True):
	start = time.time()
	image = cv.QueryFrame(cam)
	crop_rect = (20, 80, 610, 325)
	cv.SetImageROI(image, crop_rect)
	orig = cv.CloneImage(image)
	processed = cv.CloneImage(orig)

	ball_center = find_object(image,"RED")
	ball_center = (int(ball_center[0]), int(ball_center[1]))
	blue_center = find_object(image,"BLUE")
	blue_center = (int(blue_center[0]), int(blue_center[1]))
	yellow_center = find_object(image,"YELLOW")
	yellow_center = (int(yellow_center[0]), int(yellow_center[1]))
	
	yellow_image = cv.CloneImage(orig)
	yellow_crop_rect = (yellow_center[0] - 15, yellow_center[1] + 45, 74, 74)
	cv.SetImageROI(yellow_image, yellow_crop_rect)
	cv.ShowImage("YellowBlack:", yellow_image)
	
	blue_image = cv.CloneImage(orig)	
	blue_crop_rect = (blue_center[0] - 10, blue_center[1] + 45, 66, 66)
	cv.SetImageROI(blue_image, blue_crop_rect)
	cv.ShowImage("BlueBlack:", blue_image)	
		
	blue_white = find_object(blue_image, "BBLACK")
	blue_white = (int(blue_white[0]) + blue_crop_rect[0] - 22, int(blue_white[1]) + blue_crop_rect[1] - 80)	
	
	yellow_white = find_object(yellow_image, "YBLACK")
	yellow_white = (int(yellow_white[0]) + blue_crop_rect[0] - 22, int(yellow_white[1]) + blue_crop_rect[1] - 80)
	
	center_points = (ball_center, blue_center, yellow_center)
	other_points = (blue_white, yellow_white)
	draw_on_image(processed, center_points, other_points)
	
	update_worldstate(center_points, other_points)
    
	now = time.time()
	time_diff = now - old_time

	stdscr.erase()
	stdscr.addstr(0,0, "Blue Player", curses.A_UNDERLINE | curses.color_pair(2))
	stdscr.addstr(1,0, "Orientation: " + str((int(calculate_bearing(blue_white, blue_center)) - 180) % 360))
	stdscr.addstr(2,0, "Position:" + str(blue_center))
	stdscr.addstr(3,0, "Moved:" + str(euclid_distance(old_blue_position, blue_center)))
	stdscr.addstr(4,0, "Speed:" + str(calculate_speed(old_blue_position, blue_center, time_diff)))
    
	stdscr.addstr(6,0, "Yellow Player", curses.A_UNDERLINE | curses.color_pair(3))
	stdscr.addstr(7,0, "Orientation: " + str((int(calculate_bearing(yellow_white, yellow_center)) - 180) % 360))
	stdscr.addstr(8,0, "Position:" + str(yellow_center))
	stdscr.addstr(9,0, "Moved:" + str(euclid_distance(old_yellow_position, yellow_center)))
	stdscr.addstr(10,0, "Speed:" + str(calculate_speed(old_yellow_position, yellow_center, time_diff)))

	stdscr.addstr(12,0, "Ball", curses.A_UNDERLINE | curses.color_pair(1))
	stdscr.addstr(13,0, "Position:" + str(ball_center))
	stdscr.addstr(14,0, "Moved:" + str(euclid_distance(old_ball_position, ball_center)))
	stdscr.addstr(15,0, "Speed:" + str(calculate_speed(old_ball_position, ball_center, time_diff)))

	stdscr.addstr(17,0, "FPS:" + str(1/(now-start)))
	stdscr.refresh()

	cv.WaitKey(4)
	old_time = now	
